/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import database.DriverManagerConnectionPool;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author Fernet
 */
public class StockManagement {
    
    	
  public static void main(String[] args) {
      

        UIManager.put( "control", new Color( 1, 1, 1) );
        UIManager.put( "info", new Color( 1, 1, 1) );
        UIManager.put( "nimbusBase", new Color( 128, 128, 128));
        UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
        UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
        UIManager.put( "nimbusFocus", new Color(115,164,209) );
        UIManager.put( "nimbusGreen", new Color(176,179,50) );
        UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
        UIManager.put( "nimbusLightBackground",new Color( 128, 128, 128));
        UIManager.put( "nimbusOrange", new Color(191,98,4) );
        UIManager.put( "nimbusRed", new Color(169,46,34) );
        UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) );
        //quando seleziona casella
        UIManager.put( "nimbusSelectionBackground", new Color(169,46,34) );
        UIManager.put( "text",  new Color( 59, 47, 158) );
    
        UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Arial Black", Font.BOLD, 14));
        
        
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to cross-platform
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                // not worth my time
            }}
        
       int s_witdh = Toolkit.getDefaultToolkit().getScreenSize().width;
       int s_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        
        //Crea schermata di Login
        LoginDialog ld=new LoginDialog();
        ld.setLocationRelativeTo(null);
        ld.setResizable(false);
        ld.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ld.setSize(400,450);
        ld.setVisible(true);
        ld.setTitle("Login");
    

  }
}
