/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.assistente.ui.addmember;

import biblioteca.BasedeDados.BasedeDados;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tomas
 */
public class MemberAddController implements Initializable {
    
    BasedeDados data;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton SalvarBtn;
    @FXML
    private JFXButton CancelarBtn;
    
       
     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = BasedeDados.getInstance();
        // TODO
    }    

    @FXML
    private void addmember(ActionEvent event) {
        String mName = name.getText();
        String mId = id.getText();
        String mMobile = mobile.getText();
        String mEmail = email.getText();
        
       
    
       if (mName.isEmpty() || mId.isEmpty() || mMobile.isEmpty() || mEmail.isEmpty()) {      
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setContentText("Preenche todos os campos!");
             alert.showAndWait();
             return;
         }
       String st = "INSERT INTO MEMBER VALUES ("+
                 "'" +mId+"',"+
                 "'" +mName+"',"+                 
                 "'" +mMobile+"',"+
                 "'" +mEmail+"'"+                 
                 ")";
       
       System.out.println(st);
       if(data.execAction(st)){
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
    
}
