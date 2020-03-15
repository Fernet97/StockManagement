/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.UtenteDAO;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LittleJoke
 */
public class Utente {

    private static int code = 0;
    private String idutente;
    private String datareg;
    private String fullname;
    private String CF;
    private String indirizzo;
    private String telefono;
    private String email;
    private String pwd;
    private int permessi;
    private String note;
    public static final String tipo = "Utente";

    //contructor
//costruttore con utente
    /**
     * costruttore con id usato in genere per l'update Necessita di id perche
     * serve alla ricerca del codice univoco di identificazione **end**
     */
    public Utente(String idutente, String datareg, String fullname, String CF, String indirizzo, String telefono, String email, String pwd, int permessi, String note) {

        this.datareg = datareg;
        this.fullname = fullname;
        this.CF = CF;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.email = email;
        this.pwd = pwd;
        this.permessi = permessi;
        this.note = note;

        setDatareg(generateData());



    }

    /**
     * costruttore senza id usato per l'add add non necessita di id perche
     * autogenera il codice id univoco **end**
     */
    public Utente(String fullname, String CF, String indirizzo, String telefono, String email, String pwd, int permessi, String note) {

//        this.datareg = datareg;;
        this.fullname = fullname;
        System.out.println("fullname" + getFullname());
        this.CF = CF;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.email = email;
        this.pwd = pwd;
        this.permessi = permessi;
        this.note = note;

        setDatareg(generateData());
        setCode(leggiUltimoID() + 1);
        setIdutente(generateID());
        System.out.println("id utente nuovo " + getIdutente());



    }

    public Utente() {
    }

// getter & setter 
    public String getTipo() {
        return tipo;
    }

    public String getIdutente() {
        return this.idutente;
    }

    public void setIdutente(String idutente) {
        this.idutente = idutente;
    }

    public String getDatareg() {
        return datareg;
    }

    public void setDatareg(String datareg) {
        this.datareg = datareg;
    }

    public String getFullname() {
        System.out.println("diocane" + fullname);
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

    public static int getCode() {
        return code;
    }

    public static void setCode(int code) {
        Utente.code = code;
    }

    public synchronized int leggiUltimoID() {

        String tmp;
        int idlast = 0;
        String lastid;

        try {
            UtenteDAO dao = new UtenteDAO();

            lastid = dao.getLastID(this.fullname).getIdutente().toString();

            System.out.println("last id bean " + lastid);
            //v.manisera1

            // if(lastid == null) idlast = 1;
//             String numberOnly= str.replaceAll("[^0-9]", "")
            tmp = lastid.replaceAll("[^0-9]", "");
            System.out.println("tmp" + tmp);
            idlast = Integer.parseInt(tmp);
            System.out.println("ID dell'ultimo utente:" + idlast);
        } catch (SQLException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);

        } catch (NullPointerException en) {

            return idlast = 0;
        }

        return idlast;
    }

    private String generateID() {
        String idgenerato = UtenteDAO.usergen + getCode();
        return idgenerato;
    }

    public synchronized String generateData() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now)); //11/11/2019 11:11
        return dtf.format(now);
    }

}
