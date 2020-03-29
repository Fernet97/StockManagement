/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Prodotto;
import com.sun.glass.events.KeyEvent;
import dao.ProdottoDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.BoxLayout;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
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
class CategoriePanel extends JPanel {
      private final DefaultTableModel model;
      public static Object[] nuovaRiga;
      public ArrayList<String> list_cat_new;
      private FramePrincipale frameprinc;
      public ArrayList<String> list_tot;
    
    
    
    public CategoriePanel(){
        list_cat_new = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        JLabel title = new JLabel("CATEGORIE");
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
        
        panSopra.add(new JLabel(" ")); 
        
        JButton buttonNew = new JButton("ADD NEW");
        //*************+* BOTTONE AGGIUNGI NUOVA RIGA**************************
        buttonNew.addActionListener(new ActionListener() {
                        
            @Override
            public void actionPerformed(ActionEvent e) {       
                AddCategoriaDialog input = new AddCategoriaDialog();
                input.setVisible(true);
                String txt = input.getName();
                try {
                    refreshTab();
                } catch (SQLException ex) {
                    Logger.getLogger(CategoriePanel.class.getName()).log(Level.SEVERE, null, ex);
                }



            }
        });
        buttonNew.setFont(new Font("Arial Black", Font.BOLD, 15));
        panSopra.add(buttonNew);
                

        super.add(panSopra);
       
        //Tabella
        JPanel TitoloTab1 = new JPanel();
        TitoloTab1.setLayout(new GridLayout(1,1));
        TitoloTab1.setBorder (new EmptyBorder(0, 100, 20, 100));

          String[] columnNames = { "Nome", "Quantità","Query"};
          Object[][] data = {  };
            
           
           
           model = new DefaultTableModel(data, columnNames)
              {
                private static final long serialVersionUID = 1L;

                public boolean isCellEditable(int row, int column)
                {
                  return column >= 2; //il numero di celle editabili...
                }
              };
             JTable table = new JTable(model);
             table.getTableHeader().setReorderingAllowed(false);

          
             try {
              refreshTab();
          } catch (SQLException ex) {
              Logger.getLogger(CategoriePanel.class.getName()).log(Level.SEVERE, null, ex);
          }
             
             
                
             table.setRowHeight(40); //altezza celle
             
             //X colonne che hanno pulsanti
             table.getColumnModel().getColumn(2).setCellRenderer(new ClientsTableButtonRenderer());
             table.getColumnModel().getColumn(2).setCellEditor(new ClientsTableRenderer(new JCheckBox()));             

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
    
    public void  setComunicator(FramePrincipale princ){
        frameprinc  = princ;
        
    }
    
    
    
    	public ImageIcon ImpostaImg(String nomeImmag) {

                ImageIcon icon = new ImageIcon(nomeImmag);
		Image ImmagineScalata = icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		icon.setImage(ImmagineScalata);
                return icon;
	}	




        public void  refreshTab() throws SQLException{
          
  
            //Cancello vecchie righe...
            model.setRowCount(0);
            
            
            
            
            ProdottoDAO dao = new ProdottoDAO();
            
            Enumeration names;
            String key;       
            names = dao.getCatAndSum().keys();
        
     
        while(names.hasMoreElements()) {
           key = (String) names.nextElement(); //Nome categoria del db
           if(list_cat_new.contains(key)) list_cat_new.remove(key); // ELIMINO DUPLICATI IN LIST CAT_NEW
           
            model.addRow(new Object[]{key, dao.getCatAndSum().get(key), "Vai a prodotti"});
        }
        

                    
            // Aggiorno con le nuove
            for(String catDinamica: list_cat_new){
        
                model.addRow(new Object[]{catDinamica,  "DA DEFINIRE", "Vai a prodotti"} ); 
     
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
        setForeground(Color.white);
       
      setText("Vai a prodotti");
      setIcon(ImpostaImg("prodotti.png"));
      

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
              setForeground(Color.white);

      button.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {         
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
      button.setIcon(ImpostaImg("prodotti.png"));
      clicked = true;
      return button;
    }
    public Object getCellEditorValue()
    {
      if (clicked)  // SE CLICCATO QUEL BOTTONE:::::::::::::
      {
              if(button.getText().equals("Vai a prodotti")) {
                    frameprinc.VaiAProdotti(table.getValueAt(row, 0).toString());
                   //PRENDO RIFERIMENTO DI HOME PANEL E CHIAMO IL SUO METODO PER CAMBIARE VERSO LA SCHERMATA PRODOTTI
              
              
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

  
  
public class AddCategoriaDialog extends JDialog {

    private JTextField name;

    public AddCategoriaDialog() {
        super(new JFrame("Aggiungi Categoria"), "Aggiungi Categoria");
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setMinimumSize(new Dimension(500, 100));
        this.name = new JTextField();
        name.addKeyListener(new KeyAdapter() {
                    public void keyPressed(java.awt.event.KeyEvent e) {
                      if (e.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
                           confermaCategoria();
                      }
                    }
                  });

        JButton add = new JButton("Conferma nuova categoria");
        add.setForeground(Color.white);
        add.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                confermaCategoria();
            }
        });
                     
                
        this.setLayout(new GridLayout(2, 1, 5, 5));

        this.add(name);
        this.add(add);
        this.pack();
        this.setResizable(false);
    }

    private void close(){   this.dispose(); }

    public String getName(){    return this.name.getText().toUpperCase(); }
    
    public void confermaCategoria(){
   
                    if(name.getText().length() < 2) {
                    JOptionPane.showMessageDialog(getParent(),"La categoria deve avere almeno 2 lettere!");
                    close();
                }
                else{
                    Logger.getLogger("userlog").info("Hai aggiunto la categoria"+ name.getText());
                    model.addRow(new Object[]{name.getText().toUpperCase(),  "DA DEFINIRE", "Vai a prodotti"} ); 
                    list_cat_new.add(name.getText().toUpperCase());                
                    frameprinc.prodotti.list_cat_new.add(name.getText().toUpperCase());                
                    close(); 
                }
    }
}  

    
    
    
}
