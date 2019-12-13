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
 *//*
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
            // insert into utenti values ("2", "test2", "test2", "test2", "test2", "test2", "test2", 1, 1, 1, 1, 1);
                        Connection connection = null;
                        PreparedStatement preparedStatement = null;
                        
                        String insertSQL =  "INSERT INTO " +TABLE_NAME
                                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        
                        try {
                                connection = DriverManagerConnectionPool.getConnection();
                                preparedStatement = connection.prepareStatement(insertSQL);

                                preparedStatement.setString(1, u.getId());
                                preparedStatement.setString(2, u.getFullname());
                                preparedStatement.setString(3, u.getTelefono());
                                preparedStatement.setString(4, u.getEmail());
                                preparedStatement.setString(5, u.getCF());
                                preparedStatement.setString(6, u.getIndirizzo());
                                preparedStatement.setString(7, u.getPwd());
                                preparedStatement.setBoolean(8, u.isCreate());
                                preparedStatement.setBoolean(9, u.isUpdate());
                                preparedStatement.setBoolean(10, u.isView());
                                preparedStatement.setBoolean(11, u.isDelete());
                                preparedStatement.setBoolean(12, u.isIsAdmin());
                        
		
                        System.out.println(insertSQL);

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
                        PreparedStatement st = null;  
                        
                         System.out.println("Entra qui");
                                 String insertSQL= "UPDATE " +TABLE_NAME+ "SET `id` = '?', `fullname` = '?', `telefono` = '?', `email` = '?', `CF` = '?', `indirizzo` = '?', `pwd` = '?', `create` = '?', `update` = '?', `view` = '?', `delete` = '?', `isAdmin` = '?' WHERE  (" +u.getId()+")";
//                         String insertSQL= "UPDATE utenti SET id = ?, fullname = ?, telefono = ?, email = ?, CF = ?, indirizzo = ?, pwd = ?, create = ?, update = ?, view= ?, delete = ?, isAdmin = ? WHERE (id = " +u.getId()+")";
                        connection = DriverManagerConnectionPool.getConnection();
                          st = connection.prepareStatement(insertSQL);
                          
                                    Utente u1 = getByID(u.getId());
                                    remove(u.getId());
                          
                        st.setString(1, u.getId());
                        st.setString(2, u.getFullname());
                        st.setString(3, u.getTelefono());
                        st.setString(4, u.getEmail());
                        st.setString(5, u.getCF());
                        st.setString(6, u.getIndirizzo());
                        st.setString(7, u.getPwd());
                        st.setBoolean(8, u.isCreate());
                        st.setBoolean(9, u.isUpdate());
                        st.setBoolean(10, u.isView());
                        st.setBoolean(11, u.isDelete());
                        st.setBoolean(12, u.isIsAdmin());
                        System.out.println(st);
                         st.executeUpdate();

                connection.close();
        }

public synchronized void remove (String id) throws SQLException{
                        Connection connection = null;
                        Statement  statement = null;

		String query = "DELETE FROM " + this.TABLE_NAME + " WHERE id='"+id+"'";
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
}

*/