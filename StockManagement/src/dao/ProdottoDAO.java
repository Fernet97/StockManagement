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
                                                
                                                String selectSQL = "SELECT * FROM " + this.TABLE_NAME+ "order by datareg";
         
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
                                                                                                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                                                                                                bean.setQty(rs.getInt("qty"));
                                                                                                bean.setQty_min(rs.getInt("qty_min"));
                                                                                                bean.setCosto(rs.getFloat("costo"));
                                                                                                bean.setFoto(rs.getString("foto"));

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
      
        public  synchronized Prodotto getBySku(String sku) throws SQLException{
             
            Connection connection = null;
             PreparedStatement ps = null;
             
             Prodotto bean = new Prodotto();
             //SELECT * FROM prodotti WHERE sku = 1;
             String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE sku = ?";
             
                 		try  {
                                                        connection = DriverManagerConnectionPool.getConnection();
                                                        ps = connection.prepareStatement(selectSQL);
                                                        ps.setString(1, sku);// FA RIFERIMENTO AL NOME ED AL NUMERO DELLA COLONNA NEL DB

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
                                                                                                bean.setQty_min(rs.getInt("qty_min"));
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
                                    ps.setInt(9, b.getQty_min());
                                    ps.setFloat(10, b.getCosto());
                                    ps.setString(11, b.getFoto());
		
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
        public synchronized void update (Prodotto p) throws SQLException{ //in p c'è il prodotto già modificato (SKUVECCHIO,  parametri nuovi)
                        Connection connection = null;
                        PreparedStatement ps = null;
                        
                        Prodotto p1 = getBySku(p.getSku());  // i dati del record vecchio che voglio modificare
                        remove(p.getSku()); 

                        p1.setSku("uno sku generato");
                        p1.setNome(p.getNome());
                        p1.setDataReg(p.getDataReg());
                        p1.setCategoria(p.getCategoria());
                        p1.setInStock(p.isInStock());
                        p1.setDescrizione(p.getDescrizione());
                        p1.setGiorni_alla_consegna(p.getGiorni_alla_consegna());
                        p1.setQty(p.getQty());
                        p1.setQty_min(p.getQty_min());
                        p1.setCosto(p.getCosto());
                        p1.setFoto(p.getFoto());
                        add(p1);

                           
                        /*
                        System.out.println("start here");
                        
                                    String insertSQL = "UPDATE prodotti SET sku = ?, datareg = ?, nome = ?, categoria = ?, instock = ?, descrizione = ?,  giorni_alla_consegna = ?, qty = ?, costo = ?,  foto = ? WHERE sku = "+p.getSku();
                          
                        connection = DriverManagerConnectionPool.getConnection();
                        ps = connection.prepareStatement(insertSQL);     
                        
                        
                        ps.setString(1, p.getSku());
                        ps.setString(2, p.getDataReg());
                        ps.setString(3, p.getNome());
                        ps.setString(4, p.getCategoria());
                        ps.setBoolean(5, p.isInStock());
                        ps.setString(6, p.getDescrizione());
                        ps.setInt(7, p.getGiorni_alla_consegna());
                        ps.setInt(8, p.getQty());
                        ps.setFloat(9, p.getCosto());
                        ps.setString(10, p.getFoto());
                        
                        ps.executeUpdate();
                        
                        connection.commit();*/
        }
        
            public synchronized void remove (String sku) throws SQLException{
                        Connection connection = null;
                        Statement  statement = null;

		String query = "DELETE FROM " + this.TABLE_NAME + " WHERE sku ='"+sku+"'";
                System.out.println(query);

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
            
            
        public synchronized Prodotto getLastProdotto() throws SQLException{
        
            Connection connection = null;
            Statement  ps = null;
            Prodotto bean = new Prodotto();


            String query = "select* from prodotti order by datareg DESC LIMIT 1";      
                 		try  {
                                                        connection = DriverManagerConnectionPool.getConnection();
                                                        ps = connection.prepareStatement(query);

                                                        ResultSet rs = ps.executeQuery(query);
                                                        
                                                        while (rs.next()) {
                                                                                                
                                                                                                bean.setSku(rs.getString("sku"));
                                                                                                bean.setDataReg(rs.getString("datareg"));
                                                                                                bean.setNome(rs.getString("nome"));
                                                                                                bean.setCategoria(rs.getString("categoria"));
                                                                                                bean.setInStock(rs.getBoolean("instock"));
                                                                                                bean.setDescrizione(rs.getString("descrizione"));
                                                                                                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                                                                                                bean.setQty(rs.getInt("qty"));
                                                                                                bean.setQty_min(rs.getInt("qty_min"));
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

}

