/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.assistente.settings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class SettingsController implements Initializable {

    @FXML
    private JFXTextField nDaysWithoutFine;
    @FXML
    private JFXTextField finePerDay;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDefaultValues();
        // TODO
    }    

    @FXML
    private void hSalvarBtnAction(ActionEvent event) {
        
        int ndays = Integer.parseInt(nDaysWithoutFine.getText());
        float fine = Float.parseFloat(finePerDay.getText());
        String uname = username.getText();
        String pass = password.getText();
        
        Preferencias preferencias = Preferencias.gerPreferencias();
        preferencias.setnDaysWithoutFine(ndays);
        preferencias.setFinePerDay(fine);
        preferencias.setUsername(uname);
        preferencias.setPassword(pass);
        
        Preferencias.escrevaPreferenciasParaFicheiro(preferencias);
    }

    @FXML
    private void hCancelarBtnAction(ActionEvent event) {
    }

    private void initDefaultValues() { 
    
        Preferencias preferencias = Preferencias.gerPreferencias();
        nDaysWithoutFine.setText(String.valueOf(preferencias.getnDaysWithoutFine()));
        finePerDay.setText(String.valueOf(preferencias.getFinePerDay()));
        username.setText(String.valueOf(preferencias.getUsername()));
        password.setText(String.valueOf(preferencias.getPassword()));
    }
    
}
