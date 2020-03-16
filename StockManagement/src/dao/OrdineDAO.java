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

        Collection<Ordine> ordini = new LinkedList<Ordine>();

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

    public synchronized Ordine getByNum(String n_ordine) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        Ordine bean = new Ordine();
        //SELECT * FROM prodotti WHERE sku = '1';
        String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE n_ordine = "+n_ordine+"";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

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

    public synchronized void add(Ordine o) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        

Collection<Ordine> ordini = new LinkedList<Ordine>();
        String insertSQL = "INSERT INTO " + this.TABLE_NAME + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(insertSQL);

               ps.setString(2, o.getN_ordine());
               ps.setString(3, o.getData());
               while (ps.getResultSet().next()){
               ps.setInt(4, o.getQty_in_arrivo());
               ps.setInt(5, o.getGiorni_alla_consegna());
               ps.setString(6, o.getFk_utente());
               ps.setString(6, o.getProdotto_sku());
               ps.setInt(8, o.getFk_cliente());
               ps.setString(9, o.getFk_fornitore());
               //id da aggiungere
               }
               
               
           

            System.out.println("ordine add " + ordini.toString());

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
    public synchronized void update(Ordine o) throws SQLException { //in p c'è il prodotto già modificato (SKUVECCHIO,  parametri nuovi)
        Connection connection = null;
        Statement statement = null;




        System.out.println("sku del ordine da modificare: " + o.getN_ordine());
//UPDATE `db_stock`.`ordine` SET `n_ordine` = '2', `data` = '2', `qty_in_arrivo` = '2', `giorni_alla_consegna` = '2', `fk_utente` = '2', `prodotto_sku` = '2', `fk_cliente` = '2', `fk_fornitore` = '2', `id` = '2' WHERE (`idordine` = '10')
        String query = "UPDATE "+this.TABLE_NAME+" SET `data` = '2', `qty_in_arrivo` = '2', `giorni_alla_consegna` = '2', `fk_utente` = '2', `prodotto_sku` = '2', `fk_cliente` = '2', `fk_fornitore` = '2', `id` = '2' WHERE (`idordine` = '10')";
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
