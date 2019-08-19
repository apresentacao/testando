/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca;

import biblioteca.BasedeDados.BasedeDados;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tomas
 */
public class FXMLDocumentController implements Initializable {

    BasedeDados basededados;
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    @FXML
    private JFXButton SalvarBtn;
    @FXML
    private JFXButton CancelarBtn;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        basededados = BasedeDados.getInstance();
        checkData();
        // TODO
    }    

    @FXML
    private void addbook(ActionEvent event) {
        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();
        
         if (bookID.isEmpty() || bookAuthor.isEmpty() || bookName.isEmpty() || bookPublisher.isEmpty()) {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Preenche todos os campos!");
             alert.showAndWait();
             return;
         }
         
         String qu = "INSERT INTO BOOK VALUES ("+
                 "'" +bookID+"',"+
                 "'" +bookName+"',"+                 
                 "'" +bookAuthor+"',"+
                 "'" +bookPublisher+"',"+
                 ""  +true+""+
                 ")";
         System.out.println(qu);
         if(basededados.execAction(qu)){
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setHeaderText(null);
             alert.setContentText("Sucesso!");
             alert.showAndWait();
             
         }else{
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Falhou!");
             alert.showAndWait();
             
         }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void checkData() {
        String qu = "SELECT title FROM BOOK";
        ResultSet rs = basededados.execQuery(qu);
        try {
            while (rs.next()) {
                String titulo = rs.getString("title");
                System.out.println(titulo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
