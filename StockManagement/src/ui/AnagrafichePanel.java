/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Fornitore;
import dao.FornitoreDAO;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Fernet
 */
public class AnagrafichePanel extends JPanel {

    private final DefaultTableModel model;
    public static Object[] nuovaRiga;
    public Formanagrafiche form;
    

    
    
    public AnagrafichePanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        JLabel title = new JLabel("ANAGRAFICHE");
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
        
        JPanel panelcheck = new JPanel();
        JCheckBox r1=new JCheckBox("Fornitori");    
        JCheckBox r2=new JCheckBox("Utenti");  
        JCheckBox r3=new JCheckBox("Clienti");  
        panelcheck.add(r1); panelcheck.add(r2); panelcheck.add(r3);
        panSopra.add(panelcheck); 
        
        JButton buttonNew = new JButton("ADD NEW");
        //*************+* BOTTONE AGGIUNGI NUOVA RIGA**************************
        buttonNew.addActionListener(new ActionListener() {
                        
            @Override
            public void actionPerformed(ActionEvent e) {  
                Object[] options = {"Cliente", "Fornitore", "Utente"};
                
                int OpzioneScelta = JOptionPane.showOptionDialog(null,"Seleziona una tipologia", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
                if(OpzioneScelta!= JOptionPane.CLOSED_OPTION){
                    form = new Formanagrafiche(OpzioneScelta, "ADD", null);
                    form.setResizable(false);
                    form.setVisible(true);
                    form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
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

          String[] columnNames = { "ID", "Data reg.", "Fullname", "P.IVA/CF","Indirizzo", "Tel/Fax", "email", "Note","Modifica","Cancella", "Ordina"};
          //Object[][] data = { { "1", "10/12/2019", "Gianfranco Colil", "XXXXXXX","Via campo san giovanni, ITa", "398737892", "cacio@gmail.com", "una descrizione","Modifica","Cancella"} };
          Object[][] data = {};  
          
           
           
           model = new DefaultTableModel(data, columnNames)
              {
                private static final long serialVersionUID = 1L;

                public boolean isCellEditable(int row, int column)
                {
                  return column >= 8; //il numero di celle editabili...
                }
              };
             JTable table = new JTable(model);
             table.getTableHeader().setReorderingAllowed(false);

             
        try {
            refreshTab(); // Aggiorna tavola con  i fornitori del db;
        } catch (SQLException ex) {
            Logger.getLogger(AnagrafichePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
             
             
             table.setRowHeight(40); //altezza celle
             
             //X colonne che hanno pulsanti
             table.getColumnModel().getColumn(8).setCellRenderer(new ClientsTableButtonRenderer());
             table.getColumnModel().getColumn(8).setCellEditor(new ClientsTableRenderer(new JCheckBox()));

             table.getColumnModel().getColumn(9).setCellRenderer(new ClientsTableButtonRenderer());
             table.getColumnModel().getColumn(9).setCellEditor(new ClientsTableRenderer(new JCheckBox()));     
             
             table.getColumnModel().getColumn(10).setCellRenderer(new ClientsTableButtonRenderer());
             table.getColumnModel().getColumn(10).setCellEditor(new ClientsTableRenderer(new JCheckBox()));    

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
            
            FornitoreDAO dao = new FornitoreDAO();
            
            // Aggiorno con le nuove
            for(Fornitore forn: dao.getAll()){
                
               model.addRow(new Object[]{ forn.getIdfornitore(), forn.getDatareg(), forn.getFullname(), forn.getP_iva(),forn.getIndirizzo(), forn.getTel(), forn.getEmail(), forn.getNote(),"Modifica","Cancella", "Ordina"});

            
            }
            System.out.println("Numero di  record prima dell'aggiornamento  "+model.getRowCount());

        
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
      else if(getText().equals("Ordina")) setIcon(ImpostaImg("/res/img/ordini.png"));
      

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
      else if(button.getText().equals("Ordini"))button.setIcon(ImpostaImg("/res/img/ordini.png"));
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
                        table.getValueAt(row, 7)+"  ]");  
                
                if (OpzioneScelta == JOptionPane.OK_OPTION) {
                        System.out.println("OOOOOOOOKKKKKK CANCELLO");  
                        FornitoreDAO dao = new FornitoreDAO();
                        try{
                        dao.remove(table.getValueAt(row, 0).toString());
                        refreshTab();
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(getComponent(),"Se vuoi cancellare un fornitore, devi prima cancellare o modificare le dipendenze con i prodotti relativi a quest'ultimo!");                            
                            e.printStackTrace();
                            
                        }
                 }
              }
              else if(button.getText().equals("Modifica")) { // APRI FORM PER MODIFICARE RECORD
                    Object[] options = {"Cliente", "Fornitore", "Utente"};
                    int OpzioneScelta = JOptionPane.showOptionDialog(null,"Seleziona una tipologia", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
                    form = new Formanagrafiche(OpzioneScelta,"UPDATE", table.getValueAt(row, 0).toString());
                    form.setResizable(false);
                    form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    form.setVisible(true);              
              }
              
             else if(button.getText().equals("Ordina")) { // APRI FORM PER MODIFICARE RECORD
                   JOptionPane.showMessageDialog(getComponent(), "Vai in ORDINI");
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fernet
 */
     public static class Formanagrafiche extends JFrame{

         public int tipologia;
         public String modalita;
         public String IdSelezionato;
        private final ImageIcon img;
    
         
        public Formanagrafiche(int OpzioneScelta, String mod, String idSelected) {
            
            
            System.out.println("Scelta:" +OpzioneScelta);
            tipologia = OpzioneScelta;
            modalita = mod;
            IdSelezionato = idSelected;
            initComponents();        
            
            if(modalita.equals("UPDATE")) {
                System.out.println("Sono in modalità update ...");
                System.out.println(" Id selezionato: " + idSelected);
                //setFormAsID(idSelected);
            }
            img = new ImageIcon (getClass().getResource("/res/img/logo-Icon.png"));
            setSize(900, 300);
            this.setIconImage(img.getImage());
            

        
        }       

        private void initComponents() {
                
            // Cliente
            if(tipologia  == 0){
                
                JPanel pancliente = new JPanel();
                pancliente.setLayout(new BoxLayout(pancliente, BoxLayout.Y_AXIS)); 
                JLabel l = new JLabel("Cliente");
                l.setFont(new Font("Arial Black", Font.BOLD, 20));
                l.setAlignmentX(CENTER_ALIGNMENT);
                pancliente.add(l);
                
                              
                JPanel main = new JPanel();
                main.setLayout(new GridLayout(3, 3, 20, 10));
       
                JPanel pid = new JPanel();
                JLabel lid = new JLabel("    ID ");
                JTextField casid = new JTextField(15);
                casid.setAlignmentX(RIGHT_ALIGNMENT);
                pid.add(lid); pid.add(casid);
                main.add(pid);
                
                JPanel pdatareg = new JPanel();
                JLabel ldatareg = new JLabel("Data reg");
                JTextField casdatareg = new JTextField(15);
                casdatareg.setAlignmentX(RIGHT_ALIGNMENT);
                pdatareg.add(ldatareg); pdatareg.add(casdatareg);            
                main.add(pdatareg);
                
                JPanel pfullname = new JPanel();
                JLabel lfullname = new JLabel("Fullname");
                JTextField casfullname = new JTextField(15);
                casfullname.setAlignmentX(RIGHT_ALIGNMENT);
                pfullname.add(lfullname); pfullname.add(casfullname);
                main.add(pfullname);
  
                JPanel piva = new JPanel();
                JLabel liva = new JLabel("p.IVA");
                JTextField casiva = new JTextField(15);
                casiva.setAlignmentX(RIGHT_ALIGNMENT);
                piva.add(liva); piva.add(casiva);
                main.add(piva);  
                
                JPanel pindirizzo = new JPanel();
                JLabel lindirizzo = new JLabel("Indirizzo");
                JTextField casindirizzo = new JTextField(15);
                casindirizzo.setAlignmentX(RIGHT_ALIGNMENT);
                pindirizzo.add(lindirizzo); pindirizzo.add(casindirizzo);
                main.add(pindirizzo);                  
              
                JPanel ptel = new JPanel();
                JLabel ltel = new JLabel("          Tel.");
                JTextField castel = new JTextField(15);
                castel.setAlignmentX(RIGHT_ALIGNMENT);
                ptel.add(ltel); ptel.add(castel);
                main.add(ptel);                  
    
                main.add(new JLabel(""));  // PER DARE SPAZIO
                main.add(new JLabel("")); // PER DARE SPAZIO

                JPanel pemail = new JPanel();
                JLabel lemail = new JLabel("      Email");
                JTextField casemail = new JTextField(15);
                casemail.setAlignmentX(RIGHT_ALIGNMENT);
                pemail.add(lemail); pemail.add(casemail);
                main.add(pemail);
                
                pancliente.add(main);
                
                JPanel pandown = new JPanel();  
                JLabel notext = new JLabel("Note");           
                JTextArea note = new JTextArea("");
                note.setAlignmentX(LEFT_ALIGNMENT);
                note.setLineWrap (true);
                note.setRows(5);
                note.setColumns(20);
                pandown.add(notext);
                JScrollPane scrollPane = new JScrollPane(note);
                pandown.add(scrollPane);
                
                pandown.add(new JLabel("                        "));
                
                JButton salva = new JButton("Conferma");
                JButton annulla = new JButton("Annulla");
   
                pandown.add(salva);
                pandown.add(annulla);
                
                pancliente.add(pandown);    
        
                     
                add(pancliente);                 
            
            }
            
             // Fornitore
            if(tipologia  == 1){

  
            }
            
             // Utente
            if(tipologia  == 2){


            }          
            
            
            
        }
    }

  
  
 /*
  private boolean check() {
            if(jTextField3.getText().isEmpty() || jTextField4.getText().isEmpty() || jTextField5.getText().isEmpty() || jTextField6.getText().isEmpty() || jTextField9.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"Riempi tutti i campi! ['Note' è opzionale]");
                if(jTextField6.getText().length() >=15){
                                JOptionPane.showMessageDialog(this,"Numero di telefono troppo lungo");

                    return false;
                }
                return false;
            }
            
            return true;       
        }

    
    
   
    public void getOggettoforFormSave(){
    
            
            Fornitore forn = new Fornitore(jTextField3.getText() , jTextField4.getText()  , jTextField5.getText(), jTextField6.getText() , jTextField9.getText(), jTextArea1.getText());
            FornitoreDAO dao = new FornitoreDAO();
       
            try {
                int a= JOptionPane.showConfirmDialog(this,"Dario, sei proprio sicuro?");
                if(a==JOptionPane.YES_OPTION){
                    dao.add(forn);            
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
    
    
    
    
      private void setFormAsID(String idSelected) {
            
            FornitoreDAO dao = new FornitoreDAO();
            
            
        try {
            Fornitore fornitore = dao.getByID(idSelected);
            
            
            jTextField1.setText(fornitore.getIdfornitore());
            jTextField2.setText(fornitore.getDatareg());
            jTextField3.setText(fornitore.getFullname());
            jTextField4.setText(fornitore.getP_iva());
            jTextField5.setText(fornitore.getIndirizzo());
            jTextField6.setText(fornitore.getTel());
            jTextField9.setText(fornitore.getEmail());
            jTextArea1.setText(fornitore.getNote());
                  
                  
            
        } catch (SQLException ex) {
            Logger.getLogger(AnagrafichePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
            
            

        }
    
   public void getOggettoforFormUpdate(){
       Fornitore forn = new Fornitore(jTextField1.getText(), " ",jTextField3.getText() , jTextField4.getText()  , jTextField5.getText(), jTextField6.getText() , jTextField9.getText(), jTextArea1.getText());      
       FornitoreDAO dao = new FornitoreDAO();
        try {    
            
           int a= JOptionPane.showConfirmDialog(this,"Dario, sei proprio sicuro?");
           if(a==JOptionPane.YES_OPTION){
             dao.update(forn);
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
    

   

*/   
    }






