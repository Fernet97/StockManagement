/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Fornitore;
import beans.Prodotto;
import dao.FornitoreDAO;
import dao.ProdottoDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.RIGHT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import static ui.AnagrafichePanel.form;

/**
 *
 * @author Fernet
 */
public class ProdottiPanel extends JPanel {

    private final DefaultTableModel model;
    public static Object[] nuovaRiga;
    public final JTextField casella;
    public FormProdotti form;
    public ArrayList<String> list_cat_new;
    private final JTable table;

    public ProdottiPanel() {
        list_cat_new = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("PRODOTTI");
        title.setFont(new Font("Arial Black", Font.BOLD, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.add(title);

        JPanel panSopra = new JPanel();
        panSopra.setLayout(new GridLayout(1, 3));
        panSopra.setMaximumSize(new Dimension(1420, 300));
        JPanel cerca = new JPanel();
        JLabel searchlabel = new JLabel("Cerca:");
        searchlabel.setFont(new Font("Arial Black", Font.BOLD, 20));
        casella = new JTextField(20);
        cerca.add(searchlabel);
        cerca.add(casella);
        panSopra.add(cerca);

        panSopra.add(new JLabel(" "));

        JButton buttonNew = new JButton("ADD NEW");
        //*************+* BOTTONE AGGIUNGI NUOVA RIGA**************************
        buttonNew.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //debug
                System.out.print("PANNELLO PRODOTTI: categorie in sospeso: ");
                for (String cat : list_cat_new) {
                    System.out.print(cat + " ");
                }//debug

                form = new FormProdotti("ADD", null);
                form.setResizable(false);
                form.setVisible(true);
                form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            }
        });
        buttonNew.setFont(new Font("Arial Black", Font.BOLD, 15));
        panSopra.add(buttonNew);

        super.add(panSopra);

        //Tabella
        JPanel TitoloTab1 = new JPanel();
        TitoloTab1.setLayout(new GridLayout(1, 1));
        TitoloTab1.setBorder(new EmptyBorder(0, 100, 20, 100));

        String[] columnNames = {"sku", "Data reg.", "Nome", "Categoria", "Quantità", "Fornitore", "In Stock?", "Costo", "Note", "Quantità minima", "Modifica", "Cancella", "Ordina"};

        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column >= 10; //il numero di celle editabili...
            }
        };
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);

        model.addRow(data); // DA CANCELLARE

        try {
            refreshTab(); // Aggiorna tavola con  i fornitori del db;
        } catch (SQLException ex) {
            Logger.getLogger(AnagrafichePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setRowHeight(40); //altezza celle

        //X colonne che hanno pulsanti
        table.getColumnModel().getColumn(4).setCellRenderer(new CustomRender());

        table.getColumnModel().getColumn(6).setCellRenderer(new CustomStockRender());

        table.getColumnModel().getColumn(10).setCellRenderer(new ClientsTableButtonRenderer());
        table.getColumnModel().getColumn(10).setCellEditor(new ClientsTableRenderer(new JCheckBox()));

        table.getColumnModel().getColumn(11).setCellRenderer(new ClientsTableButtonRenderer());
        table.getColumnModel().getColumn(11).setCellEditor(new ClientsTableRenderer(new JCheckBox()));

        table.getColumnModel().getColumn(12).setCellRenderer(new ClientsTableButtonRenderer());
        table.getColumnModel().getColumn(12).setCellEditor(new ClientsTableRenderer(new JCheckBox()));

        JScrollPane sp = new JScrollPane(table);
        TitoloTab1.add(sp);

        //******* funzione di ricerca *******************+
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        casella.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent arg0) {
            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                String text = casella.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                String text = casella.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(text));
                }
            }
        });

        super.add(TitoloTab1);

    }

    public ImageIcon ImpostaImg(String nomeImmag) {

        ImageIcon icon = new ImageIcon((getClass().getResource(nomeImmag)));
        Image ImmagineScalata = icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        icon.setImage(ImmagineScalata);
        return icon;
    }

    public void refreshTab() throws SQLException {

        //Cancello vecchie righe...
        System.out.println("Numero di  record prima dell'aggiornamento  " + model.getRowCount());
        model.setRowCount(0);

        ProdottoDAO dao = new ProdottoDAO();

        for (Prodotto pro : dao.getAll()) {
            System.out.println(pro.getSku() + " " + pro.getDatareg() + " " + pro.getNome() + " " + pro.getCategoria() + " " + pro.getQty() + " DA DEFINIRE " + pro.isInstock() + "  " + pro.getCosto() + " " + pro.getNote() + "  " + pro.getQty_min());
            model.addRow(new Object[]{pro.getSku(), pro.getDatareg(), pro.getNome(), pro.getCategoria(), pro.getQty(), "DA DEFINIRE", pro.isInstock(), pro.getCosto(), pro.getNote(), pro.getQty_min(), "Modifica", "Cancella", "Ordina"});

        }
        System.out.println("Numero di  record prima dell'aggiornamento  " + model.getRowCount());

    }

    // RENDER DELLE QUANTITA'
    class CustomRender extends JButton implements TableCellRenderer {

        public CustomRender() {

        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            for (int i = 0; i <= 12; i++) {
                System.out.println("Valore colonna" + i + " " + table.getValueAt(row, i));
            }

            if (Integer.parseInt(table.getValueAt(row, 4).toString()) <= Integer.parseInt(table.getValueAt(row, 9).toString())) {

                setBackground(new Color(244, 80, 37));    // ROSSO        
            } else {
                setBackground(new Color(126, 169, 93));  // VERDE
            }

            setText(table.getValueAt(row, 4).toString());

            return this;
        }
    }

    // RENDER DI IN STOCK SI O NO       
    class CustomStockRender extends JButton implements TableCellRenderer {

        public CustomStockRender() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            if (Boolean.parseBoolean(table.getValueAt(row, 6).toString())) {
                setBackground(new Color(126, 169, 93));  // VERDE          

            } else {
                setBackground(new Color(244, 80, 37));    // ROSSO 
            }

            setText("");

            return this;
        }
    }

    class ClientsTableButtonRenderer extends JButton implements TableCellRenderer {

        public ClientsTableButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            if (getText().equals("Modifica")) {
                setIcon(ImpostaImg("/res/img/pencil.png"));
            } else if (getText().equals("Cancella")) {
                setIcon(ImpostaImg("/res/img/eraser.png"));
            } else if (getText().equals("Ordina")) {
                setIcon(ImpostaImg("/res/img/ordini.png"));
            }

            return this;
        }

    }

    public class ClientsTableRenderer extends DefaultCellEditor {

        private JButton button;
        private String label;
        private boolean clicked;
        private int row, col;
        private JTable table;

        public ClientsTableRenderer(JCheckBox checkBox) {
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
            if (button.getText().equals("Modifica")) {
                button.setIcon(ImpostaImg("/res/img/pencil.png"));
            } else if (button.getText().equals("Cancella")) {
                button.setIcon(ImpostaImg("/res/img/eraser.png"));
            } else if (button.getText().equals("Ordina")) {
                button.setIcon(ImpostaImg("/res/img/ordini.png"));
            }
            clicked = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (clicked) // SE CLICCATO QUEL BOTTONE:::::::::::::
            {
                //Se Ho premuto Cancella ....
                if (button.getText().equals("Cancella")) {
                    int OpzioneScelta = JOptionPane.showConfirmDialog(getComponent(), "Sicuro di voler cancellare la riga:\n [  "
                            + table.getValueAt(row, 0) + "  |   "
                            + table.getValueAt(row, 1) + "  |   "
                            + table.getValueAt(row, 2) + "  |   "
                            + table.getValueAt(row, 3) + "  |   "
                            + table.getValueAt(row, 4) + "  |   "
                            + table.getValueAt(row, 5) + "  |   "
                            + table.getValueAt(row, 6) + "  |   "
                            + table.getValueAt(row, 7) + "  |   "
                            + table.getValueAt(row, 8) + "  |   "
                            + table.getValueAt(row, 9) + "  |   "
                            + table.getValueAt(row, 10) + "  |   "
                            + table.getValueAt(row, 11) + "  ]");

                    if (OpzioneScelta == JOptionPane.OK_OPTION) {
                        System.out.println("OOOOOOOOKKKKKK CANCELLO");
                        ProdottoDAO daor = new ProdottoDAO();
                        try {
                            daor.remove(table.getValueAt(row, 0).toString());

                            refreshTab();
                        } catch (SQLException ex) {
                            Logger.getLogger(ProdottiPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (button.getText().equals("Modifica")) { // APRI FORM PER MODIFICARE RECORD

                    form = new FormProdotti("UPDATE", table.getValueAt(row, 0).toString());
                    form.setResizable(false);
                    form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    form.setVisible(true);

                } else if (button.getText().equals("Ordina")) { // APRI FORM PER MODIFICARE RECORD
                    JOptionPane.showMessageDialog(getComponent(), "Vai in ORDINI");
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

// ****************** LA FORM ***********************
    class FormProdotti extends javax.swing.JFrame {

        public String modalita;
        public String IdSelezionato;
        public JTextField casku;
        public JTextField casdatareg;      
        public JTextField casname;
        public JTextField casqty;        
        public JTextField ccosto;
        public JTextField cmin;
        public JTextField cforn;
        public JComboBox cat;
        
        private String FornitoreCorrente;

        /**
         * Creates new form FormProdotti
         */
        public FormProdotti() {
            try {
                initComponents();
            } catch (SQLException ex) {
                Logger.getLogger(ProdottiPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        private FormProdotti(String mod, String idSelected) {
            modalita = mod;
            IdSelezionato = idSelected;

            try {
                initComponents();
            } catch (SQLException ex) {
                Logger.getLogger(ProdottiPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            ImageIcon img = new ImageIcon(getClass().getResource("/res/img/logo-Icon.png"));
            setSize(900, 600);
            this.setIconImage(img.getImage());

            if (modalita.equals("UPDATE")) {
                System.out.println("Sono in modalità update ...");
                System.out.println(" Id selezionato: " + idSelected);
                FornitoreCorrente = setFormAsID(idSelected);
            }

        }


        private void initComponents() throws SQLException {
            
                JPanel panmain = new JPanel();
                panmain.setLayout(new BoxLayout(panmain, BoxLayout.Y_AXIS));
                JLabel l = new JLabel("PRODOTTO");
                l.setFont(new Font("Arial Black", Font.BOLD, 20));
                l.setAlignmentX(CENTER_ALIGNMENT);
                panmain.add(l);

                JPanel main = new JPanel();
                main.setLayout(new GridLayout(3, 3, 20, 10));

                JPanel psku = new JPanel();
                JLabel lsku = new JLabel("    SKU");
                casku = new JTextField(15);
                casku.setAlignmentX(RIGHT_ALIGNMENT);
                psku.add(lsku);
                psku.add(casku);
                main.add(psku);

                JPanel pdatareg = new JPanel();
                JLabel ldatareg = new JLabel("Data reg");
                casdatareg = new JTextField(15);
                casdatareg.setAlignmentX(RIGHT_ALIGNMENT);
                pdatareg.add(ldatareg);
                pdatareg.add(casdatareg);
                main.add(pdatareg);

                JPanel pname = new JPanel();
                JLabel lname = new JLabel("Nome");
                casname = new JTextField(15);
                casname.setAlignmentX(RIGHT_ALIGNMENT);
                pname.add(lname);
                pname.add(casname);
                main.add(pname);

                JPanel pqty = new JPanel();
                JLabel lqty = new JLabel("Quantità");
                casqty = new JTextField(15);
                casqty.setAlignmentX(RIGHT_ALIGNMENT);
                pqty.add(lqty);
                pqty.add(casqty);
                main.add(pqty);

                JPanel pcosto = new JPanel();
                JLabel lcosto = new JLabel("Costo");
                ccosto = new JTextField(15);
                ccosto.setAlignmentX(RIGHT_ALIGNMENT);
                pcosto.add(lcosto);
                pcosto.add(ccosto);
                main.add(pcosto);

                JPanel pmin = new JPanel();
                JLabel lmin = new JLabel("Qty. min");
                cmin = new JTextField(15);
                cmin.setAlignmentX(RIGHT_ALIGNMENT);
                pmin.add(lmin);
                pmin.add(cmin);
                main.add(pmin);
 
                JPanel pforn = new JPanel();
                JLabel lforn = new JLabel("Fornitore");
                cforn = new JTextField(15);
                cforn.setAlignmentX(RIGHT_ALIGNMENT);
                pforn.add(lforn);
                pforn.add(cforn);
                main.add(pforn);
                
                JPanel panelcat = new JPanel();                
                JLabel lcat = new JLabel("Categoria"); 
                panelcat.add(lcat);
                    
                cat = new JComboBox<>();
                cat.setFont(new Font("Arial Black", Font.BOLD, 15));
                cat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Categoria 1", "categoria2"}));
                cat.setForeground(Color.black);
                cat.setBackground(Color.DARK_GRAY);
                cat.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    }
                });
                
                panelcat.add(cat);   
                main.add(panelcat);
                

                panmain.add(main);
                add(panmain);
/*
                JPanel pandown = new JPanel();
                JLabel notext = new JLabel("Note");
                note = new JTextArea("");
                note.setAlignmentX(LEFT_ALIGNMENT);
                note.setLineWrap(true);
                note.setRows(5);
                note.setColumns(20);
                pandown.add(notext);
                JScrollPane scrollPane = new JScrollPane(note);
                pandown.add(scrollPane);

                pandown.add(new JLabel("                        "));

                JButton salva = new JButton("Conferma");
                salva.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (modalita.equals("ADD")) {
                            if (check()) {
                                getOggettoforFormSave();
                            }
                        } else if (check()) {
                            getOggettoforFormUpdate();
                        }

                    }
                });

                JButton annulla = new JButton("Annulla");
                annulla.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        form.setVisible(false);
                        form = null;

                    }
                });

                pandown.add(salva);
                pandown.add(annulla);

                pancliente.add(pandown);

                add(pancliente);      
                 */

        }

        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
            form.setVisible(false);

        }

        private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
            if (check()) {
                //Aggiungi riga
                if (modalita.equals("UPDATE")) {
                    getOggettoforFormUpdate();
                } else {
                    getOggettoforFormSave();
                }
            }

        }

        private boolean check() {
            if (jTextField2.getText().isEmpty() || jList1.isSelectionEmpty() || jList2.isSelectionEmpty() || jTextField6.getText().isEmpty() || jTextField5.getText().isEmpty() || jTextField7.getText().isEmpty() || jTextField9.getText().isEmpty() || jTextField9.getText().isEmpty() || jTextField4.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Compila tutti i campi! ['Note' è opzionale]");
                return false;
            }

            try { //Controlla se sono interi...
                Integer.parseInt(jTextField6.getText());
                Integer.parseInt(jTextField5.getText());
                Integer.parseInt(jTextField9.getText());
                Integer.parseInt(jTextField4.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Controlla che \"Quantità\", \"Gioni alla consegna\", \"qty minima\", \"qty in arrivo\", siano numeri validi. [ Per il costo usare \".\" per indicare la parte decimale ]");
                return false;
            }

            //controlla se sono float ...
            try {
                Float.parseFloat(jTextField7.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Controlla che le quantità ed il costo siano numeri validi! [Per il costo usare '.' per indicare la parte decimale]");
                return false;
            }

            return true;
        }

        public void getOggettoforFormSave() {
            /*
            Prodotto prod = new Prodotto(jTextField2.getText() , jList1.getSelectedValue(),Integer.parseInt(jTextField6.getText()),jCheckBox1.isSelected(),Integer.parseInt(jTextField5.getText()), Float.valueOf(jTextField7.getText()), jTextField8.getText(), Integer.parseInt(jTextField9.getText()), Integer.parseInt(jTextField4.getText()),"foto.png");
            ProdottoDAO dao = new ProdottoDAO();
            
       
            try {
                int a= JOptionPane.showConfirmDialog(this,"Dario, sei proprio sicuro?");
                 if(a==JOptionPane.YES_OPTION){
                    dao.add(prod, jList2.getSelectedValue()); 
                    form.setVisible(false);

                  }
            
        } catch (SQLException ex) {
            Logger.getLogger(AnagrafichePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        try {
            refreshTab();
        } catch (SQLException ex) {
            Logger.getLogger(AnagrafichePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
            
             */

        }

        private String setFormAsID(String idSelected) {

            ProdottoDAO dao = new ProdottoDAO();
            /*
        try {
           
            Prodotto prodotto = dao.getBySku(idSelected);
            jTextField1.setText(prodotto.getSku());
            jTextField2.setText(prodotto.getNome());
            jTextField3.setText(prodotto.getDatareg());
            jTextField4.setText(Integer.toString(prodotto.getQty_min()));
            jTextField5.setText(Integer.toString(prodotto.getGiorni_alla_consegna()));
            jTextField6.setText(Integer.toString(prodotto.getQty()));
            jTextField7.setText(String.valueOf(prodotto.getCosto()));
            jTextField8.setText(prodotto.getDescrizione());
            jTextField9.setText(Integer.toString(prodotto.getQty_inarrivo()));
            jCheckBox1.setSelected(prodotto.isInstock());
            jList1.setSelectedValue(prodotto.getCategoria(), true);
            jList2.setSelectedValue(prodotto.getId_fornitore(), true);
                       
        } catch (SQLException ex) {
            Logger.getLogger(AnagrafichePanel.class.getName()).log(Level.SEVERE, null, ex);
        }   */

            return jList2.getSelectedValue();

        }

        public void getOggettoforFormUpdate() {

            /*
            Prodotto prod = new Prodotto(jTextField1.getText(), FornitoreCorrente, jTextField2.getText() , jList1.getSelectedValue(),Integer.parseInt(jTextField6.getText()),jCheckBox1.isSelected(),Integer.parseInt(jTextField5.getText()), Float.valueOf(jTextField7.getText()), jTextField8.getText(), Integer.parseInt(jTextField9.getText()), Integer.parseInt(jTextField4.getText()),"foto.png");
            ProdottoDAO dao = new ProdottoDAO();       
        try {            
           int a= JOptionPane.showConfirmDialog(this,"Dario, sei proprio sicuro?");
           if(a==JOptionPane.YES_OPTION){
            dao.update(prod, jList2.getSelectedValue());               
             form.setVisible(false);
             System.out.println("La form:"+ form);

           }            
            
                       
        } catch (SQLException ex) {
            Logger.getLogger(AnagrafichePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            refreshTab();
        } catch (SQLException ex) {
            Logger.getLogger(AnagrafichePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
             */
        }

        // Variables declaration - do not modify                     
        private javax.swing.ButtonGroup buttonGroup1;
        private javax.swing.ButtonGroup buttonGroup2;
        private javax.swing.ButtonGroup buttonGroup3;
        private javax.swing.ButtonGroup buttonGroup4;
        private javax.swing.ButtonGroup buttonGroup5;
        private javax.swing.JButton jButton3;
        private javax.swing.JButton jButton4;
        private javax.swing.JCheckBox jCheckBox1;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JList<String> jList1;
        private javax.swing.JList<String> jList2;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JTextField jTextField2;
        private javax.swing.JTextField jTextField3;
        private javax.swing.JTextField jTextField4;
        private javax.swing.JTextField jTextField5;
        private javax.swing.JTextField jTextField6;
        private javax.swing.JTextField jTextField7;
        private javax.swing.JTextField jTextField8;
        private javax.swing.JTextField jTextField9;
        private java.awt.Label label1;
        // End of variables declaration                   
    }

}
