/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package predict_your_soulmate;

import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class RegisterController implements Initializable {

    @FXML
    private Button btnReturnFromRegister;
    @FXML
    private TableView<users> table_users;
    @FXML
    private TableColumn<users, Integer> colId;
    @FXML
    private TableColumn<users, String> colName;
    @FXML
    private TableColumn<users, Integer> colAge;
    @FXML
    private TableColumn<users, String> colReligion;
    @FXML
    private TableColumn<users, String> colDivision;
    @FXML
    private TableColumn<users, String> colProfession;
    @FXML
    private TableColumn<users, String> colEmail;
    ObservableList<users> listM;
    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfAge;
    @FXML
    private TextField tfReligion;
    @FXML
    private TextField tfDivision;
    @FXML
    private TextField tfProfession;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfId;

    /**
     * Initializes the controller class.
     * @throws java.io.IOException
     */
    @FXML
    public void handleBtnOfRegister() throws IOException{
        Parent  root  = FXMLLoader.load(getClass().getResource("MainSoulmateDesign.fxml"));
        Stage window = (Stage)btnReturnFromRegister.getScene().getWindow();
        window.setScene(new Scene (root,1236,857));
        
    }
    @FXML
    public void Add_users() throws ClassNotFoundException, SQLException{
        conn = mySQLConnect.ConnectDb();
        String sql ="insert into users (Name,Age,Religion,Division,Profession,Email)values(?,?,?,?,?,?)";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, tfName.getText());
            pst.setString(2, tfAge.getText());
            pst.setString(3, tfReligion.getText());
            pst.setString(4, tfDivision.getText());
            pst.setString(5, tfProfession.getText());
            pst.setString(6, tfEmail.getText());
            pst.execute();
            
            JOptionPane.showMessageDialog(null,"Users add success");
            UpdateTable();
            
            
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    
    
    
    public void UpdateTable(){
        colId.setCellValueFactory(new PropertyValueFactory<users,Integer>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<users,String>("Name"));
        colAge.setCellValueFactory(new PropertyValueFactory<users,Integer>("Age"));
        colReligion.setCellValueFactory(new PropertyValueFactory<users,String>("Religion"));
        colDivision.setCellValueFactory(new PropertyValueFactory<users,String>("Division")); 
        colProfession.setCellValueFactory(new PropertyValueFactory<users,String>("Profession"));
        colEmail.setCellValueFactory(new PropertyValueFactory<users,String>("Email"));
        
        try {
            listM = mySQLConnect.getDatausers();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        table_users.setItems(listM);
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UpdateTable();
        
       
    }  
//method for select users.................
    @FXML
    private void getSelected(javafx.scene.input.MouseEvent event) {
         index = table_users.getSelectionModel().getSelectedIndex();
        if(index<=-1){
            return;
        }
        tfId.setText(colId.getCellData(index).toString());
        tfName.setText(colName.getCellData(index));
        tfAge.setText(colAge.getCellData(index).toString());
        tfReligion.setText(colReligion.getCellData(index));
        tfDivision.setText(colDivision.getCellData(index));
        tfProfession.setText(colProfession.getCellData(index));
        tfEmail.setText(colEmail.getCellData(index));
    }
    
    
    @FXML
     public void Edit(){
        try{
            conn = mySQLConnect.ConnectDb();
            String value1 = tfId.getText();
            String value2 =tfName.getText();
            String value3 = tfAge.getText();
            String value4= tfReligion.getText();
            String value5= tfDivision.getText();
            String value6= tfProfession.getText();
            String value7 = tfEmail.getText();
            
            String sql = "update users set Id ='"+value1+"',Name ='"+value2+"',Age='"+value3+"',Religion ='"+value4+"',Division ='"+value5+"', Profession ='"+value6+"',Email ='"+value7+"'   where Id='"+value1+"'  ";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Update");
            UpdateTable();
            
        }catch(HeadlessException | ClassNotFoundException | SQLException e){
              JOptionPane.showMessageDialog(null, e);
        }
    }
     
    @FXML
     public void Delete() throws ClassNotFoundException, SQLException{
         conn = mySQLConnect.ConnectDb();
         String sql = "delete from users where Id=?";
         try{
             pst = conn.prepareStatement(sql);
             pst.setString(1, tfId.getText());
             pst.execute();
             JOptionPane.showMessageDialog(null, "Delete");
             UpdateTable();
             
         }catch(HeadlessException | SQLException e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
   }    
    

