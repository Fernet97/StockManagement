/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Ordine;
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
public class OrdineDAO {
    
    
    private final String TABLE_NAME = "ordine";

    public synchronized Collection<Ordine> getAll() throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        Collection<Ordine> prodotti = new LinkedList<Ordine>();

        String selectSQL = "select* from "+this.TABLE_NAME+"";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ordine bean = new Ordine();
              bean.setIdordine(rs.getInt("idordine"));
              bean.setN_ordine(rs.getString("n_ordine"));
              bean.setData(rs.getString("data"));
              bean.setQty_in_arrivo(rs.getInt("qty_in_arrivo"));
              bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
              bean.setFk_utente(rs.getString("fk_utente"));
              bean.setProdotto_sku(rs.getString("prodotto_sku"));
              bean.setFk_cliente(rs.getInt("fk_cliente"));
              bean.setFk_fornitore(rs.getString("fk_fornitore"));

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

    public synchronized Ordine getByNum(String n_ordine) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        Ordine bean = new Ordine();
        //SELECT * FROM prodotti WHERE sku = '1';
        String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE sku = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);
//            ps.setString(1, sku);// FA RIFERIMENTO AL NOME ED AL NUMERO DELLA COLONNA NEL DB

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                         bean.setIdordine(rs.getInt("idordine"));
              bean.setN_ordine(rs.getString("n_ordine"));
              bean.setData(rs.getString("data"));
              bean.setQty_in_arrivo(rs.getInt("qty_in_arrivo"));
              bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
              bean.setFk_utente(rs.getString("fk_utente"));
              bean.setProdotto_sku(rs.getString("prodotto_sku"));
              bean.setFk_cliente(rs.getInt("fk_cliente"));
              bean.setFk_fornitore(rs.getString("fk_fornitore"));

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

    public synchronized void add(Ordine b) throws SQLException {
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
    public synchronized void update(Ordine p) throws SQLException { //in p c'è il prodotto già modificato (SKUVECCHIO,  parametri nuovi)
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

    public synchronized void remove(String n_ordine) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        String query = "DELETE FROM " + this.TABLE_NAME + " WHERE  (`n_ordine` = '" + n_ordine + "');";
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

    public synchronized Collection<Ordine> getLastOrdine() throws SQLException {

        Connection connection = null;
        Statement ps = null;
        Ordine bean = new Ordine();
   Collection<Ordine> ordini = new LinkedList<Ordine>();
        String query = "select* from "+this.TABLE_NAME+" order by id DESC LIMIT 1";
        

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {

                            bean.setIdordine(rs.getInt("idordine"));
              bean.setN_ordine(rs.getString("n_ordine"));
              bean.setData(rs.getString("data"));
              bean.setQty_in_arrivo(rs.getInt("qty_in_arrivo"));
              bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
              bean.setFk_utente(rs.getString("fk_utente"));
              bean.setProdotto_sku(rs.getString("prodotto_sku"));
              bean.setFk_cliente(rs.getInt("fk_cliente"));
              bean.setFk_fornitore(rs.getString("fk_fornitore"));
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
