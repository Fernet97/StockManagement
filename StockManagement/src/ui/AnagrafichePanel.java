/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Cliente;
import beans.Fornitore;
import beans.Utente;
import dao.ClienteDAO;
import dao.FornitoreDAO;
import dao.UtenteDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
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
import javax.swing.JPasswordField;
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
public class AnagrafichePanel extends JPanel {

    private static DefaultTableModel model;
    public static Object[] nuovaRiga;
    public static Formanagrafiche form;
    public static JCheckBox checkforn;
    public static JCheckBox checkuten;
    public static JCheckBox checkclient;
    private FramePrincipale frameprinc;

    public AnagrafichePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("ANAGRAFICHE");
        title.setFont(new Font("Arial Black", Font.BOLD, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.add(title);

        JPanel panSopra = new JPanel();
        panSopra.setLayout(new GridLayout(1, 3));
        panSopra.setMaximumSize(new Dimension(1420, 300));
        JPanel cerca = new JPanel();
        JLabel searchlabel = new JLabel("Cerca:");
        searchlabel.setFont(new Font("Arial Black", Font.BOLD, 20));
        JTextField casella = new JTextField(20);
        cerca.add(searchlabel);
        cerca.add(casella);
        panSopra.add(cerca);

        JPanel panelcheck = new JPanel();

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    refreshTab();
                } catch (SQLException ex) {
                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                }
            }
        };
        checkforn = new JCheckBox("Fornitori");
        checkforn.setSelected(true);
        checkforn.addActionListener(actionListener);
        checkuten = new JCheckBox("Utenti");
        checkuten.setSelected(true);
        checkuten.addActionListener(actionListener);
        checkclient = new JCheckBox("Clienti");
        checkclient.setSelected(true);
        checkclient.addActionListener(actionListener);

        panelcheck.add(checkforn);
        panelcheck.add(checkuten);
        panelcheck.add(checkclient);
        panSopra.add(panelcheck);

        JButton buttonNew = new JButton("ADD NEW");
        //*************+* BOTTONE AGGIUNGI NUOVA RIGA**************************
        buttonNew.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Cliente", "Fornitore", "Utente"};

                int OpzioneScelta = JOptionPane.showOptionDialog(null, "Seleziona una tipologia", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
                if (OpzioneScelta != JOptionPane.CLOSED_OPTION) {
                    form = new Formanagrafiche(OpzioneScelta, "ADD", null);
                    form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    form.setResizable(false);
                    form.setLocationRelativeTo(null);  // CENTRA 
                    form.setVisible(true);
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

        String[] columnNames = {"Tipo", "ID", "Data reg.", "Fullname", "P.IVA/CF", "Indirizzo", "Tel/Fax", "email", "Note", "Modifica", "Cancella", "Ordina"};
        //Object[][] data = { { "1", "10/12/2019", "Gianfranco Colil", "XXXXXXX","Via campo san giovanni, ITa", "398737892", "cacio@gmail.com", "una descrizione","Modifica","Cancella"} };
        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column >= 9; //il numero di celle editabili...
            }
        };
        JTable table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);

        try {
            refreshTab(); // Aggiorna tavola con  i fornitori del db;
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        }

        table.setRowHeight(40); //altezza celle

        //X colonne che hanno pulsanti
        table.getColumnModel().getColumn(9).setCellRenderer(new ClientsTableButtonRenderer());
        table.getColumnModel().getColumn(9).setCellEditor(new ClientsTableRenderer(new JCheckBox()));

        table.getColumnModel().getColumn(10).setCellRenderer(new ClientsTableButtonRenderer());
        table.getColumnModel().getColumn(10).setCellEditor(new ClientsTableRenderer(new JCheckBox()));

        table.getColumnModel().getColumn(11).setCellRenderer(new ClientsTableButtonRenderer());
        table.getColumnModel().getColumn(11).setCellEditor(new ClientsTableRenderer(new JCheckBox()));

        JScrollPane sp = new JScrollPane(table);
        TitoloTab1.add(sp);

        //******* funzione di ricerca *******************+
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        
        rowSorter.setSortable(0, false);
        rowSorter.setSortable(4, false); 
        rowSorter.setSortable(5, false); 
        rowSorter.setSortable(6, false); 
        rowSorter.setSortable(7, false); 
        rowSorter.setSortable(9, false); 
        rowSorter.setSortable(10, false);// toglie il sort alle colonne di modifica cancella, ordina e tipo
        rowSorter.setSortable(11, false);
        
          rowSorter.setComparator(2, new Comparator<String>()
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
            } else if (getText().equals("Prodotti")) {
                setIcon(ImpostaImg("/res/img/prodotti.png"));
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
                    try {
                        fireEditingStopped();
                    } catch (Exception ex) {
                        Logger.getLogger("genlog").warning("Exception\n" + StockManagement.printStackTrace(ex));
                    };
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
            } else if (button.getText().equals("Prodotti")) {
                button.setIcon(ImpostaImg("/res/img/prodotti.png"));
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
                            + table.getValueAt(row, 7) + "  ]");

                    if (OpzioneScelta == JOptionPane.OK_OPTION) {  // DIFFERENZIARLO X CLIENTE, FORNITORE, UTENTE

                        if (table.getValueAt(row, 0).toString().equals("Fornitore")) {
                            FornitoreDAO dao = new FornitoreDAO();
                            try {
                                dao.remove(table.getValueAt(row, 1).toString());
                            } catch (Exception e) {
                                Logger.getLogger("genlog").warning("Exception\n" + StockManagement.printStackTrace(e));
                                

                            }
                        }

                        if (table.getValueAt(row, 0).toString().equals("Utente")) {
                            UtenteDAO dao = new UtenteDAO();
                            try {
                                dao.remove(table.getValueAt(row, 1).toString());
                            } catch (Exception e) {
                                Logger.getLogger("genlog").warning("Exception\n" + StockManagement.printStackTrace(e));
                                

                            }
                        }

                        if (table.getValueAt(row, 0).toString().equals("Cliente")) {
                            ClienteDAO dao = new ClienteDAO();
                            try {
                                dao.remove(Integer.parseInt(table.getValueAt(row, 1).toString()));
                            } catch (Exception e) {
                                Logger.getLogger("genlog").warning("Exception\n" + StockManagement.printStackTrace(e));
                                

                            }
                        }

                    }
                } else if (button.getText().equals("Modifica")) {

                    if (table.getValueAt(row, 0).toString().equals("Cliente")) {
                        form = new Formanagrafiche(0, "UPDATE", table.getValueAt(row, 1).toString());
                    }
                    if (table.getValueAt(row, 0).toString().equals("Fornitore")) {
                        form = new Formanagrafiche(1, "UPDATE", table.getValueAt(row, 1).toString());
                    }
                    if (table.getValueAt(row, 0).toString().equals("Utente")) {
                        form = new Formanagrafiche(2, "UPDATE", table.getValueAt(row, 1).toString());
                    }
                    form.setResizable(false);
                    form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    form.setVisible(true);

                } else if (button.getText().equals("Prodotti")) { // VAI A prodotti con questo fornitore
                    if(table.getValueAt(row, 0).toString().equals("Fornitore")){
                        //Prima con questo andavo in ordini ...
                        //frameprinc.OrdiniStatus = false;
                        //frameprinc.VaiAOrdini(table.getValueAt(row, 1).toString() + "|" + table.getValueAt(row, 3).toString());
                        frameprinc.VaiAProdotti(table.getValueAt(row, 1).toString()+"| "+table.getValueAt(row, 3).toString());
                    }
                    else JOptionPane.showMessageDialog(getParent(), "Puoi fare un ordine solo da un fornitore! [O da Cliente, ma non per adesso]");

                }

            }
            try {
                refreshTab();
            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
            }
            clicked = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            try {
                super.fireEditingStopped();
            } catch (Exception ex) {
                System.out.println("MANNAGGIA *******");
            };
        }
    }

// ****************** LA FORM ***********************
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
     */
    /**
     *
     * @author Fernet
     */
    public static class Formanagrafiche extends JDialog {

        public int tipologia;
        public String modalita;
        public String IdSelezionato;
        private final ImageIcon img;
        private JTextField casid;
        private JTextField casdatareg;
        private JTextField casfullname;
        private JTextField cascfiva;
        private JTextField casindirizzo;
        private JTextField castel;
        private JTextField casemail;
        private JTextArea note;
        private JComboBox<Object> permess;
        private JTextField caspwd;

        public Formanagrafiche(int OpzioneScelta, String mod, String idSelected) {

            super.setModal(true);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tipologia = OpzioneScelta;
            modalita = mod;
            IdSelezionato = idSelected;
            initComponents();

            casid.setEditable(false);
            casdatareg.setEditable(false);
            casid.setFocusable(false);
            casdatareg.setFocusable(false);
            casid.setBackground(Color.DARK_GRAY);
            casdatareg.setBackground(Color.DARK_GRAY);
            casid.setForeground(Color.yellow);
            casdatareg.setForeground(Color.yellow);

            if (modalita.equals("UPDATE")) {
                setFormAsID(idSelected);
            }
            else{
                casid.setText("AUTOGENERATO");
                casdatareg.setText("AUTOGENERATO");                
            }
            img = new ImageIcon(getClass().getResource("/res/img/logo-Icon.png"));
            setSize(900, 300);
            this.setIconImage(img.getImage());

        }

        private void initComponents() {

            // Cliente
            /* CF, INDIRIZZO, EMAIL, TELEFONO,NOTE SONO NULLABILI*/
            if (tipologia == 0) {

                JPanel pancliente = new JPanel();
                pancliente.setLayout(new BoxLayout(pancliente, BoxLayout.Y_AXIS));
                JLabel l = new JLabel("Cliente");
                l.setFont(new Font("Arial Black", Font.BOLD, 20));
                l.setAlignmentX(CENTER_ALIGNMENT);
                pancliente.add(l);

                JPanel main = new JPanel();
                main.setLayout(new GridLayout(3, 3, 20, 10));

                JPanel pid = new JPanel();
                JLabel lid = new JLabel("    ID ");
                casid = new JTextField(15);
                casid.setAlignmentX(RIGHT_ALIGNMENT);
                pid.add(lid);
                pid.add(casid);
                main.add(pid);

                JPanel pdatareg = new JPanel();
                JLabel ldatareg = new JLabel("Data reg");
                casdatareg = new JTextField(15);
                casdatareg.setAlignmentX(RIGHT_ALIGNMENT);
                pdatareg.add(ldatareg);
                pdatareg.add(casdatareg);
                main.add(pdatareg);

                JPanel pfullname = new JPanel();
                JLabel lfullname = new JLabel("Fullname");
                casfullname = new JTextField(15);
                casfullname.setAlignmentX(RIGHT_ALIGNMENT);
                pfullname.add(lfullname);
                pfullname.add(casfullname);
                main.add(pfullname);

                JPanel pcf = new JPanel();
                JLabel lcf = new JLabel("     CF");
                cascfiva = new JTextField(15);
                cascfiva.setAlignmentX(RIGHT_ALIGNMENT);
                pcf.add(lcf);
                pcf.add(cascfiva);
                main.add(pcf);

                JPanel pindirizzo = new JPanel();
                JLabel lindirizzo = new JLabel("Indirizzo");
                casindirizzo = new JTextField(15);
                casindirizzo.setAlignmentX(RIGHT_ALIGNMENT);
                pindirizzo.add(lindirizzo);
                pindirizzo.add(casindirizzo);
                main.add(pindirizzo);

                JPanel ptel = new JPanel();
                JLabel ltel = new JLabel("          Tel.");
                castel = new JTextField(15);
                castel.setAlignmentX(RIGHT_ALIGNMENT);
                ptel.add(ltel);
                ptel.add(castel);
                main.add(ptel);

                main.add(new JLabel(""));  // PER DARE SPAZIO
                main.add(new JLabel("")); // PER DARE SPAZIO

                JPanel pemail = new JPanel();
                JLabel lemail = new JLabel("      Email");
                casemail = new JTextField(15);
                casemail.setAlignmentX(RIGHT_ALIGNMENT);
                pemail.add(lemail);
                pemail.add(casemail);
                main.add(pemail);

                pancliente.add(main);

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
                        form.dispose();
                        form = null;

                    }
                });

                pandown.add(salva);
                pandown.add(annulla);

                pancliente.add(pandown);

                add(pancliente);

            }

            // Fornitore
            if (tipologia == 1) {

                JPanel panforn = new JPanel();
                panforn.setLayout(new BoxLayout(panforn, BoxLayout.Y_AXIS));
                JLabel l = new JLabel("Fornitore");
                l.setFont(new Font("Arial Black", Font.BOLD, 20));
                l.setAlignmentX(CENTER_ALIGNMENT);
                panforn.add(l);

                JPanel main = new JPanel();
                main.setLayout(new GridLayout(3, 3, 20, 10));

                JPanel pid = new JPanel();
                JLabel lid = new JLabel("    ID ");
                casid = new JTextField(15);
                casid.setAlignmentX(RIGHT_ALIGNMENT);
                pid.add(lid);
                pid.add(casid);
                main.add(pid);

                JPanel pdatareg = new JPanel();
                JLabel ldatareg = new JLabel("Data reg");
                casdatareg = new JTextField(15);
                casdatareg.setAlignmentX(RIGHT_ALIGNMENT);
                pdatareg.add(ldatareg);
                pdatareg.add(casdatareg);
                main.add(pdatareg);

                JPanel pfullname = new JPanel();
                JLabel lfullname = new JLabel("Fullname");
                casfullname = new JTextField(15);
                casfullname.setAlignmentX(RIGHT_ALIGNMENT);
                pfullname.add(lfullname);
                pfullname.add(casfullname);
                main.add(pfullname);

                JPanel piva = new JPanel();
                JLabel liva = new JLabel("p.IVA");
                cascfiva = new JTextField(15);
                cascfiva.setAlignmentX(RIGHT_ALIGNMENT);
                piva.add(liva);
                piva.add(cascfiva);
                main.add(piva);

                JPanel pindirizzo = new JPanel();
                JLabel lindirizzo = new JLabel("Indirizzo");
                casindirizzo = new JTextField(15);
                casindirizzo.setAlignmentX(RIGHT_ALIGNMENT);
                pindirizzo.add(lindirizzo);
                pindirizzo.add(casindirizzo);
                main.add(pindirizzo);

                JPanel ptel = new JPanel();
                JLabel ltel = new JLabel("          Tel.");
                castel = new JTextField(15);
                castel.setAlignmentX(RIGHT_ALIGNMENT);
                ptel.add(ltel);
                ptel.add(castel);
                main.add(ptel);

                main.add(new JLabel(""));  // PER DARE SPAZIO
                main.add(new JLabel("")); // PER DARE SPAZIO

                JPanel pemail = new JPanel();
                JLabel lemail = new JLabel("      Email");
                casemail = new JTextField(15);
                casemail.setAlignmentX(RIGHT_ALIGNMENT);
                pemail.add(lemail);
                pemail.add(casemail);
                main.add(pemail);

                panforn.add(main);

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
                        form.dispose();
                        form = null;

                    }
                });

                pandown.add(salva);
                pandown.add(annulla);

                panforn.add(pandown);

                add(panforn);

            }

            // Utente
            if (tipologia == 2) {
                JPanel panutente = new JPanel();
                panutente.setLayout(new BoxLayout(panutente, BoxLayout.Y_AXIS));
                JLabel l = new JLabel("Utente");
                l.setFont(new Font("Arial Black", Font.BOLD, 20));
                l.setAlignmentX(CENTER_ALIGNMENT);
                panutente.add(l);

                JPanel main = new JPanel();
                main.setLayout(new GridLayout(3, 3, 20, 10));

                JPanel pid = new JPanel();
                JLabel lid = new JLabel("    ID ");
                casid = new JTextField(15);
                casid.setAlignmentX(RIGHT_ALIGNMENT);
                pid.add(lid);
                pid.add(casid);
                main.add(pid);

                JPanel pdatareg = new JPanel();
                JLabel ldatareg = new JLabel("Data reg");
                casdatareg = new JTextField(15);
                casdatareg.setAlignmentX(RIGHT_ALIGNMENT);
                pdatareg.add(ldatareg);
                pdatareg.add(casdatareg);
                main.add(pdatareg);

                JPanel pfullname = new JPanel();
                JLabel lfullname = new JLabel("Fullname");
                casfullname = new JTextField(15);
                casfullname.setAlignmentX(RIGHT_ALIGNMENT);
                pfullname.add(lfullname);
                pfullname.add(casfullname);
                main.add(pfullname);

                JPanel pcf = new JPanel();
                JLabel lcf = new JLabel("     CF");
                cascfiva = new JTextField(15);
                cascfiva.setAlignmentX(RIGHT_ALIGNMENT);
                pcf.add(lcf);
                pcf.add(cascfiva);
                main.add(pcf);

                JPanel pindirizzo = new JPanel();
                JLabel lindirizzo = new JLabel("Indirizzo");
                casindirizzo = new JTextField(15);
                casindirizzo.setAlignmentX(RIGHT_ALIGNMENT);
                pindirizzo.add(lindirizzo);
                pindirizzo.add(casindirizzo);
                main.add(pindirizzo);

                JPanel ptel = new JPanel();
                JLabel ltel = new JLabel("          Tel.");
                castel = new JTextField(15);
                castel.setAlignmentX(RIGHT_ALIGNMENT);
                ptel.add(ltel);
                ptel.add(castel);
                main.add(ptel);

                main.add(new JLabel(""));  // PER DARE SPAZIO

                JPanel pwd = new JPanel();
                JLabel lpwd = new JLabel("       pwd");
                caspwd = new JPasswordField(15);
                caspwd.setAlignmentX(RIGHT_ALIGNMENT);
                pwd.add(lpwd);
                pwd.add(caspwd);
                main.add(pwd);

                JPanel pemail = new JPanel();
                JLabel lemail = new JLabel("      Email");
                casemail = new JTextField(15);
                casemail.setAlignmentX(RIGHT_ALIGNMENT);
                pemail.add(lemail);
                pemail.add(casemail);
                main.add(pemail);

                panutente.add(main);

                JPanel pandown = new JPanel();
                JLabel permessi = new JLabel("Permessi:");
                pandown.add(permessi);
                JPanel panelpermessi = new JPanel();
                panelpermessi.setLayout(new BoxLayout(panelpermessi, BoxLayout.Y_AXIS));
                permess = new JComboBox<>();
                permess.setFont(new Font("Arial Black", Font.BOLD, 15));
                permess.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Admin", "Normal user"}));
                permess.setForeground(Color.black);
                permess.setBackground(Color.DARK_GRAY);
                permess.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                    }
                });
                panelpermessi.add(permess);
                pandown.add(panelpermessi);

                pandown.add(new JLabel("        "));

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
                                if(caspwd.getText().length() > 0)  getOggettoforFormSave();
                                else JOptionPane.showMessageDialog(null, "Password obbligatoria!");
                                
                            }
                        } else if (check()) {
                            getOggettoforFormUpdate();
                        }

                    }
                });

                JButton annulla = new JButton("Annulla");
                annulla.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        form.dispose();
                        form = null;

                    }
                });

                pandown.add(salva);
                pandown.add(annulla);

                panutente.add(pandown);

                add(panutente);

            }

        }

        private void setFormAsID(String idSelected) {

            // CLIENTE
            if (tipologia == 0) {
                ClienteDAO dao = new ClienteDAO();

                try {
                    Cliente cliente = dao.getByID(idSelected);

                    casid.setText(String.valueOf(cliente.getIdcliente()));
                    casdatareg.setText(cliente.getDatareg());
                    casfullname.setText(cliente.getFullname());
                    cascfiva.setText(cliente.getCf());
                    casindirizzo.setText(cliente.getIndirizzo());
                    castel.setText(cliente.getTel());
                    casemail.setText(cliente.getEmail());
                    note.setText(cliente.getNote());

                } catch (SQLException ex) {
                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                }

            }
            
            
            // FORNITORE
            if (tipologia == 1) {
                FornitoreDAO dao = new FornitoreDAO();

                try {
                    Fornitore fornitore = dao.getByID(idSelected);

                    casid.setText(fornitore.getIdfornitore());
                    casdatareg.setText(fornitore.getDatareg());
                    casfullname.setText(fornitore.getFullname());
                    cascfiva.setText(fornitore.getP_iva());
                    casindirizzo.setText(fornitore.getIndirizzo());
                    castel.setText(fornitore.getTel());
                    casemail.setText(fornitore.getEmail());
                    note.setText(fornitore.getNote());

                } catch (SQLException ex) {
                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                }

            }

            // UTENTE
            if (tipologia == 2) {

                UtenteDAO dao = new UtenteDAO();

                try {
                    Utente u = dao.getByID(idSelected);
                    casid.setText(u.getIdutente());
                    casdatareg.setText(u.getDatareg());
                    casfullname.setText(u.getFullname());
                    cascfiva.setText(u.getCF());
                    casindirizzo.setText(u.getIndirizzo());
                    castel.setText(u.getTelefono());
                    casemail.setText(u.getEmail());
                    note.setText(u.getNote());
                    permess.setSelectedIndex(u.getPermessi());

                } catch (SQLException ex) {
                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                }

            }

        }

        public boolean check() {

            //Sutente
            if (tipologia == 2) {
                if (casfullname.getText().isEmpty() || cascfiva.getText().isEmpty() || casindirizzo.getText().isEmpty()
                        || castel.getText().isEmpty() || casemail.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Riempi tutti i campi! ['Note' è opzionale]");
                    return false;
                }
                if (cascfiva.getText().length() > 45) {
                    JOptionPane.showMessageDialog(this, "CF troppo lungo!");
                    return false;
                } 
                
                if(caspwd.getText().length() > 254 ){
                    JOptionPane.showMessageDialog(this, "Password troppo lunga!");
                    return false;
                }
                
            }
            
            // Cliente
            else if(tipologia == 0){         
                  if (casfullname.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Specificare un nome!");
                    /*
                    DA FARE:
                    Se non si specifica un nome, verrà chiesto di generare un nome automatico composto
                    dall'ID cliente incrementale generato dal DB
                    */
                    return false;
                }
                if (cascfiva.getText().length() > 45) {
                    JOptionPane.showMessageDialog(this, "CF troppo lungo!");
                    return false;
                }           
            
            }
            
            // se è un fornitore   
            else if (tipologia == 1) {
                if (casfullname.getText().isEmpty() || cascfiva.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Riempi tutti i campi! ['Note', 'Telefono', 'email', 'indirizzo' sono opzionali]");
                    return false;
                }
                                
                if (cascfiva.getText().length() > 30) {
                    JOptionPane.showMessageDialog(this, "P.IVA troppo lunga!");
                    return false;
                } 
                 
            }

            if (castel.getText().length() > 20) {
                JOptionPane.showMessageDialog(this, "Numero di telefono troppo lungo");
                return false;
            }

            if (casfullname.getText().length() > 45) {
                JOptionPane.showMessageDialog(this, "Nome troppo lungo!");
                return false;
            }           
            
            if (casindirizzo.getText().length() > 254) {
                JOptionPane.showMessageDialog(this, "Indirizzo troppo lungo!");
                return false;
            }           

            if (casemail.getText().length() > 254) {
                JOptionPane.showMessageDialog(this, "email troppo lunga!");
                return false;
            }  

            if (note.getText().length() > 65535) {
                JOptionPane.showMessageDialog(this, "note troppo lunghe!");
                return false;
            }  

            
            
            // SE NON C'E' UN NOME E UN COGNOME X UTENTE (CI DEVE ESSERE UNO SPAZIO)
            if (casfullname.getText().matches("\\S+") && tipologia == 2) {
                JOptionPane.showMessageDialog(this, "Bisogna specificare un nome e un cognome!");
                return false;
            }

            return true;
        }

        public void getOggettoforFormSave() {

            Fornitore forn;
            Utente uten;
            Cliente client;

            try {

                // Cliente
                if (tipologia == 0) {

                    int a = JOptionPane.showConfirmDialog(this, "Dario, sei proprio sicuro?");
                    if (a == JOptionPane.YES_OPTION) {
                        ClienteDAO dao = new ClienteDAO();
                        client = new Cliente(casfullname.getText(), cascfiva.getText(), casindirizzo.getText(), castel.getText(), casemail.getText(), note.getText());

                        dao.add(client);
                        form.dispose();
                    }
                }

                // Fornitore
                if (tipologia == 1) {
                    FornitoreDAO dao = new FornitoreDAO();

                    int a = JOptionPane.showConfirmDialog(this, "Dario, sei proprio sicuro?");
                    if (a == JOptionPane.YES_OPTION) {
                        forn = new Fornitore(casfullname.getText(), cascfiva.getText(), casindirizzo.getText(), castel.getText(), casemail.getText(), note.getText());
                        dao.add(forn);
                        form.dispose();
                    }
                }

                // Utente
                if (tipologia == 2) {
                    UtenteDAO dao = new UtenteDAO();

                    int a = JOptionPane.showConfirmDialog(this, "Dario, sei proprio sicuro?");
                    if (a == JOptionPane.YES_OPTION) {
                        uten = new Utente(casfullname.getText(), cascfiva.getText(), casindirizzo.getText(), castel.getText(), casemail.getText(), caspwd.getText(), permess.getSelectedIndex(), note.getText());

                        dao.add(uten);
                        form.dispose();
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
            } catch (InterruptedException ex) {
                Logger.getLogger("genlog").warning("InterruptedException\n" + StockManagement.printStackTrace(ex));

            }

            try {
                refreshTab();
            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
            }

        }

        public void getOggettoforFormUpdate() {

            Fornitore forn;
            Utente uten;
            Cliente client;

            try {
                // Cliente
                if (tipologia == 0) {
                    ClienteDAO dao = new ClienteDAO();
                    client = new Cliente(Integer.parseInt(casid.getText()),  casfullname.getText(), cascfiva.getText(), casindirizzo.getText(), castel.getText(), casemail.getText(), note.getText());

                    int a = JOptionPane.showConfirmDialog(this, "Dario, sei proprio sicuro?");
                    if (a == JOptionPane.YES_OPTION) {
                         dao.update(client);
                        form.setVisible(false);
                    }
                }

                // Fornitore
                if (tipologia == 1) {
                    FornitoreDAO dao = new FornitoreDAO();
                    int a = JOptionPane.showConfirmDialog(this, "Dario, sei proprio sicuro?");
                    if (a == JOptionPane.YES_OPTION) {
                        forn = new Fornitore(casid.getText(), casdatareg.getText(), casfullname.getText(), cascfiva.getText(), casindirizzo.getText(), castel.getText(), casemail.getText(), note.getText());

                        dao.update(forn);
                        form.dispose();
                    }
                }

                // Utente
                if (tipologia == 2) {
                    UtenteDAO dao = new UtenteDAO();

                    int a = JOptionPane.showConfirmDialog(this, "Dario, sei proprio sicuro?");
                    if (a == JOptionPane.YES_OPTION) {
                        uten = new Utente(casid.getText(), casdatareg.getText(), casfullname.getText(), cascfiva.getText(), casindirizzo.getText(), castel.getText(), casemail.getText(), caspwd.getText(), permess.getSelectedIndex(), note.getText());
                        
                        if(caspwd.getText().length() > 0) dao.updatePwd(uten);
                        else dao.update(uten);
                        
                        form.dispose();
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
            } catch (InterruptedException ex) {
                Logger.getLogger("genlog").warning("InterruptedException\n" + StockManagement.printStackTrace(ex));

            }

            try {
                refreshTab();
            } catch (SQLException ex) {
                Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
            }
        }

    }

    // DA DIFFERENZIARE PER CLIENTE, FORNITORE, UTENTE
    public static void refreshTab() throws SQLException {

        //Cancello vecchie righe...
        model.setRowCount(0);

        FornitoreDAO daof = new FornitoreDAO();
        UtenteDAO daou = new UtenteDAO();
        ClienteDAO daoc = new ClienteDAO();

        if (checkforn.isSelected()) {
            // Aggiorno con le nuove
            for (Fornitore forn : daof.getAll()) {
                model.addRow(new Object[]{forn.getTipo(), forn.getIdfornitore(), forn.getDatareg(), forn.getFullname(), forn.getP_iva(), forn.getIndirizzo(), forn.getTel(), forn.getEmail(), forn.getNote(), "Modifica", "Cancella", "Prodotti"});
            }
        }

        if (checkuten.isSelected()) {
            // Aggiorno con le nuove
            for (Utente utente : daou.getAll()) {
                model.addRow(new Object[]{utente.getTipo(), utente.getIdutente(), utente.getDatareg(), utente.getFullname(), utente.getCF(), utente.getIndirizzo(), utente.getTelefono(), utente.getEmail(), utente.getNote(), "Modifica", "Cancella", "Prodotti"});
            }
        }
        
        
        if (checkclient.isSelected()) {
            // Aggiorno con le nuove
            for (Cliente cliente : daoc.getAll()) {
                model.addRow(new Object[]{cliente.getTipo(), cliente.getIdcliente(), cliente.getDatareg(), cliente.getFullname(), cliente.getCf(), cliente.getIndirizzo(), cliente.getTel(), cliente.getEmail(), cliente.getNote(), "Modifica", "Cancella", "Prodotti"});
            }
        }

    }
    
    
    public void ViewOnlyUtenti(){
        checkclient.setSelected(false);
        checkforn.setSelected(false);
        checkuten.setSelected(true);
        
        try {
            refreshTab();
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("Exception: \n"+StockManagement.printStackTrace(ex));
        }
    
    }

    public void setComunicator(FramePrincipale princ) {
        frameprinc = princ;

    }

}
