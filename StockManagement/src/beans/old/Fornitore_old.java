/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.FornitoreDAO_old;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LittleJoke
 */
public class Fornitore_old {


    private static int code = 0 ;
    private String idfornitore;
    private String datareg;
    private String fullname;   
    private String p_iva;
    private String indirizzo;
    private String tel;
    private String email;
    private String desc;
    
    //constructor

   // Costruttore senza specificare ID ==> Genera ID automaticamente (In genere x ADD)
    /** 
     * costruttore senza id
     * usato per l'add
     * add non necessita di id perche
     * autogenera il codice id univoco
     * **end**
     */
    public Fornitore_old(String fullname, String p_iva, String indirizzo, String tel, String email, String desc) {
         
        setCode(leggiUltimoID() +1);
        this.fullname = fullname;
        this.p_iva = p_iva;
        this.indirizzo = indirizzo;
        this.tel = tel;
        this.email = email;
        this.desc = desc;
        
        setDatareg(generateData());
        setIdfornitore(generateID());
        System.out.println("ID del nuovo fornitore:"+getIdfornitore());
    }
    
    
    
    //Costruttore CON ID specificato ===> (In genere usato x UPDATE)
     /** 
     * costruttore con id
     * usato in genere per l'update
     * Necessita di id perche serve alla ricerca del codice univoco di identificazione
     * **end**
     */
        public Fornitore_old(String idfornitore, String fullname, String p_iva, String indirizzo, String tel, String email, String desc) {
            
            this.idfornitore = idfornitore;
            this.datareg = datareg;
            this.fullname = fullname;
            this.p_iva = p_iva;
            this.indirizzo = indirizzo;
            this.tel = tel;
            this.email = email;
            this.desc = desc;
            
            setDatareg(generateData());


    }


    public Fornitore_old() { }
    
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
    
        public static int getCode() {
        return code;
    }

    public static void setCode(int code) {
        Fornitore_old.code = code;
    }
    
    
       public String generateData() {
       
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	System.out.println(dtf.format(now)); //11/11/2019 11:11
        return dtf.format(now);
     }

    private String generateID() {
      
      String idgenerato = "FR-"+getCode();
      return idgenerato;
    }

    private int leggiUltimoID() {
                String tmp;
        int idlast;

        try {
            FornitoreDAO_old dao = new FornitoreDAO_old();
            
            String lastid = dao.getLastID().getIdfornitore();
            //FR-000
            if(lastid == null) return 0;
            
          
            tmp = lastid.substring(3);
            idlast= Integer.parseInt(tmp);
            System.out.println("ID dell'ultimo fornitore:"+idlast);

            
    }
                
         catch (SQLException ex) {
            Logger.getLogger(Prodotto.class.getName()).log(Level.SEVERE, null, ex);
            idlast = -99999;
        }
        
        return  idlast;

    }
}

    

