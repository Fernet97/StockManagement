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

/**
 *
 * @author LittleJoke
 */
public class UtenteDAO {
    private final String TABLE_NAME = "utenti";
        
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
                        bean.setId(rs.getString("id"));
                        bean.setFullname(rs.getString("fullname"));
                        bean.setTelefono(rs.getString("telefono"));
                        bean.setEmail(rs.getString("email"));
                        bean.setCF(rs.getString("CF"));
                        bean.setIndirizzo(rs.getString("indirizzo"));
                        bean.setPwd(rs.getString("pwd"));
                        bean.setCreate(rs.getBoolean("create"));
                        bean.setUpdate(rs.getBoolean("update"));
                        bean.setView(rs.getBoolean("view"));
                        bean.setDelete(rs.getBoolean("delete"));
                        bean.setIsAdmin(rs.getBoolean("isAdmin"));
                        
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
    
}
