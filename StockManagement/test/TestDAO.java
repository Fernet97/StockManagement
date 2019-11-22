
import beans.Fornitore;
import dao.FornitoreDAO;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fernet
 */
public class TestDAO {
    
     public static void main(String[] args) {
    
        try {
             FornitoreDAO daofornitore = new FornitoreDAO();
            
            Fornitore f = new Fornitore(1, "xxxxx", "Capra", "Capretti", "Carpisa", "Francia", "367239267111", "capra@gmail.com", "scriviamo una descriziona a caso per capra");     
            daofornitore.add(f);
            //Fornitore mimmo = daofornitore.getByID(1);
            System.out.println("Fornitore: "+f.getNome() +" " + f.getStato());
            
            
            
            //Collection<Fornitore> fornitori = daofornitore.getAll();
            
            //for(Fornitore f : fornitori){
              //   System.out.println(f.getIdfornitore());
            //}
             Fornitore f2 = new Fornitore(1, "yyyyy", "BELBELBELA", "Capretti", "Carpisa", "Italia", "367239267111", "capra@gmail.com", "un altra descrizione");     
          
            daofornitore.update(f2);

            
            
            
            
         
        
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    
    }
    
}
