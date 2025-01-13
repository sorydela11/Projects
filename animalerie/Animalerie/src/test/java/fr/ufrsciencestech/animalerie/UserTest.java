package fr.ufrsciencestech.animalerie;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.DefaultListModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        // Initialisation d'un utilisateur avec des données valides
        user = new User("Fatima", "Khmarou", "Fatima.Khmarou@example.com");
    }

    @Test
    public void testCreationUtilisateurValide() {
        assertNotNull("L'utilisateur ne doit pas être null après sa création.", user);
        assertEquals("Le prénom doit être 'Fatima'.", "Fatima", user.getFirstName());
        assertEquals("Le nom de famille doit être 'Khmarou'.", "Khmarou", user.getLastName());
        assertEquals("L'email doit être 'Fatima.Khmarou@example.com'.", "Fatima.Khmarou@example.com", user.getMail());
    }

    @Test
    public void testGetters() {
        assertEquals("Le prénom doit être 'Fatima'.", "Fatima", user.getFirstName());
        assertEquals("Le nom de famille doit être 'Khmarou'.", "Khmarou", user.getLastName());
        assertEquals("L'email doit être 'Fatima.Khmarou@example.com'.", "Fatima.Khmarou@example.com", user.getMail());
    }
    
    @Test
    public void testSetters()
    {
        user.setFirstName("Ibrahima");
        user.setLastName("Diallo");
        user.setMail("sorydela@exemple.com");
        assertEquals("Le prénom doit être 'Ibrahima'.", "Ibrahima", user.getFirstName());
        assertEquals("Le nom de famille doit être 'Diallo'.", "Diallo", user.getLastName());
        assertEquals("L'email doit être 'sorydela@exemple.com'.", "sorydela@exemple.com", user.getMail());
    }

    @Test
    public void testAjoutEtSuppressionReservation() {
        // Ajouter une réservation
        LocalDate date = LocalDate.parse("2024-12-23", DateTimeFormatter.ISO_LOCAL_DATE);
        Reservation reservation = new Reservation(date);
        Reservation reservation2 = new Reservation(date);
        user.addReservation(reservation);
        assertEquals("Le nombre de réservations doit être 1 après l'ajout.", 1, user.getNbReservations());
        
        user.addReservation(reservation2);
        assertEquals("Le nombre de réservations doit être 1 après l'ajout.", 1, user.getNbReservations());

        // Vérifier la liste des réservations en cours
        DefaultListModel<String> model = new DefaultListModel<>();
        user.listReservationEncours(model);
        assertEquals("La liste des réservations en cours doit contenir 1 élément.", 1, model.size());

        // Supprimer la réservation
        user.removeReservation(reservation);  // Suppression de la réservation de la liste
        // Vérifier que le nombre de réservations est 0 (en utilisant getReservations().size())
        assertEquals("Le nombre de réservations doit être 0 après la suppression.", 0, user.getNbReservations());
        
        reservation.confirmerReservation();
        user.addReservation(reservation);
        
        model = new DefaultListModel<>();
        user.listReservationValider(model);
        assertEquals("La liste des reservation valider doit contenir 1 élément.", 1, model.size());
        
        //pour tester le fait que la reservation existe déja
        user.addReservation(reservation);
        
        user.removeAllReservation();
        assertEquals("Le nombre de reservation doit être 0 après la suppression.", 0, user.getNbCommandes());
    }

    @Test
    public void testAjoutEtSuppressionCommande() {
        // Ajouter une commande
        Commande commande = new Commande();
        Commande commande2 = new Commande();
        user.addCommande(commande);
        assertEquals("Le nombre de commandes doit être 1 après l'ajout.", 1, user.getNbCommandes());
        
        user.addCommande(commande2);
        assertEquals("Le nombre de commandes doit être 1 après l'ajout.", 1, user.getNbCommandes());
        
        // Vérifier la liste des commandes en cours
        DefaultListModel<String> model = new DefaultListModel<>();
        user.listCommandeEncours(model);
        assertEquals("La liste des commandes en cours doit contenir 1 élément.", 1, model.size());

        // Supprimer la commande
        user.removeCommande(commande); 
        assertEquals("Le nombre de commandes doit être 0 après la suppression.", 0, user.getNbCommandes());
        
        commande.passerCommande();
        user.addCommande(commande);
        // Vérifier la liste des commandes valider
        model = new DefaultListModel<>();
        user.listCommandeValider(model);
        assertEquals("La liste des commandes valider doit contenir 1 élément.", 1, model.size());
        
        user.addCommande(commande);
        
        user.removeAllCommande();
        assertEquals("Le nombre de commandes doit être 0 après la suppression.", 0, user.getNbCommandes());
    }

    @Test

    public void testAjoutAnimal() {
    // Ajouter des animaux
    Animal animal1 = new Animal("Max", 10.0, 5, "Chien");  // Nom, poids, âge, espèce
    Animal animal2 = new Animal("Milo", 4.5, 3, "Chat");  // Nom, poids, âge, espèce

    user.addAnimal(animal1);
    user.addAnimal(animal2);

    // Vérifier le nombre d'animaux
    assertEquals("Le nombre d'animaux doit être 2 après l'ajout.", 2, user.getNbAnimaux());

    // Vérifier la liste des animaux
    DefaultListModel<String> model = new DefaultListModel<>();
    user.listAnimal(model);
    assertEquals("La liste des animaux doit contenir 2 éléments.", 2, model.size());

    // Vérifier que les animaux sont dans la liste
    assertTrue("La liste des animaux doit contenir 'Nom: Max, Espèce: Chien, Poids: 10.0 kg, Âge: 5 ans'.", 
        model.contains("Nom: Max, Espèce: Chien, Poids: 10.0 kg, Âge: 5 ans"));
    assertTrue("La liste des animaux doit contenir 'Nom: Milo, Espèce: Chat, Poids: 4.5 kg, Âge: 3 ans'.", 
        model.contains("Nom: Milo, Espèce: Chat, Poids: 4.5 kg, Âge: 3 ans"));
}

    @Test
    public void testToString()
    {
        String expResult = "Utilisateur : Fatima Khmarou, Mail : Fatima.Khmarou@example.com";
        String result = user.toString();
        assertEquals(expResult, result);
    }
    
}
