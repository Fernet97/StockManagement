/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

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
    private String sku;
    private String datareg;
    private String nome;
    private String categoria;
    private int qty;
    private boolean instock;
    private int giorni_alla_consegna;
    private float costo;
    private String descrizione;
    private int qty_inarrivo;
    private int qty_min;
    private String foto;
    
    
    //constructor

        public Prodotto(String sku, String datareg, String nome, String categoria, int qty, boolean instock, int giorni_alla_consegna, float costo, String descrizione, int qty_inarrivo, int qty_min, String foto) {
              
    //        setCode(leggiUltimoSku()+1);
            
        this.sku = sku;
        this.datareg = datareg;
        this.nome = nome;
        this.categoria = categoria;
        this.qty = qty;
        this.instock = instock;
        this.giorni_alla_consegna = giorni_alla_consegna;
        this.costo = costo;
        this.descrizione = descrizione;
        this.qty_inarrivo = qty_inarrivo;
        this.qty_min = qty_min;
        this.foto = foto;
        
      //  setDatareg(generateData());
        //setSku(generateSKU());
    }
        
        
            public Prodotto(){}
 //getter&setter
        
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public boolean isInstock() {
        return instock;
    }

    public void setInstock(boolean instock) {
        this.instock = instock;
    }

    public int getGiorni_alla_consegna() {
        return giorni_alla_consegna;
    }

    public void setGiorni_alla_consegna(int giorni_alla_consegna) {
        this.giorni_alla_consegna = giorni_alla_consegna;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getQty_inarrivo() {
        return qty_inarrivo;
    }

    public void setQty_inarrivo(int qty_inarrivo) {
        this.qty_inarrivo = qty_inarrivo;
    }

    public int getQty_min() {
        return qty_min;
    }

    public void setQty_min(int qty_min) {
        this.qty_min = qty_min;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    
    public String generateData() {
       
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	System.out.println(dtf.format(now)); //11/11/2019 11:11
        return dtf.format(now);
     
     }
     
    /* start here 
    public int getCode(){
        return code;    
    }
    
    public void setCode(int c){
        code = c;
    }
     
    
    public String generateSKU(){
        
        String skuGenerato = getCategoria().substring(0, 2);
        skuGenerato += getCode()+"-";
        skuGenerato += getDatareg();
        
        
       return skuGenerato;
        
    }

    public int leggiUltimoSku(){
        
        String tmp;
        int idlast;

        try {
            ProdottoDAO dao = new ProdottoDAO();
            
            String ultimosku = dao.getLastProdotto().getSku();
            //String ultimosku = "DI2467-2019/11/29 17:34:21";
            if(ultimosku == null) return 0;
            
            int index;
            index = ultimosku.indexOf("-");
            tmp = ultimosku.substring(2, index);
            idlast= Integer.parseInt(tmp);
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Prodotto.class.getName()).log(Level.SEVERE, null, ex);
            idlast = -99999;
        }
        
        return  idlast;

    }

   end here */
    
    /*
        
        //costruttore
    public Prodotto(String nome, String categoria, boolean instock, String descrizione, int giorni_alla_consegna, int qty, int qty_min,  float costo, String foto) {
        
        setCode(leggiUltimoSku()+1);
        
        this.datareg = datareg;
        this.nome = nome;
        this.categoria = categoria;
        this.instock = instock;
        this.descrizione = descrizione;
        this.giorni_alla_consegna = giorni_alla_consegna;
        this.qty = qty;
        this.qty_min = qty_min; 
        this.costo = costo;
        this.foto = foto;
        setDataReg(generateData());
        setSku(generateSKU());
    }
    
    public Prodotto(){}
    
    
    //getter & setter

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDataReg() {
        return datareg;
    }

    public void setDataReg(String datareg) {
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

    public boolean isInStock() {
        return instock;
    }

    public void setInStock(boolean instock) {
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

      public int getQty_min() {
        return qty_min;
    }

    public void setQty_min(int qty_min) {
        this.qty_min = qty_min;
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
    
    
    public String generateData() {
       
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	System.out.println(dtf.format(now)); //11/11/2019 11:11
        return dtf.format(now);
     
     }
     
    
    public int getCode(){
        return code;    
    }
    
    public void setCode(int c){
        code = c;
    }
     
    
    public String generateSKU(){
        
        String skuGenerato = getCategoria().substring(0, 2);
        skuGenerato += getCode()+"-";
        skuGenerato += getDataReg();
        
        
       return skuGenerato;
        
    }

    public int leggiUltimoSku(){
        
        String tmp;
        int idlast;

        try {
            ProdottoDAO dao = new ProdottoDAO();
            
            String ultimosku = dao.getLastProdotto().getSku();
            //String ultimosku = "DI2467-2019/11/29 17:34:21";
            if(ultimosku == null) return 0;
            
            int index;
            index = ultimosku.indexOf("-");
            tmp = ultimosku.substring(2, index);
            idlast= Integer.parseInt(tmp);
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Prodotto.class.getName()).log(Level.SEVERE, null, ex);
            idlast = -99999;
        }
        
        return  idlast;

    }
        
    */

       
}
