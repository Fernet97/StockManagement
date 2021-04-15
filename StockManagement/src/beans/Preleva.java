/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.PrelevaDAO;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import ui.StockManagement;

/**
 *
 * @author LittleJoke
 */
public class Preleva {

    private static int code = 0;
    private String n_ordine;
    private static String data = " ";
    private int qty; //in db si chiama "qnty"
    private String fk_utente;
    private String prodotto_sku;
    private String note;


    /**
     * 
     * @param n_ordine
     * @param data
     * @param qty
     * @param fk_utente
     * @param prodotto_sku
     * @throws InterruptedException 
     */
    public Preleva(String n_ordine, String data, int qty,  String fk_utente, String prodotto_sku) throws InterruptedException {

        this.n_ordine = n_ordine;
        this.data = data;
        this.qty = qty;
       
        this.fk_utente = fk_utente;
        this.prodotto_sku = prodotto_sku;

    }


    /**
     * per update
     * @param qty
     * @param fk_utente
     * @param prodotto_sku
     * @throws InterruptedException 
     */
    public Preleva(int qty, String fk_utente, String prodotto_sku) throws InterruptedException {

        setN_ordine(generateID());
        setData(generateData());
        setCode(this.code);
        this.qty = qty;
        this.prodotto_sku = prodotto_sku;
        this.fk_utente = fk_utente;
    }

/**
 * serve per settare le note
 * @param n_ordine
 * @param note
 * @param fk_utente
 * @throws InterruptedException 
 */
     public Preleva(String n_ordine, String note, String fk_utente) throws InterruptedException {
        setCode(this.code);
        setData(generateData());
        this.n_ordine = n_ordine;
        this.note = note;
        this.fk_utente = fk_utente;
         }

    

    public Preleva() {
    }

    public String getN_ordine() {
        return n_ordine;
    }

    public void setN_ordine(String n_ordine) {
        this.n_ordine = n_ordine;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getFk_utente() {
        return fk_utente;
    }

    public void setFk_utente(String fk_utente) {
        this.fk_utente = fk_utente;
    }

    public String getProdotto_sku() {
        return prodotto_sku;
    }

    public void setProdotto_sku(String prodotto_sku) {
        this.prodotto_sku = prodotto_sku;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    public static int getCode() {
        return code;
    }

    public static void setCode(int code) {
        Preleva.code = code;
    }

//    public String getNote() {
//        return note;
//    }
//
//    public void setNote(String note) {
//        this.note = note;
//    }
    
    

    public synchronized int leggiUltimoID() {

        int lastid;

        try {
            PrelevaDAO dao = new PrelevaDAO();

            lastid = dao.getLastPreleva().getCode();
        

        } catch (SQLException ex) {
            lastid = -99999;
            Logger.getLogger("genlog").warning("SQLException\n" + StockManagement.printStackTrace(ex));
            
        }

        return lastid;

    }
    

    


    public synchronized String generateData() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public String generateID() throws InterruptedException {

        String idgenerato = "PRE-" + getCode();
//        Thread.sleep(1000);
        return idgenerato;
    }

    public void startOrdine() throws InterruptedException {

        setCode(leggiUltimoID() + 1);
        setN_ordine(generateID());
        setData(generateData());
    }
    
    


    @Override
    public String toString() {
        return "Preleva{" + "n_ordine=" + n_ordine + ", data=" + data + ", qty=" + qty + ", fk_utente=" + fk_utente + ", prodotto_sku=" + prodotto_sku + '}';
    }

}
