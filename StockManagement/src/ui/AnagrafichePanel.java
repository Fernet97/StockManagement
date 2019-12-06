/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.BorderLayout;
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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Fernet
 */
public class AnagrafichePanel extends JPanel {
    
    
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
        JButton iconCerca = new JButton(new ImageIcon("search.png"));
        cerca.add(searchlabel);
        cerca.add(casella);
        cerca.add(iconCerca);
        panSopra.add(cerca);
        
        panSopra.add(new JLabel(" ")); 
        
        JButton buttonNew = new JButton("ADD NEW");
        buttonNew.setFont(new Font("Arial Black", Font.BOLD, 15));
        panSopra.add(buttonNew);
                

        super.add(panSopra);
       
        //Tabella
        JPanel TitoloTab1 = new JPanel();
        TitoloTab1.setLayout(new GridLayout(1,1));
        TitoloTab1.setBorder (new EmptyBorder(0, 100, 20, 100));
        String[] columns = new String[] {"ID", "p_iva","Nome", "Cognome","Azienda", "Stato", "Tel","email", "Descr.", "MANAGE"};

        Object[][] data = new Object[][] {{"1", "XXXX", "Giancarlo", "Prappo", "Bula bula corp.", "Francia", "3872987262","franco@gmail.com", "Una descrizione",""},
        {"1", "XXXX", "Giancarlo", "Prappo", "Bula bula corp.", "Francia", "3872987262","franco@gmail.com", "Una descrizione",""},
        {"1", "XXXX", "Giancarlo", "Prappo", "Bula bula corp.", "Francia", "3872987262","franco@gmail.com", "Una descrizione",""},
        {"1", "XXXX", "Giancarlo", "Prappo", "Bula bula corp.", "Francia", "3872987262","franco@gmail.com", "Una descrizione",""},
        {"1", "XXXX", "Giancarlo", "Prappo", "Bula bula corp.", "Francia", "3872987262","franco@gmail.com", "Una descrizione",""},
        {"1", "XXXX", "Giancarlo", "Prappo", "Bula bula corp.", "Francia", "3872987262","franco@gmail.com", "Una descrizione",""},
        {"1", "XXXX", "Giancarlo", "Prappo", "Bula bula corp.", "Francia", "3872987262","franco@gmail.com", "Una descrizione",""}};       
        JTable table = new JTable(data, columns);
        
        table.getColumnModel().getColumn(9).setCellRenderer(new JPanelRender());;
        //table.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JTextField()));       
          
        

        JScrollPane sp = new JScrollPane(table); 
        TitoloTab1.add(sp);  
       
        super.add(TitoloTab1);
  
    
    }
    
    
    	public ImageIcon ImpostaImg(String nomeImmag) {

                ImageIcon icon = new ImageIcon(nomeImmag);
		Image ImmagineScalata = icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		icon.setImage(ImmagineScalata);
                return icon;
	}	
    
        
 
        
        
    
// IL JPanel con i bottoni Add e Update
class JPanelRender extends JPanel implements  TableCellRenderer
{

  //CONSTRUCTOR
  public JPanelRender() {
    setOpaque(true);
    setBackground(Color.red);
    setLayout(new GridLayout(1, 2));
    JPanel panUpdate = new JPanel();
    panUpdate.setBackground(Color.cyan);
    panUpdate.add(new JLabel(ImpostaImg("pencil.png")));
    JPanel panRemove = new JPanel();
    panRemove.setBackground(Color.cyan);
    panRemove.add(new JLabel(ImpostaImg("eraser.png")));    
    add(panUpdate);
    add(panRemove);     
  }
  
  @Override
  public Component getTableCellRendererComponent(JTable table, Object obj,
      boolean selected, boolean focused, int row, int col) {

    //SET PASSED OBJECT AS BUTTON TEXT
      //setText((obj==null) ? "":obj.toString());
      //System.out.println(obj.toString());
      //if(obj.toString().equals("MODIFICA")){

     

    return this;
  }
  
     public ImageIcon ImpostaImg(String nomeImmag) {

        ImageIcon icon = new ImageIcon(nomeImmag);
        Image ImmagineScalata = icon.getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT);
        icon.setImage(ImmagineScalata);
        return icon;
	}	

}

}
/*

//BUTTON EDITOR CLASS
class ButtonEditor extends DefaultCellEditor
{
  protected JButton btn;
   private String lbl;
   private Boolean clicked;

   public ButtonEditor(JTextField txt) {
    super(txt);

    btn=new JButton();
    btn.setOpaque(true);

    //WHEN BUTTON IS CLICKED
    btn.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        fireEditingStopped();
      }


    });
  }

   
   /*
   //OVERRIDE A COUPLE OF METHODS
   @Override
  public Component getTableCellEditorComponent(JTable table, Object obj,
      boolean selected, int row, int col) {

      //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
     lbl=(obj==null) ? "":obj.toString();
     btn.setText(lbl);
     clicked=true;
    return btn;
  }

  //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
   @Override
  public Object getCellEditorValue() {

     if(clicked)
      {
      //SHOW US SOME MESSAGE
        JOptionPane.showMessageDialog(btn, lbl+" Cliccato");
      }
    //SET IT TO FALSE NOW THAT ITS CLICKED
    clicked=false;
    return new String(lbl);
  }

   @Override
  public boolean stopCellEditing() {

         //SET CLICKED TO FALSE FIRST
      clicked=false;
    return super.stopCellEditing();
  }

   @Override
  protected void fireEditingStopped() {
    // TODO Auto-generated method stub
    super.fireEditingStopped();
  }
}*/



