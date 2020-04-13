package UI;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import others.AES;

/**
 *
 * @author LittleJoke
 */
public class AddDB {

    public JTextField ip;
    private JTextField port;
    private JTextField dbname;
    private JTextField username;
    private JPasswordField passw;
    private JPasswordField passw2;
    public ArrayList<String> array;

    public void addDB() throws SQLException, IOException {

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
         
if (array.isEmpty()){
        JFrame initiate = new JFrame();
        initiate.setLocationRelativeTo(null);
        initiate.setResizable(false);
        initiate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initiate.setSize(600, 260);
        initiate.setLocationRelativeTo(null);  // CENTRA 
        initiate.setTitle("DB configure");

        JPanel centerpanel = new JPanel();
        centerpanel.setLayout(new GridLayout(4, 3));

        JPanel panelement = new JPanel();
        panelement.add(new JLabel("     Indirizzo   "));
        ip = new JTextField(20);
        panelement.add(ip);
        centerpanel.add(panelement);

        JPanel panelement1 = new JPanel();
        panelement1.add(new JLabel("        Porta   "));
        port = new JTextField(20);
        panelement1.add(port);
        centerpanel.add(panelement1);

        JPanel panelement2 = new JPanel();
        panelement2.add(new JLabel("    Nome DB     "));
        dbname = new JTextField(20);
        panelement2.add(dbname);
        centerpanel.add(panelement2);

        JPanel panelement3 = new JPanel();
        panelement3.add(new JLabel("    Username    "));
        username = new JTextField(20);
        panelement3.add(username);
        centerpanel.add(panelement3);

        JPanel panelement5 = new JPanel();
        panelement5.add(new JLabel("    Password    "));
        passw = new JPasswordField(20);
        panelement5.add(passw);
        centerpanel.add(panelement5);

        JPanel panelement6 = new JPanel();
        panelement6.add(new JLabel("Conferma Password"));
        passw2 = new JPasswordField(20);
        panelement6.add(passw2);
        centerpanel.add(panelement6);

        initiate.add(centerpanel, BorderLayout.CENTER);

        JPanel paneldown = new JPanel();
        paneldown.setLayout(new GridBagLayout());
        JButton conferma = new JButton("Conferma");
        conferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                try {
                if (passw.getText().equals(passw2.getText())) {
                    try {
                        add();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AddDB.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(AddDB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JOptionPane.showMessageDialog(null, "DB configurato");
                    AddAdmin admin = new AddAdmin();
                    admin.checkAdmin();
                    initiate.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Password non corrispondenti");
                }

            }
        });

        paneldown.add(conferma);
        initiate.add(paneldown, BorderLayout.SOUTH);

        initiate.setVisible(true);

        /*ImageIcon img = new ImageIcon((getClass().getResource("/res/img/logo-Icon.png")));
        initiate.setIconImage(img.getImage());*/
    }else {
        JOptionPane.showMessageDialog(null, "Db giá configurato");
        AddAdmin admin = new AddAdmin();
        admin.checkAdmin();
                    
    }
}

    public synchronized void add() throws InterruptedException, SQLException {
        if (array.isEmpty()) {

            array.add(AES.encrypt(ip.getText(), "miao"));
            array.add(AES.encrypt(port.getText(),"miao"));
            array.add(AES.encrypt(dbname.getText(),"miao"));
            array.add(AES.encrypt(username.getText(), "miao"));
            array.add(AES.encrypt(passw.getText(), "miao"));

            try {
                File output = new File("./DATA/CONFIG/ai.aksn");
                FileOutputStream fos;
                fos = new FileOutputStream(output);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(array);

            } catch (FileNotFoundException ex) {
//                Logger.getLogger(CategoriePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
//                Logger.getLogger(CategoriePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
           //
        }
    }

}
