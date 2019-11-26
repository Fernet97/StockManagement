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
}
