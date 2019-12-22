
import beans.Fornitore;
import beans.Prodotto;
import dao.FornitoreDAO;
import dao.ProdottoDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LittleJoke
 */
public class TestUpdateFK {
    
       public static void main(String[] args) throws InterruptedException {
    
       try {
    
            System.out.println("/*update*/");
            java.util.concurrent.TimeUnit.SECONDS.sleep(3);
             System.out.println("/*fornitore*/");
                                                                 System.out.println("/*add");
            FornitoreDAO daofornitore = new FornitoreDAO();                                                             
            Fornitore f = new Fornitore("aaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa");     
            daofornitore.add(f);
                System.out.println("addedf "+f.getIdfornitore());
            java.util.concurrent.TimeUnit.SECONDS.sleep(10);
                java.util.concurrent.TimeUnit.SECONDS.sleep(10);
                                                                 System.out.println("/*Prodotto*/");
 ProdottoDAO dao = new ProdottoDAO();
           Prodotto pr1 = new Prodotto("2..0u", "oggi", "nome", "miaot",  23, true,  87, 12.4f, "desc", 0, 1,  "foto.png");
           dao.add(pr1, f.getIdfornitore());//manca fornitore
                                                        /*update fornitore*/
                                                        
                                                        System.out.println("/*update fornitore*/");
              Fornitore f2 = new Fornitore("bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb"); 
            System.out.println("addedf2 "+f2.getIdfornitore());
              java.util.concurrent.TimeUnit.SECONDS.sleep(10);
           daofornitore.update(f2);
             System.out.println("/*updateok");
            java.util.concurrent.TimeUnit.SECONDS.sleep(10);
          //no remove perche il prodotto dipende dal fornitore, inviare messaggio errore se provi a cancellare forn con prodotti correllati
                      System.out.println("/*updatefinit");
       } catch (SQLException ex) {
            Logger.getLogger(TestUpdateFK.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
}
}
