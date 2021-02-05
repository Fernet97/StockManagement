/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Fornitore;
import beans.Ordine;
import beans.Preleva;
import beans.Prodotto;
import dao.FornitoreDAO;
import dao.OrdineDAO;
import dao.PrelevaDAO;
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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
public class OrdiniPanel extends JPanel {

    public Prodotto prodottoCorrente;
    public javax.swing.JComboBox<String> jComboBox;
    public DefaultTableModel model;
    private JTable table;
    public JTextField casella;
    public JDialog popup;
    public String skusel;
    private FramePrincipale frameprinc;
    public JPanelNomeProdotto tabnomeprodotto;
    private JLabel prodAggiunti;
    private int numprodaggiunti = 0;
    private final JButton photobtn;
    private final JButton effettuaPrelievo;
    private JPanel fotopan;
    public boolean ViewOrdiniRiep = false;
    private final RoundedPanel photo;
    private DefaultTableModel model2;
    
    public OrdiniPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel top = new JPanel(new GridLayout(1, 5));

        JButton switchOrd = new JButton(ImpostaImgSwitch("/res/img/refresh.png"));
        switchOrd.setBackground(UIManager.getColor("nimbusBase"));
        switchOrd.setText(" Passa a 'Ordini'");
        switchOrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frameprinc.OrdiniStatus = false;
                frameprinc.VaiAOrdini();
            }
        });

        top.add(switchOrd);

        top.add(new JLabel("    "));

        JLabel title = new JLabel("PRELEVA");
        title.setFont(new Font("Arial Black", Font.BOLD, 30));
        top.add(title);

        top.add(new JLabel(""));

        top.add(new JLabel(""));

        super.add(top);

        //Pannello centrale
        JPanel princ = new JPanel();
        princ.setLayout(new GridLayout(1, 2, 20, 20));

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
        casella.setBackground(Color.darkGray);

        orizontalprod.add(casella);
        sxpan.add(orizontalprod);

        //****************************************
        tabnomeprodotto = new JPanelNomeProdotto(casella, "", true);
        tabnomeprodotto.setComunicator(this);
        sxpan.add(tabnomeprodotto);

        JPanel carre = new JPanel();
        carre.setLayout(new BoxLayout(carre, BoxLayout.PAGE_AXIS));
        carre.add(Box.createRigidArea(new Dimension(20, 0)));
        JLabel infolabel = new JLabel("     Carrello:    ");
        infolabel.setFont(new Font("Arial Black", Font.ITALIC, 40));

        JPanel info = new RoundedPanel();
        info.setBackground( new Color(24, 53, 90));
        info.add(Box.createRigidArea(new Dimension(50, 10)));
        info.setLayout(new BoxLayout(info, BoxLayout.PAGE_AXIS));

        table = new JTable();
        table.getTableHeader().setReorderingAllowed(false);

        model = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                if(column == 2) return true;
                else return false;
            }
        };
        model.addColumn("SKU");
        model.addColumn("Nome");
        model.addColumn("Qty Prelevata");
        model.addColumn("Categoria");
        model.addColumn("Note");
        model.addColumn("Stock");
        
                


        table.setModel(model);
        table.getColumnModel().getColumn(5).setCellRenderer(new CustomStockRender());

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                 if(model.getRowCount() > 0 && table.getSelectedRow()!= -1){
                    setPhoto(table.getValueAt(table.getSelectedRow(), 0).toString());
                 }
            }
        });
        
        
      table.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                for(int i = 0; i < model.getRowCount(); i++){
                    System.out.println("miiiaoeo");
                    
                if(model.getValueAt(i, 2).toString().isEmpty()){
                    
                    JOptionPane.showMessageDialog(getParent(), "prodotto " + model.getValueAt(i, 0) + " parametri errati");
                     effettuaPrelievo.setEnabled(false);
                    return;
                }
                
            try {
                    Integer.parseInt(model.getValueAt(i, 2).toString());
                    
                }
                catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(getParent(), "Puoi inserire solo numeri!!!");
                     effettuaPrelievo.setEnabled(false);
                    return;
                }
            
            
                if(Integer.parseInt(model.getValueAt(i, 2).toString())<=0){
                    JOptionPane.showMessageDialog(getParent(), "Non puoi inserire numeri negativi!");
                    effettuaPrelievo.setEnabled(false);
                    return;
                }
               
              
                }
                 effettuaPrelievo.setEnabled(true);
                
            }
        });
        
        
        
        

        JScrollPane sp = new JScrollPane(table);

        info.add(sp);

        JPanel manageprod = new JPanel();
        manageprod.setLayout(new GridBagLayout());
        JButton rimuoviprod = new JButton("Elimina prod. selezionato");
        rimuoviprod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Gli indici delle righe selezionate
                for (int j = 0; j < table.getRowCount(); j++) {
                    if (table.isRowSelected(j)) {
                        JOptionPane.showMessageDialog(null, "Vuoi togliere il prodotto " + table.getValueAt(j, 0).toString() + "dal carrello?");
                        numprodaggiunti -= Integer.parseInt(model.getValueAt(j, 2).toString());
                        model.removeRow(j);
                        prodAggiunti.setText("        #Prodotti aggiunti: " + String.valueOf(numprodaggiunti) + "        ");
                    }
                }

            }
        });
        manageprod.add(rimuoviprod);

        JButton svuotaprod = new JButton("Svuota carrello");
        svuotaprod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                numprodaggiunti = 0;
                prodAggiunti.setText("        #Prodotti aggiunti: " + String.valueOf(numprodaggiunti) + "        ");
            }
        });
        manageprod.add(svuotaprod);
        
        
        JButton RiepilogoOrdini = new JButton("Riepilogo Ordini");
        RiepilogoOrdini.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                JButton btn = (JButton) e.getSource();
                
                if(ViewOrdiniRiep == false){
                    btn.setBackground(Color.YELLOW);
                    btn.setForeground(Color.RED);  
                    renderViewRiepilogo();
                    ViewOrdiniRiep = true;
                }
                
                else{
                    btn.setBackground(Color.darkGray);
                    btn.setForeground(Color.white); 
                    fotopan.removeAll();
                    fotopan.add(photo);
                    fotopan.updateUI();
                    ViewOrdiniRiep = false;
                
                }
                
                
            }
        });
        manageprod.add(RiepilogoOrdini);
        
        
        
        prodAggiunti = new JLabel("        #Prodotti aggiunti: 0        ");
        prodAggiunti.setFont(new Font("Arial Black", Font.BOLD, 15));
        prodAggiunti.setForeground(Color.red);
        manageprod.add(prodAggiunti);
        
        
       

        effettuaPrelievo = new JButton("PRELEVA");
        effettuaPrelievo.setFont(new Font("Arial Black", Font.BOLD, 16));
        effettuaPrelievo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int OpzioneScelta = JOptionPane.showConfirmDialog(getParent(), "Sei sicuro di voler prelevare i prodotti specificati nel carrello?");
                if (OpzioneScelta == JOptionPane.OK_OPTION) {
                    ProdottoDAO prodao = new ProdottoDAO();
                    PrelevaDAO predao = new PrelevaDAO();
                    
                    Preleva bean = new Preleva();
                    try {
                        bean.startOrdine();
                    } catch (InterruptedException ex) {
                        Logger.getLogger("genlog").warning("InterruptedException\n" + StockManagement.printStackTrace(ex));
                    }

                    try {
                        for (int i = 0; i < model.getRowCount(); i++) {
                            prodottoCorrente = prodao.getBySku(model.getValueAt(i, 0).toString());

                            if (check(prodottoCorrente, Integer.parseInt(model.getValueAt(i, 2).toString()))) { // Se la qty da prelevare è corretta
                                prodottoCorrente.setQty(prodottoCorrente.getQty() - Integer.parseInt(model.getValueAt(i, 2).toString()));
                                prodao.update(prodottoCorrente);
                                
                               Preleva pre = new Preleva(Integer.parseInt(model.getValueAt(i, 2).toString()), frameprinc.nomeuser, model.getValueAt(i, 0).toString());
                                predao.add(pre);
                                
                                
                            }

                        }

                        refreshTab();
                        
                    } catch (SQLException ex) {
                        Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                    } catch (InterruptedException ex) {
                       Logger.getLogger("genlog").warning("InterruptedException\n" + StockManagement.printStackTrace(ex));
                    }

                }

            }

            private boolean check(Prodotto prodottoCorrente, int qtydaTogliere) {

                if (!prodottoCorrente.isInstock()) {
                    JOptionPane.showMessageDialog(getParent(), "Il prodotto non è in stock !!!");
                    return false;
                }
                if (qtydaTogliere <= 0) {
                    JOptionPane.showMessageDialog(getParent(), "inserisci una quantità positiva");
                    return false;
                }

                if (qtydaTogliere > (prodottoCorrente.getQty() - prodottoCorrente.getQty_min())) {
                    JOptionPane.showMessageDialog(getParent(), "Attenzione! quantità insufficiente per "+ prodottoCorrente.getSku()+ "\nNon puoi prelevare più di "+ (prodottoCorrente.getQty() - prodottoCorrente.getQty_min()) + " unità!\nIl prodotto non verrà aggiunto nell'ordine");
                    return false;
                }

                if (qtydaTogliere > prodottoCorrente.getQty()) {
                    JOptionPane.showMessageDialog(getParent(), "Non puoi prendere più di " + prodottoCorrente.getQty() + " unità!");
                    return false;
                }

                return true;

            }
        });

        manageprod.add(effettuaPrelievo);

        info.add(manageprod);

        infolabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        carre.add(infolabel);
        carre.add(info);

        sxpan.add(carre);

        fotopan = new JPanel();
        fotopan.setLayout(new BoxLayout(fotopan, BoxLayout.PAGE_AXIS));
        fotopan.setBorder(new EmptyBorder(40, 40, 40, 40));
        photo = new RoundedPanel();
        photo.setLayout(new GridLayout(1, 1));
        photo.setBackground(Color.darkGray);
        photobtn = new JButton();
        photo.add(photobtn);
        fotopan.add(photo);

        princ.add(fotopan);

        super.add(princ);

    }
    
    
    public OrdiniPanel getInstance() {
             return this;

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
        try {
            
           
            casella.setBackground(Color.darkGray);
            casella.setText("");
            numprodaggiunti = 0;
            prodAggiunti.setText("        #Prodotti aggiunti: 0        ");
            model.setRowCount(0);
            if(model2 != null)model2.setRowCount(0);
            
            tabnomeprodotto.refresh();
             tabnomeprodotto.revalidate();
            
            PrelevaDAO predao = new PrelevaDAO();
            
            
            for (ArrayList<String> ordine : predao.groupByPrelievi()) {
                BigDecimal costoo = new BigDecimal(String.valueOf(ordine.get(2)));
                String coast = costoo.toPlainString();

                if(coast.contains(".") == true){
                    int punto = (char) coast.indexOf('.');
                    
                    if(coast.substring(punto).length() > 5){
                        coast = coast.substring(0, punto+5);
                        System.out.println(coast);
                    }
                }
                // CAZZZZZ
                if(model2 != null )model2.addRow(new Object[]{ordine.get(0), ordine.get(3), ordine.get(1), coast,  "Ricarica", "Visualizza"});

            }
        } catch (SQLException ex) {
           Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        }
        
        

    }

    public void setComunicator(FramePrincipale princ) {
        frameprinc = princ;

    }

    public void windowPrelCreate(String mysku) {

        try {
            
            skusel = mysku;
            
            JDialog f = new JDialog();
            f.setResizable(false);
            f.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            f.setSize(300, 115);
            f.setLocationRelativeTo(null);
            f.setModal(true);

            f.setTitle(skusel);

            ProdottoDAO prodao = new ProdottoDAO();
            Prodotto pro = prodao.getBySku(skusel);
            System.out.println("ECCO IL MOTIVO DFL DANNO "+pro.getSku()+" "+ pro.getQty()+ " "+pro.getQty_min());

            JPanel main = new JPanel();

            JLabel co = new JLabel("Quantità da prelevare:    ");
            main.add(co);

            JPanel p_arrivati = new JPanel();
            p_arrivati.setLayout(new GridLayout(1, 2));
            JTextField qtyp = new JTextField(5);
            qtyp.addKeyListener(new KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent e) {
                    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                        
                        if(Integer.parseInt(qtyp.getText()) <= 0){
                            JOptionPane.showMessageDialog(null, "Nessuna unità prelevabile!");
                            return;
                        }


                         if (Integer.parseInt(qtyp.getText()) > (pro.getQty() - pro.getQty_min())) {
                            JOptionPane.showMessageDialog(null, "Non puoi prelevare più di " + (pro.getQty() - pro.getQty_min()) + " unità!");
                            return;
                        }
                        // CHECK SE QUESTO PRODOTTO E? GIA' NEL CARRELLO
                        if (controlloProdottoUguale(skusel)) {
                            JOptionPane.showMessageDialog(null, "Hai già aggiunto questo prodotto al carrello!! (Modifica la quantità nel carrello per aggiungere nuovi prodotti)");
                            f.dispose();
                            return;
                        }
                        addToCarrello(Integer.parseInt(qtyp.getText()));
                        f.dispose();

                    }
                }
            });

            ((AbstractDocument) qtyp.getDocument()).setDocumentFilter(new DocumentFilter() {
                Pattern regEx = Pattern.compile("\\d*");

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    Matcher matcher = regEx.matcher(text);
                    if (!matcher.matches()) {
                        return;
                    }
                    super.replace(fb, offset, length, text, attrs);
                }
            });

            p_arrivati.add(qtyp);
            JLabel txt = new JLabel(" /" + (pro.getQty() - pro.getQty_min()));
            p_arrivati.add(txt);
            main.add(p_arrivati);

            f.add(main, BorderLayout.CENTER);

            JButton ok = new JButton("Aggiungi al carrello");
            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    if(qtyp.getText().length() <= 0){
                        JOptionPane.showMessageDialog(null, "Il campo non può essere vuoto!");
                        return;
                     }
                    
                    if(Integer.parseInt(qtyp.getText()) <= 0){
                        JOptionPane.showMessageDialog(null, "Nessuna unità prelevabile!");
                        return;
                    }

                    if (Integer.parseInt(qtyp.getText()) > (pro.getQty() - pro.getQty_min())) {
                        JOptionPane.showMessageDialog(null, "Non puoi prelevare più di " + (pro.getQty() - pro.getQty_min()) + " unità!");
                        return;
                    }
                    // CHECK SE QUESTO PRODOTTO E? GIA' NEL CARRELLO
                    if (controlloProdottoUguale(skusel)) {
                        JOptionPane.showMessageDialog(null, "Hai già aggiunto questo prodotto al carrello!!");
                        f.dispose();
                        return;
                    }
                    addToCarrello(Integer.parseInt(qtyp.getText()));
                    f.dispose();

                }

            });
            f.add(ok, BorderLayout.SOUTH);
            f.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        }
    }

    public void addToCarrello(int qtyScelta) {

        try {
            
            ProdottoDAO prodao = new ProdottoDAO();
            Prodotto p = prodao.getBySku(skusel);

            model.addRow(new Object[]{p.getSku(), p.getNome(), qtyScelta, p.getCategoria(), p.getNote(), p.isInstock()});
            numprodaggiunti += qtyScelta;
            prodAggiunti.setText("        #Prodotti aggiunti: " + String.valueOf(numprodaggiunti) + "        ");

        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        }

    }

    public void setPhoto(String sku) {

        try {
            ProdottoDAO prodao = new ProdottoDAO();
            Prodotto p = prodao.getBySku(sku);
            if(p.getFoto() == null || p.getFoto().equals("null")){
                photobtn.setText("NESSUNA FOTO\n PER QUESTO PRODOTTO");
            } 
            System.out.println(p.getFoto());
            ImageIcon icon = new ImageIcon(p.getFoto());
            Image ImmagineScalata = icon.getImage().getScaledInstance(600, 800, Image.SCALE_DEFAULT);
            icon.setImage(ImmagineScalata);
            photobtn.setIcon(icon);
            photobtn.setText("");
            
            //photobtn.setBackground(Color.red);
            
            
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        }
        

    }

    public boolean controlloProdottoUguale(String skuscelto) {
        System.out.println("Controllo Prodotto uguale");
        for (int i = 0; i < table.getRowCount(); i++) {
            System.out.println("Prodotto già nel carrello" + model.getValueAt(i, 0).toString() + "   sku scelto:" + skuscelto);
            if (model.getValueAt(i, 0).toString().equals(skuscelto)) {
                return true;
            }
        }

        return false;
    }
    
    
    
    public void renderViewRiepilogo(){
    
     
      fotopan.removeAll();
      fotopan.updateUI();
      

        String[] columnNames = {"# Ordine", "Data ordine", "# prodotti ordinati", "Costo Totale", "Ricarica ordine", "Visualizza"};

        Object[][] data = {};

        model2 = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column >= 3; //il numero di celle editabili...
            }
        };
        JTable table2 = new JTable(model2){
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if(column == 4) return comp;
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
        table2.getTableHeader().setReorderingAllowed(false);

        table2.getColumnModel().getColumn(4).setCellRenderer(new TableButtonRenderer());
        table2.getColumnModel().getColumn(4).setCellEditor(new TableRenderer(new JCheckBox()));

        table2.getColumnModel().getColumn(5).setCellRenderer(new TableButtonRenderer());
        table2.getColumnModel().getColumn(5).setCellEditor(new TableRenderer(new JCheckBox()));

        JScrollPane sp2 = new JScrollPane(table2);
        sp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(24, 53, 90), new Color(24, 53, 90)), "Riepilogo ordini effettuati", TitledBorder.CENTER, TitledBorder.TOP));
        fotopan.add(sp2);
        JButton ordinerimuovi = new JButton("Cancella ordine selezionato");
        ordinerimuovi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrelevaDAO daor = new PrelevaDAO();
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
        fotopan.add(ordinerimuovi);

        PrelevaDAO ordaoo = new PrelevaDAO();

        try {
            // String[] columnNames = {"# Ordine", "Data ordine", "# prodotti ordinati", "Costo Totale ordine", "In spedizione", "Controlla ordine", "Ricarica ordine"};
            for (ArrayList<String> ordine : ordaoo.groupByPrelievi()) {
                BigDecimal costoo = new BigDecimal(String.valueOf(ordine.get(2)));
                
                String coast = costoo.toPlainString();

                if(coast.contains(".") == true){
                    int punto = (char) coast.indexOf('.');
                    
                    if(coast.substring(punto).length() > 5){
                        coast = coast.substring(0, punto+5);
                        System.out.println(coast);
                    }
                }
                    
                model2.addRow(new Object[]{ordine.get(0), ordine.get(3), ordine.get(1), coast,  "Ricarica", "Visualizza"});
            }
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        } 


        fotopan.revalidate();
       
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
                
                if (button.getText().equals("Ricarica")) {
                    model.setRowCount(0);
                    refreshTab();
                    caricaOrdine(table.getValueAt(row, 0).toString());
                    
                }
                
                
            if (button.getText().equals("Visualizza")) {
                    try {
                        PrelevaDAO predao = new PrelevaDAO();
                        FrameRiepPreleva f = new FrameRiepPreleva(getInstance(), table.getValueAt(row, 0).toString(), table.getValueAt(row, 1).toString(), predao.getUser(table.getValueAt(row, 0).toString()));
                        f.setResizable(false);
                        //f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        f.setSize(1000, 400);
                        f.setLocationRelativeTo(null);  // CENTRA
                        f.setVisible(true);
                        f.setTitle("Riepilogo ordine: " + table.getValueAt(row, 0));
                        //f.setTitle("#ORDINE: PRE-1 del 28/07/2020 @admin");
                    } catch (SQLException ex) {
                        Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
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
    
    
    
        class CustomStockRender extends JButton implements TableCellRenderer {

        public CustomStockRender() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                if (value.toString().equals("false")) {
                    setBackground(new Color(244, 80, 37));    
                } else if(value.toString().equals("true")){
                    setBackground(new Color(126, 169, 93));
                }
            

            return this;
        }
    }
        
        
        
       public void caricaOrdine(String numerordine) {
    
        try {
            JOptionPane.showMessageDialog(this, "Sto caricando l'ordine " + numerordine + " nel carrello ...");
            
            ProdottoDAO prodao = new ProdottoDAO();
            PrelevaDAO ordao = new PrelevaDAO();

            //costocarrell = 0;
            
            int i = 0;
            for (Preleva o : ordao.getByNum(numerordine)) {
                
                Prodotto p = prodao.getBySku(o.getProdotto_sku());

                model.addRow(new Object[]{p.getSku(), p.getNome(), o.getQty(), p.getCategoria(), p.getNote(), p.isInstock()});
                numprodaggiunti += Integer.parseInt(model.getValueAt(i++, 2).toString());
                prodAggiunti.setText("        #Prodotti aggiunti: " + String.valueOf(numprodaggiunti) + "        ");
                
                
            } 
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        }
       }   
 }

/*
        conferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (check()) {
                    int OpzioneScelta = JOptionPane.showConfirmDialog(getParent(), "Sei sicuro di voler effettuare tali modifiche?");
                    if (OpzioneScelta == JOptionPane.OK_OPTION) {
                        System.out.println("OOOOOOOOKKKKKK EFFETTUO PRELIEVO UNITA'");

                        prodottoCorrente.setQty(qtyAttuale - Integer.parseInt(quantdaprend.getText()));
                        OrdineDAO ordao = new OrdineDAO();
                        try {
                            dao.update(prodottoCorrente);
                             Logger.getLogger("userlog").info("Ho prelevato:\n"+"Sku= " + prodottoCorrente.getSku() + " Qty prelevata= " +quantdaprend.getText());
                            casella.setText(prodottoCorrente.getSku());
                        } catch (SQLException ex) {
                            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                        }
                    }
                }
            }

            private boolean check() {

                int qtydaTogliere;
                try { //Controlla se sono interi...
                    qtydaTogliere = Integer.parseInt(quantdaprend.getText());

                } catch (NumberFormatException e) {
                    Logger.getLogger("genlog").warning("NumberFormatException\n" + StockManagement.printStackTrace(e));
                    return false;
                }

                qtydaTogliere = Integer.parseInt(quantdaprend.getText());

                if (!prodottoCorrente.isInstock()) {
                    JOptionPane.showMessageDialog(getParent(), "Il prodotto non è in stock !!!");
                    return false;
                }
                if (qtydaTogliere <= 0) {
                    JOptionPane.showMessageDialog(getParent(), "inserisci una quantità positiva");
                    return false;
                }

                if (qtydaTogliere > (qtyAttuale - qtyMin)) {
                    JOptionPane.showMessageDialog(getParent(), "Non puoi prendere più di " + (qtyAttuale - qtyMin) + " unità!");
                    return false;
                }

                if (qtydaTogliere > qtyAttuale) {
                    JOptionPane.showMessageDialog(getParent(), "Non puoi prendere più di " + qtyAttuale + " unità!");
                    return false;
                }

                return true;

            }
        });
        DXdown.add(conferma);
        princ.add(DXdown);

        super.add(princ);
 */
