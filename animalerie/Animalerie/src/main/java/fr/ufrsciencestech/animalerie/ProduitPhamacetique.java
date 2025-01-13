/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie;

/**
 * Classe représentant un produit pharmaceutique.
 * Cette classe implémente l'interface Produit, fournissant des informations sur 
 * le nom, la description, le prix, et le stock d'un produit pharmaceutique.
 * 
 * Elle est conçue pour gérer les produits spécifiques à la catégorie pharmaceutique.
 */
public class ProduitPhamacetique implements Produit {
    /**
     * Le nom du produit pharmaceutique.
     */
    private String nom;

    /**
     * Le prix du produit pharmaceutique (en euros).
     */
    private double prix;

    /**
     * La quantité en stock du produit pharmaceutique.
     */
    private int qte;
    
    

    /**
     * Constructeur permettant de créer un produit pharmaceutique avec son nom
     * et son prix. Le stock est initialisé à une valeur par défaut à une valeur de 5.
     * 
     * @param n Le nom du produit pharmaceutique.
     * @param prix Le prix du produit pharmaceutique (en euros).
     */
    public ProduitPhamacetique(String n, double prix, int qt) {
        this.nom = n;
        this.prix = prix * qt;
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

    
    
    /**
     * Fournit une représentation sous forme de chaîne de caractères des informations 
     * concernant le produit (nom, description, prix, stock).
     * 
     * @return Une chaîne contenant les détails du produit.
     */

    /**
     * Fournit une représentation sous forme de chaîne de caractères des informations 
     * concernant le produit pharmaceutique, incluant son nom
     * son prix, et sa quantité en stock.
     * 
     * @return Une chaîne contenant les détails du produit pharmaceutique.
     */
    @Override
    public String toString() {
        return "Médicamment ---> Nom : "+this.nom+" | Prix : "+this.prix+"€ | Quantité choisie :" +
                this.qte;
    }

    @Override
    public void setPrix(double new_price) {
        this.prix = new_price;
    }

    @Override
    public void setQte(int nqte) {
        this.qte = nqte;
    }

}

