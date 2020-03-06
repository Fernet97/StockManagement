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
    
        public synchronized Prodotto_has_Prodotto getByProdotto(String sku_prodotto) throws SQLException{
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;

Prodotto_has_Prodotto bean = new Prodotto_has_Prodotto();
                        String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE sku_prodotto = ?";

                          try {
                        connection = DriverManagerConnectionPool.getConnection();
                        preparedStatement = connection.prepareStatement(selectSQL);
                        preparedStatement.setString(1, sku_prodotto);
                        ResultSet rs = preparedStatement.executeQuery();

                        while (rs.next()) {


                        bean.setSku_prodotto(rs.getString("sku_prodotto"));
                          bean.setSku_componente(rs.getString("sku_componente"));
                          bean.setQty(rs.getInt("qty"));
                        


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
        
        public synchronized void add (Prodotto_has_Prodotto prpr)throws SQLException {
            
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;

                        String insertSQL =  "INSERT INTO " +TABLE_NAME
                                + " VALUES (?, ?, ?)";
                        
                        try {
                                connection = DriverManagerConnectionPool.getConnection();
                                preparedStatement = connection.prepareStatement(insertSQL);

                                preparedStatement.setString(1, prpr.getSku_prodotto());
                                preparedStatement.setString(2, prpr.getSku_componente());
                                preparedStatement.setInt(3, prpr.getQty());
                                
                            
                        
		
                        System.out.println("prpr add "+preparedStatement);
                        

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
        
        public synchronized void update (Prodotto_has_Prodotto prpr) throws SQLException{
                        Connection connection = null;
                        Statement st = null;  
                        
                         System.out.println("id dell'utente da modificare");	                                                   

                                                          
                        String insertSQL = "UPDATE "+this.TABLE_NAME+" SET `sku_prodotto` = '"+prpr.getSku_prodotto()+"', `sku_componente` = '"+prpr.getSku_componente()+"', `qty` = '"+prpr.getQty()
                                +"' WHERE (`sku_prodotto` = '"+prpr.getSku_prodotto()+"') and (`sku_componente` = '"+prpr.getSku_componente()+"')";
            System.out.println("prpr update "+ insertSQL);
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

public synchronized void remove (Prodotto_has_Prodotto prpr) throws SQLException{
                        Connection connection = null;
                        Statement  statement = null;

		String query = "DELETE FROM " + this.TABLE_NAME + " WHERE sku_prodotto='"+prpr.getSku_prodotto()+"'";
                System.out.println("prpr remove "+query);

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


    
}
