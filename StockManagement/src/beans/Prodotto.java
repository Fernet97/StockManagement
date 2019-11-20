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
public class Prodotto {
    
    private String sku;
    private String datareg;
    private String nome;
    private String categoria;
    private boolean instock;
    private String descrizione;
    private int giorni_alla_consegna;
    private int qty;
    private float costo;
    private String foto;
    
    
    //constructor

    public Prodotto(String sku, String datareg, String nome, String categoria, boolean instock, String descrizione, int giorni_alla_consegna, int qty, float costo, String foto) {
        this.sku = sku;
        this.datareg = datareg;
        this.nome = nome;
        this.categoria = categoria;
        this.instock = instock;
        this.descrizione = descrizione;
        this.giorni_alla_consegna = giorni_alla_consegna;
        this.qty = qty;
        this.costo = costo;
        this.foto = foto;
    }
    
    public Prodotto(){}
    
    
    //getter & setter

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDatareg() {
        return datareg;
    }

    public void setDatareg(String datareg) {
        this.datareg = datareg;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isInstock() {
        return instock;
    }

    public void setInstock(boolean instock) {
        this.instock = instock;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getGiorni_alla_consegna() {
        return giorni_alla_consegna;
    }

    public void setGiorni_alla_consegna(int giorni_alla_consegna) {
        this.giorni_alla_consegna = giorni_alla_consegna;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    
}
