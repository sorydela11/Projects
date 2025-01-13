/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie;

import fr.ufrsciencestech.animalerie.bd.BaseDeDonnee;
import java.util.ArrayList;

/**
 * Classe représentant une commande pour un client.
 * Cette classe contient des informations sur l'utilisateur qui a passé la commande,
 * les produits associés à la commande, ainsi que le statut de la commande.
 * 
 * Le statut de la commande peut être "En attente", "Confirmée".
 * 
 * @author delaton
 */
public class Commande {

    /**
     * Enumération représentant les différents statuts possibles pour une commande.
     */
    public enum StatutCommande {
        EN_ATTENTE,  // Commande en attente de confirmation
        CONFIRMÉE,   // Commande confirmée et en cours de traitement
    }


    /**
     * La liste des produits commandés dans le cadre de cette commande.
     */
    private ArrayList<Produit> produits;

    /**
     * Le statut de la commande.
     */
    private StatutCommande statut;

    /**
     * Constructeur permettant de créer une commande pour un utilisateur.
     * Le statut est initialisé à EN_ATTENTE par défaut.
     * 
     */
    public Commande() {
        this.produits = new ArrayList<Produit>();
        this.statut = StatutCommande.EN_ATTENTE;
    }

    /**
     * Récupère le statut de la commande.
     * 
     * @return Le type du statut
     */
    public StatutCommande getStatut() { return this.statut; }
    
    public void setStatut(String s)
    {
        this.statut = StatutCommande.valueOf(s);
    }
    
    /**
     * Récupère le nombre de produits commandés dans cette commande.
     * 
     * @return Le nombre de produits commandés.
     */
    public int getNbProduitsCommander() {
        return this.produits.size();  // Récupérer le nombre de produits
    }

    public Produit getProduit(int i)
    {
        return this.produits.get(i);
    }
    
    public Produit getProduit_by_name(String name)
    {
        for(int i=0; i<this.getNbProduitsCommander(); i++)
        {
            if(this.getProduit(i).getNom().equals(name))
            {
                return this.getProduit(i);
            }
        }
        return null;
    }
    
    /**
     * Ajoute un produit à la commande.
     * 
     * @param p Le produit à ajouter à la commande.
     */
    public void ajoutProduit(Produit p) {
        if(this.getProduit_by_name(p.getNom()) != null)
        {
            this.getProduit_by_name(p.getNom()).setPrix(this.getProduit_by_name(p.getNom()).getPrix() + p.getPrix());
            this.getProduit_by_name(p.getNom()).setQte(this.getProduit_by_name(p.getNom()).getQte() + p.getQte());
        }
        else
        {
            this.produits.add(p);
        }
    }

    public void ajoutAllproduits(Commande c) {
       for(int i=0; i < c.getNbProduitsCommander(); i++)
       {
           this.ajoutProduit(c.getProduit(i));
       }
    }
    
    /**
     * Retire un produit de la commande en fonction de l'indice.
     * 
     * @param i L'indice du produit à retirer.
     */
    public void retirerProduit(int i) {
        if (i >= 0 && i < this.produits.size()) {
            this.produits.remove(i);
        }
        
    }


    /**
     * Confirme la commande en mettant à jour le statut à CONFIRMEE.
     */
    public void passerCommande() {
        this.statut = StatutCommande.CONFIRMÉE;
    }
    
    /**
    * Vérifie si la commande a été confirmée.
    * Cette méthode retourne un booléen indiquant si la commande a été confirmée ou non,
    * en vérifiant le statut de la commande;
    * 
    * @return true si la réservation est confirmée, false sinon.
    */
   public boolean estConfirmee() {
       return this.statut.equals(StatutCommande.CONFIRMÉE);
   }


    /**
     * Calcule le montant total de la commande en fonction des produits commandés.
     * Cette méthode additionne les prix de tous les produits dans la commande 
     * pour obtenir le coût total de la commande.
     * 
     * @return Le montant total de la commande, basé sur le prix de chaque produit.
     */
    public double calculerMontantTotal() {
        double mt = 0.0;  // Retourne le montant total de la commande
        for(int i=0; i<this.getNbProduitsCommander(); i++)
        {
            mt += this.getProduit(i).getPrix();
        }
        return mt;
    }

    /**
     * Vérifie si tous les produits de la commande sont disponibles en stock.
     * Cette méthode parcourt la liste des produits commandés et vérifie leur disponibilité
     * en fonction de la méthode **`estDisponible()`** de chaque produit.
     * 
     * @return true si tous les produits sont disponibles, false sinon.
     */
    public boolean verifierDisponibilite() {
        boolean bol = true;  // Retourne true si tous les produits sont disponibles
        for(int i=0; i<this.getNbProduitsCommander(); i++)
        {
            if(!BaseDeDonnee.produitDispo(this.getProduit(i).getNom(), this.getProduit(i).getQte()))
            {
                bol = false;
            }
        }
        return bol;
    }
   
    
    /**
     * Fournit une représentation sous forme de chaîne de caractères des informations 
     * concernant la commande, incluant le statut, 
     * et la liste des produits commandés.
     * 
     * @return Une chaîne contenant les détails de la commande.
     */
    @Override
    public String toString() {
        String res = "Total Produit : "+this.getNbProduitsCommander()+" | Statut : "+
                this.statut+"\n";  // Retourner la représentation sous forme de chaîne
        for(int i=0; i<this.getNbProduitsCommander(); i++)
        {
            res += this.getProduit(i).toString() + "\n";
        }
        res += "Prix total : "+this.calculerMontantTotal();
        return res;
    }
}

