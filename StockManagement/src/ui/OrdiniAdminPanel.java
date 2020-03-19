/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Prodotto;
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
import java.sql.SQLException;
import static java.util.Collections.list;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import others.RoundedPanel;

/**
 *
 * @author Fernet
 */
public class OrdiniAdminPanel  extends JPanel{
    
    
    public Prodotto prodottoCorrente;
    private javax.swing.JComboBox<String> jComboBox;
    private DefaultTableModel model;
    private JTable table;
    private final DefaultTableModel model2;
    private final JTable table2;

    

        
    public OrdiniAdminPanel(){
    
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        JLabel title = new JLabel("ORDINI ADMIN");
        title.setFont(new Font("Arial Black", Font.BOLD, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.add(title);  

        
        //Pannello centrale
        JPanel princ = new JPanel();
        princ.setLayout(new GridLayout(2, 2, 20, 20));
        
        JPanel sxpan = new JPanel();
        sxpan.setLayout(new BoxLayout(sxpan, BoxLayout.PAGE_AXIS));
        princ.add(sxpan); 
        sxpan.setBorder(BorderFactory.createLineBorder(new Color( 27, 32, 36), 50));

        JPanel orizontalprod = new JPanel();        
        JLabel prodtext = new JLabel("Sku Prodotto");
        prodtext.setFont(new Font("Arial Black", Font.BOLD, 20));
        orizontalprod.add(prodtext);
        
        JTextField casella = new JTextField();
        casella.setColumns(30);
        orizontalprod.add(casella);
        sxpan.add(orizontalprod);
        
        jComboBox = new JComboBox<>();
        jComboBox.setFont(new Font("Arial Black", Font.BOLD, 30));
        jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Amazon", "Ebay", "AIiExpress" }));
        jComboBox.setForeground(Color.black);
        jComboBox.setBackground(Color.DARK_GRAY);
        jComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            }
        });
        sxpan.add(jComboBox);
        
        
        JList list = new JList(new DefaultListModel<>());
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(10, 11, 227, 239);
        for (int i = 0; i < 30; i++) ((DefaultListModel) list.getModel()).addElement("Prodotto"+String.valueOf(i));
        sxpan.add(scrollPane);

        
        
        
        
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
        table.setEnabled(false);
        model = new DefaultTableModel();
        model.addColumn("SKU");
        model.addColumn("quantità da ordinare");
        model.addColumn("Costo unitario");
        model.addColumn("Giorni all'arrivo");
        model.addColumn("Fornitore");
        table.setModel(model);
        JScrollPane sp = new JScrollPane(table); 
        for(int i = 0; i < 40; i++)
             model.addRow(new Object[]{"XXXXX","8","12 euro", "Arrivo tra 7 giorni", "Amazon" });  
        
        info.add(sp);
        info.add(new JButton("Rimuovi prodotto selezionato"));

        infolabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dxpan.add(infolabel);
        dxpan.add(info);
         
        princ.add(dxpan);

        /*** tabella ordin**********/
        JPanel SXdown = new JPanel();
        SXdown.setLayout(new BoxLayout(SXdown, BoxLayout.PAGE_AXIS));    
        
        
        String[] columnNames = { "# Ordine", "Data ordine", "# prodotti ordinati", "Costo Totale ordine","In spedizione", "Controlla ordine", "Ricarica ordine"};

   
        Object[][] data = {};
           
       model2 = new DefaultTableModel(data, columnNames)
        {
            private static final long serialVersionUID = 1L;
            
            public boolean isCellEditable(int row, int column)
            {
                return column >= 5; //il numero di celle editabili...
            }
        };
        table2 = new JTable(model2); 
        table2.getTableHeader().setReorderingAllowed(false);
        model2.addRow(data); // DA CANCELLARE
        refreshTab(); // Aggiorna tavola con  i fornitori del db;

        table2.getColumnModel().getColumn(4).setCellRenderer(new CustomStockRender());                     
        
        table2.getColumnModel().getColumn(5).setCellRenderer(new TableButtonRenderer());
        table2.getColumnModel().getColumn(5).setCellEditor(new TableRenderer(new JCheckBox()));             

        table2.getColumnModel().getColumn(6).setCellRenderer(new TableButtonRenderer());
        table2.getColumnModel().getColumn(6).setCellEditor(new TableRenderer(new JCheckBox()));

        
        JScrollPane sp2 = new JScrollPane(table2); 
        sp2.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (EtchedBorder.RAISED, Color.red, Color.red),"Riepilogo ordini effettuati", TitledBorder.CENTER,TitledBorder.TOP));
        SXdown.add(sp2);
        SXdown.add(new JButton("Cancella ordine selezionato"));
        princ.add(SXdown);
        
        
        JPanel DXdown = new JPanel(); 
        DXdown.setLayout(new BoxLayout(DXdown, BoxLayout.PAGE_AXIS));
        JLabel riepilogo = new JLabel("Riepilogo ordine");
        riepilogo.setFont(new Font("Arial Black", Font.BOLD, 30));
        riepilogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        DXdown.add(riepilogo); 

        JLabel numordine = new JLabel("#Ordine: N23");
        numordine.setFont(new Font("Arial Black", Font.BOLD, 15));
        numordine.setAlignmentX(Component.CENTER_ALIGNMENT);
        DXdown.add(numordine);

        JLabel tot = new JLabel("Costo totale: 431.31$");
        tot.setFont(new Font("Arial Black", Font.BOLD, 15)); 
        tot.setAlignmentX(Component.CENTER_ALIGNMENT);
        DXdown.add(tot);

        JButton conferma = new JButton("   Effettua ordine    ");
        conferma.setFont(new Font("Arial Black", Font.ITALIC, 40));
        conferma.setMinimumSize(new Dimension(100, 50));
        conferma.setAlignmentX(Component.CENTER_ALIGNMENT);
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

    private void refreshTab() {
       model2.setRowCount(0);
       model2.addRow(new Object[]{"XXXXX","XXXXX", "XXXXX", "154$","true", "view", "" });  
       model2.addRow(new Object[]{"XXXXX","XXXXX", "XXXXX", "154$","true","view", "" });  
       model2.addRow(new Object[]{"XXXXX","XXXXX", "XXXXX", "154$","true", "view", "" });  
       model2.addRow(new Object[]{"XXXXX","XXXXX", "XXXXX", "154$","true", "view", "" });  
       model2.addRow(new Object[]{"XXXXX","XXXXX", "XXXXX", "154$","true", "view", "" });  
       model2.addRow(new Object[]{"XXXXX","XXXXX", "XXXXX", "154$","true","view", "" });  
       model2.addRow(new Object[]{"XXXXX","XXXXX", "XXXXX", "154$","true","view", "" });  
       model2.addRow(new Object[]{"XXXXX","XXXXX", "XXXXX", "154$","true","view", "" });  
              
    }

    
 
 class TableButtonRenderer extends JButton implements TableCellRenderer
  {
    public TableButtonRenderer()
    {
      setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
      setText((value == null) ? "" : value.toString());
      if(getText().equals("view"))setIcon(ImpostaImg("/res/img/ordini.png"));
      else if(getText().equals("")) setIcon(ImpostaImg("/res/img/categorie.png"));
      

      return this;
    }
    
  
    
  }
  public class TableRenderer extends DefaultCellEditor
  {
    private JButton button;
    private String label;
    private boolean clicked;
    private int row, col;
    private JTable table;

    public TableRenderer(JCheckBox checkBox)
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
      if(button.getText().equals("view"))button.setIcon(ImpostaImg("/res/img/ordini.png"));
      else if(button.getText().equals("")) button.setIcon(ImpostaImg("/res/img/prodotti.png"));
      
      clicked = true;
      return button;
    }
    
    public Object getCellEditorValue()
    {
      if (clicked)  // SE CLICCATO QUEL BOTTONE:::::::::::::
      {
             if(button.getText().equals("Riepilogo ordine")) {
              
                   JOptionPane.showMessageDialog(getComponent(), "Vai Riepilogo ordini");

              
              }
              else if(button.getText().equals("Lista prodotti")) {
                   JOptionPane.showMessageDialog(getComponent(), "Vai Lista prodotti");
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
    
    
    
    
 class CustomStockRender  extends JButton implements TableCellRenderer {

        public CustomStockRender() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
           
           if(Boolean.parseBoolean(table2.getValueAt(row, 4).toString())){
                setBackground(new Color(126, 169, 93));  // VERDE          
                       
           }
           
           else setBackground(new Color(244, 80, 37));    // ROSSO 
           
            
           
           setText("");
            
        
            return this;
        }        
    }    
    
    
}


