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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LittleJoke
 */
public class Ordine {

    private static int code = 0;
    private static String n_ordine;
    private static String data = " ";
    private int qty_in_arrivo;
    private int giorni_alla_consegna;
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
     */
    public Ordine(String n_ordine, String data, int qty_in_arrivo, int giorni_alla_consegna, String fk_utente, String prodotto_sku, int fk_cliente, String fk_fornitore) throws InterruptedException {

        this.n_ordine = n_ordine;
        this.data = data;
        this.qty_in_arrivo = qty_in_arrivo;
        this.giorni_alla_consegna = giorni_alla_consegna;
        this.fk_utente = fk_utente;
        this.prodotto_sku = prodotto_sku;
        this.fk_cliente = fk_cliente;
        this.fk_fornitore = fk_fornitore;

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
    public Ordine(int qty_in_arrivo, int giorni_alla_consegna, String fk_utente, String prodotto_sku, int fk_cliente, String fk_fornitore) throws InterruptedException {

        setN_ordine(this.n_ordine);
        setData(this.data);
        setCode(this.code);
        this.qty_in_arrivo = qty_in_arrivo;
        this.giorni_alla_consegna = giorni_alla_consegna;
        this.prodotto_sku = prodotto_sku;
        this.fk_utente = fk_utente;
        this.fk_cliente = fk_cliente;
        this.fk_fornitore = fk_fornitore;

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

    public synchronized int leggiUltimoID() {
//        String tmp;
        int lastid;

        try {
            OrdineDAO dao = new OrdineDAO();

            lastid = dao.getLastOrdine().getCode();
            //ORD-000
//            if (lastid == null) {
//                return 0;
//            }
//
//            tmp = lastid.substring(4);
//            idlast = Integer.parseInt(tmp);
            System.out.println("ID dell'ultimo ordine:" + lastid);

        } catch (SQLException ex) {
            Logger.getLogger(Ordine.class.getName()).log(Level.SEVERE, null, ex);
            lastid = -99999;
        }

        return lastid;

    }

    public synchronized String generateData() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now)); //11/11/2019 11:11
        return dtf.format(now);
    }

    public String generateID() throws InterruptedException {

        String idgenerato = "ORD-" + getCode();
//        Thread.sleep(1000);
        return idgenerato;
    }

    public void startOrdine() throws InterruptedException {

//        System.out.println("data start "+getData());
        setCode(leggiUltimoID() + 1);
        setN_ordine(generateID());
        setData(generateData());
    }

    @Override
    public String toString() {
        return "Ordine{" + "n_ordine=" + n_ordine + ", data=" + data + ", qty_in_arrivo=" + qty_in_arrivo + ", giorni_alla_consegna=" + giorni_alla_consegna + ", fk_utente=" + fk_utente + ", prodotto_sku=" + prodotto_sku + ", fk_cliente=" + fk_cliente + ", fk_fornitore=" + fk_fornitore + '}';
    }

}
