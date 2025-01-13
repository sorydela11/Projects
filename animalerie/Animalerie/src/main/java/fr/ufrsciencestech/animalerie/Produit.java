/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.ufrsciencestech.animalerie;
/**
 * Interface représentant un produit. 
 * Cette interface définit les méthodes nécessaires pour obtenir des informations sur un produit, 
 * telles que son nom, sa description, son prix, et son stock disponible.
 * 
 * Toute classe implémentant cette interface devra fournir les détails des méthodes spécifiées.
 */
public interface Produit {

    /**
     * Récupère le nom du produit.
     * 
     * @return Le nom du produit sous forme de chaîne de caractères.
     */
    public String getNom();


    /**
     * Récupère le prix du produit.
     * 
     * @return Le prix du produit (en euros).
     */
    public double getPrix();

    /**
     * Récupère la quantité du produit.
     * 
     * @return Le stock disponible pour ce produit.
     */
    public int getQte();
    
    public void setPrix(double new_price);
    
    public void setQte(int nqte);
    
    /**
     * Fournit une représentation sous forme de chaîne de caractères des informations 
     * concernant le produit (nom, description, prix, stock).
     * 
     * @return Une chaîne contenant les détails du produit.
     */
    @Override
    public String toString();
}

