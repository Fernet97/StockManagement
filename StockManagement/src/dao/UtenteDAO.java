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
import static others.Passwordgen.generateRandomPassword;

/**
 *
 * @author LittleJoke
 */
public class UtenteDAO {
    private final String TABLE_NAME = "utente";
        
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
                        
                        String insertSQL =  "INSERT INTO " +TABLE_NAME
                                + " VALUES (?, ?, ?, ?, ?, ?, ?, md5(?), ?, ?)";
                        
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
                                preparedStatement.setString(8, generateRandomPassword(10));
                                preparedStatement.setInt(9, u.getPermessi());
                                preparedStatement.setString(10, u.getNote());
                            
                        
		
                        System.out.println("utente add "+preparedStatement);
                        

			preparedStatement.executeUpdate();

			connection.commit();

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
                        
                         System.out.println("id dell'utente da modificare"+u.getIdutente());	                                                   

                                                          
                        String insertSQL = "UPDATE " +TABLE_NAME+ " SET `fullname` = '"+u.getFullname()+"', `CF` = '"+u.getCF()+"', "
                                + "`indirizzo` = '"+u.getIndirizzo()+"', `tel` = '"+u.getTelefono()+"', `email` = '"+u.getEmail()+"',"
                                + " `pwd` = '"+u.getPwd()+"', `permessi` = '"+u.getPermessi()+"', `note` = '"+u.getNote()+""
                                + "' WHERE (`idutente` = '"+u.getIdutente()+"')";
            System.out.println("utente update "+ insertSQL);
		try {
			connection = DriverManagerConnectionPool.getConnection();
			st = connection.createStatement();
			st.executeUpdate(insertSQL);
			connection.commit();
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
                System.out.println("utenti remove "+query);

                        try {
                                connection = DriverManagerConnectionPool.getConnection();
                                statement = connection.createStatement();
                                statement.executeUpdate(query);
                                connection.commit();

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

  public synchronized Utente getLastID(String id) throws SQLException{
        

                        Connection connection = null;
                        PreparedStatement preparedStatement = null;

                        Utente bean = new Utente();

                        String selectSQL = "select * from "+this.TABLE_NAME+"where idutente like '%"+Utente.lastusergen+"%' order by idutente desc limit 1";

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
      
      
      
  
   
  

}
