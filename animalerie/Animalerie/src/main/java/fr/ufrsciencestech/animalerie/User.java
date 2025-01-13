package fr.ufrsciencestech.animalerie;

import java.util.ArrayList;
import javax.swing.DefaultListModel;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author delaton
 * Classe représentant un utilisateur avec des informations personnelles telles que le prénom, le nom, 
 * l'adresse mail
 * La classe permet de définir ces informations, de les modifier, et de les afficher.
 * 
 */
public class User {

    /**
     * Le prénom de l'utilisateur.
     */
    private String firstName;

    /**
     * Le nom de famille de l'utilisateur.
     */
    private String lastName;

    /**
     * L'adresse mail de l'utilisateur.
     */
    private String mail;
    
    
    /**
     * La liste de ses animaux.
     */
    private ArrayList<Animal> mesAnimaux;
    
    /**
     * La liste de ses reservations.
     */
    private ArrayList<Reservation> mesReservations;
    
    /**
     * La liste de ses commandes.
     */
    private ArrayList<Commande> mesCommandes;
    
    // Liste statique partagée contenant tous les utilisateurs
    private static ArrayList<User> users = new ArrayList<>();
    

    /**
     * Constructeur permettant de créer un utilisateur avec ses informations personnelles.
     * 
     * @param ftName Le prénom de l'utilisateur.
     * @param ltName Le nom de famille de l'utilisateur.
     * @param mail L'adresse mail de l'utilisateur.
     */
    public User(String ftName, String ltName, String mail) {
        this.firstName = ftName;
        this.lastName = ltName;
        this.mail = mail;
        this.mesAnimaux = new ArrayList<Animal>();
        this.mesReservations = new ArrayList<Reservation>();
        this.mesCommandes = new ArrayList<Commande>();
    
    }
    
    /**
     * Constructeur par copie.
     * @param c l'utilisateur existant;
     */
    public User(User c) {
        this.firstName = c.firstName;
        this.lastName = c.lastName;
        this.mail = c.mail;
        this.mesAnimaux = new ArrayList<>(c.mesAnimaux);
        this.mesReservations = new ArrayList<>(c.mesReservations);
        this.mesCommandes = new ArrayList<>(c.mesCommandes);
    }
    

    /**
     * Modifie le prénom de l'utilisateur.
     * 
     * @param chaine Le nouveau prénom de l'utilisateur.
     */
    public void setFirstName(String chaine) {
        this.firstName = chaine;
    }

    /**
     * Récupère le prénom de l'utilisateur.
     * 
     * @return Le prénom de l'utilisateur.
     */
    public String getFirstName() {
        return this.firstName;
    }
    

    
    
    /**
     * Modifie le nom de famille de l'utilisateur.
     * 
     * @param chaine Le nouveau nom de famille.
     */
    public void setLastName(String chaine) {
        this.lastName = chaine;
    }

    /**
     * Récupère le nom de famille de l'utilisateur.
     * 
     * @return Le nom de famille de l'utilisateur.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Modifie l'adresse mail de l'utilisateur.
     * 
     * @param chaine La nouvelle adresse mail.
     */
    public void setMail(String chaine) {
        this.mail = chaine;
    }

    /**
     * Récupère l'adresse mail de l'utilisateur.
     * 
     * @return L'adresse mail de l'utilisateur.
     */
    public String getMail() {
        return this.mail;
    }

    /**
     * Retourne le nombre d'animaux enregistrés pour cet utilisateur.
     *
     * @return le nombre total d'animaux sous forme d'un entier.
     */
    public int getNbAnimaux()
    {
        return this.mesAnimaux.size();
    }
    
     /**
     * Ajoute un animal à la liste des animaux.
     *
     * @param an l'animal à ajouter, représenté par une instance de la classe Animal.
     */
    public void addAnimal(Animal an)
    {
        this.mesAnimaux.add(an);
    }
    
    /**
    * Remplit le modèle fourni avec les informations de tous les animaux associés à l'utilisateur.
    * 
    * @param modelAnimal un modèle de liste dans lequel les informations des animaux seront ajoutées.
    *                         Chaque entrée représente un animal avec des détails tels que le nom, l'espèce, etc.
    */
    public void listAnimal(DefaultListModel<String> modelAnimal) 
    {
        for (Animal an : mesAnimaux) {
            modelAnimal.addElement(an.toString());
        }
    }
    
    /**
     * Retourne le nombre de reservations enregistrés pour cet utilisateur.
     *
     * @return le nombre total de reservation sous forme d'un entier.
     */
    public int getNbReservations()
    {
        return this.mesReservations.size();
    }
    
    
    public Reservation getReservation(int i)
    {
        if(i >= 0 && i < this.getNbReservations())
        {
            return this.mesReservations.get(i);
        }
        
        return null;
    }
    
    
    
     /**
     * Ajoute une reservation à la liste des reservations dont la date de reservation diffère
     * de celle qui sont déja dans la liste.
     * Sinon ,ajout les services reserver dans la nouvelle reservation dans la reservation
     * qui existe déja à la même date.
     *
     * @param r la reservation à ajouter, représenté par une instance de la classe Reservation.
     */
    public void addReservation(Reservation r)
    {
        if(this.getNbReservations() == 0)
        {
            this.mesReservations.add(r);
        }
        else
        {
            boolean bol = false;
            for(int i=0; i<this.getNbReservations() ; i++)
            {
                if(r.getDate().equals(this.mesReservations.get(i).getDate()) && !this.mesReservations.get(i).estConfirmee())
                {
                    this.mesReservations.get(i).ajoutAllService(r);
                    bol = true;
                }
            }
            if(!bol)
            {
                this.mesReservations.add(r);    
            }
        }
    }
    
    public void removeReservation(Reservation r)
    {
        for(int i=0; i<this.getNbReservations(); i++)
        {
            if(this.mesReservations.get(i).equals(r))
            {
                this.mesReservations.remove(this.mesReservations.get(i));
            }
        }
    }
    
    public void removeAllReservation()
    {
        this.mesReservations.clear();
    }
    
    
    /**
    * Remplit le modèle fourni avec les informations de toutes les réservations en cours de l'utilisateur.
    * 
    * @param modelReservation un modèle de liste dans lequel les informations des réservations en cours 
    *                         seront ajoutées. Chaque entrée représente une réservation en cours.
    */
    public void listReservationEncours(DefaultListModel<String> modelReservation)
    {
        for(int i=0; i<this.getNbReservations(); i++)
        {
            Reservation r = this.mesReservations.get(i);
            if(!r.estConfirmee())
            {
                modelReservation.addElement("Reservation n°"+(i+1)+" -> "+r.toString());
            }
        }
    }

   /**
    * Remplit le modèle fourni avec les informations de toutes les réservations validées par l'utilisateur.
    * 
    * @param modelReservation un modèle de liste dans lequel les informations des réservations validées 
    *                         seront ajoutées. Chaque entrée représente une réservation validée.
    */
    public void listReservationValider(DefaultListModel<String> modelReservation)
    {
        for(int i=0; i<this.getNbReservations(); i++)
        {
            Reservation r = this.mesReservations.get(i);
            if(r.estConfirmee())
            {
                modelReservation.addElement("Reservation n°"+(i+1)+" -> "+r.toString());
            }
        }
    }
    
    /**
     * Retourne le nombre de commandes enregistrés pour cet utilisateur.
     *
     * @return le nombre total de commandes sous forme d'un entier.
     */
    public int getNbCommandes()
    {
        return this.mesCommandes.size();
    }
    
    public Commande getCommande(int i)
    {
        if(i >= 0 && i < this.getNbCommandes())
        {
            return this.mesCommandes.get(i);
        }
        
        return null;
    }
    
    /**
     * Ajoute une commande à la liste des commandes.
     *
     * @param c la commande à ajouter, représenté par une instance de la classe Commande.
     */
    public void addCommande(Commande c)
    {
        if(this.getNbCommandes() == 0)
        {
            this.mesCommandes.add(c);
        }
        else
        {
            boolean bol = false;
            int i = 0;
            while(i < this.getNbCommandes() && !bol)
            {
                Commande exc = this.mesCommandes.get(i);
                if(!exc.estConfirmee())
                {
                    this.mesCommandes.get(i).ajoutAllproduits(c);
                    bol = true;
                }
                i++;
            }
            
            if(!bol)
            {
                this.mesCommandes.add(c);
            }
        }
    }
    
    
    public void removeCommande(Commande c)
    {
        for(int i=0; i<this.getNbCommandes(); i++)
        {
            if(this.mesCommandes.get(i).equals(c))
            {
                this.mesCommandes.remove(this.mesCommandes.get(i));
            }
        }
    }
    
    public void removeAllCommande()
    {
        this.mesCommandes.clear();
    }
    
    /**
    * Remplit le modèle fourni avec les informations de toutes les commandes en cours de l'utilisateur.
    * 
    * @param modelCommande un modèle de liste dans lequel les informations des commandes en cours 
    *                         seront ajoutées. Chaque entrée représente une commande en cours.
    */
    public void listCommandeEncours(DefaultListModel<String> modelCommande)
    {
        for(int i=0; i<this.getNbCommandes(); i++)
        {
            Commande r = this.mesCommandes.get(i);
            if(!r.estConfirmee())
            {
                modelCommande.addElement("Commande n°"+(i+1)+" -> "+r.toString());
            }
        }
    }

   /**
    * Remplit le modèle fourni avec les informations de toutes les commandes validées par l'utilisateur.
    * 
    * @param modelCommande un modèle de liste dans lequel les informations des commandes validées 
    *                         seront ajoutées. Chaque entrée représente une commande validée.
    */
    public void listCommandeValider(DefaultListModel<String> modelCommande)
    {
        for(int i=0; i<this.getNbCommandes(); i++)
        {
            Commande r = this.mesCommandes.get(i);
            if(r.estConfirmee())
            {
                modelCommande.addElement("Commande n°"+(i+1)+" -> "+r.toString());
            }
        }
    }
    
    
  
    /**
     * Fournit une représentation sous forme de chaîne de caractères des informations 
     * de l'utilisateur.
     * 
     * @return Une chaîne contenant les informations de l'utilisateur.
     */
    @Override
    public String toString() {
        return "Utilisateur : " + firstName + " " + lastName + ", Mail : " + mail;
    
    }
}

