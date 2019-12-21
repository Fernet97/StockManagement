
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
 * @author Fernet
 */
public class TestDAORemove {
    
      public static void main(String[] args) throws InterruptedException {
         
       
         try{
          FornitoreDAO daofornitore = new FornitoreDAO();
          Fornitore f = new Fornitore("aaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa", "aaaaaaa");     
          daofornitore.add(f);
    
         ProdottoDAO dao = new ProdottoDAO();
         Prodotto pr1 = new Prodotto("2..0u", "oggi", "nome", "miaot",  23, true,  87, 12.4f, "desc", 0, 1,  "foto.png");
         dao.add(pr1, f.getIdfornitore());
             System.out.println("wait10s");
                     java.util.concurrent.TimeUnit.SECONDS.sleep(10);
                        
         
         dao.remove(pr1, f);

         }catch(Exception e ){
         
         }
    }   
    
    
    
    
    
}
