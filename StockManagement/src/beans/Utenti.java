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
public class Utenti {
   
   private int id; 
   private String fullname; 
   private String telefono; 
   private String email; 
   private String CF; 
   private String indirizzo; 
   private String pwd; 
   private boolean create; 
   private boolean update; 
   private boolean view; 
   private boolean delete; 
   private boolean isAdmin;
   
   //contructor

    public Utenti(int id, String fullname, String telefono, String email, String CF, String indirizzo, String pwd, boolean create, boolean update, boolean view, boolean delete, boolean isAdmin) {
        this.id = id;
        this.fullname = fullname;
        this.telefono = telefono;
        this.email = email;
        this.CF = CF;
        this.indirizzo = indirizzo;
        this.pwd = pwd;
        this.create = create;
        this.update = update;
        this.view = view;
        this.delete = delete;
        this.isAdmin = isAdmin;
    }
    
    //getter & setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
   
}
