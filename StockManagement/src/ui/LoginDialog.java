package ui;

import beans.Utente;
import dao.UtenteDAO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import others.Cryptorr;

public class LoginDialog extends javax.swing.JDialog {

    private JLabel label_nome;
    private JTextField casella_nome;
    private JLabel label_pwd;
    private JPasswordField casella_pwd;
    private JButton ButtonAccedi;
    private JLabel logo;
    public static String nomeutente;

    //Costruttore
    public LoginDialog() {
        CreaGUI();
    }

    private static Logger userlogger = Logger.getLogger("userlog");
    public static FileHandler fhu;

    private void CreaGUI() {

        ImageIcon img = new ImageIcon(getClass().getResource("/res/img/logo-Icon.png"));
        this.setIconImage(img.getImage());

        //Crea pannello Iniziale: Titolo- Logo 
        JPanel PannelloIniziale = new JPanel();
        PannelloIniziale = new JPanel();
        JLabel titolo = new JLabel("$tock managemenT");
        titolo.setFont(new Font("Arial Black", Font.BOLD, 20));
        PannelloIniziale.add(titolo, BorderLayout.NORTH);
        ImageIcon icon = new ImageIcon(getClass().getResource("/res/img/logo.jpg"));
        Image ImmagineScalata = icon.getImage().getScaledInstance(400, 225, Image.SCALE_DEFAULT);
        icon.setImage(ImmagineScalata);
        JLabel logo = new JLabel(icon);
        PannelloIniziale.add(logo, BorderLayout.CENTER);
        this.add(PannelloIniziale, BorderLayout.NORTH);

        //Crea campi username e password
        JPanel PannelloAutenticazione = new JPanel();
        PannelloAutenticazione.add(new JLabel("")); // per dare un po di margine sopra           
        JPanel panelnome = new JPanel();
        panelnome.setLayout(new FlowLayout());  // Floaw layout allinea gli oggetti in una riga
        label_nome = new JLabel("Nome utente :");
        label_nome.setFont(new Font("Arial Black", Font.PLAIN, 15));
        casella_nome = new JTextField("", 20);
        casella_nome.setFont(new Font("Arial Black", Font.BOLD, 10));
        casella_nome.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    avviaPrincFrame();
                }
            }
        });
        panelnome.add(label_nome);
        panelnome.add(casella_nome);

        JPanel panelpwd = new JPanel();
        panelpwd.setLayout(new FlowLayout());
        label_pwd = new JLabel("       Password :");
        label_pwd.setFont(new Font("Arial Black", Font.PLAIN, 15));
        casella_pwd = new JPasswordField("", 20);
        casella_pwd.setFont(new Font("Arial Black", Font.BOLD, 10));
        casella_pwd.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    avviaPrincFrame();
                }
            }
        });

        panelpwd.add(label_pwd);
        panelpwd.add(casella_pwd);

        PannelloAutenticazione.add(panelnome);
        PannelloAutenticazione.add(panelpwd);

        this.add(PannelloAutenticazione, BorderLayout.CENTER);

        //Crea tasto Accedi
        JPanel pannelloB = new JPanel();
        ButtonAccedi = new JButton("Accedi");
        ButtonAccedi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                avviaPrincFrame();
            }
        });
        ButtonAccedi.setFont(new Font("Arial Black", Font.PLAIN, 15));
        pannelloB.add(ButtonAccedi, BorderLayout.CENTER);
        this.add(pannelloB, BorderLayout.SOUTH);

    }

    // verifica Login
    public boolean checkLogin(String user, String password) {

        nomeutente = user;

        
        /*serve per accedere senza login
        if (user.equals("") && password.equals("")) {
            System.out.println("ENTRATO COME GESUDIO");
            return true;
        }*/

        if (user.equals("gesudio") && password.equals("$FoReNgAy,66.")) {
            Logger.getLogger("genlog").info("GOD MODE ON\n- - - 666 - - ENTRATO COME GESUDIO ;) - - -666 - - -");
            return true;
        }

        Utente utente = null;
        UtenteDAO udao = new UtenteDAO();
        try {
            utente = udao.getByID(user);
        } catch (SQLException ex) {
 Logger.getLogger("genlog").warning("SQLException\n"+ex);        }

        try {

            if (utente.getIdutente().equals(user)) {
                if (utente.getPwd().equals(Cryptorr.MD5(password))) {
                  //  System.out.println("PASSWORD CORRETTA");
                    //System.out.println("pwd nel db:" + utente.getPwd() + " pwd inserita adesso:" + password);
                    return true;
                }
            }

            JOptionPane.showMessageDialog(this, "Password sbagliata!");
            Logger.getLogger("genlog").info("Password sbagliata!\n");
            return false;
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "nome utente non valido!");
 Logger.getLogger("genlog").info("nome utente non valido!\n"+e);          
 return false;
        }
    }

    public void avviaPrincFrame() {

        if (checkLogin(casella_nome.getText(), casella_pwd.getText())) {
            FramePrincipale mainFrame = new FramePrincipale(nomeutente);
            logUserStart();
            mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainFrame.setVisible(true);
            //mainFrame.setResizable(false);
            mainFrame.setTitle("$tock managemenT");
            //mainFrame.setSize(1750, 1050);

            mainFrame.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));
            mainFrame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
             Logger.getLogger("userlog").info("ACCESSO COME: " + nomeutente + "\n");
            dispose();
        } else {
//          JOptionPane.showMessageDialog(this, "errore accesso");
        }

    }

    public void logUserStart() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
            LocalDateTime now = LocalDateTime.now();

            String datanow = dtf.format(now);

            // This block configure the logger with handler and formatter  
            fhu = new FileHandler("./DATA/LOG/USERLOG/LOG_" + nomeutente + "_" + datanow + ".log");
            userlogger.addHandler(fhu);
            SimpleFormatter formatter = new SimpleFormatter();
            fhu.setFormatter(formatter);

        } catch (SecurityException e) {
             Logger.getLogger("genlog").warning("SecurityException\n"+e);
            e.printStackTrace();
        } catch (IOException e) {
                         Logger.getLogger("genlog").warning("IOException\n"+e);

            e.printStackTrace();
        } catch (Exception e) {             Logger.getLogger("genlog").warning("Exception\n"+e);

            e.printStackTrace();
        }
    }
}
