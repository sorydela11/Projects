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
public class EducationCanineTest {
    
    private EducationCanine instance;
    
    @Before
    public void setUp() {
        instance = new EducationCanine();
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of getPrix method, of class EducationCanine.
     */
    @Test
    public void testGetPrix() {
        double expResult = 50.0;
        double result = instance.getPrix();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of getDuree method, of class EducationCanine.
     */
    @Test
    public void testGetDuree() {
        String expResult = "1 heure";
        String result = instance.getDuree();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPrix method, of class EducationCanine.
     */
    @Test
    public void testSetPrix() {
        double p = 25.0;
        instance.setPrix(p);
        assertEquals(p, instance.getPrix(), 0.01);
    }

    /**
     * Test of setDuree method, of class EducationCanine.
     */
    @Test
    public void testSetDuree() {
        String chaine = "30 minutes";
        instance.setDuree(chaine);
        assertEquals(chaine, instance.getDuree());
    }

    /**
     * Test of toString method, of class EducationCanine.
     */
    @Test
    public void testToString() {
        String expResult = "Éducation Canine ---> Durée : 1 heure | Prix : 50.0€";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescr method, of class EducationCanine.
     */
    @Test
    public void testGetDescr() {
        String expResult = null;
        String result = instance.getDescr();
        assertEquals(expResult, result);
    }
    
}
