/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinedao;

import dao.OrdineDAO;
import beans.Ordine;
import java.sql.SQLException;

/**
 *
 * @author LittleJoke
 */
public class updatenote {
    public static void main(String[] args) throws SQLException {
        OrdineDAO dao = new OrdineDAO();
        
      dao.updateNote("gesu", "admin", "ORD-1", "gatto");

        
    }
    
}
