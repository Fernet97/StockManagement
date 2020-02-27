/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import beans.Fornitore_old;
import beans.Prodotto;
import database.DriverManagerConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 *
 * @author LittleJoke
 */
public class ProdottoDAO_old {
     private final String TABLE_NAME = "prodotti";
     
     
      public synchronized Collection<Prodotto> getAll() throws SQLException{
        
                Connection connection = null;
                PreparedStatement ps = null;

                Collection<Prodotto> prodotti = new  LinkedList<Prodotto>();

                String selectSQL = "select* from prodotti, prodotti_has_fornitori where prodotti.sku = prodotti_has_fornitori.prodotti_sku";

                try {
			connection = DriverManagerConnectionPool.getConnection();
			 ps = connection.prepareStatement(selectSQL);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Prodotto  bean  =  new  Prodotto(); 
                                bean.setSku(rs.getString("sku"));
                                bean.setDatareg(rs.getString("datareg"));
                                bean.setNome(rs.getString("nome"));
                                bean.setCategoria(rs.getString("categoria"));
                                bean.setQty(rs.getInt("qty"));
                                bean.setInstock(rs.getBoolean("instock"));
                                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                                bean.setCosto(rs.getFloat("costo"));
                                bean.setDescrizione(rs.getString("descrizione"));
                                bean.setQty_inarrivo(rs.getInt("qty_inarrivo"));
                                bean.setQty_min(rs.getInt("qty_min"));
                                bean.setFoto(rs.getString("foto"));
                                bean.setId_fornitore(rs.getString("fornitori_idfornitori"));

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
             String selectSQL = "SELECT * FROM " + this.TABLE_NAME+ ", prodotti_has_fornitori" + " WHERE prodotti.sku = ? and prodotti.sku = prodotti_has_fornitori.prodotti_sku";
             
                 		try  {
                                                        connection = DriverManagerConnectionPool.getConnection();
                                                        ps = connection.prepareStatement(selectSQL);
                                                        ps.setString(1, sku);// FA RIFERIMENTO AL NOME ED AL NUMERO DELLA COLONNA NEL DB

                                                        ResultSet rs = ps.executeQuery();
                                                        
                                                        while (rs.next()) {
                                                                                                
                                                                                                bean.setSku(rs.getString("sku"));
                                                                                                bean.setDatareg(rs.getString("datareg"));
                                                                                                bean.setNome(rs.getString("nome"));
                                                                                                bean.setCategoria(rs.getString("categoria"));
                                                                                                bean.setQty(rs.getInt("qty"));
                                                                                                bean.setInstock(rs.getBoolean("instock"));
                                                                                                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                                                                                                bean.setCosto(rs.getFloat("costo"));
                                                                                                bean.setDescrizione(rs.getString("descrizione"));
                                                                                                bean.setQty_inarrivo(rs.getInt("qty_inarrivo"));
                                                                                                bean.setQty_min(rs.getInt("qty_min"));
                                                                                                bean.setFoto(rs.getString("foto"));
                                                                                                bean.setId_fornitore(rs.getString("fornitori_idfornitori"));
                                                                                                
                                                                                                  
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
  
       
        public synchronized void add(Prodotto b, String IdFornitore) throws SQLException{
                         Connection connection = null;
                        PreparedStatement ps = null;
        
                        String insertSQL= "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); INSERT INTO `prodotti_has_fornitori` (`prodotti_sku`, `fornitori_idfornitori`) VALUES ('"+b.getSku()+"', '"+IdFornitore+"');";       
                        
                        try{
                                    connection = DriverManagerConnectionPool.getConnection();
                                    ps = connection.prepareStatement(insertSQL);     
                                    
                                    ps.setString(1, b.getSku());
                                    ps.setString(2, b.getDatareg());
                                    ps.setString(3, b.getNome());
                                    ps.setString(4, b.getCategoria());
                                    ps.setInt(5, b.getQty());
                                    ps.setBoolean(6, b.isInstock());//tinyint
                                    ps.setInt(7, b.getGiorni_alla_consegna());
                                    ps.setFloat(8, b.getCosto());
                                    ps.setString(9, b.getDescrizione());
                                   ps.setInt(10, b.getQty_inarrivo());
                                    ps.setInt(11, b.getQty_min());  
                                    ps.setString(12, b.getFoto());
		
                                    System.out.println("prodotto add "+insertSQL);
                                    
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
        
        
        // NB: ma se vuoi cambiare la categoria .. ma poi dovrei cambiare anche lo sku?
        public synchronized void update (Prodotto p, String IdNewForn) throws SQLException{ //in p c'è il prodotto già modificato (SKUVECCHIO,  parametri nuovi)
  		Connection connection = null;
		Statement statement = null;
                String idNewFornitore;
                
                
                if(IdNewForn.equals("")) idNewFornitore = p.getId_fornitore();
                else idNewFornitore = IdNewForn;
		
                System.out.println("sku del prodoto da modificare: "+ p.getSku());
		String query = "UPDATE `db_stock`.`prodotti` SET `nome` = '"+p.getNome()+"', `categoria` = '"+p.getCategoria()+"', `qty` = '"+p.getQty()+"', `instock` = "+p.isInstock()+", `giorni_alla_consegna` = '"+p.getGiorni_alla_consegna()+"', `costo` = '"+p.getCosto()+"', `descrizione` = '"+p.getDescrizione()+"', `qty_inarrivo` = '"+p.getQty_inarrivo()+"', `qty_min` = '"+p.getQty_min()+"',"
                        + " `foto` = '"+p.getFoto()+"' WHERE (`sku` = '"+p.getSku()+"'); UPDATE `db_stock`.`prodotti_has_fornitori` SET `fornitori_idfornitori` = '"+idNewFornitore+"' WHERE (`prodotti_sku` = '"+p.getSku()+"') and (`fornitori_idfornitori` = '"+p.getId_fornitore()+"');";
		System.out.println("prodotto update "+query);	
                
		try {
			connection = DriverManagerConnectionPool.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(query);
			connection.commit();
		} finally {
			try {
				if (statement != null)
					statement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}          
            

        }
                           

      
              
            public synchronized void remove (String sku,  String fornitoreID) throws SQLException{
                        Connection connection = null;
                        Statement  statement = null;
                                    //DELETE FROM `db_stock`.`prodotti` WHERE (`sku` = 'mi1-13/12/2019 11:21:41');
                                                    // WHERE (`prodotti_sku` = 'mi1-16/12/2019 11:13:59') and (`fornitori_idfornitori` = 'FR-2');
                                         //   DELETE FROM `db_stock`.`prodotti_has_fornitori` WHERE (`prodotti_sku` = 'ca2-16/12/2019 12:21:16') and (`fornitori_idfornitori` = 'FR-2');
            String query = "DELETE FROM prodotti_has_fornitori WHERE (`prodotti_sku` =  '"+sku+"') and (`fornitori_idfornitori` = '"+fornitoreID+"'); "
                    + "DELETE FROM " +this.TABLE_NAME + " WHERE  (`sku` = '"+sku+"');"; 
                System.out.println("prodotto remove "+query);


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
                            bean.setDatareg(rs.getString("datareg"));
                            bean.setNome(rs.getString("nome"));
                            bean.setCategoria(rs.getString("categoria"));
                            bean.setQty(rs.getInt("qty"));
                            bean.setInstock(rs.getBoolean("instock"));
                            bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                            bean.setCosto(rs.getFloat("costo"));
                            bean.setDescrizione(rs.getString("descrizione"));
                            bean.setQty_inarrivo(rs.getInt("qty_inarrivo"));
                            bean.setQty_min(rs.getInt("qty_min"));
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
        
        
        
        
        public synchronized   Hashtable<String, String> getCatAndSum() throws SQLException {
            Connection connection = null;
            Statement  ps = null;
            Prodotto bean = new Prodotto();
            Hashtable<String, String> hashtable = new Hashtable<String, String>();

            String query = "select categoria, sum(qty) from prodotti GROUP BY categoria;";      
           
            try  {
                        connection = DriverManagerConnectionPool.getConnection();
                        ps = connection.prepareStatement(query);

                        ResultSet rs = ps.executeQuery(query);

                        while (rs.next()) {
                                 hashtable.put(rs.getString("categoria"),rs.getString("sum(qty)"));


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
                                                return hashtable;      
        
        }
        
        
}

  


