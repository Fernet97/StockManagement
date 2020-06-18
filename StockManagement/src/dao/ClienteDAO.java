/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Cliente;
import database.DriverManagerConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 *
 * @author LittleJoke
 */
public class ClienteDAO {
    
    private final String TABLE_NAME = "cliente";

    public synchronized Collection<Cliente> getAll() throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        Collection<Cliente> clienti = new LinkedList<Cliente>();

        String selectSQL = "select* from "+this.TABLE_NAME+"";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente bean = new Cliente();
               bean.setIdcliente(rs.getInt("idcliente"));
               bean.setDatareg(rs.getString("datareg"));
               bean.setFullname(rs.getString("fullname"));
               bean.setCf(rs.getString("cf"));
               bean.setIndirizzo(rs.getString("indirizzo"));
               bean.setTel(rs.getString("tel"));
               bean.setEmail(rs.getString("email"));
               bean.setNote(rs.getString("note"));

                clienti.add(bean);

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
        return clienti;

    }

    public synchronized Cliente getByID(String id) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        Cliente bean = new Cliente();
        //SELECT * FROM prodotti WHERE sku = '1';
        String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE idcliente = "+id+"";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

               bean.setIdcliente(rs.getInt("idcliente"));
               bean.setDatareg(rs.getString("datareg"));
               bean.setFullname(rs.getString("fullname"));
               bean.setCf(rs.getString("cf"));
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

    public synchronized void add(Cliente c) throws SQLException {
        Connection connection = null;
        Statement statement = null;

        String insertSQL = "INSERT INTO "+this.TABLE_NAME+" (`datareg`, `fullname`, `cf`, `indirizzo`, `tel`, `email`, `note`) "
                + "VALUES ( '"+c.getDatareg()+"', '"+c.getFullname()+"', '"+c.getCf()+"', '"+c.getIndirizzo()+"', '"+c.getTel()+"', "
                + "'"+c.getEmail()+"', '"+c.getNote()+"')";
        
       
        try {
            connection = DriverManagerConnectionPool.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(insertSQL);
            connection.commit();
            Logger.getLogger("userlog").info(insertSQL);
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

    // NB: ma se vuoi cambiare la categoria .. ma poi dovrei cambiare anche lo sku? DA RISCRIVERE
    public synchronized void update(Cliente c) throws SQLException { //in p c'è il prodotto già modificato (SKUVECCHIO,  parametri nuovi)
        Connection connection = null;
        Statement statement = null;

        Logger.getLogger("userlog").info("id del cliente da modificare: \n" + c.getIdcliente());
//UPDATE `db_stock`.`cliente` SET `idcliente` = '2', `datareg` = '?', `fullname` = '?', `cf` = '?', `indirizzo` = '?', `tel` = '?', `email` = '?', `note` = '?' WHERE (`idcliente` = '1');

        String query = "UPDATE "+this.TABLE_NAME+" SET `fullname` = '"+c.getFullname()+"', `cf` = '"+c.getCf()+"', `indirizzo` = '"+c.getIndirizzo()+"', `tel` = '"+c.getTel()+"', `email` = '"+c.getEmail()+"', `note` = '"+c.getNote()+"' WHERE (`idcliente` = '"+c.getIdcliente()+"')";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.commit();
            Logger.getLogger("userlog").info(query);
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

    public synchronized void remove(int id) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        String query = "DELETE FROM " + this.TABLE_NAME + " WHERE  (`idcliente` = '" + id + "');";


        try {
            connection = DriverManagerConnectionPool.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.commit();
            
            Logger.getLogger("userlog").info(query);
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

    public synchronized Cliente getLastCliente() throws SQLException {

        Connection connection = null;
        Statement ps = null;
        Cliente bean = new Cliente();

        String query = "select* from "+this.TABLE_NAME+" order by idcliente DESC LIMIT 1";
        

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {

               bean.setIdcliente(rs.getInt("idcliente"));
               bean.setDatareg(rs.getString("datareg"));
               bean.setFullname(rs.getString("fullname"));
               bean.setCf(rs.getString("cf"));
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
