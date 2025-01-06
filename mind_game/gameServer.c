#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <pthread.h>
#include <time.h>

#define MAX_CLIENTS 50

// Définition des constantes représentant différents types de messages échangés dans le jeu
#define MSG_ROUND_NUMBER 1 // Message pour informer du numéro du round en cours
#define MSG_WIN_STATUS 2 // Message pour indiquer le statut de victoire d'un round
#define MSG_CARD 3 // Message lié à l'attribution des cartes
#define MSG_GAME_STATE 4 // Message pour informer de l'état général du jeu (en cours)
#define MSG_GAME_STARTED 5 // Message indiquant que le round a commencé
#define MSG_NORMAL 6 // Message utilisé pour des messages normaux
#define MSG_LOST 7 // Message pour indiquer qu'un joueur a perdu un round en jouant un mauvais numéro
#define MSG_END_GAME 8 // Message pour signaler la fin de la partie cause de mauvais carte jouer
#define MSG_GONE_CL 9 // Message indiquant qu'un client (joueur) s'est déconnecté
#define MSG_REQUEST_NAME 10 // Message demandant le nom d'un joueur
#define MSG_ALL_ROUND_PLAYED 11 // Message pour informer que tous les rounds ont été joués


// Définition d'une structure pour stocker les informations sur chaque joueur
typedef struct {
    int socket;                   // Socket associé au joueur
    char name[100];               // Nom du joueur (maximum 100 caractères)
    int nb_victoire;              // Nombre de victoires du joueur
    int nb_defaite;               // Nombre de défaites du joueur
    int total_manche_play;        // Nombre total de manches jouées par le joueur
    int cards_played;             // Nombre total de cartes jouées par le joueur
    int streak_victory;           // Série actuelle de victoires consécutives
} client_data_t;



// Variables globales pour la synchronisation
client_data_t clients[MAX_CLIENTS]; // Tableau global pour stocker les données de tous les joueurs


int serverRun = 1;   // Indicateur pour maintenir le serveur en fonctionnement
int nbclient = 0;    // Compteur de clients connectés
int totalClient;  // Nombre total de clients requis pour démarrer la partie
int game_started = 0; // Indique si la partie a commencé
int nbRound;  // Nombre de manches dans le jeu

int roundInProgress = 0;  // Indique le round en cours
int end_of_tour = 0;      // Indique la fin d'un round (toutes les cartes dans ce round on été jouer)
int all_players_ready = 0;    // Nombre de joueurs prêts pour un round
int all_players_ans = 0;    // Indique si tous les joueurs ont donner leurs réponses au serveur (lors de la fin d'un round)
int end_game = 0;       // Indique si la partie est terminée
int win; //status de victoire d'un round

pthread_t all_clients_thread[MAX_CLIENTS]; // Tableau pour stocker les threads des clients connectés
int* numbers_orders; // Tableau pour stocker les cartes (numéros) joués dans les manches

pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t sync_msg = PTHREAD_MUTEX_INITIALIZER;


void error(char *msg)
{
    perror(msg);
    exit(1);
}

// Fonction pour ajouter un client et initialiser ses statistiques
void ajout_client_stat(int indice, int socket, const char* name)
{
    client_data_t data;
    data.socket = socket;
    strncpy(data.name, name, sizeof(data.name) - 1); // Copie la chaîne tout en évitant les dépassements
    data.name[sizeof(data.name) - 1] = '\0';        // Ajoute un terminateur nul
    data.nb_victoire = 0;                           // Initialise les statistiques du client
    data.nb_defaite = 0;
    data.total_manche_play = 0;
    data.cards_played = 0;
    data.streak_victory = 0;
    pthread_mutex_lock(&lock);
    clients[indice-1] = data;// Ajoute le joueur au tableau global
    pthread_mutex_unlock(&lock);
}

// Fonction pour mettre à jour les statistiques d'un client
void update_client_stat(int indice, int statWin, int nb_card)
{
    pthread_mutex_lock(&lock);
    clients[indice-1].total_manche_play++; // Incrémente le total des manches jouées
    clients[indice-1].cards_played += nb_card; // Ajoute les cartes jouées au total
    if(statWin)
    {
        clients[indice-1].nb_victoire++; // Incrémente les victoires si le client a gagné
        clients[indice-1].streak_victory++;  // Met à jour la série de victoires
    }
    else
    {
        clients[indice-1].nb_defaite++;      // Incrémente les défaites si le client a perdu
    }
    pthread_mutex_unlock(&lock);
}

// Fonction pour diffuser un message à tous les joueurs sauf celui qui n'est pas concerné
void broadcast_message(int type, const char *msg, int new_socket) {
    pthread_mutex_lock(&sync_msg);
    for (int i = 0; i < nbclient; i++) {
        if(new_socket != clients[i].socket){
            send(clients[i].socket, &type, sizeof(type), 0); // Envoi du type de message
            send(clients[i].socket, msg, strlen(msg) + 1, 0); // Envoie du message;
        }
    }
    pthread_mutex_unlock(&sync_msg);
}

// Fonction pour initialiser le tableau des cartes joués
int* init_numbers()
{
    int total = nbRound * totalClient;
    int *numbs = (int*) calloc(total, sizeof(int)); // Alloue et initialise un tableau pour stocker les cartes
    if (numbs == NULL) {
        perror("Échec de l'allocation mémoire");
        exit(1);
    }
    return numbs;
}

// Fonction pour réinitialiser le tableau des cartes
void reset_number()
{
    int total = nbRound * totalClient;
    for(int i=0; i<total; i++)
    {
        numbers_orders[i] = 0; // Réinitialise tous les numéros à 0
    }
}

// Fonction pour ajouter un numéro dans l'ordre (tri croissant)
int add_number(int number, int round)
{
    pthread_mutex_lock(&lock);
    int total_plays = (round + 1) * totalClient;
    int reussite = 1;   // Indique si la carte a été ajouté avec succès
    int i = 0;

    // Chercher l'emplacement correct pour insérer `number`
    while(i < total_plays && reussite)
    {
        if(number == numbers_orders[i])
        {
            reussite = 0; // Échec si la carte existe déjà
        }
        else if (numbers_orders[i] == 0) 
        {
            numbers_orders[i] = number; // Ajout de la carte à la première position libre
            i = total_plays; //pour sortir directement de la boucle
        }
        else if(number < numbers_orders[i])
        {
            // Décaler tous les éléments suivants pour insérer `number`
            for (int j = total_plays - 1; j > i; j--) 
            {
                numbers_orders[j] = numbers_orders[j - 1];
            }
            numbers_orders[i] = number; 
            i = total_plays; //pour sortir directement de la boucle
        }
        i++;
    }

    pthread_mutex_unlock(&lock);
    return reussite;
}

// Vérifie si une carte correspond à une carte valide pour le tour actuel (si le joueur a jouer la bonne carte)
int verif_round(int number, int size)
{
    for(int i=0; i<size; i++)
    {
        if(numbers_orders[i] != 0)
        {
            if(number == numbers_orders[i])
            {
                numbers_orders[i] = 0; // Réinitialise le numéro correspondant
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }
    return 0;
}

//fonctionne qui donnes les cartes aux joueurs en fonction du round en cours
void distribution_of_cards(int new_socket, int round) {
    int type = MSG_CARD;
    char msg[500];
    srand(time(NULL));
    sleep(1);
    for (int i = 0; i < (roundInProgress+1); i++) {
        int random_num = (rand() % 100) + 1; // Génère un numéro aléatoire entre 1 et 100
        if(add_number(random_num, roundInProgress) == 1) // si l'ajout réussi
        {
            //envoie de la carte au joueur en question
            sleep(1);
            send(new_socket, &type, sizeof(type), 0);
            send(new_socket, &random_num, sizeof(random_num), 0);
            sleep(1);
        }
        else{
            i--; // Réessaye si l'ajout dans le tableau des a échoué (carte existante)
        }
        
    }
    pthread_mutex_lock(&lock);
    all_players_ready++; //indique que ce joueur a recu toutes ses cartes et prêt a jouer
    pthread_mutex_unlock(&lock);
}

//fonction qui gère un client (joueur) appéler par un thread
void* handle_client(void* arg) {
    client_data_t* client_data = (client_data_t*) arg;
    int new_socket = client_data->socket; //recupère le socket lié au joueur
    int id = nbclient; //recupère son indice pour pouvoir stocker ses informations

    // Demander le nom du joueur
    char name[100];  // Variable pour stocker le nom
    int type = MSG_REQUEST_NAME;  // type de message pour la demande de nom
    send(new_socket, &type, sizeof(type), 0);  // Envoyer la demande de nom

    // Recevoir le nom du joueur(client)
    if (read(new_socket, name, sizeof(name)) <= 0) {
        perror("Erreur de lecture du nom du client");
        close(new_socket);
        pthread_exit(NULL);
    }

    //Ajouter les informations du joueur au tableau contenant tout les joueurs 
    ajout_client_stat(id, new_socket, name);
    free(client_data);

    //tant que le jeu n'est pas finie (le joueur n'a pas décider d'arreter ou tout les rounds n'ont pas été effectuer)
    while(!end_game)
    {
        int type;

        //envoie du statut permettant de savoir si la partie peut commencer (c'est à dire que tout les joueurs sont connecter)
        if (send(new_socket, &game_started, sizeof(game_started), 0) < 0) {
            perror("Erreur d'envoi de game_status");
            break;
        }

        //attendre les aux joueurs avant de démarrer la partie
        while(!game_started)
        {
            usleep(500000);
        }
        

        // Envoie du message de bienvenue au joueur pour désigner le début de la partie
        type = MSG_NORMAL;
        char msg[500] = "--------------------------GAME ON-----------------------------";
        send(new_socket, &type, sizeof(type), 0);
        send(new_socket, msg, strlen(msg) + 1 ,0);
        sleep(1);

        roundInProgress = 0; //pour connaitre le round en cours
        
        //tant qu'on a pas effectuer tout les rounds et que la partie est en cours 
        while(roundInProgress < nbRound && game_started)
        {
            //Envoie du round en cours au joueur
            type = MSG_ROUND_NUMBER;
            int round_number = roundInProgress + 1;
            send(new_socket, &type, sizeof(type), 0);
            send(new_socket, &round_number, sizeof(round_number), 0);

            //distribution des cartes au joueur en fonction du round en cours (nombre carte envoyer = le round en cours)
            distribution_of_cards(new_socket, roundInProgress);
            sleep(1);
            
            //tant que tout les joueurs n'ont pas reçu toutes leurs cartes, le joueur attend
            while(all_players_ready < totalClient)
            {
                usleep(500000);
            }

            //Envoie du message pour désigner que le round a commencer
            type = MSG_GAME_STARTED;
            send(new_socket, &type, sizeof(type), 0);
        
            int backround = roundInProgress; //enregistre ce round (amener à etre incrémenter)
            int number; //pour connaitre le nombre que le joueur a jouer
            int total_plays = (roundInProgress+1) * totalClient; //indicateur pour savoir si toutes les cartes ont été jouer pour le round en question
            win = 1; //status de victoire du round initialiser à 1
            int has_cards = 1; // Indicateur pour vérifier si le joueurs a des cartes
            int nbcard = 0; // Indicateur permettant de vérifer le nombre de carte joué par le joueur
            
            //tant que toutes les cartes pour ce round n'ont pas été jouer
            while(end_of_tour<total_plays)
            {
                //si le joueur à toujours des cartes
                if (has_cards) {
                    //envoie du message ou statut pour lui demander de jouer une carte
                    type = MSG_GAME_STATE;
                    send(new_socket, &type, sizeof(type), 0);
                    sleep(1);

                    // Lecture du numéro joué par le jouer
                    if (read(new_socket, &number, sizeof(number)) <= 0) {
                        perror("Erreur de lecture du nombre du client");
                        end_game = 1;
                        game_started = 0;
                        break;
                    }

                    if (number == 0) {  // Si le nombre jouer est égale à 0 alors cela signifit que le joueur n'a plus de cartes
                        pthread_mutex_lock(&lock);
                        type = MSG_NORMAL;
                        has_cards = 0;  // Marquer le joueur comme n'ayant plus de cartes
                        //envoie du msg lui disant qu'il n'a plus de carte
                        char message[500] = "Vous n'avez plus de carte, veuillez patienter jusqu'à la fin du round...";
                        send(new_socket, &type, sizeof(type), 0);
                        send(new_socket, message, strlen(message) + 1, 0);
                        //Envoie du msg aux joueurs pour dire que ce joueur n'a plus de cartes en sa possesion 
                        sprintf(message, "%s n'a plus de carte", name);
                        broadcast_message(type, message, new_socket);
                        pthread_mutex_unlock(&lock);
                    } else if (number > 0) { // si il joue un nombre valide
                        nbcard++;//incrémentation de son nombre de carte jouer
                        pthread_mutex_lock(&lock);
                        if (verif_round(number, total_plays)) { //on vérifie si il a jouer le bon numéro
                            // Numéro valide
                            end_of_tour++; //incrémente le nombre de carte jouer 
                            //envoie du message aux joueurs pour indiquer la carte jouer par ce joueur
                            type = MSG_NORMAL;
                            sprintf(msg, "%s a joué le numéro %d", name, number);
                            broadcast_message(type, msg, new_socket);
                        } else {//sinon 
                            // Numéro invalide, fin de tour
                            win = 0; //statut de victoire du round à 0
                            end_of_tour = total_plays; //toutes les cartes jouer pour pouvoir sortie de la boucle
                            
                            // Envoie MSG_END_GAME et le message de fin de round
                            type = MSG_LOST;
                            send(new_socket, &type, sizeof(type), 0);
                            // Message pour les autres jouers pour indiquer que le joueur à jouer un mauvais numéro et mettre fin au round
                            type = MSG_END_GAME;
                            sprintf(msg, "%s a joué le numéro %d INVALIDE", name, number);
                            broadcast_message(type, msg, new_socket);
                            
                        }
                        pthread_mutex_unlock(&lock);
                    } else if (number == -2) //si le joueur joue le numéro -2 cela signifie qu'il a reçu un message de fin de round pour cause de mauvaise carte jouer (type msg -> MSG_END_GAME)
                    {   pthread_mutex_lock(&lock);
                        // Reçoit confirmation de fin de participation du joueur
                        end_of_tour = total_plays;
                        win = 0;
                        pthread_mutex_unlock(&lock);
                    }
                } else {
                    // Si le joueur n'a plus de carte, il attend et on continue le tour sans lui envoyer de message
                    usleep(500000);
                }
            }
            
            sleep(1);

            if (end_of_tour == total_plays) {
                all_players_ans = 0;
                update_client_stat(id, win, nbcard); //mis à jour des informations du joueur
                int typeW = MSG_WIN_STATUS;
                int win_status = win;
                // Envoyer le statut de victoire du round
                send(new_socket, &typeW, sizeof(typeW), 0); //envoie du type de msg
                send(new_socket, &win_status, sizeof(win_status), 0); //envoie du status

                //lire la réponse du joueur pour savoir si il veut continuer, arreter ou reprendre la partie
                int continuer;
                if (read(new_socket, &continuer, sizeof(continuer)) <= 0) {
                    perror("Erreur de lecture du choix du client");
                    break;
                }
                
                //si il décide de continuer
                if (continuer == 1) {
                    pthread_mutex_lock(&lock);
                    //on vérifie si le round précédent est égale au round en cours
                    if (backround == roundInProgress) {
                        roundInProgress++; //on passe on round suivant
                        //on réinitialise les variables
                        end_of_tour = 0;
                        all_players_ready = 0;
                        reset_number(); // on supprime les cartes présent dans le tableau de carte
                    }
                    all_players_ans++; //on indique que ce joueur est prêt a continuer (il a donner sa réponse)
                    pthread_mutex_unlock(&lock);
                } else if(continuer == 0){// si il décide d'arreter
                    pthread_mutex_lock(&lock);
                    //on envoie le message aux autres joueurs pour dire que ce joueur décide d'arréter et on indique la fin de partie
                    type = MSG_GONE_CL; //type de message envoyer
                    sprintf(msg, "%s s'est déconnecter\n !!! FIN DE PARTIE !!!", name); //le message
                    broadcast_message(type, msg, new_socket); //envoie du message aux autres joueurs
                    //on réinitialise les variables
                    end_of_tour = 0; 
                    all_players_ready = 0;
                    game_started = 0; // pour sortie de la boucle indiquant la fin de partie
                    nbclient--; //on décrémente le nombre de joueur 
                    end_game = 1; // pour sortie de la boucle indiquant la fin du jeu (fermeture du thread lié à ce joueur)
                    pthread_mutex_unlock(&lock);
                }
                //sinon, le joueur décide de recommencer la partie
                else{ 
                    pthread_mutex_lock(&lock);
                    //on réinitialise les variables
                    roundInProgress = 0;
                    end_of_tour = 0;
                    all_players_ready = 0;
                    reset_number(); // on supprime les cartes présent dans le tableau de carte
                    all_players_ans++; //on indique que ce joueur est prêt a recommencer (il a donner sa réponse)
                    pthread_mutex_unlock(&lock);
                }
            }
            //tant que tout les joueurs n'ont pas donner leurs réponses on attend
            while(all_players_ans < nbclient)
            {
                usleep(500000);
            }
        }

        //si les joueurs ont effectuer tout les rounds, on envoie un message et on met fin au jeu 
        if(roundInProgress == nbRound)
        {  
            //envoie du message indiquant que les joueurs ont remporté la partie
            type = MSG_ALL_ROUND_PLAYED;
            char message[500] = "----Tous les rounds sont effectuer | VOUS AVEZ GAGNER----";
            send(new_socket, &type, sizeof(type), 0);
            send(new_socket, message, strlen(message) + 1, 0);
            end_game = 1; // pour sortie de la boucle indiquant la fin du jeu (fermeture du thread lié à ce joueur)
        }

    }

    close(new_socket);//fermeture du socket lié à ce joueur
    pthread_exit(NULL);//fermeture du thread
}



void sort_classement(client_data_t *data, int *count)
{
    // Mettre à jour les streaks des joueurs actuels
    for (int i = 0; i < totalClient; i++) {
        int found = 0; // Variable pour vérifier si le joueur est trouvé dans le tableau
        for (int j = 0; j < *count; j++) {
            // Comparer le nom du client actuel avec celui dans le tableau de classement
            if (strcmp(clients[i].name, data[j].name) == 0) {
                // Si le joueur existe, mettre à jour son streak
                data[j].streak_victory += clients[i].streak_victory;
                found = 1; // Marquer comme trouvé
                break; // Arrêter la recherche pour ce client
            }
        }
        if (!found) { //client pas trouver
            // Nouveau joueur, ajouter au tableau
            data[*count] = clients[i];
            (*count)++;
        }
    }

    // Tri par streak décroissant
    for (int i = 0; i < *count - 1; i++) {
        for (int j = i + 1; j < *count; j++) {
            if (data[i].streak_victory < data[j].streak_victory) {
                // Échanger les positions si l'ordre est incorrect
                client_data_t temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
        }
    }

}

void generate_stat_classement()
{
    const char *fileStat = "Statistique.txt"; // Nom du fichier pour les statistiques
    const char *fileClass = "Classement.csv"; // Nom du fichier pour le classement
    
    // Ouvrir le fichier des statistiques en mode ajout
    FILE *file = fopen(fileStat, "a");
    if (file == NULL) {
        perror("Erreur lors de l'ouverture du fichier");
    }

    // Obtenir la date et l'heure actuelles
    time_t now = time(NULL);
    if (now == -1) {
        perror("Erreur lors de la récupération de l'heure");
        fclose(file);
    }
    struct tm *local = localtime(&now);
    if (local == NULL) {
        perror("Erreur lors de la conversion de l'heure");
        fclose(file);
    }

    // Écrire la date et l'heure dans le fichier
    fprintf(file, "Date de la partie : %02d/%02d/%04d %02d:%02d:%02d\n\n", 
            local->tm_mday, local->tm_mon + 1, local->tm_year + 1900, 
            local->tm_hour, local->tm_min, local->tm_sec);
    
    fprintf(file, "Player Name || NB VICTOIRE || NB DEFAITE || TOTAL ROUND JOUER || TOTAL CARTES JOUER\n");

    for(int i=0; i<totalClient; i++)
    {
        fprintf(file, "%s        ||      %d      ||      %d     ||       %d           ||       %d\n",
                clients[i].name, clients[i].nb_victoire, clients[i].nb_defaite, clients[i].total_manche_play, clients[i].cards_played);
    }

    fprintf(file, "\n\n");
    // Fermer le fichier
    fclose(file);
    // Générer un fichier PDF à partir des statistiques
    char sys[100] = "enscript Statistique.txt -o - | ps2pdf - Stats.pdf";
    system(sys);


    //pour le classement
    FILE *f = fopen(fileClass, "r");
    if (f == NULL) {
        perror("Erreur lors de l'ouverture du fichier en lecture");
        f = fopen(fileClass, "w"); // Crée un fichier s'il n'existe pas
        if (f == NULL) {
            perror("Erreur lors de la création du fichier Classement");
        }
    }
    client_data_t existing_clients[MAX_CLIENTS];
    int count = 0;
    char line[1024];

    // Lire les joueurs existants dans le classement
    while (fgets(line, sizeof(line), f)) {
        sscanf(line, "%99[^,], %d", existing_clients[count].name, &existing_clients[count].streak_victory);
        count++;   
    }
    fclose(f);

    sort_classement(existing_clients, &count);

    f = fopen(fileClass, "w");
    if(f == NULL)
    {
        perror("Erreur lors de l'ouverture du fichier");
    }
    
    printf("------------------------------ CLASSEMENT -----------------------------------\nNOM      |||     NOMBRE DE VICTOIRE CONSÉCUTIVE \n");
    for(int i=0; i < count && i < 10; i++)
    {
        fprintf(f, "%s,%d\n", existing_clients[i].name, existing_clients[i].streak_victory);
        printf("%s      |||        %d\n",existing_clients[i].name, existing_clients[i].streak_victory);
    }

    fclose(file);    

}

int main(int argc, char *argv[])
{
    if(argc < 4) {
        fprintf(stderr,"ERROR, failure to launch the game server\n");
        exit(1);
    }

    int sockServ, newsockfd, clilen;
    struct sockaddr_in serv_addr;
    int addrlen = sizeof(serv_addr);
    totalClient = atoi(argv[2]); //récupère le nombre client accpeter par le serveur
    nbRound = atoi(argv[3]); //recupere le nombre total de round
    numbers_orders = init_numbers(); //initialiser le tableau de carte

    sockServ = socket(AF_INET, SOCK_STREAM, 0);
    if(sockServ < 0)
        error("ERROR opening socket");

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(atoi(argv[1]));

    if (bind(sockServ, (struct sockaddr *) &serv_addr,
              sizeof(serv_addr)) < 0) 
              error("ERROR on binding");
    listen(sockServ,totalClient);

    printf("Serveur en attente de connexions...\n");

    while (serverRun) {
        // Acceptation de la connexion d'un client
        if ((newsockfd = accept(sockServ, (struct sockaddr *)&serv_addr, (socklen_t*)&addrlen)) < 0) {
            error("accept failed");
        }
        // Allocation dynamique de la structure contenant les données du client
        client_data_t* client_data = malloc(sizeof(client_data_t));
        client_data->socket = newsockfd;

        // Créer un thread pour chaque client
        if (pthread_create(&all_clients_thread[nbclient], NULL, handle_client, (void*)client_data) != 0) {
            perror("Erreur lors de la création du thread");
            free(client_data);
            close(newsockfd);
        } else {
            //printf("Client %d connecté et géré par un thread.\n", nbclient + 1);
            nbclient++;
        }
        // Limitation pour éviter de dépasser le nombre maximal de clients
        if (nbclient >= totalClient) {
            game_started = 1;
            printf("Tous les clients sont connecter debut de la partie.\n");
            serverRun = 0;
        }
    }
    
    // Attendre que tous les threads terminent
    for (int i = 0; i < totalClient; i++) {
        pthread_join(all_clients_thread[i], NULL);
    }
    
    printf("Génération des statistiques et du classement\n");
    generate_stat_classement();

    close(sockServ);
    return 0;
}
