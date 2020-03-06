/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;
import dao.UtenteDAO;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import static others.Passwordgen.generateRandomPassword;

/**
 *
 * @author LittleJoke
 */
public class Utente {
   
   private static int code = 0 ;
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


   //contructor
//costruttore con utente
   
       /** 
     * costruttore con id
     * usato in genere per l'update
     * Necessita di id perche serve alla ricerca del codice univoco di identificazione
     * **end**
     */
    public Utente(String idutente, String fullname, String CF, String indirizzo, String telefono, String email, String pwd, int permessi, String note) {
      
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
     * costruttore senza id
     * usato per l'add
     * add non necessita di id perche
     * autogenera il codice id univoco
     * **end**
     */
     public Utente( String fullname, String CF, String indirizzo, String telefono, String email, String pwd, int permessi, String note) {
        
         setCode(leggiUltimoID() +1);
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
           setIdutente(generateID());
           System.out.println("id utente nuovo "+getIdutente());
    }

    public Utente() { }

// getter & setter 

    public String getIdutente() {
        return idutente;
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
    
    
    public String generateData(){
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	System.out.println(dtf.format(now)); //11/11/2019 11:11
        return dtf.format(now);
    }
    
     public String usergen(){
    
            String[] name = getFullname().split(" ");
            name[0] = name[0].substring(0, 1) + "."; 
            return name[0]+name[1];
    }
    
    private String generateID(){
         String idgenerato = usergen() +getCode();
      return idgenerato;
    }
    
    private int leggiUltimoID(){
          
             String tmp;
        int idlast;

        try {
            UtenteDAO dao = new UtenteDAO();
            
            String lastid = dao.getLastID().getIdutente();
            //v.manisera1
            if(lastid == null) return 0;
            
//             String numberOnly= str.replaceAll("[^0-9]", "")
            tmp = lastid.replaceAll("[^0-9]", "");
            idlast= Integer.parseInt(tmp);
            System.out.println("ID dell'ultimo utente:"+idlast);
    }
                    
         catch (SQLException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
            idlast = -99999;
         }
    
        return idlast;
    }
    
   
    
}
