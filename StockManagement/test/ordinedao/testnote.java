/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinedao;
import beans.Ordine;
import dao.OrdineDAO;
import java.sql.SQLException;

/**
 *
 * @author LittleJoke
 */
public class testnote {
    public static void main(String[] args) throws InterruptedException, SQLException {
        Ordine bean = new Ordine();
        Ordine o = new Ordine("ORD-1", "prova note", "admin");
        OrdineDAO dao = new OrdineDAO();
        dao.addNote(o);

        System.out.println("note ordine "+ dao.getNote("ORD-1"));
        System.out.println("id ordine "+ dao.getId("ORD-1"));
        
        
    }
}
