/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.assistente.ui.menu;

import biblioteca.BasedeDados.BasedeDados;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author tomas
 */
public class MenuController implements Initializable {

    @FXML
    private HBox book_info;
    @FXML
    private TextField bookIdInput;
    @FXML
    private Text bookName;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text bookStatus;
    @FXML
    private HBox member_info;
    
    BasedeDados basededados;
    @FXML
    private TextField memberIdInput;
    @FXML
    private Text memberName;
    @FXML
    private Text memberMobile;
    @FXML
    private JFXTextField bookID;
    @FXML
    private ListView<String> issueDataList;
    
    Boolean isReadyForSubmission = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         JFXDepthManager.setDepth(book_info, 1);
         JFXDepthManager.setDepth(member_info, 1);
         
         basededados = BasedeDados.getInstance();
        // TODO
    }    

    @FXML
    private void loadAddMember(ActionEvent event) {
        loadWindow("/biblioteca/assistente/ui/addmember/member_add.fxml", "Adicionar Membro");
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
        loadWindow("/biblioteca/assistente/ui/addbook/FXMLDocument.fxml", "Adicionar Livro");
    }

    @FXML
    private void loadbookTable(ActionEvent event) {
        loadWindow("/biblioteca/assistente/ui/listbook/book_list.fxml", "Listar todos Livros");
        
    }

    @FXML
    private void loadMemberTable(ActionEvent event) {
        loadWindow("/biblioteca/assistente/ui/listmember/member_list.fxml", "Listar todos Membros");
    }
    
    void loadWindow(String loc, String title){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void loadBookInfo(ActionEvent event) {
        clearBookCache();
        
        String id = bookIdInput.getText();
        String qu = "SELECT * FROM BOOK WHERE id = '"+id+"'";
        ResultSet rs = basededados.execQuery(qu);
        Boolean flag = false;
        
        try {
            while (rs.next()){
                
                String bName = rs.getString("title");
                String bAuthor = rs.getString("author");
                Boolean bStatus = rs.getBoolean("isAvail");
                
                bookName.setText(bName);
                bookAuthor.setText(bAuthor);
                String status = (bStatus)?"Disponível" : "Indisponínel";
                bookStatus.setText(status);
                flag = true;
             
            }
            
            if(!flag){
                bookName.setText(" Livro  inexistente no sistema");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    void clearBookCache(){
        bookName.setText("");
        bookAuthor.setText("");
        bookStatus.setText("");
        
    }
    
    
    

    @FXML
    private void loadMemberkInfo(ActionEvent event) {
        clearMemberCache();
        
        String id = memberIdInput.getText();
        String qu = "SELECT * FROM MEMBER WHERE id = '"+id+"'";
        ResultSet rs = basededados.execQuery(qu);
        Boolean flag = false;
        
        try {
            while (rs.next()){
                
                String mName = rs.getString("name");
                String mMobile = rs.getString("mobile");
                
                
                memberName.setText(mName);
                memberMobile.setText(mMobile);
                              
                flag = true;
             
            }
            
            if(!flag){
                memberName.setText(" Membro não cadastrado.");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    void clearMemberCache(){
        memberName.setText("");
        memberMobile.setText("");
    }
//Operacao de ler dados do livro com codigo
    @FXML
    private void loadIssueOperation(ActionEvent event) {
        String memberId = memberIdInput.getText();
        String bookId = bookIdInput.getText();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar a solicitação");
        alert.setHeaderText(null);
        alert.setContentText("Tem certeza que quer atribuir o manual "+bookName.getText()+ 
                "\n para "+memberName.getText()+" ?");
        
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get()==ButtonType.OK){
            String str = "INSERT INTO ISSUE (memberId,bookId) VALUES ("
                    + "'"+memberId+"',"
                    + "'"+bookId+"')";
            String str2 = "UPDATE BOOK SET isAvail = false WHERE id = '"+bookId+"'";
            System.out.println(str+" e "+str2);
            
            if(basededados.execAction(str)&&basededados.execAction(str2)){
                
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Sucesso!");
                alert1.setHeaderText(null);
                alert1.setContentText("Manual entregue com Sucesso!");
                alert1.showAndWait();
                
            }else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                 alert1.setTitle("Falhou!");
                 alert1.setHeaderText(null);
                 alert1.setContentText("Manual não entregue!");
                 alert1.showAndWait();
                
            }
                    
            
        } else{
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Cancelado!");
                alert1.setHeaderText(null);
                alert1.setContentText("Operação cancelada!");
                alert1.showAndWait();
            
        }
        
    }
// Informacao do campo devolucao e renovar pedido.
    @FXML
    private void loadBookInfo2(ActionEvent event) {
        
        ObservableList<String> issueData = FXCollections.observableArrayList();
        
        isReadyForSubmission = false;
                
        String id = bookID.getText();
        String qu = "SELECT * FROM ISSUE WHERE bookId = '"+id+"'";
        ResultSet rs = basededados.execQuery(qu);
        try{
            while (rs.next()){
                String mBookID = id;
                String mMemberID = rs.getString("memberId");
                Timestamp mIssueTime = rs.getTimestamp("issueTime");
                int mRenewCount = rs.getInt("renew_count");
                
                //informacao da data e hora de solicitacao do livro
                
                issueData.add("Data e Hora de Solicitação : "+mIssueTime.getHours()+":"+(mIssueTime.getMinutes()));
                issueData.add("Contador de Solicitação : "+mRenewCount);
                
                //Informacao do livro a visualizar para devolucao do livro
                issueData.add("");
                issueData.add("Informação do Livro: ");
                qu = "SELECT * FROM BOOK WHERE ID = '"+mBookID+"'";
                ResultSet r1 = basededados.execQuery(qu);
                while(r1.next()){
                    
                    issueData.add("");
                    issueData.add("\tCódigo do Livro: "+r1.getString("id"));
                    issueData.add("\tTítulo do Livro: "+r1.getString("title"));
                    issueData.add("\tAutor do Livro: "+r1.getString("author"));
                    issueData.add("\tAno de Publicação: "+r1.getString("publisher"));
                    
                }
                // Informacao do membro a vizsualizar para devolucao do livro
                 issueData.add("");
                 issueData.add("Informação do Membro: ");
                qu = "SELECT * FROM MEMBER WHERE ID = '"+mMemberID+"'";
                r1 = basededados.execQuery(qu);
                while(r1.next()){
                    
                    issueData.add("");                    
                    issueData.add("\tNome do Membro: "+r1.getString("name"));
                    issueData.add("\tContacto do Membro: "+r1.getString("mobile"));
                    issueData.add("\tEmail do Membro: "+r1.getString("email"));
                }
                isReadyForSubmission = true;
            }
            
        }catch(SQLException ex){
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        issueDataList.getItems().setAll(issueData);
        
    }
// Devolucao do livro
    @FXML
    private void loadSubmissionOp(ActionEvent event) {
        if(!isReadyForSubmission){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Falhou!");
            alert.setHeaderText(null);
            alert.setContentText("Selecione o Livro a devolver!");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar a solicitação");
        alert.setHeaderText(null);
        alert.setContentText("Tem certeza que quer devolver o Manual? ");
        
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get()==ButtonType.OK){
        String id = bookID.getText();
        String ac1 = "DELETE FROM ISSUE WHERE BOOKID = '"+id+"'";
        String ac2 = "UPDATE BOOK SET ISAVAIL = TRUE WHERE ID = '"+id+"'";
        
        if(basededados.execAction(ac1)&&basededados.execAction(ac2)){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Sucesso!");
            alert1.setHeaderText(null);
            alert1.setContentText("Livro devolvido!");
            alert1.showAndWait();
            
        }else{
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Falhou!");
            alert1.setHeaderText(null);
            alert1.setContentText("Devolução do Livro falhou!");
            alert1.showAndWait();
            
        }
        
    }else{
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Cancelada!");
            alert1.setHeaderText(null);
            alert1.setContentText("Operação cancelada!");
            alert1.showAndWait();
            
        }
    }

    @FXML
    private void loadRenewOp(ActionEvent event) {
        
           if(!isReadyForSubmission){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Falhou!");
            alert.setHeaderText(null);
            alert.setContentText("Selecione o Livro a solicitar!");
            alert.showAndWait();
            return;
        }
           
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar renovação!");
        alert.setHeaderText(null);
        alert.setContentText("Tem certeza que quer renovar o pedido? ");
        
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get()==ButtonType.OK){
            String ac = "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP, renew_count = renew_count+1 WHERE BOOKID = '"+bookID.getText()+" '";
            System.out.println(ac);
            if(basededados.execAction(ac)){
             Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Sucesso!");
            alert1.setHeaderText(null);
            alert1.setContentText("Pedido de Livro renovado!");
            alert1.showAndWait();
                
            }else{
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Falhou!");
            alert1.setHeaderText(null);
            alert1.setContentText("O Pedido Falhou!");
            alert1.showAndWait();
            
                
            }
        }else{
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Cancelada!");
            alert1.setHeaderText(null);
            alert1.setContentText("Operação cancelada!");
            alert1.showAndWait();
        }
        
    }
    
}
