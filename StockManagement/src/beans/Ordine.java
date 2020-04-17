/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.OrdineDAO;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import ui.StockManagement;

/**
 *
 * @author LittleJoke
 */
public class Ordine {

    private static int code = 0;
    private static String n_ordine;
    private static String data = " ";
    private int qty_in_arrivo;
    private int qty_arrivata;
    private int giorni_alla_consegna;
    private String note;
    private String data_arrivo;
    private String fk_utente;
    private String prodotto_sku;
    private int fk_cliente;
    private String fk_fornitore;

    /**
     *
     * @param n_ordine
     * @param data
     * @param qty_in_arrivo
     * @param giorni_alla_consegna
     * @param fk_utente
     * @param prodotto_sku
     * @param fk_cliente
     * @param fk_fornitore
     * @param qty_arrivata
     */
    public Ordine(String n_ordine, String data, int qty_in_arrivo, int giorni_alla_consegna, String fk_utente, String prodotto_sku, int fk_cliente, String fk_fornitore, int qty_arrivata) throws InterruptedException {

        this.n_ordine = n_ordine;
        this.data = data;
        this.qty_in_arrivo = qty_in_arrivo;
        this.giorni_alla_consegna = giorni_alla_consegna;
        this.fk_utente = fk_utente;
        this.prodotto_sku = prodotto_sku;
        this.fk_cliente = fk_cliente;
        this.fk_fornitore = fk_fornitore;
        this.qty_arrivata = qty_arrivata;

    }

    /**
     * per update
     *
     * @param qty_in_arrivo
     * @param giorni_alla_consegna
     * @param prodotto_sku
     * @param fk_cliente
     * @param fk_fornitore
     */
    public Ordine(int qty_in_arrivo, int giorni_alla_consegna, String fk_utente, String prodotto_sku, int fk_cliente, String fk_fornitore, int qty_arrivata) throws InterruptedException {

        setN_ordine(this.n_ordine);
        setData(this.data);
        setCode(this.code);
        this.qty_in_arrivo = qty_in_arrivo;
        this.giorni_alla_consegna = giorni_alla_consegna;
        this.prodotto_sku = prodotto_sku;
        this.fk_utente = fk_utente;
        this.fk_cliente = fk_cliente;
        this.fk_fornitore = fk_fornitore;
        this.qty_arrivata = qty_arrivata;

    }

/**
 * serve per settare le note
 * @param n_ordine
 * @param data
 * @param note
 * @param fk_utente
 * @throws InterruptedException 
 */
     public Ordine(String n_ordine, String note, String fk_utente) throws InterruptedException {
        setCode(this.code);
        setData(generateData());
        this.n_ordine = n_ordine;
        this.data = data;
        this.note = note;
        this.fk_utente = fk_utente;
         
        
    }

    

    public Ordine() {
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

    public int getQty_in_arrivo() {
        return qty_in_arrivo;
    }

    public void setQty_in_arrivo(int qty_in_arrivo) {
        this.qty_in_arrivo = qty_in_arrivo;
    }

    public int getGiorni_alla_consegna() {
        return giorni_alla_consegna;
    }

    public void setGiorni_alla_consegna(int giorni_alla_consegna) {
        this.giorni_alla_consegna = giorni_alla_consegna;
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

    public int getFk_cliente() {
        return fk_cliente;
    }

    public void setFk_cliente(int fk_cliente) {
        this.fk_cliente = fk_cliente;
    }

    public String getFk_fornitore() {
        return fk_fornitore;
    }

    public void setFk_fornitore(String fk_fornitore) {
        this.fk_fornitore = fk_fornitore;
    }

    public static int getCode() {
        return code;
    }

    public static void setCode(int code) {
        Ordine.code = code;
    }

    public int getQty_arrivata() {
        return qty_arrivata;
    }

    public void setQty_arrivata(int qty_arrivata) {
        this.qty_arrivata = qty_arrivata;
    }

    public String getData_arrivo() {
        return data_arrivo;
    }

    public void setData_arrivo(String data_arrivo) {
        this.data_arrivo = data_arrivo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    

    public synchronized int leggiUltimoID() {

        int lastid;

        try {
            OrdineDAO dao = new OrdineDAO();

            lastid = dao.getLastOrdine().getCode();
        

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

        String idgenerato = "ORD-" + getCode();
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
        return "Ordine{" + "n_ordine=" + n_ordine + ", data=" + data + ", qty_in_arrivo=" + qty_in_arrivo + ", giorni_alla_consegna=" + giorni_alla_consegna + ", fk_utente=" + fk_utente + ", prodotto_sku=" + prodotto_sku + ", fk_cliente=" + fk_cliente + ", fk_fornitore=" + fk_fornitore + ", qty_arrivata=" + qty_arrivata + ", data_arrivo=" + data_arrivo +", note=" + note + '}';
    }

}
