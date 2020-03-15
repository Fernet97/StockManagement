/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author LittleJoke
 */
public class Ordine {
    
    private int idordine;
    private String n_ordine;
    private String data;
    private int qty_in_arrivo;
    private int giorni_alla_consegna;
    private String fk_utente;
    private String prodotto_sku;
    private int fk_cliente;
    private String fk_fornitore;
    String tipo;
    
/**
 * 
 * @param idordine
 * @param n_ordine
 * @param data
 * @param qty_in_arrivo
 * @param giorni_alla_consegna
 * @param fk_utente
 * @param prodotto_sku
 * @param fk_cliente
 * @param fk_fornitore 
 */
    public Ordine(int idordine, String n_ordine, String data, int qty_in_arrivo, int giorni_alla_consegna, String fk_utente, String prodotto_sku, int fk_cliente, String fk_fornitore) {
        this.idordine = idordine;
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
     * @param qty_in_arrivo
     * @param giorni_alla_consegna
     * @param prodotto_sku
     * @param fk_cliente
     * @param fk_fornitore 
     */
    public Ordine(int qty_in_arrivo, int giorni_alla_consegna, String prodotto_sku, int fk_cliente, String fk_fornitore) {
        this.qty_in_arrivo = qty_in_arrivo;
        this.giorni_alla_consegna = giorni_alla_consegna;
        this.prodotto_sku = prodotto_sku;
        this.fk_cliente = fk_cliente;
        this.fk_fornitore = fk_fornitore;
    }

    public Ordine() {
    }
    
    

    public int getIdordine() {
        return idordine;
    }

    public void setIdordine(int idordine) {
        this.idordine = idordine;
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
    
    
    
    
}
