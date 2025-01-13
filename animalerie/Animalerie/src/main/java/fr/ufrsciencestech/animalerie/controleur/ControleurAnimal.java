/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie.controleur;

import fr.ufrsciencestech.animalerie.*;
import fr.ufrsciencestech.animalerie.bd.BaseDeDonnee;
import fr.ufrsciencestech.animalerie.view.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author delaton
 */
public class ControleurAnimal extends Controleur{
    private AjoutAnimalView anView;
    
    public ControleurAnimal(User user, View m)
    {
        super(user, m);
    }
    
    public void setVueG(View g)
    {
        this.menU = g;
    }
    
    public void setAnView(AjoutAnimalView p)
    {
        this.anView = p;
    }
    
    public AjoutAnimalView getAnView() 
    {
        return this.anView;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(((Component) ae.getSource()).getName().equals("Confirmer"))
        {
            String nom = anView.getNom().getText();
            int age = Integer.parseInt(anView.getAge().getText());
            String espece = anView.getEspece().getText();
            double p = Double.parseDouble(anView.getPoids().getText());
            Animal a = new Animal(nom, p, age, espece);
            
            anView.getNom().setText("");
            anView.getAge().setText("");
            anView.getEspece().setText("");
            anView.getPoids().setText("");
            
            //ajout de l'animal
            int id_client = BaseDeDonnee.getId_client(this.user.getMail());
            if(BaseDeDonnee.addAnimal(id_client, nom, p, age, espece))
            {
                this.user.addAnimal(a);
                DefaultListModel<String> model = new DefaultListModel<>();
                user.listAnimal(model);
                menU.getListAnimaux().setModel(model);
                
                anView.showMessage("Animal ajouté avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                anView.dispose();
            }
            else
            {
                anView.showMessage("Échec ajout animal "+nom, "Échec", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else
        {
            anView.getNom().setText("");
            anView.getAge().setText("");
            anView.getEspece().setText("");
            anView.getPoids().setText("");
            anView.dispose();
        }
    }
    
    
}
