/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fr.ufrsciencestech.animalerie.view;
import fr.ufrsciencestech.animalerie.bd.BaseDeDonnee;
import fr.ufrsciencestech.animalerie.controleur.*;
import java.awt.*;
import javax.swing.*;


/**
 *
 * @author delaton
 */
public class View extends javax.swing.JFrame {
    /**
     * Creates new form View
     */
    public View() {
        initComponents();
        this.btServices.setName("Service");
        this.btProduits.setName("Produits");
        this.BtAjoutAnimal.setName("Animal");
        this.btQuit.setName("Quit");
        this.jAnnuleCommand.setName("AnnulerCommande");
        this.jAnnuleReserv.setName("AnnulerReserv");
        this.jConfirmReserv.setName("ConfirmReserv");
        this.jValideCommand.setName("ConfirmCommand");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        PanProfil = new javax.swing.JPanel();
        InfoProfil = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PanNom = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jNom = new javax.swing.JTextField();
        PanPrenom = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPrenom = new javax.swing.JTextField();
        PanMail = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jMail = new javax.swing.JTextField();
        InfoAnimal = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListAnimaux = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        BtAjoutAnimal = new javax.swing.JButton();
        CenterPan = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pAcceuil = new javax.swing.JPanel();
        btProduits = new javax.swing.JButton();
        btServices = new javax.swing.JButton();
        pQuit = new javax.swing.JPanel();
        btQuit = new javax.swing.JButton();
        PanHist = new javax.swing.JPanel();
        jReservation = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListReservation = new javax.swing.JList<>();
        jPanel6 = new javax.swing.JPanel();
        jAnnuleReserv = new javax.swing.JButton();
        jConfirmReserv = new javax.swing.JButton();
        jCommande = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListCommande = new javax.swing.JList<>();
        jPanel10 = new javax.swing.JPanel();
        jValideCommand = new javax.swing.JButton();
        jAnnuleCommand = new javax.swing.JButton();
        jHist = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListHist = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        // Ajout des renderers pour chaque JList
        setListRenderer(jListAnimaux);
        setListRenderer(jListReservation);
        setListRenderer(jListCommande);
        setListRenderer(jListHist);
        
        PanProfil.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanProfil.setLayout(new java.awt.GridLayout(2, 1));

        InfoProfil.setLayout(new java.awt.GridLayout(4, 1));

        jLabel1.setFont(new java.awt.Font("DejaVu Serif", 3, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Profil");
        jLabel1.setToolTipText("");
        InfoProfil.add(jLabel1);

        jLabel3.setFont(new java.awt.Font("DejaVu Serif", 2, 18)); // NOI18N
        jLabel3.setText("Nom :");
        PanNom.add(jLabel3);

        jNom.setFont(new java.awt.Font("DejaVu Serif", 2, 14)); // NOI18N
        jNom.setForeground(new java.awt.Color(102, 102, 0));
        
        PanNom.add(jNom);

        InfoProfil.add(PanNom);

        jLabel5.setFont(new java.awt.Font("DejaVu Serif", 2, 18)); // NOI18N
        jLabel5.setText("Prénom : ");
        PanPrenom.add(jLabel5);

        jPrenom.setFont(new java.awt.Font("DejaVu Serif", 2, 14)); // NOI18N
        jPrenom.setForeground(new java.awt.Color(102, 102, 0));
        PanPrenom.add(jPrenom);

        InfoProfil.add(PanPrenom);

        jLabel6.setFont(new java.awt.Font("DejaVu Serif", 2, 18)); // NOI18N
        jLabel6.setText("Mail : ");
        PanMail.add(jLabel6);

        jMail.setFont(new java.awt.Font("DejaVu Serif", 2, 14)); // NOI18N
        jMail.setForeground(new java.awt.Color(102, 102, 0));
        PanMail.add(jMail);
        
        jNom.setEditable(false);
        jPrenom.setEditable(false);
        jMail.setEditable(false);

        InfoProfil.add(PanMail);

        PanProfil.add(InfoProfil);

        InfoAnimal.setLayout(new java.awt.GridLayout(3, 1));

        jLabel2.setFont(new java.awt.Font("DejaVu Serif", 3, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Mes Animaux");
        InfoAnimal.add(jLabel2);

        jListAnimaux.setFont(new java.awt.Font("DejaVu Serif", 2, 17)); // NOI18N
        jListAnimaux.setForeground(new java.awt.Color(0, 102, 102));
        jScrollPane1.setViewportView(jListAnimaux);

        InfoAnimal.add(jScrollPane1);

        BtAjoutAnimal.setText("Ajouter un animal");
        jPanel4.add(BtAjoutAnimal);

        InfoAnimal.add(jPanel4);

        PanProfil.add(InfoAnimal);

        getContentPane().add(PanProfil, java.awt.BorderLayout.WEST);

        CenterPan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CenterPan.setLayout(new java.awt.GridLayout(3, 1));

        jLabel4.setFont(new java.awt.Font("DejaVu Serif", 3, 28)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Animalerie");
        CenterPan.add(jLabel4);

        btProduits.setText(" Boutique");
        btProduits.setToolTipText("");
        btProduits.setHideActionText(true);

        btServices.setText("Réserver un service");

        javax.swing.GroupLayout pAcceuilLayout = new javax.swing.GroupLayout(pAcceuil);
        pAcceuil.setLayout(pAcceuilLayout);
        pAcceuilLayout.setHorizontalGroup(
            pAcceuilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pAcceuilLayout.createSequentialGroup()
                .addContainerGap(229, Short.MAX_VALUE)
                .addGroup(pAcceuilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btProduits, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                    .addComponent(btServices, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(235, Short.MAX_VALUE))
        );
        pAcceuilLayout.setVerticalGroup(
            pAcceuilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pAcceuilLayout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addComponent(btProduits, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(btServices, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        CenterPan.add(pAcceuil);

        btQuit.setText("Quit");
        javax.swing.GroupLayout pQuitLayout = new javax.swing.GroupLayout(pQuit);
        pQuit.setLayout(pQuitLayout);
        pQuitLayout.setHorizontalGroup(
            pQuitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pQuitLayout.createSequentialGroup()
                .addContainerGap(230, Short.MAX_VALUE)
                .addComponent(btQuit, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(236, Short.MAX_VALUE))
        );
        pQuitLayout.setVerticalGroup(
            pQuitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pQuitLayout.createSequentialGroup()
                .addContainerGap(115, Short.MAX_VALUE)
                .addComponent(btQuit, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
        );

        CenterPan.add(pQuit);

        getContentPane().add(CenterPan, java.awt.BorderLayout.CENTER);

        PanHist.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanHist.setLayout(new java.awt.GridLayout(3, 1));

        jReservation.setLayout(new java.awt.GridLayout(3, 1));

        jLabel7.setFont(new java.awt.Font("DejaVu Sans", 3, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Mes Réservations");
        jLabel7.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jReservation.add(jLabel7);

        jPanel1.setLayout(new java.awt.GridLayout(1, 1));

        jListReservation.setFont(new java.awt.Font("DejaVu Serif", 2, 17)); // NOI18N
        jScrollPane2.setViewportView(jListReservation);

        jPanel1.add(jScrollPane2);

        jReservation.add(jPanel1);

        jAnnuleReserv.setText("Annuler");

        jConfirmReserv.setText("Confirmer");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jConfirmReserv, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jAnnuleReserv, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jConfirmReserv, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jAnnuleReserv, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jReservation.add(jPanel6);

        PanHist.add(jReservation);

        jCommande.setLayout(new java.awt.GridLayout(3, 1));

        jLabel8.setFont(new java.awt.Font("DejaVu Sans", 3, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Mes commandes");
        jCommande.add(jLabel8);

        jPanel9.setLayout(new java.awt.GridLayout(1, 1));

        jListCommande.setFont(new java.awt.Font("DejaVu Serif", 2, 17)); // NOI18N
        jScrollPane4.setViewportView(jListCommande);

        jPanel9.add(jScrollPane4);

        jCommande.add(jPanel9);

        jValideCommand.setText("Valider");

        jAnnuleCommand.setText("Annuler");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jValideCommand, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jAnnuleCommand, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jValideCommand, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jAnnuleCommand, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jCommande.add(jPanel10);

        PanHist.add(jCommande);

        jHist.setLayout(new java.awt.GridLayout(2, 1));

        jLabel9.setFont(new java.awt.Font("DejaVu Sans", 3, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Historiques");
        jHist.add(jLabel9);

        jPanel8.setLayout(new java.awt.GridLayout(1, 1));

        jListHist.setFont(new java.awt.Font("DejaVu Serif", 2, 17)); // NOI18N
        jScrollPane3.setViewportView(jListHist);

        jPanel8.add(jScrollPane3);

        jHist.add(jPanel8);

        PanHist.add(jHist);

        getContentPane().add(PanHist, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>                        

    private void setListRenderer(JList<String> list) {
    list.setCellRenderer(new ListCellRenderer<String>() {
        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
            JTextArea textArea = new JTextArea(value);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setOpaque(true);
            textArea.setFont(new Font("DejaVu Serif", Font.PLAIN, 11));
            textArea.setSize(500, 500);

            // Ajustement des couleurs selon la sélection
            if (isSelected) {
                textArea.setBackground(Color.RED);
                textArea.setForeground(Color.BLACK);
            } else {
                textArea.setBackground(list.getBackground());
                textArea.setForeground(list.getForeground());
            }

            // Retourne la cellule personnalisée
            return textArea;
        }
    });
}
 
    public JList getListReservation()
    {
        return this.jListReservation;
    }
    
    public JList getListCommande()
    {
        return this.jListCommande;
    }
    
    public void setListCommande(JList jCom)
    {
        this.jListCommande = jCom;
    }
    
    public JList getListAnimaux()
    {
        return this.jListAnimaux;
    }
    
    public JList getListHistorique()
    {
        return this.jListHist;
    }
    
    public void setInformation(String nom, String prenom, String mail)
    {
        jNom.setText(nom);
        jPrenom.setText(prenom);
        jMail.setText(mail);
    }
    
    public void ajoutControleur(Controleur c)
    {
        this.BtAjoutAnimal.addActionListener(c);
        this.btProduits.addActionListener(c);
        this.btServices.addActionListener(c);
        this.jAnnuleCommand.addActionListener(c);
        this.jAnnuleReserv.addActionListener(c);
        this.jConfirmReserv.addActionListener(c);
        this.jValideCommand.addActionListener(c);
        this.btQuit.addActionListener(c);
    }
    
   
   
    // Variables declaration - do not modify                     
    private javax.swing.JButton BtAjoutAnimal;
    private javax.swing.JPanel CenterPan;
    private javax.swing.JPanel InfoAnimal;
    private javax.swing.JPanel InfoProfil;
    private javax.swing.JPanel PanHist;
    private javax.swing.JPanel PanMail;
    private javax.swing.JPanel PanNom;
    private javax.swing.JPanel PanPrenom;
    private javax.swing.JPanel PanProfil;
    private javax.swing.JButton btProduits;
    private javax.swing.JButton btQuit;
    private javax.swing.JButton btServices;
    private javax.swing.JButton jAnnuleCommand;
    private javax.swing.JButton jAnnuleReserv;
    private javax.swing.JPanel jCommande;
    private javax.swing.JButton jConfirmReserv;
    private javax.swing.JPanel jHist;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jListAnimaux;
    private javax.swing.JList<String> jListCommande;
    private javax.swing.JList<String> jListHist;
    private javax.swing.JList<String> jListReservation;
    private javax.swing.JTextField jMail;
    private javax.swing.JTextField jNom;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField jPrenom;
    private javax.swing.JPanel jReservation;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton jValideCommand;
    private javax.swing.JPanel pAcceuil;
    private javax.swing.JPanel pQuit;
    // End of variables declaration                   
}
