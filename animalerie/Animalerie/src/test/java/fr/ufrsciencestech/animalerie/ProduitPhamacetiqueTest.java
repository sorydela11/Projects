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
public class ProduitPhamacetiqueTest {
    
    private ProduitPhamacetique instance;
    
    @Before
    public void setUp() {
        instance = new ProduitPhamacetique("Amoxiciline", 15.0, 2);
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of getNom method, of class ProduitPhamacetique.
     */
    @Test
    public void testGetNom() {
        String expResult = "Amoxiciline";
        String result = instance.getNom();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPrix method, of class ProduitPhamacetique.
     */
    @Test
    public void testGetPrix() {
        double expResult = 30.0;
        double result = instance.getPrix();
        assertEquals(expResult, result, 0.01);
    }

    /**
     * Test of getQte method, of class ProduitPhamacetique.
     */
    @Test
    public void testGetQte() {
        int expResult = 2;
        int result = instance.getQte();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class ProduitPhamacetique.
     */
    @Test
    public void testToString() {
        String expResult = "Médicamment ---> Nom : Amoxiciline | Prix : 30.0€ | Quantité choisie :2";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPrix method, of class ProduitPhamacetique.
     */
    @Test
    public void testSetPrix() {
        double new_price = 20.0;
        instance.setPrix(new_price);
        assertEquals("Le prix du medicament devrait être modifié à 20.0.", new_price, instance.getPrix(), 0.01);
    }

    /**
     * Test of setQte method, of class ProduitPhamacetique.
     */
    @Test
    public void testSetQte() {
        int nqte = 1;
        instance.setQte(nqte);
        assertEquals("La quantité en stock de l'accessoire devrait être modifiée à 2.", nqte, instance.getQte());
    }
    
}
