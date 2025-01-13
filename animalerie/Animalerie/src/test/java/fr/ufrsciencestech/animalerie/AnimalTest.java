/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fr.ufrsciencestech.animalerie;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author delaton
 */
public class AnimalTest {
    
    private Animal instance;
    
    @Before
    public void setUp() {
        instance = new Animal("Rex", 20.5, 5, "Chien");
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test du setter et getter pour le nom de l'animal.
     */
    
   @Test
    public void testSetAndGetName() {
        String newName = "Max";
        instance.setName(newName);
        assertEquals("Le nom de l'animal devrait être modifié.", newName, instance.getName());
    }

    /**
     * Test du setter et getter pour l'âge de l'animal.
     */
    @Test
    public void testSetAndGetAge() {
        int newAge = 6;
        instance.setAge(newAge);
        assertEquals("L'âge de l'animal devrait être modifié.", newAge, instance.getAge());
    }

    /**
     * Test du setter et getter pour le poids de l'animal.
     */
    @Test
    public void testSetAndGetPoids() {
        double newPoids = 25.0;
        instance.setPoids(newPoids);
        assertEquals("Le poids de l'animal devrait être modifié.", newPoids, instance.getPoids(), 0.01);
    }

    /**
     * Test du setter et getter pour l'espèce de l'animal.
     */
    @Test
    public void testSetAndGetEspece() {
        String newEspece = "Chat";
        instance.setEspece(newEspece);
        assertEquals("L'espèce de l'animal devrait être modifiée.", newEspece, instance.getEspece());
    }

    /**
     * Test de la méthode toString.
     */
    @Test
    public void testToString() {
        String expected = "Nom: Rex, Espèce: Chien, Poids: 20.5 kg, Âge: 5 ans";
        assertEquals("La méthode toString devrait retourner une représentation correcte.", expected, instance.toString());
    }
}
