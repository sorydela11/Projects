/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie;

import java.util.ArrayList;
import java.time.*;

/**
 * Classe représentant une réservation pour un client.
 * Cette classe contient des informations sur la date de la réservation, 
 * le statut de la réservation, l'utilisateur qui effectue la réservation, 
 * et les services associés à cette réservation.
 * 
 * Le statut de la réservation peut être par exemple "En attente", "Confirmée".
 * 
 * @author delaton
 */
public class Reservation {

    /**
     * Enumération représentant les différents statuts possibles pour une réservation.
     */
    public enum StatutReservation {
        EN_ATTENTE,  // Réservation en attente de confirmation
        CONFIRMÉE,   // Réservation confirmée
    }

    /**
     * La date de la réservation.
     */
    private LocalDate dateR;

    /**
     * Le statut de la réservation (EN_ATTENTE, CONFIRMEE, ANNULEE).
     */
    private StatutReservation statut;



    /**
     * La liste des services réservés dans le cadre de cette réservation.
     */
    private ArrayList<Service> services;

    /**
     * Constructeur permettant de créer une réservation pour un utilisateur.
     * Le statut est initialisé à EN_ATTENTE par défaut.
     * 
     * @param date pour la date de la reservation
     */
    public Reservation(LocalDate date) {
        this.dateR = date;
        this.statut = StatutReservation.EN_ATTENTE; // Statut initial est "EN_ATTENTE"
        this.services = new ArrayList<Service>(); // Initialisation de la liste de services
        
    }

    
    /**
     * Récupère la date de la reservation
     * @return la date
     */
    
    public LocalDate getDate()
    {
        return this.dateR;
    }
    
    /**
     * Récupère le statut de la reservation.
     * 
     * @return Le type du statut
     */
    public StatutReservation getStatut() { return this.statut; }
    
    public void setStatut(String s)
    {
        this.statut = StatutReservation.valueOf(s);
    }
    /**
     * Récupère le nombre de services réservés dans cette réservation.
     * 
     * @return Le nombre de services.
     */
    public int getNbServicesReserver() {
        return this.services.size();
    }
    
    /**
    * Récupère la liste des services réservés pour cette réservation.
    * Cette méthode retourne une **ArrayList** contenant tous les services associés à la réservation.
    * 
    * @return Une **ArrayList** de services réservés. Si aucun service n'a été ajouté à la réservation,
    *         une liste vide est retournée.
    */
    public ArrayList<Service> getServices() {
        return this.services;
    }
    
    /**
     * Récupere le service à un indice donner
     * @param i l'indice du service que l'on veut
     * @return le service
     */
    public Service getService(int i)
    {
        if (i >= 0 && i < this.services.size()) {
            return this.services.get(i);
        }
        return null;
    }

    /**
     * Ajoute un service à la réservation.
     * 
     * @param s Le service à ajouter à la réservation.
     */
    public void ajoutService(Service s) {
        if (s != null) {
            this.services.add(s);
        }

    }
    
    /**
     * Ajout tout les services d'une autre réservation à la réservation.
     * @param r la reservation dont contient les services que l'on veut ajouter
     */
    
    public void ajoutAllService(Reservation r)
    {
        if (r != null) {
            this.services.addAll(r.services);
        }
    }

    /**
     * Retire un service de la réservation en fonction de l'indice.
     * 
     * @param i L'indice du service à retirer.
     */
    public void retirerService(int i) {
        if (i >= 0 && i < this.services.size()) {
            this.services.remove(i);
        }
        
    }
    
    /**
    * Vérifie si la réservation a été confirmée.
    * Cette méthode retourne un booléen indiquant si la réservation a été confirmée ou non,
    * en vérifiant le statut de la réservation.
    * 
    * @return true si la réservation est confirmée, false sinon.
    */
   public boolean estConfirmee() {
        return this.statut == StatutReservation.CONFIRMÉE;
   }

   /**
    * Calcule le coût total de la réservation.
    * Cette méthode calcule le coût total en fonction des services réservés, en ajoutant
    * le prix de chaque service dans la réservation.
    * 
    * @return Le coût total de la réservation.
    */
    public double calculerCoutTotal() {
        double mt = 0.0;
        for(int i=0; i<this.getNbServicesReserver(); i++)
        {
            mt += this.getService(i).getPrix();
        }
        return mt;
    }

    
    /**
     * Confirme la réservation en mettant à jour le statut à CONFIRMEE.
     */
    public void confirmerReservation() {
        this.statut = StatutReservation.CONFIRMÉE;
    }

    
    
    /**
     * Fournit une représentation sous forme de chaîne de caractères des informations 
     * concernant la réservation, le statut, 
     * la date et la liste des services réservés.
     * 
     * @return Une chaîne contenant les détails de la réservation.
     */
    @Override
    public String toString() {
        String res = "";
        res += "Date de reservation : "+this.dateR.toString()+" | Statut : "+this.statut+
                "\nServices : \n";
        for(int i=0; i<this.getNbServicesReserver(); i++)
        {
            res += this.services.get(i).toString() + "\n";
        }
        res += "Prix total : "+this.calculerCoutTotal();
        return res;
    }
}

