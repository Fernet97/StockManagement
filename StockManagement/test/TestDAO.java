
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

//********************************WARNING*****************************************************/
//LA REMOVE NON FUNZIONA IN TestDAO A CAUSA delle foreign key nella tabella "prodotti_has_fornitori"
//PER TESTARE QUESTA FUNZIONE USARE IL FILE TestDAORemove.java
//********************************************************************************************//
public class TestDAO {
    
     public static void main(String[] args) throws InterruptedException {
    
       try {
           
                      System.out.println("/*add, update*/");
           java.util.concurrent.TimeUnit.SECONDS.sleep(3);
                                                                 /*add, update e remove*/
                       System.out.println("/*fornitore*/");
                                                                 System.out.println("/*add");
            FornitoreDAO daofornitore = new FornitoreDAO();                                                             
            Fornitore f = new Fornitore("aaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa");     
            daofornitore.add(f);
            System.out.println("addedf "+f.getIdfornitore());
            java.util.concurrent.TimeUnit.SECONDS.sleep(10);
            Fornitore f2 = new Fornitore("bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb"); 
            System.out.println("addedf2 "+f2.getIdfornitore());
              java.util.concurrent.TimeUnit.SECONDS.sleep(10);
           daofornitore.update(f2);
                      System.out.println("/*updateok");
            // java.util.concurrent.TimeUnit.SECONDS.sleep(10);
        //   daofornitore.remove(f);
                     // System.out.println("/*remove ok");
                       java.util.concurrent.TimeUnit.SECONDS.sleep(10);
                                                                 System.out.println("/*Prodotto*/");
 ProdottoDAO dao = new ProdottoDAO();
           Prodotto pr1 = new Prodotto("2..0u", "oggi", "nome", "miaot",  23, true,  87, 12.4f, "desc", 0, 1,  "foto.png");
           dao.add(pr1, f2.getIdfornitore());//manca fornitore
         System.out.println("added pr1"+pr1.getSku());
            java.util.concurrent.TimeUnit.SECONDS.sleep(10);
           Prodotto pr2 = new Prodotto("2", "miao", "aaaaa", "cazzzzzaaataa",  47, false,  87, 12.4f, "desc", 0, 1,  "foto.png");
  System.out.println("added pr2 "+pr2.getSku());
            java.util.concurrent.TimeUnit.SECONDS.sleep(10);
  dao.update(pr2);
           System.out.println("/*updateok");
                              java.util.concurrent.TimeUnit.SECONDS.sleep(10);
       //    dao.remove(pr1,  f);
           //System.out.println("/*remove ok");
           System.out.println("");
            System.out.println("");
            
                            java.util.concurrent.TimeUnit.SECONDS.sleep(3);

               
                   
                   
                                                            
        
        
        
                                                                /*getbyid*/
                   System.out.println("/*getbyid*/");
                   java.util.concurrent.TimeUnit.SECONDS.sleep(3);
                   System.out.println("/*fornitore");
                      FornitoreDAO daofornitore1 = new FornitoreDAO();
           Fornitore byID = daofornitore1.getByID("bbbbbbb");//must be changed
                    System.out.println("fornitore "+ byID.getIdfornitore());
                  
                                java.util.concurrent.TimeUnit.SECONDS.sleep(5);
                                
                    System.out.println("/*prodotto");
                    ProdottoDAO dao1 = new ProdottoDAO();
                    Prodotto byIDp =  dao1.getBySku("ca2-13/12/2019 13:16:32");//must be changed
                    System.out.println("prodotto " + byIDp.getSku());
                  
                    java.util.concurrent.TimeUnit.SECONDS.sleep(10);
                                          System.out.println("");
                      System.out.println("");
                    
                                                            /*getall*/
           
                                                               System.out.println("/*getall*/");
                   java.util.concurrent.TimeUnit.SECONDS.sleep(3);
                                         System.out.println("/*fornitore");
             FornitoreDAO daofornitore2 = new FornitoreDAO();
            Collection<Fornitore> fornitori = daofornitore2.getAll();
            
            for(Fornitore getall : fornitori){
              System.out.println("testdaoprint "+getall.getIdfornitore());
            }
            
            java.util.concurrent.TimeUnit.SECONDS.sleep(5);
                                  System.out.println("/*prodotto");
                      
            ProdottoDAO dao2 = new ProdottoDAO();
            Collection<Prodotto> prodotti = dao2.getAll();
            for (Prodotto p: prodotti){
                System.out.println("test dao prodotti "+ p.getSku());
            }
     
           java.util.concurrent.TimeUnit.SECONDS.sleep(10);
                                 System.out.println("");
                      System.out.println("");
           System.out.println("/*finit*/");
                   
                   
                   
                   
       } catch (SQLException ex) {
            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    
    }
    
}
