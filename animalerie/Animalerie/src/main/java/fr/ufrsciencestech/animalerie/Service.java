/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.ufrsciencestech.animalerie;

/**
 * Interface représentant un service général. 
 * Les classes qui implémentent cette interface doivent fournir une description du service, 
 * un prix, et une méthode pour représenter le service sous forme de chaîne de caractères.
 * 
 * @author delaton
 */
public interface Service {

    
    public String getDuree();
    
    public String getDescr();
    
    /**
     * Récupère le prix du service.
     * 
     * @return Le prix du service (en euros).
     */
    public double getPrix();

    /**
     * Fournit une représentation sous forme de chaîne de caractères du service.
     * 
     * @return Une chaîne contenant les détails du service, comme la description et le prix.
     */
    @Override
    public String toString();
}

