/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientedao;


import java.sql.SQLException;
import dao.ClienteDAO;
import beans.Cliente;

/**
 *
 * @author LittleJoke
 */
public class test {

    public static void main(String[] args) throws InterruptedException, SQLException {

       ClienteDAO daoc = new ClienteDAO();
       Cliente c = new Cliente("nome", "", "", "", "", "");
       daoc.add(c);
        
       
    }

}
