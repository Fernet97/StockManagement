/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dao.OrdineDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Fernet
 */

class FrameRiepPreleva extends JDialog{

    private DefaultTableModel model2;
    private final JTable table2;
    private final JTextArea casNote;
    private final JScrollPane sp3;
     
    public FrameRiepPreleva(OrdiniPanel pan, String numordine, String dataordine, String costoTot){

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        addWindowListener( new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent we) {
                        int OpzioneScelta = JOptionPane.showConfirmDialog(getParent(), "Sei sicuro di uscire ed annullare le modifiche?", "", JOptionPane.YES_NO_OPTION);
                        if (OpzioneScelta == JOptionPane.OK_OPTION) {
                            dispose();
                         }
                        
                    }
                } );


        ImageIcon img = new ImageIcon((getClass().getResource("/res/img/logo-Icon.png")));
        this.setIconImage(img.getImage());
        
                String[] columnNames = { "SKU", "Nome Prodotto", "Costo", "Quantita' prelevata"};

        Object[][] data = {};

        model2 = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        
      table2 = new JTable(model2){
         public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component comp = super.prepareRenderer(renderer, row, column);
            if(column >=5) return comp;

            Color alternateColor = new Color(24, 53, 90);
            Color whiteColor = new Color(10, 25, 43);
            if(!comp.getBackground().equals(getSelectionBackground())) {
               Color c = (row % 2 == 0 ? alternateColor : whiteColor);
               comp.setBackground(c);
               c = null;
            }
            return comp;
         }
      };;
      
       table2.getTableHeader().setReorderingAllowed(false);
            JScrollPane sp2 = new JScrollPane(table2);
        sp2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(24, 53, 90), new Color(24, 53, 90)), "#ORDINE: PRE-1 del 28/07/2020 @admin", TitledBorder.CENTER, TitledBorder.TOP));
        
        add(sp2, BorderLayout.CENTER);
      
        
                // VISIBILE SOLO SE PREMO BUTTON NOTE
                
       JPanel leftpan = new JPanel();
                
        casNote = new JTextArea();
        casNote.setAlignmentX(LEFT_ALIGNMENT);
        casNote.setLineWrap(true);
        casNote.setWrapStyleWord(true);
        casNote.setRows(5);
        casNote.setColumns(20);        
        casNote.setText("");
        sp3 = new JScrollPane(casNote);
        
        
        JButton bannulla = new JButton("Annulla");
        bannulla.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                  int OpzioneScelta = JOptionPane.showConfirmDialog(getParent(), "Sei sicuro di uscire ed annullare le modifiche?");

                if (OpzioneScelta == JOptionPane.OK_OPTION) {

                    dispose();
            }
            }

        });
        
        JButton comment = new JButton("Salva");
        comment.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            }

        });
        
       //leftpan.setBackground(Color.pink);
       leftpan.setLayout(new BoxLayout(leftpan, BoxLayout.Y_AXIS));
       leftpan.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED,new Color(24, 53, 90), new Color(24, 53, 90)), "Note", TitledBorder.CENTER, TitledBorder.TOP));
       
        leftpan.add(sp3);
        JPanel commenpan = new JPanel();
        commenpan.setLayout(new BoxLayout(commenpan, BoxLayout.X_AXIS));
        commenpan.add(comment);
        leftpan.add(commenpan);
        
        add(leftpan, BorderLayout.EAST);


               
        
        
    }
    
}
