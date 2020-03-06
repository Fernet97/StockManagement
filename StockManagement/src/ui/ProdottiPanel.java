/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Fornitore;
import beans.Prodotto;
import dao.FornitoreDAO;
import dao.ProdottoDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Fernet
 */
public class ProdottiPanel extends JPanel{
  
    /*
    private final DefaultTableModel model;
    public static Object[] nuovaRiga;
    public final JTextField casella;
    public FormProdotti form;
    public ArrayList<String> list_cat_new;
    private final JTable table;


    
    
    public ProdottiPanel(){
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
                for(String cat: list_cat_new){
                    System.out.print(cat+ " ");
                }//debug
                
                form = new FormProdotti("ADD", null);
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
        TitoloTab1.setLayout(new GridLayout(1,1));
        TitoloTab1.setBorder (new EmptyBorder(0, 100, 20, 100));

          String[] columnNames = { "sku", "Data reg.", "Nome", "Categoria","Quantità","Fornitore","In Stock?", "giorni alla consegna", "Costo","Note","Quantità in arrivo", "Quantità minima", "Modifica","Cancella" };
          //Object[][] data = { { "XXXXX", "10/12/2019", "YYYYYY", "XXXXXXX","34", "Amazon", "Sì", "3", "23$","una descrizione a caso","33", "13", "Modifica","Cancella"   } };
          Object[][] data = {};  
   
           
           
           model = new DefaultTableModel(data, columnNames)
              {
                private static final long serialVersionUID = 1L;

                public boolean isCellEditable(int row, int column)
                {
                  return column >= 12; //il numero di celle editabili...
                }
              };
             table = new JTable(model);
             table.getTableHeader().setReorderingAllowed(false);


        try {
            refreshTab(); // Aggiorna tavola con  i fornitori del db;
        } catch (SQLException ex) {
            Logger.getLogger(AnagrafichePanel.class.getName()).log(Level.SEVERE, null, ex);
        }             
             
             
             
             table.setRowHeight(40); //altezza celle
             
             //X colonne che hanno pulsanti
             table.getColumnModel().getColumn(4).setCellRenderer(new CustomRender());
            
             table.getColumnModel().getColumn(6).setCellRenderer(new CustomStockRender());                     
             
             table.getColumnModel().getColumn(12).setCellRenderer(new ClientsTableButtonRenderer());
             table.getColumnModel().getColumn(12).setCellEditor(new ClientsTableRenderer(new JCheckBox()));

             table.getColumnModel().getColumn(13).setCellRenderer(new ClientsTableButtonRenderer());
             table.getColumnModel().getColumn(13).setCellEditor(new ClientsTableRenderer(new JCheckBox()));             

            JScrollPane sp = new JScrollPane(table); 
            TitoloTab1.add(sp);  
        
        
        
        //******* funzione di ricerca *******************+
              TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
             table.setRowSorter(rowSorter);
        
         casella.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void changedUpdate(DocumentEvent arg0) {}

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
        
        
        
        


        
        public void  refreshTab() throws SQLException{
            
            
            //Cancello vecchie righe...
            System.out.println("Numero di  record prima dell'aggiornamento  "+model.getRowCount());
            model.setRowCount(0);
            
            ProdottoDAO dao = new ProdottoDAO();
            
            // Aggiorno con le nuove
            for(Prodotto pro: dao.getAll()){
               System.out.println(pro.getSku() +" "+ pro.getDatareg()+" "+ pro.getNome()+" "+pro.getCategoria()+" "+ pro.getQty()+" "+pro.getId_fornitore()+" "+pro.isInstock()+" "+ pro.getGiorni_alla_consegna()+" "+pro.getCosto()+" "+ pro.getDescrizione()+" "+pro.getQty_inarrivo()+" "+ pro.getQty_min());
               model.addRow(new Object[]{ pro.getSku(), pro.getDatareg(), pro.getNome(), pro.getCategoria(), pro.getQty(), pro.getId_fornitore(), pro.isInstock(), pro.getGiorni_alla_consegna(), pro.getCosto(), pro.getDescrizione(), pro.getQty_inarrivo(), pro.getQty_min(),"Modifica","Cancella"});
               
            
            }
            System.out.println("Numero di  record prima dell'aggiornamento  "+model.getRowCount());
      

        
        }

  
 // RENDER DELLE QUANTITA'
 class CustomRender  extends JButton implements TableCellRenderer {

        public CustomRender() {
        
        
        
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
           if(Integer.parseInt(table.getValueAt(row, 4).toString()) <= Integer.parseInt(table.getValueAt(row, 11).toString())){
           
                setBackground(new Color(244, 80, 37));    // ROSSO        
           }
           
           else setBackground(new Color(126, 169, 93));  // VERDE
           
            
           
           setText(table.getValueAt(row, 4).toString());
            
        
            return this;
        }
 }
 
         
 // RENDER DI IN STOCK SI O NO       
 class CustomStockRender  extends JButton implements TableCellRenderer {

        public CustomStockRender() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
           
           if(Boolean.parseBoolean(table.getValueAt(row, 6).toString())){
                setBackground(new Color(126, 169, 93));  // VERDE          
                       
           }
           
           else setBackground(new Color(244, 80, 37));    // ROSSO 
           
            
           
           setText("");
            
        
            return this;
        }        
    }




  class ClientsTableButtonRenderer extends JButton implements TableCellRenderer
  {
    public ClientsTableButtonRenderer()
    {
      setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
      setText((value == null) ? "" : value.toString());
      if(getText().equals("Modifica"))setIcon(ImpostaImg("/res/img/pencil.png"));
      else if(getText().equals("Cancella")) setIcon(ImpostaImg("/res/img/eraser.png"));
      

      return this;
    }
    
  
    
  }
  public class ClientsTableRenderer extends DefaultCellEditor
  {
    private JButton button;
    private String label;
    private boolean clicked;
    private int row, col;
    private JTable table;

    public ClientsTableRenderer(JCheckBox checkBox)
    {
      super(checkBox);
      button = new JButton();    // ECCO IL BOTTONE
      button.setOpaque(true);
      button.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {         
          System.out.println("APRI FORMMMMm");
          fireEditingStopped();
        }
      });
    }
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
      this.table = table;
      this.row = row;
      this.col = column;

   
      button.setForeground(Color.black);
      button.setBackground(UIManager.getColor("Button.background"));
      
      label = (value == null) ? "" : value.toString();
      button.setText(label);
      if(button.getText().equals("Modifica"))  button.setIcon(ImpostaImg("/res/img/pencil.png"));
      else if(button.getText().equals("Cancella"))button.setIcon(ImpostaImg("/res/img/eraser.png"));
      clicked = true;
      return button;
    }
    
    public Object getCellEditorValue()
    {
      if (clicked)  // SE CLICCATO QUEL BOTTONE:::::::::::::
      {
               //Se Ho premuto Cancella ....
              if(button.getText().equals("Cancella")) {
                int OpzioneScelta = JOptionPane.showConfirmDialog(getComponent(),"Sicuro di voler cancellare la riga:\n [  "+
                        table.getValueAt(row, 0)+"  |   "+
                        table.getValueAt(row, 1)+"  |   "+
                        table.getValueAt(row, 2)+"  |   "+
                        table.getValueAt(row, 3)+"  |   "+
                        table.getValueAt(row, 4)+"  |   "+
                        table.getValueAt(row, 5)+"  |   "+
                        table.getValueAt(row, 6)+"  |   "+
                        table.getValueAt(row, 7)+"  |   "+
                        table.getValueAt(row, 8)+"  |   "+
                        table.getValueAt(row, 9)+"  |   "+
                        table.getValueAt(row, 10)+"  |   "+                    
                        table.getValueAt(row, 11)+"  ]");  
                
                if (OpzioneScelta == JOptionPane.OK_OPTION) {
                        System.out.println("OOOOOOOOKKKKKK CANCELLO");  
                        ProdottoDAO daor = new ProdottoDAO();
                    try {
                        daor.remove(table.getValueAt(row, 0).toString(), table.getValueAt(row, 5).toString());
                        
                        refreshTab();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProdottiPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 }
              }
              else if(button.getText().equals("Modifica")) { // APRI FORM PER MODIFICARE RECORD
              
                    form = new FormProdotti("UPDATE", table.getValueAt(row, 0).toString());
                    form.setResizable(false);
                    form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    form.setVisible(true);
              
              }
              
              
      }
      clicked = false;
      return new String(label);
    }

    public boolean stopCellEditing()
    {
      clicked = false;
      return super.stopCellEditing();
    }

    protected void fireEditingStopped()
    {
      super.fireEditingStopped();
    }
  }

  
// ****************** LA FORM ***********************
class FormProdotti extends javax.swing.JFrame {
    
    
    public String modalita;
    public String IdSelezionato;
    private String FornitoreCorrente;

    /**
     * Creates new form FormProdotti
     */
    
    /*
    public FormProdotti() {
        try {
            initComponents();
        } catch (SQLException ex) {
            Logger.getLogger(ProdottiPanel.class.getName()).log(Level.SEVERE, null, ex);
        }        
        ImageIcon img = new ImageIcon(getClass().getResource("/res/img/logo-Icon.png"));
        this.setIconImage(img.getImage());        
    }
    
    private FormProdotti(String mod, String idSelected) {
        modalita = mod;
        IdSelezionato = idSelected;
        
        try {        
            initComponents();
        } catch (SQLException ex) {
            Logger.getLogger(ProdottiPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(modalita.equals("UPDATE")) {
            System.out.println("Sono in modalità update ...");
            System.out.println(" Id selezionato: " + idSelected);
            FornitoreCorrente = setFormAsID(idSelected);
        }
        ImageIcon img = new ImageIcon("logo-Icon.png");
        this.setIconImage(img.getImage());
            
        }    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    /*
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() throws SQLException {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        label1 = new java.awt.Label();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jLabel9 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        label1.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        label1.setForeground(new java.awt.Color(204, 0, 0));
        label1.setText("FORM PRODOTTO");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("SKU");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("Data reg.");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setText("Nome");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("Giorni alla consegna");

        jTextField1.setText("AutoGenerato");
        jTextField1.setEnabled(false);        
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        
        jTextField3.setText("AutoGenerato");
        jTextField3.setEnabled(false); 
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setText("Quantità");

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setText("Quantità Minima");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setText("Categoria");

        jList1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        ProdottoDAO dao = new ProdottoDAO();
        // Cat dal db + Cat dinamiche aggiunte prima in java
        String[] list_prod = new String[dao.getAll().size()+ list_cat_new.size()+1];
        for(int y =0; y<list_cat_new.size(); y++){ //Aggiungo le categorie aggiunte prima dinamicamente..
            list_prod[y] = list_cat_new.get(y);
            System.out.println("size:" + list_cat_new.get(y));        
        }        
             
        Iterator<Prodotto> iter = dao.getAll().iterator();
        int i =0;
        int sum = list_cat_new.size();
        while(iter.hasNext()){ //Aggiungo prima le categorie estrapolate dal db..
            list_prod[sum+i] = iter.next().getCategoria();
            i++;
        }
        
                
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
             // Elimina i possibili duplicati nella lista risultante...
            String[] stringsP = Arrays.stream(list_prod).distinct().toArray(String[]:: new);;
            public int getSize() { return stringsP.length; }
            public String getElementAt(int i) { return stringsP[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setText("Costo");

        jList2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        FornitoreDAO daof = new FornitoreDAO();
        String[] list_forn = new String[daof.getAll().size()];
        Iterator<Fornitore> iter2 = daof.getAll().iterator();
        int j =0;
        while(iter2.hasNext()){
            list_forn[j] = iter2.next().getIdfornitore();
            j++;
        }     
        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] stringsF = list_forn;
            public int getSize() { return stringsF.length; }
            public String getElementAt(int j) { return stringsF[j]; }
        });
        
         
        jList2.addListSelectionListener(new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent e)
                {
                    
          // JPOP MENU          
        /*
                JPopupMenu pop = new JPopupMenu();
                pop.add(new JMenuItem(jList2.getSelectedValue()));
                pop.show(getParent(), 30, 30);
             */                    
               
    /*
                }
              });
              
        jScrollPane2.setViewportView(jList2);
        

        
        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setText("Fornitore");

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jLabel11.setText("Note");

        jCheckBox1.setText("In Stock");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setText("Quantità in arrivo");

        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jButton3.setText("Annulla");
        jButton3.setForeground(Color.BLACK);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("CONFERMA");
        jButton4.setForeground(Color.BLACK);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField6))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(jTextField7)))
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel10)
                                .addComponent(jLabel5))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(112, 112, 112)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(356, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(355, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        form.setVisible(false);

    }                                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if(check()){
            //Aggiungi riga
            if(modalita.equals("UPDATE")) getOggettoforFormUpdate();
            else getOggettoforFormSave();
        }
      
    } 
     
    
    
    private boolean check() {
            if(jTextField2.getText().isEmpty() || jList1.isSelectionEmpty() || jList2.isSelectionEmpty() || jTextField6.getText().isEmpty() ||jTextField5.getText().isEmpty() || jTextField7.getText().isEmpty() || jTextField9.getText().isEmpty() || jTextField9.getText().isEmpty() || jTextField4.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"Compila tutti i campi! ['Note' è opzionale]");
                return false;}
            
            
              try{ //Controlla se sono interi...
                    Integer.parseInt(jTextField6.getText());
                    Integer.parseInt(jTextField5.getText()); 
                    Integer.parseInt(jTextField9.getText()); 
                    Integer.parseInt(jTextField4.getText());        
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(this,"Controlla che \"Quantità\", \"Gioni alla consegna\", \"qty minima\", \"qty in arrivo\", siano numeri validi. [ Per il costo usare \".\" per indicare la parte decimale ]");
                    return false;
                }
              

              
                //controlla se sono float ...
                try{
                    Float.parseFloat(jTextField7.getText());
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(this,"Controlla che le quantità ed il costo siano numeri validi! [Per il costo usare '.' per indicare la parte decimale]");
                    return false;
                }
            

        return true;
    }
            
    
    public void getOggettoforFormSave(){
        
            Prodotto prod = new Prodotto(jTextField2.getText() , jList1.getSelectedValue(),Integer.parseInt(jTextField6.getText()),jCheckBox1.isSelected(),Integer.parseInt(jTextField5.getText()), Float.valueOf(jTextField7.getText()), jTextField8.getText(), Integer.parseInt(jTextField9.getText()), Integer.parseInt(jTextField4.getText()),"foto.png");
            ProdottoDAO dao = new ProdottoDAO();
            
       
            try {
                int a= JOptionPane.showConfirmDialog(this,"Dario, sei proprio sicuro?");
                 if(a==JOptionPane.YES_OPTION){
                    dao.add(prod, jList2.getSelectedValue()); 
                    form.setVisible(false);

                  }
            
        } catch (SQLException ex) {
            Logger.getLogger(AnagrafichePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        try {
            refreshTab();
        } catch (SQLException ex) {
            Logger.getLogger(AnagrafichePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
            
         
    
    }    
    
     
    private String setFormAsID(String idSelected) {
            
            ProdottoDAO dao = new ProdottoDAO();
    
        try {
            Prodotto prodotto = dao.getBySku(idSelected);
            jTextField1.setText(prodotto.getSku());
            jTextField2.setText(prodotto.getNome());
            jTextField3.setText(prodotto.getDatareg());
            jTextField4.setText(Integer.toString(prodotto.getQty_min()));
            jTextField5.setText(Integer.toString(prodotto.getGiorni_alla_consegna()));
            jTextField6.setText(Integer.toString(prodotto.getQty()));
            jTextField7.setText(String.valueOf(prodotto.getCosto()));
            jTextField8.setText(prodotto.getDescrizione());
            jTextField9.setText(Integer.toString(prodotto.getQty_inarrivo()));
            jCheckBox1.setSelected(prodotto.isInstock());
            jList1.setSelectedValue(prodotto.getCategoria(), true);
            jList2.setSelectedValue(prodotto.getId_fornitore(), true);
                       
        } catch (SQLException ex) {
            Logger.getLogger(AnagrafichePanel.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
        return jList2.getSelectedValue();
        
    }
    
    
   public void getOggettoforFormUpdate(){
       
            Prodotto prod = new Prodotto(jTextField1.getText(), FornitoreCorrente, jTextField2.getText() , jList1.getSelectedValue(),Integer.parseInt(jTextField6.getText()),jCheckBox1.isSelected(),Integer.parseInt(jTextField5.getText()), Float.valueOf(jTextField7.getText()), jTextField8.getText(), Integer.parseInt(jTextField9.getText()), Integer.parseInt(jTextField4.getText()),"foto.png");
            ProdottoDAO dao = new ProdottoDAO();       
        try {            
           int a= JOptionPane.showConfirmDialog(this,"Dario, sei proprio sicuro?");
           if(a==JOptionPane.YES_OPTION){
            dao.update(prod, jList2.getSelectedValue());               
             form.setVisible(false);
             System.out.println("La form:"+ form);

           }            
            
                       
        } catch (SQLException ex) {
            Logger.getLogger(AnagrafichePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            refreshTab();
        } catch (SQLException ex) {
            Logger.getLogger(AnagrafichePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
        
    
    
    
    
    

    // Variables declaration - do not modify                     
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private java.awt.Label label1;
    // End of variables declaration                   
    }
    
*/
    
    
}
