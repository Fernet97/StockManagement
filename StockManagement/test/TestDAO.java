
import beans.Fornitore;
import beans.Prodotto;
import beans.Utente;
import dao.FornitoreDAO;
import dao.ProdottoDAO;
//import dao.UtenteDAO;
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
            
                                        /*getall*/
          /* 
             FornitoreDAO daofornitore = new FornitoreDAO();
            Collection<Fornitore> fornitori = daofornitore.getAll();
            
            for(Fornitore f : fornitori){
              System.out.println("testdaoprint"+f.getIdfornitore());
            }
            
            ProdottoDAO dao = new ProdottoDAO();
            Collection<Prodotto> prodotti = dao.getAll();
            for (Prodotto p: prodotti){
                System.out.println("test dao prodotti "+ p.getSku());
            }
     */
          
          
          
          
          
          
          
          
           
                                                                 /*add, update e remove*/
         
 ProdottoDAO dao = new ProdottoDAO();
           Prodotto pr1 = new Prodotto("2..0u", "oggi", "nome", "miaot",  23, true,  87, 12.4f, "desc", 0, 1,  "foto.png");
           dao.add(pr1);
         System.out.println(pr1.getSku());
            java.util.concurrent.TimeUnit.SECONDS.sleep(10);
           Prodotto pr2 = new Prodotto("2", "miao", "aaaaa", "cazzzzzaaataa",  47, false,  87, 12.4f, "desc", 0, 1,  "foto.png");
  System.out.println(pr2.getSku());
            java.util.concurrent.TimeUnit.SECONDS.sleep(10);
  dao.update(pr2);
                              java.util.concurrent.TimeUnit.SECONDS.sleep(10);
           dao.remove(pr1);

              FornitoreDAO daofornitore = new FornitoreDAO();
           
             
            Fornitore f = new Fornitore("aaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa");     
            daofornitore.add(f);
            System.out.println(f.getIdfornitore());
            java.util.concurrent.TimeUnit.SECONDS.sleep(10);
            Fornitore f2 = new Fornitore("bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb");     
              java.util.concurrent.TimeUnit.SECONDS.sleep(10);
           daofornitore.update(f2);
             java.util.concurrent.TimeUnit.SECONDS.sleep(10);
           daofornitore.remove(f);
            
                
               
                   
                   
                                                            
        
        
        
                                                                /*getbyid*/
                   
                   /*    FornitoreDAO daofornitore = new FornitoreDAO();
           Fornitore byID = daofornitore.getByID("asd");
                    System.out.println("fornitore "+ byID.getIdfornitore());
                  
                    
                    ProdottoDAO dao = new ProdottoDAO();
                    Prodotto byIDp =  dao.getBySku("a");
                    System.out.println("prodotto " + byIDp.getSku());
           */
                   
                   
                   
                   
       } catch (SQLException ex) {
            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    
    }
    
}
