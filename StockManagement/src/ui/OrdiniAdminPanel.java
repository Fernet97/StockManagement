/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Fornitore;
import beans.Ordine;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import others.RoundedPanel;

/**
 *
 * @author Fernet
 */
public class OrdiniAdminPanel extends JPanel {

    public static String nomeutente;
    public Prodotto prodottoCorrente;
    public javax.swing.JComboBox<String> jComboBox;
    public DefaultTableModel model;
    private JTable table;
    private final DefaultTableModel model2;
    private final JTable table2;
    public JList list;
    private final DefaultListModel listModel;
    private final JLabel costot;
    private double costocarrell = 0;
    private final JLabel numordine;
    public JTextField casella;
    public JDialog popup;
    public String skusel;
    private JTextField ggallacons;
    private JTextField casellaqty;
    private FramePrincipale frameprinc;
    private final JPanelNomeProdotto tabnomeprodotto;


    public OrdiniAdminPanel(String user) {
        nomeutente = user;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel top = new JPanel(new GridLayout(1, 5));
          
        JButton switchOrd = new JButton(ImpostaImgSwitch("/res/img/refresh.png"));
        switchOrd.setBackground(UIManager.getColor("nimbusBase"));
        switchOrd.setText(" Passa a 'Preleva'");
        switchOrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frameprinc.VaiAPreleva();
            }
        });
        
        
        
        top.add(switchOrd);
        
        top.add(new JLabel("    "));
        
        JLabel title = new JLabel("ORDINI ADMIN");
        title.setFont(new Font("Arial Black", Font.BOLD, 30));
        top.add(title);
        
        top.add(new JLabel(""));

        top.add(new JLabel(""));

                     
        super.add(top);

        //Pannello centrale
        JPanel princ = new JPanel();
        princ.setLayout(new GridLayout(2, 2, 20, 20));

        JPanel sxpan = new JPanel();
        sxpan.setLayout(new BoxLayout(sxpan, BoxLayout.Y_AXIS));
        princ.add(sxpan);
        //sxpan.setBorder(BorderFactory.createLineBorder(new Color(27, 32, 36), 50));

        JPanel orizontalprod = new JPanel();
        JLabel prodtext = new JLabel("Cerca prodotto:");
        prodtext.setFont(new Font("Arial Black", Font.BOLD, 20));
        orizontalprod.add(prodtext);

        casella = new JTextField();
        casella.setColumns(30);
        casella.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent arg0) {
            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                casella.setForeground(Color.white);
                casella.setBackground(Color.gray);
                if(casella.getText().length()==0) 
                    return;
                String text = casella.getText();
                OrdineDAO ordinedao = new OrdineDAO();
                String forny = "";

                ProdottoDAO prodao = new ProdottoDAO();
                try {
                    Prodotto p = prodao.getBySku(text);
                    if (p.getSku() == null) {
                        //++++++++++++++++++++
                        //non e' nemmeno un nome?
                       if( prodao.getByNome(text).size()<=0){
                        casella.setForeground(Color.white);
                        casella.setBackground(Color.red);
                          // System.out.println("non c'è nessun nome");
                       }
                       else{
                          // System.out.println("c'è un nome");
                                                      // Sì apri popup con tabella con gli sku associati a quel nome
                                // SKU| NOME|  FORNITORE|   NOTE|  Visualizza|  agg. al carrello|
                                // lo sku selezionato va poi passato alla casella "Cerca Prodotto"
                            //No --> non ho trovato niente
                        casella.setBackground(Color.yellow);
                        casella.setForeground(Color.red);   
                        tabnomeprodotto.aggiornaNome(casella.getText());     
                       }

                                             
                    } else {
                        casella.setBackground(Color.green);
                        ((DefaultListModel) list.getModel()).addElement(p.getSku() + "|  " + p.getNome());

                        forny = ordinedao.getFPr(text);

                        FornitoreDAO forndao = new FornitoreDAO();
                        String fornyname = "";
                        for (Fornitore f : forndao.getAll()) {
                            if (f.getIdfornitore().equals(forny)) {
                                fornyname = f.getFullname();
                                jComboBox.getModel().setSelectedItem(forny + "|" + fornyname);
                                break;
                            }
                        }
                        //forny --> con il nuovo fornitore selezionato
                        //***********************************************************
                        if (fornyname.equals("")) {
                           int OpzioneScelta =  JOptionPane.showConfirmDialog(null, "il prodotto non è associato a nessun fornitore.\nVuoi associarlo ad un fornitore e aggiungerlo al carrello?");
                           if (OpzioneScelta == JOptionPane.OK_OPTION) {
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
                                        System.out.println("Fornitore selezionato:" + jComboBoxxx.getSelectedItem().toString());
                                        jComboBox.getModel().setSelectedItem(jComboBoxxx.getSelectedItem().toString());

                                    }
                                });
                                confermaforn.setFont(new Font("Arial Black", Font.BOLD, 20));
                                vaiarod.add(confermaforn);

                                try {
                                    for (Fornitore f : forndao.getAll()) {
                                        jComboBoxxx.addItem(f.getIdfornitore() + "|" + f.getFullname());
                                    }
                                    if (jComboBoxxx.getItemCount() == 0) {
                                        JOptionPane.showMessageDialog(null, "Non hai creato ancora un fornitore!");
                                        return;
                                    }

                                } catch (SQLException ex) {
                                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                                }

                                vaiarod.setVisible(true);
                           
                           }  else return;
                        }
                        //******************************************************

                        aggiungiTOcarrello(p.getSku() + "|  " + p.getNome());

                    }

                } catch (SQLException ex) {
                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                }

            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                  insertUpdate(arg0);
                  tabnomeprodotto.aggiornaNome(casella.getText());
            }
        });

        orizontalprod.add(casella);
        sxpan.add(orizontalprod);

        jComboBox = new JComboBox<>();
        jComboBox.setVisible(false); //Logica nascosta
        jComboBox.setFont(new Font("Arial Black", Font.BOLD, 30));
        // Aggiungi i nomi del fornitori
        jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Seleziona un fornitore"}));
        FornitoreDAO daof = new FornitoreDAO();
        try {
            for (Fornitore f : daof.getAll()) {

                jComboBox.addItem(f.getIdfornitore() + "|" + f.getFullname());
            }
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        }
        jComboBox.setForeground(Color.black);
        jComboBox.setBackground(Color.DARK_GRAY);
        jComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // SVUOTA LA LISTA DI PRODOTTI
                listModel.clear();
                FornitoreDAO daof = new FornitoreDAO();
                if (jComboBox.getSelectedItem().toString().equals("Seleziona un fornitore")) {
                    return;
                }
                String idfornitore = "";
                String selezionato = "";
                String subselezionato = "";
                selezionato = jComboBox.getSelectedItem().toString();
                subselezionato = selezionato.substring(0, selezionato.lastIndexOf("|"));

                idfornitore = subselezionato;
                OrdineDAO daoo = new OrdineDAO();
                ProdottoDAO prodao = new ProdottoDAO();

                try {
                    for (String sku : daoo.getPFr(idfornitore)) {
                        Prodotto pp = prodao.getBySku(sku);
                        ((DefaultListModel) list.getModel()).addElement(pp.getSku() + "|  " + pp.getNome());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                }
            }
        });
        sxpan.add(jComboBox);
        
        //Logica nascosta
        listModel = new DefaultListModel();
        list = new JList(listModel);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(10, 11, 227, 239);

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // se non è doppio click
                if (e.getClickCount() != 2) {
                    return;
                }
                int index = list.getSelectedIndex(); // Prodotto selezionato dalla jlist
                String s = (String) list.getSelectedValue();

                aggiungiTOcarrello(list.getSelectedValue().toString());
            }
        });
        
        //****************************************
        tabnomeprodotto = new JPanelNomeProdotto(casella,"", false);        
        sxpan.add(tabnomeprodotto);

        JPanel dxpan = new JPanel();
        dxpan.setLayout(new BoxLayout(dxpan, BoxLayout.PAGE_AXIS));
        dxpan.add(Box.createRigidArea(new Dimension(20, 0)));
        JLabel infolabel = new JLabel("     Carrello:    ");
        infolabel.setFont(new Font("Arial Black", Font.ITALIC, 40));

        JPanel info = new RoundedPanel();
        info.setBackground(new Color(151, 109, 248));
        info.add(Box.createRigidArea(new Dimension(50, 10)));
        info.setLayout(new BoxLayout(info, BoxLayout.PAGE_AXIS));

        table = new JTable();
        table.getTableHeader().setReorderingAllowed(false);

        model = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 2 || column == 4) {
                    return false; //il numero di celle NON editabili...
                } else {
                    return true;
                }
            }
        };
        model.addColumn("SKU");
        model.addColumn("quantità da ordinare");
        model.addColumn("Costo unitario");
        model.addColumn("Giorni all'arrivo");
        model.addColumn("Fornitore");
        table.setModel(model);

        JScrollPane sp = new JScrollPane(table);

        info.add(sp);

        JPanel manageprod = new JPanel();
        manageprod.setLayout(new GridBagLayout());
        JButton rimuoviprod = new JButton("Elimina prodotto selezionato");
        rimuoviprod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                  // Gli indici delle righe selezionate
                for (int j=0; j<table.getRowCount(); j++) {
                    if(table.isRowSelected(j)){
                        JOptionPane.showMessageDialog(null, "Vuoi togliere il prodotto "+table.getValueAt(j, 0).toString()+ "dal carrello?");
                        model.removeRow(j);
                        costocarrell -= Double.parseDouble(table.getValueAt(j, 2).toString()) * Integer.parseInt(table.getValueAt(j, 1).toString());


                        BigDecimal bd = new BigDecimal(String.valueOf(costocarrell));
                        String coast = String.valueOf(bd.toPlainString());
                        if(coast.contains(".") == true){
                            int punto = (char) coast.indexOf('.');

                            if(coast.substring(punto).length() > 5){
                                coast = coast.substring(0, punto+5);
                                System.out.println(coast);
                            }
                        }

                        costot.setText("Costo totale: " + coast + " euro");
                    }
                }              
                
                
            }
        });
        manageprod.add(rimuoviprod);

        JButton svuotaprod = new JButton("Svuota carrello");
        svuotaprod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                costocarrell = 0;
                costot.setText("Costo totale: 0 euro");
                listModel.clear();
                jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Seleziona un fornitore"}));
                FornitoreDAO daof = new FornitoreDAO();
                try {
                    for (Fornitore f : daof.getAll()) {
                        jComboBox.addItem(f.getIdfornitore() + "|" + f.getFullname());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                }
            }
        });
        manageprod.add(svuotaprod);

        info.add(manageprod);

        infolabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dxpan.add(infolabel);
        dxpan.add(info);

        princ.add(dxpan);

        /**
         * * tabella ordin*********
         */
        JPanel SXdown = new JPanel();
        SXdown.setLayout(new BoxLayout(SXdown, BoxLayout.PAGE_AXIS));

        String[] columnNames = {"# Ordine", "Data ordine", "# prodotti ordinati", "Costo Totale", "Prodotti arrivati", "Stato ordine", "Controlla ordine", "Ricarica ordine"};

        Object[][] data = {};

        model2 = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column >= 6; //il numero di celle editabili...
            }
        };
        table2 = new JTable(model2);
        table2.getTableHeader().setReorderingAllowed(false);
        // model2.addRow(data); // DA CANCELLARE

        table2.getColumnModel().getColumn(5).setCellRenderer(new CustomStockRender());

        table2.getColumnModel().getColumn(6).setCellRenderer(new TableButtonRenderer());
        table2.getColumnModel().getColumn(6).setCellEditor(new TableRenderer(new JCheckBox()));

        table2.getColumnModel().getColumn(7).setCellRenderer(new TableButtonRenderer());
        table2.getColumnModel().getColumn(7).setCellEditor(new TableRenderer(new JCheckBox()));

        JScrollPane sp2 = new JScrollPane(table2);
        sp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.red, Color.red), "Riepilogo ordini effettuati", TitledBorder.CENTER, TitledBorder.TOP));
        SXdown.add(sp2);
        JButton ordinerimuovi = new JButton("Cancella ordine selezionato");
        ordinerimuovi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrdineDAO daor = new OrdineDAO();
                try {
                    for (int i = 0; i < table2.getSelectedRows().length; i++) {

                        int OpzioneScelta = JOptionPane.showConfirmDialog(getParent(), "Sicuro di voler cancellare l'ordine: "
                                + table2.getValueAt(table2.getSelectedRow(), 0));

                        if (OpzioneScelta == JOptionPane.OK_OPTION) {
                            daor.removeOrd(table2.getValueAt(table2.getSelectedRow(), 0).toString());
                            model2.removeRow(table2.getSelectedRow());
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                }

            }
        });
        SXdown.add(ordinerimuovi);

        OrdineDAO ordaoo = new OrdineDAO();

        try {
            // String[] columnNames = {"# Ordine", "Data ordine", "# prodotti ordinati", "Costo Totale ordine", "In spedizione", "Controlla ordine", "Ricarica ordine"};
            for (ArrayList<String> ordine : ordaoo.groupByOrdini()) {
                BigDecimal costoo = new BigDecimal(String.valueOf(ordine.get(2)));
                
                String coast = costoo.toPlainString();

                if(coast.contains(".") == true){
                    int punto = (char) coast.indexOf('.');
                    
                    if(coast.substring(punto).length() > 5){
                        coast = coast.substring(0, punto+5);
                        System.out.println(coast);
                    }
                }
                    
                model2.addRow(new Object[]{ordine.get(0), ordine.get(3), ordine.get(1), coast, ordine.get(4), ordaoo.isArrivato(ordine.get(0)), "Apri", "Ricarica"});
            }
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        } catch (ParseException ex) {
            Logger.getLogger("genlog").warning("ParseException\n" + StockManagement.printStackTrace(ex));
        }

        princ.add(SXdown);

        JPanel DXdown = new JPanel();
        DXdown.setLayout(new BoxLayout(DXdown, BoxLayout.PAGE_AXIS));
        JLabel riepilogo = new JLabel("Riepilogo ordine");
        riepilogo.setFont(new Font("Arial Black", Font.BOLD, 30));
        riepilogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        DXdown.add(riepilogo);

        numordine = new JLabel("#Ordine: ORD-X");
        numordine.setForeground(Color.red);
        numordine.setFont(new Font("Arial Black", Font.BOLD, 15));
        numordine.setAlignmentX(Component.CENTER_ALIGNMENT);
        DXdown.add(numordine);

        costot = new JLabel("Costo totale: 0 euro");
        costot.setFont(new Font("Arial Black", Font.BOLD, 15));
        costot.setAlignmentX(Component.CENTER_ALIGNMENT);
        costot.setForeground(Color.red);
        DXdown.add(costot);

        JButton conferma = new JButton("   Effettua ordine    ");
        conferma.setFont(new Font("Arial Black", Font.ITALIC, 40));
        conferma.setMinimumSize(new Dimension(100, 50));
        conferma.setAlignmentX(Component.CENTER_ALIGNMENT);

        //SALVA L'ORDINE
        conferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int OpzioneScelta = JOptionPane.showConfirmDialog(getParent(), "Sei sicuro di voler effettuare questo ordine?");
                if (OpzioneScelta == JOptionPane.OK_OPTION) {

                    OrdineDAO ordao = new OrdineDAO();
                    Ordine bean = new Ordine();
                    try {
                        bean.startOrdine();
                    } catch (InterruptedException ex) {
                        Logger.getLogger("genlog").warning("InterruptedException\n" + StockManagement.printStackTrace(ex));
                    }

                    for (int i = 0; i < model.getRowCount(); i++) {
                        try {

                            String selezionato = "";
                            String subselezionato = "";
                            selezionato = model.getValueAt(i, 4).toString();
                            subselezionato = selezionato.substring(0, selezionato.lastIndexOf("|"));
                            Ordine o = new Ordine(Integer.parseInt(model.getValueAt(i, 1).toString()), Integer.parseInt(model.getValueAt(i, 3).toString()), user, model.getValueAt(i, 0).toString(), 0, subselezionato, 0);
                            ordao.add(o);
                        } catch (SQLException ex) {
                            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                        } catch (InterruptedException ex) {
                            Logger.getLogger("genlog").warning("InterruptedException\n" + StockManagement.printStackTrace(ex));
                        }

                    }
                    refreshTab();
                    model.setRowCount(0);
                    costocarrell = 0;
                    costot.setText("Costo totale: 0 euro");

                }

            }
        });

        DXdown.add(conferma);

        princ.add(DXdown);

        super.add(princ);

    }

    public ImageIcon ImpostaImg(String nomeImmag) {

        ImageIcon icon = new ImageIcon((getClass().getResource(nomeImmag)));
        Image ImmagineScalata = icon.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);
        icon.setImage(ImmagineScalata);
        return icon;
    }

    public ImageIcon ImpostaImgSwitch(String nomeImmag) {

        ImageIcon icon = new ImageIcon((getClass().getResource(nomeImmag)));
        Image ImmagineScalata = icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        icon.setImage(ImmagineScalata);
        return icon;
    }    
    
    

    //QUANDO CHIAMARE IL REFRESH DI ORDINI?
    public void refreshTab() {
        // refresh lista fornitori
        // BISOGNA SVUOTARE E RICARICARE LISTA FORN
        //System.out.println("R   E   F   R   E   S   H   !   !   !");
        jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Seleziona un fornitore"}));
        FornitoreDAO daof = new FornitoreDAO();
        try {
            for (Fornitore f : daof.getAll()) {
                jComboBox.addItem(f.getIdfornitore() + "|" + f.getFullname());
            }
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        }

        casella.setBackground(Color.gray);
        casella.setText("");
        costot.setText("Costo totale: 0 euro");
        numordine.setText("#Ordine:");
        jComboBox.setSelectedIndex(0);
        listModel.clear();
        // model.setRowCount(0); magari il carrello non lo svuoto ogni volta al cambio scheda

        // REFRESHA TABELLA RIEPILOGO ORDINI
        OrdineDAO ordaoo = new OrdineDAO();
        model2.setRowCount(0);
        try {
            for (ArrayList<String> ordine : ordaoo.groupByOrdini()) {
                BigDecimal costoo = new BigDecimal(String.valueOf(ordine.get(2)));
                String coast = costoo.toPlainString();

                if(coast.contains(".") == true){
                    int punto = (char) coast.indexOf('.');
                    
                    if(coast.substring(punto).length() > 5){
                        coast = coast.substring(0, punto+5);
                        System.out.println(coast);
                    }
                }
                    
                model2.addRow(new Object[]{ordine.get(0), ordine.get(3), ordine.get(1), coast, ordine.get(4), ordaoo.isArrivato(ordine.get(0)), "Apri", "Ricarica"});
            }
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        } catch (ParseException ex) {
            Logger.getLogger("genlog").warning("ParseException\n" + StockManagement.printStackTrace(ex));
        }

    }

    class TableButtonRenderer extends JButton implements TableCellRenderer {

        public TableButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            if (getText().equals("view")) {
                setIcon(ImpostaImg("/res/img/ordini.png"));
            } else if (getText().equals("")) {
                setIcon(ImpostaImg("/res/img/categorie.png"));
            }

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
            if (button.getText().equals("view")) {
                button.setIcon(ImpostaImg("/res/img/ordini.png"));
            } else if (button.getText().equals("")) {
                button.setIcon(ImpostaImg("/res/img/prodotti.png"));
            }

            clicked = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (clicked) // SE CLICCATO QUEL BOTTONE:::::::::::::
            {
                boolean instockk = false;
                if (button.getText().equals("Apri")) {
                    if (table.getValueAt(row, 5).toString().equals("3")) {
                        instockk = true;

                    }
                    FrameRiepilogo f = new FrameRiepilogo(getInstance(), table.getValueAt(row, 0).toString(), table.getValueAt(row, 1).toString(), table.getValueAt(row, 3).toString(), instockk);
                    f.setResizable(false);
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setSize(1400, 400);
                    f.setLocationRelativeTo(null);  // CENTRA 
                    f.setVisible(true);
                    f.setTitle("Riepilogo ordine: " + table.getValueAt(row, 0));

                } else if (button.getText().equals("Ricarica")) {
                    model.setRowCount(0);
                    refreshTab();
                    caricaOrdine(table.getValueAt(row, 0).toString());

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

    class CustomStockRender extends JButton implements TableCellRenderer {

        public CustomStockRender() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            if (Integer.parseInt(table2.getValueAt(row, 5).toString()) == 3) {
                setBackground(new Color(126, 169, 93));  // VERDE          

            } else if (Integer.parseInt(table2.getValueAt(row, 5).toString()) == 0) {
                setBackground(new Color(244, 80, 37));    // ROSSO 
            } else if (Integer.parseInt(table2.getValueAt(row, 5).toString()) == 2) {
                setBackground(Color.yellow); // GIALLO
            }

            setText("");

            return this;
        }
    }

    public void caricaOrdine(String numerordine) {
        JOptionPane.showMessageDialog(this, "Sto caricando l'ordine " + numerordine + " nel carrello ...");
        OrdineDAO ordao = new OrdineDAO();

        try {
            FornitoreDAO forndao = new FornitoreDAO();
            ProdottoDAO prodao = new ProdottoDAO();

            for (Ordine o : ordao.getByNum(numerordine)) {
                Fornitore f = forndao.getByID(o.getFk_fornitore());
                Prodotto p = prodao.getBySku(o.getProdotto_sku());
                costocarrell = 0;

                BigDecimal costoo = new BigDecimal(String.valueOf(p.getCosto()));

                model.addRow(new Object[]{p.getSku(), o.getQty_in_arrivo(), costoo.toPlainString(), o.getGiorni_alla_consegna(), f.getIdfornitore() + "|" + f.getFullname()});
                costocarrell += p.getCosto() * o.getQty_in_arrivo();
                BigDecimal bd = new BigDecimal(String.valueOf(costocarrell));
                String coast = String.valueOf(bd.toPlainString());             
                if(coast.contains(".") == true){
                    int punto = (char) coast.indexOf('.');
                    
                    if(coast.substring(punto).length() > 5){
                        coast = coast.substring(0, punto+5);
                        System.out.println(coast);
                    }
                }
                costot.setText("Costo totale: " + coast + " euro");
                int prossimoord = o.leggiUltimoID() + 1;
                numordine.setText("#Ordine: ORD-" + prossimoord);

            }
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        }

    }

    public void aggiungiTOcarrello(String skuselezionato) {

        skusel = skuselezionato.substring(0, skuselezionato.indexOf("|"));
        //Quando clicco un prodotto dalla jList mi si apre la finestra Pop-UP
        popup = new JDialog();
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setModal(true);
        popup.setResizable(false);
        popup.setSize(new Dimension(300, 300));
        popup.setLayout(new GridLayout(4, 1));
        popup.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {

            }

            public void windowClosing(WindowEvent e) {
                System.out.println("CHIUDOOOOOOOOOOOOOOOO");
                listModel.clear();
                jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Seleziona un fornitore"}));
                FornitoreDAO daof = new FornitoreDAO();
                try {
                    for (Fornitore f : daof.getAll()) {
                        jComboBox.addItem(f.getIdfornitore() + "|" + f.getFullname());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                }

            }
        });

        JLabel prodt = new JLabel("   " + skuselezionato);
        prodt.setFont(new Font("Arial Black", Font.ITALIC, 15));
        popup.add(prodt);

        JPanel panelUP1 = new JPanel();
        panelUP1.add(new JLabel("Quantità da ordinare; "));
        casellaqty = new JTextField(20);
        ((AbstractDocument) casellaqty.getDocument()).setDocumentFilter(new DocumentFilter() {
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
        
        panelUP1.add(casellaqty);
        popup.add(panelUP1);

        JPanel panelUP2 = new JPanel();
        panelUP2.add(new JLabel("Giorni alla consegna: "));
        ggallacons = new JTextField(20);
        ((AbstractDocument) ggallacons.getDocument()).setDocumentFilter(new DocumentFilter() {
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
        
   
        ggallacons.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    confermaProdotto();
                    listModel.clear();
                    FornitoreDAO daof = new FornitoreDAO();
                    if (jComboBox.getSelectedItem().toString().equals("Seleziona un fornitore")) {
                        return;
                    }
                    String idfornitore = "";
                    String selezionato = "";
                    String subselezionato = "";
                    selezionato = jComboBox.getSelectedItem().toString();
                    subselezionato = selezionato.substring(0, selezionato.lastIndexOf("|"));

                    idfornitore = subselezionato;
                    OrdineDAO daoo = new OrdineDAO();
                    ProdottoDAO prodao = new ProdottoDAO();

                    try {
                        for (String sku : daoo.getPFr(idfornitore)) {
                            Prodotto pp = prodao.getBySku(sku);
                            ((DefaultListModel) list.getModel()).addElement(pp.getSku() + "|  " + pp.getNome());
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                    }

                }
            }
        });
        panelUP2.add(ggallacons);
        popup.add(panelUP2);

        JButton ButtonConferma = new JButton("Aggiungi al carrello");
        ButtonConferma.setFont(new Font("Arial Black", Font.ITALIC, 20));

        ButtonConferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confermaProdotto();
                listModel.clear();
                FornitoreDAO daof = new FornitoreDAO();
                if (jComboBox.getSelectedItem().toString().equals("Seleziona un fornitore")) {
                    return;
                }
                String idfornitore = "";
                String selezionato = "";
                String subselezionato = "";
                selezionato = jComboBox.getSelectedItem().toString();
                subselezionato = selezionato.substring(0, selezionato.lastIndexOf("|"));

                idfornitore = subselezionato;
                OrdineDAO daoo = new OrdineDAO();
                ProdottoDAO prodao = new ProdottoDAO();

                try {
                    for (String sku : daoo.getPFr(idfornitore)) {
                        Prodotto pp = prodao.getBySku(sku);
                        ((DefaultListModel) list.getModel()).addElement(pp.getSku() + "|  " + pp.getNome());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                }

            }
        });

        popup.add(ButtonConferma);
        popup.setLocationRelativeTo(null);
        popup.setVisible(true);

    }

    public void confermaProdotto() {

        ProdottoDAO pdao = new ProdottoDAO();

        try {
            Prodotto p = pdao.getBySku(skusel);
            
            if(!(ggallacons.getText().length() > 4)  && !(casellaqty.getText().length() > 6)){

                BigDecimal costoo = new BigDecimal(String.valueOf(p.getCosto()));

                if (controlloProdottoUguale(skusel)) {
                    popup.dispose();
                    JOptionPane.showMessageDialog(getParent(), "Attenzione! Ricorda che:\n1) non puoi associare più fornitori ad un solo prodotto mentre lo stai aggiungendo al carrello.\n2)Non puoi mettere più volte lo stesso prodotto nel carrello durante lo stesso ordine.\n Se vuoi incrementarne la quantità, perchè non modificare tal valore nella tabella del carrello? Easy ;)");
                    return;
                }

                String costouny= costoo.toPlainString();
                if(costoo.toPlainString().contains(".") == true){
                    int punto = (char) costoo.toPlainString().indexOf('.');
                    
                    if(costoo.toPlainString().substring(punto).length() > 5){
                        costouny = costoo.toPlainString().substring(0, punto+5);
                        System.out.println(costoo.toPlainString());
                    }
                }
                          
                model.addRow(new Object[]{skusel, casellaqty.getText(),costouny+" €" , ggallacons.getText(), jComboBox.getSelectedItem().toString()});

                costocarrell += p.getCosto() * Integer.parseInt(casellaqty.getText());
                BigDecimal bd = new BigDecimal(String.valueOf(costocarrell));
                
                String coast = String.valueOf(bd.toPlainString());

                if(coast.contains(".") == true){
                    int punto = (char) coast.indexOf('.');
                    
                    if(coast.substring(punto).length() > 5){
                        coast = coast.substring(0, punto+5);
                        System.out.println(coast);
                    }
                }
                           
                costot.setText("Costo totale: " + coast + " euro");
                Ordine o = new Ordine();
                int prossimoord = o.leggiUltimoID() + 1;
                numordine.setText("#Ordine: ORD-" + prossimoord);
                popup.dispose();
                
            }else JOptionPane.showMessageDialog(null, "Errore lunghezza dati! Giorni alla consegna: max 4 cifre. Quantità da ordinare: max 6 cifre.");
           
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        }
    }

    public boolean controlloProdottoUguale(String skuscelto) {

        for (int i = 0; i < table.getRowCount(); i++) {
            System.out.println("Prodotto già nel carrello" + model.getValueAt(i, 0).toString() + "   sku scelto:" + skuscelto);
            if (model.getValueAt(i, 0).toString().equals(skuscelto)) {
                return true;
            }
        }

        return false;
    }

    public OrdiniAdminPanel getInstance() {
        return this;

    }
    
    public void setComunicator(FramePrincipale princ) {
        frameprinc = princ;

    }
    
    

}
