package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe che si occupa di fornire una connessione con il database
 * @author Fernet
 *st
 */
public  class DriverManagerConnectionPool  {

        private static List<Connection> freeDbConnections;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        boolean flag=false;

	
	static {
		freeDbConnections = new LinkedList<Connection>();
		try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

		} catch (Exception e) {
			System.out.println("DB driver not found:"+ e.getMessage());
		} 
	}

	
	private static synchronized Connection createDBConnection() throws SQLException {
		Connection newConnection = null;
		String ip = "localhost";
		String port = "3306";
		String db = "db_stock";
		String username = "root";
		String password = "fut5al17l";

		

		newConnection = DriverManager.getConnection("jdbc:mysql://"+ ip+":"+ port+"/"+db+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", username, password);

		newConnection.setAutoCommit(false);
		return newConnection;
	}


	public static synchronized Connection getConnection() throws SQLException {
		Connection connection;

		if (!freeDbConnections.isEmpty()) {
			connection = (Connection) freeDbConnections.get(0);
			freeDbConnections.remove(0);

			try {
				if (connection.isClosed())
					connection = getConnection();
			} catch (SQLException e) {
				connection.close();
				connection = getConnection();
			}
		} else {
			connection = createDBConnection();		
		}

		return connection;
	}
        
        
            // verifica connessione
    public boolean checkLogin(int ID,String password, String user){
        if("ADMINISTRATOR".equals(user)){
            //String query="SELECT * FROM utenti WHERE id='"+ID+"' AND password='"+password+"'AND isAdmin= 1";
            String query="SELECT * FROM utenti WHERE id='1' AND pwd='test' AND isAdmin= '1'";
            try{
                rs=stmt.executeQuery(query);
                while(rs.next()){
                    flag=true;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            String query="SELECT * FROM utenti WHERE id='"+ID+"' AND pwd='"+password+"'AND isAdmin = 0'";
            try{
                rs=stmt.executeQuery(query);
                while(rs.next()){
                    flag=true;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return flag;
    }
        
        
        
        

	public static synchronized void releaseConnection(Connection connection) throws SQLException {
		if(connection != null) freeDbConnections.add(connection);
	}
        
        
        

 
}