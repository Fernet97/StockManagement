/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import beans.Fornitore;
import beans.Prodotto;
import beans.Utente;
import dao.FornitoreDAO;
import dao.ProdottoDAO;
import dao.UtenteDAO;
import java.sql.SQLException;

/**
 *
 * @author LittleJoke
 */
public class Poopulator {
    
    public static void main(String[] args) throws SQLException {
/**
 * utente add
 */
        UtenteDAO dao = new UtenteDAO();
//        (fullname, CF, indirizzo, telefono, email, pwd, permessi, note)
        Utente u= new Utente("v ii", "cf", "via1", "121", "@1", "pwd",0 , "note");
        Utente u2= new Utente("g ii", "cf", "via1", "121", "@1", "pwd",0 , "note");
        Utente u3= new Utente("v nn", "cf", "via1", "121", "@1", "pwd",0 , "note");
        Utente u4= new Utente("v jj", "cf", "via1", "121", "@1", "pwd",0 , "note");
        Utente u5= new Utente("r ds", "cf", "via1", "121", "@1", "pwd",0 , "note");
        Utente u6= new Utente("ef gg", "cf", "via1", "121", "@1", "pwd",0 , "note");
        Utente u7= new Utente("vitordf ll", "cf", "via1", "121", "@1", "pwd",0 , "note");
        Utente u8= new Utente("x hh", "cf", "via1", "121", "@1", "pwd",0 , "note");
        Utente u9= new Utente("q jj", "cf", "via1", "121", "@1", "pwd",0 , "note");
        Utente u10= new Utente("p gwe", "cf", "via1", "121", "@1", "pwd",0 , "note");
        
        dao.add(u);
        dao.add(u2);
        dao.add(u3);
        dao.add(u4);
        dao.add(u5);
        dao.add(u6);
        dao.add(u7);
        dao.add(u8);
        dao.add(u9);
        dao.add(u10);
       // System.out.println(+u.toString()+"\n"+u2.toString()+"\n"+u3.toString()+"\n"+u4.toString()+"\n"+u5.toString()+"\n"+u6.toString()+"\n"+u7.toString()+"\n"+u8.toString()+"\n"+u9.toString()+"\n"+u10.toString()+"\n");
    
    
    
    
    /**
     * fornitore add
     */
    FornitoreDAO daof = new FornitoreDAO();
//        Fornitore f = new Fornitore(fullname, p_iva, indirizzo, tel, email, note)

      Fornitore b= new Fornitore("v ii", "cf", "via1", "121", "@1", "pwd");
        Fornitore b2= new Fornitore("alixpress", "piva", "indirizzo", "tel", "email", "note");
        Fornitore b3= new Fornitore("alixpress", "piva", "indirizzo", "tel", "email", "note");
        Fornitore b4= new Fornitore("alixpress", "piva", "indirizzo", "tel", "email", "note");
        Fornitore b5= new Fornitore("alixpress", "piva", "indirizzo", "tel", "email", "note");
        Fornitore b6= new Fornitore("alixpress", "piva", "indirizzo", "tel", "email", "note");
        Fornitore b7= new Fornitore("alixpress", "piva", "indirizzo", "tel", "email", "note");
        Fornitore b8= new Fornitore("alixpress", "piva", "indirizzo", "tel", "email", "note");
        Fornitore b9= new Fornitore("alixpress", "piva", "indirizzo", "tel", "email", "note");
        Fornitore b10= new Fornitore("alixpress", "piva", "indirizzo", "tel", "email", "note");
        

    daof.add(b);
    daof.add(b2);
    daof.add(b3);
    daof.add(b4);
    daof.add(b5);
    daof.add(b6);
    daof.add(b7);
    daof.add(b8);
    daof.add(b9);
    daof.add(b10);
    
    
        
    
/**
 * prodotto add
 */    
ProdottoDAO daop =new ProdottoDAO();
//public Prodotto(String nome int qty,String Categoria,int instock, float costo,int qty_min,String note,String foto,int negozio)
    Prodotto p = new Prodotto("1n5088", 10, "diodi", 1, 10.5f, 2, "note","foto", 1);
    Prodotto p2 = new Prodotto("1n5088", 10, "diodi", 1, 10.5f, 2, "note","foto", 1);
    Prodotto p3 = new Prodotto("1n5088", 10, "diodi", 1, 10.5f, 2, "note","foto", 1);
    Prodotto p4 = new Prodotto("1n5088", 10, "diodi", 1, 10.5f, 2, "note","foto", 1);
    Prodotto p5 = new Prodotto("1n5088", 10, "diodi", 1, 10.5f, 2, "note","foto", 1);
    Prodotto p6 = new Prodotto("1n5088", 10, "diodi", 1, 10.5f, 2, "note","foto", 1);
    Prodotto p7 = new Prodotto("1n5088", 10, "diodi", 1, 10.5f, 2, "note","foto", 1);
    Prodotto p8 = new Prodotto("1n5088", 10, "diodi", 1, 10.5f, 2, "note","foto", 1);
    Prodotto p9 = new Prodotto("1n5088", 10, "diodi", 1, 10.5f, 2, "note","foto", 1);
    Prodotto p10 = new Prodotto("1n5088", 10, "diodi", 1, 10.5f, 2, "note","foto", 1);
        
        daop.add(p);
        daop.add(p2);
        daop.add(p3);
        daop.add(p4);
        daop.add(p5);
        daop.add(p6);
        daop.add(p7);
        daop.add(p8);
        daop.add(p9);
        daop.add(p10);
        }  
}
