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
public class ToilettageTest {
    
    private Toilettage instance;
    
    @Before
    public void setUp() {
        instance = new Toilettage();
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of getDescr method, of class Toilettage.
     */
    @Test
    public void testGetDescr() {
        String expResult = Toilettage.TypeDeToilettage.TOILETTAGE_DE_BASE.name();
        String result = instance.getDescr();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPrix method, of class Toilettage.
     */
    @Test
    public void testGetPrix() {
        double expResult = 100.0;
        double result = instance.getPrix();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of setDescr method, of class Toilettage.
     */
    @Test
    public void testSetDescr() {
        System.out.println("setDescr");
        Toilettage.TypeDeToilettage type = Toilettage.TypeDeToilettage.TONNAGE_COMPLET;
        instance.setDescr(type);
        String expResult = Toilettage.TypeDeToilettage.TONNAGE_COMPLET.name();
        assertEquals(expResult, instance.getDescr());
    }

    /**
     * Test of setPrix method, of class Toilettage.
     */
    @Test
    public void testSetPrix() {
        double p = 50.0;
        instance.setPrix(p);
        assertEquals(p, instance.getPrix(), 0.01);
    }

    /**
     * Test of toString method, of class Toilettage.
     */
    @Test
    public void testToString() {
        String expResult = "Toilettage  ---> TOILETTAGE_DE_BASE | Prix : 100.0€";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDuree method, of class Toilettage.
     */
    @Test
    public void testGetDuree() {
        String expResult = null;
        String result = instance.getDuree();
        assertEquals(expResult, result);
    }
    
}
