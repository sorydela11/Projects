/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie;

/**
 * Classe représentant un service de promenade. 
 * Ce service inclut une description, un prix et une durée. 
 * Il implémente l'interface Service pour garantir la compatibilité avec d'autres services.
 * 
 * @author delaton
 */
public class Promenade implements Service {
    
    /**
     * Le prix du service de promenade (en euros).
     */
    private double prix;
    
    /**
     * La durée de la promenade (par exemple, "30 minutes", "1 heure").
     */
    private String duree;
    
    /**
     * Constructeur par défaut permettant de créer une promenade avec comme prix a 10 euro , 
     * d'une durée de 30 minutes 
     */
    public Promenade() {
        this.prix = 10.0; // Prix par défaut en euros
        this.duree = "30 minutes"; // Durée par défaut
    }
    
    /**
     * Constructeur permettant de créer une promenade en définissant son prix et sa durée.
     * 
     * @param prix Le prix de la promenade (en euros).
     * @param duree La durée de la promenade.
     */
    public Promenade(double prix, String duree) {
        this.prix = prix;       // Initialisation du prix
        this.duree = duree;     // Initialisation de la durée
    }
    

    /**
     * Récupère le prix de la promenade.
     * 
     * @return Le prix de la promenade (en euros).
     */
    @Override
    public double getPrix() {
        return this.prix;
    }
    
    /**
     * Récupère la durée de la promenade.
     * 
     * @return La durée de la promenade.
     */
    public String getDuree() {
        return  this.duree;
    }
    
    
    /**
     * Modifie le prix de la promenade.
     * 
     * @param p Le nouveau prix (en euros).
     */
    public void setPrix(double p) {
        this.prix= p ;
    }
    
    /**
     * Modifie la durée de la promenade.
     * 
     * @param chaine La nouvelle durée de la promenade.
     */
    public void setDuree(String chaine) {
        this.duree = chaine ;
    }
    
    /**
     * Fournit une représentation sous forme de chaîne de caractères du service de promenade.
     * 
     * @return Une chaîne contenant les détails de la promenade (description, prix, durée).
     */
    @Override
    public String toString() {
        return "Promenade  ---> Durée : "+this.duree+" | Prix : "+this.getPrix()+"€";
    }

    @Override
    public String getDescr() {
        return null;
    }
}

