package fr.ufrsciencestech.animalerie;

import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReservationTest {
    
    private Reservation reservation;
    private Service service1;
    private Service service2;

    @Before
    public void setUp() {
        // Initialisation des objets avant chaque test
        reservation = new Reservation(LocalDate.of(2024, 12, 23)); // Date de la réservation
        service1 = new Garde(50.0,"Garde");  // Service 1 avec un prix de 50
        service2 = new Promenade(20.0,"Promenade");  // Service 2 avec un prix de 20
    }

    /**
     * Test of getDate method, of class Reservation.
     */
    @Test
    public void testGetDate() {
        System.out.println("getDate");
        LocalDate expResult = LocalDate.of(2024, 12, 23);
        LocalDate result = reservation.getDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStatut method, of class Reservation.
     */
    @Test
    public void testGetStatut() {
        System.out.println("getStatut");
        Reservation.StatutReservation expResult = Reservation.StatutReservation.EN_ATTENTE;
        Reservation.StatutReservation result = reservation.getStatut();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNbServicesReserver method, of class Reservation.
     */
    @Test
    public void testGetNbServicesReserver() {
        System.out.println("getNbServicesReserver");
        int expResult = 0;  // Aucune réservation de service au départ
        int result = reservation.getNbServicesReserver();
        assertEquals(expResult, result);

        // Ajout de services
        reservation.ajoutService(service1);
        reservation.ajoutService(service2);
        expResult = 2;
        result = reservation.getNbServicesReserver();
        assertEquals(expResult, result);
    }

    /**
     * Test of getServices method, of class Reservation.
     */
    @Test
    public void testGetServices() {
        System.out.println("getServices");
        ArrayList<Service> expResult = new ArrayList<>();
        expResult.add(service1);
        expResult.add(service2);

        reservation.ajoutService(service1);
        reservation.ajoutService(service2);

        ArrayList<Service> result = reservation.getServices();
        assertEquals(expResult, result);
    }

    /**
     * Test of getService method, of class Reservation.
     */
    @Test
    public void testGetService() {
        System.out.println("getService");
        reservation.ajoutService(service1);
        reservation.ajoutService(service2);

        Service expResult = service1;
        Service result = reservation.getService(0);
        assertEquals(expResult, result);
    }

    /**
     * Test of ajoutService method, of class Reservation.
     */
    @Test
    public void testAjoutService() {
        System.out.println("ajoutService");
        reservation.ajoutService(service1);
        
        assertEquals(1, reservation.getNbServicesReserver()); // Vérifier que 1 service est ajouté
        assertTrue(reservation.getServices().contains(service1)); // Vérifier que le service a bien été ajouté
    }

    /**
     * Test of ajoutAllService method, of class Reservation.
     */
    @Test
    public void testAjoutAllService() {
        System.out.println("ajoutAllService");
        Reservation otherReservation = new Reservation(LocalDate.of(2024, 12, 24));
        otherReservation.ajoutService(service1);
        otherReservation.ajoutService(service2);

        reservation.ajoutAllService(otherReservation);

        assertEquals(2, reservation.getNbServicesReserver()); // Vérifier que les services ont été ajoutés
        assertTrue(reservation.getServices().contains(service1)); // Vérifier que le service 1 a été ajouté
        assertTrue(reservation.getServices().contains(service2)); // Vérifier que le service 2 a été ajouté
    }

    /**
     * Test of retirerService method, of class Reservation.
     */
    @Test
    public void testRetirerService() {
        System.out.println("retirerService");
        reservation.ajoutService(service1);
        reservation.ajoutService(service2);
        
        reservation.retirerService(0);  // Retirer le premier service

        assertEquals(1, reservation.getNbServicesReserver()); // Vérifier qu'il reste un service
        assertFalse(reservation.getServices().contains(service1)); // Vérifier que le service 1 a été retiré
    }

    /**
     * Test of estConfirmee method, of class Reservation.
     */
    @Test
    public void testEstConfirmee() {
        System.out.println("estConfirmee");
        boolean expResult = false;
        boolean result = reservation.estConfirmee();
        assertEquals(expResult, result);

        reservation.confirmerReservation(); // Confirmer la réservation
        expResult = true;
        result = reservation.estConfirmee();
        assertEquals(expResult, result);  // Vérifier que la réservation est confirmée
    }

    /**
     * Test of calculerCoutTotal method, of class Reservation.
     */
    @Test
    public void testCalculerCoutTotal() {
        System.out.println("calculerCoutTotal");
        reservation.ajoutService(service1);
        reservation.ajoutService(service2);

        double expResult = 70.0; // Coût total attendu (50 + 20)
        double result = reservation.calculerCoutTotal();
        assertEquals(expResult, result, 0.01);  // Vérifier que le coût total est correct
    }

    /**
     * Test of confirmerReservation method, of class Reservation.
     */
    @Test
    public void testConfirmerReservation() {
        System.out.println("confirmerReservation");
        reservation.confirmerReservation();
        
        assertEquals(Reservation.StatutReservation.CONFIRMÉE, reservation.getStatut()); // Vérifier que le statut est CONFIRMEE
    }

    /**
     * Test of toString method, of class Reservation.
     */
    @Test




  public void testToString() {
        System.out.println("toString");
        String expResult = "Date de reservation : "+reservation.getDate().toString()+" | Statut : "+reservation.getStatut()+"\nServices : \n";
        for(int i=0; i<reservation.getNbServicesReserver(); i++)
        {
            expResult += reservation.getService(i).toString() + "\n";
        }
        expResult += "Prix total : "+reservation.calculerCoutTotal();
        String result = reservation.toString();
        assertEquals(expResult, result); // Vérifier que la chaîne contient la date et le statut attendu
    }
}
