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
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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
                form.setLocationRelativeTo(null);
                form.setAlwaysOnTop(true);
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
            Logger.getLogger("genlog").warning("SQLException\n" + ex);
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
        model.setRowCount(0);

        ProdottoDAO dao = new ProdottoDAO();
        FornitoreDAO forndao = new FornitoreDAO();

        OrdineDAO daoo = new OrdineDAO();

        for (Prodotto pro : dao.getAll()) {
            Fornitore forni = forndao.getByID(daoo.getFPr(pro.getSku()));
            String forny = forni.getIdfornitore() + "|  " + forni.getFullname();

            // CONDENSA 0s
            BigDecimal bd = new BigDecimal(String.valueOf(pro.getCosto()));
            model.addRow(new Object[]{pro.getSku(), pro.getDatareg(), pro.getNome(), pro.getCategoria(), pro.getQty(), forny, pro.isInstock(), bd.toPlainString(), pro.getNote(), pro.getQty_min(), "Modifica", "Cancella", "Ordina"});

        }

    }

    // RENDER DELLE QUANTITA'
    class CustomRender extends JButton implements TableCellRenderer {

        public CustomRender() {

        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

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

            if ((table.getValueAt(row, 6).toString().equals("true"))) {
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
        private String prodSceltoxOrdine;
        private JFrame vaiarod;
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
                        Logger.getLogger("genlog").warning("IndexOutOfBoundsException\n" + es);
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
                        ProdottoDAO daor = new ProdottoDAO();
                        try {
                            daor.remove(table.getValueAt(row, 0).toString());

                            refreshTab();
                        } catch (SQLException ex) {
                            Logger.getLogger("genlog").warning("SQLException\n" + ex);
                        }
                    }
                } else if (button.getText().equals("Modifica")) { // APRI FORM PER MODIFICARE RECORD

                    form = new FormProdotti("UPDATE", table.getValueAt(row, 0).toString());
                    form.setResizable(false);
                    form.setLocationRelativeTo(null);
                    form.setAlwaysOnTop(true);
                    form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    form.setVisible(true);

                } else if (button.getText().equals("Ordina")) { // APRI FORM PER MODIFICARE RECORD
                    //Se un fornitore non è definito
                    if (table.getValueAt(row, 5).toString().equals("null|  null")) {
                        vaiarod = new JFrame();
                        vaiarod.setLocationRelativeTo(null); 
                        vaiarod.setResizable(false);
                        vaiarod.setAlwaysOnTop(true);
                        vaiarod.setSize(new Dimension(300, 300));
                        vaiarod.setVisible(true);
                        vaiarod.setLayout(new GridLayout(2, 1));
                        vaiarod.add(new JLabel("Seleziona un fornitore a cui associare il prodotto"));
                        FornitoreDAO forndao = new FornitoreDAO();
                        jComboBox = new JComboBox<>();
                        vaiarod.add(jComboBox);

                        prodSceltoxOrdine = table.getValueAt(row, 0).toString() + "|" + table.getValueAt(row, 2).toString();

                        try {
                            for (Fornitore f : forndao.getAll()) {
                                jComboBox.addItem(f.getIdfornitore() + "|" + f.getFullname());
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger("genlog").warning("SQLException\n" + ex);
                        }

                        jComboBox.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                frameprinc.VaiAOrdiniconProdFORNULL(jComboBox.getSelectedItem().toString(), prodSceltoxOrdine);
                                vaiarod.setVisible(false);

                            }
                        });

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

        private JTextArea note;
        private JCheckBox inStock;
        private JCheckBox negozio;
        private JButton bfoto;

        /**
         * Creates new form FormProdotti
         */
        public FormProdotti() {

            try {
                initComponents();
            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + ex);
            }

        }

        private FormProdotti(String mod, String idSelected) {
            modalita = mod;
            IdSelezionato = idSelected;

            try {
                initComponents();
                casku.setEditable(false);
                casdatareg.setEditable(false);
                casku.setBackground(Color.DARK_GRAY);
                casdatareg.setBackground(Color.DARK_GRAY);

                if (modalita.equals("UPDATE")) {
                    setFormAsID(idSelected);
                    cat.setEnabled(false);
                    cat.setBackground(Color.darkGray);
                }

            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + ex);
            }

            ImageIcon img = new ImageIcon(getClass().getResource("/res/img/logo-Icon.png"));
            setSize(600, (650));
            this.setIconImage(img.getImage());

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

            ProdottoDAO dao = new ProdottoDAO();
            // Cat dal db + Cat dinamiche aggiunte prima in java
            String[] list_prod = new String[dao.getAll().size() + list_cat_new.size() + 1];
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
            String[] stringsP = Arrays.stream(list_prod).distinct().toArray(String[]::new);;

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
                    try {
                        JFileChooser jFileChooser = new JFileChooser();
                        jFileChooser.setCurrentDirectory(new File("./"));
                        int result = jFileChooser.showOpenDialog(new JFrame());
                        if (result == JFileChooser.APPROVE_OPTION) {
                            // File selezionato
                            File selectedFile = jFileChooser.getSelectedFile();
                            percorsofoto = selectedFile.getAbsolutePath();

                            ImageIcon icon = new ImageIcon(percorsofoto);
                            Image ImmagineScalata = icon.getImage().getScaledInstance(90, 80, Image.SCALE_DEFAULT);
                            icon.setImage(ImmagineScalata);
                            bfoto.setIcon(icon);

                        }
                    } catch (StringIndexOutOfBoundsException ex) {// mi dava -1 alla riga 664
                        Logger.getLogger("userlog").warning("StringIndexOutOfBoundsException: percorso foto \n" + ex);
                        ex.printStackTrace();
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
                                Logger.getLogger("genlog").warning("InterruptedException\n" + ex);
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
                    form.setVisible(false);

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

            }

            try { //Controlla se sono interi...
                Integer.parseInt(casqty.getText());
                Integer.parseInt(cmin.getText());

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Controlla che \"Quantità\",  \"qty minima\",  siano numeri validi. [ Per il costo usare \".\" per indicare la parte decimale ]");
                Logger.getLogger("genlog").warning("NumberFormatException\n" + e);
                return false;
            }

            //controlla se sono float ...
            try {
                Float.parseFloat(ccosto.getText());
            } catch (NumberFormatException e) {
                Logger.getLogger("genlog").warning("NumberFormatException\n" + e);
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
                    prod = new Prodotto(casname.getText(), Integer.parseInt(casqty.getText()), cat.getSelectedItem().toString(), inStock.isSelected(), Float.valueOf(ccosto.getText()), Integer.parseInt(cmin.getText()), note.getText(), percorsofoto, negozio.isSelected());
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
                    form.setVisible(false);

                }

                refreshTab();

            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + ex);
            } catch (InterruptedException ex) {
                Logger.getLogger("genlog").warning("InterruptedException\n" + ex);
            } catch (IOException ex) {
                Logger.getLogger("genlog").warning("IOException\n" + ex);
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

                ccosto.setText(bd.toPlainString());
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
                if (percorsofoto == null) {
                    icon = new ImageIcon(getClass().getResource("/res/img/upload.png"));
                } else {
                    icon = new ImageIcon(percorsofoto);
                }
                Image ImmagineScalata = icon.getImage().getScaledInstance(90, 80, Image.SCALE_DEFAULT);
                icon.setImage(ImmagineScalata);
                bfoto.setIcon(icon);

            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + ex);
            }

        }

        public void getOggettoforFormUpdate() throws InterruptedException {

            ProdottoDAO dao = new ProdottoDAO();
            try {
                int a = JOptionPane.showConfirmDialog(this, "Dario, sei proprio sicuro?");
                if (a == JOptionPane.YES_OPTION) {
                    Prodotto prod = new Prodotto(casku.getText(), casdatareg.getText(), casname.getText(), Integer.parseInt(casqty.getText()), cat.getSelectedItem().toString(), inStock.isSelected(), Float.valueOf(ccosto.getText()), Integer.parseInt(cmin.getText()), note.getText(), percorsofoto, negozio.isSelected());

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
                    form.setVisible(false);

                }

            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + ex);
            } catch (IOException ex) {
                Logger.getLogger("genlog").warning("IOException\n" + ex);
            }

            try {
                refreshTab();
            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException" + ex);
            }

        }

    }

    public void setComunicator(FramePrincipale princ) {
        frameprinc = princ;

    }

}
