package fr.ufrsciencestech.animalerie;

import fr.ufrsciencestech.animalerie.bd.BaseDeDonnee;
import fr.ufrsciencestech.animalerie.controleur.ControleurProduitTest;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CommandeTest {
    
    private Commande commande;
    private Produit produit1;
    private Produit produit2;

    @Before
    public void setUp() {
        // Initialisation des objets avant chaque test
        commande = new Commande();
        // Création de produits avec le constructeur
        produit1 = new Accessoire("Coussin/Lit", 10.0, 5); // Produit avec un prix et une quantite
        produit2 = new ProduitPhamacetique("Paracétamol", 15.0, 1);

    }

    /**
     * Test de la méthode getStatut()
     */
    @Test
    public void testGetStatut() {
        Commande.StatutCommande result = commande.getStatut();
        assertEquals("Le statut de la commande devrait être EN_ATTENTE par défaut.", 
                     Commande.StatutCommande.EN_ATTENTE, result);
    }
    
    @Test
    public void testSetStatut(){
        commande.setStatut("CONFIRMÉE");
        Commande.StatutCommande result = commande.getStatut();
        assertEquals("Le statut de la commande devrait être CONFIRMÉE.", 
                     Commande.StatutCommande.CONFIRMÉE, result);
    }

    /**
     * Test de la méthode getNbProduitsCommander()
     */
    @Test
    public void testGetNbProduitsCommander() {
        // Avant ajout de produits, la commande devrait avoir 0 produit
        assertEquals("La commande ne devrait pas contenir de produits au départ.", 0, commande.getNbProduitsCommander());

        // Ajout de produits et vérification
        commande.ajoutProduit(produit1);
        commande.ajoutProduit(produit2);
        assertEquals("La commande devrait contenir 2 produits après ajout.", 2, commande.getNbProduitsCommander());
    }

    /**
     * Test de la méthode ajoutProduit()
     */
    @Test
    public void testAjoutProduit() {
        commande.ajoutProduit(produit1);
        assertEquals("La commande devrait contenir 1 produit après ajout.", 1, commande.getNbProduitsCommander());
        produit1.setQte(3);
        commande.ajoutProduit(produit1);
        assertEquals("La commande devrait contenir 1 produit après ajout.", 1, commande.getNbProduitsCommander());
        assertEquals("La quantité du produi doit augmenter", 6, produit1.getQte());
    }

    /**
     * Test de la méthode ajoutAllproduits()
     */
    @Test
    public void testAjoutAllproduits() {
        Commande autreCommande = new Commande();
        autreCommande.ajoutProduit(produit1);
        autreCommande.ajoutProduit(produit2);

        // Ajout de tous les produits de l'autre commande à la commande actuelle
        commande.ajoutAllproduits(autreCommande);
        assertEquals("La commande devrait contenir 2 produits après ajout des produits d'une autre commande.", 2, commande.getNbProduitsCommander());
    }

    /**
     * Test de la méthode getProduit()
     */
    @Test
    public void testGetProduit() {
        commande.ajoutProduit(produit1);
        commande.ajoutProduit(produit2);
        Produit result = commande.getProduit(1);  // Récupérer le 2ème produit (indice 1)
        assertEquals("Le produit récupéré devrait être le produit 2.", produit2, result);
    }

    /**
     * Test de la méthode retirerProduit()
     */
    @Test
    public void testRetirerProduit() {
        commande.ajoutProduit(produit1);
        commande.ajoutProduit(produit2);
        
        // Avant suppression
        assertEquals("La commande devrait contenir 2 produits.", 2, commande.getNbProduitsCommander());
        
        // Retirer un produit
        commande.retirerProduit(0); // Retirer le premier produit (indice 0)
        
        // Après suppression
        assertEquals("La commande devrait contenir 1 produit après suppression.", 1, commande.getNbProduitsCommander());
    }

    /**
     * Test de la méthode passerCommande()
     */
    @Test
    public void testPasserCommande() {
        commande.ajoutProduit(produit1);
        commande.ajoutProduit(produit2);
        
        // Vérifier le statut avant de passer la commande
        assertEquals("Le statut de la commande devrait être EN_ATTENTE avant de passer la commande.",
                     Commande.StatutCommande.EN_ATTENTE, commande.getStatut());
        
        // Passer la commande
        commande.passerCommande();
        
        // Vérifier le statut après avoir passé la commande
        assertEquals("Le statut de la commande devrait être CONFIRMEE après passage.",
                     Commande.StatutCommande.CONFIRMÉE, commande.getStatut());
    }

    /**
     * Test de la méthode calculerMontantTotal()
     */
    @Test
    public void testCalculerMontantTotal() {
        commande.ajoutProduit(produit1);
        commande.ajoutProduit(produit2);
        
        // Calcul du montant total attendu
        double montantAttendu = produit1.getPrix() + produit2.getPrix();
        double result = commande.calculerMontantTotal();
        
        assertEquals("Le montant total de la commande devrait être correct.",
                     montantAttendu, result, 0.01);  // Utilisation de la marge d'erreur (delta)
    }
    
    @Test
    public void testVerifierDisponibilite()
    {
        BaseDeDonnee.connexionBD();
        
        commande.ajoutProduit(produit1);
        System.out.println(commande.getNbProduitsCommander());
        
        boolean expResult = commande.verifierDisponibilite();
        System.out.println(expResult);
        
        assertTrue("Tous les produits doivent etre dispo en stock", expResult);
        
        try {
            BaseDeDonnee.c.close();
        } catch (SQLException ex) {
            Logger.getLogger(ControleurProduitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /**
     * Test de la méthode toString()
     */
    @Test
    public void testToString() {
        commande.ajoutProduit(produit1);
        commande.ajoutProduit(produit2);
        
        String result = commande.toString();
        String expResult = "Total Produit : "+commande.getNbProduitsCommander()+" | Statut : " +commande.getStatut()+"\n";
        for(int i=0; i<commande.getNbProduitsCommander(); i++)
        {
            expResult += commande.getProduit(i).toString() +"\n";
        }
        expResult += "Prix total : "+commande.calculerMontantTotal();
        
        assertEquals("La méthode toString() devrait renvoyer la représentation correcte de la commande.", 
                     expResult, result);
    }
}
