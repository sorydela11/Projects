/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fr.ufrsciencestech.animalerie;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author delaton
 */
public class AccessoireTest {
    
    private Accessoire instance;
    
    @Before
    public void setUp() {
        instance = new Accessoire("Jouet", 10.0, 2);
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of getNom method, of class Accessoire.
     */
    @Test
    public void testGetNom() {
        String expResult = "Jouet";
        String result = instance.getNom();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPrix method, of class Accessoire.
     */
    @Test
    public void testGetPrix() {
        double expResult = 20.0;
        double result = instance.getPrix();
        assertEquals("Le prix total de l'accessoire devrait être 20.0 (10.0 * 2).", expResult, result, 0.01);
    }

    /**
     * Test of getQte method, of class Accessoire.
     */
    @Test
    public void testGetQte() {
        int expResult = 2;
        int result = instance.getQte();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Accessoire.
     */
    @Test
    public void testToString() {
        String expected = "Accessoire ---> Nom : Jouet | Prix : 20.0€ | Quantité choisie :2";
        assertEquals("La méthode toString devrait retourner les informations correctes.", expected, instance.toString());
    }

    /**
     * Test of setPrix method, of class Accessoire.
     */
    @Test
    public void testSetPrix() {
        double new_price = 10.0;
        instance.setPrix(new_price);
        assertEquals("Le prix de l'accessoire devrait être modifié à 15.0.", new_price, instance.getPrix(), 0.01);
    }

    /**
     * Test of setQte method, of class Accessoire.
     */
    @Test
    public void testSetQte() {
        int nqte = 5;
        instance.setQte(nqte);
        assertEquals("La quantité en stock de l'accessoire devrait être modifiée à 5.", 5, instance.getQte());
    }
    
}
