/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie.controleur;

import fr.ufrsciencestech.animalerie.*;
import fr.ufrsciencestech.animalerie.view.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author delaton
 */
public abstract class Controleur implements ActionListener{
    protected User user;
    protected View menU;
    
    public Controleur(User u, View v)
    {
        this.user = u;
        this.menU = v;
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        
    }
    
}
