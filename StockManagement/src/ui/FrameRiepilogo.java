/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Ordine;
import beans.Prodotto;
import dao.OrdineDAO;
import dao.ProdottoDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Fernet
 */
class FrameRiepilogo extends JFrame{

    private  DefaultTableModel model2;
    private final JTable table2;
    private final String numordine;
    
    public FrameRiepilogo(String numordine){
        
        this.numordine = numordine;
    
        ImageIcon img = new ImageIcon((getClass().getResource("/res/img/logo-Icon.png")));
        this.setIconImage(img.getImage());
        
             String[] columnNames = {"#Ordine","Fornitore" ,"SKU prodotto", "Costo", "Quantita' arrivata", "Data prevista di arrivo", " E' Arrivato?", "Messo in Stock?", "Gestisci"};

        Object[][] data = {};

        model2 = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column >= 8; //il numero di celle editabili...
            }
        };
        table2 = new JTable(model2);
        table2.getTableHeader().setReorderingAllowed(false);
        
        table2.getColumnModel().getColumn(8).setCellRenderer(new TableButtonRenderer());
        table2.getColumnModel().getColumn(8).setCellEditor(new TableRenderer(new JCheckBox()));
  
       
        
        JScrollPane sp2 = new JScrollPane(table2);
        sp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.red, Color.red),"  #ORDINE: "+ numordine+"  ", TitledBorder.CENTER, TitledBorder.TOP));
        add(sp2, BorderLayout.CENTER);  
        
        JButton  bannulla= new JButton("Annulla");
        bannulla.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }

        });
        
        
        
        
        JButton bconferma= new JButton("Conferma");
       
        JPanel pdown = new JPanel();
        pdown.setLayout(new GridBagLayout());
        pdown.add(bconferma);
        pdown.add(bannulla);
        add(pdown, BorderLayout.SOUTH);
        
        refreshOrdini();
        
    
    
    
    
    }

    public void refreshOrdini() {
        
        model2.setRowCount(0);
        
              OrdineDAO ordaoo = new OrdineDAO();

        try {
            /*
                            bean.setN_ordine(rs.getString("n_ordine"));
                bean.setData(rs.getString("data"));
                bean.setQty_in_arrivo(rs.getInt("qty_in_arrivo"));
                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                bean.setFk_utente(rs.getString("fk_utente"));
                bean.setProdotto_sku(rs.getString("prodotto_sku"));
                bean.setFk_cliente(rs.getInt("fk_cliente"));
                bean.setFk_fornitore(rs.getString("fk_fornitore"));

            
            
            */         
            //  String[] columnNames = {"#Ordine","Fornitore" ,"SKU prodotto", "Costo", "Quantita' arrivata", "Data prevista di arrivo", " E' Arrivato?", "Messo in Stock?"};
            
            ProdottoDAO prodao = new ProdottoDAO();
            for (Ordine ordine : ordaoo.getByNum(numordine)) {
                
                model2.addRow(new Object[]{ordine.getN_ordine(), ordine.getFk_fornitore(), ordine.getProdotto_sku(), prodao.getBySku(ordine.getProdotto_sku()).getCosto(), "0/"+ordine.getQty_in_arrivo(), "22/12/2020", "Sì", "No"});
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdiniAdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
    
    
    class TableButtonRenderer extends JButton implements TableCellRenderer {

        public TableButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());

            setIcon(ImpostaImg("/res/img/pencil.png"));

            return this;
        }

    }

    public class TableRenderer extends DefaultCellEditor {

        private JButton button;
        private String label;
        private boolean clicked;
        private int row, col;
        private JTable table;

        public TableRenderer(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();    // ECCO IL BOTTONE

            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("APRI FORMMMMm");
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            this.row = row;
            this.col = column;

            button.setForeground(Color.black);
            button.setBackground(UIManager.getColor("Button.background"));

            label = (value == null) ? "" : value.toString();
            button.setText(label);
            button.setIcon(ImpostaImg("/res/img/pencil.png"));
      
            clicked = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (clicked) // SE CLICCATO QUEL BOTTONE:::::::::::::
            {
                if (button.getText().equals("Controlla ordine")) {
                    FrameRiepilogo f = new FrameRiepilogo(table.getValueAt(row, 0).toString());
                    f.setResizable(false);
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setSize(1300,400);
                    f.setVisible(true);
                    f.setTitle("Riepilogo ordine: "+ table.getValueAt(row, 0));
                    

                } else if (button.getText().equals("Ricarica ordine")) {
                    
                }

            }
            clicked = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
 
 
        public ImageIcon ImpostaImg(String nomeImmag) {

        ImageIcon icon = new ImageIcon((getClass().getResource(nomeImmag)));
        Image ImmagineScalata = icon.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);
        icon.setImage(ImmagineScalata);
        return icon;
    }
    
    
}
