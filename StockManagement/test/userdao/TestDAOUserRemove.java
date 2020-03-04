package userdao;


import dao.UtenteDAO;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LittleJoke
 */
public class TestDAOUserRemove {
       public static void main(String[] args) throws InterruptedException, SQLException{
           System.out.println("*//remove*//");
           UtenteDAO  dao = new UtenteDAO();
           dao.remove("f.caino2");
       }
    
}
