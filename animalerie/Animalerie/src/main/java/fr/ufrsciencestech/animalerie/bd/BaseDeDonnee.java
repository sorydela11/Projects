/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.ufrsciencestech.animalerie.bd;

import fr.ufrsciencestech.animalerie.Reservation.StatutReservation;
import fr.ufrsciencestech.animalerie.*;
import fr.ufrsciencestech.animalerie.Toilettage.TypeDeToilettage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

/**
 *
 * @author delaton
 */
public class BaseDeDonnee {
    public static Connection c;
    private static final String BD_url = "jdbc:oracle:thin:@eluard:1521:ENSE2024";
    private static final String username = "id657893";
    private static final String password = "id657893";
    private static final String filePath = "/home/delaton/Prog/GenieLog/Animalerie/animalerie/Base_de_donnee.sql"; 
    
    private static Connection connectToBD() throws SQLException
    {
        return DriverManager.getConnection(BD_url, username, password);
    }
    
    public static boolean connexionBD()
    {
        boolean bol = false;
        try{
          BaseDeDonnee.c = BaseDeDonnee.connectToBD();
          System.out.println("Connexion réussi");
          bol = true;
        }
        catch(SQLException e)
        {
            System.out.println("Échec connexion à la base de donnée");
            //e.printStackTrace();
        }
        return bol;
    }
    
    
    // Méthode pour exécuter un fichier SQL
    public static void executeSQLFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
            Statement statement = c.createStatement()) {
            
            String line;
            StringBuilder sql = new StringBuilder();

            // Lire le fichier ligne par ligne
            while ((line = br.readLine()) != null) {
                // Ignorer les commentaires ou lignes vides
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--") || line.startsWith("#")) {
                    continue;
                }

                // Ajouter la ligne à la commande SQL
                sql.append(line).append(" ");

                // Exécuter la commande si elle se termine par un point-virgule
                if (line.endsWith(";")) {
                    String command = sql.toString().replace(";", "").trim(); // Supprimer le point-virgule
                    statement.execute(command);
                    sql.setLength(0); // Réinitialiser pour la prochaine commande
                }
            }

                System.out.println("Script SQL exécuté avec succès !");
        } catch (IOException | SQLException e) {
            System.out.println("Erreur lors de l'exécution du fichier SQL.");
            //e.printStackTrace();
        }
    }
    
    public static boolean userExist(String mail, String passWord)
    {
        boolean bol = false;
        try{
            String req = "SELECT COUNT(*) AS total FROM CLIENT WHERE mail = ? AND mot_de_passe = ?";
            PreparedStatement ppState = c.prepareStatement(req);
            ppState.setString(1, mail);
            ppState.setString(2, passWord);
            
            ResultSet res = ppState.executeQuery();
            
            if(res.next())
            {
                int count = res.getInt("total");
                if(count > 0)
                {
                    bol = true;
                }
            }
            
            res.close();
            ppState.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
         
        return bol;
    }
    
    public static boolean createUser(String nom, String prenom, String mail, String pass)
    {
        boolean bol = false;
        try{
            
            String req = "INSERT INTO CLIENT (nom, prenom, mail, mot_de_passe) VALUES (?, ?, ?, ?)";
            PreparedStatement ppState = c.prepareStatement(req);
            ppState.setString(1, nom);
            ppState.setString(2, prenom);
            ppState.setString(3, mail);
            ppState.setString(4, pass);
            
            int res = ppState.executeUpdate();
            System.out.println("Lignes affectées : " + res);
            // Si au moins une ligne est affectée, l'insertion a réussi
            if (res > 0) {
                bol = true;
            }
            ppState.close();
            
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return bol;
    }
    
    public static boolean deleteUser(String mail)
    {
        boolean bol = false;
        try{
            
            String req = "DELETE FROM CLIENT WHERE mail = ?";
            PreparedStatement ppState = c.prepareStatement(req);
            ppState.setString(1, mail);
            
            int res = ppState.executeUpdate();
            // Si au moins une ligne est affectée, l'insertion a réussi
            if (res > 0) {
                bol = true;
            }
            ppState.close();
            
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return bol;
    }
    
    public static int getId_client(String mail)
    {
        int id = -1;
        try{
            String rid = "SELECT id_client FROM CLIENT WHERE mail = ?";
            PreparedStatement ppRes = c.prepareStatement(rid);
            ppRes.setString(1, mail);
            
            ResultSet res = ppRes.executeQuery();
            
            if(res.next())
            {
               id = res.getInt("id_client");
            }
            
            res.close();
            ppRes.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }

        return id;
    }
    
    public static int getId_service(String nom_s, String duree, String descr, double p, int id_reserv)
    {
        int id = -1;
        String rid = "";
        PreparedStatement ppRes = null;
        try{
            if(duree == null )
            {
               rid = "SELECT id_service FROM SERVICE_P WHERE nom_s = ? AND descr = ? AND prix_s = ? AND id_reservation = ?";
               ppRes = c.prepareStatement(rid);
               ppRes.setString(1, nom_s);
               ppRes.setString(2, descr);
               ppRes.setDouble(3, p);
               ppRes.setInt(4, id_reserv);
            }
            else
            {
               rid = "SELECT id_service FROM SERVICE_P WHERE nom_s = ? AND duree = ? AND prix_s = ? AND id_reservation = ?"; 
               ppRes = c.prepareStatement(rid);
               ppRes.setString(1, nom_s);
               ppRes.setString(2, duree);
               ppRes.setDouble(3, p);
               ppRes.setInt(4, id_reserv);
            }
            
            ResultSet res = ppRes.executeQuery();
            
            if(res.next())
            {
               id = res.getInt("id_service");
            }
            
            res.close();
            ppRes.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return id;
    }
    
    public static int getId_reservation(LocalDate date,  StatutReservation statut, int id_client)
    {
        int id = -1;
        try{
            String rid = "SELECT id_reservation FROM RESERVATION WHERE statut_r = ? AND date_r = ? AND id_client = ?";
            PreparedStatement ppRes = c.prepareStatement(rid);
            ppRes.setString(1, statut.name());
            ppRes.setDate(2, Date.valueOf(date));
            ppRes.setInt(3, id_client);
            
            ResultSet res = ppRes.executeQuery();
            
            if(res.next())
            {
               id = res.getInt("id_reservation");
            }
            
            res.close();
            ppRes.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return id;
    }
    
    public static int getId_Produit_Stock(String name)
    {
        int id = -1;
        try{
            String rid = "SELECT id_produit FROM PRODUIT_STOCK WHERE nom_p = ?";
            PreparedStatement ppRes = c.prepareStatement(rid);
            ppRes.setString(1, name);
            
            ResultSet res = ppRes.executeQuery();
            
            if(res.next())
            {
               id = res.getInt("id_produit");
            }
            
            res.close();
            ppRes.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return id;
    }
    
    public static int getId_Produit_Acheter(String name, int id_commande)
    {
        int id = -1;
        try{
            String rid = "SELECT id_produit FROM PRODUIT_ACHETER WHERE nom_p = ? AND id_commande = ?";
            PreparedStatement ppRes = c.prepareStatement(rid);
            ppRes.setString(1, name);
            ppRes.setInt(2, id_commande);
            
            ResultSet res = ppRes.executeQuery();
            
            if(res.next())
            {
               id = res.getInt("id_produit");
            }
            
            res.close();
            ppRes.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return id;
    }
    
    public static int getId_Commande(int id_client, String statut)
    {
        int id = -1;
        try{
            String rid = "SELECT id_commande FROM COMMANDE WHERE statut_c = ? AND id_client = ?";
            PreparedStatement ppRes = c.prepareStatement(rid);
            ppRes.setString(1, statut);
            ppRes.setInt(2, id_client);
            
            ResultSet res = ppRes.executeQuery();
            
            if(res.next())
            {
               id = res.getInt("id_commande");
            }
            
            res.close();
            ppRes.close();
        }
        catch(SQLException e)
        {
           // e.printStackTrace();
        }
        
        return id;
    }
    
    public static boolean addAnimal(int id_client, String name, double p, int age, String espece)
    {
        boolean bol = false;
        
        try{
            String reqAdAn = "INSERT INTO ANIMAL(nom_a, poids, age, espece, id_client) VALUES(?, ?, ?, ?, ?)";
            
            PreparedStatement ppAdd = c.prepareStatement(reqAdAn);
            
            ppAdd.setString(1, name);
            ppAdd.setDouble(2, p);
            ppAdd.setInt(3, age);
            ppAdd.setString(4, espece);
            ppAdd.setInt(5, id_client);
            
            int add = ppAdd.executeUpdate();
            if (add > 0) {
                bol = true;
            }
            
            ppAdd.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return bol;
    }     
    
    public static boolean addService(String nom_s, double prix, String duree, String descr, int id_reserv)
    {
        boolean bol = false;
        
        try{
            String req = "INSERT INTO SERVICE_P (nom_s, prix_s, duree, descr, id_reservation) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement ppState = c.prepareStatement(req);
            ppState.setString(1, nom_s);
            ppState.setDouble(2, prix);
            ppState.setString(3, duree);
            ppState.setString(4, descr);
            ppState.setInt(5, id_reserv);
            
            int res = ppState.executeUpdate();
            if(res > 0)
            {
                bol = true;
            }
            
            ppState.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return bol;
    }
    
    public static boolean addReservation(int id, int id_client, String stat_r, Date d)
    {
        boolean bol = false;
        
        try{
            String req = "INSERT INTO RESERVATION VALUES(?, ?, ?, ?)";
            PreparedStatement ppState = c.prepareStatement(req);
            
            ppState.setInt(1, id);
            ppState.setString(2, stat_r);
            ppState.setDate(3, d);
            ppState.setInt(4, id_client);

            int add = ppState.executeUpdate();
            if(add > 0)
            {
                bol = true;
            } 
            
            ppState.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return bol;
    }
    
    public static boolean produitDispo(String name, int quantite)
    {
        boolean bol = false;
        try{
            String req = "SELECT COUNT(*) AS total FROM PRODUIT_STOCK WHERE nom_p = ? AND ? <= stock";
            PreparedStatement ppState = c.prepareStatement(req);
            ppState.setString(1, name);
            ppState.setInt(2, quantite);
            
            ResultSet res = ppState.executeQuery();
            
            if(res.next())
            {
                int count = res.getInt("total");
                if(count > 0)
                {
                    bol = true;
                }
            }
            
            res.close();
            ppState.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return bol;
    }
   
    public static double getPrixproduit(String name_p)
    {
        double res = -1;
        try{
            String req = "SELECT prix_p FROM PRODUIT_STOCK WHERE nom_p = ?";
            PreparedStatement ppState = c.prepareStatement(req);
            ppState.setString(1, name_p);
            
            ResultSet price = ppState.executeQuery();
            
            if(price.next())
            {
                res = price.getDouble("prix_p");
            }
            
            price.close();
            ppState.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return res;
    }
    
    public static boolean updateProduitStock(int indicator, String nom_p, int qte)
    {
        boolean bol = false;
        try{
            String reqAncStock = "SELECT stock FROM PRODUIT_STOCK WHERE nom_p = ?";
            String req = "UPDATE PRODUIT_STOCK SET stock = ? WHERE nom_p = ?";
            PreparedStatement ppState = c.prepareStatement(req);
            PreparedStatement ppAnc = c.prepareStatement(reqAncStock);
            
            ppAnc.setString(1, nom_p);
            
            ResultSet res = ppAnc.executeQuery();
            if(res.next())
            {
                int stock = res.getInt("stock");
                
                if(indicator == 1)
                {
                    ppState.setInt(1, (stock-qte));
                    ppState.setString(2, nom_p);
                }
                else
                {
                    ppState.setInt(1, (stock+qte));
                    ppState.setString(2, nom_p);
                }
                
                
                int mod = ppState.executeUpdate();
                if(mod > 0)
                {
                    bol = true;
                } 
            }
            
            res.close();
            ppState.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return bol;
    }
    
    public static boolean updateProduitAcheter(int id_produit, int qte, double nprix, int id_commande)
    {
        boolean bol = false;
        try{
            String reqAncStock = "SELECT qteChoisie, prix_p FROM PRODUIT_ACHETER WHERE id_produit = ? and id_commande = ?";
            String req = "UPDATE PRODUIT_ACHETER SET qteChoisie = ?, prix_p = ? WHERE id_produit = ? AND id_commande = ?";
            PreparedStatement ppState = c.prepareStatement(req);
            PreparedStatement ppAnc = c.prepareStatement(reqAncStock);
            
            ppAnc.setInt(1, id_produit);
            ppAnc.setInt(2, id_commande);
            
            ResultSet res = ppAnc.executeQuery();
            if(res.next())
            {
                int qteChoisie = res.getInt("qteChoisie");
                double prix_anc = res.getDouble("prix_p");
                
                ppState.setInt(1, (qteChoisie + qte));
                ppState.setDouble(2, (nprix + prix_anc));
                ppState.setInt(3, id_produit);
                ppState.setInt(4, id_commande);
                
                int mod = ppState.executeUpdate();
                if(mod > 0)
                {
                    bol = true;
                } 
            }
            
            res.close();
            ppState.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return bol;
    }
    
    public static boolean addProduit(String nom_p, int qteChoisie, double prix, int id_command)
    {
        boolean bol = false;
        
        try{
            String req = "INSERT INTO PRODUIT_ACHETER(nom_p, qteChoisie, prix_p, id_commande) VALUES(?, ?, ?, ?)";
            PreparedStatement ppState = c.prepareStatement(req);
            
            ppState.setString(1, nom_p);
            ppState.setInt(2, qteChoisie);
            ppState.setDouble(3, prix);
            ppState.setInt(4, id_command);

            int add = ppState.executeUpdate();
            if(add > 0)
            {
                    bol = true;
            } 
            
            ppState.close();
        }
        catch(SQLException e)
        {
           // e.printStackTrace();
        }
        
        return bol;
    }
   
    public static boolean addCommande(int id, int id_client, String statut_c)
    {
        boolean bol = false;
        
        try{
            String req = "INSERT INTO COMMANDE VALUES(?, ?, ?)";
            PreparedStatement ppState = c.prepareStatement(req);
            
            ppState.setInt(1, id);
            ppState.setString(2, statut_c);
            ppState.setInt(3, id_client);

            int add = ppState.executeUpdate();
            if(add > 0)
            {
                bol = true;
            } 
            
            ppState.close();
        }
        catch(SQLException e)
        {
           // e.printStackTrace();
        }
        
        return bol;
    }
    
    public static User infoUser(String email) {
        User u = null;
        try {
            String req = "SELECT * FROM CLIENT WHERE mail = ?";
            PreparedStatement statement = c.prepareStatement(req);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String mail = rs.getString("mail");
                u = new User(prenom, nom, mail);
            }
            
            rs.close();
            statement.close();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        
        return u;
    }
    
    public static void getAllAnimal(User u)
    {
        //récuperer l'id du client dans la base de données
        int id_client = BaseDeDonnee.getId_client(u.getMail());
        String req = "SELECT * FROM ANIMAL WHERE id_client = ?";
        try
        {
            PreparedStatement pp = c.prepareStatement(req);
            pp.setInt(1, id_client);
            
            ResultSet res = pp.executeQuery();
            
            while(res.next())
            {
                String nom = res.getString("nom_a");
                String espece = res.getString("espece");
                int age = res.getInt("age");
                double p = res.getDouble("poids");
                
                u.addAnimal(new Animal(nom, p, age, espece)); //ajout de l'animal
            }
            
            res.close();
            pp.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
    }
    
    public static void getAllReservation(User u)
    {
        //récuperer l'id du client dans la base de données
        int id_client = BaseDeDonnee.getId_client(u.getMail());
        //requete pour recuperer les services que le user a reserver
        String req = "SELECT * FROM SERVICE_P WHERE id_reservation = ?";
        String reqReserv = "SELECT * FROM RESERVATION WHERE id_client = ?";
        try
        {
            PreparedStatement pp = c.prepareStatement(req);
            PreparedStatement rs = c.prepareStatement(reqReserv);
            rs.setInt(1, id_client);
            
            ResultSet ser = rs.executeQuery();
            
            //pour chaque reservation
            while(ser.next())
            {
                LocalDate date = ser.getDate("date_r").toLocalDate();
                String statut = ser.getString("statut_r");
                Reservation r = new Reservation(date);
                r.setStatut(statut);
                
                int id_reservation = ser.getInt("id_reservation");
                pp.setInt(1, id_reservation);
                ResultSet res = pp.executeQuery();
                //pour chaque service inclut dans cette reservation
                while(res.next())
                {
                    //on recupere les info de ce service
                    String nom_s = res.getString("nom_s");
                    double p = res.getDouble("prix_s");
                    String duree = res.getString("duree");
                    String descr = res.getString("descr");
                    
                    switch(nom_s)
                    {
                        case "Promenade" : r.ajoutService(new Promenade(p,duree)); break;
                        case "Garde" : r.ajoutService(new Garde(p,duree)); break;
                        case "Toilettage" : r.ajoutService(new Toilettage(p, TypeDeToilettage.valueOf(descr))); break;
                        case "Education Canine" : r.ajoutService(new EducationCanine(p,duree)); break;
                    }
                }
                u.addReservation(r);
                res.close();
            }
            ser.close();
            pp.close();
            rs.close();
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
    }
    
    public static void getAllCommande(User u)
    {
        int id_client = BaseDeDonnee.getId_client(u.getMail());
        //requete pour recuperer les PRODUIT que le user a ACHETER
        String req = "SELECT * FROM PRODUIT_STOCK PS INNER JOIN PRODUIT_ACHETER P ON PS.id_produit = P.id_produit WHERE P.id_commande = ?";
        String reqReserv = "SELECT * FROM COMMANDE WHERE id_client = ?";
        try
        {
            PreparedStatement pp = c.prepareStatement(req);
            PreparedStatement rs = c.prepareStatement(reqReserv);
            rs.setInt(1, id_client);
            
            ResultSet ser = rs.executeQuery();
            //pour chaque commmande
            while(ser.next())
            {
                String c = ser.getString("statut_c");
                Commande newc = new Commande();
                newc.setStatut(c);
                
                int id_comm = ser.getInt("id_commande");
                pp.setInt(1, id_comm);
                ResultSet res = pp.executeQuery();
                //pour chaque produit dans cette commande
                while(res.next())
                {
                    //on recupere les info de ce service
                    String nom = res.getString("nom_p");
                    int qte = res.getInt("qteChoisie");
                    double prix = res.getDouble("prix_p");
                    
                    String type = res.getString("type_produit");
                    
                    switch(type)
                    {
                        case "Accessoire" : newc.ajoutProduit(new Accessoire(nom, prix, qte)); break;
                        case "Médicament" : newc.ajoutProduit(new ProduitPhamacetique(nom, prix, qte)); break;
                    }
                }
                u.addCommande(newc);
                res.close();
                
            }
            pp.close();
            rs.close();
        }
        catch(SQLException e)
        {
           // e.printStackTrace();
        }
    }
    
    public static boolean updateStatutReservation(int indicator, int id_reservation)
    {
        boolean bol = false;
        String req = "";
        if(indicator == 1)
        {
            req = "UPDATE RESERVATION SET statut_r = 'CONFIRMÉE' WHERE id_reservation = ?";
        }
        else
        {
            req = "DELETE FROM RESERVATION WHERE id_reservation = ?";
        }
        
        try{
           PreparedStatement ppState = c.prepareStatement(req);
           ppState.setInt(1, id_reservation);
           
           ResultSet res = ppState.executeQuery();
           
           if(res.next())
           {
               bol = true;
           }
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return bol;
    }
    
    public static boolean updateStatutCommande(int indicator, int id_commande)
    {
        boolean bol = false;
        String req = "";
        if(indicator == 1)
        {
            req = "UPDATE COMMANDE SET statut_c = 'CONFIRMÉE' WHERE id_commande = ?";
        }
        else
        {
            req = "DELETE FROM COMMANDE WHERE id_commande = ?";
        }
        
        try{
           PreparedStatement ppState = c.prepareStatement(req);
           ppState.setInt(1, id_commande);
           
           ResultSet res = ppState.executeQuery();
           
           if(res.next())
           {
               bol = true;
           }
        }
        catch(SQLException e)
        {
            //e.printStackTrace();
        }
        
        return bol;
    }
}
