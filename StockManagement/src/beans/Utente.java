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
   
   private static int code = 0 ;
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
//costruttore con utente
   
       /** 
     * costruttore con id
     * usato in genere per l'update
     * Necessita di id perche serve alla ricerca del codice univoco di identificazione
     * **end**
     */
    public Utente(String id, String fullname, String CF, String indirizzo, String telefono, String email, String pwd, int permessi, String note) {
      
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
           setId(generateID());
           System.out.println("id utente nuovo "+getId());
    }

    public Utente() { }

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
    
    private String generateID(){
         String idgenerato = "ut-"+getCode();
      return idgenerato;
    }
    
    private int leggiUltimoID(){
          
             String tmp;
        int idlast;

        try {
            UtenteDAO dao = new UtenteDAO();
            
            String lastid = dao.getLastID().getId();
            //ut-000
            if(lastid == null) return 0;
            
          
            tmp = lastid.substring(3);
            idlast= Integer.parseInt(tmp);
            System.out.println("ID dell'ultimo fornitore:"+idlast);
    }
                    
         catch (SQLException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
            idlast = -99999;
         }
    
        return idlast;
    }
    
}
