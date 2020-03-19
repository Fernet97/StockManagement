/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prodottodao;

import java.sql.SQLException;
import dao.ProdottoDAO;
import beans.Prodotto;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author LittleJoke
 */
public class test {

    public static void main(String[] args) throws InterruptedException, SQLException {
                        ProdottoDAO daop = new ProdottoDAO();
 Prodotto p = new Prodotto("1aaa", 1, "dican", true, 111.0f, 2, "note", "foto", true);
            daop.add(p);
            System.out.println("add ok wait for update");
//            Thread.sleep(5000);
            
//             ProdottoDAO daopu = new ProdottoDAO();
//            
//             Prodotto p2 = daopu.getBySku(p.getSku());
//             
//             p2.setNegozio(false);
//             p2.setInstock(false);
//             daopu.update(p2);
                     
                     
    }
}
