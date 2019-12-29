
import dao.UtenteDAO;
import beans.Utente;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LittleJoke
 */
public class testDAOUser {
     public static void main(String[] args) throws InterruptedException, SQLException {
         System.out.println("utente");
                               System.out.println("/*add*/");
           java.util.concurrent.TimeUnit.SECONDS.sleep(3);
          // UtenteDAO  dao = new UtenteDAO();
           //Utente u = new Utente ("1","1","1","1","1","1", 1 ,"1");
           //dao.add(u);
                   UtenteDAO  dao2 = new UtenteDAO();
                   
      Utente u2 = new Utente (dao2.getByID("ut-1")+ "fullname", "CF"," indirizzo", "telefono", "email", "pwd", 0, "note");
               dao2.update(u2);
                   }
    
}
