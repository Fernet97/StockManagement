/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Utente;
import others.JavaProcessId;
import database.DriverManagerConnectionPool;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author Fernet
 */
public class StockManagement {

        private static Logger logger = Logger.getLogger("genlog");
        private static FileHandler fh;
     

    
    public static void main(String[] args) throws InterruptedException {

        JavaProcessId.jPID();

        // CREA CARTELLA DI ROOT DEI DATI
        File directory = new File("./DATA/");
        if (!directory.mkdir()) {
        }

        // CREA CARTELLA QRCODE
        File directory1 = new File("./DATA/QRCODE/");
        if (!directory1.mkdir()) {
        }

        // CREA CARTELLA IMG
        File directory2 = new File("./DATA/IMG/");
        if (!directory2.mkdir()) {
        }

        // CREA CARTELLA LOG
        File directory3 = new File("./DATA/LOG/");
        if (!directory3.mkdir()) {
        }

        // CREA CARTELLA USERLOG
        File directory4 = new File("./DATA/LOG/USERLOG/");
        if (!directory4.mkdir()) {
            
        }

        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
            LocalDateTime now = LocalDateTime.now();

            String datanow = dtf.format(now);

            // This block configure the logger with handler and formatter  
            fh = new FileHandler("./DATA/LOG/LOG_" + datanow + ".log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);



        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        


        UIManager.put("control", new Color(27, 32, 36));
        UIManager.put("info", new Color(40, 45, 51));
        UIManager.put("nimbusBase", new Color(40, 45, 51));
        UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
        UIManager.put("nimbusDisabledText", Color.YELLOW);
        UIManager.put("nimbusFocus", new Color(115, 164, 209));
        UIManager.put("nimbusGreen", new Color(176, 179, 50));
        UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
        UIManager.put("nimbusLightBackground", new Color(128, 128, 128));
        UIManager.put("nimbusOrange", new Color(191, 98, 4));
        UIManager.put("nimbusRed", new Color(169, 46, 34));
        UIManager.put("nimbusSelectedText", Color.white);
        //quando seleziona casella
        UIManager.put("nimbusSelectionBackground", Color.DARK_GRAY);
        UIManager.put("text", Color.white);

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
            }
        }

        int s_witdh = Toolkit.getDefaultToolkit().getScreenSize().width;
        int s_height = Toolkit.getDefaultToolkit().getScreenSize().height;

        //Crea schermata di Login
        LoginDialog ld = new LoginDialog();
        ld.setLocationRelativeTo(null);
        ld.setResizable(false);
        ld.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ld.setSize(400, 450);
        ld.setVisible(true);
        ld.setTitle("Login");

    }
    
    public static void closeFH() throws FileNotFoundException {
        fh.close();  
        LoginDialog.fhu.close();
    }


}
