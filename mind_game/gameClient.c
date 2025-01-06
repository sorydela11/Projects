#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <arpa/inet.h>
#include <pthread.h>

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

int stop_thread = 1; // Variable globale pour indiquer si le thread pour l'entrer utilisateur doit s'arrêter
int sock = 0;        // Descripteur de socket
int tour;            // Compteur de tours pour un round
int* cards;          //les cartes de l'utilisateur
int k = 0;           // Indice utilisé pour ajouter des cartes au tableau
int rnd;             // Round en cours

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

// Affiche toutes les cartes existantes (non nulle)
void show_cards(int *cards, int size) {
    printf("Vos cartes -> ");
    for (int i = 0; i < size; i++) {
        if (cards[i] != 0) {
            printf("%d ", cards[i]);
        }
    }
    printf("\n");
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

// Thread pour gérer les entrées utilisateur pendant la partie
void* user_input(void* arg) {
    pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, NULL);    // Autorise l'annulation
    pthread_setcanceltype(PTHREAD_CANCEL_ASYNCHRONOUS, NULL); // Annulation immédiate

    while (1) {
        if (!stop_thread && tour < rnd) {
            int choice;
            char input[10];
            if (fgets(input, sizeof(input), stdin) != NULL) {
                input[strcspn(input, "\n")] = 0; // Retire le '\n'
                if (input[0] != '\0') {
                    choice = atoi(input);
                    if (stop_thread) {
                        pthread_exit(NULL);
                    }
                    if (remove_cards(cards, choice, rnd)) { //si la carte existe on l'envoie au serveur
                        if (send(sock, &choice, sizeof(choice), 0) < 0) {
                            perror("Erreur d'envoi de votre nombre");
                        }
                        tour++; //incrémentation du compteur de tour
                    } else {
                        printf("Carte inexistante, impossible de la supprimer\n");
                        choice = -1;
                        if (send(sock, &choice, sizeof(choice), 0) < 0) {
                            perror("Erreur d'envoi de votre nombre");
                        }
                    }
                }
            }
        } else {
            break;
        }
        sleep(1); // Pause d'une seconde pour limiter la boucle
    }
    pthread_exit(NULL); // Fin du thread
}


int main(int argc, char *argv[]) {
    struct sockaddr_in serv_addr;

    if(argc < 3) {
        fprintf(stderr,"Usage ./gameClient ip_adress port\n");
        exit(1);
    }
    
    // Création du socket
    if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        printf("\n Échec de la création du socket \n");
        return -1;
    }

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(atoi(argv[2]));

    // Convertir l'adresse IPv4 et l'IPv6 de texte en binaire
    if (inet_pton(AF_INET, argv[1], &serv_addr.sin_addr) <= 0) {
        printf("\nAdresse invalide/Adresse non prise en charge \n");
        return -1;
    }

    // Connexion au serveur
    printf("Tentative de connexion au serveur sur le port %d...\n", atoi(argv[2]));
    if (connect(sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
        printf("\nÉchec de la connexion \n");
        return -1;
    }

    int game_ON = 0; // Indique si le jeu est en cours
    int end_game = 0; // Indique si la partie est terminée
    pthread_t input_thread; // Thread pour l'entrée utilisateur
    char name[100];

    // Recevoir la demande de nom
    int type;
    if (read(sock, &type, sizeof(type)) <= 0) {
        perror("Erreur de lecture de la demande de nom");
        close(sock);
        return -1;
    }

    if (type == MSG_REQUEST_NAME) {
        printf("Veuillez entrer votre nom : ");
        scanf("%s", name);

        // Envoyer le nom au serveur
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

            if (!game_ON) {
                printf("En attente d'autres joueurs, veuillez patienter...\n");  // Message d'attente si la partie n'a pas encore commencer
            } else {
                while(!finPartie) //tant que la partie n'est pas terminer
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
                            printf("\033[34m----------------  ROUND %d --> Distribution of Cards ---------------------\033[0m\n", round_number);
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
                            printf("Carte reçue : %d\n", card_value);
                            init_cards(cards, rnd, k, card_value); // Ajoute la carte reçue au tableau
                            k++; // Incrémente l'index pour ajouter la prochaine carte
                            break;
                        }

                        case MSG_GAME_STATE: {
                            //fournir des cartes tant que tous les tours ne sont pas joués (il a toujours un carte en sa possesion)
                            if(tour < rnd){
                                printf("*. Donnez une carte .*\n"); 
                                show_cards(cards, rnd);// Affiche les cartes actuelles de l'utilisateur
                                sleep(1);
                                if(stop_thread == 1)
                                {
                                    pthread_create(&input_thread, NULL, user_input, NULL);  // Lance un thread pour gérer l'entrée utilisateur
                                    stop_thread = 0;
                                }
                            }
                            else
                            { //il n'a plus de carte
                                int no_more_cards = 0; //envoyer au serveur un signal lui disant que l'utilisateur n'a plus de carte
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
                            free(cards);
                            k = 0;

                            int envoie = 1; //variable pour savoir si l'utilisateur continue, arrete ou recommence la partie

                            int win_status;  // Récupère le statut de victoire
                            if (read(sock, &win_status, sizeof(win_status)) <= 0) {
                                perror("Erreur de lecture de l'état de victoire");
                                break;
                            }

                            if (win_status == 1) { //Si il a gagné, un message de succès est affiché et on passe au prochain round
                                printf("\033[32m************** Round remporté avec succès! On passe au prochain round **************\033[0m\n");
                            } else {//Sinon, l'utilisateur peut choisir de rejouer ou de terminer la partie.
                                printf("\033[31m************************ Round perdu ************************\033[0m\n");
                                while(envoie == 1)
                                {
                                    printf("Voulez vous recommencer la partie (O/n) : ");
                                    char choix[5];
                                    scanf("%s", choix);
                                    if(strcmp(choix, "O") == 0 || strcmp(choix, "o") == 0)
                                    {
                                        printf("\033[32m------------------------ On recommence la partie ------------------------\n\033[0m");
                                        envoie = 2;
                                    }
                                    else if(strcmp(choix, "N") == 0 || strcmp(choix, "n") == 0)
                                    {
                                        printf("\033[31m------------------------ Fin de la partie ------------------------\n\033[0m");
                                        envoie = 0;
                                        //fin du jeu
                                        finPartie = 1;
                                        end_game = 1;
                                    }
                                    else
                                    {
                                        printf("!!!! Entrer une bonne option !!!!\n");
                                    }
                                }
                            }
                            
                            sleep(1);
                            if (send(sock, &envoie, sizeof(envoie), 0) < 0) {
                                perror("Erreur d'envoi de votre nombre");
                                break;
                            }

                            break;
                        }

                        case MSG_GAME_STARTED: {
                            //Affiche un message indiquant que la partie a commencé.
                            printf("|| ------------------------ Partie en cours ------------------------ ||\n");
                            break;
                        }

                        case MSG_NORMAL: {
                            char msg[500];
                            if (read(sock, msg, sizeof(msg)) <= 0) {
                                perror("Erreur de lecture du message de jeu NORMAL");
                                break;
                            }
                            printf("| %s |\n",msg);
                            break;
                        }

                        case MSG_LOST: {
                            //Informe le joueur qu'une carte invalide a été jouée
                            printf("\033[31m !!!!!!!!!! Vous avez joué un numéro invalide !!!!!!!!!!\033[0m\n");
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
                            printf("\033[31m!!! %s !!! \033[0m\n", msg);  // Affiche le message reçu

                            stop_thread = 1;

                            // Annulez le thread pour arrêter immédiatement
                            pthread_cancel(input_thread);
                            pthread_join(input_thread, NULL);  // Assurez-vous qu'il est terminé

                            // Envoyer un signal de fin de participation au serveur
                            int numb = -2;
                            if (send(sock, &numb, sizeof(numb), 0) < 0) {
                                perror("Erreur d'envoi du signal de fin de tour");
                            }

                            break;
                        }

                        case MSG_GONE_CL: {
                            //Indique qu'un joueur a quitté le jeu
                            //Fin de la partie
                            finPartie = 1;
                            end_game = 1;
                            game_ON = 0;
                            char msg[500];
                            if (read(sock, msg, sizeof(msg)) <= 0) {
                                perror("Erreur de lecture du message d'un client partie de jeu");
                                break;
                            }
                            printf("%s\n", msg);  // Affiche le message reçu
                            printf("\033[34m---------------- On revient au menu principal ----------------\033[0m\n");
                            
                            break;
                        }

                        case MSG_ALL_ROUND_PLAYED: {
                            //Indique que tous les rounds ont été joués
                            char msg[500];
                            if (read(sock, msg, sizeof(msg)) <= 0) {
                                perror("Erreur de lecture du message de jeu MSG_END_GAME");
                                break;
                            }
                            printf("\033[32m%s\033[0m\n", msg);  // Affiche le message reçu
                            printf("\033[34m---------------- On revient au menu principal ----------------\033[0m\n");
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
                            printf("%s\n",msg);
                            break;
                        }
                    }
                }
        }      
    }
    
    close(sock);
    /*On revient au menu principal*/
    char runMenu[30];
    strcpy(runMenu,"./mindGame.sh");
    printf("\n\n");
    system(runMenu); //retour au menu principal
    
    return 0;
}



    

