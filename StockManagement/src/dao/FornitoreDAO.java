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
 * @author LittleJoke
 */
public class FornitoreDAO {

    private final String TABLE_NAME = "fornitore";

    public synchronized Collection<Fornitore> getAll() throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<Fornitore> fornitori = new LinkedList<Fornitore>();

        String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " order by datareg";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Fornitore bean = new Fornitore();

                bean.setIdfornitore(rs.getString("idfornitore"));
                bean.setDatareg(rs.getString("datareg"));
                bean.setFullname(rs.getString("fullname"));
                bean.setP_iva(rs.getString("p_iva"));
                bean.setIndirizzo(rs.getString("indirizzo"));
                bean.setTel(rs.getString("tel"));
                bean.setEmail(rs.getString("email"));
                bean.setNote(rs.getString("note"));

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

    public synchronized Fornitore getByID(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;

        Fornitore bean = new Fornitore();
        String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE idfornitore ='"+id+"'";
        System.out.println("QUERY:"+selectSQL);
        try {
            connection = DriverManagerConnectionPool.getConnection();
//            ps.setString(1, id);// FA RIFERIMENTO AL NOME ED AL NUMERO DELLA COLONNA NEL DB
            ps = connection.prepareStatement(selectSQL);
          

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                bean.setIdfornitore(rs.getString("idfornitore"));
                bean.setDatareg(rs.getString("datareg"));
                bean.setFullname(rs.getString("fullname"));
                bean.setP_iva(rs.getString("p_iva"));
                bean.setIndirizzo(rs.getString("indirizzo"));
                bean.setTel(rs.getString("tel"));
                bean.setEmail(rs.getString("email"));
                bean.setNote(rs.getString("note"));

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

    public synchronized void add(Fornitore b) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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
            preparedStatement.setString(8, b.getNote());

            preparedStatement.executeUpdate();
        System.out.println("fornitore add " + preparedStatement.toString());

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

  
    public synchronized void update(Fornitore b) throws SQLException {
        Connection connection = null;
        Statement statement = null;

        Fornitore f = b;

        System.out.println("Id del fornitore da modificare: " + f.getIdfornitore());
        String query = "UPDATE "+this.TABLE_NAME+" SET   `fullname` = '" + f.getFullname() + "', `p_iva` = '" + f.getP_iva() + "',"
                + " `indirizzo` = '" + f.getIndirizzo() + "', `tel` = '" + f.getTel() + "', `email` = '" + f.getEmail() + "', "
                + "`note` = '" + f.getNote() + "' WHERE (`idfornitore` = '" + f.getIdfornitore() + "');";
        System.out.println("fornitore update " + query);
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

    public synchronized void remove(String idFornitore) throws SQLException {

        Connection connection = null;
        Statement statement = null;
//DELETE FROM `db_stock`.`prodotti` WHERE (`sku` = 'mi1-13/12/2019 11:21:41');
        String query = "DELETE FROM " + this.TABLE_NAME + " WHERE (`idfornitore` = '" + idFornitore + "')";
        System.out.println("fornitori remove " + query);
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

    public synchronized Fornitore getLastID() throws SQLException {

        Connection connection = null;
        Statement ps = null;
        Fornitore bean = new Fornitore();

        String query = "select* from "+this.TABLE_NAME+" order by idfornitore DESC LIMIT 1";
        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {

                bean.setIdfornitore(rs.getString("idfornitore"));
                bean.setDatareg(rs.getString("datareg"));
                bean.setFullname(rs.getString("fullname"));
                bean.setP_iva(rs.getString("p_iva"));
                bean.setIndirizzo(rs.getString("indirizzo"));
                bean.setTel(rs.getString("tel"));
                bean.setEmail(rs.getString("email"));
                bean.setNote(rs.getString("note"));

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

}
