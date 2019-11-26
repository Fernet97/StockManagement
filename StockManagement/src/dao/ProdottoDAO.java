/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import beans.Prodotto;
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
public class ProdottoDAO {
     private final String TABLE_NAME = "prodotti";
     
     
      public synchronized Collection<Prodotto> getAll() throws SQLException{
        
                                                Connection connection = null;
                                                PreparedStatement ps = null;
                                                
                                                Collection<Prodotto> prodotti = new  LinkedList<Prodotto>();
                                                
                                                String selectSQL = "SELECT * FROM " + this.TABLE_NAME;
         
                                                try {
			connection = DriverManagerConnectionPool.getConnection();
			 ps = connection.prepareStatement(selectSQL);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Prodotto  bean = new Prodotto();
                                                                                                
                                                                                                bean.setSku(rs.getString("sku"));
                                                                                                bean.setDataReg(rs.getString("datareg"));
                                                                                                bean.setNome(rs.getString("nome"));
                                                                                                bean.setCategoria(rs.getString("categoria"));
                                                                                                bean.setInStock(rs.getBoolean("instock"));
                                                                                                bean.setDescrizione(rs.getString("descrizione"));

                                                                                                prodotti.add(bean);
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
                                                return prodotti;
                                                
    }
      
        public  synchronized Prodotto getBySku(int sku) throws SQLException{
             
            Connection connection = null;
             PreparedStatement ps = null;
             
             Prodotto bean = new Prodotto();
             //SELECT * FROM prodotti WHERE sku = 1;
             String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE sku = ?";
             
                 		try  {
                                                        connection = DriverManagerConnectionPool.getConnection();
                                                        ps = connection.prepareStatement(selectSQL);
                                                        ps.setInt(1, sku);// FA RIFERIMENTO AL NOME ED AL NUMERO DELLA COLONNA NEL DB

                                                        ResultSet rs = ps.executeQuery();
                                                        
                                                        while (rs.next()) {
                                                                                                
                                                                                                bean.setSku(rs.getString("sku"));
                                                                                                bean.setDataReg(rs.getString("datareg"));
                                                                                                bean.setNome(rs.getString("nome"));
                                                                                                bean.setCategoria(rs.getString("categoria"));
                                                                                                bean.setInStock(rs.getBoolean("instock"));
                                                                                                bean.setDescrizione(rs.getString("descrizione"));
                                                                                                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                                                                                                bean.setQty(rs.getInt("qty"));
                                                                                                bean.setCosto(rs.getFloat("costo"));
                                                                                                bean.setFoto(rs.getString("foto"));
                                                                                                  
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
  
        public synchronized void add(Prodotto b) throws SQLException{
                         Connection connection = null;
                        PreparedStatement ps = null;
        
                        String insertSQL= "INSERT INTO " + TABLE_NAME
                                                                      + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";       
                        
                        try{
                                    connection = DriverManagerConnectionPool.getConnection();
                                    ps = connection.prepareStatement(insertSQL);     
                                    
                                    ps.setString(1, b.getSku());
                                    ps.setString(2, b.getDataReg());
                                    ps.setString(3, b.getNome());
                                    ps.setString(4, b.getCategoria());
                                    ps.setBoolean(5, b.isInStock());//tinyint
                                    ps.setString(6, b.getDescrizione());
                                    ps.setInt(7, b.getGiorni_alla_consegna());
                                    ps.setInt(8, b.getQty());
                                    ps.setFloat(9, b.getCosto());
                                    ps.setString(10, b.getFoto());
		
                                    System.out.println(insertSQL);
                                    
                                    ps.executeUpdate();
                                    
                                    connection.commit();
                        }
                        finally{
                                    try{
                                            if (ps != null)
                                                ps.close();
                                    }
                                    finally{
                                                 DriverManagerConnectionPool.releaseConnection(connection);
                                    }
                        }
        }
}

