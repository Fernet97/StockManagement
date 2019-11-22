/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Fornitore;
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
 * @author Fernet
 */
public class FornitoreDAO {
    
        private final String TABLE_NAME = "fornitori";


        public synchronized Collection<Fornitore> getAll() throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Fornitore> fornitori = new LinkedList<Fornitore>();

		String selectSQL = "SELECT * FROM " + this.TABLE_NAME;

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Fornitore bean = new Fornitore();
                                bean.setIdfornitore(rs.getInt("idfornitori"));
                                bean.setP_iva(rs.getString("p.iva"));
				bean.setNome(rs.getString("nome"));
                                bean.setCognome(rs.getString("cognome"));
                                bean.setNome_azienda(rs.getString("nome_azienda"));
                                bean.setStato(rs.getString("stato"));
                                bean.setTel(rs.getString("tel"));
                                bean.setEmail(rs.getString("email"));
                                bean.setDesc(rs.getString("descr"));

				fornitori.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return fornitori;
	}



        
        public synchronized Fornitore getByID(int id) throws SQLException {
                
 		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Fornitore bean = new Fornitore();

		String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE idfornitori = ?";
        
 		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);// FA RIFERIMENTO AL NOME ED AL NUMERO DELLA COLONNA NEL DB

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
                                bean.setIdfornitore(rs.getInt("idfornitori"));
                                bean.setP_iva(rs.getString("p.iva"));
				bean.setNome(rs.getString("nome"));
                                bean.setCognome(rs.getString("cognome"));
                                bean.setNome_azienda(rs.getString("nome_azienda"));
                                bean.setStato(rs.getString("stato"));
                                bean.setTel(rs.getString("tel"));
                                bean.setEmail(rs.getString("email"));
                                bean.setDesc(rs.getString("desc"));
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




	
        // INSERT INTO `db_stock`.`fornitori` (`idfornitori`, `p.iva`, `nome`, `cognome`, `nome_azienda`, `stato`, `tel`, `email`, `desc`) VALUES ('1', 'xxxxx', 'Mimmo', 'Mimmetti', 'Mimmo corp', 'Italia', '367267267123', 'mimmo@gmail.com', 'scriviamo una descriziona a caso per mimmo');
	public synchronized void add(Fornitore b) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (idfornitori, p.iva, nome, cognome, nome_azienda, stato, tel, email, desc) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);

                        preparedStatement.setInt(1, b.getIdfornitore());
                        preparedStatement.setString(2, b.getP_iva());
			preparedStatement.setString(3, b.getNome());
                        preparedStatement.setString(4, b.getCognome());
                        preparedStatement.setString(5, b.getNome_azienda());
                        preparedStatement.setString(6, b.getStato());
                        preparedStatement.setString(7, b.getTel());
                        preparedStatement.setString(8, b.getEmail());
                        preparedStatement.setString(9, b.getDesc());
		
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
        
        

        
        
	public synchronized void update(Fornitore b) throws SQLException {
		
                Connection connection = null;
		Statement statement = null;

		
		String query = "UPDATE " + TABLE_NAME+ " SET p.iva="+b.getP_iva()+", nome="+b.getNome()+", cognome="+b.getCognome()+", nome_azienda="+b.getNome_azienda()+", stato="+b.getStato()+", tel="+b.getTel()+", email="+b.getEmail()+", desc="+b.getDesc()+"  WHERE ID ="+b.getIdfornitore()+";";
		
		System.out.println(query);
		
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

}
