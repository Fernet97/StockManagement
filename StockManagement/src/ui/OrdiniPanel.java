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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Spinner;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import others.RoundedPanel;

/**
 *
 * @author Fernet
 */
public class OrdiniPanel extends JPanel {

    public Prodotto prodottoCorrente;
    private final ProdottoDAO dao;
    private final JLabel infosku;
    private final JLabel infonome;
    private final JLabel infocat;
    private final JTextArea infonote;
    private final JLabel infostock;
    private final JTextField casella;
    private final JLabel qty;
    private int qtyAttuale;


    public OrdiniPanel(){
        
        dao = new ProdottoDAO();
        qtyAttuale= 0;
    
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        JLabel title = new JLabel("ORDINI");
        title.setFont(new Font("Arial Black", Font.BOLD, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.add(title);  
        
        
        JPanel cerca = new JPanel();    
        JLabel searchlabel = new JLabel("Cerca prodotto:");
        searchlabel.setFont(new Font("Arial Black", Font.BOLD, 20));
        casella = new JTextField(30);
        casella.addCaretListener(new CaretListener() {
        @Override
        public void caretUpdate(CaretEvent e) {
            System.out.println("Il testo è stato campiato: "+casella.getText());
            try {

                aggiornaScheda(casella.getText());
            } catch (SQLException ex) {
                casella.setBackground(Color.red);
            }
            

        }
    });
        
        cerca.add(searchlabel);
        cerca.add(casella);
        cerca.setMaximumSize(new Dimension(1000, 100));
        
        super.add(cerca);
        
        //Pannello centrale
        JPanel princ = new JPanel();
        princ.setLayout(new GridLayout(2, 2, 40, 40));
        
        JPanel fotopan = new JPanel();
        princ.add(fotopan); //Aggiungi pannello x Foto a Ovest
        fotopan.setBackground(new Color( 128, 128, 128));
        fotopan.setBorder(BorderFactory.createLineBorder(new Color( 27, 32, 36), 50));
        fotopan.add(new JLabel("FOTO"));
        
        JPanel info = new JPanel();
        info.setLayout(new GridLayout(5, 2, 20, 20));
        info.setBorder(BorderFactory.createLineBorder(new Color( 27, 32, 36), 20));
        
        JLabel infolabel = new JLabel("SKU prodotto:");
        infolabel.setFont(new Font("Arial Black", Font.BOLD, 30));
        infolabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(infolabel);
  
        infosku = new JLabel("");
        infosku.setFont(new Font("Arial Black", Font.BOLD, 20));
        infosku.setForeground(new Color(244, 80, 37));
        infosku.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(infosku);
        
        
        JLabel infolabel2 = new JLabel("Nome:");
        infolabel2.setFont(new Font("Arial Black", Font.BOLD, 20));
        infolabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(infolabel2);
  
        infonome = new JLabel("");
        infonome.setFont(new Font("Arial Black", Font.BOLD, 20));
        infonome.setForeground(new Color(244, 80, 37));
        infonome.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(infonome);  
        
        
        JLabel infolabel3 = new JLabel("Categoria:");
        infolabel3.setFont(new Font("Arial Black", Font.BOLD, 20));
        infolabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(infolabel3);
  
        infocat = new JLabel("");
        infocat.setFont(new Font("Arial Black", Font.BOLD, 20));
        infocat.setForeground(new Color(244, 80, 37));
        infocat.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(infocat);    
        
        
        JLabel infolabel4 = new JLabel("Note:");
        infolabel4.setFont(new Font("Arial Black", Font.BOLD, 20));
        infolabel4.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(infolabel4);
  
        infonote = new JTextArea("");
        infonote.setFont(new Font("Arial Black", Font.ITALIC, 15));
        infonote.setEditable(false);
        infonote.setLineWrap(true);
        infonote.setForeground(Color.white);
        infonote.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(infonote);    

        JLabel infolabel5 = new JLabel("In Stock?:");
        infolabel5.setFont(new Font("Arial Black", Font.BOLD, 20));
        infolabel5.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(infolabel5);
  
        infostock = new JLabel("");
        infostock.setFont(new Font("Arial Black", Font.BOLD, 20));
        infostock.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(infostock);          
        
        princ.add(info);

        /*** PRENDI E CONFERMA **********/
        JPanel SXdown = new RoundedPanel();
        SXdown.setLayout(new GridLayout(6, 1));
        SXdown.add(new JLabel(""));        
        SXdown.add(new JLabel("")); 
        
        JPanel quantityPanel = new JPanel(new GridBagLayout());
        JLabel infolabel6 = new JLabel("Quantità:   ");
        infolabel6.setFont(new Font("Arial Black", Font.BOLD, 30));
        quantityPanel.add(infolabel6);
        qty = new JLabel("");
        qty.setFont(new Font("Arial Black", Font.BOLD, 40));      
        qty.setForeground(new Color(244, 80, 37));
        quantityPanel.add(qty);
        SXdown.add(quantityPanel);

        JPanel quantityPanel2 = new JPanel(new GridBagLayout());
        JLabel daprendere = new JLabel("Prendi:  ");
        daprendere.setFont(new Font("Arial Black", Font.BOLD, 30));
        quantityPanel2.add(daprendere);
        JTextField quantdaprend = new JTextField(10);
        quantdaprend.setFont(new Font("Arial Black", Font.BOLD, 30));
        quantityPanel2.add(quantdaprend);
        SXdown.add(quantityPanel2);

        SXdown.add(new JLabel(""));        
        SXdown.add(new JLabel("")); 
        
        princ.add(SXdown);
        
        JPanel DXdown = new JPanel(new GridBagLayout());        
        JButton conferma = new JButton("   Conferma    ");
        conferma.setFont(new Font("Arial Black", Font.ITALIC, 40));
        conferma.setMinimumSize(new Dimension(100, 50));
        conferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(check()){
                int OpzioneScelta = JOptionPane.showConfirmDialog(getParent(),"Sei sicuro di voler effettuare tali modifiche?");
                if (OpzioneScelta == JOptionPane.OK_OPTION) System.out.println("OOOOOOOOKKKKKK EFFETTUO PRELIEVO UNITA'");  
                    prodottoCorrente.setQty(qtyAttuale - Integer.parseInt(quantdaprend.getText()));
                    try {
                        System.out.println("aggiorno "+prodottoCorrente.getSku() + "  "+ prodottoCorrente.getId_fornitore() );
                        dao.update(prodottoCorrente, prodottoCorrente.getId_fornitore());
                        casella.setText(prodottoCorrente.getSku());
                    } catch (SQLException ex) {
                        Logger.getLogger(OrdiniPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            private boolean check() {

                try{ //Controlla se sono interi...
                    Integer.parseInt(quantdaprend.getText());       
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(getParent(),"Formato quantità da prelevare errata!!");
                    return false;
                }
                
                int qtydaTogliere = Integer.parseInt(quantdaprend.getText());
                if(qtydaTogliere > qtyAttuale) {
                    JOptionPane.showMessageDialog(getParent(), "Non puoi prendere più di "+qtyAttuale+ " unità!");
                    return false;
                }     
                
                return true;

            }
        });
       DXdown.add(conferma); 
       princ.add(DXdown);     
   
       
       
        super.add(princ);
        
        

    
        
    
    }
   
    
    public void aggiornaScheda(String sku) throws SQLException{
    
        prodottoCorrente = dao.getBySku(sku);
     
                casella.setBackground(Color.green);


                infosku.setText(prodottoCorrente.getSku());
                infonome.setText(prodottoCorrente.getNome());
                infocat.setText(prodottoCorrente.getCategoria());
                infonote.setText(prodottoCorrente.getDescrizione());
                qtyAttuale = prodottoCorrente.getQty();
                qty.setText(Integer.toString(prodottoCorrente.getQty()) + " (Qty min = "+prodottoCorrente.getQty_min()+")");
                if(prodottoCorrente.getQty() <= prodottoCorrente.getQty_min()) qty.setForeground(Color.red);
                else qty.setForeground(Color.green);
                


                if(prodottoCorrente.isInstock()){
                    infostock.setText("SI");
                    infostock.setForeground(Color.green);

                }

                else{
                    infostock.setText("NO");
                    infostock.setForeground(Color.red);

                }
                
        
        
    
    
    
    }
}





