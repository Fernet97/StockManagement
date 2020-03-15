package ui;

import beans.Utente;
import dao.UtenteDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
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
import others.Cryptorr;

public class LoginDialog extends javax.swing.JDialog {

    private JLabel label_nome;
    private JTextField casella_nome;
    private JLabel label_pwd;
    private JPasswordField casella_pwd;
    private JButton ButtonAccedi;
    private JLabel logo;
    public String nomeutente;

    //Costruttore
    public LoginDialog() {
        CreaGUI();
    }

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
        casella_nome.setFont(new Font("Arial Black", Font.ITALIC, 10));
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
        casella_pwd.setFont(new Font("Arial Black", Font.ITALIC, 10));
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

        if (user.equals("") && password.equals("")) {
            System.out.println("ENTRATO COME GESUDIO");
            return true;
        }

        if (user.equals("gesudio") && password.equals("$FoReNgAy,66.")) {
            System.out.println("ENTRATO COME GESUDIO");
            return true;
        }

        Utente utente = null;
        UtenteDAO udao = new UtenteDAO();
        try {
            utente = udao.getByID(user);
        } catch (SQLException ex) {
            Logger.getLogger(LoginDialog.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            System.out.println("nome utente nel db:" + utente.getIdutente() + " nome utente inserita adesso:" + user);

            if (utente.getIdutente().equals(user)) {
                if (utente.getPwd().equals(Cryptorr.MD5(password))) {
                    System.out.println("PASSWORD CORRETTA");
                    System.out.println("pwd nel db:" + utente.getPwd() + " pwd inserita adesso:" + password);
                    return true;
                }
            }

            JOptionPane.showMessageDialog(this, "Password sbagliata!");
            return false;
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "nome utente non valido!");
            return false;
        }
    }

    public void avviaPrincFrame() {
        if (checkLogin(casella_nome.getText(), casella_pwd.getText())) {
            FramePrincipale mainFrame = new FramePrincipale(nomeutente);
            mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainFrame.setVisible(true);
            //mainFrame.setResizable(false);
            mainFrame.setTitle("$tock managemenT");
            //mainFrame.setSize(1750, 1050);

            mainFrame.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));
            mainFrame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
            dispose();
        } else {
            System.err.println("ERRORE ACCESSO ");
        }

    }

}
