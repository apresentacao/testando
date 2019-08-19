package biblioteca.BasedeDados;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author tomas
 */
public class BasedeDados {
    
    private static BasedeDados dados=null;
    
    private static final String DB_URL = "jdbc:derby:basededados;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;
    
    
    private BasedeDados(){
        createConnection();
        setupBookTable();
        setupMemberTable();
        setupIssueTable();
    }
    
    public static BasedeDados getInstance() 
    {
     if (dados == null){
         dados = new BasedeDados();
     }   
     return dados;
    }
    
    void createConnection(){
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
            } catch (Exception e){
                e.printStackTrace();
            }
    }
    // Tabela de Adicionar novos Livros
    void setupBookTable(){
        String TABLE_NAME = "BOOK";
        try{
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if(tables.next()){
                System.out.println("Tabela "+TABLE_NAME+" já existe!");
            } else{
                stmt.execute("CREATE TABLE "+TABLE_NAME+"("
                        + "    id varchar(200) primary key,\n"
                        + "    title varchar(200),\n"
                        + "    author varchar(200),\n"
                        + "    publisher varchar(200),\n"
                        + "    isAvail boolean default true"
                        + ")");
            }
        }catch (SQLException e){
            System.err.println(e.getMessage()+"...setupDatabase");
        }finally{
            
        }
    }
    
    // Tabela de Adicionar novos Membros
    void setupMemberTable() {
        String TABLE_NAME = "MEMBER";
        try{
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if(tables.next()){
                System.out.println("Tabela "+TABLE_NAME+" já existe!");
            } else{
                stmt.execute("CREATE TABLE "+TABLE_NAME+"("
                        + "    id varchar(200) primary key,\n"
                        + "    name varchar(200),\n"
                        + "    mobile varchar(20),\n"
                        + "    email varchar(100)\n"                        
                        + ")");
            }
        }catch (SQLException ex){
            System.err.println(ex.getMessage()+"...setupDatabase");
        }finally{
            
        }
    
    }
    // Tabela de adicionar pedidos ou solicitacao de livros
    void setupIssueTable(){
        String TABLE_NAME = "ISSUE";
        try{
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            
            if(tables.next()){
                System.out.println("Tabela "+TABLE_NAME+" já existe!");
                
            }else {
                stmt.execute("CREATE TABLE "+TABLE_NAME+"("
                +" bookId varchar (200) primary key,\n"
                +" memberId varchar (200),\n"
                +" issueTime timestamp default CURRENT_TIMESTAMP,\n"
                +" renew_count integer default 0,\n"
                        //chave estrangeira da tabela livro
                +" FOREIGN KEY (bookId) REFERENCES BOOK(id),\n"
                        //chave estrangeira da tabela membro
                +" FOREIGN KEY (memberId) REFERENCES MEMBER(id)"                      
                +" )");
            }
        }catch (SQLException e){
            System.err.println(e.getMessage()+" ...setupDatabase");
            
        } finally {
            
        }
    }
    
    public ResultSet execQuery(String query){
        ResultSet result;
        try{
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex){
            System.out.println("Exceção na query:dados"+ex.getLocalizedMessage());
            return null;
        }finally{
            
        }
        return result;
    }
    
    
    public boolean execAction(String qu){
        try{
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null," Erro: "+ex.getMessage()," Erro Ocorrido ",JOptionPane.ERROR_MESSAGE);
            System.out.println("Exceção na query:dados"+ex.getLocalizedMessage());
            return false;
        }finally{
            
        }
    }

     
    
}