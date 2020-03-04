package userdao;


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
           UtenteDAO  dao = new UtenteDAO();
//                                Utente(fullname, CF, indirizzo, telefono, email, pwd, permessi, note)
           Utente u = new Utente ("flabio caino ","cf","via della capocchia","333","mail","pwd", 1 ,"note");
          
                   
           dao.add(u);
           System.out.println("pswd md5 "+ u.getPwd());
           java.util.concurrent.TimeUnit.SECONDS.sleep(3);
           System.out.println("//*update*//");
                   UtenteDAO  dao2 = new UtenteDAO();
                   java.util.concurrent.TimeUnit.SECONDS.sleep(10);
      Utente u2 = dao2.getByID(u.getIdutente());
      u2.setTelefono("miiiiaaaao");
               dao2.update(u2);
                   }
    
}
