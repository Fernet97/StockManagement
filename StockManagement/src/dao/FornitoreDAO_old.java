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
import java.util.LinkedList;

/**
 *
 * @author Fernet
 * @author LittleJoke95
 */
public class FornitoreDAO_old {

    private final String TABLE_NAME = "fornitori";

    public synchronized Collection<Fornitore_old> getAll() throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<Fornitore_old> fornitori = new LinkedList<Fornitore_old>();

        String selectSQL = "SELECT * FROM " + this.TABLE_NAME+ " order by datareg";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Fornitore_old bean = new Fornitore_old();

                bean.setIdfornitore(rs.getString("idfornitori"));
                bean.setDatareg(rs.getString("datareg"));
                bean.setFullname(rs.getString("fullname"));
                bean.setP_iva(rs.getString("p_iva"));
                bean.setIndirizzo(rs.getString("indirizzo"));
                bean.setTel(rs.getString("tel"));
                bean.setEmail(rs.getString("email"));
                bean.setDesc(rs.getString("description"));

                fornitori.add(bean);

            }

        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }

        return fornitori;
    }

    public synchronized Fornitore_old getByID(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;

        Fornitore_old bean = new Fornitore_old();

        String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE idfornitori = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);
            ps.setString(1, id);// FA RIFERIMENTO AL NOME ED AL NUMERO DELLA COLONNA NEL DB

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                bean.setIdfornitore(rs.getString("idfornitori"));
                bean.setDatareg(rs.getString("datareg"));
                bean.setFullname(rs.getString("fullname"));
                bean.setP_iva(rs.getString("p_iva"));
                bean.setIndirizzo(rs.getString("indirizzo"));
                bean.setTel(rs.getString("tel"));
                bean.setEmail(rs.getString("email"));
                bean.setDesc(rs.getString("description"));

            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }

        }
        return bean;
    }

    // INSERT INTO `db_stock`.`fornitori` (`idfornitori`, `p_iva`, `nome`, `cognome`, `nome_azienda`, `stato`, `tel`, `email`, `description`) VALUES ('1', 'xxxxx', 'Mimmo', 'Mimmetti', 'Mimmo corp', 'Italia', '367267267123', 'mimmo@gmail.com', 'scriviamo una descriziona a caso per mimmo');
    public synchronized void add(Fornitore_old b) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println("fornitore add "+insertSQL);

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, b.getIdfornitore());
            preparedStatement.setString(2, b.getDatareg());
            preparedStatement.setString(3, b.getFullname());
            preparedStatement.setString(4, b.getP_iva());
            preparedStatement.setString(5, b.getIndirizzo());
            preparedStatement.setString(6, b.getTel());
            preparedStatement.setString(7, b.getEmail());
            preparedStatement.setString(8, b.getDesc());


            preparedStatement.executeUpdate();

            connection.commit();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }

    }

    /**
     * ogni volta che si richiama il metodo update, VA' SEMPRE RICHIAMATO IL
     * METODO remove per eliminare il vecchio record che doveva essere
     * aggiornato
     *
     * es:
     * <pre>
     * ProdottoDAO dao = new ProdottoDAO();
     * Prodotto pr1 = new Prodotto("test1", "test1");
     * dao.add(pr1);
     * Prodotto pr2 = new Prodotto("test2", "test2");
     * dao.update(pr2); //richiama metodo update
     * dao.remove(pr1); //richiama metodo remove
     * </pre>
     *
     * @param p
     * @throws SQLException
     */
    public synchronized void update(Fornitore_old b) throws SQLException {
		Connection connection = null;
		Statement statement = null;

		Fornitore_old f = b;
		
                System.out.println("Id del fornitore da modificare: "+ f.getIdfornitore());
		String query = "UPDATE fornitori SET   `fullname` = '"+f.getFullname()+"', `p_iva` = '"+f.getP_iva()+"', `indirizzo` = '"+f.getIndirizzo()+"', `tel` = '"+f.getTel()+"', `email` = '"+f.getEmail()+"', `description` = '"+f.getDesc()+"' WHERE (`idfornitori` = '"+f.getIdfornitore()+"');";
		      System.out.println("fornitore update "+ query);		
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

    public synchronized void remove(String idFornitore) throws SQLException {

        Connection connection = null;
        Statement statement = null;
//DELETE FROM `db_stock`.`prodotti` WHERE (`sku` = 'mi1-13/12/2019 11:21:41');
        String query = "DELETE FROM " + this.TABLE_NAME + " WHERE (`idfornitori` = '"+idFornitore+"')";
        System.out.println("fornitori remove "+query);
        try {
            connection = DriverManagerConnectionPool.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.commit();

        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }

    }
    
    public synchronized Fornitore_old getLastID() throws SQLException{
        
            Connection connection = null;
            Statement  ps = null;
            Fornitore_old bean = new Fornitore_old();


            String query = "select* from fornitori order by datareg DESC LIMIT 1";      
                 		try  {
                                                        connection = DriverManagerConnectionPool.getConnection();
                                                        ps = connection.prepareStatement(query);

                                                        ResultSet rs = ps.executeQuery(query);
                                                        
                                                        while (rs.next()) {
                                                                                                
                                                            bean.setIdfornitore(rs.getString("idfornitori"));
                                                            bean.setDatareg(rs.getString("datareg"));
                                                            bean.setFullname(rs.getString("fullname"));
                                                            bean.setP_iva(rs.getString("p_iva"));
                                                            bean.setIndirizzo(rs.getString("indirizzo"));
                                                            bean.setTel(rs.getString("tel"));
                                                            bean.setEmail(rs.getString("email"));
                                                            bean.setDesc(rs.getString("description"));

                                                                                                  
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
