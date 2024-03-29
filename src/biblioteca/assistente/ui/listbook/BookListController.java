/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.assistente.ui.listbook;

import biblioteca.BasedeDados.BasedeDados;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
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
public class BookListController implements Initializable {
    
    ObservableList<Book> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Book> tableView;
     @FXML
    private TableColumn<Book,String> titleCol;
    @FXML
    private TableColumn<Book,String> idCol;
   
    @FXML
    private TableColumn<Book,String> authorCol;
    @FXML
    private TableColumn<Book,String> publisherCol;
    @FXML
    private TableColumn<Book,Boolean> availabilityCol;
    

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
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));        
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
        
    }

    private void loadData() {
        BasedeDados data = BasedeDados.getInstance();
        String qu = "SELECT * FROM BOOK";
        ResultSet rs = data.execQuery(qu);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String title = rs.getString("title");                               
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                Boolean avail = rs.getBoolean("isAvail");
                
                list.add(new Book(id, title, author, publisher, avail));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookListLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.getItems().setAll(list);
    
    }
   public static class Book{
        private final SimpleStringProperty id;
        private final SimpleStringProperty title;               
        private final SimpleStringProperty author;
        private final SimpleStringProperty publisher;
        private final SimpleBooleanProperty availability;

        public Book(String id, String title,   String author, String pub, Boolean avail) {            
            this.id = new SimpleStringProperty(id);
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(pub);
            this.availability = new SimpleBooleanProperty(avail);
            
        }
        
         public String getId() {
            return id.get();
        }
        
         public String getTitle() {
            return title.get();
        }
                     

        public String getAuthor() {
            return author.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public Boolean getAvailability() {
            return availability.get();
        }
        
        
        
    }
    
}
