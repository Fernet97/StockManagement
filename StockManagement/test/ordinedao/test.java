/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinedao;

import java.sql.SQLException;
import dao.OrdineDAO;
import beans.Ordine;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author LittleJoke
 */
public class test {

    public static void main(String[] args) throws InterruptedException, SQLException {
            Ordine bean = new Ordine();
            OrdineDAO dao =new OrdineDAO();
            bean.startOrdine();

            
        
            
//            Ordine o = new Ordine(qty in arrivo, gg alla cons, fk_utente, prodotto_sku, cliente, fk_fornitore)
            Ordine o = new Ordine(0, 0, "admin","di18-16/03/2020 16:07:16", 0, "FR-1");
            Ordine o2 = new Ordine(0, 0, "admin","op15-16/03/2020 16:07:16", 0, "FR-1");
            Ordine o3 = new Ordine(0, 0, "admin","re4-16/03/2020 16:07:16", 0, "FR-1");
           
        dao.add(o);
        dao.add(o2);
        dao.add(o3);
            
            
            
    }
}