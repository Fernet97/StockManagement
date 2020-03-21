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
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Hashtable;

/**
 *
 * @author LittleJoke
 */
public class OrdineDAO {

    private final String TABLE_NAME = "ordine";

    public synchronized Collection<Ordine> getAll() throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        Collection<Ordine> ordini = new LinkedList<Ordine>();

        String selectSQL = "select* from " + this.TABLE_NAME + "";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ordine bean = new Ordine();
                bean.setN_ordine(rs.getString("n_ordine"));
                bean.setData(rs.getString("data"));
                bean.setQty_in_arrivo(rs.getInt("qty_in_arrivo"));
                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                bean.setFk_utente(rs.getString("fk_utente"));
                bean.setProdotto_sku(rs.getString("prodotto_sku"));
                bean.setFk_cliente(rs.getInt("fk_cliente"));
                bean.setFk_fornitore(rs.getString("fk_fornitore"));

                ordini.add(bean);

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
        return ordini;

    }

    public synchronized Collection<Ordine> getByNum(String o) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        

        Collection<Ordine> ordini = new LinkedList<Ordine>();
        //SELECT * FROM prodotti WHERE sku = '1';
        
        String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE n_ordine = '"+o+"'";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ordine bean = new Ordine();
                bean.setN_ordine(rs.getString("n_ordine"));
                bean.setData(rs.getString("data"));
                bean.setQty_in_arrivo(rs.getInt("qty_in_arrivo"));
                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                bean.setFk_utente(rs.getString("fk_utente"));
                bean.setProdotto_sku(rs.getString("prodotto_sku"));
                bean.setFk_cliente(rs.getInt("fk_cliente"));
                bean.setFk_fornitore(rs.getString("fk_fornitore"));

                ordini.add(bean);
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
        return ordini;
    }

    public synchronized void add(Ordine o) throws SQLException {
       Connection connection = null;
        Statement statement = null;

        String query = "INSERT INTO "+this.TABLE_NAME+" (`n_ordine`, `data`, `qty_in_arrivo`, `giorni_alla_consegna`, "
                                                                                + "`fk_utente`, `prodotto_sku`, `fk_cliente`, `fk_fornitore`, `id`) "
                                                                                         + "VALUES ('"+o.getN_ordine()+"', '"+o.getData()+"', '"
                                                                                        + ""+o.getQty_in_arrivo()+"', '"+o.getGiorni_alla_consegna()+"',"
                                                                                        + " '"+o.getFk_utente()+"', '"+o.getProdotto_sku()+"',"
                                                                                        + " '"+o.getFk_cliente()+"', '"+o.getFk_fornitore()+"', '"+o.getCode()+"')";

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

    // NB: ma se vuoi cambiare la categoria .. ma poi dovrei cambiare anche lo sku? DA RISCRIVERE
    public synchronized void update(Ordine o) throws SQLException { //in p c'è il prodotto già modificato (SKUVECCHIO,  parametri nuovi)
        Connection connection = null;
        Statement statement = null;

        System.out.println("sku del ordine da modificare: " + o.getN_ordine());
//UPDATE `db_stock`.`ordine` SET `n_ordine` = '2', `data` = '2', `qty_in_arrivo` = '2', `giorni_alla_consegna` = '2', `fk_utente` = '2', `prodotto_sku` = '2', `fk_cliente` = '2', `fk_fornitore` = '2', `id` = '2' WHERE (`idordine` = '10')
        String query = "UPDATE " + this.TABLE_NAME + " SET `data` = '2', `qty_in_arrivo` = '2', `giorni_alla_consegna` = '2', `fk_utente` = '2', `prodotto_sku` = '2', `fk_cliente` = '2', `fk_fornitore` = '2', `id` = '2' WHERE (`idordine` = '10')";
        System.out.println("ordine update " + query);

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

    public synchronized void removeOrd(String n_ordine) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        String query = "DELETE FROM " + this.TABLE_NAME + " WHERE  (`n_ordine` = '" + n_ordine + "');";
        System.out.println("ordine remove " + query);

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

    public synchronized void removePr(String n_ordine, String prodotto_sku) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        String query = "DELETE FROM " + this.TABLE_NAME + " WHERE  `n_ordine` = '" + n_ordine + "' and `prodotto_sku` = '" + prodotto_sku + "";
        System.out.println("prodotto ord remove " + query);

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

    public synchronized Ordine getLastOrdine() throws SQLException {

        Connection connection = null;
        Statement ps = null;
        Ordine bean = new Ordine();

        String query = "select* from " + this.TABLE_NAME + " order by id DESC LIMIT 1";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {

                bean.setN_ordine(rs.getString("n_ordine"));
                bean.setData(rs.getString("data"));
                bean.setQty_in_arrivo(rs.getInt("qty_in_arrivo"));
                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                bean.setFk_utente(rs.getString("fk_utente"));
                bean.setProdotto_sku(rs.getString("prodotto_sku"));
                bean.setFk_cliente(rs.getInt("fk_cliente"));
                bean.setFk_fornitore(rs.getString("fk_fornitore"));
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
    
    
        public synchronized ArrayList<ArrayList<String>> groupByOrdini() throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        ArrayList<ArrayList<String>> ordini = new ArrayList<>(5);
        
        String selectSQL = "select n_ordine , sum(qty_in_arrivo), sum(costo), giorni_alla_consegna ,data from db_stock.ordine,"
                + " db_stock.prodotto where prodotto_sku = sku group by n_ordine, giorni_alla_consegna";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ArrayList<String> record = new ArrayList<String>();
                record.add(rs.getString("n_ordine"));
                record.add(rs.getString("sum(qty_in_arrivo)"));
                record.add(rs.getString("sum(costo)"));
                record.add(rs.getString("giorni_alla_consegna"));
                record.add(rs.getString("data"));
                
                ordini.add(record);
                
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
        return ordini;
    }
        
        
}
