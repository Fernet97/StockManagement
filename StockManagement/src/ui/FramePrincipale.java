/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Ordine;
import others.JavaProcessId;
import beans.Prodotto;
import beans.Utente;
import dao.OrdineDAO;
import dao.ProdottoDAO;
import dao.UtenteDAO;
import database.DriverManagerConnectionPool;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import others.RoundedPanel;

/**
 *
 * @author Fernet
 */
public class FramePrincipale extends JFrame {

    private JPanel HomePanel;
    private CardLayout cardlayout;
    private JPanel pannellolaterale;
    private JPanel pannelloOpzioni;
    public ProdottiPanel prodotti;
    public CategoriePanel categorie;
    private CodiciPanel codici;
    private OrdiniPanel ordini;
    private OrdiniAdminPanel ordiniadmin;
    private DefaultTableModel model;
    private JTable table;
    private DefaultTableModel model2;
    private JTable table2;
    public String nomeuser;
    public Utente user;
    public ButtonDash button;
    public ButtonDash button1;
    public ButtonDash button2;
    public ButtonDash button3;
    public ButtonDash button4;
    public ButtonDash button5;
    private AnagrafichePanel anagrafiche;
    public boolean OrdiniStatus = false;
    

    public FramePrincipale(String nomeutente) {
        nomeuser = nomeutente;
        CreaGUI();
        refresh();
    }

    public void CreaGUI() {

        UtenteDAO daouten = new UtenteDAO();
        try {
            user = daouten.getByID(nomeuser);
        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        }

        //set icona finestra
        ImageIcon img = new ImageIcon((getClass().getResource("/res/img/logo-Icon.png")));
        this.setIconImage(img.getImage());

        //Aggiungi barra Menu
        CreaMenu();

        //***** AGGIUNGI LE CARTE/SCHERMATE *********
        HomePanel = new JPanel(new CardLayout());
        cardlayout = new CardLayout();
        HomePanel.setLayout(cardlayout);

        //Aggiungi la carta "DASHBOARD"
        JPanel dashboard = new JPanel();
        HomePanel.add(dashboard, "Dashboard");

        ordini = new OrdiniPanel();
        HomePanel.add(ordini, "Preleva");
        ordini.setComunicator(this);

        //Aggiungi la carta "ANAGRAFICHE"
        anagrafiche = new AnagrafichePanel();
        HomePanel.add(anagrafiche, "Anagrafiche");
        anagrafiche.setComunicator(this);

        // Aggiungi la carta "CATEGORIE"
        categorie = new CategoriePanel();
        HomePanel.add(categorie, "Categorie");
        categorie.setComunicator(this);

        // Aggiungi la carta "PRODOTTO"
        prodotti = new ProdottiPanel();
        HomePanel.add(prodotti, "Prodotti");
        prodotti.setComunicator(this);

        // Aggiungi la carta "CODICI"
        codici = new CodiciPanel();
        HomePanel.add(codici, "Codici");

        //Aggiungi la carta "ORDINIADMIN"
        ordiniadmin = new OrdiniAdminPanel(nomeuser);
        HomePanel.add(ordiniadmin, "Ordini"); //Se è admin allora va sempre con etichetta "ordini"
        ordiniadmin.setComunicator(this);

        //Aggiungi la carta "REPORT"
        JPanel report = new JPanel();
        report.add(new JLabel(" R E P O R T !  ! !"));
        HomePanel.add(report, "Report");

        //Barra Laterale (rimarrà fissa per ogni schermata)
        pannellolaterale = new JPanel();
        JLabel TitleLaterale = new JLabel("       DASHBOARD       "); //Per dare ampiezza al jpanel
        TitleLaterale.setFont(new Font("Arial Black", Font.BOLD, 20));
        pannellolaterale.setLayout(new BoxLayout(pannellolaterale, BoxLayout.Y_AXIS));
        TitleLaterale.setAlignmentX(CENTER_ALIGNMENT);

        pannellolaterale.add(new JLabel("Accesso come " + nomeuser));
        pannellolaterale.add(TitleLaterale);

        pannelloOpzioni = new JPanel();
        pannelloOpzioni.setLayout(new GridLayout(7, 1, 30, 30));
        pannelloOpzioni.add(new ButtonLaterale("Dashboard"));
        pannelloOpzioni.add(new ButtonLaterale("Anagrafiche"));
        pannelloOpzioni.add(new ButtonLaterale("Categorie"));
        pannelloOpzioni.add(new ButtonLaterale("Prodotti"));
        pannelloOpzioni.add(new ButtonLaterale("Codici"));
        pannelloOpzioni.add(new ButtonLaterale("Ordini"));
        pannelloOpzioni.add(new ButtonLaterale("Report"));
        pannelloOpzioni.setAlignmentX(CENTER_ALIGNMENT);
        //pannelloOpzioni.setBackground(new Color( 128, 128, 128));
        pannellolaterale.add(pannelloOpzioni);

        pannellolaterale.setBorder(new EmptyBorder(10, 0, 0, 40)); // per dare un po di margini

        //************ CARD DASHBOARD ****************
        JPanel pannellodash = new JPanel();
        JPanel pannelloTab = new JPanel();

        // i pulsantoni della dashboard
        pannellodash.setBorder(new EmptyBorder(0, 20, 0, 0));
        pannellodash.setLayout(new GridLayout(3, 2, 50, 70));

        //Bottoni Dash
        button = new ButtonDash("Totale prodotti in magazzino");
        button.setBackground(new Color(250, 190, 80));
        pannellodash.add(button);

        button1 = new ButtonDash("Totale prodotti in arrivo");
        button1.setBackground(new Color(250, 190, 80));
        pannellodash.add(button1);

        button2 = new ButtonDash("Spese totali");
        button2.setBackground(new Color(92, 91, 47));
        pannellodash.add(button2);

        button3 = new ButtonDash("Vendite totali");
        button3.setBackground(new Color(92, 91, 47));
        pannellodash.add(button3);

        button4 = new ButtonDash("Totale utenti registrati");
        button4.setBackground(new Color(151, 109, 248));
        pannellodash.add(button4);

        button5 = new ButtonDash("Ordini effettuati");
        button5.setBackground(new Color(236, 50, 213));
        pannellodash.add(button5);

        //Le tabelle ...
        ProdottoDAO daop = new ProdottoDAO();

        JPanel TitoloTab1 = new JPanel(new GridLayout(1, 1));
        TitoloTab1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.red, Color.red), "prodotti in esaurimento", TitledBorder.RIGHT, TitledBorder.TOP));
        table = new JTable();
        table.setEnabled(false);
        model = new DefaultTableModel();
        model.addColumn("Nome");
        model.addColumn("Quantità");
        table.setModel(model);
        JScrollPane sp = new JScrollPane(table);
        TitoloTab1.add(sp);

        JPanel TitoloTab2 = new JPanel(new GridLayout(1, 1));
        TitoloTab2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.red, Color.red), "Prodotti in arrivo", TitledBorder.RIGHT, TitledBorder.TOP));
        table2 = new JTable();
        model2 = new DefaultTableModel();
        model2.addColumn("Nome");
        model2.addColumn("Quantità");
        model2.addColumn("#Ordine");
        model2.addColumn("Data arrivo");

        table2.setEnabled(false);
        table2.setModel(model2);
        JScrollPane sp2 = new JScrollPane(table2);
        TitoloTab2.add(sp2);

        OrdineDAO ordao = new OrdineDAO();

        try {
            for (Prodotto prod : daop.getAll()) {
                if (prod.getQty() <= prod.getQty_min()) {
                    model.addRow(new Object[]{prod.getSku() + "  !" + prod.getNome(), prod.getQty()});

                }

            }

            for (Ordine o : ordao.getAll()) {

                if (ordao.ggConsegnaPR2(o.getN_ordine(), o.getProdotto_sku()) <= 5 && o.getGiorni_alla_consegna() >= 0) {
                    model2.addRow(new Object[]{o.getProdotto_sku(), o.getQty_in_arrivo(), o.getN_ordine(), ordao.dataArrivo(o.getN_ordine(), o.getProdotto_sku())});
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));

        } catch (ParseException ex) {
            Logger.getLogger("genlog").warning("ParseException\n" + StockManagement.printStackTrace(ex));
        }

        pannelloTab.setLayout(new GridLayout(2, 1));
        pannelloTab.add(TitoloTab1);
        pannelloTab.add(TitoloTab2);
        pannelloTab.setBorder(new EmptyBorder(0, 100, 0, 20));

        dashboard.setLayout(new GridLayout(1, 2));
        dashboard.add(pannellodash);
        dashboard.add(pannelloTab);

        add(pannellolaterale, BorderLayout.WEST);
        add(HomePanel, BorderLayout.CENTER);
        //X dar magini verticali...
        add(new JLabel("                           "), BorderLayout.NORTH); //Soluzioni rustiche
        add(new JLabel("                           "), BorderLayout.SOUTH); //Soluzioni rustiche
    }

    public void CreaMenu() {

        JMenuBar menu = new JMenuBar();
        JMenu m1 = new JMenu("Info");
        JMenu m2 = new JMenu("Profilo");

        JMenuItem itemAbout = new JMenuItem("Manuale");
        itemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "\nGUIDA\n-Come creo un fornitore?\n-Come creo una categoria?\n-Come creo un prodotto?");
            }
        });
        m1.add(itemAbout);

        JMenuItem itemAggiorna = new JMenuItem("Riavvio");
        JMenuItem itemChiudi = new JMenuItem("Chiudi");

        itemAggiorna.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "Riavvio programma ...");
                try {
                    riavviaStockManagement();
                } catch (IOException ex) {
                    Logger.getLogger("genlog").warning("IOException\n" + StockManagement.printStackTrace(ex));
                } catch (InterruptedException ex) {
                    Logger.getLogger("genlog").warning("InterruptedException\n" + StockManagement.printStackTrace(ex));
                }

            }
        });

        itemChiudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    chiudiStockManagement();
                } catch (IOException ex) {
                    Logger.getLogger("genlog").warning("IOException\n" + StockManagement.printStackTrace(ex));
                } catch (InterruptedException ex) {
                    Logger.getLogger("genlog").warning("InterruptedException\n" + StockManagement.printStackTrace(ex));
                }

            }
        });

        m2.add(itemAggiorna);
        m2.add(itemChiudi);

        menu.add(m1);
        menu.add(m2);
        setJMenuBar(menu);
    }

    public void VaiAProdotti(String query) {
        //prodotti.casella
        cardlayout.show(HomePanel, "Prodotti");
        prodotti.casella.setText(query);

    }

    public void VaiAProdottiInArrivo() {
        cardlayout.show(HomePanel, "Prodotti");
        prodotti.ViewOnlyInArrivo();

    }
    
    
    public void VaiAPreleva() {
        OrdiniStatus = true;
        cardlayout.show(HomePanel, "Preleva");
    }
   
    

    public void VaiAOrdini() {
        cardlayout.show(HomePanel, "Ordini");
    }
    

    public void VaiAOrdini(String forn) {
        cardlayout.show(HomePanel, "Ordini");
        ordiniadmin.jComboBox.getModel().setSelectedItem(forn);

    }

    public void VaiAOrdiniconProdFORNULL(String forn, String prod) {
        if (ordiniadmin.controlloProdottoUguale(prod.substring(0, prod.indexOf("|")))) {
            JOptionPane.showMessageDialog(null, "non puoi associare più fornitori ad un solo prodotto mentre lo stai aggiungendo al carrello.");
            return;
        }

        cardlayout.show(HomePanel, "Ordini");
        ordiniadmin.jComboBox.getModel().setSelectedItem(forn);
        ((DefaultListModel) ordiniadmin.list.getModel()).addElement(prod);
        ordiniadmin.aggiungiTOcarrello(prod);

    }

    public void VaiAOrdiniconProdFornCEH(String prod) {
        cardlayout.show(HomePanel, "Ordini");
        ordiniadmin.casella.setText(prod);
    }

    public void VaiUtenti() {
        cardlayout.show(HomePanel, "Anagrafiche");
        anagrafiche.ViewOnlyUtenti();
    }

    public void riavviaStockManagement() throws IOException, InterruptedException {

        System.out.println("STO PER RiaVVIARE");
        Connection con = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            System.out.println("La connessione: " + con);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(getParent(), "Non trovo nessuna connesione :(");
            Logger.getLogger(FramePrincipale.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            DriverManagerConnectionPool.releaseConnection(con);

        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning(" " + ex);
        }

        System.out.println("La connessione dopo averla chiusa: " + con);
        dispose();
        StockManagement.main(new String[1]);
    }

    public void chiudiStockManagement() throws IOException, InterruptedException {

        System.out.println("STO PER CHIUDERE");
        Connection con = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            System.out.println("La connessione: " + con);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(getParent(), "Non trovo nessuna connesione :(");
            Logger.getLogger("genlog").warning("SQLException:Non trovo nessuna connesione\n" + StockManagement.printStackTrace(ex));
        }

        try {
            DriverManagerConnectionPool.releaseConnection(con);

        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException" + StockManagement.printStackTrace(ex));
        }

        //  System.out.println("La connessione dopo averla chiusa: " + con);
        StockManagement.closeFH();
        dispose();
//        JavaProcessId.kILL();

    }

    class ButtonLaterale extends JPanel { //BOTTONI NAVIGAZIONE DASHBOARD/ANAGRAFICHE ECC

        public final String tipo;
        public boolean premuto = false; // per button nel pannello laterale
        public Color color_etichetta;
        private JLabel icon;
        private int code;
        private final JPanel pan;

        public ButtonLaterale(String tipo) {
            super();
            this.tipo = tipo;
            super.setBackground(new Color(128, 128, 128));

            super.setLayout(new GridLayout(1, 2));

            if (tipo.equals("Dashboard")) {
                icon = new JLabel(ImpostaImg("/res/img/home.png"));
                color_etichetta = new Color(244, 80, 37);
                code = 0;

            }
            if (tipo.equals("Ordini")) {
                icon = new JLabel(ImpostaImg("/res/img/ordini.png"));
                color_etichetta = new Color(236, 50, 213);
                code = 5;

            }
            if (tipo.equals("Anagrafiche")) {
                icon = new JLabel(ImpostaImg("/res/img/anagrafiche.png"));
                color_etichetta = new Color(151, 109, 248);
                code = 1;
            }

            if (tipo.equals("Categorie")) {
                icon = new JLabel(ImpostaImg("/res/img/categorie.png"));
                color_etichetta = new Color(118, 47, 53);
                code = 2;
            }
            if (tipo.equals("Codici")) {
                icon = new JLabel(ImpostaImg("/res/img/codici.png"));
                color_etichetta = new Color(126, 169, 93);
                code = 4;
            }

            if (tipo.equals("Prodotti")) {
                icon = new JLabel(ImpostaImg("/res/img/prodotti.png"));
                color_etichetta = new Color(250, 190, 80);
                code = 3;
            }

            if (tipo.equals("Report")) {
                icon = new JLabel(ImpostaImg("/res/img/report.png"));
                color_etichetta = new Color(92, 91, 47);
                code = 6;
            }

            JPanel etichetta1 = new JPanel();
            etichetta1.setBackground(color_etichetta);

            pan = new JPanel();
            pan.setBackground(new Color(128, 128, 128));
            icon.setBorder(new EmptyBorder(5, 0, 0, 0));
            icon.setHorizontalAlignment(JLabel.CENTER);
            JLabel testo = new JLabel(tipo);
            testo.setFont(new Font("Arial Black", Font.BOLD, 18));
            testo.setHorizontalAlignment(JLabel.CENTER);
            pan.setLayout(new GridLayout(2, 1));
            pan.add(icon);
            pan.add(testo);

            super.add(etichetta1);
            super.add(pan);

            super.addMouseListener(new MouseListener() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    pan.setBackground(color_etichetta);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    ButtonLaterale bottonepremuto = (ButtonLaterale) e.getSource();
                    bottonepremuto.premuto = true;
                    pan.setBackground(color_etichetta);

                    try {

                        if (user.getPermessi() == 0) {
                            codici.refreshTab();
                            prodotti.refreshTab();
                            categorie.refreshTab();
                            ordiniadmin.refreshTab();
                        }
                        refresh();

                    } catch (SQLException ex) {
                        Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                    }
                    disattivaTuttiIBottoniTranne(bottonepremuto.code);

                    if(tipo.equals("Ordini") && OrdiniStatus) cardlayout.show(HomePanel, "Preleva");
                    else cardlayout.show(HomePanel, tipo);
                    HomePanel.setBorder(BorderFactory.createMatteBorder(0, 20, 0, 0, color_etichetta));
                    pannellolaterale.setBorder(new EmptyBorder(10, 0, 0, 10)); // per dare un po di margini

                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    ButtonLaterale bottonepremuto = (ButtonLaterale) e.getSource();
                    if (!bottonepremuto.premuto) {
                        pan.setBackground(new Color(128, 128, 128));
                    }

                }

            });

        }

        public void disattivaTuttiIBottoniTranne(int cod) {
            for (int i = 0; i < pannelloOpzioni.getComponentCount(); i++) {

                if (i != cod) {
                    ButtonLaterale b = (ButtonLaterale) pannelloOpzioni.getComponent(i);
                    b.pan.setBackground(new Color(128, 128, 128));
                    b.premuto = false;
                }

            }

        }

        public ImageIcon ImpostaImg(String nomeImmag) {

            ImageIcon icon = new ImageIcon(getClass().getResource(nomeImmag));
            Image ImmagineScalata = icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
            icon.setImage(ImmagineScalata);
            return icon;
        }

    }

    public void refresh() {
        ProdottoDAO daop = new ProdottoDAO();

        model.setRowCount(0);
        model2.setRowCount(0);

        OrdineDAO ordao = new OrdineDAO();

        try {
            for (Prodotto prod : daop.getAll()) {
                if (prod.getQty() <= prod.getQty_min()) {
                    model.addRow(new Object[]{prod.getSku() + "  !" + prod.getNome(), prod.getQty()});
                }
            }

            for (Ordine o : ordao.getAll()) {
                if (ordao.ggConsegnaPR2(o.getN_ordine(), o.getProdotto_sku()) <= 5 && o.getGiorni_alla_consegna() >= 0) {
                    model2.addRow(new Object[]{o.getProdotto_sku(), o.getQty_in_arrivo(), o.getN_ordine(), ordao.dataArrivo(o.getN_ordine(), o.getProdotto_sku())});
                }

            }
            //Aggiornare numeri sui pannelli della dash ...
            button.refreshButtonDash();
            button1.refreshButtonDash();
            button2.refreshButtonDash();
            button3.refreshButtonDash();
            button4.refreshButtonDash();
            button5.refreshButtonDash();

        } catch (SQLException ex) {
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
        } catch (ParseException ex) {
            Logger.getLogger("genlog").warning("ParseException\n" + StockManagement.printStackTrace(ex));
        }
    }

    class ButtonDash extends RoundedPanel { //PANNELLO TOTALE PRODOTTI

        private int number;
        public RoundedPanel vai;
        private String type;
        public JLabel title;
        private JLabel num;
        private JLabel scrittaVai;

        public ButtonDash(String type) {
            this.type = type;
            setForeground(new Color(27, 32, 36));
            number = 0;
            super.setLayout(new GridLayout(3, 1));
            super.setBackground(new Color(66, 139, 221));
            title = new JLabel(type); //Per dare ampiezza al jpanel
            title.setFont(new Font("Arial Black", Font.BOLD, 20));
            title.setHorizontalAlignment(JLabel.CENTER);

            vai = new RoundedPanel();
            vai.setFont(new Font("Arial Black", Font.BOLD, 15));
            vai.setBackground(new Color(128, 128, 128));
            vai.setForeground(Color.black);
            vai.setLayout(new GridLayout(1, 1));
            num = new JLabel(); //Per dare ampiezza al jpanel

            refreshButtonDash();

            super.add(title);
            super.add(num);
            super.add(vai);

        }

        public void refreshButtonDash() {
            number = 0;

            if (type.equals("Totale utenti registrati")) {

                UtenteDAO dao = new UtenteDAO();
                try {
                    number = dao.getAll().size();
                } catch (SQLException ex) {
                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                }
                scrittaVai = new JLabel(ImpostaImg("/res/img/users.png"));
                vai.removeAll();

                vai.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        VaiUtenti();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });

                vai.add(scrittaVai);

            }

            if (type.equals("Totale prodotti in magazzino")) {
                ProdottoDAO dao = new ProdottoDAO();
                Enumeration names;
                String key;
                try {
                    names = dao.getCatAndSum().keys();
                    while (names.hasMoreElements()) {
                        key = (String) names.nextElement();
                        number += Integer.parseInt(dao.getCatAndSum().get(key));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                }

                vai.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        VaiAProdotti("");
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });

                scrittaVai = new JLabel(ImpostaImg("/res/img/prodotti.png"));
                vai.removeAll();
                vai.add(scrittaVai);

            }

            if (type.equals("Totale prodotti in arrivo")) {
                number = 0;
                OrdineDAO ordao = new OrdineDAO();
                try {
                    for (Ordine o : ordao.getAll()) {
                        if (o.getGiorni_alla_consegna() >= 0) {
                            number += o.getQty_in_arrivo();
                        }

                    }
                } catch (SQLException ex) {
                    Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
                }
                vai.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        VaiAProdottiInArrivo();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });

                scrittaVai = new JLabel(ImpostaImg("/res/img/prodotti.png"));
                vai.removeAll();
                vai.add(scrittaVai);

            }

            if (type.equals("Ordini effettuati")) {

                Ordine o = new Ordine();
                number = o.leggiUltimoID();

                vai.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e){
                        /*if(OrdiniStatus) VaiAPreleva();  server x manenere vista "Preleva/ordini"
                        else */ 
                       OrdiniStatus = false;
                       VaiAOrdini("Seleziona un fornitore");
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });

                scrittaVai = new JLabel(ImpostaImg("/res/img/ordini.png"));
                vai.removeAll();

                vai.add(scrittaVai);

            }

            num.setText(String.valueOf(number));
            num.setFont(new Font("Arial Black", Font.BOLD, 30));
            num.setForeground(Color.white);
            num.setHorizontalAlignment(JLabel.CENTER);

        }

    }

    public ImageIcon ImpostaImg(String nomeImmag) {

        ImageIcon icon = new ImageIcon(getClass().getResource(nomeImmag));
        Image ImmagineScalata = icon.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        icon.setImage(ImmagineScalata);
        return icon;
    }
}
