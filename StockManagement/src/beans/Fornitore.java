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
    
    private String idfornitore;
    private String datareg;
    private String fullname;   
    private String p_iva;
    private String indirizzo;
    private String tel;
    private String email;
    private String desc;
    
    //constructor

    public Fornitore(String idfornitore, String datareg, String fullname, String p_iva, String indirizzo, String tel, String email, String desc) {
        this.idfornitore = idfornitore;
        this.datareg = datareg;
        this.fullname = fullname;
        this.p_iva = p_iva;
        this.indirizzo = indirizzo;
        this.tel = tel;
        this.email = email;
        this.desc = desc;
    }

    public Fornitore() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  //getter & setter

    public String getIdfornitore() {
        return idfornitore;
    }

    public void setIdfornitore(String idfornitore) {
        this.idfornitore = idfornitore;
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

    public String getP_iva() {
        return p_iva;
    }

    public void setP_iva(String p_iva) {
        this.p_iva = p_iva;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    

    
}
