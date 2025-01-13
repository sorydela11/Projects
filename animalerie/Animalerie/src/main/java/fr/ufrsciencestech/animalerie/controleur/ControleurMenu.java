/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie.controleur;

import fr.ufrsciencestech.animalerie.*;
import fr.ufrsciencestech.animalerie.Commande.StatutCommande;
import fr.ufrsciencestech.animalerie.Reservation.StatutReservation;
import fr.ufrsciencestech.animalerie.bd.BaseDeDonnee;
import fr.ufrsciencestech.animalerie.view.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.DefaultListModel;

/**
 *
 * @author delaton
 */
public class ControleurMenu extends Controleur{
    
    public ControleurMenu(User user, View m)
    {
        super(user, m);
    }
    
    public void setViewMenu(View v)
    {
        super.menU = v;
    }
    
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (((Component) ae.getSource()).getName().equals("Service")) {
            ReservationView reservationView = new ReservationView();
            ControleurReservation controleurReservation = new ControleurReservation(user, menU);
            //controleurReservation.setVueG(menU);
            controleurReservation.setRsView(reservationView);
            reservationView.ajoutControleur(controleurReservation);
            reservationView.setVisible(true);
        }
        
        else if (((Component) ae.getSource()).getName().equals("Produits")) {
            // Créer et configurer le contrôleur pour les produits
            ProduitView produitView = new ProduitView();
            ControleurProduit controleurProduit = new ControleurProduit(user, menU);
            //controleurProduit.setVueG(menU);
            controleurProduit.setProduitView(produitView);
            produitView.ajoutControleur(controleurProduit);
            produitView.setVisible(true);
        }
        
        else if (((Component) ae.getSource()).getName().equals("Animal")){
            AjoutAnimalView ajoutAn = new AjoutAnimalView();
            ControleurAnimal cn = new ControleurAnimal(user, menU);
            //cn.setVueG(menU);
            cn.setAnView(ajoutAn);
            ajoutAn.ajoutControleur(cn);
            ajoutAn.setVisible(true);
        }
        
        else if (((Component) ae.getSource()).getName().equals("Quit"))
        {
            this.menU.dispose();
            try {
                BaseDeDonnee.c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        else if (((Component) ae.getSource()).getName().equals("AnnulerCommande")){
            int id_client = BaseDeDonnee.getId_client(user.getMail());
            for(int i=0; i<this.user.getNbCommandes(); i++)
            {
                Commande c = this.user.getCommande(i);
                if(c.getStatut() == StatutCommande.EN_ATTENTE)
                {
                    this.maj_stock_bd(c);
                    int id_comm = BaseDeDonnee.getId_Commande(id_client, c.getStatut().name());
                    if(id_comm != -1 && BaseDeDonnee.updateStatutCommande(0, id_comm))
                    {
                        System.out.println("Commande "+id_comm+" supprimer de la base de donnee");
                        this.user.removeCommande(c);
                    } 
                    else
                    {
                        System.out.println("Impossible de supprimer la commande "+id_comm+" de la base de donnee");
                    }
                }
                
            }
            
            //réaffichage
            DefaultListModel<String> model = new DefaultListModel<>();
            user.listCommandeEncours(model);
            menU.getListCommande().setModel(model);

            model = new DefaultListModel<>();
            user.listCommandeValider(model);
            user.listReservationValider(model);
            menU.getListHistorique().setModel(model);
            
        }
        
        else if (((Component) ae.getSource()).getName().equals("AnnulerReserv")){
            int id_client = BaseDeDonnee.getId_client(user.getMail());
            for(int i=0; i<this.user.getNbReservations(); i++)
            {
                Reservation r = this.user.getReservation(i);
                if(r.getStatut() == StatutReservation.EN_ATTENTE)
                {
                    int id_res = BaseDeDonnee.getId_reservation(r.getDate(), r.getStatut(), id_client);
                    if(id_res != -1 && BaseDeDonnee.updateStatutReservation(0, id_res))
                    {
                        System.out.println("Reservation "+id_res+" supprimer de la base de donnee");
                        this.user.removeReservation(r);
                    } 
                    else
                    {
                        System.out.println("Impossible de supprimer la reservation "+id_res+" de la base de donnee");
                    }
                }
            }
            
            DefaultListModel<String> model = new DefaultListModel<>();
            user.listReservationEncours(model);
            menU.getListReservation().setModel(model);

            model = new DefaultListModel<>();
            user.listCommandeValider(model);
            user.listReservationValider(model);
            menU.getListHistorique().setModel(model);
        }
        
        else if (((Component) ae.getSource()).getName().equals("ConfirmReserv")){
            int id_client = BaseDeDonnee.getId_client(user.getMail());
            for(int i=0; i<this.user.getNbReservations(); i++)
            {
                Reservation r = this.user.getReservation(i);
                if(r.getStatut() == StatutReservation.EN_ATTENTE)
                {
                    int id_res = BaseDeDonnee.getId_reservation(r.getDate(), r.getStatut(), id_client);
                    if(id_res != -1 && BaseDeDonnee.updateStatutReservation(1, id_res))
                    {
                        System.out.println("Statut reservation -> "+id_res+" passer à confirmé");
                        r.confirmerReservation();
                    } 
                    else
                    {
                        System.out.println("Impossible de changer le statut de la reservation "+id_res+" de la base de donnee");
                    }
                }
            }
            
            DefaultListModel<String> model = new DefaultListModel<>();
            user.listReservationEncours(model);
            menU.getListReservation().setModel(model);

            model = new DefaultListModel<>();
            user.listCommandeValider(model);
            user.listReservationValider(model);
            menU.getListHistorique().setModel(model);

        }
        
        else if (((Component) ae.getSource()).getName().equals("ConfirmCommand")){
            int id_client = BaseDeDonnee.getId_client(user.getMail());
            for(int i=0; i<this.user.getNbCommandes(); i++)
            {
                Commande c = this.user.getCommande(i);
                if(c.getStatut() == StatutCommande.EN_ATTENTE)
                {
                    int id_comm = BaseDeDonnee.getId_Commande(id_client, c.getStatut().name());
                    if(id_comm != -1 && BaseDeDonnee.updateStatutCommande(1, id_comm))
                    {
                        System.out.println("Commande "+id_comm+" est passe a confirme dans la base de donnee");
                        c.passerCommande();
                    } 
                    else
                    {
                        System.out.println("Impossible de changer le statut de la commande "+id_comm+" de la base de donnee");
                    }
                }   
            }
            
            DefaultListModel<String> model = new DefaultListModel<>();
            user.listCommandeEncours(model);
            menU.getListCommande().setModel(model);

            model = new DefaultListModel<>();
            user.listCommandeValider(model);
            user.listReservationValider(model);
            menU.getListHistorique().setModel(model);
        }
        
    }
    
    
    public void maj_stock_bd(Commande c)
    {
        for(int i=0; i<c.getNbProduitsCommander(); i++)
        {
            Produit p = c.getProduit(i);
            if(BaseDeDonnee.updateProduitStock(0, p.getNom(), p.getQte()))
            {
                System.out.println("Augmentation du stock produit "+p.getNom()+" dans la bd car commande annuler");
            }
            else
            {
                System.out.println("Echec Augmentation du stock produit "+p.getNom()+" dans la bd car commande annuler");
            }
        }
    }
    
}
