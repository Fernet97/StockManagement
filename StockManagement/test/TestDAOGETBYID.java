
import beans.Fornitore;
import beans.Prodotto;
import dao.FornitoreDAO;
import dao.ProdottoDAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LittleJoke
 */

// legge il prodotto ed il fornitore in base ad sku ed id ed elimina la relazione
// prodotto e fornitore e di conseguenza elimina il prodotto, poi elimina il fornitore
public class TestDAOGETBYID {
        public static void main(String[] args) throws InterruptedException {
         
       
         try{
  
    
    
                                                    /*getbyid*/
                   System.out.println("/*getbyid*/");
                   java.util.concurrent.TimeUnit.SECONDS.sleep(3);
                   System.out.println("/*fornitore");
                      FornitoreDAO daofornitore1 = new FornitoreDAO();
           Fornitore byID = daofornitore1.getByID("FR-5");//must be changed
                    System.out.println("fornitore "+ byID.getIdfornitore());
                  
                                java.util.concurrent.TimeUnit.SECONDS.sleep(5);
                                
                    System.out.println("/*prodotto");
                    ProdottoDAO dao1 = new ProdottoDAO();
                    Prodotto byIDp =  dao1.getBySku("mi5-17/12/2019 09:22:29");//must be changed
                    System.out.println("prodotto " + byIDp.getSku());
                  
                    java.util.concurrent.TimeUnit.SECONDS.sleep(10);
                      System.out.println("remove prod_h_forn & prodotto");
                       java.util.concurrent.TimeUnit.SECONDS.sleep(5);
                                                                

                     dao1.remove(byIDp, byID);
                     System.out.println("remove fornitore");
                      java.util.concurrent.TimeUnit.SECONDS.sleep(5);
                     daofornitore1.remove(byID);
                      
                      
                              }catch(Exception e ){
         
         }
    }   
}
