package others;

import beans.Utente;
import dao.UtenteDAO;
import database.DriverManagerConnectionPool;
import java.awt.BorderLayout;
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
    private static JTextField casname1;
    private static JTextField casname2;
    private static JTextField casname3;
    private static JPasswordField casname4;
    private static JPasswordField casname5;
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
        initiate.setAlwaysOnTop(true);  // Focus
        initiate.setTitle("Admin configure");
        
        JPanel centerpanel = new JPanel();
        centerpanel.setLayout(new GridLayout(3,2));
        
        JPanel panelement = new JPanel();
        panelement.add(new JLabel("Nome e Cognome"));
        casname = new JTextField(20);
        panelement.add(casname);
        centerpanel.add(panelement);

        
        JPanel panelement1 = new JPanel();
        panelement1.add(new JLabel("Codice Fiscale"));
        casname1 = new JTextField(20);
        panelement1.add(casname1);
        centerpanel.add(panelement1);

        
        JPanel panelement2 = new JPanel();
        panelement2.add(new JLabel("   Indirizzo   "));
        casname2 = new JTextField(20);
        panelement2.add(casname2);
        centerpanel.add(panelement2);

        
        JPanel panelement3 = new JPanel();
        panelement3.add(new JLabel("      Email      "));
        casname3 = new JTextField(20);
        panelement3.add(casname3);
        centerpanel.add(panelement3);

        
        JPanel panelement4 = new JPanel();
        panelement4.add(new JLabel("  Password  "));
        casname4 = new JPasswordField(20);
        panelement4.add(casname4);
        centerpanel.add(panelement4);

        
        JPanel panelement5 = new JPanel();
        panelement5.add(new JLabel("Conferma Password"));
        casname5 = new JPasswordField(20);
        panelement5.add(casname5);
        centerpanel.add(panelement5);

        

   
        initiate.add(centerpanel, BorderLayout.CENTER);
        
        JPanel paneldown = new JPanel();
        paneldown.setLayout(new GridBagLayout());
        JButton conferma = new JButton("Conferma");
        conferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adminAdd();
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
    
   
    public static synchronized void adminAdd() throws InterruptedException, SQLException {
        
       
        Utente u = new Utente();
        u.setFullname(casname.getText());
        
           
        
        Connection connection = null;
        Statement statement = null;
        String query = "INSERT INTO `db_stock`.`utente` (`idutente`, `datareg`, `fullname`, `cf`, `indirizzo`, `tel`, `email`, `pwd`, `permessi`, `note`, `id`) "
                + "VALUES ('giacca', '"+u.generateData()+"', '"+u.getFullname()+"', '"+u.getCF()+"', '"+u.getIndirizzo()+"',"
                + " '"+u.getTelefono()+"', '"+u.getEmail()+"', md5('"+u.getPwd()+"'), '0', 'null', '0')";
  
        System.out.println(query);
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
