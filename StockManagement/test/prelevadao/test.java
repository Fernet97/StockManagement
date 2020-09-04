/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prelevadao;

import ordinedao.*;
import java.sql.SQLException;
import dao.PrelevaDAO;
import beans.Preleva;
import com.sun.javafx.geom.transform.TransformHelper;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author LittleJoke
 */
public class test {

    public static void main(String[] args) throws InterruptedException, SQLException {
            Preleva bean = new Preleva();
            PrelevaDAO dao =new PrelevaDAO();
            
                    

        for (int i = 1; i < 3; i++) {
            
        bean.startOrdine();
//        Preleva p = new Preleva(n_ordine, data, qty, fk_utente, prodotto_sku)
        Preleva o = new Preleva(17 ,  "admin", "CI1-07/07/2020 17:12:13");
        Preleva o2 = new Preleva( 36, "admin", "CI2-07/07/2020 17:12:20");
        Preleva o3 = new Preleva( 4, "admin", "CI3-07/07/2020 17:12:31");

        dao.add(o);
            System.out.println("dion");
        dao.add(o2);
            System.out.println("mammt");
        dao.add(o3);
       
            System.out.println("aspetta 15 secondi , giro= "+i);
        Thread.sleep(15000);
        }

//        String n = "1";
//           Collection<Ordine> ordini = dao.getByNum(n);
//            
//            for(Ordine getall : ordini){
//              System.out.println("testdaoprint " +getall.toString());
//            }
            
            
            
    }
}

