package prelevadao;

import beans.Preleva;
import dao.PrelevaDAO;
import java.sql.SQLException;

/**
 *
 * @author LittleJoke
 */
public class testnotepreleva {

    public static void main(String[] args) throws InterruptedException, SQLException {
        Preleva bean = new Preleva();
        Preleva o = new Preleva("PRE-8", "MIIIAAAOOO", "admin");
        PrelevaDAO dao = new PrelevaDAO();
        dao.addNote(o);

        System.out.println("note ordine " + dao.getNote("PRE-8"));
        System.out.println("id ordine " + dao.getId("PRE-8"));

        System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
        Thread.sleep(10000);
        PrelevaDAO dao2 = new PrelevaDAO();
        dao2.updateNote("gesu", "admin", "PRE-8");
        System.out.println("note ordine " + dao.getNote("PRE-8"));
        System.out.println("id ordine " + dao.getId("PRE-8"));
        
        System.out.println("eliminazione tra 3 sec");
        Thread.sleep(3000);
        dao.removeNote("PRE-8");

    }
}
    
    

