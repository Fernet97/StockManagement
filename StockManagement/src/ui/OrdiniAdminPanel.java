/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Prodotto;
import dao.ProdottoDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import static java.util.Collections.list;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import others.RoundedPanel;

/**
 *
 * @author Fernet
 */
public class OrdiniAdminPanel  extends JPanel{
    
    
    public Prodotto prodottoCorrente;
    private javax.swing.JComboBox<String> jComboBox;
    private DefaultTableModel model;
    private JTable table;

    

        
    public OrdiniAdminPanel(){
    
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        JLabel title = new JLabel("ORDINI ADMIN");
        title.setFont(new Font("Arial Black", Font.BOLD, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.add(title);  

        
        //Pannello centrale
        JPanel princ = new JPanel();
        princ.setLayout(new GridLayout(2, 2, 20, 20));
        
        JPanel sxpan = new JPanel();
        sxpan.setLayout(new BoxLayout(sxpan, BoxLayout.PAGE_AXIS));
        princ.add(sxpan); 
        sxpan.setBorder(BorderFactory.createLineBorder(new Color( 27, 32, 36), 50));

        JPanel orizontalprod = new JPanel();        
        JLabel prodtext = new JLabel("Sku Prodotto");
        prodtext.setFont(new Font("Arial Black", Font.BOLD, 20));
        orizontalprod.add(prodtext);
        
        JTextField casella = new JTextField();
        casella.setColumns(30);
        orizontalprod.add(casella);
        sxpan.add(orizontalprod);
        
        jComboBox = new JComboBox<>();
        jComboBox.setFont(new Font("Arial Black", Font.BOLD, 30));
        jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Amazon", "Ebay", "AIiExpress" }));
        jComboBox.setForeground(Color.black);
        jComboBox.setBackground(Color.DARK_GRAY);
        jComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            }
        });
        sxpan.add(jComboBox);
        
        
        JList list = new JList(new DefaultListModel<>());
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(10, 11, 227, 239);
        for (int i = 0; i < 30; i++) ((DefaultListModel) list.getModel()).addElement("Prodotto"+String.valueOf(i));
        sxpan.add(scrollPane);

        
        
        
        
        JPanel dxpan = new JPanel();
        dxpan.setLayout(new BoxLayout(dxpan, BoxLayout.PAGE_AXIS));    
        dxpan.add(Box.createRigidArea(new Dimension(20, 0)));
        JLabel infolabel = new JLabel("     Carrello:    ");
        infolabel.setFont(new Font("Arial Black", Font.ITALIC, 40));      
        
        JPanel info = new RoundedPanel();
        info.setBackground(new Color(151, 109, 248));
        info.add(Box.createRigidArea(new Dimension(50, 10)));
        info.setLayout(new BoxLayout(info, BoxLayout.PAGE_AXIS));
        JList listcar = new JList(new DefaultListModel<>());
        JScrollPane scrollPanecar = new JScrollPane(listcar);
        for (int i = 0; i < 6; i++) ((DefaultListModel) listcar.getModel()).addElement("Prodottoscelto"+String.valueOf(i+"  x2  arriva tra 7 giorni da Amazon"));
        for (int i = 7; i < 14; i++) ((DefaultListModel) listcar.getModel()).addElement("Prodottoscelto"+String.valueOf(i)+" x1 arriva tra 5 giorni da Ebay");
        info.add(scrollPanecar);
        info.add(new JButton("Rimuovi prodotto selezionato"));

        infolabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dxpan.add(infolabel);
        dxpan.add(info);
         
        princ.add(dxpan);

        /*** tabella ordin**********/
        JPanel SXdown = new JPanel();
        SXdown.setLayout(new BoxLayout(SXdown, BoxLayout.PAGE_AXIS));    
        table = new JTable(); 
        table.setEnabled(false);
        model = new DefaultTableModel();
        model.addColumn("ID ordine");
        model.addColumn("# ordine");    
        model.addColumn("Data");  
        model.addColumn("Quantità in arrivo");
        model.addColumn("Giorni alla consegna");    
        model.addColumn("Utente ordinante");   
        model.addColumn("Sku prodotto");  
        model.addColumn("ID cliente");  
        model.addColumn("ID Fornitore");  
        table.setModel(model);
        JScrollPane sp = new JScrollPane(table); 
        sp.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (EtchedBorder.RAISED, Color.red, Color.red),"Riepilogo ordini effettuati", TitledBorder.CENTER,TitledBorder.TOP));
        SXdown.add(sp);
        for(int i = 0; i < 40; i++)
             model.addRow(new Object[]{"XXXXX","XXXXX", "XXXXX", "XXXXX","XXXXX","XXXXX", "XXXXX", "XXXXX", "XXXXX" });  
        
        princ.add(SXdown);
        
        
        JPanel DXdown = new JPanel(); 
        DXdown.setLayout(new BoxLayout(DXdown, BoxLayout.PAGE_AXIS));
        JLabel riepilogo = new JLabel("Riepilogo ordine");
        riepilogo.setFont(new Font("Arial Black", Font.BOLD, 30));
        riepilogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        DXdown.add(riepilogo); 

        JLabel numordine = new JLabel("#Ordine: N23");
        numordine.setFont(new Font("Arial Black", Font.BOLD, 15));
        numordine.setAlignmentX(Component.CENTER_ALIGNMENT);
        DXdown.add(numordine);

        JLabel tot = new JLabel("Costo totale: 431.31$");
        tot.setFont(new Font("Arial Black", Font.BOLD, 15)); 
        tot.setAlignmentX(Component.CENTER_ALIGNMENT);
        DXdown.add(tot);

        JButton conferma = new JButton("   Effettua ordine    ");
        conferma.setFont(new Font("Arial Black", Font.ITALIC, 40));
        conferma.setMinimumSize(new Dimension(100, 50));
        conferma.setAlignmentX(Component.CENTER_ALIGNMENT);
        DXdown.add(conferma); 
        
        
        princ.add(DXdown);     
   
       
       
        super.add(princ);
        
    }

}

