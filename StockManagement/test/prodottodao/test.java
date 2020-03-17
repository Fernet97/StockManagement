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
 Prodotto p = new Prodotto("1n5088", 10, "diodi", 1, 10.5f, 2, "note", "foto", 1);
            daop.add(p);
            System.out.println("add ok wait for update");
            Thread.sleep(3000);
            
             ProdottoDAO daopu = new ProdottoDAO();
            
             Prodotto p2 = daopu.getBySku(p.getSku());
             
             p2.setNome("2n5999");
             daopu.update(p2);
                     
                     
    }
}
