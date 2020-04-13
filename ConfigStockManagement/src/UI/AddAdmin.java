package UI;

import Main.Config_StockManagement;
import beans.Utente;
import database.DriverManagerConnectionPool;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
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
public class AddAdmin {

    private JTextField casname;
    private JTextField CF;
    private JTextField indirizzo;
    private JTextField email;
    private JTextField tel;
    private JPasswordField passw;
    private JPasswordField passw2;
    ImageIcon img = new ImageIcon((getClass().getResource("/res/img/logo-Icon.png")));

    public void adminAdd() throws SQLException {
        


        JFrame initiate = new JFrame();
        initiate.setIconImage(img.getImage());
        initiate.setLocationRelativeTo(null);
        initiate.setResizable(false);
        initiate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initiate.setSize(600, 400);
        initiate.setLocationRelativeTo(null);  // CENTRA 
        initiate.setTitle("Admin configure");

        JPanel centerpanel = new JPanel();
        centerpanel.setLayout(new GridLayout(4, 3));

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
                    if (passw.getText().equals(passw2.getText())) {
                        Add(casname.getText(), CF.getText(), indirizzo.getText(), tel.getText(), email.getText(), passw.getText());
                        JOptionPane.showMessageDialog(null, "Admin registrato");
                        initiate.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Password non corrispondenti");
                    }

                } catch (InterruptedException ex) {
                    Logger.getLogger(AddAdmin.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AddAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        paneldown.add(conferma);
        initiate.add(paneldown, BorderLayout.SOUTH);

        initiate.setVisible(true);

    }

    public synchronized void Add(String nome, String cf, String indirizzo, String tel, String email, String pswd) throws InterruptedException, SQLException {


        Connection connection = null;
        Statement statement = null;

        String query = "INSERT INTO `db_stock`.`utente` (`idutente`, `datareg`, `fullname`, `cf`, `indirizzo`, `tel`, `email`, `pwd`, `permessi`, `note`, `id`) "
                + "VALUES ('admin', '" + generateData() + "', '" + nome + "', '" + cf + "', '" + indirizzo + "',"
                + " '" + tel + "', '" + email + "', md5('" + pswd + "'), '0', 'null', '0')";

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

    public synchronized Utente getAdmin() throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Utente bean = new Utente();

        String sql = "SELECT * FROM utente WHERE idutente = 'admin' ";

 try {
                        connection = DriverManagerConnectionPool.getConnection();
                        preparedStatement = connection.prepareStatement(sql);
                        ResultSet rs = preparedStatement.executeQuery();

                        while (rs.next()) {


                        bean.setIdutente(rs.getString("idutente"));
                        bean.setDatareg(rs.getString("datareg"));
                        bean.setFullname(rs.getString("fullname"));
                        bean.setCF(rs.getString("CF"));
                        bean.setIndirizzo(rs.getString("indirizzo"));
                        bean.setTelefono(rs.getString("tel"));
                        bean.setEmail(rs.getString("email"));
                        bean.setPwd(rs.getString("pwd"));
                        bean.setPermessi(rs.getInt("permessi"));
                        bean.setNote(rs.getString("note"));


                        }

                } finally {
                        try {
                                if (preparedStatement != null)
                                        preparedStatement.close();
                        } finally {
                                DriverManagerConnectionPool.releaseConnection(connection);
                        }
                }

                    return bean;          
            }
    
    
     public synchronized String generateData() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
     
     public void checkAdmin(){
         AddAdmin admin = new AddAdmin();
                    String idutente = null;
                    try {
                        idutente = admin.getAdmin().getIdutente();
                    } catch (SQLException ex) {
                        Logger.getLogger(AddDB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String Admin = "admin";
                    if (idutente == null) {
                        try {
//                             String pswd = JOptionPane.showInputDialog("Inserisci password del db");
                             String pswd = Config_StockManagement.pass("Inserisci la password del DB", "Admin Configure");
                              if (pswd == null)return;
                             if(pswd.equals(DriverManagerConnectionPool.pswd)){
                            admin.adminAdd();
                             }else{
                             JOptionPane.showMessageDialog(null, "Password errata");
                             }
                        } catch (SQLException ex) {
                            Logger.getLogger(AddDB.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (idutente.equals(Admin)) {
                        JOptionPane.showMessageDialog(null, "Admin gia presente");
                    }
     }
}
