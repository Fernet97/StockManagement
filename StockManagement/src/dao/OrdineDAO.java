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
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.logging.Logger;

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

        String selectSQL = "select* from " + this.TABLE_NAME + " where prodotto_sku != 'VOID'";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ordine bean = new Ordine();
                bean.setN_ordine(rs.getString("n_ordine"));
                bean.setData(rs.getString("data"));
                bean.setQty_in_arrivo(rs.getInt("qty_in_arrivo"));
                bean.setQty_arrivata(rs.getInt("qty_arrivata"));
                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                bean.setData_arrivo(rs.getString("data_arrivo"));
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

        String selectSQL = "SELECT * FROM " + this.TABLE_NAME + " WHERE n_ordine = '" + o + "' and prodotto_sku != 'VOID'";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ordine bean = new Ordine();
                bean.setN_ordine(rs.getString("n_ordine"));
                bean.setData(rs.getString("data"));
                bean.setQty_in_arrivo(rs.getInt("qty_in_arrivo"));
                bean.setQty_arrivata(rs.getInt("qty_arrivata"));
                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                bean.setData_arrivo(rs.getString("data_arrivo"));
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

        String query = "INSERT INTO " + this.TABLE_NAME + " (`n_ordine`, `data`, `qty_in_arrivo`, `giorni_alla_consegna`, "
                + "`fk_utente`, `prodotto_sku`, `fk_cliente`, `fk_fornitore`, `id`) "
                + "VALUES ('" + o.getN_ordine() + "', '" + o.getData() + "', '"
                + "" + o.getQty_in_arrivo() + "', '" + o.getGiorni_alla_consegna() + "',"
                + " '" + o.getFk_utente() + "', '" + o.getProdotto_sku() + "',"
                + " '" + o.getFk_cliente() + "', '" + o.getFk_fornitore() + "', '" + o.getCode() + "')";

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

    /*
    Modifica i giorni alla consegna:
    -1 : indico che l'ordine è arrivato
    -2: indica che è arrivato e conteggiato
     */
    public synchronized void updateGG(String nordine, String sku, int gg) throws SQLException { //in p c'è il prodotto già modificato (SKUVECCHIO,  parametri nuovi)
        Connection connection = null;
        Statement statement = null;

//UPDATE `db_stock`.`ordine` SET `n_ordine` = '2', `data` = '2', `qty_in_arrivo` = '2', `giorni_alla_consegna` = '2', `fk_utente` = '2', `prodotto_sku` = '2', `fk_cliente` = '2', `fk_fornitore` = '2', `id` = '2' WHERE (`idordine` = '10')
        String query = "UPDATE " + this.TABLE_NAME + " SET  giorni_alla_consegna = '" + gg + "' "
                + " WHERE n_ordine = '" + nordine + "' AND prodotto_sku = '" + sku + "'";
        Logger.getLogger("userlog").info("ordine che si sta per modificare \nn_ordine= " + nordine + " sku= " + sku);

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

// setta la qty arrivata  per quel preciso prodotto di quell'ordine
    public synchronized void setQtyArrivata(String nordine, String sku, int qty) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        Ordine bean = new Ordine();
        String query = "UPDATE " + this.TABLE_NAME + " SET  qty_arrivata = '" + qty
                + "' , data_arrivo = '" + bean.generateData() + "'  WHERE n_ordine = '" + nordine + "' AND prodotto_sku = '" + sku + "'";
        Logger.getLogger("userlog").info("ordine che si sta per modificare \nn_ordine= " + nordine + " sku= " + sku);

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

    public synchronized void removeOrd(String n_ordine) throws SQLException {
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

        String selectSQL = "select n_ordine ,data , sum(qty_in_arrivo) , sum(qty_in_arrivo * costo), sum(qty_arrivata) from db_stock.ordine, "
                + " db_stock.prodotto where prodotto_sku = sku group by n_ordine";

        
        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ArrayList<String> record = new ArrayList<String>();
                record.add(rs.getString("n_ordine"));
                record.add(rs.getString("sum(qty_in_arrivo)"));
                record.add(rs.getString("sum(qty_in_arrivo * costo)"));
                record.add(rs.getString("data"));
                record.add(rs.getString("sum(qty_arrivata)"));

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
     *
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

        String selectSQL = "SELECT distinct prodotto_sku FROM " + this.TABLE_NAME + " WHERE fk_fornitore = '" + fr + "'";

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
     *
     * @param pr
     * @return
     * @throws SQLException
     */
    public synchronized String getFPr(String pr) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;

        String fornitore = "";
        //SELECT * FROM prodotti WHERE sku = '1';

        String selectSQL = "SELECT fk_fornitore FROM " + this.TABLE_NAME + " WHERE prodotto_sku = '" + pr + "'";

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
     * returna 0 se non é ancora il giorno della consegna rosso returna 2 se il
     * giorno della consegna e uguale o maggiore alla data attuale giallo
     * returna 3 se il valore in db di gg alla consegna é uguale a -1 returna -1
     * errore
     *
     * @param o
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public synchronized int isArrivato(String o) throws SQLException, ParseException {

        Connection connection = null;
        PreparedStatement ps = null;

        Ordine bean = new Ordine();

        String sql = "select n_ordine ,data , giorni_alla_consegna from ordine where n_ordine = '" + o + "' and prodotto_sku != 'VOID' order by giorni_alla_consegna desc limit 1";

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
            String[] nows = bean.generateData().split(" ");
            LocalDate now = LocalDate.parse(nows[0], formatter);

            //get data ordine
            String[] dbs = bean.getData().split(" ");
            LocalDate db = LocalDate.parse(dbs[0], formatter);

            // get data della consegna
            LocalDate consegna = db.plusDays(bean.getGiorni_alla_consegna());

            Logger.getLogger("userlog").info("now data " + now.format(formatter) + " db data " + db.format(formatter)
                    + " consegna " + consegna.format(formatter));

            // Se è -1 allora è arrivato, se è -2 è messo anche in stock
            if (bean.getGiorni_alla_consegna() == -2) {
                return 3;//verde
            } else if (bean.getGiorni_alla_consegna() == -1) {
                return 2;//giallo
            } else if (now.format(formatter).equals(consegna.format(formatter))) {
                return 2; //giallo
            } else if (now.isAfter(consegna)) {
                return 2; //giallo
            } else if (now.isBefore(consegna)) {
                return 0; //rosso
            }
            return -1;

//             now.format(nowf); questo mi serve per far apparire la data come stringa per confrontarla con db.format(dbf);
        }
    }

    /**
     * returna il numero di giorni che mancano alla consegna dell ordine
     *
     * @param o
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public synchronized int[] ggConsegnaPR(String o, String p) throws SQLException, ParseException { // da definire (query OK)

        int[] array = new int[15];//valore da definire         
        Connection connection = null;
        PreparedStatement ps = null;

        Ordine bean = new Ordine();

        String sql = "select distinct n_ordine ,data , giorni_alla_consegna, prodotto_sku from " + this.TABLE_NAME + " "
                + "where n_ordine = '" + o + "'  and prodotto_sku = '" + p + "' order by giorni_alla_consegna";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {

                bean.setN_ordine(rs.getString("n_ordine"));
                bean.setData(rs.getString("data"));
                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                bean.setProdotto_sku(rs.getString("prodotto_sku"));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                //get data ordine
                String[] dbs = bean.getData().split(" ");
                LocalDate db = LocalDate.parse(dbs[0], formatter);

                //get data attuale
                String[] nows = bean.generateData().split(" ");
                LocalDate now = LocalDate.parse(nows[0], formatter);

                // get giorni mancanti consegna
                LocalDate consegna = db.plusDays(bean.getGiorni_alla_consegna());

                int gg = (int) DAYS.between(consegna, now);
                array[i] = gg;
                Logger.getLogger("userlog").info("now data " + now.format(formatter) + " db data " + db.format(formatter)
                        + " consegna " + consegna.format(formatter) + " giorni mancanti  " + gg);
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

    public synchronized String dataArrivo(String o, String sku) throws SQLException, ParseException {

        Connection connection = null;
        PreparedStatement ps = null;

        Ordine bean = new Ordine();

        String sql = "select n_ordine ,data , giorni_alla_consegna from ordine where n_ordine = '" + o + "' and prodotto_sku = '" + sku + "'  "
                + "order by giorni_alla_consegna desc limit 1";

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
            String[] nows = bean.generateData().split(" ");
            LocalDate now = LocalDate.parse(nows[0], formatter);

            //get data ordine
            String[] dbs = bean.getData().split(" ");
            LocalDate db = LocalDate.parse(dbs[0], formatter);

            // get data della consegna
            LocalDate consegna = db.plusDays(bean.getGiorni_alla_consegna());

            Logger.getLogger("userlog").info("data prevista di arrivo " + consegna.format(formatter));

            return consegna.format(formatter);

        }
    }

    public synchronized int ggConsegnaPR2(String o, String p) throws SQLException, ParseException { // da definire (query OK)

        Connection connection = null;
        PreparedStatement ps = null;
        int gg = -9999;

        Ordine bean = new Ordine();

        String sql = "select distinct n_ordine ,data , giorni_alla_consegna, prodotto_sku from " + this.TABLE_NAME + " "
                + "where n_ordine = '" + o + "'  and prodotto_sku = '" + p + "'";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {

                bean.setN_ordine(rs.getString("n_ordine"));
                bean.setData(rs.getString("data"));
                bean.setGiorni_alla_consegna(rs.getInt("giorni_alla_consegna"));
                bean.setProdotto_sku(rs.getString("prodotto_sku"));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                //get data ordine
                String[] dbs = bean.getData().split(" ");
                LocalDate db = LocalDate.parse(dbs[0], formatter);

                //get data attuale
                String[] nows = bean.generateData().split(" ");
                LocalDate now = LocalDate.parse(nows[0], formatter);

                // get giorni mancanti consegna
                LocalDate consegna = db.plusDays(bean.getGiorni_alla_consegna());

                gg = (int) DAYS.between(consegna, now);
                Logger.getLogger("userlog").info("now data " + now.format(formatter) + " db data " + db.format(formatter)
                        + " consegna " + consegna.format(formatter) + " giorni mancanti  " + gg);
                i++;
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
                return gg;

            }

        }
    }

    /**
     * ritorna il numero di ordini effettuati
     *
     * @return
     * @throws SQLException
     */
    public synchronized int qtyOrdini() throws SQLException { // da definire (query OK)

        Connection connection = null;
        PreparedStatement ps = null;

        int n = 0;

        String sql = "select count(distinct n_ordine) from " + this.TABLE_NAME;

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            n = rs.getInt("count(distinct n_ordine)");

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
                return n;

            }

        }
    }

    public synchronized int getQtyArrSku(String sku) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;
        int n = 0;
        String selectSQL = "select sum(qty_in_arrivo) from " + this.TABLE_NAME + " where giorni_alla_consegna >=0 and prodotto_sku='" + sku + "'";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            ps = connection.prepareStatement(selectSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                n = rs.getInt("sum(qty_in_arrivo)");
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
        return n;

    }

    public synchronized void addNote(Ordine o) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        Ordine bean = new Ordine();

         String query = "INSERT INTO " + this.TABLE_NAME + " (`n_ordine`, `data`, `qty_in_arrivo`, `qty_arrivata`, "
                + "`giorni_alla_consegna`, `data_arrivo`, `note`, `fk_utente`, `prodotto_sku`, `fk_cliente`, `id`)"
                + " VALUES ('" + o.getN_ordine() + "', '" + bean.generateData() + "', '0', '0', '0', '0', '" + o.getNote() + "',"
                + " '" + o.getFk_utente() + "', 'VOID', '0', '" +  getId(o.getN_ordine()) + "')";

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
    
    
    //UPDATE `db_stock`.`ordine` SET `data` = '19/04/2020 17:41:00', `note` = 'miao', `fk_utente` = 'gatto silvestro' WHERE (`id` = 'x') and (`prodotto_sku` = 'VOID');

      public synchronized void updateNote(Ordine o) throws SQLException { //in p c'è il prodotto già modificato (SKUVECCHIO,  parametri nuovi)
        Connection connection = null;
        Statement statement = null;
         Ordine bean = new Ordine();

         String query = "UPDATE " + this.TABLE_NAME + " SET data` = '"+bean.generateData()+"', "
                 + "`note` = '"+o.getNote()+"', `fk_utente` = '"+o.getFk_utente()+"' "
                 + "WHERE (`id` = '"+getId(o.getN_ordine()) +"') and (`prodotto_sku` = 'VOID')";

      

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

        String sql = "select note from ordine where n_ordine = '" + ord + "'and prodotto_sku = 'VOID'";

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

        String sql = "select id from ordine where n_ordine = '" + ord + "' order by id desc limit 1";

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

