/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Preleva;
import database.DriverManagerConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 *
 * @author LittleJoke
 */
public class PrelevaDAO {

    private final String TABLE_NAME = "preleva";

    public synchronized Collection<Preleva> getAll() throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        Collection<Preleva> prelievi = new LinkedList<Preleva>();

        String selectSQL = "select* from " + this.TABLE_NAME + " where prodotto_sku != 'VOID'";
        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Preleva bean = new Preleva();
                bean.setN_ordine(rs.getString("n_ordine"));
                bean.setData(rs.getString("data"));
                bean.setQty(rs.getInt("qnty"));
                bean.setFk_utente(rs.getString("fk_utente"));
                bean.setProdotto_sku(rs.getString("prodotto_sku"));

                prelievi.add(bean);

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
        return prelievi;

    }

    public synchronized Collection<Preleva> getByNum(String o) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        Collection<Preleva> Prelievo = new LinkedList<Preleva>();
        //SELECT * FROM prodotti WHERE sku = '1';

        String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE n_ordine = '" + o + "' and prodotto_sku != 'VOID'";// void identifica le note

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Preleva bean = new Preleva();
                bean.setN_ordine(rs.getString("n_ordine"));
                bean.setData(rs.getString("data"));
                bean.setQty(rs.getInt("qnty"));
                bean.setFk_utente(rs.getString("fk_utente"));
                bean.setProdotto_sku(rs.getString("prodotto_sku"));

                Prelievo.add(bean);
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
        return Prelievo;
    }

    public synchronized void add(Preleva o) throws SQLException { // testato funzionante
        Connection connection = null;
        Statement statement = null;
        
        
        String query= "INSERT INTO `"+this.TABLE_NAME+"` ( `n_ordine`, `data`, `qnty`, `fk_utente`, `prodotto_sku`, `id`)"
        +"VALUES ('"+o.getN_ordine()+"', '"+o.getData()+"', '"+o.getQty()+"', '"+o.getFk_utente()+"', '"
                +o.getProdotto_sku()+"', '"+o.getCode()+"')";


//        String query = "INSERT INTO " + this.TABLE_NAME + " (`n_ordine`, `data`, `qty`, "
//                + "`fk_utente`, `prodotto_sku`, `id`) "
//                + "VALUES ('" + o.getN_ordine() + "', '" + o.getData() + "', '"
//                + "" + o.getQty() + "', ',"
//                + " '" + o.getFk_utente() + "', '" + o.getProdotto_sku() 
//                + " '', '" + o.getCode() + "')";

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




    public synchronized void removeOrd(String n_ordine) throws SQLException {// solo all admin
        Connection connection = null;
        Statement statement = null;
        String query = "DELETE FROM " + this.TABLE_NAME + " WHERE  (`n_ordine` = '" + n_ordine + "');";

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

    public synchronized Preleva getLastPreleva() throws SQLException {

        Connection connection = null;
        Statement ps = null;
        Preleva bean = new Preleva();

        String query = "select* from " + this.TABLE_NAME + " order by id DESC LIMIT 1";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {

                bean.setN_ordine(rs.getString("n_ordine"));
                bean.setData(rs.getString("data"));
                bean.setQty(rs.getInt("qnty"));
                bean.setFk_utente(rs.getString("fk_utente"));
                bean.setProdotto_sku(rs.getString("prodotto_sku"));
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

   
    public synchronized ArrayList<ArrayList<String>> groupByPrelievi() throws SQLException { //serve per la jtable del riepilogo

        Connection connection = null;
        PreparedStatement ps = null;

        ArrayList<ArrayList<String>> ordini = new ArrayList<>(5);

        String selectSQL = "select n_ordine ,data , sum(qnty) , sum(qnty * costo), fk_utente from db_stock.preleva, "
                + " db_stock.prodotto where prodotto_sku = sku group by n_ordine"; 

        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ArrayList<String> record = new ArrayList<String>();
                record.add(rs.getString("n_ordine"));
                record.add(rs.getString("sum(qnty)"));
                record.add(rs.getString("sum(qnty * costo)"));
                record.add(rs.getString("data"));
                record.add(rs.getString("fk_utente"));

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


    
    public synchronized void addNote(Preleva o) throws SQLException { // da testare
        Connection connection = null;
        Statement statement = null;
        Preleva bean = new Preleva();
      //  INSERT INTO `db_stock`.`preleva` ( `n_ordine`, `data`, `qnty`, `fk_utente`, `prodotto_sku`, `note`, `id`) VALUES ('PRE-2', 'ORA', '0', 'admin', 'VOID', 'MIIIIAAAOAAOAO', '2');

//INSERT INTO `db_stock`.`preleva` (`n_ordine`, `data`, `qnty`, `fk_utente`, `prodotto_sku`, `note`, `id`) VALUES ('a', 'data', '0', 'admin', 'VOID', 'NOTE', 'ID');
         String query = "INSERT INTO " + this.TABLE_NAME + "(`n_ordine`, `data`, `qnty`, `fk_utente`, `prodotto_sku`, `note`, `id`)"
                + " VALUES ('" + o.getN_ordine() + "', '" + bean.generateData() + "', '0', '"+ o.getFk_utente()+"', 'VOID', "
                 + " '"+ o.getNote() +"', '" +  getId(o.getN_ordine()) + "')";
         
         

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
    
    
// da verificare
      public synchronized void updateNote(String note, String utente, String n_ordine) throws SQLException { //in p c'è il prodotto già modificato (SKUVECCHIO,  parametri nuovi)
        Connection connection = null;
        Statement statement = null;
         Preleva bean = new Preleva();

         String query = "UPDATE " + this.TABLE_NAME + " SET `data` = '"+bean.generateData()+"', "
                 + "`note` = '"+note+"', `fk_utente` = '"+utente+"' "
                 + "WHERE (`n_ordine` = '"+n_ordine+"') and (`prodotto_sku` = 'VOID')";

      //UPDATE `db_stock`.`preleva` SET `data` = 'dataaa', `fk_utente` = 'adminna', `note` = 'NOTEeee' WHERE (`n_ordine` = 'a') and (`prodotto_sku` = 'VOID');
//nuova

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

    public synchronized String getNote(String ord) throws SQLException { // da definire (query OK)

        Connection connection = null;
        PreparedStatement ps = null;

        String note = "";

        String sql = "select note from "+this.TABLE_NAME+" where n_ordine = '" + ord + "'and prodotto_sku = 'VOID'";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                note = rs.getString("note");
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
                return note;

            }

        }
    }
    
    
      public synchronized int getId(String ord) throws SQLException { // da definire (query OK)

        Connection connection = null;
        PreparedStatement ps = null;

        int id =0;

        String sql = "select id from "+this.TABLE_NAME+" where n_ordine = '" + ord + "' order by id desc limit 1";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
                return id;

            }

        }
      }
        
       public synchronized void removeNote(String n_ordine) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        String query = "DELETE FROM " + this.TABLE_NAME + " WHERE  `n_ordine` = '" + n_ordine + "' and prodotto_sku = 'VOID'";

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
    

}// chiude la classe

