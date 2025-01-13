/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie;

/**
 * Classe représentant un accessoire pour animaux.
 * Cette classe implémente l'interface Produit, fournissant des informations sur 
 * le nom, la description, le prix, le stock, et le type d'un accessoire.
 * 
 * 
 * 
 * @author delaton
 */
public class Accessoire implements Produit {

    /**
     * Le nom de l'accessoire.
     */
    private String nom;

    /**
     * Le prix de l'accessoire (en euros).
     */
    private double prix;

    /**
     * La quantité en stock de l'accessoire.
     */
    private int qte;

    /**
     * Constructeur permettant de créer un accessoire avec son nom, 
     * son prix. Le stock est initialisé à une valeur par défaut qui est de 5.
     * 
     * @param n Le nom de l'accessoire.
     * @param prix Le prix de l'accessoire (en euros).
     */
    public Accessoire(String n, double prix, int qt) {
        this.nom = n;
        this.prix = prix * qt ;
        this.qte = qt;
    }

    /**
     * Récupère le nom de l'accessoire.
     * 
     * @return Le nom de l'accessoire.
     */
    @Override
    public String getNom() { return this.nom; }

    

    /**
     * Récupère le prix de l'accessoire.
     * 
     * @return Le prix de l'accessoire (en euros).
     */
    @Override
    public double getPrix() { return this.prix; }

    /**
     * Récupère la quantité en stock de l'accessoire.
     * 
     * @return La quantité disponible en stock pour cet accessoire.
     */
    @Override
    public int getQte() { return this.qte; }

   
     @Override
    public void setPrix(double new_price) {
        this.prix = new_price;
    }

    @Override
    public void setQte(int nqte) {
        this.qte = nqte;
    }
    
    /**
     * Fournit une représentation sous forme de chaîne de caractères des informations 
     * concernant l'accessoire, incluant son nom, sa description, son prix, son type, 
     * et sa quantité en stock.
     * 
     * @return Une chaîne contenant les détails de l'accessoire.
     */
    @Override
    public String toString() { 
        return "Accessoire ---> Nom : "+this.nom+" | Prix : "+this.prix+"€ | Quantité choisie :" +
                this.qte;
    }

}