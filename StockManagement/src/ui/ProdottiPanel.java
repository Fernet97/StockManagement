/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import org.apache.commons.io.FilenameUtils;
import beans.Fornitore;
import beans.Prodotto;
import dao.FornitoreDAO;
import dao.OrdineDAO;
import dao.ProdottoDAO;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import static java.awt.Component.RIGHT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

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
    public String percorsofoto;
    private FramePrincipale frameprinc;
    public JRadioButton checkOnlyArriv;

    public ProdottiPanel() {

        try {
            File file = new File("./DATA/CONFIG/aikkop.aksn");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            list_cat_new = (ArrayList<String>) ois.readObject();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger("genlog").warning("ClassNotFoundException\n" + StockManagement.printStackTrace(ex));
        } catch (FileNotFoundException ex) {
            File file = new File("aikkop.aksn");
            Logger.getLogger("genlog").warning("FileNotFoundException\n" + StockManagement.printStackTrace(ex));
        } catch (IOException ex) {
            Logger.getLogger("genlog").warning("IOException\n" + StockManagement.printStackTrace(ex));
            list_cat_new = new ArrayList<>();
        }

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

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    refreshTab();
                } catch (SQLException ex) {
                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                }
            }
        };
        checkOnlyArriv = new JRadioButton("Solo prodotti in arrivo");
        checkOnlyArriv.addActionListener(actionListener);
        cerca.add(checkOnlyArriv);
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
                form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                if (!form.nessunacat) {
                    form.setLocationRelativeTo(null);
                    form.setAlwaysOnTop(true);
                    form.setResizable(false);
                    form.setVisible(true);
                    form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                }

            }
        });
        buttonNew.setFont(new Font("Arial Black", Font.BOLD, 15));
        panSopra.add(buttonNew);

        super.add(panSopra);

        //Tabella
        JPanel TitoloTab1 = new JPanel();
        TitoloTab1.setLayout(new GridLayout(1, 1));
        TitoloTab1.setBorder(new EmptyBorder(0, 100, 20, 100));

        String[] columnNames = {"sku", "Data reg.", "Nome", "Categoria", "Quantità", "Fornitore", "In Stock?", "Costo", "Note", "Quantità in arrivo", "Modifica", "Cancella", "Ordina"};

        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column >= 10; //il numero di celle editabili...
            }

            public Class getColumnClass(int column) {
                switch (column) {

                    case 6:
                        return Boolean.class;//instock
                        
                    case 7:
                        return BigDecimal.class;//costo
                        
                    case 4:
                        return Integer.class;//qty

                    case 9:
                        return Integer.class;//qty in arrivo


                    default:
                        return String.class;
                }
            }

        };
        table = new JTable(model){
         public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component comp = super.prepareRenderer(renderer, row, column);
            if(column == 4 || column == 6 || column == 7 || column == 9) return comp;
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
                table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setRowHeight(30);
        table.getTableHeader().setReorderingAllowed(false);


        try {
            refreshTab(); // Aggiorna tavola con  i fornitori del db;
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLExLogception\n" + StockManagement.printStackTrace(ex));
        }

        table.setRowHeight(40); //altezza celle

        //X colonne che hanno semafori
        table.getColumnModel().getColumn(4).setCellRenderer(new CustomRender());

        table.getColumnModel().getColumn(6).setCellRenderer(new CustomStockRender());

        table.getColumnModel().getColumn(7).setCellRenderer(new CustomCostoRender()); // costo 

        table.getColumnModel().getColumn(9).setCellRenderer(new CustomInArrivoRender());

        //X colonne che hanno pulsanti
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
       rowSorter.setSortable(10, false);// toglie il sort alle colonne di modifica cancella ed ordina
       rowSorter.setSortable(11, false);
       rowSorter.setSortable(12, false);
        //table.setAutoCreateRowSorter(true);
        // TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
          //table.setRowSorter(sorter);
          rowSorter.setComparator(1, new Comparator<String>()
          {
             @Override
             public int compare(String o1, String o2)
             {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime ldt1, ldt2;
                ldt1 = LocalDateTime.parse(o1, formatter);
                ldt2 = LocalDateTime.parse(o2, formatter);
                return ldt1.compareTo(ldt2);
             }
          });

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
        
       model.setRowCount(0);

        ProdottoDAO dao = new ProdottoDAO();
        FornitoreDAO forndao = new FornitoreDAO();

        OrdineDAO daoo = new OrdineDAO();

        
        
        for (Prodotto pro : dao.getAll()) {
            
            Fornitore forni = forndao.getByID(daoo.getFPr(pro.getSku()));
            String forny = forni.getIdfornitore() + "|  " + forni.getFullname();

            String snote = "";
            if (pro.getNote().length() > 0) {
                snote = "Sì";
            } else {
                snote = "No";
            }

            if (checkOnlyArriv.isSelected()) {
                if (daoo.getQtyArrSku(pro.getSku()) > 0) { //Aggiungo solo righe che hanno prodotti in arrivo
                    // CONDENSA 0s
                    BigDecimal bd = new BigDecimal(String.valueOf(pro.getCosto()));

                    model.addRow(new Object[]{pro.getSku(), pro.getDatareg(), pro.getNome(), pro.getCategoria(), pro.getQty(), forny, pro.isInstock(), bd.toPlainString(), snote, daoo.getQtyArrSku(pro.getSku()), "Modifica", "Cancella", "Ordina"});

                }

            } else {
                // CONDENSA 0s
                BigDecimal bd = new BigDecimal(String.valueOf(pro.getCosto()));
                model.addRow(new Object[]{pro.getSku(), pro.getDatareg(), pro.getNome(), pro.getCategoria(), pro.getQty(), forny, pro.isInstock(), bd.toPlainString(), snote, daoo.getQtyArrSku(pro.getSku()), "Modifica", "Cancella", "Ordina"});

            }
            


        }

        try {
            File file = new File("./DATA/CONFIG/aikkop.aksn");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            list_cat_new = (ArrayList<String>) ois.readObject();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger("genlog").warning("ClassNotFoundException\n" + StockManagement.printStackTrace(ex));
        } catch (FileNotFoundException ex) {
            File file = new File("aikkop.aksn");
            Logger.getLogger("genlog").warning("FileNotFoundException\n" + StockManagement.printStackTrace(ex));
        } catch (IOException ex) {
            Logger.getLogger("genlog").warning("IOException\n" + StockManagement.printStackTrace(ex));
            list_cat_new = new ArrayList<>();
        }

    }

    // RENDER DELLE QUANTITA'
    class CustomRender extends JButton implements TableCellRenderer {

        public CustomRender() {

        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            ProdottoDAO prodao = new ProdottoDAO();
            Prodotto p = null;
            try {
                p = prodao.getBySku(table.getValueAt(row, 0).toString());
            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
            }

            if (Integer.parseInt(table.getValueAt(row, 4).toString()) <= p.getQty_min()) {
                setForeground(Color.white);

                setBackground(new Color(198, 59, 52));    // ROSSO        
            } else {
               setBackground(new Color(98, 190, 92));  // VERDE
                setForeground(Color.black);
            }

            setText(table.getValueAt(row, 4).toString());

            return this;
        }
    }

    // RENDER DELLE QUANTITA'
    class CustomCostoRender extends JButton implements TableCellRenderer {

        public CustomCostoRender() {

        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            setEnabled(false);
            setFont(new Font("Arial Black", Font.BOLD, 11));

            if (table.getValueAt(row, 7).toString().contains(".") == true) {
                char punto = (char) table.getValueAt(row, 7).toString().indexOf('.');

                //table.getValueAt(row, 7).toString().trim();
                if (table.getValueAt(row, 7).toString().substring(punto).length() > 5) {
                    setText(table.getValueAt(row, 7).toString().substring(0, punto + 5) + " €");

                    return this;
                }

            }

            setText(table.getValueAt(row, 7).toString() + " €");

            return this;
        }
    }

    // RENDER DI IN STOCK SI O NO       
    class CustomStockRender extends JButton implements TableCellRenderer {

        public CustomStockRender() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            if ((table.getValueAt(row, 6).toString().equals("true"))) {
                setBackground(new Color(98, 190, 92));  // VERDE         

            } else {
                setBackground(new Color(198, 59, 52));    // ROSSO   
                                setForeground(Color.white);

            }

            setText("");

            return this;
        }
    }

    // RENDER DI QTY IN ARRIVO O NO     
    class CustomInArrivoRender extends JButton implements TableCellRenderer {

        public CustomInArrivoRender() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            if (Integer.parseInt(table.getValueAt(row, 9).toString()) > 0) {
                 setBackground(new Color(198, 59, 52));    // ROSSO   
                setText(table.getValueAt(row, 9).toString());
                setForeground(Color.white);

            } else {
               setBackground(new Color(98, 190, 92));  // VERDE     

                setText("");

            }

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
                setIcon(ImpostaImg("/res/img/ordini_a.png"));
            }
            setText("");

            return this;
        }

    }

    public class ClientsTableRenderer extends DefaultCellEditor {

        private JButton button;
        private String label;
        private boolean clicked;
        private int row, col;
        private JTable table;
        private String prodSceltoxOrdine;
        private JDialog vaiarod;
        private JComboBox<String> jComboBox;

        public ClientsTableRenderer(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();    // ECCO IL BOTTONE
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        fireEditingStopped();
                    } catch (IndexOutOfBoundsException es) {
                        Logger.getLogger("genlog").warning("IndexOutOfBoundsException\n- - - - - BUG SUPREMO - - - - -\n" + StockManagement.printStackTrace(es));
                    }

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
                button.setIcon(ImpostaImg("/res/img/ordini_a.png"));
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
                        ProdottoDAO daor = new ProdottoDAO();
                        try {
                            daor.remove(table.getValueAt(row, 0).toString());
                            refreshTab();
                        } catch (SQLException ex) {
                            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                        }
                    }
                } else if (button.getText().equals("Modifica")) { // APRI FORM PER MODIFICARE RECORD

                    form = new FormProdotti("UPDATE", table.getValueAt(row, 0).toString());
                    form.setResizable(false);
                    form.setLocationRelativeTo(null);
                    form.setAlwaysOnTop(true);
                    form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    form.setVisible(true);

                } else if (button.getText().equals("Ordina")) {
                    frameprinc.OrdiniStatus = false;
                    //Se un fornitore non è definito
                    if (table.getValueAt(row, 5).toString().equals("null|  null")) {
                        vaiarod = new JDialog();
                        vaiarod.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        vaiarod.setResizable(false);
                        vaiarod.setModal(true);
                        vaiarod.setLocationRelativeTo(null);

                        vaiarod.setTitle("Seleziona un fornitore a cui associare il prodotto");
                        vaiarod.setSize(new Dimension(500, 150));
                        vaiarod.setLayout(new GridBagLayout());
                        FornitoreDAO forndao = new FornitoreDAO();
                        jComboBox = new JComboBox<>();
                        jComboBox.setFont(new Font("Arial Black", Font.BOLD, 20));
                        vaiarod.add(jComboBox);

                        vaiarod.add(new JLabel("       "));

                        JButton confermaforn = new JButton("Conferma");
                        confermaforn.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                vaiarod.dispose();
                                frameprinc.VaiAOrdiniconProdFORNULL(jComboBox.getSelectedItem().toString(), prodSceltoxOrdine);

                            }
                        });
                        confermaforn.setFont(new Font("Arial Black", Font.BOLD, 20));
                        vaiarod.add(confermaforn);

                        prodSceltoxOrdine = table.getValueAt(row, 0).toString() + "|" + table.getValueAt(row, 2).toString();

                        try {
                            for (Fornitore f : forndao.getAll()) {
                                jComboBox.addItem(f.getIdfornitore() + "|" + f.getFullname());
                            }
                            if (jComboBox.getItemCount() == 0) {
                                JOptionPane.showMessageDialog(null, "Non hai creato ancora un fornitore!");
                                return new String(label);
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                        }

                        vaiarod.setVisible(true);

                    } // SE IL FORNITORE ERA GIA' DEFINITO
                    else {
                        frameprinc.VaiAOrdiniconProdFornCEH(table.getValueAt(row, 0).toString());

                    }

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
            try{
                super.fireEditingStopped();}
            catch(Exception e){
                Logger.getLogger("genlog").warning(e.getMessage());

            } 
        }
    }

// ****************** LA FORM ***********************
    class FormProdotti extends JDialog {

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

        private JTextArea note;
        private JCheckBox inStock;
        private JCheckBox negozio;
        private JButton bfoto;
        private String[] stringsP;
        private String[] list_prod;
        public boolean nessunacat;

        /**
         * Creates new form FormProdotti
         */
        public FormProdotti() {

            try {

                initComponents();

            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
            }

        }

        private FormProdotti(String mod, String idSelected) {

            modalita = mod;
            IdSelezionato = idSelected;

            try {

                if (setCategoryList()) {

                    setModal(true);
                    initComponents();

                    cforn.setFocusable(false);

                    casku.setEditable(false);
                    casku.setFocusable(false);

                    casdatareg.setEditable(false);
                    casdatareg.setFocusable(false);

                    casku.setBackground(Color.DARK_GRAY);
                    casdatareg.setBackground(Color.DARK_GRAY);

                    if (modalita.equals("UPDATE")) {
                        setFormAsID(idSelected);
                        cat.setEnabled(false);
                        cat.setBackground(Color.darkGray);
                    } else {
                        casku.setText("AUTOGENERATO");
                        casku.setForeground(Color.yellow);

                        casdatareg.setText("AUTOGENERATO");
                        casdatareg.setForeground(Color.yellow);

                    }

                    ImageIcon img = new ImageIcon(getClass().getResource("/res/img/logo-Icon.png"));
                    setSize(600, (650));
                    this.setIconImage(img.getImage());

                } else {
                    dispose();
                }

            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
            }
        }

        private boolean setCategoryList() throws SQLException {

            ProdottoDAO dao = new ProdottoDAO();
            // Cat dal db + Cat dinamiche aggiunte prima in java
            list_prod = new String[dao.getAll().size() + list_cat_new.size() + 1];
            for (int y = 0; y < list_cat_new.size(); y++) { //Aggiungo le categorie aggiunte prima dinamicamente..
                list_prod[y] = list_cat_new.get(y);
            }

            Iterator<Prodotto> iter = dao.getAll().iterator();
            int i = 0;
            int sum = list_cat_new.size();
            while (iter.hasNext()) { //Aggiungo prima le categorie estrapolate dal db..
                list_prod[sum + i] = iter.next().getCategoria();
                i++;
            }
            stringsP = Arrays.stream(list_prod).distinct().toArray(String[]::new);;

            // Se non ci sono categorie (c'è solo la stringa vuota)
            if (stringsP.length == 1) {
                JOptionPane.showMessageDialog(null, "Devi aggiungere prima almeno una categoria!!");
                nessunacat = true;
                return false;
            }
            nessunacat = false;
            return true;

        }

        private void initComponents() throws SQLException {
            percorsofoto = null;
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
            ((AbstractDocument) casqty.getDocument()).setDocumentFilter(new DocumentFilter() {
                    Pattern regEx = Pattern.compile("\\d*");

                    @Override
                    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                        Matcher matcher = regEx.matcher(text);
                        if (!matcher.matches()) {
                            return;
                        }
                        super.replace(fb, offset, length, text, attrs);
                    }
                });
            
            
            
            casqty.setAlignmentX(RIGHT_ALIGNMENT);
            pqty.add(lqty);
            pqty.add(casqty);
            main.add(pqty);

            JPanel pcosto = new JPanel();
            JLabel lcosto = new JLabel("Costo unitario €");
            ccosto = new JTextField(15);
            ccosto.setAlignmentX(RIGHT_ALIGNMENT);
            ((AbstractDocument) ccosto.getDocument()).setDocumentFilter(new DocumentFilter() {
            Pattern regEx = Pattern.compile("^\\d*(\\.\\d*)?$");
                    @Override
                    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                        char punto = '.';
                        int x = 0;
                        int counter = 0;  
                        
                        Matcher matcher = regEx.matcher(text);
                        
                        if (!matcher.matches()) {
                            return;
                        }
                        
                       while ((x = (ccosto.getText().indexOf(punto, x) + 1)) > 0) {           
                            counter++;
                            if(counter >1){
                                return;
                            }
                        }
                                   
                        super.replace(fb, offset, length, text, attrs);
                    }
                }); 
            
            ccosto.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent arg0) {}

                @Override
                public void removeUpdate(DocumentEvent arg0) {
                          // Highlighter highlighter = ccosto.getHighlighter();
                          //highlighter.removeAllHighlights();              
                         System.out.println("removeeeeeeeee");              
                }

                    @Override
                    public void insertUpdate(DocumentEvent e) { 
                        char punto = '.';
                        int x = 0;
                        int counter = 0;    
                        
                    try {
                    while ((x = (ccosto.getText().indexOf(punto, x) + 1)) > 0) {           
                            counter++;
                            if(counter >1){
                                Highlighter highlighter = ccosto.getHighlighter();
                                HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
                                highlighter.addHighlight(x-1 , x, painter);                                
                            }

                        }

                    } catch (BadLocationException ex) {
                       Logger.getLogger("genlog").warning("BadLocationException\n" + StockManagement.printStackTrace(ex));
                    }                  
            }
            });

            
            pcosto.add(lcosto);
            pcosto.add(ccosto);
            main.add(pcosto);

            JPanel pmin = new JPanel();
            JLabel lmin = new JLabel("Qty. min");
            cmin = new JTextField(15);
            cmin.setAlignmentX(RIGHT_ALIGNMENT);
            ((AbstractDocument) cmin.getDocument()).setDocumentFilter(new DocumentFilter() {
                    Pattern regEx = Pattern.compile("\\d*");

                    @Override
                    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                        Matcher matcher = regEx.matcher(text);
                        if (!matcher.matches()) {
                            return;
                        }
                        super.replace(fb, offset, length, text, attrs);
                    }
                });            

            pmin.add(lmin);
            pmin.add(cmin);
            main.add(pmin);

            JPanel pforn = new JPanel();
            JLabel lforn = new JLabel("Fornitore");
            cforn = new JTextField(15);
            cforn.setText("DA DEFINIRE");
            cforn.setBackground(Color.darkGray);
            cforn.setEditable(false);
            cforn.setAlignmentX(RIGHT_ALIGNMENT);
            pforn.add(lforn);
            pforn.add(cforn);
            main.add(pforn);

            JPanel panelcat = new JPanel();
            JLabel lcat = new JLabel("Categoria");
            panelcat.add(lcat);

            cat = new JComboBox<>();
            cat.setFont(new Font("Arial Black", Font.BOLD, 15));

            cat.setModel(new javax.swing.DefaultComboBoxModel<>(stringsP));
            cat.setForeground(Color.black);
            cat.setBackground(Color.DARK_GRAY);
            cat.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                }
            });

            panelcat.add(cat);
            main.add(panelcat);

            negozio = new JCheckBox("Negozio");
            negozio.setFont(new Font("Arial Black", Font.BOLD, 15));
            main.add(negozio);

            panmain.add(main);

            // PAN DOWN CHE HA NOTE E JPANEL X FOTO           
            JPanel pandown = new JPanel();
            pandown.setLayout(new GridLayout(2, 2, 20, 10));

            JPanel pnote = new JPanel();
            JLabel notext = new JLabel("Note");
            note = new JTextArea("");
            note.setAlignmentX(LEFT_ALIGNMENT);
            note.setLineWrap(true);
            note.setRows(5);
            note.setColumns(20);
            pandown.add(notext);
            JScrollPane scrollPane = new JScrollPane(note);
            pnote.add(notext);
            pnote.add(scrollPane);
            pandown.add(pnote);

            bfoto = new JButton();
            bfoto.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setAlwaysOnTop(false);

                    try {
                        JFileChooser jFileChooser = new JFileChooser();
                        jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp", "tiff"));
                        jFileChooser.setCurrentDirectory(new File("./"));
                        jFileChooser.setAcceptAllFileFilterUsed(false);

                        JFrame framechooser = new JFrame();
                        framechooser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        int result = jFileChooser.showOpenDialog(framechooser);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            // File selezionato
                            File selectedFile = jFileChooser.getSelectedFile();
                            percorsofoto = selectedFile.getAbsolutePath();

                            ImageIcon icon = new ImageIcon(percorsofoto);
                            Image ImmagineScalata = icon.getImage().getScaledInstance(90, 80, Image.SCALE_DEFAULT);
                            icon.setImage(ImmagineScalata);
                            bfoto.setIcon(icon);
                            setAlwaysOnTop(true);
                            framechooser.dispose();

                        }
                        else {
                            jFileChooser.cancelSelection();
                            framechooser.dispose();
                        }
                        
                        
                    } catch (StringIndexOutOfBoundsException ex) {// mi dava -1 alla riga 664
                        Logger.getLogger("userlog").warning("StringIndexOutOfBoundsException: percorso foto \n" + StockManagement.printStackTrace(ex));

                    }

                }
            });
            ImageIcon icon = new ImageIcon(getClass().getResource("/res/img/upload.png"));
            Image ImmagineScalata = icon.getImage().getScaledInstance(90, 80, Image.SCALE_DEFAULT);
            icon.setImage(ImmagineScalata);
            bfoto.setIcon(icon);

            pandown.add(bfoto);

            JPanel panstock = new JPanel();
            panstock.add(new JLabel("        "));
            inStock = new JCheckBox("In Stock");
            inStock.setFont(new Font("Arial Black", Font.BOLD, 15));
            panstock.add(inStock);
            pandown.add(panstock);

            JPanel Buttons = new JPanel(new GridBagLayout());
            Buttons.add(new JLabel("     "));
            JButton salva = new JButton("Conferma");
            salva.setFont(new Font("Arial Black", Font.BOLD, 15));

            salva.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (check()) {
                        if (modalita.equals("UPDATE")) {
                            try {
                                getOggettoforFormUpdate();
                            } catch (InterruptedException ex) {
                                Logger.getLogger("genlog").warning("InterruptedException\n" + StockManagement.printStackTrace(ex));
                            }
                        } else {
                            getOggettoforFormSave();
                        }
                    }
                }
            }
            );

            JButton annulla = new JButton("Annulla");
            annulla.setFont(new Font("Arial Black", Font.BOLD, 15));
            annulla.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    form.dispose();

                }
            });

            Buttons.add(salva);
            Buttons.add(annulla);
            Buttons.add(new JLabel("     "));

            pandown.add(Buttons);

            panmain.add(main);
            panmain.add(pandown);

            add(panmain);

        }

        // casname.getText(), Integer.parseInt(casqty.getText()), cat.getSelectedItem().toString(), Innegozio, Float.valueOf(ccosto.getText()), Integer.parseInt(cmin.getText()), note.getText(), percorsofoto, InStock);
        private boolean check() {
            if (casname.getText().isEmpty() || casqty.getText().isEmpty() || ccosto.getText().isEmpty() || cmin.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Compila tutti i campi! ['Note' è opzionale]");
                return false;
            }

            if (cat.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Devi scegliere una categoria!!");
                cat.requestFocus();
                return false;
            }

            if (casname.getText().length() > 45) {
                JOptionPane.showMessageDialog(this, "Nome troppo lungo!");
                casname.requestFocus();
                return false;
            }

            if (casqty.getText().length() > 10) {
                JOptionPane.showMessageDialog(this, "Quantità esagerata!");
                casqty.requestFocus();
                return false;
            }

            if (ccosto.getText().contains(".") == true) {
                char punto = (char) ccosto.getText().indexOf('.');

                if (ccosto.getText().substring(0, punto).length() > 7) {
                    JOptionPane.showMessageDialog(this, "Troppe cifre nella parte intera!");
                    ccosto.requestFocus();
                    return false;
                }

                if (ccosto.getText().substring(punto).length() > 5) {
                    JOptionPane.showMessageDialog(this, "Troppe cifre nella parte decimale!");
                    ccosto.requestFocus();
                    return false;

                }

            } else {

                if (ccosto.getText().length() > 7) {
                    JOptionPane.showMessageDialog(this, "Troppe cifre!");
                    ccosto.requestFocus();
                    return false;
                }

            }

            if (cmin.getText().length() > 10) {
                JOptionPane.showMessageDialog(this, "Quantità minima non valida!");
                cmin.requestFocus();
                return false;
            }

            if (note.getText().length() > 65535) {
                JOptionPane.showMessageDialog(this, "Note troppo lunghe!");
                note.requestFocus();
                return false;
            }

            try { //Controlla se sono interi...
                Integer.parseInt(casqty.getText());
                Integer.parseInt(cmin.getText());

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Controlla che \"Quantità\",  \"qty minima\",  siano numeri validi. [ Per il costo usare \".\" per indicare la parte decimale ]");
                Logger.getLogger("genlog").warning("NumberFormatException\n" + StockManagement.printStackTrace(e));
                return false;
            }

            //controlla se sono float ...
            try {
                Double.parseDouble(ccosto.getText());
            } catch (NumberFormatException e) {
                Logger.getLogger("genlog").warning("NumberFormatException\n" + StockManagement.printStackTrace(e));
                JOptionPane.showMessageDialog(this, "Controlla che le quantità ed il costo siano numeri validi! [Per il costo usare '.' per indicare la parte decimale]");
                return false;
            }

            return true;
        }
        
        

        public void getOggettoforFormSave() {

            // public Prodotto(String nome, int qty, String Categoria, int instock, float costo, int qty_min, String note, String foto, int negozio)
            Prodotto prod;
            try {
                int a = JOptionPane.showConfirmDialog(this, "Dario, sei proprio sicuro?");
                if (a == JOptionPane.YES_OPTION) {
                    prod = new Prodotto(casname.getText(), Integer.parseInt(casqty.getText()), cat.getSelectedItem().toString(), inStock.isSelected(), Double.parseDouble(ccosto.getText()), Integer.parseInt(cmin.getText()), note.getText(), percorsofoto, negozio.isSelected());
                    ProdottoDAO dao = new ProdottoDAO();

                    if (percorsofoto != null) {
                        Path sourcepath = Paths.get(percorsofoto);
                        String estensione = FilenameUtils.getExtension(percorsofoto).toString();
                        // POSSO AGGIUNGERE SOLO FILE PNG
                        Path destinationepath = Paths.get("./DATA/IMG/" + prod.getSku().substring(0, prod.getSku().indexOf("-")) + "." + estensione);

                        //copia del file
                        Files.copy(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
                        percorsofoto = destinationepath.toString();

                        prod.setFoto(percorsofoto);
                    }
                    dao.add(prod);
                    form.dispose();

                }

                refreshTab();

            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
            } catch (InterruptedException ex) {
                Logger.getLogger("genlog").warning("InterruptedException\n" + StockManagement.printStackTrace(ex));
            } catch (IOException ex) {
                Logger.getLogger("genlog").warning("IOException\n" + StockManagement.printStackTrace(ex));
            }

        }

        private void setFormAsID(String idSelected) {

            ProdottoDAO dao = new ProdottoDAO();

            try {

                Prodotto prodotto = dao.getBySku(idSelected);
                casku.setText(prodotto.getSku());
                casname.setText(prodotto.getNome());
                casdatareg.setText(prodotto.getDatareg());
                cmin.setText(Integer.toString(prodotto.getQty_min()));
                casqty.setText(Integer.toString(prodotto.getQty()));

                // CONDENSA 0s
                BigDecimal bd = new BigDecimal(String.valueOf(prodotto.getCosto()));
                System.out.println("il grande decimalo " + bd.toString());

                if (bd.toPlainString().contains(".") == true) {
                    char punto = (char) bd.toPlainString().indexOf('.');
                    if (bd.toPlainString().substring(punto).length() > 5) {
                        ccosto.setText(bd.toPlainString().substring(0, punto + 5));
                    }
                    else ccosto.setText(bd.toPlainString());

                }
                else ccosto.setText(bd.toPlainString());
                
                
                note.setText(prodotto.getNote());
                inStock.setSelected(prodotto.isInstock());

                OrdineDAO daoo = new OrdineDAO();
                FornitoreDAO forndao = new FornitoreDAO();
                Fornitore forni = forndao.getByID(daoo.getFPr(prodotto.getSku()));
                cforn.setText(forni.getIdfornitore() + "|   " + forni.getFullname());

                cat.setSelectedItem(prodotto.getCategoria());

                negozio.setSelected(prodotto.isNegozio());

                cat.setSelectedItem(prodotto.getCategoria());
                percorsofoto = prodotto.getFoto();
                ImageIcon icon;
                if (percorsofoto == null || percorsofoto.equals("null") || percorsofoto.equals("NULL")) {
                    icon = new ImageIcon(getClass().getResource("/res/img/upload.png"));
                } else {
                    icon = new ImageIcon(percorsofoto);
                }
                Image ImmagineScalata = icon.getImage().getScaledInstance(90, 80, Image.SCALE_DEFAULT);
                icon.setImage(ImmagineScalata);
                bfoto.setIcon(icon);

            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
            }

        }

        public void getOggettoforFormUpdate() throws InterruptedException {

            ProdottoDAO dao = new ProdottoDAO();
            try {
                int a = JOptionPane.showConfirmDialog(this, "Dario, sei proprio sicuro?");
                if (a == JOptionPane.YES_OPTION) {
                    Prodotto prod = new Prodotto(casku.getText(), casdatareg.getText(), casname.getText(), Integer.parseInt(casqty.getText()), cat.getSelectedItem().toString(), inStock.isSelected(), Double.parseDouble(ccosto.getText()), Integer.parseInt(cmin.getText()), note.getText(), percorsofoto, negozio.isSelected());

                    if (percorsofoto != null && !percorsofoto.equals("NULL") && !percorsofoto.equals("null")) {
                        Path sourcepath = Paths.get(percorsofoto);
                        String estensione = FilenameUtils.getExtension(percorsofoto).toString();
                        Path destinationepath = Paths.get("./DATA/IMG/" + prod.getSku().substring(0, prod.getSku().indexOf("-")) + "." + estensione);

                        //copia del file
                        Files.copy(sourcepath, destinationepath, StandardCopyOption.REPLACE_EXISTING);
                        percorsofoto = destinationepath.toString();

                        prod.setFoto(percorsofoto);
                    }

                    dao.update(prod);
                    form.dispose();

                }

            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
            } catch (IOException ex) {
                Logger.getLogger("genlog").warning("IOException\n" + StockManagement.printStackTrace(ex));
            }

          try {
                refreshTab();
            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException" + StockManagement.printStackTrace(ex));
            }

        }

    }

    public void ViewOnlyInArrivo() {

        checkOnlyArriv.setSelected(true);

        try {
            refreshTab();
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        }

    }

    public void setComunicator(FramePrincipale princ) {
        frameprinc = princ;

    }

}
