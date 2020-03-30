/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.FornitoreDAO;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 *
 * @author LittleJoke
 */
public class Fornitore {

    private static int code = 0;
    private String idfornitore;
    private String datareg;
    private String fullname;
    private String p_iva;
    private String indirizzo;
    private String tel;
    private String email;
    private String note;
    public static final String tipo = "Fornitore";

    /**
     *
     * @param idfornitore
     * @param datareg
     * @param fullname
     * @param p_iva
     * @param indirizzo
     * @param tel
     * @param email
     * @param note
     */
    public Fornitore(String idfornitore, String datareg, String fullname, String p_iva, String indirizzo, String tel, String email, String note) throws InterruptedException {

        this.datareg = datareg;
        this.fullname = fullname;
        this.p_iva = p_iva;
        this.indirizzo = indirizzo;
        this.tel = tel;
        this.email = email;
        this.note = note;
        this.idfornitore = idfornitore;
        setDatareg(generateData());

    }

    /**
     *
     *
     * @param fullname
     * @param p_iva
     * @param indirizzo
     * @param tel
     * @param email
     * @param note
     */
    public Fornitore(String fullname, String p_iva, String indirizzo, String tel, String email, String note) throws InterruptedException {
        this.fullname = fullname;
        this.p_iva = p_iva;
        this.indirizzo = indirizzo;
        this.tel = tel;
        this.email = email;
        this.note = note;

        setCode(leggiUltimoID() + 1);
        setIdfornitore(generateID());
        setDatareg(generateData());

    }

    public Fornitore() {

    }

    public String getTipo() {
        return tipo;
    }

    // getter & setter
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
        Fornitore.code = code;
    }

    private synchronized int leggiUltimoID() {
        String tmp;
        int idlast;

        try {
            FornitoreDAO dao = new FornitoreDAO();

            String lastid = dao.getLastID().getIdfornitore();
            //FR-000
            if (lastid == null) {
                return 0;
            }

            tmp = lastid.substring(3);
            idlast = Integer.parseInt(tmp);

        } catch (SQLException ex) {
            idlast = -99999;
            Logger.getLogger("genlog").warning("SQLException\n" + ex);
            ex.printStackTrace();

        }

        return idlast;

    }

    public synchronized String generateData() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    private String generateID() throws InterruptedException {

        String idgenerato = "FR-" + getCode();
//        Thread.sleep(1000);
        return idgenerato;
    }

    @Override
    public String toString() {
        return "Fornitore{" + "idfornitore=" + idfornitore + ", datareg=" + datareg + ", fullname=" + fullname + ", p_iva=" + p_iva + ", indirizzo=" + indirizzo + ", tel=" + tel + ", email=" + email + ", note=" + note + '}';
    }

}
