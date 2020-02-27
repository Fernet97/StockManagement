/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

//import dao.ProdottoDAO;  
import dao.ProdottoDAO;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LittleJoke
 */
public class Prodotto {
   
    private static int code;
    String sku;
    String datareg;
    String nome;
    int qty;
    String Categoria;
    boolean instock;
    float costo;
    int qty_min;
    String note;
    String foto;
    boolean negozio;
    

    // costruttore completo
    public Prodotto(String sku, String datareg, String nome, int qty, String Categoria, boolean instock, float costo, int qty_min, String note, String foto, boolean negozio) {
        this.sku = sku;
        this.datareg = datareg;
        this.nome = nome;
        this.qty = qty;
        this.Categoria = Categoria;
        this.instock = instock;
        this.costo = costo;
        this.qty_min = qty_min;
        this.note = note;
        this.foto = foto;
        this.negozio = negozio;
        setDatareg(generateData());

    }

    //costruttore senza id utente
    public Prodotto(String datareg, String nome, int qty, String Categoria, boolean instock, float costo, int qty_min, String note, String foto, boolean negozio) {

        setCode(leggiUltimoSku() + 1);
        setDatareg(generateData());
        setSku(generateSKU());
        this.datareg = datareg;
        this.nome = nome;
        this.qty = qty;
        this.Categoria = Categoria;
        this.instock = instock;
        this.costo = costo;
        this.qty_min = qty_min;
        this.note = note;
        this.foto = foto;
        this.negozio = negozio;
    }

    // getter & setter senza code
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public boolean isInstock() {
        return instock;
    }

    public void setInstock(boolean instock) {
        this.instock = instock;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public int getQty_min() {
        return qty_min;
    }

    public void setQty_min(int qty_min) {
        this.qty_min = qty_min;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public boolean isNegozio() {
        return negozio;
    }

    public void setNegozio(boolean negozio) {
        this.negozio = negozio;
    }

    public String generateData() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now)); //11/11/2019 11:11
        return dtf.format(now);

    }

    public int getCode() {
        return code;
    }

    public void setCode(int c) {
        code = c;
    }

    public String generateSKU() {
        System.out.println("Categoria del nuovo prodotto: " + getCategoria());
        String skuGenerato = getCategoria().substring(0, 2);
        skuGenerato += getCode() + "-";
        skuGenerato += getDatareg();

        return skuGenerato;

    }

    public int leggiUltimoSku() {

        String tmp;
        int idlast;

        try {
            ProdottoDAO dao = new ProdottoDAO();

            String ultimosku = dao.getLastProdotto().getSku();
            //String ultimosku = "DI2467-2019/11/29 17:34:21";
            if (ultimosku == null) {
                return 0;
            }

            int index;
            index = ultimosku.indexOf("-");
            tmp = ultimosku.substring(2, index);
            idlast = Integer.parseInt(tmp);

        } catch (SQLException ex) {
            Logger.getLogger(Prodotto.class.getName()).log(Level.SEVERE, null, ex);
            idlast = -99999;
        }

        return idlast;

    }
}

