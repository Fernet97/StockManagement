package others;

import beans.Utente;
import dao.UtenteDAO;
import database.DriverManagerConnectionPool;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author LittleJoke
 */
public class FirstRun {
/**
* classe che servirá per richiamare metodo di add 
* al primo avvio del software,
* aggiungendo il primo utente MASTERADMIN 
     * @throws java.lang.InterruptedException
     * @throws java.sql.SQLException
 */
    public synchronized void adminAdd() throws InterruptedException, SQLException {
        Utente u = new Utente();
                
        Connection connection = null;
        Statement statement = null;
        String query = "INSERT INTO `db_stock`.`utente` (`idutente`, `datareg`, `fullname`, `cf`, `indirizzo`, `tel`, `email`, `pwd`, `permessi`, `note`, `id`) "
                + "VALUES ('admin', '"+u.generateData()+"', '"+u.getFullname()+"', '"+u.getCF()+"', '"+u.getIndirizzo()+"',"
                + " '"+u.getTelefono()+"', '"+u.getEmail()+"', md5('"+u.getPwd()+"'), '0', 'null', '0')";
  
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
