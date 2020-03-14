package userdao;

import beans.Utente;
import dao.UtenteDAO;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LittleJoke
 */
public class TestDAOUserGet {
     public static void main(String[] args) throws InterruptedException, SQLException {
         
            try {    
     
         System.out.println("utente");
         System.out.println("/*getAll*/");
           java.util.concurrent.TimeUnit.SECONDS.sleep(3);
           UtenteDAO  dao = new UtenteDAO();
           Collection<Utente> utenti = dao.getAll();
            
            for(Utente getall : utenti){
              System.out.println("testdaoprint " +getall.getIdutente());
            }
            
            
            UtenteDAO dao2 = new UtenteDAO();
                Utente byid = dao2.getByID("g.bambino1");
                System.out.println("getbyid "+byid.getTelefono());
                
     
            }
                catch (SQLException ex) {
                    Logger.getLogger(TestDAOUserGet.class.getName()).log(Level.SEVERE, null, ex);
                    }
     }
}
            
        
     
          
