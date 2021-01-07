/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Fornitore;
import beans.Prodotto;
import dao.FornitoreDAO;
import dao.OrdineDAO;
import dao.ProdottoDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import ui.OrdiniPanel.TableButtonRenderer;

/**
 *
 * @author Fernet
 */
public class JPanelNomeProdotto extends JPanel {

    public DefaultTableModel model;
    private JTable table;
    private JTextField casella;
    private JScrollPane sp2;
    private String text;
    private boolean PrelevaMode;
    private OrdiniPanel Ordinipanel;
    private TableRenderer myTableRender;
    private OrdiniAdminPanel OrdiniAdminPanel;

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
        
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        
            casella.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent arg0) {
            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                    ProdottoDAO prodao = new ProdottoDAO();
                    OrdineDAO ordinedao = new OrdineDAO();

                String text = casella.getText();
                
                  if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                    casella.setBackground(Color.darkGray);
                    casella.setForeground(Color.white);
                    
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(text));
                    try {
                        Prodotto p = prodao.getBySku(text);
                        if(p.getSku() != null){ //sE è UNO SKU
                            casella.setBackground(Color.green);
                            casella.setForeground(Color.black);
                            
                            String fornitore = ordinedao.getFPr(text);
                            FornitoreDAO fordao = new FornitoreDAO();
                            
                            //Però non è associato nessun fornitore..
                            if ( fornitore == null || fornitore.length() < 2) {
                                casella.setBackground(Color.ORANGE);
                                casella.setForeground(Color.black);
                                System.out.println("E' uno sku senza fonritore");
                                nessunFornitore(text);
                                return;
                            }
                            
                        OrdiniAdminPanel.aggiungiTOcarrello(text,fornitore+"|"+ fordao.getByID(fornitore).getFullname() );
                            
                            
                        }
                        else {
                            casella.setBackground(Color.yellow);
                            casella.setForeground(Color.red);
                        }
                        
                    if(rowSorter.getViewRowCount()  < 1){
                            casella.setBackground(Color.red);
                            casella.setForeground(Color.white);
                    }
                    
                    if(casella.getText().length() <= 0){
                        System.out.println("nulla");
                        casella.setBackground(Color.darkGray);
                        casella.setForeground(Color.white);
                     }
                        

                        
                    } catch (SQLException ex) {
                        Logger.getLogger(ProdottiPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                    ProdottoDAO prodao = new ProdottoDAO();
                    OrdineDAO ordinedao = new OrdineDAO();

                String text = casella.getText();
                
                  if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                    casella.setBackground(Color.darkGray);
                    casella.setForeground(Color.white);
                    
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(text));
                    try {
                        Prodotto p = prodao.getBySku(text);
                        if(p.getSku() != null){ //sE è UNO SKU
                            casella.setBackground(Color.green);
                            casella.setForeground(Color.black);
                                
                            String fornitore = ordinedao.getFPr(text);
                            FornitoreDAO fordao = new FornitoreDAO();

                            //Però non è associato nessun fornitore..
                            if (ordinedao.getFPr(text) == null || ordinedao.getFPr(text).length() < 2) {
                                casella.setBackground(Color.ORANGE);
                                casella.setForeground(Color.black);
                                System.out.println("E' uno sku senza fonritore");
                                nessunFornitore(text);
                                return;
                            }
                            
                        OrdiniAdminPanel.aggiungiTOcarrello(text,fornitore+"|"+ fordao.getByID(fornitore).getFullname() );

                            
                            
                        }
                        else {
                            casella.setBackground(Color.yellow);
                            casella.setForeground(Color.red);
                        }
                        
                    if(rowSorter.getViewRowCount()  < 1){
                            casella.setBackground(Color.red);
                            casella.setForeground(Color.white);
                    }
                    
                    if(casella.getText().length() <= 0){
                        System.out.println("nulla");
                        casella.setBackground(Color.darkGray);
                        casella.setForeground(Color.white);
                     }
                        

                        
                    } catch (SQLException ex) {
                        Logger.getLogger(ProdottiPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        );
        
        sp2 = new JScrollPane(table);
        sp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(24, 53, 90), new Color(24, 53, 90)), " Prodotti con nome: " + text + " ", TitledBorder.CENTER, TitledBorder.TOP));

        add(sp2);

        refresh();

        

    }
    
    
    public void nessunFornitore(String text){
        int OpzioneScelta =  JOptionPane.showConfirmDialog(null, "il prodotto non è associato a nessun fornitore.\nVuoi associarlo ad un fornitore e aggiungerlo al carrello?");
       if (OpzioneScelta == JOptionPane.OK_OPTION) {
            try {
                FornitoreDAO forndao = new FornitoreDAO();
                JDialog vaiarod = new JDialog();
                vaiarod.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                vaiarod.setResizable(false);
                vaiarod.setModal(true);
                vaiarod.setLocationRelativeTo(null);
                
                vaiarod.setTitle("Seleziona un fornitore a cui associare il prodotto");
                vaiarod.setSize(new Dimension(500, 150));
                vaiarod.setLayout(new GridBagLayout());
                JComboBox jComboBoxxx = new JComboBox<>();
                jComboBoxxx.setFont(new Font("Arial Black", Font.BOLD, 20));
                vaiarod.add(jComboBoxxx);
                
                vaiarod.add(new JLabel("       "));
                
                JButton confermaforn = new JButton("Conferma");
                confermaforn.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        vaiarod.dispose();
                        OrdiniAdminPanel.aggiungiTOcarrello(text, jComboBoxxx.getSelectedItem().toString());
                    }
                });
                confermaforn.setFont(new Font("Arial Black", Font.BOLD, 20));
                vaiarod.add(confermaforn);
                
                for (Fornitore f : forndao.getAll()) {
                    jComboBoxxx.addItem(f.getIdfornitore() + "|" + f.getFullname());
                }
                if (jComboBoxxx.getItemCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Non hai creato ancora un fornitore!");
                    return;
                }
                
                
                
                vaiarod.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(JPanelNomeProdotto.class.getName()).log(Level.SEVERE, null, ex);
            }
    } 
       else {

                //Deve essere un thread a parte rispetto al thread del documen listener
         Runnable pusliscicasella = new Runnable() {
             @Override
             public void run() {
                 casella.setText("");
             }
         };       
         SwingUtilities.invokeLater(pusliscicasella);
       
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

    


    public ImageIcon ImpostaImg(String nomeImmag) {

        ImageIcon icon = new ImageIcon((getClass().getResource(nomeImmag)));
        Image ImmagineScalata = icon.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);
        icon.setImage(ImmagineScalata);
        return icon;
    }

    public void modePreleva() {
        String[] columnNames = {"SKU", "Nome", "Categoria", "Qty", "Qty prelevabile", "Note", "Stock", "Add al Carrello"};

        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column >= 7; //il numero di celle editabili...
            }
        };

        table = new JTable(model){
         public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component comp = super.prepareRenderer(renderer, row, column);
            if(column > 5) return comp;
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

        table.getColumnModel().getColumn(6).setCellRenderer(new CustomStockRender());

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
        
        
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        
            casella.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent arg0) {
            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                    ProdottoDAO prodao = new ProdottoDAO();
                    OrdineDAO ordinedao = new OrdineDAO();

                String text = casella.getText();
                
                  if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                    casella.setBackground(Color.darkGray);
                    casella.setForeground(Color.white);
                    
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(text));
                    try {
                        Prodotto p = prodao.getBySku(text);
                        if(p.getSku() != null){ //sE è UNO SKU
                            casella.setBackground(Color.green);
                            casella.setForeground(Color.black);
                            
                            if(!p.isInstock()){ //però il prodotto non è in stock ..
                                casella.setBackground(Color.ORANGE);
                                casella.setForeground(Color.black);
                                JOptionPane.showMessageDialog(null, "Prodotto non in stock. Contattare l'amministratore.");
                                //Deve essere un thread a parte rispetto al thread del documen listener
                                Runnable pusliscicasella = new Runnable() {
                                    @Override
                                    public void run() {
                                        casella.setText("");
                                    }
                                };       
                                SwingUtilities.invokeLater(pusliscicasella);
                                return;
                            }
                            
                            //Però non è associato nessun fornitore..
                            if (ordinedao.getFPr(text) == null || ordinedao.getFPr(text).length() < 2) {
                                casella.setBackground(Color.ORANGE);
                                casella.setForeground(Color.black);
                                System.out.println("E' uno sku senza fonritore");
                                JOptionPane.showMessageDialog(null, "Fornitore non associato. Contattare l'amministratore.");
                                //Deve essere un thread a parte rispetto al thread del documen listener
                                Runnable pusliscicasella = new Runnable() {
                                    @Override
                                    public void run() {
                                        casella.setText("");
                                    }
                                };       
                                SwingUtilities.invokeLater(pusliscicasella);
                                return;
                            }
                            
                           Ordinipanel.windowPrelCreate(casella.getText());

                            
                            
                        }
                        else {
                            casella.setBackground(Color.yellow);
                            casella.setForeground(Color.red);
                        }
                        
                    if(rowSorter.getViewRowCount()  < 1){
                            casella.setBackground(Color.red);
                            casella.setForeground(Color.white);
                    }
                    
                    if(casella.getText().length() <= 0){
                        System.out.println("nulla");
                        casella.setBackground(Color.darkGray);
                        casella.setForeground(Color.white);
                     }
                        

                        
                    } catch (SQLException ex) {
                        Logger.getLogger(ProdottiPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                    ProdottoDAO prodao = new ProdottoDAO();
                    OrdineDAO ordinedao = new OrdineDAO();

                String text = casella.getText();
                
                  if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                    casella.setBackground(Color.darkGray);
                    casella.setForeground(Color.white);
                    
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(text));
                    try {
                        Prodotto p = prodao.getBySku(text);
                        if(p.getSku() != null){ //sE è UNO SKU
                            casella.setBackground(Color.green);
                            casella.setForeground(Color.black);
                            
                            if(!p.isInstock()){ //però il prodotto non è in stock ..
                                casella.setBackground(Color.ORANGE);
                                casella.setForeground(Color.black);
                                JOptionPane.showMessageDialog(null, "Prodotto non in stock. Contattare l'amministratore.");
                                //Deve essere un thread a parte rispetto al thread del documen listener
                                Runnable pusliscicasella = new Runnable() {
                                    @Override
                                    public void run() {
                                        casella.setText("");
                                    }
                                };       
                                SwingUtilities.invokeLater(pusliscicasella);
                                return;
                            }
                            
                            //Però non è associato nessun fornitore..
                            if (ordinedao.getFPr(text) == null || ordinedao.getFPr(text).length() < 2) {
                                casella.setBackground(Color.ORANGE);
                                casella.setForeground(Color.black);
                                System.out.println("E' uno sku senza fonritore");
                                JOptionPane.showMessageDialog(null, "Fornitore non associato. Contattare l'amministratore.");
                                //Deve essere un thread a parte rispetto al thread del documen listener
                                Runnable pusliscicasella = new Runnable() {
                                    @Override
                                    public void run() {
                                        casella.setText("");
                                    }
                                };       
                                SwingUtilities.invokeLater(pusliscicasella);
                                return;
                            }
                            
                            Ordinipanel.windowPrelCreate(casella.getText());
                            
                        }
                        else {
                            casella.setBackground(Color.yellow);
                            casella.setForeground(Color.red);
                        }
                        
                    if(rowSorter.getViewRowCount()  < 1){
                            casella.setBackground(Color.red);
                            casella.setForeground(Color.white);
                    }
                    
                    if(casella.getText().length() <= 0){
                        System.out.println("nulla");
                        casella.setBackground(Color.darkGray);
                        casella.setForeground(Color.white);
                     }
                        

                    } catch (SQLException ex) {
                        Logger.getLogger(ProdottiPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        );
            
        

        sp2 = new JScrollPane(table);
        sp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(24, 53, 90), new Color(24, 53, 90)), " Prodotti con nome: " + text + " ", TitledBorder.CENTER, TitledBorder.TOP));

        add(sp2);
        
        
  

        refresh();

    }

    public void setComunicator(OrdiniPanel ordinipanel) {
        Ordinipanel = ordinipanel;

    }
    
    
        public void setAdminComunicator(OrdiniAdminPanel o) {
        OrdiniAdminPanel = o;

    }
        
        
        
        
    public void  refresh(){
        try {
            ProdottoDAO prodao = new ProdottoDAO();
            OrdineDAO ordao = new OrdineDAO();

            
            model.setRowCount(0);
    
    
            if(PrelevaMode){

                    for (Prodotto p : prodao.getAll()) {
                        model.addRow(new Object[]{p.getSku(), p.getNome(), p.getCategoria(), String.valueOf(p.getQty()), String.valueOf(p.getQty() - p.getQty_min()), p.getNote(), p.isInstock(), ""});
                    }   
            }
            else{

                for (Prodotto p : prodao.getAll()) {
                    model.addRow(new Object[]{p.getSku(), p.getNome(), ordao.getFPr(p.getSku()), p.getCosto(), p.getNote(), ""});
                }         

            }

           } catch (SQLException ex) {
                    Logger.getLogger(JPanelNomeProdotto.class.getName()).log(Level.SEVERE, null, ex);
           }
    
    }
        
        
        
        
       
    class CustomStockRender extends JButton implements TableCellRenderer {

        public CustomStockRender() {
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
