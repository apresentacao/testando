/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.assistente.settings;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tomas
 */
public class Preferencias {
    
    public static final String CONFIG_FILE = "config.txt";
    
    int nDaysWithoutFine;
    float finePerDay;
    String username;
    String password;
    
    public Preferencias(){
        nDaysWithoutFine = 14;
        finePerDay = 2;
        username = "admin";
        password = "12345";
        
    }

    public int getnDaysWithoutFine() {
        return nDaysWithoutFine;
    }

    public void setnDaysWithoutFine(int nDaysWithoutFine) {
        this.nDaysWithoutFine = nDaysWithoutFine;
    }

    public float getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(float finePerDay) {
        this.finePerDay = finePerDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public static void initConfig(){
        Writer writer = null;
        
        try {
            Preferencias preferencias = new Preferencias();
             Gson gson = new Gson();
             writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferencias, writer);
       
            
        } catch (IOException ex) {
            Logger.getLogger(Preferencias.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            
            try {
            writer.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Preferencias.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        
    }
    
    public static Preferencias gerPreferencias(){
        Gson gson = new Gson();
        Preferencias preferencias = new Preferencias();
        try {
            
            preferencias = gson.fromJson(new FileReader(CONFIG_FILE), Preferencias.class);
        } catch (FileNotFoundException ex) {
            initConfig();
            Logger.getLogger(Preferencias.class.getName()).log(Level.SEVERE, null, ex);
        }
        return preferencias;
    }
    
    public static void escrevaPreferenciasParaFicheiro(Preferencias preferencias){
        Writer writer = null;
        
        try {
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferencias, writer);
       
            
        } catch (IOException ex) {
            Logger.getLogger(Preferencias.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            
            try {
            writer.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Preferencias.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        
    
        
    }
     
    
}
