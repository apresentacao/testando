/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.assistente.ui.listmember;

import biblioteca.BasedeDados.BasedeDados;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 *
 * @author tomas
 */
public class MemberListController implements Initializable {
    
    
    ObservableList<Member> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Member> tableView;
    @FXML
    private TableColumn<Member, String> idCol;
    @FXML
    private TableColumn<Member, String>nameCol;
    @FXML
    private TableColumn<Member, String> mobileCol;
    @FXML
    private TableColumn<Member, String> emailCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        
        loadData();
        // TODO
    } 
    private void initCol(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));        
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        
    }
    
    private void loadData() {
        BasedeDados data = BasedeDados.getInstance();
        String qu = "SELECT * FROM MEMBER";
        ResultSet rs = data.execQuery(qu);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");                               
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                
                
                list.add(new Member(id, name, mobile, email));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberListLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.getItems().setAll(list);
    
    }
    
    public static class Member{
        private final SimpleStringProperty id;
        private final SimpleStringProperty name;               
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;
        ;

        public Member(String id, String name, String mobile, String email) {            
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
            
            
        }
        
         public String getId() {
            return id.get();
        }
        
         public String getTitle() {
            return name.get();
        }
                     

        public String getAuthor() {
            return mobile.get();
        }

        public String getPublisher() {
            return email.get();
        }

       
        
        
        
    }
    
}
