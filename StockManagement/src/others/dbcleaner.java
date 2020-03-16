package others;

import beans.Utente;
import dao.UtenteDAO;
import database.DriverManagerConnectionPool;
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

    public static void main(String[] args) throws InterruptedException, SQLException {

        Connection connection = null;
        Statement statement = null;
        String query = "  DELETE FROM ordine; DELETE FROM cliente; DELETE FROM prodotto_has_prodotto; DELETE FROM prodotto; DELETE FROM fornitore; DELETE FROM utente;INSERT INTO `db_stock`.`utente` (`idutente`, `datareg`, `fullname`, `cf`, `indirizzo`, `tel`, `email`, `pwd`, `permessi`, `note`, `id`) VALUES ('admin', 'NULL', 'NULL', 'NULL', 'NULL', 'NULL', 'NULL', 'admin', '0', 'null', '0')";
  
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
