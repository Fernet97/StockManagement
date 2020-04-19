/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Ordine;
import beans.Prodotto;
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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
class FrameRiepilogo extends JDialog {

    private DefaultTableModel model2;
    private final JTable table2;
    private final String Numordine;
    public OrdiniAdminPanel panadmin;
    private String arrivato;
    private String messoInStock;
    private String costoTot;
    private boolean giamessoaposto;
    private final JScrollPane sp3;
    private final JPanel pdown;
    private JLabel notepresenti;
    private final JTextArea casNote;

    public FrameRiepilogo(OrdiniAdminPanel panAdmin, String numordine, String dataordine, String costoTot, boolean giamessoaposto) {
        this.panadmin = panAdmin;
        this.Numordine = numordine;
        this.costoTot = costoTot;
        this.giamessoaposto = giamessoaposto;

        setModal(true);

        ImageIcon img = new ImageIcon((getClass().getResource("/res/img/logo-Icon.png")));
        this.setIconImage(img.getImage());

        String[] columnNames = {"#Ordine", "Fornitore", "SKU prodotto", "Costo", "Quantita' arrivata/ Quantità prevista", "Data di arrivo", " E' Arrivato?", "Messo in Stock?", "Conteggio"};

        Object[][] data = {};

        model2 = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column >= 8; //il numero di celle editabili...
            }
        };
        table2 = new JTable(model2);
        table2.getTableHeader().setReorderingAllowed(false);

        table2.getColumnModel().getColumn(6).setCellRenderer(new CustomStockRender());
        table2.getColumnModel().getColumn(7).setCellRenderer(new CustomStockRender());

        table2.getColumnModel().getColumn(8).setCellRenderer(new TableButtonRenderer());
        table2.getColumnModel().getColumn(8).setCellEditor(new TableRenderer(new JCheckBox()));

        JScrollPane sp2 = new JScrollPane(table2);
        sp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.red, Color.red), "  #ORDINE: " + numordine + "  del " + dataordine + "   ", TitledBorder.CENTER, TitledBorder.TOP));
        
        add(sp2, BorderLayout.CENTER);

        // VISIBILE SOLO SE PREMO BUTTON NOTE
        casNote = new JTextArea();
        casNote.setAlignmentX(LEFT_ALIGNMENT);
        casNote.setLineWrap(true);
        casNote.setRows(5);
        casNote.setColumns(20);        
        casNote.setText("");
        sp3 = new JScrollPane(casNote);
        sp3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.red, Color.red), "Note", TitledBorder.CENTER, TitledBorder.TOP));
        
        
        JButton bannulla = new JButton("Annulla");
        bannulla.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }

        });

        // Dobbiamo autocompilare i conteggi??
        JButton stockSelezion = new JButton("Metti in Stock");
        stockSelezion.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                if( table2.getSelectedRows().length== 0) JOptionPane.showMessageDialog(null, "Devi selezionare almeno un prodotto!");
                
                for (int i = 0; i < table2.getSelectedRows().length; i++) {
                    // DEVE ESSERE PRIMA ARRIVATO
                    if(model2.getValueAt(i, 6).toString().equals("Sì"))
                        model2.setValueAt("Sì", i, 7);
                    else{
                        JOptionPane.showMessageDialog(null, "Il prodotto "+model2.getValueAt(i, 2)+" non è ancora arrivato, non puoi metterlo in stock!");}
                }
            }
        });

        //BOTTONI COMMENTI
        JButton comment = new JButton("Note");
        comment.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            
                //Ho settato un testo
                if(casNote.getText().length() > 0){
                    OrdineDAO ordao = new OrdineDAO();
                    try {
                        if(ordao.getNote(Numordine).length()<=0) notepresenti.setText("     Hai appena aggiunto una nota per quest'ordine");
                        else notepresenti.setText("     Hai già registrato una nota per questo ordine");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "errore nel caricamento della nota ..");}
                    
                }
                else notepresenti.setText("");
                
                
               if(comment.getForeground() == Color.red){
                   remove(sp3);
                   setVisible(true);
                    comment.setBackground(Color.GRAY);
                    comment.setForeground(Color.white);
                }
               
               else{ 
                    sp3.setVisible(true);
                    add(sp3, BorderLayout.EAST);
                    setVisible(true);
                    comment.setBackground(Color.yellow);
                    comment.setForeground(Color.red);
       
                    
               }
            }

        });

        JButton bconferma = new JButton("Conferma");
        bconferma.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int OpzioneScelta = JOptionPane.showConfirmDialog(getParent(), "Sei sicuro di voler apportare le seguenti modifiche?????");

                if (OpzioneScelta == JOptionPane.OK_OPTION) {

                    try {
                        OrdineDAO ordinedao = new OrdineDAO();

                        // SALVA I DATI MODIFICATI
                        for (int i = 0; i < model2.getRowCount(); i++) {

                            String qtyArrivata = model2.getValueAt(i, 4).toString();
                            qtyArrivata = qtyArrivata.substring(0, qtyArrivata.indexOf("/"));
                            int qtyarriv = Integer.parseInt(qtyArrivata);

                            // Se è arrivato
                            if (model2.getValueAt(i, 6).toString().equals("Sì")) {
                                // setto a -1 gg
                                ordinedao.updateGG(Numordine, model2.getValueAt(i, 2).toString(), -1);

                                // setto la qty arrivata nwll'ordine
                                ordinedao.setQtyArrivata(Numordine, model2.getValueAt(i, 2).toString(), qtyarriv);
                            }

                            // Se è l'ho messo a posto
                            if (model2.getValueAt(i, 7).toString().equals("Sì")) {

                                // setto a -2 gg
                                ordinedao.updateGG(Numordine, model2.getValueAt(i, 2).toString(), -2);

                                //Setto la qty arrivata nell'ordine 
                                ordinedao.setQtyArrivata(Numordine, model2.getValueAt(i, 2).toString(), qtyarriv);

                                //Sommo questa qty arrivata alla qty del prodotto corrispondente
                                ProdottoDAO daop = new ProdottoDAO();
                                Prodotto p = daop.getBySku(model2.getValueAt(i, 2).toString());
                                p.setQty(p.getQty() + qtyarriv); // SOMMO LA NUOVA QTY ARRIVATA
                                p.setInstock(true);
                                daop.update(p);

                            }
                            
                            // SALVO EVENTUALI NOTE
                            if(casNote.getText().length() > 0){
                                Ordine o = new Ordine(Numordine,casNote.getText(), "BOOOOOOOOOOOOH");
                                if(ordinedao.getNote(Numordine).length()>0 ){
                                    ordinedao.updateNote("gesu", "admin", "ORD-1");}
                                else ordinedao.addNote(o);
                            }
                            else { // Se ho cancellato i commenti nella casella, allora cancella nel db quel commento
                                 if(ordinedao.getNote(Numordine).length()>=0 ){
                                    ordinedao.removeNote(Numordine);
                                 }
                            }

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FrameRiepilogo.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    setVisible(false);
                    panadmin.refreshTab();
                }
            }

        });

        pdown = new JPanel(new GridLayout(1, 3));
        // pdown.setBackground(Color.red); // FACCIAMO CON IL COLORE DEL SEMAFORO?
        pdown.setLayout(new GridBagLayout());

        pdown.add(bannulla);
        pdown.add(bconferma);

        pdown.add(new JLabel("              "));

        JLabel costoTotale = new JLabel("   Costo totale ordine:  " + costoTot);
        costoTotale.setFont(new Font("Arial Black", Font.BOLD, 12));
        pdown.add(costoTotale);

        pdown.add(new JLabel("              "));

        pdown.add(stockSelezion);
        pdown.add(comment);
        
        notepresenti = new JLabel("");
        notepresenti.setForeground(Color.red);
        pdown.add(notepresenti);


        add(pdown, BorderLayout.SOUTH);

        refreshOrdini();

    }

    public void refreshOrdini() {

        model2.setRowCount(0);

        OrdineDAO ordaoo = new OrdineDAO();
        
        try {
            //Prendo eventuali note del db per quest'ordine
            casNote.setText(ordaoo.getNote(Numordine));
            if(ordaoo.getNote(Numordine).length()>0)  notepresenti.setText("    Hai già registrato una nota per questo ordine");
            
            //  String[] columnNames = {"#Ordine","Fornitore" ,"SKU prodotto", "Costo", "Quantita' arrivata", "Data prevista di arrivo", " E' Arrivato?", "Messo in Stock?"};
            ProdottoDAO prodao = new ProdottoDAO();
            arrivato = "";
            messoInStock = "";
            String datao = "";
            for (Ordine ordine : ordaoo.getByNum(Numordine)) {

                if (ordine.getGiorni_alla_consegna() < 0) {
                    arrivato = "Sì";
                    datao = ordine.getData_arrivo() + " (arrivato)"; // data quando è arrivata 

                    if (ordine.getGiorni_alla_consegna() <= -2) {
                        messoInStock = "Sì";
                    } else {
                        messoInStock = "No";
                    }
                } else {
                    arrivato = "No";
                    messoInStock = "No";
                    datao = ordaoo.dataArrivo(ordine.getN_ordine(), ordine.getProdotto_sku()) + " (previsto)"; // data prevista
                }

                BigDecimal costoo = new BigDecimal(String.valueOf(prodao.getBySku(ordine.getProdotto_sku()).getCosto()));

                model2.addRow(new Object[]{ordine.getN_ordine(), ordine.getFk_fornitore(), ordine.getProdotto_sku(), costoo.toPlainString(),
                    ordine.getQty_arrivata() + "/" + ordine.getQty_in_arrivo(), datao, arrivato, messoInStock});
                
                
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

            setIcon(ImpostaImg("/res/img/pencil.png"));
            if (giamessoaposto) {
                setEnabled(false);
                setIcon(null);
            }

            return this;
        }

    }

    // BUTTON per "è arrivato" e per "Messo in Stock"
    class CustomStockRender extends JButton implements TableCellRenderer {

        public CustomStockRender() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            // se è colonna è arrivato
            if (column == 6) {
                if (value.toString().equals("No")) {
                    setBackground(new Color(244, 80, 37));    // ROSSO       
                } else {
                    // VERDE
                    setBackground(new Color(126, 169, 93));
                    // SOLO X BUTTON E' ARRIVATO: diventa giallo se qty conteggiata < qty che doveva arrivare
                    String s = model2.getValueAt(row, 4).toString();
                    String qtyconteggiata = s.substring(0, s.indexOf('/'));
                    String qtyprevista = s.substring(s.indexOf('/') + 1);

                    if (Integer.parseInt(qtyconteggiata) < Integer.parseInt(qtyprevista)) {
                        setBackground(Color.yellow); // GIALLO
                    }
                }
            } else if (column == 7) {
                if (value.toString().equals("No")) {
                    setBackground(new Color(244, 80, 37));    // ROSSO       
                } else {
                    setBackground(new Color(126, 169, 93));
                }
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

            skusel = table.getValueAt(row, 2).toString();
            button.setForeground(Color.black);
            button.setBackground(UIManager.getColor("Button.background"));

            label = (value == null) ? "" : value.toString();
            button.setText(label);
            button.setIcon(ImpostaImg("/res/img/pencil.png"));

            if (giamessoaposto) {
                button.setEnabled(false);
                button.setIcon(null);
            }

            clicked = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (clicked) // SE CLICCATO QUEL BOTTONE:::::::::::::
            {
                JDialog f = new JDialog();
                f.setResizable(false);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setSize(280, 100);
                f.setLocationRelativeTo(null);
                f.setModal(true);

                f.setTitle(Numordine + "| " + skusel);

                ProdottoDAO prodao = new ProdottoDAO();

                JPanel main = new JPanel();
                
                JLabel co = new JLabel("# prodotti arrivati    ");
                main.add(co);

                JPanel p_arrivati = new JPanel();
                p_arrivati.setLayout(new GridLayout(1, 2));
                JTextField qtyarr = new JTextField(5);
                qtyarr.addKeyListener(new KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent e) {
                    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
 
                        String qtyChedovevaArrivare = model2.getValueAt(row, 4).toString();
                        qtyChedovevaArrivare = qtyChedovevaArrivare.substring(qtyChedovevaArrivare.indexOf('/'));
                        // Setta la qty arrivata davvero
                        model2.setValueAt(qtyarr.getText() + qtyChedovevaArrivare, row, 4);

                        // è arrivato
                        if (Integer.parseInt(qtyarr.getText()) > 0) {
                            model2.setValueAt("Sì", row, 6);
                        } else {
                            model2.setValueAt("No", row, 6);
                        }

                        f.setVisible(false);
                       
                    }
                }});

                String arrivat = model2.getValueAt(row, 4).toString();
                arrivat = arrivat.substring(0, arrivat.indexOf("/"));
                qtyarr.setText(arrivat);

                ((AbstractDocument) qtyarr.getDocument()).setDocumentFilter(new DocumentFilter() {
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

                p_arrivati.add(qtyarr);
                String qtyChedovevaArrivare = model2.getValueAt(row, 4).toString();
                qtyChedovevaArrivare = qtyChedovevaArrivare.substring(qtyChedovevaArrivare.indexOf('/'));
                JLabel txt = new JLabel(qtyChedovevaArrivare);
                p_arrivati.add(txt);
                main.add(p_arrivati);

                /*JCheckBox r2 = new JCheckBox();
                if (messoInStock.equals("Sì")) {
                    r2.setSelected(true);
                } else {
                    r2.setSelected(false);
                }
                r2.setAlignmentX(CENTER_ALIGNMENT);

                main.add(r2);*/

                f.add(main, BorderLayout.CENTER);

                JButton ok = new JButton("O K   K   E   Y");
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String qtyChedovevaArrivare = model2.getValueAt(row, 4).toString();
                        qtyChedovevaArrivare = qtyChedovevaArrivare.substring(qtyChedovevaArrivare.indexOf('/'));
                        // Setta la qty arrivata davvero
                        model2.setValueAt(qtyarr.getText() + qtyChedovevaArrivare, row, 4);

                        // è arrivato
                        if (Integer.parseInt(qtyarr.getText()) > 0) {
                            model2.setValueAt("Sì", row, 6);
                        } else {
                            model2.setValueAt("No", row, 6);
                        }

                        f.setVisible(false);

                    }

                });
                f.add(ok, BorderLayout.SOUTH);
                f.setVisible(!giamessoaposto);

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

    public ImageIcon ImpostaImg(String nomeImmag) {

        ImageIcon icon = new ImageIcon((getClass().getResource(nomeImmag)));
        Image ImmagineScalata = icon.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);
        icon.setImage(ImmagineScalata);
        return icon;
    }

}
