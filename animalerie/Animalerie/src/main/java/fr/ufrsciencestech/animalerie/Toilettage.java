/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie;

/**
 * Classe représentant un service de toilettage. 
 * Cette classe implémente l'interface Service et fournit un type et un prix pour le service.
 * 
 * @author delaton
 */
public class Toilettage implements Service {

    /**
     * Le type  de toilettage.
     */
    public enum TypeDeToilettage{
        TOILETTAGE_DE_BASE,  // "Toilettage professionnel comprenant coupe, bain, soin des ongles, rasage des coussinets pour un animal propre et soigné."
        DOUCHE,   // "bain prfessionel"
        COUPE_ET_SOINS_DES_ONGLES, // "coupe professionel" 
        RASAGE_DES_COUSSINET_DE_PATTES,//
        TONNAGE_COMPLET
    }

  
    
    private TypeDeToilettage type ;


    /**
     * Le prix du service de toilettage (en euros).
     */
    private double prix;
    
    /**
     * Constructeur par défaut permettant de créer un service de toilettage complet 
     * dont le prix est de 100 euro pour la totale.
     */
    public Toilettage() {
        this.type = TypeDeToilettage.TOILETTAGE_DE_BASE;//"Toilettage professionnel comprenant coupe, bain, soin des ongles, rasage des coussinets pour un animal propre et soigné."
        this.prix = 100; // Prix par défaut en euros
    }
    
    /**
     * Constructeur permettant de créer un service de toilettage avec un prix initial.
     * 
     * @param type pour le type de toilettage choisis
     * @param prix Le prix du service (en euros).
     */
    public Toilettage(double prix, TypeDeToilettage type) {
        this.type = type;
        this.prix = prix;
    }
    
    /**
     * Récupère le type du service de toilettage.
     * 
     * @return le type du service de toilettage.
     */
    @Override
    public String getDescr() {
        return this.type.name();
    }

     /**
     * Récupère le prix du service de toilettage.
     * 
     * @return Le prix du service (en euros).
     */
    @Override
    public double getPrix() {
        return this.prix;
    }
    
    /**
     * Modifie le type du service de toilettage.
     * 
     * @param type.
     */
    public void setDescr(TypeDeToilettage type) {
        this.type = type;  // Modification du type de toilettage
    }
    
    /**
     * Modifie le prix du service de toilettage.
     * 
     * @param p Le nouveau prix (en euros).
     */
    public void setPrix(double p) {
        this.prix = p;  // Modification du prix
    }

    
    /**
     * Fournit une représentation sous forme de chaîne de caractères du service de toilettage.
     * 
     * @return Une chaîne décrivant le service (description et prix).
     */
    @Override
    public String toString() {
        return "Toilettage  ---> "+this.type+" | Prix : "+this.getPrix()+"€";
    }

    @Override
    public String getDuree() {
        return null;
    }
}
