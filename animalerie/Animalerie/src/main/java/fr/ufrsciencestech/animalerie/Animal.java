/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie;

/**
 * Classe représentant un animal avec des caractéristiques principales telles que 
 * le nom, le poids, l'âge, et l'espèce. Elle fournit des méthodes pour gérer 
 * ces informations.
 * 
 * @author delaton
 */
public class Animal {

    /**
     * Le nom de l'animal.
     */
    private String name;

    /**
     * Le poids de l'animal (en kilogrammes).
     */
    private double poids;

    /**
     * L'âge de l'animal (en années).
     */
    private int age;

    /**
     * L'espèce de l'animal (par exemple, "Chien", "Chat").
     */
    private String espece;

    /**
     * Constructeur permettant de créer un animal avec un nom, un poids et un âge.
     * 
     * @param name Le nom de l'animal.
     * @param p Le poids de l'animal (en kilogrammes).
     * @param age L'âge de l'animal (en années).
     * @param espece L'espèce de l'animal.
     */
    public Animal(String name, double p, int age, String espece) {
        this.name = name;
        this.poids = p;
        this.age = age;
        this.espece = espece;
    
    }

    /**
     * Modifie le nom de l'animal.
     * 
     * @param chaine Le nouveau nom de l'animal.
     */
    public void setName(String chaine) {
        this.name = chaine;
     }

    /**
     * Récupère le nom de l'animal.
     * 
     * @return Le nom de l'animal.
     */
    public String getName() { 
        return this.name;
    }

    /**
     * Modifie l'âge de l'animal.
     * 
     * @param numb Le nouvel âge de l'animal (en années).
     */
    public void setAge(int numb) { 
        this.age = numb;
    }

    /**
     * Récupère l'âge de l'animal.
     * 
     * @return L'âge de l'animal (en années).
     */
    public int getAge() {
        return this.age; 
    }

    /**
     * Modifie le poids de l'animal.
     * 
     * @param p Le nouveau poids de l'animal (en kilogrammes).
     */
    public void setPoids(double p) {
        this.poids = p;
     }

    /**
     * Récupère le poids de l'animal.
     * 
     * @return Le poids de l'animal (en kilogrammes).
     */
    public double getPoids() { 
        return this.poids; 
    }

    /**
     * Modifie l'espèce de l'animal.
     * 
     * @param chaine La nouvelle espèce de l'animal (par exemple, "Chien").
     */
    public void setEspece(String chaine) { 
        this.espece = chaine ;
    
    }

    /**
     * Récupère l'espèce de l'animal.
     * 
     * @return L'espèce de l'animal (par exemple, "Chien").
     */
    public String getEspece() { 
        return this.espece; 
    }

    /**
     * Fournit une représentation sous forme de chaîne de caractères des informations 
     * concernant l'animal (nom, poids, âge, espèce).
     * 
     * @return Une chaîne contenant les détails de l'animal.
     */
    @Override
    public String toString() { 
        return "Nom: " + this.name + ", Espèce: " + this.espece + ", Poids: " + this.poids + " kg, Âge: " + this.age + " ans"; 
    }
}
