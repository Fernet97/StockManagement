package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;




public class LoginDialog extends javax.swing.JDialog {

    private javax.swing.JComboBox comboBox;
    private JLabel label_nome;
    private JTextField casella_nome;
    private JLabel label_pwd;
    private JPasswordField casella_pwd;
    private JButton ButtonAccedi;
    private JLabel logo;

    //Costruttore
    public LoginDialog() {
        
        CreaGUI();
    }
        
    
            
   /* verifica Login
    public boolean checkLogin(int ID,String password, String user){
        if("ADMINISTRATOR".equals(user)){
            //String query="SELECT * FROM utenti WHERE id='"+ID+"' AND password='"+password+"'AND isAdmin= 1";
            String query="SELECT * FROM utenti WHERE id='1' AND pwd='test' AND isAdmin= '1'";
            try{
                rs=stmt.executeQuery(query);
                while(rs.next()){
                    flag=true;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            String query="SELECT * FROM utenti WHERE id='"+ID+"' AND pwd='"+password+"'AND isAdmin = 0'";
            try{
                rs=stmt.executeQuery(query);
                while(rs.next()){
                    flag=true;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return flag;
    }*/

        private void CreaGUI() {
        
                //Crea pannello Iniziale: Titolo- Logo - Selettore tipo itente
                JPanel PannelloIniziale = new JPanel();
                PannelloIniziale = new JPanel();
                JLabel titolo = new JLabel("$tock managemenT");
                titolo.setFont(new Font("Arial Black", Font.BOLD, 20));
		PannelloIniziale.add(titolo, BorderLayout.NORTH);
                ImageIcon icon = new ImageIcon("logo.jpg");
                Image ImmagineScalata = icon.getImage().getScaledInstance(400, 225, Image.SCALE_DEFAULT);
		icon.setImage(ImmagineScalata);
		JLabel logo = new JLabel(icon);
		PannelloIniziale.add(logo, BorderLayout.CENTER);
		this.add(PannelloIniziale, BorderLayout.NORTH);

               
                //Crea campi username e password
                JPanel PannelloAutenticazione = new JPanel();
                PannelloAutenticazione.add(new JLabel("")); // per dare un po di margine sopra           
                JPanel panelnome = new JPanel();
                panelnome.setLayout (new FlowLayout());  // Floaw layout allinea gli oggetti in una riga
                label_nome = new JLabel("Nome utente :");
                label_nome.setFont(new Font("Arial Black", Font.PLAIN, 15));                
                casella_nome = new JTextField("", 20);
                casella_nome.setFont(new Font("Arial Black", Font.ITALIC, 10));
                panelnome.add(label_nome);
                panelnome.add(casella_nome);
               
                

                JPanel panelpwd = new JPanel();
                panelpwd.setLayout (new FlowLayout());  
                label_pwd = new JLabel("       Password :");
                label_pwd.setFont(new Font("Arial Black", Font.PLAIN, 15));
                casella_pwd = new JPasswordField("", 20);
                casella_pwd.setFont(new Font("Arial Black", Font.ITALIC, 10));
                panelpwd.add(label_pwd);
                panelpwd.add(casella_pwd); 
                
                
                PannelloAutenticazione.add(panelnome); 
                PannelloAutenticazione.add(panelpwd);               
                             
                this.add(PannelloAutenticazione,BorderLayout.CENTER);
              
                                
                //Crea tasto Accedi e Pulisci
                JPanel pannelloB = new JPanel();
                ButtonAccedi = new JButton("Accedi"); 
                ButtonAccedi.setFont(new Font("Arial Black", Font.PLAIN, 15));
                pannelloB.add(ButtonAccedi, BorderLayout.CENTER);
                this.add(pannelloB, BorderLayout.SOUTH);
               
        }
       
}
