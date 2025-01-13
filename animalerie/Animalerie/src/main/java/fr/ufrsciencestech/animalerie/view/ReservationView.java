/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fr.ufrsciencestech.animalerie.view;

import fr.ufrsciencestech.animalerie.controleur.*;
import java.awt.Component;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author delaton
 */
public class ReservationView extends javax.swing.JFrame {

    /**
     * Creates new form ReservationView
     */
    public ReservationView() {
        initComponents();
        btConfirmer.setName("btConfirmer");
        btAnnuler.setName("btAnnuler");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        btGReservation = new javax.swing.ButtonGroup();
        btGDuree = new javax.swing.ButtonGroup();
        btGToil = new javax.swing.ButtonGroup();
        btDate = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        pCenter = new javax.swing.JPanel();
        PanChoice = new javax.swing.JPanel();
        btPromenade = new javax.swing.JRadioButton();
        btGarde = new javax.swing.JRadioButton();
        btToilettage = new javax.swing.JRadioButton();
        btEducation = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jPannel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        PanDuree = new javax.swing.JPanel();
        btTrenteMin = new javax.swing.JRadioButton();
        btUneHeure = new javax.swing.JRadioButton();
        btUneHeureTrente = new javax.swing.JRadioButton();
        btDeuxHeures = new javax.swing.JRadioButton();
        btTroisHeures = new javax.swing.JRadioButton();
        btQuatreHeures = new javax.swing.JRadioButton();
        btJournee = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        PanToilet = new javax.swing.JPanel();
        btToitBase = new javax.swing.JRadioButton();
        btCoupe = new javax.swing.JRadioButton();
        btRasage = new javax.swing.JRadioButton();
        btDouche = new javax.swing.JRadioButton();
        btTonnage = new javax.swing.JRadioButton();
        PanDate = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        PanToilet1 = new javax.swing.JPanel();
        btDemain = new javax.swing.JRadioButton();
        btApDemain = new javax.swing.JRadioButton();
        btOneWeek = new javax.swing.JRadioButton();
        btTwoWeeks = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        btConfirmer = new javax.swing.JButton();
        btAnnuler = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("DejaVu Serif", 3, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Reservation d'un service");
        getContentPane().add(jLabel1, java.awt.BorderLayout.NORTH);

        pCenter.setLayout(new java.awt.GridLayout(1, 3));

        PanChoice.setLayout(new java.awt.GridLayout(4, 1));

        btGReservation.add(btPromenade);
        btPromenade.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btPromenade.setSelected(true);
        btPromenade.setText("Promenade");
        btPromenade.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btPromenade.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btPromenade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPromenadeActionPerformed(evt);
            }
        });
        PanChoice.add(btPromenade);

        btGReservation.add(btGarde);
        btGarde.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btGarde.setText("Garde");
        btGarde.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btGarde.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btGarde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGardeActionPerformed(evt);
            }
        });
        PanChoice.add(btGarde);

        btGReservation.add(btToilettage);
        btToilettage.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btToilettage.setText("Toilettage");
        btToilettage.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btToilettage.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btToilettage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btToilettageActionPerformed(evt);
            }
        });
        PanChoice.add(btToilettage);

        btGReservation.add(btEducation);
        btEducation.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btEducation.setText("Education Canine");
        btEducation.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btEducation.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btEducation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEducationActionPerformed(evt);
            }
        });
        PanChoice.add(btEducation);

        pCenter.add(PanChoice);

        jPanel3.setLayout(new java.awt.GridLayout(2, 1));

        jPannel4.setLayout(new java.awt.GridLayout(2, 1));

        jLabel2.setFont(new java.awt.Font("DejaVu Serif", 2, 17)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Sélectionner la durée");
        jLabel2.setToolTipText("");
        jPannel4.add(jLabel2);

        PanDuree.setToolTipText("");
        PanDuree.setLayout(new java.awt.GridLayout(4, 2));

        btGDuree.add(btTrenteMin);
        btTrenteMin.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btTrenteMin.setSelected(true);
        btTrenteMin.setText("30 minutes");
        btTrenteMin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btTrenteMin.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btTrenteMin.setMargin(new java.awt.Insets(2, 10, 2, 10));
        PanDuree.add(btTrenteMin);

        btGDuree.add(btUneHeure);
        btUneHeure.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btUneHeure.setText("1 heure");
        btUneHeure.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btUneHeure.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btUneHeure.setMargin(new java.awt.Insets(2, 10, 2, 10));
        PanDuree.add(btUneHeure);

        btGDuree.add(btUneHeureTrente);
        btUneHeureTrente.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btUneHeureTrente.setText("1 heure 30 minutes");
        btUneHeureTrente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btUneHeureTrente.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btUneHeureTrente.setMargin(new java.awt.Insets(2, 10, 2, 10));
        
        PanDuree.add(btUneHeureTrente);

        btGDuree.add(btDeuxHeures);
        btDeuxHeures.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btDeuxHeures.setText("2 heures");
        btDeuxHeures.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btDeuxHeures.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btDeuxHeures.setMargin(new java.awt.Insets(2, 10, 2, 10));
        PanDuree.add(btDeuxHeures);

        btGDuree.add(btTroisHeures);
        btTroisHeures.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btTroisHeures.setText("3 heures");
        btTroisHeures.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btTroisHeures.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btTroisHeures.setMargin(new java.awt.Insets(2, 10, 2, 10));
        PanDuree.add(btTroisHeures);

        btGDuree.add(btQuatreHeures);
        btQuatreHeures.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btQuatreHeures.setText("4 heures");
        btQuatreHeures.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btQuatreHeures.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btQuatreHeures.setMargin(new java.awt.Insets(2, 10, 2, 10));
        
        PanDuree.add(btQuatreHeures);

        btGDuree.add(btJournee);
        btJournee.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btJournee.setText("24 heures");
        btJournee.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btJournee.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btJournee.setMargin(new java.awt.Insets(2, 10, 2, 10));
        PanDuree.add(btJournee);

        jPannel4.add(PanDuree);

        jPanel3.add(jPannel4);

        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        jLabel3.setFont(new java.awt.Font("DejaVu Serif", 2, 17)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Personnaliser le pack de toilettage");
        jLabel3.setToolTipText("");
        jPanel5.add(jLabel3);

        PanToilet.setLayout(new java.awt.GridLayout(3, 2));

        btGToil.add(btToitBase);
        btToitBase.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btToitBase.setSelected(true);
        btToitBase.setText("Toilettage de base");
        btToitBase.setEnabled(false);
        btToitBase.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btToitBase.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btToitBase.setMargin(new java.awt.Insets(2, 10, 2, 10));
        PanToilet.add(btToitBase);

        btGToil.add(btCoupe);
        btCoupe.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btCoupe.setText("Coupe et soins des ongles");
        btCoupe.setEnabled(false);
        btCoupe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btCoupe.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btCoupe.setMargin(new java.awt.Insets(2, 10, 2, 10));
        PanToilet.add(btCoupe);

        btGToil.add(btRasage);
        btRasage.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btRasage.setText("Rasage des coussinets");
        btRasage.setEnabled(false);
        btRasage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btRasage.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btRasage.setMargin(new java.awt.Insets(2, 10, 2, 10));
        PanToilet.add(btRasage);

        btGToil.add(btDouche);
        btDouche.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btDouche.setText("Douche");
        btDouche.setEnabled(false);
        btDouche.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btDouche.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btDouche.setMargin(new java.awt.Insets(2, 10, 2, 10));
        PanToilet.add(btDouche);

        btGToil.add(btTonnage);
        btTonnage.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btTonnage.setText("Tonnage complet");
        btTonnage.setEnabled(false);
        btTonnage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btTonnage.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btTonnage.setMargin(new java.awt.Insets(2, 10, 2, 10));
        PanToilet.add(btTonnage);

        jPanel5.add(PanToilet);

        jPanel3.add(jPanel5);

        pCenter.add(jPanel3);

        PanDate.setLayout(new java.awt.GridLayout(2, 1));

        jLabel4.setFont(new java.awt.Font("DejaVu Serif", 2, 17)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Sélectionner la date de la réservation");
        jLabel4.setToolTipText("");
        PanDate.add(jLabel4);

        PanToilet1.setLayout(new java.awt.GridLayout(3, 2));

        btDate.add(btDemain);
        btDemain.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btDemain.setSelected(true);
        btDemain.setText("Demain");
        btDemain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btDemain.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btDemain.setMargin(new java.awt.Insets(2, 10, 2, 10));
        PanToilet1.add(btDemain);

        btDate.add(btApDemain);
        btApDemain.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btApDemain.setText("Après-demain");
        btApDemain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btApDemain.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btApDemain.setMargin(new java.awt.Insets(2, 10, 2, 10));
        
        PanToilet1.add(btApDemain);

        btDate.add(btOneWeek);
        btOneWeek.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btOneWeek.setText("Dans une semaine");
        btOneWeek.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btOneWeek.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btOneWeek.setMargin(new java.awt.Insets(2, 10, 2, 10));
        PanToilet1.add(btOneWeek);

        btDate.add(btTwoWeeks);
        btTwoWeeks.setFont(new java.awt.Font("DejaVu Serif", 2, 16)); // NOI18N
        btTwoWeeks.setText("Dans deux semaines");
        btTwoWeeks.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btTwoWeeks.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btTwoWeeks.setMargin(new java.awt.Insets(2, 10, 2, 10));
        PanToilet1.add(btTwoWeeks);

        PanDate.add(PanToilet1);

        pCenter.add(PanDate);

        getContentPane().add(pCenter, java.awt.BorderLayout.CENTER);

        btConfirmer.setFont(new java.awt.Font("DejaVu Serif", 3, 16)); // NOI18N
        btConfirmer.setText("Ajouter");
        
        btAnnuler.setFont(new java.awt.Font("DejaVu Serif", 3, 16)); // NOI18N
        btAnnuler.setText("Annuler");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(391, Short.MAX_VALUE)
                .addComponent(btConfirmer, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                .addComponent(btAnnuler, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(420, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAnnuler)
                    .addComponent(btConfirmer))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>                        

                                             

    private void btEducationActionPerformed(java.awt.event.ActionEvent evt) {                                            
        setPanelRadioButtonsEnabled(PanDuree, true); // Désactiver les boutons de panToilett
        setPanelRadioButtonsEnabled(PanToilet, false);
        this.btTrenteMin.setEnabled(false);
    }                                           

    private void btToilettageActionPerformed(java.awt.event.ActionEvent evt) {                                             
        setPanelRadioButtonsEnabled(PanDuree, false); // Désactiver les boutons de panToilett
        setPanelRadioButtonsEnabled(PanToilet, true);
    }                                            

    private void btGardeActionPerformed(java.awt.event.ActionEvent evt) {                                        
        setPanelRadioButtonsEnabled(PanDuree, true); // Désactiver les boutons de panToilett
        setPanelRadioButtonsEnabled(PanToilet, false);
        this.btTrenteMin.setEnabled(false);
    }                                       

    private void btPromenadeActionPerformed(java.awt.event.ActionEvent evt) {                                            
        setPanelRadioButtonsEnabled(PanDuree, true); // Désactiver les boutons de panToilett
        setPanelRadioButtonsEnabled(PanToilet, false);
    }                                                                                

    // Méthode utilitaire pour activer ou désactiver les JRadioButtons d'un JPanel
    private void setPanelRadioButtonsEnabled(JPanel panel, boolean enabled) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JRadioButton) {
                comp.setEnabled(enabled);
            }
        }
    }
    
    public ButtonGroup getBtGduree()
    {
        return this.btGDuree;
    }
    public ButtonGroup getBtDate()
    {
        return this.btDate;
    }
    public ButtonGroup getBtGReservation()
    {
        return this.btGReservation;
    }
    public ButtonGroup getBtGDesc()
    {
        return this.btGToil;
    }
    
    public void ajoutControleur(Controleur c)
    {
        this.btConfirmer.addActionListener(c);
        this.btAnnuler.addActionListener(c);
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    

    // Variables declaration - do not modify                     
    private javax.swing.JPanel PanChoice;
    private javax.swing.JPanel PanDate;
    private javax.swing.JPanel PanDuree;
    private javax.swing.JPanel PanToilet;
    private javax.swing.JPanel PanToilet1;
    private javax.swing.JButton btAnnuler;
    private javax.swing.JRadioButton btApDemain;
    private javax.swing.JButton btConfirmer;
    private javax.swing.JRadioButton btCoupe;
    private javax.swing.ButtonGroup btDate;
    private javax.swing.JRadioButton btDemain;
    private javax.swing.JRadioButton btDeuxHeures;
    private javax.swing.JRadioButton btDouche;
    private javax.swing.JRadioButton btEducation;
    private javax.swing.ButtonGroup btGDuree;
    private javax.swing.ButtonGroup btGReservation;
    private javax.swing.ButtonGroup btGToil;
    private javax.swing.JRadioButton btGarde;
    private javax.swing.JRadioButton btJournee;
    private javax.swing.JRadioButton btOneWeek;
    private javax.swing.JRadioButton btPromenade;
    private javax.swing.JRadioButton btQuatreHeures;
    private javax.swing.JRadioButton btRasage;
    private javax.swing.JRadioButton btToilettage;
    private javax.swing.JRadioButton btToitBase;
    private javax.swing.JRadioButton btTonnage;
    private javax.swing.JRadioButton btTrenteMin;
    private javax.swing.JRadioButton btTroisHeures;
    private javax.swing.JRadioButton btTwoWeeks;
    private javax.swing.JRadioButton btUneHeure;
    private javax.swing.JRadioButton btUneHeureTrente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPannel4;
    private javax.swing.JPanel pCenter;
    // End of variables declaration                   
}
