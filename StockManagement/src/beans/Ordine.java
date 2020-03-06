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
    
    
    
    
}
