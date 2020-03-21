/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinedao;

import java.sql.SQLException;
import dao.OrdineDAO;
import beans.Ordine;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author LittleJoke
 */
public class test1 {

    public static void main(String[] args) throws InterruptedException, SQLException {
            Ordine bean = new Ordine();
            OrdineDAO dao =new OrdineDAO();

            
  int n = dao.groupByOrdini().size();
for (int i = 0; i < n; i++) {
    System.out.println("aaaaa "+dao.groupByOrdini().get(i).get(0));
    int m = dao.groupByOrdini().get(i).size();
    for (int j = 0; j < m; j++) {

        System.out.print(" = "+ dao.groupByOrdini().get(i).get(j) );
    }
    System.out.println("");
}
          
            
            
            
    }
}

