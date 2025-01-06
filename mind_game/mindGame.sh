#!/bin/bash

choice=0
nbround=0

select_game_options() {
	echo "--------------------WELCOME TO THE MIND GAME---------------------------"
	echo "---------------------------Main Menu----------------------------------"
	echo ""
	while :
	do
		echo "1 - Coop Mode"
		echo "2 - Offline Mode vs CPU"
		echo "3 - Quit"
		echo "Select one option : "
		read option
		if [ $option -eq 1 ]
		then
			while [ $choice -eq 0 ]
			do
				echo "Select the numbers of players u want"
				echo "a - 2 players"
				echo "b - 3 players"
				echo "c - 4 players"
				read nbplayers
				if [ $nbplayers = "a" ]
				then
					echo "You selected 2 players"
					while [ $nbround -eq 0 ]
					do
						echo "Select the numbers of round u want"
						echo "a - 3 rounds"
						echo "b - 4 rounds"
						echo "c - 5 rounds"
						echo "d - 6 rounds"
						read roundselect
						if [ $roundselect = "a" ]
	                                	then
	                                        	echo "You selected 3 rounds"
	                                        	choice=2
	                                        	port=8080
							nbround=3
						elif [ $roundselect = "b" ]
                                                then
                                                        echo "You selected 4 rounds"
                                                        choice=2
                                                        port=8081
                                                        nbround=4
						elif [ $roundselect = "c" ]
                                                then
                                                        echo "You selected 5 rounds"
                                                        choice=2
                                                        port=8082
                                                        nbround=5
						elif [ $roundselect = "d" ]
                                                then
                                                        echo "You selected 6 rounds"
                                                        choice=2
                                                        port=8083
                                                        nbround=6
						else
                        		                echo "Please pick a correctily option"
			                        fi
					done
				
				elif [ $nbplayers = "b" ]
				then
					echo "You selected 3 players"
	                                while [ $nbround -eq 0 ]
                                        do
                                                echo "Select the numbers of round u want"
                                                echo "a - 3 rounds"
                                                echo "b - 4 rounds"
                                                echo "c - 5 rounds"
                                                echo "d - 6 rounds"
                                                read roundselect
                                                if [ $roundselect = "a" ]
                                                then
                                                        echo "You selected 3 rounds"
                                                        choice=3
                                                        port=8090
                                                        nbround=3
                                                elif [ $roundselect = "b" ]
                                                then
                                                        echo "You selected 4 rounds"
                                                        choice=3
                                                        port=8091
                                                        nbround=4
                                                elif [ $roundselect = "c" ]
                                                then
							echo "You selected 5 rounds"
                                                        choice=3
                                                        port=8092
                                                        nbround=5
                                                elif [ $roundselect = "d" ]
                                                then
                                                        echo "You selected 6 rounds"
                                                        choice=3
                                                        port=8093
                                                        nbround=6
                                                else
                                                        echo "Please pick a correctily option"
                                                fi
                                        done

				elif [ $nbplayers = "c" ]
				then
					echo "You selected 4 players"
	                                while [ $nbround -eq 0 ]
                                        do
                                                echo "Select the numbers of round u want"
                                                echo "a - 3 rounds"
                                                echo "b - 4 rounds"
                                                echo "c - 5 rounds"
                                                echo "d - 6 rounds"
                                                read roundselect
                                                if [ $roundselect = "a" ]
                                                then
                                                        echo "You selected 3 rounds"
                                                        choice=4
                                                        port=9900
                                                        nbround=3
                                                elif [ $roundselect = "b" ]
                                                then
                                                        echo "You selected 4 rounds"
                                                        choice=4
                                                        port=9901
                                                        nbround=4
                                                elif [ $roundselect = "c" ]
                                                then
                                                        echo "You selected 5 rounds"
                                                        choice=4
                                                        port=9902
                                                        nbround=5
                                                elif [ $roundselect = "d" ]
                                                then
                                                        echo "You selected 6 rounds"
                                                        choice=4
                                                        port=9903
                                                        nbround=6
                                                else
                                                        echo "Please pick a correctily option"
                                                fi
                                        done
				else
					echo "Please pick a correctily option"
				fi
			done
			break
		elif [ $option -eq 2 ]
		then 
			while [ $choice -eq 0 ]
	                do
	                        echo "Select the numbers of CPU u want"
	                        echo "a - You vs 1CPU"
	                        echo "b - You vs 2CPU" 
				echo "c - You vs 3CPU"
	                        read nbCpu
	                        if [ $nbCpu = "a" ]
	                        then
	                                echo "You selected 2 players"
					while [ $nbround -eq 0 ]
                                        do
                                                echo "Select the numbers of round u want"
                                                echo "a - 3 rounds"
                                                echo "b - 4 rounds"
                                                echo "c - 5 rounds"
                                                echo "d - 6 rounds"
                                                read roundselect
                                                if [ $roundselect = "a" ]
                                                then
                                                        echo "You selected 3 rounds"
                                                        choice=2
                                                        port=5000
                                                        nbround=3
                                                elif [ $roundselect = "b" ]
                                                then
                                                        echo "You selected 4 rounds"
                                                        choice=2
                                                        port=5001
                                                        nbround=4
                                                elif [ $roundselect = "c" ]
                                                then
                                                        echo "You selected 5 rounds"
                                                        choice=2
                                                        port=5002
                                                        nbround=5
                                                elif [ $roundselect = "d" ]
                                                then
                                                        echo "You selected 6 rounds"
                                                        choice=2
                                                        port=5003
                                                        nbround=6
                                                else
                                                        echo "Please pick a correctily option"
                                                fi
                                        done
	                        elif [ $nbCpu = "b" ]
	                        then 
	                                echo "You selected 3 players"
	                                while [ $nbround -eq 0 ]
                                        do
                                                echo "Select the numbers of round u want"
                                                echo "a - 3 rounds"
                                                echo "b - 4 rounds"
                                                echo "c - 5 rounds"
                                                echo "d - 6 rounds"
                                                read roundselect
                                                if [ $roundselect = "a" ]
                                                then
                                                        echo "You selected 3 rounds"
                                                        choice=3
                                                        port=5100
                                                        nbround=3
                                                elif [ $roundselect = "b" ]
                                                then
                                                        echo "You selected 4 rounds"
                                                        choice=4
                                                        port=5101
                                                        nbround=4
                                                elif [ $roundselect = "c" ]
                                                then
                                                        echo "You selected 5 rounds"
                                                        choice=3
                                                        port=5102
                                                        nbround=5
                                                elif [ $roundselect = "d" ]
                                                then
                                                        echo "You selected 6 rounds"
                                                        choice=3
                                                        port=5103
                                                        nbround=6
                                                else
                                                        echo "Please pick a correctily option"
                                                fi
                                        done
	                        elif [ $nbCpu = "c" ]
	                        then 
	                                echo "You selected 4 players"
	                                while [ $nbround -eq 0 ]
                                        do
                                                echo "Select the numbers of round u want"
                                                echo "a - 3 rounds"
                                                echo "b - 4 rounds"
                                                echo "c - 5 rounds"
                                                echo "d - 6 rounds"
                                                read roundselect
                                                if [ $roundselect = "a" ]
                                                then
                                                        echo "You selected 3 rounds"
                                                        choice=4
                                                        port=5110
                                                        nbround=3
                                                elif [ $roundselect = "b" ]
                                                then
                                                        echo "You selected 4 rounds"
                                                        choice=4
                                                        port=5111
                                                        nbround=4
                                                elif [ $roundselect = "c" ]
                                                then
                                                        echo "You selected 5 rounds"
                                                        choice=4
                                                        port=5112
                                                        nbround=5
                                                elif [ $roundselect = "d" ]
                                                then
                                                        echo "You selected 6 rounds"
                                                        choice=4
                                                        port=5113
                                                        nbround=6
                                                else
                                                        echo "Please pick a correctily option"
                                                fi
                                        done
	                        else 
	                                echo "Please pick a correctily option"
	                        fi 
	                done 

			break;
		elif [ $option -eq 3  ]
		then 
			exit 1
		else
			echo "Select a correct option please!!!"
		fi
	done
}

select_game_options

if [ $option -eq 1 ]; then
    echo "Donnez l'adresse ip du serveur"
    read ip_address
else
    # Compiler le serveur
    gcc -o gameServer gameServer.c
    # Lancer le serveur en arrière-plan
    ./gameServer $port $choice $nbround &> /dev/null &
    # Attendre que le serveur est le temps de démarrer avant de lancer le client
    sleep 1  # Attendre que le serveur démarre

    ip_address="127.0.0.1"  # Définir l'IP par défaut
    # Compiler le client robot
    gcc -o gameRobot gameRobot.c 

    # Incrémenter les robots
    id=1
    runCpu=$((choice - 1))  # Nombre de robots à exécuter

    # Lancer les robots
    while [ $id -le $runCpu ]; do
        ./gameRobot $port Cpu$id &> /dev/null &
        sleep 1
	id=$((id + 1)) # Incrémentation
    done
fi
clear
    
# Compiler le client
gcc -o gameClient gameClient.c
# Lancer le client après avoir attendu que le serveur ait démarré
./gameClient $ip_address $port
    

