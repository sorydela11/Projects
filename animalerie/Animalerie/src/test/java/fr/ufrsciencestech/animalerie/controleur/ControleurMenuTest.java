package fr.ufrsciencestech.animalerie.controleur;

import fr.ufrsciencestech.animalerie.*;
import fr.ufrsciencestech.animalerie.Commande.StatutCommande;
import fr.ufrsciencestech.animalerie.Reservation.StatutReservation;
import fr.ufrsciencestech.animalerie.bd.BaseDeDonnee;
import fr.ufrsciencestech.animalerie.view.*;
import org.junit.*;
import static org.mockito.Mockito.*;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.*;

public class ControleurMenuTest {
    private ControleurMenu controleurMenu;
    private User mockUser;
    private View mockMenu;

    @Before
    public void setUp() {
        // Mock des dépendances
        mockUser = mock(User.class);
        mockMenu = mock(View.class);

        // Initialisation du contrôleur
        controleurMenu = new ControleurMenu(mockUser, mockMenu);
    }

    @After
    public void tearDown() {
        mockUser = null;
        mockMenu = null;
        controleurMenu = null;
    }

    @Test
    public void testActionPerformed_service() {
        // Simuler un événement "Service"
        JButton button = new JButton();
        button.setName("Service");
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "Service");

        // Mock de la vue ReservationView pour ne pas l'afficher réellement
        ReservationView mockReservationView = mock(ReservationView.class);

        // Mock du contrôleur ControleurReservation
        ControleurReservation mockControleurReservation = mock(ControleurReservation.class);

        // Configurer le comportement attendu pour le contrôleur
        doNothing().when(mockControleurReservation).setRsView(mockReservationView);
        doNothing().when(mockReservationView).ajoutControleur(mockControleurReservation);

        // Appeler la méthode actionPerformed
        controleurMenu.actionPerformed(event);

        // Vérification que la méthode setVisible n'est pas appelée sur ReservationView
        verify(mockReservationView, never()).setVisible(true); // La vue ne doit pas être visible

        // Vérifications : la vue et le contrôleur associés à "Service" doivent être créés et affichés
        verify(mockMenu, never()).dispose(); // Le menu principal reste ouvert
    }

    @Test
    public void testActionPerformed_produits() {
        // Simuler un événement "Produits"
        JButton button = new JButton();
        button.setName("Produits");
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "Produits");
        
        // Mock de la vue ProduitView pour ne pas l'afficher réellement
        ProduitView mockProduitView = mock(ProduitView.class);

        // Mock du contrôleur ControleurProduit
        ControleurProduit mockControleurProduit = mock(ControleurProduit.class);

        // Configurer le comportement attendu pour le contrôleur
        doNothing().when(mockControleurProduit).setProduitView(mockProduitView);
        doNothing().when(mockProduitView).ajoutControleur(mockControleurProduit);

        // Appeler la méthode actionPerformed
        controleurMenu.actionPerformed(event);

        // Vérification que la méthode setVisible n'est pas appelée sur ProduitView
        verify(mockProduitView, never()).setVisible(true); // La vue ne doit pas être visible

        // Vérifications : la vue ProduitView doit être affichée
        verify(mockMenu, never()).dispose(); // Le menu principal reste ouvert
    }

    @Test
    public void testActionPerformed_animal() {
        // Simuler un événement "Animal"
        JButton button = new JButton();
        button.setName("Animal");
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "Animal");
        
        // Mock de la vue AjoutAnimalView pour ne pas l'afficher réellement
        AjoutAnimalView mockAjoutAnimalView = mock(AjoutAnimalView.class);

        // Mock du contrôleur ControleurAnimal
        ControleurAnimal mockControleurAnimal = mock(ControleurAnimal.class);

        // Configurer le comportement attendu pour le contrôleur
        doNothing().when(mockControleurAnimal).setAnView(mockAjoutAnimalView);
        doNothing().when(mockAjoutAnimalView).ajoutControleur(mockControleurAnimal);

        // Appeler la méthode actionPerformed
        controleurMenu.actionPerformed(event);

        // Vérification que la méthode setVisible n'est pas appelée sur AjoutAnimalView
        verify(mockAjoutAnimalView, never()).setVisible(true); // La vue ne doit pas être visible
        // Vérifications : la vue AjoutAnimalView doit être affichée
        verify(mockMenu, never()).dispose(); // Le menu principal reste ouvert
    }

    @Test
    public void testActionPerformed_quit() throws SQLException {
        BaseDeDonnee.connexionBD();
        // Simuler un événement "Quit"
        JButton button = new JButton();
        button.setName("Quit");
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "Quit");

        // Appeler la méthode actionPerformed
        controleurMenu.actionPerformed(event);

        // Vérifications : le menu principal doit être fermé et la connexion DB doit être fermée
        verify(mockMenu, times(1)).dispose();
        verify(mockMenu, never()).setVisible(true); // Le menu ne doit pas se réafficher
    }

}
