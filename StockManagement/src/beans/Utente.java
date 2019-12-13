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
public class Utente {
   
   private String id; 
   private String datareg;
   private String fullname; 
   private String CF;
   private String indirizzo; 
   private String telefono; 
   private String email; 
   private String pwd; 
   private int permessi; 
   private String note; 


   //contructor

    public Utente(String id, String datareg, String fullname, String CF, String indirizzo, String telefono, String email, String pwd, int permessi, String note) {
        this.id = id;
        this.datareg = datareg;
        this.fullname = fullname;
        this.CF = CF;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.email = email;
        this.pwd = pwd;
        this.permessi = permessi;
        this.note = note;
    }

// getter & setter 

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCF() {
        return CF;
    }

    public void setCF(String CF) {
        this.CF = CF;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getPermessi() {
        return permessi;
    }

    public void setPermessi(int permessi) {
        this.permessi = permessi;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
}
