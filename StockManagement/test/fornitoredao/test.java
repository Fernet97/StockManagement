/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fornitoredao;

import java.sql.SQLException;
import dao.FornitoreDAO;
import beans.Fornitore;

/**
 *
 * @author LittleJoke
 */
public class test {

    public static void main(String[] args) throws InterruptedException, SQLException {

       FornitoreDAO dao = new FornitoreDAO();
       //Fornitore(String idfornitore, String datareg, String fullname, String p_iva, String indirizzo, String tel, String email, String note)
       Fornitore f = new Fornitore( "fullmane", "piva", "indirizzo", "tel", "email", "note");
        dao.add(f);
        
       
    }

}
