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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
public class JPanelNomeProdotto extends JPanel {

    private DefaultTableModel model;
    private JTable table;
    private JTextField casella;
    private JScrollPane sp2;
    private String text;
    private boolean PrelevaMode;
    private OrdiniPanel Ordinipanel;
    private TableRenderer myTableRender;

    public JPanelNomeProdotto(JTextField casella, String text, boolean PrelevaMode) {
        this.casella = casella;
        this.text = text;
        this.PrelevaMode = PrelevaMode;

        setLayout(new GridLayout(1, 1));

        if (PrelevaMode) {
            modePreleva();
            return;
        }

        String[] columnNames = {"SKU", "Nome", "Fornitore", "Costo", "Note", "Aggiungi al carrello"};

        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column >= 5; //il numero di celle editabili...
            }
        };
        table = new JTable(model){
         public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component comp = super.prepareRenderer(renderer, row, column);
            if(column == 5) return comp;
            Color alternateColor = new Color(24, 53, 90);
            Color whiteColor = new Color(10, 25, 43);
            if(!comp.getBackground().equals(getSelectionBackground())) {
               Color c = (row % 2 == 0 ? alternateColor : whiteColor);
               comp.setBackground(c);
               c = null;
            }
            return comp;
         }
      };
        table.getColumnModel().getColumn(5).setCellRenderer(new TableButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new TableRenderer(new JCheckBox()));

        sp2 = new JScrollPane(table);
        sp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(24, 53, 90), new Color(24, 53, 90)), " Prodotti con nome: " + text + " ", TitledBorder.CENTER, TitledBorder.TOP));

        add(sp2);

        ProdottoDAO prodao = new ProdottoDAO();
        OrdineDAO ordao = new OrdineDAO();

        try {
            for (Prodotto p : prodao.getByNome(text)) {
                model.addRow(new Object[]{p.getSku(), p.getNome(), ordao.getFPr(p.getSku()), p.getCosto(), p.getNote(), ""});
            }

        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        }

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
                    try {
                        fireEditingStopped();
                    } catch (ArrayIndexOutOfBoundsException ex) {
                    }
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

            }
            // clicked = false;
            return "";
        }

        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            try {
                super.fireEditingStopped();
            } catch (ArrayIndexOutOfBoundsException ex) {
            }
        }
    }

    public void aggiornaNome(String newtext) {

        sp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.red, Color.red), " Prodotti con nome: " + newtext + " ", TitledBorder.CENTER, TitledBorder.TOP));
        ProdottoDAO prodao = new ProdottoDAO();
        OrdineDAO ordao = new OrdineDAO();

        //myTableRender.fireEditingStopped();
            model.setRowCount(0);

        try {
            for (Prodotto p : prodao.getByNome(newtext)) {
                if (PrelevaMode) {
                    model.addRow(new Object[]{p.getSku(), p.getNome(), p.getCategoria(), String.valueOf(p.getQty()), String.valueOf(p.getQty() - p.getQty_min()), p.getNote(), p.isNegozio(), ""});
                } else {
                    model.addRow(new Object[]{p.getSku(), p.getNome(), ordao.getFPr(p.getSku()), p.getCosto(), p.getNote(), ""});
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        }

    }

    public ImageIcon ImpostaImg(String nomeImmag) {

        ImageIcon icon = new ImageIcon((getClass().getResource(nomeImmag)));
        Image ImmagineScalata = icon.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);
        icon.setImage(ImmagineScalata);
        return icon;
    }

    public void modePreleva() {
        String[] columnNames = {"SKU", "Nome", "Categoria", "Qty", "Qty prelevabile", "Note", "Negozio", "Add al Carrello"};

        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column >= 7; //il numero di celle editabili...
            }
        };

        table = new JTable(model);

        table.getColumnModel().getColumn(6).setCellRenderer(new CustomNegozioRender());

        table.getColumnModel().getColumn(7).setCellRenderer(new TableButtonRenderer());
        myTableRender = new TableRenderer(new JCheckBox());
        table.getColumnModel().getColumn(7).setCellEditor(myTableRender);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if(model.getRowCount() > 0 && table.getSelectedRow()!= -1){
                    System.out.println("Riga selezionata:"+ table.getSelectedRow());
                    Ordinipanel.setPhoto(table.getValueAt(table.getSelectedRow(), 0).toString());
                }
            }
        });

        sp2 = new JScrollPane(table);
        sp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(24, 53, 90), new Color(24, 53, 90)), " Prodotti con nome: " + text + " ", TitledBorder.CENTER, TitledBorder.TOP));

        add(sp2);

    }

    public void setComunicator(OrdiniPanel ordinipanel) {
        Ordinipanel = ordinipanel;

    }

    class CustomNegozioRender extends JButton implements TableCellRenderer {

        public CustomNegozioRender() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            if (value.toString().equals("false")) {
                setBackground(new Color(244, 80, 37));
            } else if (value.toString().equals("true")) {
                setBackground(new Color(126, 169, 93));
            }

            return this;
        }
    }
}
