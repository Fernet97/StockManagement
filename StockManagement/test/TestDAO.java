
import beans.Fornitore;
import beans.Prodotto;
import dao.FornitoreDAO;
import dao.ProdottoDAO;
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
            
            
             //FornitoreDAO daofornitore = new FornitoreDAO();
           
             /*
            Fornitore f = new Fornitore(2, "zzzzz", "Accipicchia", "AHAHAH", "Paccc", "Svizzera", "23423423423", "uanm@gmail.com", "descrizione a caso");     
            daofornitore.add(f);
           // Fornitore mimmo = daofornitore.getByID(1);
            System.out.println("Fornitore: "+f.getNome() +" " + f.getStato());
            */
            
           /*Fornitore f2 = new Fornitore(2, "yyyyy", "BELBELBELA", "Capretti", "Carpisa", "Italia", "367239267111", "capra@gmail.com", "un altra descrizione");     
           daofornitore.update(f2);
            */
           
           
           //daofornitore.remove(2);
           
            
           /* Collection<Fornitore> fornitori = daofornitore.getAll();
            
            for(Fornitore f : fornitori){
              System.out.println(f.getIdfornitore());
            }
          

            daofornitore.remove(1);
        
            */
           ProdottoDAO dao = new ProdottoDAO();
           Prodotto pr1 = new Prodotto("1N9099", "DIODO", true, " caso2", 23, 87, 88, 12.4f,  "foto.png");
           dao.add(pr1);
           System.out.println("ultimo index:"+pr1.leggiUltimoSku());

          //System.out.println("Ho trovato: "+ pp.getNome());
            
            
       } catch (SQLException ex) {
            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    
    }
    
}
