/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Prodotto;
import dao.OrdineDAO;
import dao.ProdottoDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Fernet
 */
public class FrameNomeProdotto extends JDialog{
    
    private DefaultTableModel model;
    private final JTable table;
    private JTextField casella;
    
   
    public FrameNomeProdotto(JTextField casella, String text) throws SQLException{
        this.casella = casella;
        
        setTitle(" Seleziona un prodotto da aggiungere al carrello  ");
        setModal(true);
        
        String[] columnNames = {"SKU", "Nome", "Fornitore", "Note",  "Aggiungi al carrello"};

        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column >= 4; //il numero di celle editabili...
            }
        };
        table = new JTable(model);
        table.getColumnModel().getColumn(4).setCellRenderer(new TableButtonRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new TableRenderer(new JCheckBox()));

        JScrollPane sp2 = new JScrollPane(table);
        sp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.red, Color.red), " Prodotti con nome: "+text+ " ", TitledBorder.CENTER, TitledBorder.TOP));
        
        add(sp2, BorderLayout.CENTER);
        
        
        ProdottoDAO prodao = new ProdottoDAO();
        OrdineDAO ordao = new OrdineDAO();
        
        for(Prodotto p: prodao.getByNome(text)){
            model.addRow(new Object[]{p.getSku(), p.getNome(), ordao.getFPr(p.getSku()), p.getNote(), ""});
        
        }
        
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    
    }
    
    
     class TableButtonRenderer extends JButton implements TableCellRenderer {

        public TableButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());

            setIcon(ImpostaImg("/res/img/ordini.png"));

            return this;
        }

    }
     
    public class TableRenderer extends DefaultCellEditor {

        private JButton button;
        private String label;
        private boolean clicked;
        private int row, col;
        private JTable table;
        public String skusel;

        public TableRenderer(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();    // ECCO IL BOTTONE

            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            this.row = row;
            this.col = column;

            button.setText(label);
            button.setIcon(ImpostaImg("/res/img/ordini.png"));

            clicked = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (clicked) // SE CLICCATO QUEL BOTTONE:::::::::::::
            {
                casella.setText(model.getValueAt(row, 0).toString());
                chiudi();

            }
            clicked = false;
            return "";
        }

        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
    
    
    public void chiudi(){
        setModal(false);
        dispose();
    
    
    }
    
    
    public ImageIcon ImpostaImg(String nomeImmag) {

        ImageIcon icon = new ImageIcon((getClass().getResource(nomeImmag)));
        Image ImmagineScalata = icon.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);
        icon.setImage(ImmagineScalata);
        return icon;
    }
    
}
