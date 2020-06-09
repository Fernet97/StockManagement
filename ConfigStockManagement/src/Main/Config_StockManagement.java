/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import UI.AddAdmin;
import UI.AddDB;

import UI.dbcleaner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import others.AES;
import javax.swing.JPasswordField;

/**
 *
 * @author LittleJoke
 */
public class Config_StockManagement extends JPasswordField {

    public static ArrayList<String> array;
    public static String pswd;
    
    ImageIcon img = new ImageIcon((getClass().getResource("/res/img/logo-Icon.png")));

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        File directory = new File("./DATA/");
        if (!directory.mkdir()) {
        }
        File directory2 = new File("./DATA/CONFIG/");
        if (!directory2.mkdir()) {
        }

        AddDB db = new AddDB();
        dbcleaner cleaner = new dbcleaner();
        AddAdmin admin = new AddAdmin();


        Object[] options = {"Configura", "DBCleaner",};
        //JOptionPane.showOptionDialog(null, "Seleziona una tipologia", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
        int i = JOptionPane.showOptionDialog(null, "", "Configura StockManagement", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (i == 0) {
            db.addDB();
        } else if (i == 1) {

            //genera il file
            File file2 = new File("./DATA/CONFIG/ai.aksn");
            if (!file2.createNewFile()) {
            }
            try {
                File file = new File("./DATA/CONFIG/ai.aksn");
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                array = (ArrayList<String>) ois.readObject();

            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(CategoriePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                File file = new File("./DATA/CONFIG/ai.aksn");
//                Logger.getLogger(CategoriePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                //Logger.getLogger(CategoriePanel.class.getName()).log(Level.SEVERE, null, ex);
                array = new ArrayList<>();
            }
            if (array.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nessuna Configurazione disponibile.");

            } else {
                pswd = pass("!!!ATTENZIONE Stai per CANCELLARE IL DB!!! Inserisci password del db", "DBCleaner");
                if (pswd == null)return;
                if (pswd.equals(get())) {
                    pswd = pass("!!!ATTENZIONE, OPERAZIONE IRREVERSIBILE. Stai per CANCELLARE DB!!! Inserisci nuovamente la password", "DBCleaner");
                     if (pswd == null)return;
                    if (pswd.equals(get())) {
                        cleaner.dbCleaner();
                        JOptionPane.showMessageDialog(null, "Ora il DB é vuoto");
                    } else {
                        JOptionPane.showMessageDialog(null, "password errata");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "password errata");
                }
            }
        }
    }

    public static String get() {

        try {
            File file = new File("./DATA/CONFIG/ai.aksn");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            array = (ArrayList<String>) ois.readObject();

        } catch (ClassNotFoundException ex) {
//                Logger.getLogger(CategoriePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            File file = new File("./DATA/CONFIG/ai.aksn");
//                Logger.getLogger(CategoriePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(CategoriePanel.class.getName()).log(Level.SEVERE, null, ex);
            array = new ArrayList<>();
        }
        return AES.decrypt(array.get(4), "miao");
    }

    /**
     *
     * @param desc
     * @param title
     * @return
     */
    public static String pass(String desc, String title) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(desc);
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"Verifica", "Annulla"};
        int i = JOptionPane.showOptionDialog(null, panel, title, JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (i == 0) // pressing OK button
        {
            return pass.getText();
        }
        return null;
    }

}
