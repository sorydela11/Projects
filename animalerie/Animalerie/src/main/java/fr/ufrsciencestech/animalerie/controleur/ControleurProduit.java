package fr.ufrsciencestech.animalerie.controleur;

import fr.ufrsciencestech.animalerie.*;
import fr.ufrsciencestech.animalerie.bd.BaseDeDonnee;
import fr.ufrsciencestech.animalerie.view.*;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class ControleurProduit extends Controleur {
    private ProduitView produitView;

    /**
     * Constructeur du controleur Produit.
     *
     * @param user        L'utilisateur actuel.
     * @param produitView La vue associée à l'ajout de produits.
     */
    public ControleurProduit(User user, View m) {
        super(user, m);
    }
    
    public void setVueG(View g)
    {
        super.menU = g;
    }
    
    public void setProduitView(ProduitView p)
    {
        this.produitView = p;
    }
    
    public ProduitView getProduitView() { return this.produitView; }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // Vérifier quel bouton a été cliqué
        if (((Component) ae.getSource()).getName().equals("btConfirmer")) {
            
            Commande commande = new Commande();
            //ajout dans la base donnee
            int id_client = BaseDeDonnee.getId_client(user.getMail());
            int id_comm = BaseDeDonnee.getId_Commande(id_client, commande.getStatut().name());
            //si la commande n'existe pas dans la base de donnee on l'ajoute
            if(id_comm == -1)
            {
                System.out.println(id_comm);
                BaseDeDonnee.addCommande(user.getNbCommandes()+1, id_client, commande.getStatut().name());
            }
            id_comm = BaseDeDonnee.getId_Commande(id_client, commande.getStatut().name());
            System.out.println(id_comm);
            
            
            // Vérifier si l'utilisateur a choisi "Accessoire" ou "Médicament"
            boolean isAccessoire = produitView.getBtAccess().isSelected();
            JComboBox<String> comboBox = isAccessoire ? produitView.getjAccess() : produitView.getjMed();

            // Récupérer le nom du produit sélectionné
            String produitNom = (String) comboBox.getSelectedItem();
            //int indiceProd = (int) comboBox.getSelectedIndex();
            double prix = BaseDeDonnee.getPrixproduit(produitNom);
            
            // Récupérer la quantité sélectionnée
            int quantite = Integer.parseInt((String) produitView.getJQuantite().getSelectedItem());

            // Créer le produit approprié
            Produit produit = null;
            if (BaseDeDonnee.produitDispo(produitNom, quantite)){
                System.out.println("Produit "+produitNom+" dispo en stock");
                BaseDeDonnee.updateProduitStock(1, produitNom, quantite);
                if(isAccessoire)
                {
                    produit = new Accessoire(produitNom, prix, quantite);
                }
                else
                {
                    produit = new ProduitPhamacetique(produitNom, prix, quantite);
                }
                
                if(BaseDeDonnee.getId_Produit_Acheter(produitNom, id_comm) == -1)
                {
                    System.out.println("id produit : "+BaseDeDonnee.getId_Produit_Acheter(produitNom, id_comm));
                    if(BaseDeDonnee.addProduit(produitNom, quantite, prix, id_comm))
                    {
                        System.out.println("id produit : "+BaseDeDonnee.getId_Produit_Acheter(produitNom, id_comm));
                    }
                    else
                    {
                        System.out.println("impossible d'ajout le produit "+produitNom);
                    }
                }
                else
                {
                    System.out.println("id produit : "+BaseDeDonnee.getId_Produit_Acheter(produitNom, id_comm));
                    int id_prod = BaseDeDonnee.getId_Produit_Acheter(produitNom, id_comm);
                    if(BaseDeDonnee.updateProduitAcheter(id_prod, quantite, prix, id_comm))
                    {
                        System.out.println("MAJ du produit acheter car il existe déja");
                    }
                    else
                    {
                        System.out.println("MAJ du produit impossible");
                    }
                }
                
                // Ajouter le produit à la commande de l'utilisateur
                commande.ajoutProduit(produit);
                super.user.addCommande(commande);
                //affichage
                DefaultListModel<String> model = new DefaultListModel<>();
                user.listCommandeEncours(model);
                menU.getListCommande().setModel(model);
                // Afficher un message de confirmation
                //JOptionPane.showMessageDialog(produitView, "Produit ajouté : " + produitNom + " (" + quantite + ")", "Succès", JOptionPane.INFORMATION_MESSAGE);
                produitView.showMessage("Produit ajouté : " + produitNom + " (" + quantite + ")", "Succès", JOptionPane.INFORMATION_MESSAGE);
                produitView.dispose();
            } 
            else {
                System.out.println("Produit "+produitNom+" indisponible en stock");
                produitView.showMessage("Produit "+ produitNom + " Quantité -> (" + quantite + ")" + " indisponible en stock","Échec", JOptionPane.INFORMATION_MESSAGE);
                //JOptionPane.showMessageDialog(produitView, "Produit "+ produitNom + " Quantité -> (" + quantite + ")" + " indisponible en stock","Échec", JOptionPane.INFORMATION_MESSAGE);
            }

        }
        else if(((Component) ae.getSource()).getName().equals("btAnnuler"))
        {
            produitView.dispose();
        }
    }

}
