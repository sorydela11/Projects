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
public class PromenadeTest {
    
    private Promenade instance;
    
    @Before
    public void setUp() {
        instance = new Promenade();
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of getPrix method, of class Promenade.
     */
    @Test
    public void testGetPrix() {
        double expResult = 10.0;
        double result = instance.getPrix();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of getDuree method, of class Promenade.
     */
    @Test
    public void testGetDuree() {
        String expResult = "30 minutes";
        String result = instance.getDuree();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPrix method, of class Promenade.
     */
    @Test
    public void testSetPrix() {
        double p = 15.0;
        instance.setPrix(p);
        assertEquals(p, instance.getPrix(), 0.01);
    }

    /**
     * Test of setDuree method, of class Promenade.
     */
    @Test
    public void testSetDuree() {
        String chaine = "1 heure";
        instance.setDuree(chaine);
        assertEquals(chaine, instance.getDuree());
    }

    /**
     * Test of toString method, of class Promenade.
     */
    @Test
    public void testToString() {
        String expResult = "Promenade  ---> Durée : 30 minutes | Prix : 10.0€";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescr method, of class Promenade.
     */
    @Test
    public void testGetDescr() {
        String expResult = null;
        String result = instance.getDescr();
        assertEquals(expResult, result);
    }
    
}
