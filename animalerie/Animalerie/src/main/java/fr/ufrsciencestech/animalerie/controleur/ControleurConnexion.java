/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie.controleur;

import fr.ufrsciencestech.animalerie.User;
import fr.ufrsciencestech.animalerie.bd.*;
import fr.ufrsciencestech.animalerie.view.*;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author delaton
 */
public class ControleurConnexion extends Controleur{
    private UserConnect conView;
    private UserInscription insView;
    
    public ControleurConnexion(UserConnect c, UserInscription u) {
        super(null, null);
        this.conView = c;
        this.insView = u;
    }
    
    public void setConnectVue(UserConnect u)
    {
        this.conView = u;
    }
    
    public void setInscriptionVue(UserInscription u)
    {
        this.insView = u;
    }
    
    public void setUser(User u)
    {
        super.user = u;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (((Component) ae.getSource()).getName().equals("Connexion")) {
            String mail = conView.jMail().getText();
            String password = conView.JPass().getText();
            if(BaseDeDonnee.userExist(mail, password))
            {
                conView.showMessage("Connexion en cours", "Succès", JOptionPane.INFORMATION_MESSAGE);
                user = BaseDeDonnee.infoUser(mail);
                BaseDeDonnee.getAllAnimal(user);
                BaseDeDonnee.getAllReservation(user);
                BaseDeDonnee.getAllCommande(user);
                
                menU = new View();
                ControleurMenu c = new ControleurMenu(user, menU);
                menU.ajoutControleur(c);
                
                //maj info du user
                menU.setInformation(user.getLastName(), user.getFirstName(), user.getMail());
                
                DefaultListModel<String> model = new DefaultListModel<>();
                user.listReservationEncours(model);
                menU.getListReservation().setModel(model);

                model = new DefaultListModel<>();
                user.listCommandeEncours(model);
                menU.getListCommande().setModel(model);

                model = new DefaultListModel<>();
                user.listAnimal(model);
                menU.getListAnimaux().setModel(model);
                
                model = new DefaultListModel<>();
                user.listCommandeValider(model);
                user.listReservationValider(model);
                menU.getListHistorique().setModel(model);
                
                conView.jMail().setText("");
                conView.JPass().setText("");
                conView.dispose();
                
                 // Agrandir le JFrame à la taille de l'écran
                menU.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiser la fenêtre
                menU.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); // Adapter à l'écran

                menU.setVisible(true);
            }
            else
            {
                conView.showMessage("Utilisateur inexistant : "+mail+" | "+password+"! Créer un compte ou vérifier vos identifiant", "Échec", JOptionPane.INFORMATION_MESSAGE);
                conView.jMail().setText("");
                conView.JPass().setText("");
            }
            
        }
        
        else if(((Component) ae.getSource()).getName().equals("Inscription")){
            String nom = insView.jNom().getText();
            String prenom = insView.jPrenom().getText();
            String mail = insView.jMail().getText();
            String password = insView.JPass().getText();
            
            if(this.verifyEntryMailAndPassWord(mail, password))
            {
                if(BaseDeDonnee.createUser(nom, prenom, mail, password))
                {
                    insView.showMessage("Utilisateur ajouté avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    insView.jMail().setText("");
                    insView.JPass().setText("");
                    insView.jNom().setText("");
                    insView.jPrenom().setText("");
                    insView.dispose();
                    user = new User(prenom, nom, mail);
                    menU = new View();
                    ControleurMenu c = new ControleurMenu(user, menU);
                    menU.ajoutControleur(c);

                    //maj info du user
                    menU.setInformation(nom, prenom, mail);
                    menU.setVisible(true);
                }
                else
                {
                    insView.showMessage("Mail : "+mail+" déja existant, utiliser un autre mail ou connectez vous", "Échec", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else
            {
                insView.showMessage("Entrée invalide !!! Mail incorrect ou mot de passe trop court (moins de 6 caractères) !!! \n Exemple : Mail -> username@gmail.com | Mot de passe -> abc123?!", "Échec", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
        else if(((Component) ae.getSource()).getName().equals("ViewInscription")){
            insView.setVisible(true);
            conView.dispose();
        }
        
        else if(((Component) ae.getSource()).getName().equals("ViewConnexion"))
        {
            conView.setVisible(true);
            insView.dispose();
        }
        
        else if(((Component) ae.getSource()).getName().equals("Quit"))
        {
            conView.dispose();
            insView.dispose();
            try {
                BaseDeDonnee.c.close();
            } catch (SQLException ex) {
                Logger.getLogger(ControleurConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    private boolean verifyEntryMailAndPassWord(String mail, String pass)
    {
        boolean res = true;
        
        if(!mail.contains("@gmail.com"))
        {
            res = false;
        }
        else if(pass.length() < 6)
        {
            res = false;
        }
        
        return res;
    }
    
}
