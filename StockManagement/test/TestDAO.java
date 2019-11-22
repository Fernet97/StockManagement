
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
            
            //Fornitore f = new Fornitore(8, "xxxxx", "Capra", "Capretti", "Carpisa", "Francia", "367239267111", "capra@gmail.com", "scriviamo una descriziona a caso per capra");     
            //daofornitore.add(f);
            //Fornitore mimmo = daofornitore.getByID(1);
            //System.out.println("Fornitore: "+mimmo.getNome() +" " + mimmo.getStato());
            
            
            
            //Collection<Fornitore> fornitori = daofornitore.getAll();
            
            //for(Fornitore f : fornitori){
              //   System.out.println(f.getIdfornitore());
            //}
            
            Fornitore f = new Fornitore(7, "yyyyyy", "Capra", "Capretti", "Carpisa", "Francia", "367239267111", "capra@gmail.com", "scriviamo");     
            daofornitore.update(f);

            
            
            
            
         
            
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    
    }
    
}
