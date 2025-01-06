#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <arpa/inet.h>
#include <pthread.h>
#include <time.h> 

// Définition des constantes représentant différents types de messages échangés dans le jeu
#define MSG_ROUND_NUMBER 1 // Message pour informer du numéro du round en cours
#define MSG_WIN_STATUS 2 // Message pour indiquer le statut de victoire d'un round
#define MSG_CARD 3 // Message lié à l'attribution des cartes
#define MSG_GAME_STATE 4 // Message pour informer de l'état général du jeu (en cours)
#define MSG_GAME_STARTED 5 // Message indiquant que la round a commencé
#define MSG_NORMAL 6 // Message utilisé pour des messages normaux
#define MSG_LOST 7 // Message pour indiquer qu'un joueur a perdu un round en jouant un mauvais numéro
#define MSG_END_GAME 8 // Message pour signaler la fin de la partie cause de mauvais carte jouer
#define MSG_GONE_CL 9 // Message indiquant qu'un client (joueur) s'est déconnecté
#define MSG_REQUEST_NAME 10 // Message demandant le nom d'un joueur
#define MSG_ALL_ROUND_PLAYED 11 // Message pour informer que tous les rounds ont été joués

// Définition des constantes
#define MAX_ROUNDS 6 //représente le nombres de rounds maximun qu'il peut y avoir
#define MAX_CARDS 100 // représente le nombres de cartes qui existe
#define TRAINING_FILE "training_data.csv"

// Variable globale 
int stop_thread = 1; // Variable globale pour indiquer si le thread pour l'entrer utilisateur doit s'arrêter
int sock = 0;        // Descripteur de socket
int tour;            // Compteur de tours pour un round
int* cards;          //les cartes du robot
int k = 0;           // Indice utilisé pour ajouter des cartes au tableau
int rnd;             // Round en cours
int learned_cards[MAX_ROUNDS + 1][MAX_CARDS + 1] = {0};  // Historique des cartes jouées pour l'apprentissage
pthread_mutex_t file_mutex = PTHREAD_MUTEX_INITIALIZER; //garantie l'accès concurrent au fichier
pthread_mutex_t sync_mutex = PTHREAD_MUTEX_INITIALIZER;
 

// Fonction pour enregistrer les données d'entraînement dans un fichier CSV
void save_training_data(int round, int card_played, int result) {
    pthread_mutex_lock(&file_mutex);
    //Ouverture du fichier en ajout (si le fichier n'existe pas il le créer automatiquement)
    FILE* file = fopen("training_data.csv", "a");
    if (file == NULL) {
        perror("Erreur d'ouverture du fichier de données");
        return;
    }
    fprintf(file, "%d,%d,%d\n", round, card_played, result); //enregistrement du round en cours, de la carte jouer durant ce round et du résultat du round
    fclose(file);
    pthread_mutex_unlock(&file_mutex);
}


// Fonction pour charger les données d'entraînement depuis un fichier CSV
void train_model(const char* filename) {
    pthread_mutex_lock(&file_mutex);
    //Ouverture du fichier en lecture
    FILE* file = fopen(filename, "r");
    if (file == NULL) {
        perror("Erreur d'ouverture du fichier de données");
        return;
    }

    char line[1024];
    while (fgets(line, sizeof(line), file)) {
        //pour chaque ligne on récupère le round, la carte et le résultat;
        int round, card_played, result;
        sscanf(line, "%d,%d,%d", &round, &card_played, &result);
        learned_cards[round][card_played]++; //on incrémente la fréquence de la carte
    }

    fclose(file);
    pthread_mutex_unlock(&file_mutex);
}

// Fonction pour prédire la meilleure carte à jouer en fonction des données apprises
int predict_card(int round, int* cards, int size) {
    int best_card = -1; //pour recuperer la carte
    int best_frequency = -1; //pour connaitre la fréquence de la carte

    //pour chaque existante
    for (int i = 0; i < size; i++) {
        int card = cards[i];
        //si la carte a une fréquence élever aux cartes on met a jouer les variables
        if (card != 0 && learned_cards[round][card] > best_frequency) {
            best_frequency = learned_cards[round][card];
            best_card = card;
        }
    }

    return best_card;
}


// Fonction qui gère l'entraînement du modèle dans un thread séparé
void* train_model_thread(void* arg) {
    train_model(TRAINING_FILE);
    pthread_exit(NULL);
}


// Fonction pour allouer dynamiquement un tableau de cartes
int* my_cards(int size) {
    int* cards = malloc(size * sizeof(int));
    if (cards == NULL) {
        fprintf(stderr, "Erreur d'allocation mémoire\n");
        exit(1); // Quitte le programme si l'allocation échoue
    }
    return cards;
}

// Initialise une carte à une position donnée
void init_cards(int* cards, int size, int indice, int number) {
    if (indice >= 0 && indice < size) {
        cards[indice] = number; // Ajoute la carte à l'indice spécifié
    } else {
        printf("Impossible d'ajouter la carte, tableau plein\n");
    }
}


// Supprime une carte du tableau en la remplaçant par 0
int remove_cards(int* cards, int card, int size) {
    for (int i = 0; i < size; i++) {
        if (cards[i] == card) {
            cards[i] = 0; // Supprime la carte
            return 1; // Suppression réussie
        }
    }
    return 0; // Carte introuvable
}

// Fonction pour trouver la carte minimale dans le tableau
int min_Cards(int* cards, int size)
{
    int min = cards[0];
    for(int i=1; i< size; i++)
    {
        if (min == 0) {
            min = cards[i];  // Mettre à jour min si un élément plus petit est trouvé
        }
        else if(cards[i] != 0 && cards[i] < min)
        {
            min = cards[i];
        }
    }
    return min;
}


// Fonction exécutée dans un thread pour jouer des cartes automatiquement
void* play_cards(void* arg)
{
    // Active l'annulation immédiate du thread
    pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, NULL);
    pthread_setcanceltype(PTHREAD_CANCEL_ASYNCHRONOUS, NULL);

    srand(time(NULL) ^ getpid());
    int delay = rand() % 15 + 3;
    while (1) {

        sleep(delay);
        if(!stop_thread && tour<rnd)
        {
            int card = predict_card(rnd, cards, rnd);
            
            if (card == -1) {
                // Pas de prédiction valide, fallback à la carte minimale
                card = min_Cards(cards, rnd);
            }
            
            if(remove_cards(cards, card, rnd))
            {
                pthread_mutex_lock(&sync_mutex);
                if (send(sock, &card, sizeof(card), 0) < 0) {
                    perror("Erreur d'envoi de votre nombre");
                }
                pthread_mutex_unlock(&sync_mutex);
                tour++;

                // Enregistrer la carte jouée pour l'apprentissage
                int result = -1; // On ne connaît pas encore le résultat ici
                save_training_data(rnd, card, result);
            }
            else {
                printf("Carte inexistante, impossible de la supprimer\n");
                int choice = -1;
                if (send(sock, &choice, sizeof(choice), 0) < 0) {
                    perror("Erreur d'envoi de votre nombre");
                }
            }
        }
        else
        {
            break;
        }
        delay = rand() % 10 + 1;
    }
    pthread_exit(NULL);
}


// fonction principal
int main(int argc, char *argv[]) {
    struct sockaddr_in serv_addr;

    if(argc < 3) {
        fprintf(stderr,"Usage ./gameRobot port id_Robot\n");
        exit(1);
    }
    
    // Création du socket
    if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        printf("\n Échec de la création du socket \n");
        return -1;
    }

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(atoi(argv[1]));

    // Convertir l'adresse IPv4 et l'IPv6 de texte en binaire
    if (inet_pton(AF_INET, "127.0.0.1", &serv_addr.sin_addr) <= 0) {
        printf("\nAdresse invalide/Adresse non prise en charge \n");
        return -1;
    }

    // Connexion au serveur
    printf("Tentative de connexion au serveur sur le port %d...\n", atoi(argv[1]));
    if (connect(sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
        printf("\nÉchec de la connexion \n");
        return -1;
    }

    train_model(TRAINING_FILE);
    printf("Modèle entraîné à partir du fichier %s.\n", TRAINING_FILE);

    int game_ON = 0; // Indique si le jeu est en cours
    int end_game = 0; // Indique si la partie est terminée
    pthread_t input_thread; // Thread pour l'entrée utilisateur
    char name[100];
    strcpy(name,argv[2]);

    // Recevoir la demande de nom
    int type;
    if (read(sock, &type, sizeof(type)) <= 0) {
        perror("Erreur de lecture de la demande de nom");
        close(sock);
        return -1;
    }

    if (type == MSG_REQUEST_NAME) {
        // Envoyer le nom du robot au serveur
        if (send(sock, name, strlen(name) + 1, 0) < 0) {
            perror("Erreur d'envoi du nom");
            close(sock);
            return -1;
        }
    }


// Boucle principale du jeu (tant que la partie n'est pas terminer)
    while (!end_game) {
            int finPartie = 0;  // Indicateur pour savoir si la partie est terminée
            if (read(sock, &game_ON, sizeof(game_ON)) <= 0) {
                perror("Erreur de lecture du message de game_ON");
                break;  // Quitte la boucle si une erreur se produit
            }
            if (game_ON){
                //tant que la partie n'est pas terminer
                while(!finPartie)
                {
                    int type;
                    // Lire le type de message avant le switch
                    if (read(sock, &type, sizeof(type)) <= 0) {
                        perror("Erreur de lecture du type de message");
                        break;
                    }

                    switch (type) {
                        case MSG_ROUND_NUMBER: {
                            int round_number;
                            if (read(sock, &round_number, sizeof(round_number)) <= 0) {
                                perror("Erreur de lecture du numéro de round");
                                //break;
                            }
                            rnd = round_number;  // Mise à jour du nombre de rounds
                            tour = 0;            // Réinitialisation du tour actuel
                            k = 0;               // Réinitialisation de l'index des cartes
                            cards = my_cards(rnd); // Allocation des cartes pour le round
                            break;
                        }

                        case MSG_CARD: {
                            int card_value;
                            if (read(sock, &card_value, sizeof(card_value)) <= 0) {
                                perror("Erreur de lecture de la carte");
                                break;
                            }
                            init_cards(cards, rnd, k, card_value); // Ajoute la carte reçue au tableau
                            k++; // Incrémente l'index pour ajouter la prochaine carte
                            break;
                        }

                        case MSG_GAME_STATE: {
                            //fournir des cartes tant que tous les tours ne sont pas joués (il a toujours un carte en sa possesion)
                            if(tour < rnd){
                                if(stop_thread == 1)
                                {
                                    pthread_create(&input_thread, NULL, play_cards, NULL);  // Lance un thread pour gérer l'envoie d'une carte par le robot
                                    stop_thread = 0;
                                }
                            }
                            else {
                                //il n'a plus de carte
                                int no_more_cards = 0; //envoyer au serveur un signal lui disant que le robot n'a plus de carte
                                if (send(sock, &no_more_cards, sizeof(no_more_cards), 0) < 0) {
                                    perror("Erreur d'envoi du signal de fin de cartes");
                                }
                            }

                            break;
                        }

                        case MSG_WIN_STATUS: {
                            stop_thread = 1;   // Stoppe le thread d'entrée utilisateur
                            // Réinitialise les variables
                            tour = 0;
                            k = 0;
                            
                            int envoie = 1; //variable pour savoir si le robot continue ou recommance la partie

                            int win_status;// Récupère le statut de victoire 
                            if (read(sock, &win_status, sizeof(win_status)) <= 0) {
                                perror("Erreur de lecture de l'état de victoire");
                                break;
                            }

                            //si il a perdu le robot envoie un signal au serveur pour dire qu'il veut recommencer (sinon il continue toujours la partie)
                            if (win_status != 1) {
                                envoie = 2;
                            }
                            sleep(1);
                            // Enregistrer les résultats pour chaque carte jouée précédemment
                            save_training_data(rnd, -1, win_status);
                            // Créer un thread pour entraîner le modèle en parallèle
                            pthread_t train_thread;
                            pthread_create(&train_thread, NULL, train_model_thread, NULL);
                            pthread_detach(train_thread); // Détacher le thread pour qu'il s'exécute indépendamment

                            free(cards);

                            if (send(sock, &envoie, sizeof(envoie), 0) < 0) {
                                perror("Erreur d'envoi de votre nombre");
                                break;
                            }

                            break;
                        }

                        case MSG_GAME_STARTED: {
                            break;
                        }

                        case MSG_NORMAL: {
                            char msg[500];
                            if (read(sock, msg, sizeof(msg)) <= 0) {
                                perror("Erreur de lecture du message de jeu NORMAL");
                                break;
                            }
                            break;
                        }

                        case MSG_LOST: {
                            //le robot a jouer une mauvaise carte 
                            stop_thread = 1;

                            //Arrête immédiatement le thread d'entrée utilisateur
                            pthread_cancel(input_thread);
                            pthread_join(input_thread, NULL);  // Assure qu'il soit terminé

                            break;
                        }

                        case MSG_END_GAME: {
                            //Indique la perte du round (un autre joueur a jouer une mauvaise carte).
                            char msg[500];
                            if (read(sock, msg, sizeof(msg)) <= 0) {
                                perror("Erreur de lecture du message de jeu MSG_END_GAME");
                                break;
                            }
                            stop_thread = 1;

                            //Arrête immédiatement le thread d'entrée utilisateur
                            pthread_cancel(input_thread);
                            pthread_join(input_thread, NULL);  // Assure qu'il soit terminé

                            // Envoyer un signal de fin de participation au serveur
                            int numb = -2;
                            if (send(sock, &numb, sizeof(numb), 0) < 0) {
                                perror("Erreur d'envoi du signal de fin de tour");
                            }

                            break;
                        }

                        case MSG_GONE_CL: {
                            //Indique que le joueur a quitté le jeu
                            //Fin de la partie
                            finPartie = 1;
                            end_game = 1;
                            game_ON = 0;
                            char msg[500];
                            if (read(sock, msg, sizeof(msg)) <= 0) {
                                perror("Erreur de lecture du message d'un client partie de jeu");
                                break;
                            }
                            break;
                        }

                        case MSG_ALL_ROUND_PLAYED: {
                            //Indique que tous les rounds ont été joués
                            char msg[500];
                            if (read(sock, msg, sizeof(msg)) <= 0) {
                                perror("Erreur de lecture du message de jeu MSG_END_GAME");
                                break;
                            }
                            //Fin de la partie
                            finPartie = 1;
                            end_game = 1;
                            break;
                        }

                        default: {
                            char msg[500];
                            if (read(sock, msg, sizeof(msg)) <= 0) {
                                perror("Erreur de lecture du message de jeu DEFAULT");
                                break;
                            }
                            break;
                        }
                    }
                }
        }      
    }

    
    close(sock);
    return 0;
}



    

