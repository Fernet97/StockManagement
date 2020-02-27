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
public class Cliente {
    
    private int idcliente;
    private String datareg;
    private String fullname;
    private String cf;
    private String indirizzo;
    private String tel;
    private String email;
    private String note;

    /**
     * 
     * @param idcliente
     * @param datareg
     * @param fullname
     * @param cf
     * @param indirizzo
     * @param tel
     * @param email
     * @param note 
     */
    public Cliente(int idcliente, String datareg, String fullname, String cf, String indirizzo, String tel, String email, String note) {
        this.idcliente = idcliente;
        this.datareg = datareg;
        this.fullname = fullname;
        this.cf = cf;
        this.indirizzo = indirizzo;
        this.tel = tel;
        this.email = email;
        this.note = note;
    }

    
    /**
     * per update
     * @param fullname
     * @param cf
     * @param indirizzo
     * @param tel
     * @param email
     * @param note 
     */
    public Cliente(String fullname, String cf, String indirizzo, String tel, String email, String note) {
        this.fullname = fullname;
        this.cf = cf;
        this.indirizzo = indirizzo;
        this.tel = tel;
        this.email = email;
        this.note = note;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getDatareg() {
        return datareg;
    }

    public void setDatareg(String datareg) {
        this.datareg = datareg;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    
    
}
