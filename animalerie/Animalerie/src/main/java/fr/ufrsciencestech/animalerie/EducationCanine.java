/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie;

/**
 *
 * @author delaton
 */
/**
 * Classe représentant un service d'éducation canine.
 * Ce service inclut une description, un prix et une durée.
 * Il implémente l'interface Service pour garantir la compatibilité avec d'autres services.
 * 
 * @author delaton
 */
public class EducationCanine implements Service {
    
    
    /**
     * Le prix du service d'éducation canine (en euros).
     */
    private double prix;
    
    /**
     * La durée de la session d'éducation canine
     */
    private String duree;
    
    /**
     * Constructeur par défaut permettant de créer un service d'éducation canine 
     * avec un prix par défaut de 50 euros pour une durée de 1 heure.
     */
    public EducationCanine() {
        this.prix = 50.0; // Prix par défaut en euros
        this.duree = "1 heure"; // Durée par défaut
    }
    
    /**
     * Constructeur permettant de créer un service d'éducation canine avec un prix et une durée.
     * 
     * @param prix Le prix du service (en euros).
     * @param duree La durée de la session d'éducation canine.
     */
    public EducationCanine(double prix, String duree) {
        this.prix = prix;       // Initialisation du prix
        this.duree = duree;     // Initialisation de la durée
    }
    
    

    /**
     * Récupère le prix du service d'éducation canine.
     * 
     * @return Le prix du service (en euros).
     */
    @Override
    public double getPrix() {
        return this.prix;
    }
    
    /**
     * Récupère la durée du service d'éducation canine.
     * 
     * @return La durée de la session d'éducation canine.
     */
    public String getDuree() {
        return this.duree;
    }
    
    
    /**
     * Modifie le prix du service d'éducation canine.
     * 
     * @param p Le nouveau prix (en euros).
     */
    public void setPrix(double p) {
        this.prix = p;  // Modification du prix
    }
    
    /**
     * Modifie la durée du service d'éducation canine.
     * 
     * @param chaine La nouvelle durée de la session.
     */
    public void setDuree(String chaine) {
        this.duree = chaine; // Modification de la durée
    }
    
    /**
     * Fournit une représentation sous forme de chaîne de caractères du service d'éducation canine.
     * 
     * @return Une chaîne contenant les détails du service (description, prix, durée).
     */
    @Override
    public String toString() {
        return "Éducation Canine ---> Durée : "+this.duree+" | Prix : "+this.getPrix()+"€";
    }

    @Override
    public String getDescr() {
        return null;
    }
}
