/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prodottodao;

import java.sql.SQLException;
import dao.ProdottoDAO;
import beans.Prodotto;

/**
 *
 * @author LittleJoke
 */
public class test {

    public static void main(String[] args) throws InterruptedException, SQLException {

        ProdottoDAO dao = new ProdottoDAO();
//         Prodotto p2 = new Prodotto(nome, qty, Categoria, instock, costo, qtymin, note, foto, negozio)
         Prodotto p = new Prodotto("2n4001", 0, "diodo", 0, 1.2f, 0, "note", "foto", 1);
        dao.add(p);
        
        Thread.sleep(4000);
        System.out.println("update");

                ProdottoDAO dao2 = new ProdottoDAO();
                Prodotto pu = dao2.getBySku(p.getSku());
                System.out.println("pu "+pu.toString());
//Prodotto(sku, nome, qty, Categoria, instock, costo, qtymin, note, foto, negozio)                        
                pu.setInstock(1);
                dao.update(pu);
                        Thread.sleep(6000);

                dao.remove(p.getSku());
    }

}
