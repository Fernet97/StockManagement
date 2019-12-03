/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Fernet
 */
public class AnagrafichePanel extends JPanel {
    
    
    public AnagrafichePanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        JLabel title = new JLabel("ANAGRAFICHE");
        title.setFont(new Font("Arial Black", Font.BOLD, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.add(title);
    
        
        JPanel panSopra = new JPanel();
        panSopra.setLayout(new GridLayout(1, 3));
        panSopra.setMaximumSize(new Dimension(1420, 300));
        JPanel cerca = new JPanel();    
        JLabel searchlabel = new JLabel("Cerca:");
        searchlabel.setFont(new Font("Arial Black", Font.BOLD, 20));
        JTextField casella = new JTextField(20);
        JButton iconCerca = new JButton(new ImageIcon("search.png"));
        cerca.add(searchlabel);
        cerca.add(casella);
        cerca.add(iconCerca);
        panSopra.add(cerca);
        
        panSopra.add(new JLabel(" ")); 
        
        JButton buttonNew = new JButton("ADD NEW");
        buttonNew.setFont(new Font("Arial Black", Font.BOLD, 15));
        panSopra.add(buttonNew);
                

        super.add(panSopra);
       
        //Tabella
        JPanel TitoloTab1 = new JPanel();
        TitoloTab1.setLayout(new GridLayout(1,1));
        TitoloTab1.setBorder (new EmptyBorder(0, 100, 20, 100));
        String[] columns = new String[] {"Nome", "Quantità"};
        Object[][] data = new Object[][] {{"XXXXXX", 14}, {"YYYYYY", 21}, {"ZZZZZZZ", 17}};
        JTable table = new JTable(data, columns);
        JScrollPane sp = new JScrollPane(table); 
        TitoloTab1.add(sp);  
        super.add(TitoloTab1);
        

        
        
        
        
        
        
    
    }
    
    
    	public ImageIcon ImpostaImg(String nomeImmag) {

                ImageIcon icon = new ImageIcon(nomeImmag);
		Image ImmagineScalata = icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		icon.setImage(ImmagineScalata);
                return icon;
	}	
    
    
    
}
