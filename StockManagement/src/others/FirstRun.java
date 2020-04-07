package others;

import beans.Utente;
import dao.UtenteDAO;
import database.DriverManagerConnectionPool;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import ui.ProdottiPanel;


/**
 *
 * @author LittleJoke
 */
public class FirstRun {

    private static JTextField casname;
    private static JTextField CF;
    private static JTextField indirizzo;
    private static JTextField email;
    private static JTextField tel;
    private static JPasswordField passw;
    private static JPasswordField passw2;
/**
* classe che servirá per richiamare metodo di add 
* al primo avvio del software,
* aggiungendo il primo utente MASTERADMIN 
     * @throws java.lang.InterruptedException
     * @throws java.sql.SQLException
 */
      public static void main(String[] args) {
          
        JFrame initiate = new JFrame();
        initiate.setLocationRelativeTo(null);
        initiate.setResizable(false);
        initiate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initiate.setSize(600, 400);
        initiate.setLocationRelativeTo(null);  // CENTRA 
//        initiate.setAlwaysOnTop(true);  // Focus
        initiate.setTitle("Admin configure");
        
        JPanel centerpanel = new JPanel();
        centerpanel.setLayout(new GridLayout(4,3));
        
        JPanel panelement = new JPanel();
        panelement.add(new JLabel("Nome e Cognome"));
        casname = new JTextField(20);
        panelement.add(casname);
        centerpanel.add(panelement);

        
        JPanel panelement1 = new JPanel();
        panelement1.add(new JLabel("Codice Fiscale"));
        CF = new JTextField(20);
        panelement1.add(CF);
        centerpanel.add(panelement1);

        
        JPanel panelement2 = new JPanel();
        panelement2.add(new JLabel("   Indirizzo   "));
        indirizzo = new JTextField(20);
        panelement2.add(indirizzo);
        centerpanel.add(panelement2);

        
        JPanel panelement3 = new JPanel();
        panelement3.add(new JLabel("      Email      "));
        email = new JTextField(20);
        panelement3.add(email);
        centerpanel.add(panelement3);

        
        JPanel panelement4 = new JPanel();
        panelement4.add(new JLabel("    Telefono    "));
        tel = new JTextField(20);
        panelement4.add(tel);
        centerpanel.add(panelement4);

        
        JPanel panelement5 = new JPanel();
        panelement5.add(new JLabel("    Password    "));
        passw = new JPasswordField(20);
        panelement5.add(passw);
        centerpanel.add(panelement5);
        
        JPanel panelement6 = new JPanel();
        panelement6.add(new JLabel("    Conferma Password   "));
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
                try {
                     if (passw.getText().equals(passw2.getText())){
                         adminAdd(casname.getText() ,CF.getText(), indirizzo.getText(), tel.getText(),email.getText(),passw.getText());
                         JOptionPane.showMessageDialog(null, "Admin registrato");
                         
                         }
                     else {JOptionPane.showMessageDialog(null, "Password non corrispondenti");
                     }
                                        
                } catch (InterruptedException ex) {
                    Logger.getLogger(FirstRun.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(FirstRun.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        paneldown.add(conferma);
        initiate.add(paneldown, BorderLayout.SOUTH);
        
        initiate.setVisible(true);

        /*ImageIcon img = new ImageIcon((getClass().getResource("/res/img/logo-Icon.png")));
        initiate.setIconImage(img.getImage());*/
    
     
     
     }
    
   
    public static synchronized void adminAdd(String nome, String cf, String indirizzo, String tel, String email, String pswd) throws InterruptedException, SQLException {
        
       
       Utente u = new Utente();
        Connection connection = null;
        Statement statement = null;
       
        
           
        
        
        String query = "INSERT INTO `db_stock`.`utente` (`idutente`, `datareg`, `fullname`, `cf`, `indirizzo`, `tel`, `email`, `pwd`, `permessi`, `note`, `id`) "
                + "VALUES ('admin', '"+u.generateData()+"', '"+nome+"', '"+cf+"', '"+indirizzo+"',"
                + " '"+tel+"', '"+email+"', md5('"+pswd+"'), '0', 'null', '0')";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.commit();

        } finally {
            
            try {
                if (statement != null) {
                    statement.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }  
    
    }
        
}
