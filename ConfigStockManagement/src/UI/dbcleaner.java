package UI;

import beans.Utente;
import database.DriverManagerConnectionPool;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author LittleJoke
 */
public class dbcleaner {

    public void dbCleaner() throws InterruptedException, SQLException {
        File f = new File("./DATA/CONFIG/aikkop.aksn");
        if (f.exists()){
            f.delete();
        }
        
        Utente u = new Utente();
        Connection connection = null;
        Statement statement = null;
        String query = "  DELETE FROM ordine; DELETE FROM cliente; DELETE FROM prodotto_has_prodotto; DELETE FROM prodotto; DELETE FROM fornitore; DELETE FROM utente";
                
        System.out.println(query);
        try {
            connection = DriverManagerConnectionPool.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.commit();

        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }       
    }
}
