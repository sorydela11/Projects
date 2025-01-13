/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie;

/**
 * Classe représentant un service de garde pour animaux.
 * Ce service inclut une description, un prix et une durée.
 * Il implémente l'interface Service pour garantir une compatibilité avec d'autres types de services.
 * 
 * @author delaton
 */
public class Garde implements Service {


    /**
     * Le prix du service de garde (en euros).
     */
    private double prix;

    /**
     * La durée de la garde.
     */
    private String duree;

    /**
     * Constructeur par défaut permettant de créer un service de garde 
     * un prix initial de 20 euros et une durée de 1 heure .
     */
    public Garde() {
        this.prix = 20.0; // Prix par défaut en euros
        this.duree = "1 heure"; // Durée par défaut
    }

    /**
     * Constructeur permettant de créer un service de garde en définissant son prix et sa durée.
     * 
     * @param prix Le prix de la garde (en euros).
     * @param duree La durée de la garde.
     */
    public Garde(double prix, String duree) {
        this.prix = prix;       // Initialisation du prix
        this.duree = duree;     // Initialisation de la durée
    }

    
    /**
     * Récupère le prix du service de garde.
     * 
     * @return Le prix du service de garde (en euros).
     */
    @Override
    public double getPrix() {
        return this.prix;
    }

    /**
     * Récupère la durée du service de garde.
     * 
     * @return La durée de la garde.
     */
    public String getDuree() {
        return this.duree;
    }

    /**
     * Modifie le prix du service de garde.
     * 
     * @param p Le nouveau prix (en euros).
     */
    public void setPrix(double p) {
        this.prix = p;  // Modification du prix
    }

    /**
     * Modifie la durée du service de garde.
     * 
     * @param chaine La nouvelle durée de la garde.
     */
    public void setDuree(String chaine) {
        this.duree = chaine; // Modification de la durée
    }

    /**
     * Fournit une représentation sous forme de chaîne de caractères du service de garde.
     * 
     * @return Une chaîne contenant les détails de la garde (description, prix, durée).
     */
    @Override
    public String toString() {
       return "Garde  ---> Durée : "+this.duree+" | Prix : "+this.getPrix()+"€";
    }

    @Override
    public String getDescr() {
        return null;
    }
}
