
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
 * @author Fernet
 */
public class TestDAODAO {
    
    public static void main(String[] args) throws InterruptedException {
    
    
          
        try {
            
            ProdottoDAO dao = new ProdottoDAO();
            Prodotto pr = new Prodotto("alal", "MINCH", 2, true, 3, 2.4f, "qualche notnia", 1, 3, "foto.png");
            dao.add(pr, "FR-1");
            java.util.concurrent.TimeUnit.SECONDS.sleep(4);

            Prodotto pr2 = new Prodotto("uu", "BIOJ", 2, true, 3, 5.4f, "notm", 1, 3, "foto.png");
            dao.add(pr2, "FR-2");          
            
            /*
            Prodotto pr2 = new Prodotto("MI1-25/12/2019 18:37:24", "FR-1", "iosdofisd", "RRRRR", 2, true, 3, 2.4f, "sds notnia", 1, 3, "foto.png");
            dao.update(pr2, "FR-2"); // "" se non vuoi modificare il fornitore
           
                    
            
            ProdottoDAO dao = new ProdottoDAO();
            for(Prodotto p: dao.getAll()){
                System.out.println(p.getCategoria());
            }*/
            
            
        } catch (SQLException ex) {
            Logger.getLogger(TestDAODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    
    
    
    
    
    }
    
}
