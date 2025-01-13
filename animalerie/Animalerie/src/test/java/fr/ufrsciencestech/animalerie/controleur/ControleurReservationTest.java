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

public class ControleurReservationTest {

    private ControleurReservation controleurReservation;
    private ReservationView mockReservationView;
    private View mockView;
    private User mockUser;

    @Before
    public void setUp() {
        mockReservationView = mock(ReservationView.class);
        mockView = mock(View.class);
        mockUser = mock(User.class);

        when(mockUser.getMail()).thenReturn("abcd@gmail.com");

        controleurReservation = new ControleurReservation(mockUser, mockView);
        controleurReservation.setRsView(mockReservationView);
    }

    @After
    public void tearDown() {
        controleurReservation = null;
        mockReservationView = null;
        mockView = null;
        mockUser = null;
    }

    @Test
    public void testSetVueG() {
        View newView = mock(View.class);
        controleurReservation.setVueG(newView);
        assertEquals(newView, controleurReservation.menU);
    }

    @Test
    public void testSetRsView() {
        ReservationView newReservationView = mock(ReservationView.class);
        controleurReservation.setRsView(newReservationView);
        assertEquals(newReservationView, controleurReservation.getVReservation());
    }

    @Test
    public void testActionPerformed_ConfirmReservation() throws SQLException {
        BaseDeDonnee.connexionBD();
        BaseDeDonnee.createUser("", "", mockUser.getMail(), "");
    // Mock des composants nécessaires
    ButtonGroup mockBtDate = mock(ButtonGroup.class);
    Enumeration<AbstractButton> mockDateButtons = mock(Enumeration.class);
    when(mockBtDate.getElements()).thenReturn(mockDateButtons);

    // Créer un mock de AbstractButton sans utiliser directement sa classe
    JRadioButton mockButtonDate = mock(JRadioButton.class);
    when(mockButtonDate.isSelected()).thenReturn(true);
    when(mockButtonDate.getText()).thenReturn("Demain");
    when(mockDateButtons.hasMoreElements()).thenReturn(true, false);
    when(mockDateButtons.nextElement()).thenReturn(mockButtonDate);

    when(mockReservationView.getBtDate()).thenReturn(mockBtDate);

   // Mock pour les groupes de boutons radio (durée et type de réservation)
    ButtonGroup mockBtGDuree = mock(ButtonGroup.class);
    ButtonGroup mockBtGReservation = mock(ButtonGroup.class);

    Enumeration<AbstractButton> mockDureeButtons = mock(Enumeration.class);
    Enumeration<AbstractButton> mockReservButtons = mock(Enumeration.class);

    // Créer un mock pour le bouton sélectionné dans chaque groupe de boutons
    JRadioButton mockButtonDuree = mock(JRadioButton.class);
    JRadioButton mockButtonReserv = mock(JRadioButton.class);

    // Configurer les boutons pour simuler les sélections
    when(mockButtonDuree.isSelected()).thenReturn(true);
    when(mockButtonDuree.getText()).thenReturn("30 minutes");  // Exemple pour la durée

    when(mockButtonReserv.isSelected()).thenReturn(true);
    when(mockButtonReserv.getText()).thenReturn("Promenade");  // Exemple pour le service

    // Simuler les énumérations de boutons dans les groupes de durée et de service
    when(mockDureeButtons.hasMoreElements()).thenReturn(true, false);  // Il y a un bouton de plus
    when(mockDureeButtons.nextElement()).thenReturn(mockButtonDuree);  // Retourne le bouton de durée

    when(mockReservButtons.hasMoreElements()).thenReturn(true, false);  // Il y a un bouton de plus
    when(mockReservButtons.nextElement()).thenReturn(mockButtonReserv);  // Retourne le bouton de service

    // Configurer les groupes de boutons pour renvoyer les énumérations de boutons correspondantes
    when(mockBtGDuree.getElements()).thenReturn(mockDureeButtons);
    when(mockBtGReservation.getElements()).thenReturn(mockReservButtons);

    // Retourner ces groupes dans la vue simulée
    when(mockReservationView.getBtGduree()).thenReturn(mockBtGDuree);
    when(mockReservationView.getBtGReservation()).thenReturn(mockBtGReservation);

    
    when(mockView.getListReservation()).thenReturn(new JList());

    // Simuler un événement ActionEvent
    JButton button = new JButton();
    button.setName("btConfirmer");
    ActionEvent ae = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null);

    // Appel à l'action
    controleurReservation.actionPerformed(ae);

    // Vérification de l'appel à la méthode dispose
    verify(mockReservationView, times(1)).dispose();
    
    try {
            BaseDeDonnee.deleteUser(mockUser.getMail());
            BaseDeDonnee.c.close();
        } catch (SQLException ex) {
            Logger.getLogger(ControleurProduitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
}

    @Test
    public void testActionPerformed_AnnulerReservation() {
        JButton button = new JButton();
        button.setName("btAnnuler");
        ActionEvent ae = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, null);

        controleurReservation.actionPerformed(ae);

        verify(mockReservationView, times(1)).dispose();
    }
}
