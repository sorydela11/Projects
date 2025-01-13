package fr.ufrsciencestech.animalerie.main;

import fr.ufrsciencestech.animalerie.bd.BaseDeDonnee;
import fr.ufrsciencestech.animalerie.view.UserConnect;
import fr.ufrsciencestech.animalerie.view.UserInscription;
import fr.ufrsciencestech.animalerie.controleur.ControleurConnexion;
import org.junit.*;
import static org.mockito.Mockito.*;

public class AnimalerieTest {
    
    // Mock des classes nécessaires
    private UserConnect mockUserConnect;
    private UserInscription mockUserInscription;
    private ControleurConnexion mockControleurConnexion;
    
    @Before
    public void setUp() {
        // Mock des vues
        mockUserConnect = mock(UserConnect.class);
        mockUserInscription = mock(UserInscription.class);
        // Mock du contrôleur
        mockControleurConnexion = mock(ControleurConnexion.class);
        
    }
    
    @After
    public void tearDown() {
        // Mock des vues
        mockUserConnect = null;
        mockUserInscription = null;
        // Mock du contrôleur
        mockControleurConnexion = null;
    }

    /**
     * Test de la méthode main de la classe Animalerie.
     * Nous vérifions que les vues sont créées et que le contrôleur est ajouté.
     */
    @Test
    public void testMain() {
        // Mock de la connexion à la BD
        if(BaseDeDonnee.connexionBD())
        {
             // Créer une instance de Animalerie avec les mocks injectés
            Animalerie animalerie = new Animalerie(mockUserConnect, mockUserInscription);
            animalerie.ajoutControleur(mockControleurConnexion);

            // Exécution de la méthode start
            animalerie.start();

            // Vérification que la méthode setVisible(true) a bien été appelée pour UserConnect
            verify(mockUserConnect, times(1)).setVisible(true);

            // Vérification que les vues ont bien ajouté le contrôleur
            verify(mockUserInscription, times(1)).ajoutControleur(mockControleurConnexion);
            verify(mockUserConnect, times(1)).ajoutControleur(mockControleurConnexion);

            // Vérification que les vues sont bien visibles
            verify(mockUserConnect, times(1)).setVisible(true);
            verify(mockUserInscription, never()).setVisible(true);
        }
        /*else
        {
            // Exécution de la méthode main
            Animalerie.main(null);

            // Vérification qu'aucune vue n'a été affichée
            verify(mockUserConnect, never()).setVisible(true);
            verify(mockUserInscription, never()).setVisible(true);
        }*/
        
    }
    
}
