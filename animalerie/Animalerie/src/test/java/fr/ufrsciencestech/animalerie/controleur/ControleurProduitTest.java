/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fr.ufrsciencestech.animalerie.controleur;

import fr.ufrsciencestech.animalerie.*;
import fr.ufrsciencestech.animalerie.bd.BaseDeDonnee;
import fr.ufrsciencestech.animalerie.view.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author delaton
 */
public class ControleurProduitTest {
    
    private ControleurProduit controleurProduit;
    private ProduitView mockProduitView;
    private View mockView;
    private User mockUser;

    @Before
    public void setUp() {
        mockProduitView = mock(ProduitView.class);
        mockView = mock(View.class);
        mockUser = mock(User.class);

        mockUser.setMail("abcd@gmail.com");
        
        
        controleurProduit = new ControleurProduit(mockUser, mockView);
        controleurProduit.setProduitView(mockProduitView);
    }
    @After
    public void tearDown() {
        controleurProduit = null;
        mockProduitView = null;
        mockView = null;
        mockUser = null;
    }

    @Test
    public void testSetVueG() {
        View newView = mock(View.class);
        controleurProduit.setVueG(newView);
        assertEquals(newView, controleurProduit.menU);
    }

    @Test
    public void testSetProduitView() {
        ProduitView newProduitView = mock(ProduitView.class);
        controleurProduit.setProduitView(newProduitView);
        assertEquals(newProduitView, controleurProduit.getProduitView());
    }

    @Test
    public void testActionPerformed_ProduitDisponible() {
        BaseDeDonnee.connexionBD();
        BaseDeDonnee.executeSQLFile();
        BaseDeDonnee.createUser("", "", mockUser.getMail(), "");
        
        // Créer des mocks pour les composants de ProduitView
        JRadioButton mockBtAccess = mock(JRadioButton.class);
        JComboBox<String> mockjAccess = mock(JComboBox.class);
        JComboBox<String> mockjQuantite = mock(JComboBox.class);

        // Simulation des valeurs pour le test
        when(mockBtAccess.isSelected()).thenReturn(true);
        when(mockjAccess.getSelectedItem()).thenReturn("Coussin/Lit");
        when(mockjQuantite.getSelectedItem()).thenReturn("2");
        
        // Configurer les méthodes de ProduitView pour retourner les mocks
        when(mockProduitView.getBtAccess()).thenReturn(mockBtAccess);
        when(mockProduitView.getjAccess()).thenReturn(mockjAccess);
        when(mockProduitView.getJQuantite()).thenReturn(mockjQuantite);
        
        when(mockView.getListCommande()).thenReturn(new JList());
        
        JButton button = new JButton();
        button.setName("btConfirmer");
        ActionEvent ae = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null);

        // Exécution de l'action
        controleurProduit.actionPerformed(ae);

        // Vérifications
        verify(mockProduitView, times(1)).showMessage(
            "Produit ajouté : Coussin/Lit (2)", 
            "Succès", 
            JOptionPane.INFORMATION_MESSAGE
        );
        verify(mockProduitView).dispose();
        
        try {
            BaseDeDonnee.deleteUser(mockUser.getMail());
            BaseDeDonnee.c.close();
        } catch (SQLException ex) {
            Logger.getLogger(ControleurProduitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testActionPerformed_ProduitIndisponible() {
        BaseDeDonnee.connexionBD();
        BaseDeDonnee.createUser("", "", mockUser.getMail(), "");
        
        // Créer des mocks pour les composants de ProduitView
        JRadioButton mockBtAccess = mock(JRadioButton.class);
        JComboBox<String> mockjAccess = mock(JComboBox.class);
        JComboBox<String> mockjQuantite = mock(JComboBox.class);

        // Simulation des valeurs pour le test
        when(mockBtAccess.isSelected()).thenReturn(true);
        when(mockjAccess.getSelectedItem()).thenReturn("Coussin/Lit");
        when(mockjQuantite.getSelectedItem()).thenReturn("12");
        
        // Configurer les méthodes de ProduitView pour retourner les mocks
        when(mockProduitView.getBtAccess()).thenReturn(mockBtAccess);
        when(mockProduitView.getjAccess()).thenReturn(mockjAccess);
        when(mockProduitView.getJQuantite()).thenReturn(mockjQuantite);
        
        JButton button = new JButton();
        button.setName("btConfirmer");
        ActionEvent ae = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null);

        controleurProduit.actionPerformed(ae);

        // Vérifie que le message d'erreur a été affiché
        verify(mockProduitView, times(1)).showMessage(
            "Produit Coussin/Lit Quantité -> (12) indisponible en stock",
            "Échec",
            JOptionPane.INFORMATION_MESSAGE
        );
        verify(mockProduitView, never()).dispose();
        
        try {
            BaseDeDonnee.deleteUser(mockUser.getMail());
            BaseDeDonnee.c.close();
        } catch (SQLException ex) {
            Logger.getLogger(ControleurProduitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testActionPerformed_Annuler() {
        JButton button = new JButton();
        button.setName("btAnnuler");
        ActionEvent ae = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null);

        controleurProduit.actionPerformed(ae);

        verify(mockProduitView, times(1)).dispose();
    }

}
