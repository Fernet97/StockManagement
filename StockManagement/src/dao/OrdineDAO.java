/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Ordine;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.dateTime;
import database.DriverManagerConnectionPool;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Hashtable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.time.temporal.TemporalAccessor;


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
    public synchronized void updateGG(Ordine o) throws SQLException { //in p c'è il prodotto già modificato (SKUVECCHIO,  parametri nuovi)
        Connection connection = null;
        Statement statement = null;

        System.out.println("sku del ordine da modificare: " + o.getN_ordine());
//UPDATE `db_stock`.`ordine` SET `n_ordine` = '2', `data` = '2', `qty_in_arrivo` = '2', `giorni_alla_consegna` = '2', `fk_utente` = '2', `prodotto_sku` = '2', `fk_cliente` = '2', `fk_fornitore` = '2', `id` = '2' WHERE (`idordine` = '10')
        String query = "UPDATE " + this.TABLE_NAME + " SET  `giorni_alla_consegna` = '-1',  WHERE (`n_ordine` = '"+o.getN_ordine()+"' AND `prodotto_sku` = '"+o.getProdotto_sku()+"' )";
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

        ArrayList<ArrayList<String>> ordini = new ArrayList<>(4);
        
        String selectSQL = "select n_ordine ,data , sum(qty_in_arrivo), sum(costo) from db_stock.ordine,  db_stock.prodotto where prodotto_sku = sku group by n_ordine";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ArrayList<String> record = new ArrayList<String>();
                record.add(rs.getString("n_ordine"));
                record.add(rs.getString("sum(qty_in_arrivo)"));
                record.add(rs.getString("sum(costo)"));
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
        
        
        /**
         * metodo che getta i prodotti in base al fornitore collegato
         * @param fr
         * @return
         * @throws SQLException 
         */
        // MI TORNA ANCHE DUPLICATI?
          public synchronized Collection<String> getPFr(String fr) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        

        Collection<String> ordini = new LinkedList<String>();
        //SELECT * FROM prodotti WHERE sku = '1';
        
        String selectSQL = "SELECT prodotto_sku FROM " + this.TABLE_NAME + " WHERE fk_fornitore = '"+fr+"'";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ordini.add(rs.getString("prodotto_sku"));
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
         /**
          * add metodo che getta il fornitore collegato al prodotto
          * @param pr
          * @return
          * @throws SQLException 
          */ 
        public synchronized String getFPr(String pr) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        

        String fornitore = "";
        //SELECT * FROM prodotti WHERE sku = '1';
        
        String selectSQL = "SELECT fk_fornitore FROM " + this.TABLE_NAME + " WHERE prodotto_sku = '"+pr+"'";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                fornitore = rs.getString("fk_fornitore");
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
        return fornitore;
    
        }
        
        
        /**
         * returna 0 se non é ancora il giorno della consegna rosso
         * returna 2 se il giorno della consegna e uguale o maggiore alla data attuale giallo
         * returna 3 se il valore in db di gg alla consegna é uguale a -1
         * returna -1 errore
         * @param o
         * @return
         * @throws SQLException
         * @throws ParseException 
         */
        public synchronized int isArrivato(String o) throws SQLException, ParseException  {
        
            Connection connection = null;
            PreparedStatement ps = null;

            Ordine bean =new Ordine();

            String sql = "select n_ordine ,data , giorni_alla_consegna from ordine where n_ordine = '"+o+"' order by giorni_alla_consegna desc limit 1";
            
             try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

           while (rs.next()) {
                bean.setN_ordine(rs.getString("n_ordine"));
                bean.setData(rs.getString("data"));
                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                
             }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            //get data attuale
            String [] nows= bean.generateData().split(" ");
            LocalDate now = LocalDate.parse(nows[0], formatter);
            
            //get data ordine
            String [] dbs= bean.getData().split(" ");
            LocalDate db = LocalDate.parse(dbs[0], formatter);
            
            // get data della consegna
            LocalDate consegna = db.plusDays(bean.getGiorni_alla_consegna());
            
             System.out.println("now data "+ now.format(formatter)+ " db data "+db.format(formatter)+ " consegna "+ consegna.format(formatter));
             
             if (bean.getGiorni_alla_consegna() == -1 ){
                return  3;//verde
            }else if (now.format(formatter).equals(consegna.format(formatter))){
                return 2; //giallo
            }else if (now.isAfter(consegna)){
                return  2; //giallo
            }else if (now.isBefore(consegna)){
                return  0; //rosso
            }
            return -1;
            
//             now.format(nowf); questo mi serve per far apparire la data come stringa per confrontarla con db.format(dbf);
            }
        }
        
        /**
         * returna il numero di giorni che mancano alla consegna dell ordine
         * @param o
         * @return
         * @throws SQLException
         * @throws ParseException 
         */
        public synchronized int[] ggConsegnaPR(String o, String p) throws SQLException, ParseException  { // da definire (query OK)
            
             int[] array = new int [15];//valore da definire         
            Connection connection = null;
            PreparedStatement ps = null;

            Ordine bean =new Ordine();

            String sql = "select distinct n_ordine ,data , giorni_alla_consegna, prodotto_sku from "+this.TABLE_NAME+" "
                    + "where n_ordine = '"+o+"'  and prodotto_sku = '"+p+"' order by giorni_alla_consegna";
            
             try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            int i =0;
           while (rs.next()) {
               
                bean.setN_ordine(rs.getString("n_ordine"));
                bean.setData(rs.getString("data"));
                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                bean.setProdotto_sku(rs.getString("prodotto_sku"));
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            //get data ordine
            String [] dbs= bean.getData().split(" ");
            LocalDate db = LocalDate.parse(dbs[0], formatter);
            
            //get data attuale
            String [] nows= bean.generateData().split(" ");
            LocalDate now = LocalDate.parse(nows[0], formatter);
            
            // get giorni mancanti consegna
            LocalDate consegna = db.plusDays(bean.getGiorni_alla_consegna());
            
            int gg = (int) DAYS.between(consegna,  now);
            array [i] = gg;
System.out.println("now data "+ now.format(formatter)+ " db data "+db.format(formatter)+ " consegna "+ consegna.format(formatter) + " giorni mancanti  " + gg);
                    i++;
           }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        
                         
            return array;

             }
        
        }
        
        
                

             
}// chiude la classe

  
    
        
        

        

