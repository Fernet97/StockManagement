
import beans.Fornitore;
import beans.Prodotto;
import beans.Utente;
import dao.FornitoreDAO;
import dao.ProdottoDAO;
import dao.UtenteDAO;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
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
    
     public static void main(String[] args) throws InterruptedException {
    
       try {
            
            
             FornitoreDAO daofornitore = new FornitoreDAO();
           
             
            Fornitore f = new Fornitore("aaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa");     
            daofornitore.add(f);
            java.util.concurrent.TimeUnit.SECONDS.sleep(10);

            
            
           Fornitore f2 = new Fornitore( f.getIdfornitore(), "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb");     
           daofornitore.update(f2);
            
           
           
           //daofornitore.remove(2);
           
            
           /* Collection<Fornitore> fornitori = daofornitore.getAll();
            
            for(Fornitore f : fornitori){
              System.out.println(f.getIdfornitore());
            }
          

            daofornitore.remove(1);
        
          
           ProdottoDAO dao = new ProdottoDAO();
           Prodotto pr1 = new Prodotto("1N999sku", "oggi", "nome", "cat",  23, true,  87, 12.4f, "desc", 0, 1,  "foto.png");
           dao.add(pr1);
           //System.out.println("ultimo index:"+pr1.leggiUltimoSku());
/*
          //System.out.println("Ho trovato: "+ pp.getNome());
            
           
           UtenteDAO user = new UtenteDAO();
           Collection<Utente> uno = user.getAll();
           for (Utente u : uno) {
               System.out.println(u.getId()+ u.getFullname());
           }
          
           */
        //   UtenteDAO dao = new UtenteDAO();
          // Utente u = new Utente ("2", "test2", "test2", "test2", "test2", "test2", "test...", true, true, true, true, true);
           //dao.add(u);
      //     System.out.println("add ok");
          // Utente u2 = new Utente("2", "test3", "test3", "test3", "test3", "test3", "testadicazzo", true, true, true, true, true);
           //dao.update(u2);
           // System.out.println(u);*/
           
 ProdottoDAO dao = new ProdottoDAO();
           Prodotto pr1 = new Prodotto("2..0u", "oggi", "nome", "miaot",  23, true,  87, 12.4f, "desc", 0, 1,  "foto.png");
           dao.add(pr1);
           //System.out.println("ultimo index:"+pr1.leggiUltimoSku());
           Prodotto pr2 = new Prodotto("2", "miao", "aaaaa", "cazzzzzaaataa",  47, false,  87, 12.4f, "desc", 0, 1,  "foto.png");

           dao.update(pr2);

           dao.remove(pr1.getSku());
          //System.out.println("Ho trovato: "+ pp.getNome());
           
       } catch (SQLException ex) {
            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    
    }
    
}
