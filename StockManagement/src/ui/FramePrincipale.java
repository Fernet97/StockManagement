/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


/**
 *
 * @author Fernet
 */
public class FramePrincipale extends JFrame{
    
    private JPanel HomePanel;
    private CardLayout cardlayout;
    private JPanel pannellolaterale;
    private JPanel pannelloOpzioni;

    
    public FramePrincipale(){
           CreaGUI();
    }

    public void CreaGUI() {

        //set icona finestra
        ImageIcon img = new ImageIcon("logo-Icon.png");
        this.setIconImage(img.getImage());
                
        
        //Aggiungi barra Menu
        CreaMenu();

        //***** AGGIUNGI LE CARTE/SCHERMATE *********
        
        HomePanel = new JPanel(new CardLayout());
        cardlayout = new CardLayout();
        HomePanel.setLayout(cardlayout);
                
        //Aggiungi la carta "DASHBOARD"
        JPanel dashboard = new JPanel();
        HomePanel.add(dashboard, "Dashboard");
        
        //Aggiungi la carta "ORDINI"
        JPanel ordini =new JPanel();
        ordini.add(new JLabel(" O R D I I N I !  ! !"));
        HomePanel.add(ordini, "Ordini");
        
        //Aggiungi la carta "ANAGRAFICHE"
        JPanel anagrafiche =new JPanel();
        anagrafiche.add(new JLabel(" a n a g r a f i c h e !  ! !"));
        HomePanel.add(anagrafiche, "Anagrafiche");
        
        
        // Aggiungi la carta "CATEGORIE"
        JPanel categorie =new JPanel();
        categorie.add(new JLabel("  C A T E G O R I E!  ! !"));
        HomePanel.add(categorie, "Categorie");      
        
        // Aggiungi la carta "CODICI"
        JPanel codici =new JPanel();
        codici.add(new JLabel(" CODICI !  ! !"));
        HomePanel.add(codici, "Codici");      
        


        //Barra Laterale (rimarrà fissa per ogni schermata)
        pannellolaterale = new JPanel();
        JLabel TitleLaterale = new JLabel("       DASHBOARD       "); //Per dare ampiezza al jpanel
        TitleLaterale.setFont(new Font("Arial Black", Font.BOLD, 20));
        pannellolaterale.setLayout(new BoxLayout(pannellolaterale, BoxLayout.Y_AXIS));
        TitleLaterale.setAlignmentX(CENTER_ALIGNMENT);  
        pannellolaterale.add(TitleLaterale);
        
        pannelloOpzioni = new JPanel();
        pannelloOpzioni.setLayout(new GridLayout(6, 1, 30, 30));
        pannelloOpzioni.add(new ButtonLaterale("Dashboard"));
        pannelloOpzioni.add(new ButtonLaterale("Ordini"));
        pannelloOpzioni.add(new ButtonLaterale("Anagrafiche"));
        pannelloOpzioni.add(new ButtonLaterale("Categorie"));
        pannelloOpzioni.add(new ButtonLaterale("Codici"));
        pannelloOpzioni.setAlignmentX(CENTER_ALIGNMENT);
        //pannelloOpzioni.setBackground(new Color( 128, 128, 128));
        pannellolaterale.add(pannelloOpzioni);

        pannellolaterale.setBorder(new EmptyBorder(10, 0, 0, 40)); // per dare un po di margini
        
        
        //************ CARD DASHBOARD ****************
        JPanel pannellodash = new JPanel();
        JPanel pannelloTab = new JPanel();
  
        // i pulsantoni della dashboard
        pannellodash.setLayout(new GridLayout(3,2, 50, 70));

        for(int i =0; i<6; i++){
            JButton button = new JButton("Ciao"+ i);
            button.setBackground(new Color( 66, 139, 221));
            pannellodash.add(button);
            
        }
        
        //Le tabelle ...
        JPanel TitoloTab1 = new JPanel ();
        TitoloTab1.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (EtchedBorder.RAISED, Color.red, Color.red),"Ultimi 15 prodotti in esaurimento", TitledBorder.RIGHT,TitledBorder.TOP));
        String[] columns = new String[] {"Nome", "Quantità"};
        Object[][] data = new Object[][] {{"XXXXXX", 14}, {"YYYYYY", 21}, {"ZZZZZZZ", 17}};
        JTable table = new JTable(data, columns);       
        JScrollPane sp = new JScrollPane(table); 
        TitoloTab1.add(sp);
        
        JPanel TitoloTab2 = new JPanel ();
        TitoloTab2.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (EtchedBorder.RAISED, Color.red, Color.red),"Giorni all'arrivo", TitledBorder.RIGHT,TitledBorder.TOP));
        String[] columns2 = new String[] {"Nome", "Quantità"};
        Object[][] data2 = new Object[][] {{"XXXXXX", 344}, {"YYYYYY", 644}, {"ZZZZZZZ", 28}};
        JTable table2 = new JTable(data2, columns2);        
        JScrollPane sp2 = new JScrollPane(table2); 
        TitoloTab2.add(sp2);
        

        pannelloTab.setLayout(new GridLayout(2,1));
        pannelloTab.add(TitoloTab1);
        pannelloTab.add(TitoloTab2);
        pannelloTab.setBorder(new EmptyBorder(20, 200, 20, 50));
                         
                       
        dashboard.setLayout (new GridLayout(1,2));  
        dashboard.add(pannellodash);
        dashboard.add(pannelloTab);  
        
 
        add(pannellolaterale, BorderLayout.WEST); 
        add(HomePanel, BorderLayout.CENTER);
        //X dar magini verticali...
        add(new JLabel("                           "), BorderLayout.NORTH); //Soluzioni rustiche
        add(new JLabel("                           "), BorderLayout.SOUTH); //Soluzioni rustiche
    }

    
    
    
    public void CreaMenu() {
        
        JMenuBar menu = new JMenuBar();
        JMenu m1 = new JMenu("Info");
        JMenu m2 = new JMenu("Profilo");
        
        JMenuItem itemAbout = new JMenuItem("Chi siamo");
        m1.add(itemAbout);
                
        JMenuItem itemLogout = new JMenuItem("Logout");
        JMenuItem itemEsci = new JMenuItem("Esci");
        m2.add(itemLogout);
        m2.add(itemEsci);

        menu.add(m1);
        menu.add(m2);       
        setJMenuBar(menu);
    }

 



 class ButtonLaterale extends JPanel{
	
	public final String tipo;
        public boolean premuto = false; // per button nel pannello laterale
        private Color color_etichetta;
        private JLabel icon;
        private int code;
        private final JPanel pan;

        
	public ButtonLaterale(String tipo) {
		super();
                this.tipo = tipo;
		super.setBackground(new Color( 128, 128, 128));
                
                super.setLayout(new GridLayout(1,2));
                
                        
                if(tipo.equals("Dashboard")){
                    icon = new JLabel(ImpostaImg("home.png"));
                    color_etichetta = Color.blue;
                    code = 0;

                }
                if(tipo.equals("Ordini")){
                    icon = new JLabel(ImpostaImg("ordini.png"));
                    color_etichetta = Color.orange; 
                    code = 1;
                
                }   
                if(tipo.equals("Anagrafiche")){
                    icon = new JLabel(ImpostaImg("anagrafiche.png"));
                    color_etichetta = Color.yellow;                   
                    code = 2;
                }       
                
                if(tipo.equals("Categorie")){
                    icon = new JLabel(ImpostaImg("categorie.png"));
                    color_etichetta = Color.cyan;                   
                    code = 3;
                }                     
               if(tipo.equals("Codici")){
                    icon = new JLabel(ImpostaImg("codici.png"));
                    color_etichetta = Color.WHITE;                   
                    code = 4;
                }

                JPanel etichetta1 =  new JPanel();
                etichetta1.setBackground(color_etichetta);
  
                pan = new JPanel();
                pan.setBackground(new Color( 128, 128, 128));
                icon.setBorder(new EmptyBorder(5, 0, 0, 0));
                JLabel testo = new  JLabel("  "+tipo);
                testo.setFont(new Font("Arial Black", Font.BOLD, 18));
                pan.setLayout(new GridLayout(2,1));
                pan.add(icon);
                pan.add(testo);
                               
                super.add(etichetta1);
                super.add(pan);
                
                super.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                       pan.setBackground(color_etichetta);
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                       ButtonLaterale bottonepremuto =(ButtonLaterale) e.getSource();
                       bottonepremuto.premuto = true;
                                                                     
                       disattivaTuttiIBottoniTranne(bottonepremuto.code);
                        
                       cardlayout.show(HomePanel, tipo);
                       pan.setBackground(color_etichetta);
                       HomePanel.setBorder(BorderFactory.createMatteBorder(0, 20, 0, 0, color_etichetta));
                       pannellolaterale.setBorder(new EmptyBorder(10, 0, 0, 10)); // per dare un po di margini
       
                       
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {}

                    @Override
                    public void mouseReleased(MouseEvent e) {}

                    @Override
                    public void mouseExited(MouseEvent e) {
                      ButtonLaterale bottonepremuto =(ButtonLaterale) e.getSource();
                       if(!bottonepremuto.premuto)
                             pan.setBackground(new Color( 128, 128, 128));  
                       
                    }

                });
               
                
	}
        
        public void  disattivaTuttiIBottoniTranne(int cod){
            for(int i=0; i< pannelloOpzioni.getComponentCount(); i++){
                
                if(i != cod) {
                    ButtonLaterale b = (ButtonLaterale) pannelloOpzioni.getComponent(i);
                    b.pan.setBackground(new Color( 128, 128, 128));  
                    b.premuto = false;
                }
            
            }
        
        }
	
        

        

	public ImageIcon ImpostaImg(String nomeImmag) {

                ImageIcon icon = new ImageIcon(nomeImmag);
		Image ImmagineScalata = icon.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
		icon.setImage(ImmagineScalata);
                return icon;
	}



		
    }
 
}