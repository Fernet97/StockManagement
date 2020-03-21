/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinedao;

import java.sql.SQLException;
import dao.OrdineDAO;
import beans.Ordine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;


/**
 *
 * @author LittleJoke
 */
public class test2 {

    public static void main(String[] args) throws InterruptedException, SQLException {
            Ordine bean = new Ordine();
            OrdineDAO dao =new OrdineDAO();

            
 
 Collection<String> utenti = dao.getPFr("fr-1");
            
            for(String get : utenti){
              System.out.println("testdaoprint " +get);
            }
          
            
            
            
    }
}

