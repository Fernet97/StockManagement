package database;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import others.AES;
/**
 * Classe che si occupa di fornire una connessione con il database
 *
 * @author Fernet 
 */
public class DriverManagerConnectionPool {
    

    private static List<Connection> freeDbConnections;
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    boolean flag = false;
    public static ArrayList<String> array;
    public static String pswd;

    static {
        freeDbConnections = new LinkedList<Connection>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"DB driver not found: \n" + e.getMessage());
            
                
        }
    }
/**
 * stanno 2 newConnection
 * commenta a seconda della rete su cui vuoi lavorare (locale oppure remota su rete LAN)
 * @return
 * @throws SQLException 
 */
    private static synchronized Connection createDBConnection() throws SQLException {
     ArrayList<String> arr =getConf();
        Connection newConnection = null;
    
        
         String ip = AES.decrypt(arr.get(0), "miao");
         String port = AES.decrypt(arr.get(1), "miao");
         String db = AES.decrypt(arr.get(2), "miao");
        String username = AES.decrypt(arr.get(3), "miao");
        String password = AES.decrypt(arr.get(4), "miao");
        pswd = password;

        newConnection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db + "?allowMultiQueries=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", username, password);

        newConnection.setAutoCommit(false);
      
 System.out.println("connection miao "+newConnection);
        return newConnection;
    }

    public static synchronized Connection getConnection() throws SQLException {
        Connection connection = null;

        if (!freeDbConnections.isEmpty()) {
            connection = (Connection) freeDbConnections.get(0);
            freeDbConnections.remove(0);

            try {
                if (connection.isClosed()) {
                    connection = getConnection();
                }
            } catch (SQLException e) {
                connection.close();
                connection = getConnection();
                 JOptionPane.showMessageDialog(null,"SQLException: \n" + e.getMessage());
            }
        } else { 
                try{
                connection = createDBConnection();
            }catch(CommunicationsException exx){
                    JOptionPane.showMessageDialog(null, "Impossibile connettersi al DB: \n"+exx.getMessage()+"");
                   
                    
                }
        }
        
       

        return connection;
    }

    public static synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection != null) {
            freeDbConnections.add(connection);
        }
    }
    
        public static ArrayList<String> getConf(){
    
     try {
//                File file = new File("./ai.aksn");
                File file = new File("./DATA/CONFIG/ai.aksn");
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                array = (ArrayList<String>) ois.readObject();

            } catch (ClassNotFoundException ex) {
                 JOptionPane.showMessageDialog(null,"ClassNotFoundException: \n" + ex.getMessage());
            } catch (FileNotFoundException ex) {
//                File file = new File("./ai.aksn");
                File file = new File("./DATA/CONFIG/ai.aksn");
                JOptionPane.showMessageDialog(null,"File di configurazione non trovato: \n" + ex.getMessage());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"IOException: \n" + ex.getMessage());
                array = new ArrayList<>();
            }return array;
}

}
