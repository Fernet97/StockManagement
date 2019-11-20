/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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
import javax.swing.border.TitledBorder;


/**
 *
 * @author Fernet
 */
public class FramePrincipale extends JFrame{
    
    
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
        
        JPanel HomePanel = new JPanel(new CardLayout());
        CardLayout cardlayout = new CardLayout();
        HomePanel.setLayout(cardlayout);
                
        //Aggiungi la carta "DASHBOARD"
        JPanel dashboard = new JPanel();
        HomePanel.add(dashboard, "DASHBOARD");
        
        //Aggiungi la carta "ANAGRAFICHE"
        JPanel anagrafiche =new JPanel();
        anagrafiche.add(new JLabel(" a n a g r a f i c h e !  ! !"));
        anagrafiche.setBackground(Color.red);
        HomePanel.add(anagrafiche, "ANAGRAFICHE");
        


        JPanel pannellodash = new JPanel();
        JPanel pannelloTab = new JPanel();
        dashboard.add(pannellodash);
        dashboard.add(pannelloTab);
        pannellodash.setLayout(new GridLayout(3,2, 50, 70));
       
        for(int i =0; i<6; i++){
            JButton button = new JButton("Ciao"+ i);
            button.setBackground(new Color( 66, 139, 221));
            pannellodash.add(button);
            
        }
        
        //Le tabelle ...
        JPanel TitoloTab1 = new JPanel ();
        TitoloTab1.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),"Ultimi 15 prodotti in esaurimento", TitledBorder.CENTER,TitledBorder.TOP));
        String[] columns = new String[] {"Nome", "Quantità"};
        Object[][] data = new Object[][] {{"XXXXXX", 14}, {"YYYYYY", 21}, {"ZZZZZZZ", 17}};
        JTable table = new JTable(data, columns);       
        JScrollPane sp = new JScrollPane(table); 
        TitoloTab1.add(sp);
        
        JPanel TitoloTab2 = new JPanel ();
        TitoloTab2.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),"Giorni all'arrivo", TitledBorder.CENTER,TitledBorder.TOP));
        String[] columns2 = new String[] {"Nome", "Quantità"};
        Object[][] data2 = new Object[][] {{"XXXXXX", 344}, {"YYYYYY", 644}, {"ZZZZZZZ", 28}};
        JTable table2 = new JTable(data2, columns2);        
        JScrollPane sp2 = new JScrollPane(table2); 
        TitoloTab2.add(sp2);
        


        pannelloTab.setLayout(new GridLayout(2,1));
        pannelloTab.add(TitoloTab1);
        pannelloTab.add(TitoloTab2);


        //Aggiungi tutto a questo Jpanel HomePage
        JPanel pannelloprinc = new JPanel ();
        pannelloprinc.setLayout(new GridLayout(1,2, 20, 20));
        pannelloprinc.add(pannellodash);
        pannelloprinc.add(pannelloTab);
        pannelloprinc.setBorder(new EmptyBorder(20, 0, 0, 0)); // per dare un po di margini

        setLayout(new BorderLayout());
        add(pannelloprinc, BorderLayout.CENTER);
        add(new JLabel("                           "), BorderLayout.NORTH); //Soluzioni rustiche
        add(new JLabel("                           "), BorderLayout.WEST); //Soluzioni rustiche
        add(new JLabel("                           "), BorderLayout.EAST); //Soluzioni rustiche
        add(new JLabel("                           "), BorderLayout.SOUTH); //Soluzioni rustiche
        
        
        
        //Barra Laterale
        JPanel pannellolaterale = new JPanel();
        JLabel TitleLaterale = new JLabel("       DASHBOARD       "); //Per dare ampiezza al jpanel
        TitleLaterale.setFont(new Font("Arial Black", Font.BOLD, 20));
        pannellolaterale.setLayout(new BoxLayout(pannellolaterale, BoxLayout.Y_AXIS));
        TitleLaterale.setAlignmentX(CENTER_ALIGNMENT);  
        pannellolaterale.add(TitleLaterale);
        
        JPanel pannelloOpzioni = new JPanel();
        pannelloOpzioni.setLayout(new GridLayout(5, 1, 30, 30));
        pannelloOpzioni.add(new JButton("Home"));     
        JButton buttonAnagrafiche = new JButton("Anagrafiche");
        buttonAnagrafiche.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardlayout.show(HomePanel, "ANAGRAFICHE");
                    }
                });;
        pannelloOpzioni.add(buttonAnagrafiche);        
        pannelloOpzioni.add(new JButton("Categorie"));
        pannelloOpzioni.add(new JButton("Prodotti"));
        pannelloOpzioni.add(new JButton("Codici"));
        pannelloOpzioni.setAlignmentX(CENTER_ALIGNMENT);
        pannelloOpzioni.setBackground(new Color( 128, 128, 128));
        pannellolaterale.add(pannelloOpzioni);
        pannellolaterale.setBorder(new EmptyBorder(10, 0, 0, 40)); // per dare un po di margini
        
        add(pannellolaterale, BorderLayout.WEST);
        
        


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

   


    
}
