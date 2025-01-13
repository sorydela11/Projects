/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fr.ufrsciencestech.animalerie.controleur;


import fr.ufrsciencestech.animalerie.*;
import fr.ufrsciencestech.animalerie.view.*;
import fr.ufrsciencestech.animalerie.bd.BaseDeDonnee;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 *
 * @author delaton
 */
public class ControleurAnimalTest {
    
    private ControleurAnimal controleurAnimal;
    private AjoutAnimalView mockAnimalView;
    private View mockView;
    private User mockUser;
    
    
    @Before
    public void setUp() {
        mockAnimalView = mock(AjoutAnimalView.class);
        mockView = mock(View.class);
        mockUser = mock(User.class);

        when(mockUser.getMail()).thenReturn("abcd@gmail.com");

        controleurAnimal = new ControleurAnimal(mockUser, mockView);
        controleurAnimal.setAnView(mockAnimalView);
    }
    
    @After
    public void tearDown() {
        mockView = null;
        mockUser = null;
        mockAnimalView = null;
        controleurAnimal = null;
    }

    /**
     * Test of setVueG method, of class ControleurAnimal.
     */
    @Test
    public void testSetVueG() {
        View newView = mock(View.class);
        controleurAnimal.setVueG(newView);
        assertEquals(newView, controleurAnimal.menU);
    }

    /**
     * Test of setAnView method, of class ControleurAnimal.
     */
    @Test
    public void testSetAnView() {
        AjoutAnimalView newAnimalView = mock(AjoutAnimalView.class);
        controleurAnimal.setAnView(newAnimalView);
        assertEquals(newAnimalView, controleurAnimal.getAnView());
    }

    /**
     * Test of actionPerformed method, of class ControleurAnimal.
     */
    @Test
    public void testActionPerformed_confirmerAnimal() {
        BaseDeDonnee.connexionBD();
        BaseDeDonnee.createUser("", "", mockUser.getMail(), "");
        JTextField nomField = mock(JTextField.class);
        JTextField ageField = mock(JTextField.class);
        JTextField especeField = mock(JTextField.class);
        JTextField poidsField = mock(JTextField.class);

        when(mockAnimalView.getNom()).thenReturn(nomField);
        when(mockAnimalView.getAge()).thenReturn(ageField);
        when(mockAnimalView.getEspece()).thenReturn(especeField);
        when(mockAnimalView.getPoids()).thenReturn(poidsField);

        when(nomField.getText()).thenReturn("Rex");
        when(ageField.getText()).thenReturn("3");
        when(especeField.getText()).thenReturn("Chien");
        when(poidsField.getText()).thenReturn("20.5");

        // Mock de la liste d'animaux dans la vue principale
        JList<String> mockList = mock(JList.class);
        when(mockView.getListAnimaux()).thenReturn(mockList);

        DefaultListModel<String> mockModel = mock(DefaultListModel.class);
        doNothing().when(mockUser).listAnimal(mockModel);

        // Création de l'événement
        // Simuler un événement ActionEvent
        JButton button = new JButton();
        button.setName("Confirmer");
        ActionEvent ae = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null);
        controleurAnimal.actionPerformed(ae);
        
        // Vérification des interactions
        verify(nomField).setText("");
        verify(ageField).setText("");
        verify(especeField).setText("");
        verify(poidsField).setText("");

        // Vérifie que le message d'erreur a été affiché
        verify(mockAnimalView, times(1)).showMessage(
            "Animal ajouté avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE
        );
        verify(mockAnimalView).dispose();
        
        try {
            BaseDeDonnee.deleteUser(mockUser.getMail());
            BaseDeDonnee.c.close();
        } catch (SQLException ex) {
            Logger.getLogger(ControleurProduitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testActionPerformed_annulerAnimal() {
        // Mock des champs de la vue AjoutAnimalView
        JTextField nomField = mock(JTextField.class);
        JTextField ageField = mock(JTextField.class);
        JTextField especeField = mock(JTextField.class);
        JTextField poidsField = mock(JTextField.class);

        when(mockAnimalView.getNom()).thenReturn(nomField);
        when(mockAnimalView.getAge()).thenReturn(ageField);
        when(mockAnimalView.getEspece()).thenReturn(especeField);
        when(mockAnimalView.getPoids()).thenReturn(poidsField);

        // Création de l'événement
        // Simuler un événement ActionEvent
        JButton button = new JButton();
        button.setName("Annuler");
        ActionEvent ae = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null);
        controleurAnimal.actionPerformed(ae);

        // Vérification des interactions
        verify(nomField).setText("");
        verify(ageField).setText("");
        verify(especeField).setText("");
        verify(poidsField).setText("");

        verify(mockAnimalView).dispose();
    }
    
}
