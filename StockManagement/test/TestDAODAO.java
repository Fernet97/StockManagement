
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

            Prodotto pr2 = new Prodotto("ca1-22/12/2019 23:35:09", "XXXXX", "DIODD", 2, true, 3, 1.4f, "NOTEE", 1, 3, "foto.png");
            dao.update(pr2);

            
            //Fornitore f2 = new Fornitore("FR-1", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb", "bbbbbbb"); 
            
            //dao.update(f2);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(TestDAODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    
    
    
    
    
    }
    
}
