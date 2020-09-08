/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Ordine;
import beans.Preleva;
import beans.Prodotto;
import dao.OrdineDAO;
import dao.PrelevaDAO;
import dao.ProdottoDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Fernet
 */

class FrameRiepPreleva extends JDialog{

    private DefaultTableModel model2;
    private final JTable table2;
    private final JTextArea casNote;
    private final JScrollPane sp3;
    private final String Numordine;
    private final String Dataordine;
    private final String User;
    private final JButton comment;
    private OrdiniPanel ordinipanel;
     
    public FrameRiepPreleva(OrdiniPanel pan, String numordine, String dataordine, String user){

         this.ordinipanel = pan;
         this.Numordine = numordine;
         this.Dataordine = dataordine;
         this.User = user;
         
        
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        addWindowListener( new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent we) {
                        int OpzioneScelta = JOptionPane.showConfirmDialog(getParent(), "Sei sicuro di uscire ed annullare le modifiche?", "", JOptionPane.YES_NO_OPTION);
                        if (OpzioneScelta == JOptionPane.OK_OPTION) {
                            dispose();
                         }
                        
                    }
                } );


        ImageIcon img = new ImageIcon((getClass().getResource("/res/img/logo-Icon.png")));
        this.setIconImage(img.getImage());
        
                String[] columnNames = { "SKU", "Nome Prodotto", "Costo", "Quantita' prelevata"};

        Object[][] data = {};

        model2 = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        
      table2 = new JTable(model2){
         public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component comp = super.prepareRenderer(renderer, row, column);
            if(column >=5) return comp;

            Color alternateColor = new Color(24, 53, 90);
            Color whiteColor = new Color(10, 25, 43);
            if(!comp.getBackground().equals(getSelectionBackground())) {
               Color c = (row % 2 == 0 ? alternateColor : whiteColor);
               comp.setBackground(c);
               c = null;
            }
            return comp;
         }
      };;
      
       table2.getTableHeader().setReorderingAllowed(false);
            JScrollPane sp2 = new JScrollPane(table2);
        sp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(24, 53, 90), new Color(24, 53, 90)), "#ORDINE: "+Numordine+" del "+Dataordine+" @"+User+" ", TitledBorder.CENTER, TitledBorder.TOP));
        
        add(sp2, BorderLayout.CENTER);
      
        
                // VISIBILE SOLO SE PREMO BUTTON NOTE
                
       JPanel leftpan = new JPanel();
                
        casNote = new JTextArea();
        casNote.setAlignmentX(LEFT_ALIGNMENT);
        casNote.setLineWrap(true);
        casNote.setWrapStyleWord(true);
        casNote.setRows(5);
        casNote.setColumns(20);        
        casNote.setText("");
        sp3 = new JScrollPane(casNote);
        
        
        JButton bannulla = new JButton("Annulla");
        bannulla.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                  int OpzioneScelta = JOptionPane.showConfirmDialog(getParent(), "Sei sicuro di uscire ed annullare le modifiche?");

                if (OpzioneScelta == JOptionPane.OK_OPTION) {

                    dispose();
            }
            }

        });
        
        comment = new JButton("Salva");
        comment.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                    PrelevaDAO ordinedao = new PrelevaDAO();
                    
                    try{
                            // SALVO EVENTUALI NOTE
                            if(casNote.getText().length() > 0){
                                Preleva o = new Preleva(Numordine,casNote.getText(), OrdiniAdminPanel.nomeutente);
                                if(ordinedao.getNote(Numordine).length()>0 ){
                                    ordinedao.updateNote(casNote.getText(),  OrdiniAdminPanel.nomeutente, Numordine);}
                                else ordinedao.addNote(o);
                            }
                            else { // Se ho cancellato i commenti nella casella, allora cancella nel db quel commento
                                 if(ordinedao.getNote(Numordine).length()>=0 ){
                                    ordinedao.removeNote(Numordine);
                                 }
                            }
                    } catch (InterruptedException ex) {        
                    Logger.getLogger(FrameRiepPreleva.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(FrameRiepPreleva.class.getName()).log(Level.SEVERE, null, ex);
                }        
                            
            }

        });
        
       //leftpan.setBackground(Color.pink);
       leftpan.setLayout(new BoxLayout(leftpan, BoxLayout.Y_AXIS));
       leftpan.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED,new Color(24, 53, 90), new Color(24, 53, 90)), "Note", TitledBorder.CENTER, TitledBorder.TOP));
       
        leftpan.add(sp3);
        JPanel commenpan = new JPanel();
        commenpan.setLayout(new BoxLayout(commenpan, BoxLayout.X_AXIS));
        commenpan.add(comment);
        leftpan.add(commenpan);
        
        add(leftpan, BorderLayout.EAST);


        refreshOrdini();
        
        
    }
    
    
     public void refreshOrdini() {
         

        model2.setRowCount(0);

         PrelevaDAO ordaoo = new PrelevaDAO();
        
        try {
            //Prendo eventuali note del db per quest'ordine
           // casNote.setText(ordaoo.getNote(Numordine));
            //if(ordaoo.getNote(Numordine).length()>0)  notepresenti.setText("    Hai già registrato una nota per questo ordine");
            
            //  String[] columnNames = {"#Ordine","Fornitore" ,"SKU prodotto", "Costo", "Quantita' arrivata", "Data prevista di arrivo", " E' Arrivato?", "Messo in Stock?"};
            ProdottoDAO prodao = new ProdottoDAO();
            for (Preleva ordine : ordaoo.getByNum(Numordine)) {

                BigDecimal costoo = new BigDecimal(String.valueOf(prodao.getBySku(ordine.getProdotto_sku()).getCosto()));
                Prodotto p = prodao.getBySku(ordine.getProdotto_sku());
       
               model2.addRow(new Object[]{ordine.getProdotto_sku(), p.getNome() ,costoo.toPlainString(),ordine.getQty()});
                
                
            }
            
            //CARICA NOTE
            casNote.setText(ordaoo.getNote(Numordine));
            
            
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        } 

    }
    
}
