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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.table.DefaultTableModel;
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
    public JList list;
    private final DefaultListModel listModel;
    private double costocarrell = 0;
    public JTextField casella;
    public JDialog popup;
    public String skusel;
    private JTextField ggallacons;
    private JTextField casellaqty;
    private FramePrincipale frameprinc;
    private final JPanelNomeProdotto tabnomeprodotto;


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
        
        //METTERE LISTENER CASELLA
        
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
            public void mouseReleased(MouseEvent e) {
                // se non è doppio click
                if (e.getClickCount() != 2) {
                    return;
                }
                int index = list.getSelectedIndex(); // Prodotto selezionato dalla jlist
                String s = (String) list.getSelectedValue();

                //aggiungiTOcarrello(list.getSelectedValue().toString());
            }
        });
        
        //****************************************
        tabnomeprodotto = new JPanelNomeProdotto(casella,"", true);  
        sxpan.add(tabnomeprodotto);

        JPanel carre = new JPanel();
        carre.setLayout(new BoxLayout(carre, BoxLayout.PAGE_AXIS));
        carre.add(Box.createRigidArea(new Dimension(20, 0)));
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
        model.addColumn("Nome");
        model.addColumn("Qty Prelevata");
        model.addColumn("Categoria");
        model.addColumn("Note");
        model.addColumn("Negozio");
        table.setModel(model);

        JScrollPane sp = new JScrollPane(table);

        info.add(sp);

        JPanel manageprod = new JPanel();
        manageprod.setLayout(new GridBagLayout());
        JButton rimuoviprod = new JButton("Elimina prod. selezionato");
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
        
        JLabel prodAggiunti = new JLabel("        #Prodotti aggiunti: XXX        ");
        prodAggiunti.setFont(new Font("Arial Black", Font.BOLD, 15));
        prodAggiunti.setForeground(Color.red);
        manageprod.add(prodAggiunti);

        
        JButton effettuaPrelievo  = new JButton("       PRELEVA       ");
        effettuaPrelievo.setFont(new Font("Arial Black", Font.BOLD, 16));
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
        photo.setBackground(Color.darkGray);
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
        jComboBox.setSelectedIndex(0);
        listModel.clear();
        // model.setRowCount(0); magari il carrello non lo svuoto ogni volta al cambio scheda



    }



        
        
    public void setComunicator(FramePrincipale princ) {
        frameprinc = princ;

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