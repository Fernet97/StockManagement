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
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Fernet
 */
class FrameRiepilogo extends JFrame {

    private DefaultTableModel model2;
    private final JTable table2;
    private final String Numordine;
    public OrdiniAdminPanel panadmin;
    private String arrivato;
    private String messoInStock;
    
    public FrameRiepilogo(OrdiniAdminPanel panAdmin, String numordine, String dataordine) {
        panadmin = panAdmin;
        Numordine = numordine;

        ImageIcon img = new ImageIcon((getClass().getResource("/res/img/logo-Icon.png")));
        this.setIconImage(img.getImage());

        String[] columnNames = {"#Ordine", "Fornitore", "SKU prodotto", "Costo", "Quantita' arrivata/ Quantità prevista", "Data di arrivo", " E' Arrivato?", "Messo in Stock?", "Gestisci"};

        Object[][] data = {};

        model2 = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column >= 8; //il numero di celle editabili...
            }
        };
        table2 = new JTable(model2);
        table2.getTableHeader().setReorderingAllowed(false);

        table2.getColumnModel().getColumn(8).setCellRenderer(new TableButtonRenderer());
        table2.getColumnModel().getColumn(8).setCellEditor(new TableRenderer(new JCheckBox()));

        JScrollPane sp2 = new JScrollPane(table2);
        sp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.red, Color.red), "  #ORDINE: " + numordine + "  del "+dataordine+ "   ", TitledBorder.CENTER, TitledBorder.TOP));
        add(sp2, BorderLayout.CENTER);

        JButton bannulla = new JButton("Annulla");
        bannulla.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
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

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(FrameRiepilogo.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    setVisible(false);
                    panadmin.refreshTab();
                }
            }

        });

        JPanel pdown = new JPanel();
        pdown.setLayout(new GridBagLayout());
        pdown.add(bconferma);
        pdown.add(bannulla);
        add(pdown, BorderLayout.SOUTH);

        refreshOrdini();

    }

    public void refreshOrdini() {

        model2.setRowCount(0);

        OrdineDAO ordaoo = new OrdineDAO();

        try {
            //  String[] columnNames = {"#Ordine","Fornitore" ,"SKU prodotto", "Costo", "Quantita' arrivata", "Data prevista di arrivo", " E' Arrivato?", "Messo in Stock?"};

            ProdottoDAO prodao = new ProdottoDAO();
            arrivato = "";
             messoInStock = "";
             String datao = "";
            for (Ordine ordine : ordaoo.getByNum(Numordine)) {

                if (ordine.getGiorni_alla_consegna() < 0) {
                    arrivato = "Sì";
                    datao = ordine.getData_arrivo()+ " (arrivato)"; // data quando è arrivata 
                    
                    if (ordine.getGiorni_alla_consegna() <= -2) {
                        messoInStock = "Sì";
                    } else {
                        messoInStock = "No";
                    }
                } else {
                    arrivato = "No";
                    messoInStock = "No";
                    datao = ordaoo.dataArrivo(ordine.getN_ordine(), ordine.getProdotto_sku())+ " (previsto)"; // data prevista
                }

     
                model2.addRow(new Object[]{ordine.getN_ordine(), ordine.getFk_fornitore(), ordine.getProdotto_sku(), prodao.getBySku(ordine.getProdotto_sku()).getCosto(),
                    ordine.getQty_arrivata() +"/"+ordine.getQty_in_arrivo(), datao, arrivato, messoInStock});
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdiniAdminPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FrameRiepilogo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class TableButtonRenderer extends JButton implements TableCellRenderer {

        public TableButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());

            setIcon(ImpostaImg("/res/img/pencil.png"));

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
                    System.out.println("APRI FORMMMMm");
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

            clicked = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (clicked) // SE CLICCATO QUEL BOTTONE:::::::::::::
            {
                JFrame f = new JFrame();
                f.setResizable(false);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setSize(500, 150);
                f.setVisible(true);
                f.setTitle(Numordine + "| " + skusel);

                ProdottoDAO prodao = new ProdottoDAO();

                JPanel main = new JPanel();
                main.setLayout(new GridLayout(2, 3));

                JLabel co = new JLabel(" # prodotti arrivati");
                JLabel c1 = new JLabel("E' arrivato");
                JLabel c2 = new JLabel("l'ho messo in Stock");

                main.add(co);
                main.add(c1);
                main.add(c2);

                JPanel p_arrivati = new JPanel();
                p_arrivati.setLayout(new GridLayout(1, 2));
                JTextField qtyarr = new JTextField(10);
                
            
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

                JCheckBox r1 = new JCheckBox();
                if(arrivato.equals("Sì")) r1.setSelected(true);
                else r1.setSelected(false);
                r1.setAlignmentX(CENTER_ALIGNMENT);
              
                JCheckBox r2 = new JCheckBox();
                if(messoInStock.equals("Sì")) r2.setSelected(true);
                else r2.setSelected(false);                 
                r2.setAlignmentX(CENTER_ALIGNMENT);

                main.add(r1);
                main.add(r2);

                f.add(main);

                JButton ok = new JButton("O K   K   E   Y");
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String qtyChedovevaArrivare = model2.getValueAt(row, 4).toString();
                        qtyChedovevaArrivare = qtyChedovevaArrivare.substring(qtyChedovevaArrivare.indexOf('/'));
                        // Setta la qty arrivata davvero
                        model2.setValueAt(qtyarr.getText() + qtyChedovevaArrivare, row, 4);

                        // è arrivato
                        if (r1.isSelected()) {
                            model2.setValueAt("Sì", row, 6);
                        } else {
                            model2.setValueAt("No", row, 6);
                        }

                        if (r2.isSelected()) {
                            model2.setValueAt("Sì", row, 7);
                        } else {
                            model2.setValueAt("No", row, 7);
                        }

                        f.setVisible(false);

                    }

                });
                f.add(ok, BorderLayout.SOUTH);

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
