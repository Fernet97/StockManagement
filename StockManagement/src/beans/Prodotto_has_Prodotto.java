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
public class Prodotto_has_Prodotto {
   String sku_prodotto;
   String sku_componente;
   String qty;

    public Prodotto_has_Prodotto(String sku_prodotto, String sku_componente, String qty) {
        this.sku_prodotto = sku_prodotto;
        this.sku_componente = sku_componente;
        this.qty = qty;
    }

    public String getSku_prodotto() {
        return sku_prodotto;
    }

    public void setSku_prodotto(String sku_prodotto) {
        this.sku_prodotto = sku_prodotto;
    }

    public String getSku_componente() {
        return sku_componente;
    }

    public void setSku_componente(String sku_componente) {
        this.sku_componente = sku_componente;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
 
   
}