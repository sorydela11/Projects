/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie.controleur;

import fr.ufrsciencestech.animalerie.*;
import fr.ufrsciencestech.animalerie.Toilettage.TypeDeToilettage;
import fr.ufrsciencestech.animalerie.bd.BaseDeDonnee;
import fr.ufrsciencestech.animalerie.view.*;
import java.awt.Component;
import java.awt.event.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Enumeration;
import javax.swing.*;

/**
 *
 * @author delaton
 */
public class ControleurReservation extends Controleur{
    private ReservationView reserv;
    
    public ControleurReservation(User user, View m)
    {
        super(user, m);
    }
    
    
    public void setVueG(View g)
    {
        super.menU = g;
    }
    
    public void setRsView(ReservationView r)
    {
        this.reserv = r;
    }
    
    public ReservationView getVReservation() {
        return this.reserv;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Service s = null;
        if(((Component)ae.getSource()).getName().equals("btConfirmer"))
        {
            // Récupérer le groupe de boutons radio pour la durée
            ButtonGroup btDate = reserv.getBtDate(); 
             // Récupérer l'Enumeration des boutons dans le groupe
            Enumeration<AbstractButton> elements = btDate.getElements();
            LocalDate date = LocalDate.now();

            // Parcourir l'Enumeration avec un while
            while (elements.hasMoreElements()) {
                AbstractButton button = elements.nextElement();
                if (button.isSelected()) {
                   switch(button.getText())
                   {
                       case "Demain" : date = date.plusDays(1); break;
                       case "Après-demain" : date = date.plusDays(2); break;
                       case "Dans une semaine" : date = date.plusWeeks(1); break;
                       case "Dans deux semaines" : date = date.plusWeeks(2); break;
                       default : date = date.plusDays(5); break;
                   }
                   break;
                }
            }
            
            Reservation r = new Reservation(date);
            //ajout dans la base donnee
            int id_client = BaseDeDonnee.getId_client(user.getMail());
            //si la reservation n'existe pas dans la base de donnee on l'ajoute
            if(BaseDeDonnee.getId_reservation(r.getDate(), r.getStatut(), id_client) == -1)
            {
                System.out.println("Reservation inexistante dans la bd");
                BaseDeDonnee.addReservation(user.getNbReservations()+1, id_client, r.getStatut().name(), Date.valueOf(r.getDate()));
            }
            int id_reserv = BaseDeDonnee.getId_reservation(r.getDate(), r.getStatut(), id_client);
            System.out.println("id_reservation -> "+id_reserv);
            if(id_reserv != -1)
            {
                // Récupérer le groupe de boutons radio pour la durée
                ButtonGroup btGDuree = reserv.getBtGduree(); 
                 // Récupérer l'Enumeration des boutons dans le groupe
                elements = btGDuree.getElements();
                // Parcourir l'Enumeration avec un while
                String duree = "";
                int i=1;
                while (elements.hasMoreElements()) {
                    AbstractButton button = elements.nextElement();
                    if (button.isSelected()) {
                       duree = button.getText();
                       break;
                    }
                    i++;
                }

                // Récupérer le groupe de boutons radio pour la durée
                ButtonGroup btGReserv = reserv.getBtGReservation(); 
                 // Récupérer l'Enumeration des boutons dans le groupe
                elements = btGReserv.getElements();
                // Parcourir l'Enumeration avec un while
                while (elements.hasMoreElements()) {
                    AbstractButton button = elements.nextElement();
                    if (button.isSelected()) {
                        double prix = 0.0;
                        switch(button.getText())
                        {
                            case "Promenade" : prix = 10.0 * i; s = new Promenade(prix,duree);
                                this.ajoutBaseDedonnee("Promenade", prix, duree, null, id_reserv);
                                break;
                            case "Garde" : prix = 20.0 * (i-1); s = new Garde(prix,duree); 
                                this.ajoutBaseDedonnee("Garde", prix, duree, null, id_reserv);
                                break;
                            case "Education Canine" : prix = 50.0 * (i-1); s = new EducationCanine(prix,duree);
                                this.ajoutBaseDedonnee("Education Canine", prix, duree, null, id_reserv);
                                break;
                            case "Toilettage" : s = recupToit(id_reserv); break;
                        }
                    }
                }

                r.ajoutService(s);
                super.user.addReservation(r);

                DefaultListModel<String> model = new DefaultListModel<>();
                user.listReservationEncours(model);
                menU.getListReservation().setModel(model);

                reserv.showMessage("Service"+ s.toString() + " ajouter dans vos réservations", "Succès", JOptionPane.INFORMATION_MESSAGE);
                reserv.dispose();
            }
            
            else
            {
                reserv.showMessage("Échec ajout reservation dans la bd son id est "+id_reserv, "Échec", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
        else if(((Component)ae.getSource()).getName().equals("btAnnuler"))
        {
            reserv.dispose();
        }
    }
    
    public Service recupToit(int id_reserv)
    {
        Service s = null;
        String des = "";
        TypeDeToilettage tp = TypeDeToilettage.TOILETTAGE_DE_BASE;
        int i = 1; 
        // Récupérer le groupe de boutons radio pour la durée
        ButtonGroup btGDesc = reserv.getBtGDesc(); 
        // Récupérer l'Enumeration des boutons dans le groupe
        Enumeration<AbstractButton> eletDesc = btGDesc.getElements();
        while (eletDesc.hasMoreElements()) {
            AbstractButton bt = eletDesc.nextElement();
            if (bt.isSelected()) {
               des = bt.getText();
               switch(des)
               {
                   case "Douche" : tp = TypeDeToilettage.DOUCHE; break;
                   case "Coupe et soins des ongles" : tp = TypeDeToilettage.COUPE_ET_SOINS_DES_ONGLES; break;
                   case "Tonnage complet" : tp = TypeDeToilettage.TONNAGE_COMPLET; break;
                   case "Rasage des coussinets" : tp = TypeDeToilettage.RASAGE_DES_COUSSINET_DE_PATTES; break;
                   case "Toilettge de base" : tp = TypeDeToilettage.TOILETTAGE_DE_BASE; break;
               }
               break;
            }
            i++;
        }
        double prix = 30.0 * i;
        s = new Toilettage(prix, tp);
        this.ajoutBaseDedonnee("Toilettage", prix, null, tp.name(), id_reserv);
        return s;
    }
    
    public void ajoutBaseDedonnee(String nom, double prix, String duree, String desc, int id_reserv)
    {
        if(BaseDeDonnee.addService(nom, prix, duree, desc, id_reserv))
        {
            System.out.println("Ajout reussi du service "+nom+" "+duree+" "+desc+" id_reser : "+id_reserv+" dans la base de donnee");
                   
        }
        else
        {
            System.out.println("Echec ajout du service "+nom+" dans la base de donnee");
        }
    }

    
}
