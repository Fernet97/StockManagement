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
import java.util.Hashtable;
import java.util.LinkedList;

/**
 *
 * @author LittleJoke
 */
public class ProdottoDAO {

    private final String TABLE_NAME = "prodotto";

    public synchronized Collection<Prodotto> getAll() throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        Collection<Prodotto> prodotti = new LinkedList<Prodotto>();

        String selectSQL = "select* from "+this.TABLE_NAME+"";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Prodotto bean = new Prodotto();
                bean.setSku(rs.getString("sku"));
                bean.setDatareg(rs.getString("datareg"));
                bean.setNome(rs.getString("nome"));
                bean.setQty(rs.getInt("qty"));
                bean.setCategoria(rs.getString("categoria"));
                bean.setInstock(rs.getInt("instock"));
                bean.setCosto(rs.getFloat("costo"));
                bean.setQty_min(rs.getInt("qty_min"));
                bean.setNote(rs.getString("note"));
                bean.setFoto(rs.getString("foto"));
                bean.setNegozio(rs.getInt("negozio"));

                prodotti.add(bean);

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
        return prodotti;

    }

    public synchronized Prodotto getBySku(String sku) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        Prodotto bean = new Prodotto();
        //SELECT * FROM prodotti WHERE sku = '1';
        String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE sku = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);
            ps.setString(1, sku);// FA RIFERIMENTO AL NOME ED AL NUMERO DELLA COLONNA NEL DB

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                bean.setSku(rs.getString("sku"));
                bean.setDatareg(rs.getString("datareg"));
                bean.setNome(rs.getString("nome"));
                bean.setQty(rs.getInt("qty"));
                bean.setCategoria(rs.getString("categoria"));
                bean.setInstock(rs.getInt("instock"));
                bean.setCosto(rs.getFloat("costo"));
                bean.setQty_min(rs.getInt("qty_min"));
                bean.setNote(rs.getString("note"));
                bean.setFoto(rs.getString("foto"));
                bean.setNegozio(rs.getInt("negozio"));


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

    public synchronized void add(Prodotto b) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;

        String insertSQL = "INSERT INTO " + this.TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(insertSQL);

                ps.setString(1, b.getSku());
                ps.setString(2, b.getDatareg());
                ps.setString(3, b.getNome());
                ps.setInt(4, b.getQty());
                ps.setString(5, b.getCategoria());
                ps.setInt(6, b.isInstock());
                ps.setFloat(7, b.getCosto());
                ps.setInt(8, b.getQty_min());
                ps.setString(9, b.getNote());
                ps.setString(10, b.getFoto());
                ps.setInt(11, b.isNegozio());
                ps.setInt(12, b.getCode());
           

            System.out.println("prodotto add " + b.toString());

            ps.executeUpdate();

            connection.commit();

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }

    // NB: ma se vuoi cambiare la categoria .. ma poi dovrei cambiare anche lo sku? DA RISCRIVERE
    public synchronized void update(Prodotto p) throws SQLException { //in p c'è il prodotto già modificato (SKUVECCHIO,  parametri nuovi)
        Connection connection = null;
        Statement statement = null;




        System.out.println("sku del prodoto da modificare: " + p.getSku());
       // UPDATE `db_stock`.`prodotto` SET `sku` = '1', `datareg` = '2', `nome` = '2', `qty` = '2', `categoria` = '2', `instock` = '2', `costo` = '2', `qty_min` = '2', `note` = '2', `foto` = '2', `negozio` = '2' WHERE (`sku` = '1');

        String query = "UPDATE "+this.TABLE_NAME+" SET `nome` = '"+p.getNome()+"', `qty` = '"+p.getQty()+"', `categoria` = '"+p.getCategoria()+"', "
                + "`instock` = '"+p.isInstock()+"', `costo` = '"+p.getCosto()+"', `qty_min` = '"+p.getQty_min()+"', `note` = '"+p.getNote()+"', "
                + "`foto` = '"+p.getNote()+"', `negozio` = '"+p.isNegozio()+"' WHERE (`sku` = '"+p.getSku()+"')";
        System.out.println("prodotto update " + query);

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

    public synchronized void remove(String sku) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        String query = "DELETE FROM " + this.TABLE_NAME + " WHERE  (`sku` = '" + sku + "');";
        System.out.println("prodotto remove " + query);

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

    public synchronized Prodotto getLastProdotto() throws SQLException {

        Connection connection = null;
        Statement ps = null;
        Prodotto bean = new Prodotto();

        String query = "select* from "+this.TABLE_NAME+" order by id DESC LIMIT 1";
        

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {

                bean.setSku(rs.getString("sku"));
                bean.setDatareg(rs.getString("datareg"));
                bean.setNome(rs.getString("nome"));
                bean.setQty(rs.getInt("qty"));
                bean.setCategoria(rs.getString("categoria"));
                bean.setInstock(rs.getInt("instock"));
                bean.setCosto(rs.getFloat("costo"));
                bean.setQty_min(rs.getInt("qty_min"));
                bean.setNote(rs.getString("note"));
                bean.setFoto(rs.getString("foto"));
                bean.setNegozio(rs.getInt("negozio"));
                bean.setCode(rs.getInt("id"));
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

    public synchronized Hashtable<String, String> getCatAndSum() throws SQLException {
        Connection connection = null;
        Statement ps = null;
        Prodotto bean = new Prodotto();
        Hashtable<String, String> hashtable = new Hashtable<String, String>();

        String query= "select categoria, sum(qty) from "+this.TABLE_NAME+" GROUP BY categoria;";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {
                hashtable.put(rs.getString("categoria"), rs.getString("sum(qty)"));

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
        return hashtable;

    }

}
