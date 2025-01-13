package fr.ufrsciencestech.animalerie.controleur;

import fr.ufrsciencestech.animalerie.User;
import fr.ufrsciencestech.animalerie.bd.BaseDeDonnee;
import fr.ufrsciencestech.animalerie.view.UserConnect;
import fr.ufrsciencestech.animalerie.view.UserInscription;
import fr.ufrsciencestech.animalerie.view.View;
import org.junit.*;
import static org.mockito.Mockito.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControleurConnexionTest {

    private ControleurConnexion controleurConnexion;
    private UserConnect mockConnectView;
    private UserInscription mockInscriptionView;
    private User mockUser;
    private View mockView;

    @Before
    public void setUp() {
        mockConnectView = mock(UserConnect.class);
        mockInscriptionView = mock(UserInscription.class);
        mockUser = mock(User.class);
        mockView = mock(View.class);

        controleurConnexion = new ControleurConnexion(mockConnectView, mockInscriptionView);
        controleurConnexion.menU = mockView;
    }

    @After
    public void tearDown() {
        mockConnectView = null;
        mockInscriptionView = null;
        mockUser = null;
        mockView = null;
        controleurConnexion = null;
    }

    @Test
    public void testActionPerformed_connexionValidUser() {
        BaseDeDonnee.connexionBD();
        BaseDeDonnee.createUser("user", "user", "user@gmail.com", "password123");
        // Mocks pour la vue de connexion
        JTextField mockMailField = mock(JTextField.class);
        JTextField mockPassField = mock(JTextField.class);

        when(mockConnectView.jMail()).thenReturn(mockMailField);
        when(mockConnectView.JPass()).thenReturn(mockPassField);

        when(mockMailField.getText()).thenReturn("user@gmail.com");
        when(mockPassField.getText()).thenReturn("password123");
        
        // Empêcher toute action réelle du menu
        doNothing().when(mockView).setVisible(true);
        controleurConnexion.menU = mockView;

        // Création d'un événement de clic sur "Connexion"
        JButton button = new JButton();
        button.setName("Connexion");
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "Connexion");

        controleurConnexion.actionPerformed(event);

        verify(mockConnectView, times(1)).showMessage(
            "Connexion en cours", "Succès", JOptionPane.INFORMATION_MESSAGE
        );
        
        verify(mockView, never()).setVisible(true);
        
        try {
            BaseDeDonnee.deleteUser("user@gmail.com");
            BaseDeDonnee.c.close();
        } catch (SQLException ex) {
            Logger.getLogger(ControleurProduitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testActionPerformed_connexionInvalidUser() {
        BaseDeDonnee.connexionBD();
        // Mocks pour la vue de connexion
        JTextField mockMailField = mock(JTextField.class);
        JTextField mockPassField = mock(JTextField.class);

        when(mockConnectView.jMail()).thenReturn(mockMailField);
        when(mockConnectView.JPass()).thenReturn(mockPassField);

        when(mockMailField.getText()).thenReturn("user@gmail.com");
        when(mockPassField.getText()).thenReturn("wrongpassword");


        // Création d'un événement de clic sur "Connexion"
        JButton button = new JButton();
        button.setName("Connexion");
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "Connexion");

        controleurConnexion.actionPerformed(event);
        
        verify(mockConnectView, times(1)).showMessage(
            "Utilisateur inexistant : user@gmail.com | wrongpassword! Créer un compte ou vérifier vos identifiant", 
            "Échec", 
            JOptionPane.INFORMATION_MESSAGE
        );
        
        try {
            BaseDeDonnee.c.close();
        } catch (SQLException ex) {
            Logger.getLogger(ControleurProduitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testActionPerformed_inscriptionValidUser() {
        BaseDeDonnee.connexionBD();
        BaseDeDonnee.deleteUser("jean.dupont@gmail.com");
        // Mocks pour la vue d'inscription
        JTextField mockNomField = mock(JTextField.class);
        JTextField mockPrenomField = mock(JTextField.class);
        JTextField mockMailField = mock(JTextField.class);
        JTextField mockPassField = mock(JTextField.class);

        when(mockInscriptionView.jNom()).thenReturn(mockNomField);
        when(mockInscriptionView.jPrenom()).thenReturn(mockPrenomField);
        when(mockInscriptionView.jMail()).thenReturn(mockMailField);
        when(mockInscriptionView.JPass()).thenReturn(mockPassField);

        when(mockNomField.getText()).thenReturn("Dupont");
        when(mockPrenomField.getText()).thenReturn("Jean");
        when(mockMailField.getText()).thenReturn("jean.dupont@gmail.com");
        when(mockPassField.getText()).thenReturn("password123");

        // Empêcher toute action réelle du menu
        doNothing().when(mockView).setVisible(true);
        
        // Création d'un événement de clic sur "Inscription"
        JButton button = new JButton();
        button.setName("Inscription");
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "Inscription");

        controleurConnexion.actionPerformed(event);

        verify(mockInscriptionView, times(1)).showMessage(
            "Utilisateur ajouté avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE
        );
        verify(mockView, never()).setVisible(true);
        verify(mockInscriptionView).dispose();
        
        try {
            BaseDeDonnee.c.close();
        } catch (SQLException ex) {
            Logger.getLogger(ControleurProduitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testActionPerformed_viewInscription() {
        // Création d'un événement de clic sur "ViewInscription"
        JButton button = new JButton();
        button.setName("ViewInscription");
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "ViewInscription");

        controleurConnexion.actionPerformed(event);

        // Vérification des interactions
        verify(mockInscriptionView).setVisible(true);
        verify(mockConnectView).dispose();
    }

    @Test
    public void testActionPerformed_viewConnexion() {
        // Création d'un événement de clic sur "ViewConnexion"
        JButton button = new JButton();
        button.setName("ViewConnexion");
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "ViewConnexion");

        controleurConnexion.actionPerformed(event);

        // Vérification des interactions
        verify(mockConnectView).setVisible(true);
        verify(mockInscriptionView).dispose();
    }

    @Test
    public void testActionPerformed_quit() throws SQLException {
        

        // Création d'un événement de clic sur "Quit"
        JButton button = new JButton();
        button.setName("Quit");
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "Quit");

        controleurConnexion.actionPerformed(event);

        // Vérification des interactions
        verify(mockConnectView).dispose();
        verify(mockInscriptionView).dispose();
    }
}
