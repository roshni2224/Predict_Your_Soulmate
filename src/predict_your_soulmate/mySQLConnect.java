/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package predict_your_soulmate;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class mySQLConnect {
    static String url = "jdbc:mysql://127.0.0.1/register";
    static String username = "root";
    static String password ="";
     Connection conn = null;
     
      public  static Connection ConnectDb() throws ClassNotFoundException, SQLException{
        try{
           //Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection(url,username,password);
        JOptionPane.showMessageDialog(null,"Connection Established");
         return conn;
       
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Connection is not Established");
           return null;
        }
        
    }
      
      public static ObservableList<users> getDatausers() throws ClassNotFoundException, SQLException{
        Connection conn = ConnectDb();
        ObservableList<users>  list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new users (Integer.parseInt(rs.getString("Id")),Integer.parseInt(rs.getString("Age")),rs.getString("Name"),rs.getString("Religion"),rs.getString("Division"),rs.getString("Profession"),rs.getString("Email")));
                
                
                
            }
            
        }catch(NumberFormatException | SQLException e){
            System.out.println(e);
        }
           
        return list;
        
      }
    
}
