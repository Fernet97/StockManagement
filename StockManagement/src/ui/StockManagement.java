/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import database.DriverManagerConnectionPool;
import javax.swing.JFrame;

/**
 *
 * @author Fernet
 */
public class StockManagement {
    
    	
  public static void main(String[] args) {
      
      System.out.println("****** STOCK MAMAGEMENT*******");
      System.out.println("Check connessione ...");

      // Istanzia il frame Login
      DriverManagerConnectionPool dv = new DriverManagerConnectionPool();
      dv.checkLogin(1, "test", "test");
      //LoginDialog login = new LoginDialog();
      //login.setVisible(true);
      //login.setTitle("Login");
                
    

  }
}
