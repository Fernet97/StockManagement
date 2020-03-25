/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinedao;

import java.sql.SQLException;
import dao.OrdineDAO;
import beans.Ordine;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;


/**
 *
 * @author LittleJoke
 */
public class test3 {

    public static void main(String[] args) throws InterruptedException, SQLException, ParseException {
//Ordine bean = new Ordine();
            OrdineDAO dao =new OrdineDAO();

            
            System.out.println(dao.isArrivato("ord-2"));

//        System.out.println(dao.ggConsegna());
//System.out.println(dao.ggConsegnaPR("ord-1", "di10-22/03/2020 21:26:50"));
            
            
    }
}

