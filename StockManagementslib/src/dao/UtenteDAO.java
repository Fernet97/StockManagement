/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Utente;
import database.DriverManagerConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 *
 * @author LittleJoke
 */
public class UtenteDAO {
    private final String TABLE_NAME = "utente";
    public static String usergen = "";
        
    public synchronized Collection<Utente> getAll() throws SQLException {

            Connection connection = null;
            PreparedStatement preparedStatement = null;

            Collection<Utente> utenti = new LinkedList<Utente>();

            String selectSQL = "SELECT * FROM " + this.TABLE_NAME;

            try {
                    connection = DriverManagerConnectionPool.getConnection();
                    preparedStatement = connection.prepareStatement(selectSQL);

                    ResultSet rs = preparedStatement.executeQuery();

                    while (rs.next()) {
                    
                        Utente bean = new Utente();
                        
                        bean.setIdutente(rs.getString("idutente"));
                        bean.setDatareg(rs.getString("datareg"));
                        bean.setFullname(rs.getString("fullname"));
                        bean.setCF(rs.getString("CF"));
                        bean.setIndirizzo(rs.getString("indirizzo"));
                        bean.setTelefono(rs.getString("tel"));
                        bean.setEmail(rs.getString("email"));
                        bean.setPwd(rs.getString("pwd"));
                        bean.setPermessi(rs.getInt("permessi"));
                        bean.setNote(rs.getString("note"));

                        
                        utenti.add(bean);
                    }

            } finally {
                    try {
                            if (preparedStatement != null)
                                    preparedStatement.close();
                    } finally {
                            DriverManagerConnectionPool.releaseConnection(connection);
                    }
            }
		
		return utenti;
	}
    
        public synchronized Utente getByID(String id) throws SQLException{
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;

                        Utente bean = new Utente();

                        String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE idutente = ?";

                          try {
                        connection = DriverManagerConnectionPool.getConnection();
                        preparedStatement = connection.prepareStatement(selectSQL);
                        preparedStatement.setString(1, id);
                        ResultSet rs = preparedStatement.executeQuery();

                        while (rs.next()) {


                        bean.setIdutente(rs.getString("idutente"));
                        bean.setDatareg(rs.getString("datareg"));
                        bean.setFullname(rs.getString("fullname"));
                        bean.setCF(rs.getString("CF"));
                        bean.setIndirizzo(rs.getString("indirizzo"));
                        bean.setTelefono(rs.getString("tel"));
                        bean.setEmail(rs.getString("email"));
                        bean.setPwd(rs.getString("pwd"));
                        bean.setPermessi(rs.getInt("permessi"));
                        bean.setNote(rs.getString("note"));


                        }

                } finally {
                        try {
                                if (preparedStatement != null)
                                        preparedStatement.close();
                        } finally {
                                DriverManagerConnectionPool.releaseConnection(connection);
                        }
                }

                    return bean;          
            }
        
        public synchronized void add (Utente u)throws SQLException {
            
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        
                       // System.out.println("Pwd utente nuovo:"+u.getPwd()+"->"+Cryptorr.MD5(u.getPwd()));
                        
                        String insertSQL =  "INSERT INTO " +TABLE_NAME
                                + " VALUES (?, ?, ?, ?, ?, ?, ?, md5(?), ?, ?, ?)";
                        
                        try {
                                connection = DriverManagerConnectionPool.getConnection();
                                preparedStatement = connection.prepareStatement(insertSQL);

                                preparedStatement.setString(1, u.getIdutente());
                                preparedStatement.setString(2, u.getDatareg());
                                preparedStatement.setString(3, u.getFullname());
                                preparedStatement.setString(4, u.getCF());
                                preparedStatement.setString(5, u.getIndirizzo());
                                preparedStatement.setString(6, u.getTelefono());    
                                preparedStatement.setString(7, u.getEmail());
                                preparedStatement.setString(8, u.getPwd());
                                preparedStatement.setInt(9, u.getPermessi());
                                preparedStatement.setString(10, u.getNote());
                                preparedStatement.setInt(11, u.getCode());
                                
                                
                        

			preparedStatement.executeUpdate();

			connection.commit();
                                Logger.getLogger("userlog").info(u.toString());// non ritorna la password @ovveride cambiato nel bean

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		

        }
        
        public synchronized void update (Utente u) throws SQLException{
                        Connection connection = null;
                        Statement st = null;  
                        
                         Logger.getLogger("userlog").info("id dell'utente da modificare"+u.getIdutente());	                                                   

                                                          
                        String insertSQL = "UPDATE " +TABLE_NAME+ " SET `fullname` = '"+u.getFullname()+"', `CF` = '"+u.getCF()+"', "
                                + "`indirizzo` = '"+u.getIndirizzo()+"', `tel` = '"+u.getTelefono()+"', `email` = '"+u.getEmail()+"',"
                                + " `pwd` = md5('"+u.getPwd()+"'), `permessi` = '"+u.getPermessi()+"', `note` = '"+u.getNote()+""
                                + "' WHERE (`idutente` = '"+u.getIdutente()+"')";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			st = connection.createStatement();
			st.executeUpdate(insertSQL);
			connection.commit();
                         Logger.getLogger("userlog").info(u.toString());// non ritorna la password @ovveride cambiato nel bean
		} finally {
			try {
				if (st != null)
					st.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}	

    }

public synchronized void remove (String id) throws SQLException{
                        Connection connection = null;
                        Statement  statement = null;

		String query = "DELETE FROM " + this.TABLE_NAME + " WHERE idutente='"+id+"'";
               Logger.getLogger("userlog").info("utenti remove \n"+query);

                        try {
                                connection = DriverManagerConnectionPool.getConnection();
                                statement = connection.createStatement();
                                statement.executeUpdate(query);
                                connection.commit();
                                 Logger.getLogger("userlog").info(query);

                                    }
                                        finally {
                                                            try {
                                                                    if (statement != null)
                                                                        statement.close();
                                                                            }
                                                                                    finally {
				DriverManagerConnectionPool.releaseConnection(connection);
                                                                                                        }
                                                            }
	
	
	}

  public synchronized Utente getLastID(String fullname) throws SQLException{
        

                        Connection connection = null;
                        PreparedStatement preparedStatement = null;

                        Utente bean = new Utente();
                        
                                    String[] name = fullname.split(" ");
                                    name[0] = name[0].substring(0, 1) + ".";
                                    usergen = name[0]+name[1];

                        String selectSQL = "select * from "+this.TABLE_NAME+" where idutente like '%"+usergen+"%' order by id desc limit 1";

                          try {
                        connection = DriverManagerConnectionPool.getConnection();
                        preparedStatement = connection.prepareStatement(selectSQL);
                        ResultSet rs = preparedStatement.executeQuery();

                        while (rs.next()) {


                        bean.setIdutente(rs.getString("idutente"));
                        bean.setDatareg(rs.getString("datareg"));
                        bean.setFullname(rs.getString("fullname"));
                        bean.setCF(rs.getString("CF"));
                        bean.setIndirizzo(rs.getString("indirizzo"));
                        bean.setTelefono(rs.getString("tel"));
                        bean.setEmail(rs.getString("email"));
                        bean.setPwd(rs.getString("pwd"));
                        bean.setPermessi(rs.getInt("permessi"));
                        bean.setNote(rs.getString("note"));
                        bean.setCode(rs.getInt("id"));


                        }
                            
                } finally {
                        try {
                                if (preparedStatement != null)
                                        preparedStatement.close();
                        } finally {
                                DriverManagerConnectionPool.releaseConnection(connection);
                        }
                }

                    return bean;          
            }
      
      

  
   
  

}
