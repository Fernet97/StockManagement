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
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
    private JPanelNomeProdotto tabnomeprodotto;
    private JLabel prodAggiunti;
    private int numprodaggiunti = 0;
    private final JButton photobtn;

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
        casella.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent arg0) {
            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                try {
                    casella.setForeground(Color.white);
                    casella.setBackground(Color.gray);
                    if (casella.getText().length() == 0) {
                        return;
                    }

                    String text = casella.getText();

                    ProdottoDAO prodao = new ProdottoDAO();
                    Prodotto p = prodao.getBySku(text);
                    if (p.getSku() == null) {
                        //++++++++++++++++++++
                        //non e' nemmeno un nome?
                        if (prodao.getByNome(text).size() <= 0) {
                            casella.setForeground(Color.white);
                            casella.setBackground(Color.red);
                            // System.out.println("non c'è nessun nome");
                        } else {
                            casella.setBackground(Color.yellow);
                            casella.setForeground(Color.red);
                            tabnomeprodotto.aggiornaNome(casella.getText());
                        }

                    } else { // SE HA TROVATO UNO SKU

                        OrdineDAO ordinedao = new OrdineDAO();
                        if (ordinedao.getFPr(text) == null || ordinedao.getFPr(text).length() < 2) {
                            System.out.println("E' uno sku senza fonritore");
                            JOptionPane.showMessageDialog(null, "Fornitore non associato. Contattare l'amministratore.");
                            casella.setBackground(Color.red);
                            return;
                        }

                        casella.setBackground(Color.green);
                        skusel = text;
                        System.out.println("SCEGLIERE LA QTY DA PRELEVARE");
                        if(!p.isInstock()){
                            JOptionPane.showMessageDialog(null, "Prodotto non in stock. Contattare l'amministratore.");
                            return;
                        }
                        // CASELLA SCELTA QTY
                        windowPrelCreate();

                        //DOPO AGGIUNGI AL CARRELLO
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(OrdiniPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                insertUpdate(e);
                tabnomeprodotto.aggiornaNome(casella.getText());
            }

        }
        );

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
                return false;
            }
        };
        model.addColumn("SKU");
        model.addColumn("Nome");
        model.addColumn("Qty Prelevata");
        model.addColumn("Categoria");
        model.addColumn("Note");
        model.addColumn("Negozio");
        
                


        table.setModel(model);
        table.getColumnModel().getColumn(5).setCellRenderer(new CustomNegozioRender());

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                 if(model.getRowCount() > 0 && table.getSelectedRow()!= -1){
                    setPhoto(table.getValueAt(table.getSelectedRow(), 0).toString());
                 }
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

        prodAggiunti = new JLabel("        #Prodotti aggiunti: 0        ");
        prodAggiunti.setFont(new Font("Arial Black", Font.BOLD, 15));
        prodAggiunti.setForeground(Color.red);
        manageprod.add(prodAggiunti);

        JButton effettuaPrelievo = new JButton("       PRELEVA       ");
        effettuaPrelievo.setFont(new Font("Arial Black", Font.BOLD, 16));
        effettuaPrelievo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int OpzioneScelta = JOptionPane.showConfirmDialog(getParent(), "Sei sicuro di voler prelevare i prodotti specificati nel carrello?");
                if (OpzioneScelta == JOptionPane.OK_OPTION) {
                    ProdottoDAO prodao = new ProdottoDAO();

                    try {
                        for (int i = 0; i < model.getRowCount(); i++) {
                            prodottoCorrente = prodao.getBySku(model.getValueAt(i, 0).toString());

                            if (check(prodottoCorrente, Integer.parseInt(model.getValueAt(i, 2).toString()))) { // Se la qty da prelevare è corretta
                                prodottoCorrente.setQty(prodottoCorrente.getQty() - Integer.parseInt(model.getValueAt(i, 2).toString()));
                                prodao.update(prodottoCorrente);
                            }

                        }

                        refreshTab();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
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
                    JOptionPane.showMessageDialog(getParent(), "Non puoi prendere più di " + (prodottoCorrente.getQty() - prodottoCorrente.getQty_min()) + " unità!");
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

        JPanel fotopan = new JPanel();
        fotopan.setLayout(new BoxLayout(fotopan, BoxLayout.PAGE_AXIS));
        fotopan.setBorder(new EmptyBorder(40, 40, 40, 40));
        RoundedPanel photo = new RoundedPanel();
        photo.setLayout(new GridLayout(1, 1));
        photo.setBackground(Color.darkGray);
        photobtn = new JButton();
        photo.add(photobtn);
        fotopan.add(photo);

        princ.add(fotopan);

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
        casella.setBackground(Color.gray);
        casella.setText("");
        prodAggiunti.setText("        #Prodotti aggiunti: 0        ");
        model.setRowCount(0);


    }

    public void setComunicator(FramePrincipale princ) {
        frameprinc = princ;

    }

    public void windowPrelCreate() {

        try {
            JDialog f = new JDialog();
            f.setResizable(false);
            f.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            f.setSize(300, 115);
            f.setLocationRelativeTo(null);
            f.setModal(true);

            f.setTitle(skusel);

            ProdottoDAO prodao = new ProdottoDAO();
            Prodotto pro = prodao.getBySku(skusel);

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
                            JOptionPane.showMessageDialog(null, "Hai già aggiunto questo prodotto al carrello!!");
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

            JButton ok = new JButton("O K   K   E   Y");
            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
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
            Logger.getLogger(OrdiniPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addToCarrello(int qtyScelta) {

        try {
            
            ProdottoDAO prodao = new ProdottoDAO();
            Prodotto p = prodao.getBySku(skusel);

            model.addRow(new Object[]{p.getSku(), p.getNome(), qtyScelta, p.getCategoria(), p.getNote(), p.isNegozio()});
            numprodaggiunti += qtyScelta;
            prodAggiunti.setText("        #Prodotti aggiunti: " + String.valueOf(numprodaggiunti) + "        ");

        } catch (SQLException ex) {
            Logger.getLogger(OrdiniPanel.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(OrdiniPanel.class.getName()).log(Level.SEVERE, null, ex);
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
    
    
    
        class CustomNegozioRender extends JButton implements TableCellRenderer {

        public CustomNegozioRender() {
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
