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
public class Fornitore {
    
    private int idfornitori;
    private String p_iva;
    private String nome;
    private String cognome;
    private String nome_azienda;
    private String stato;
    private String tel;
    private String email;
    private String desc;
    
    //constructor

    public Fornitore(int idfornitori, String p_iva, String nome, String cognome, String nome_azienda, String stato, String tel, String email, String desc) {
        this.idfornitori = idfornitori;
        this.p_iva = p_iva;
        this.nome = nome;
        this.cognome = cognome;
        this.nome_azienda = nome_azienda;
        this.stato = stato;
        this.tel = tel;
        this.email = email;
        this.desc = desc;
    }
    
   //getter & setter

    public int getIdfornitori() {
        return idfornitori;
    }

    public void setIdfornitori(int idfornitori) {
        this.idfornitori = idfornitori;
    }

    public String getP_iva() {
        return p_iva;
    }

    public void setP_iva(String p_iva) {
        this.p_iva = p_iva;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome_azienda() {
        return nome_azienda;
    }

    public void setNome_azienda(String nome_azienda) {
        this.nome_azienda = nome_azienda;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    
    
    
}
