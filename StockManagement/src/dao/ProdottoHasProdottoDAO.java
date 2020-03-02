/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Prodotto_has_Prodotto;
import database.DriverManagerConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;


/**
 *
 * @author LittleJoke
 */
public class ProdottoHasProdottoDAO {
    
    private final String TABLE_NAME = "prodotto_has_prodotto";
        
    public synchronized Collection<Prodotto_has_Prodotto> getAll() throws SQLException {

            Connection connection = null;
            PreparedStatement preparedStatement = null;

            Collection<Prodotto_has_Prodotto> prpr = new LinkedList<Prodotto_has_Prodotto>();

            String selectSQL = "SELECT * FROM " + this.TABLE_NAME;

            try {
                    connection = DriverManagerConnectionPool.getConnection();
                    preparedStatement = connection.prepareStatement(selectSQL);

                    ResultSet rs = preparedStatement.executeQuery();

                    while (rs.next()) {
                    
                    Prodotto_has_Prodotto bean = new Prodotto_has_Prodotto();
                    
                          bean.setSku_prodotto(rs.getString("sku_prodotto"));
                          bean.setSku_componente(rs.getString("sku_componente"));
                          bean.setQty(rs.getInt("qty"));
                        
                        prpr.add(bean);
                    }

            } finally {
                    try {
                            if (preparedStatement != null)
                                    preparedStatement.close();
                    } finally {
                            DriverManagerConnectionPool.releaseConnection(connection);
                    }
            }
		
		return prpr;
	}
    
        public synchronized Utente getByID(String id) throws SQLException{
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;

                        Utente bean = new Utente();

                        String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE id = ?";

                          try {
                        connection = DriverManagerConnectionPool.getConnection();
                        preparedStatement = connection.prepareStatement(selectSQL);
                        preparedStatement.setString(1, id);
                        ResultSet rs = preparedStatement.executeQuery();

                        while (rs.next()) {


                        bean.setId(rs.getString("id"));
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

                                preparedStatement.setString(1, u.getId());
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
                        
                         System.out.println("id dell'utente da modificare"+u.getId());	                                                   

                                                          
                        String insertSQL = "UPDATE " +TABLE_NAME+ " SET `fullname` = '"+u.getFullname()+"', `CF` = '"+u.getCF()+"', `indirizzo` = '"+u.getIndirizzo()+"', `tel` = '"+u.getTelefono()+"', `email` = '"+u.getEmail()+"', `pwd` = '"+u.getPwd()+"', `permessi` = '"+u.getPermessi()+"', `note` = '"+u.getNote()+"' WHERE (`id` = '"+u.getId()+"')";
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

		String query = "DELETE FROM " + this.TABLE_NAME + " WHERE id='"+id+"'";
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

  public synchronized Utente getLastID() throws SQLException{
        
            Connection connection = null;
            Statement  ps = null;
           Utente bean = new Utente();


            String query = "select* from utenti order by datareg DESC LIMIT 1";      
                 		try  {
                                                        connection = DriverManagerConnectionPool.getConnection();
                                                        ps = connection.prepareStatement(query);

                                                        ResultSet rs = ps.executeQuery(query);
                                                        
                                                        while (rs.next()) {
                                                                                                
                                                            bean.setId(rs.getString("id"));
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
                                }
                                                finally {
			try {
                                                                                if (ps != null)
                                                                                    ps.close();
                                                                            }
                                                                          finally {
                                                                                    DriverManagerConnectionPool.releaseConnection(connection);
                                                                                    }
                        
                                                  }
                                                return bean;
        
        }
    
}
