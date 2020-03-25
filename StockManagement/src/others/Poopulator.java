/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import beans.Cliente;
import beans.Fornitore;
import beans.Ordine;
import beans.Prodotto;
import beans.Utente;
import dao.ClienteDAO;
import dao.FornitoreDAO;
import dao.OrdineDAO;
import dao.ProdottoDAO;
import dao.UtenteDAO;
import java.sql.SQLException;

/**
 *
 * @author LittleJoke
 */
public class Poopulator {

    public static void main(String[] args) throws SQLException, InterruptedException {

        dbcleaner.main(args);//richiama un depopulator 

        for (int i = 0; i < 2; i++) {

            /**
             * utente add
             */
            UtenteDAO dao = new UtenteDAO();
//        (fullname, CF, indirizzo, telefono, email, pwd, permessi, note)
            Utente u = new Utente("vittorio manisera", "cf", "via1", "121", "@1", "pwd", 0, "note");
            dao.add(u);
            Utente u2 = new Utente("vittorio manisera", "cf", "via1", "121", "@1", "pwd", 0, "note");
            dao.add(u2);
            Utente u3 = new Utente("pielurigi liguori", "cf", "via1", "121", "@1", "pwd", 0, "note");
            dao.add(u3);
            Utente u4 = new Utente("dario traifuochi", "cf", "via1", "121", "@1", "pwd", 0, "note");
            dao.add(u4);
            Utente u5 = new Utente("labio faino", "cf", "via1", "121", "@1", "pwd", 0, "note");
            dao.add(u5);
            Utente u6 = new Utente("fabbio caino", "cf", "via1", "121", "@1", "pwd", 0, "note");
            dao.add(u6);
            Utente u7 = new Utente("zoccola puttana", "cf", "via1", "121", "@1", "pwd", 0, "note");
            dao.add(u7);
            Utente u8 = new Utente("meshon cancro", "cf", "via1", "121", "@1", "pwd", 0, "note");
            dao.add(u8);
            Utente u9 = new Utente("vincenzo manisera", "cf", "via1", "121", "@1", "pwd", 0, "note");
            dao.add(u9);
            Utente u10 = new Utente("babbeo nato", "cf", "via1", "121", "@1", "pwd", 0, "note");
            dao.add(u10);

            // System.out.println(+u.toString()+"\n"+u2.toString()+"\n"+u3.toString()+"\n"+u4.toString()+"\n"+u5.toString()+"\n"+u6.toString()+"\n"+u7.toString()+"\n"+u8.toString()+"\n"+u9.toString()+"\n"+u10.toString()+"\n");
            /**
             * fornitore add
             */
            FornitoreDAO daof = new FornitoreDAO();
//        Fornitore f = new Fornitore(fullname, p_iva, indirizzo, tel, email, note)
//(idfornitore, datareg, fullname, p_iva, indirizzo, tel, email, note)
            Fornitore b = new Fornitore("alixpresas", "piva", "via", "333", "@1", "note");
            daof.add(b);
            Fornitore b2 = new Fornitore("amanoz", "piva", "via", "333", "@1", "note");
            daof.add(b2);
            Fornitore b3 = new Fornitore("miao", "pina", "via", "333", "@1", "note");
            daof.add(b3);
            Fornitore b4 = new Fornitore("alixpress", "piva", "via", "333", "@1", "note");
            daof.add(b4);
            Fornitore b5 = new Fornitore("alixpreas", "piva", "via", "333", "@1", "note");
            daof.add(b5);
            Fornitore b6 = new Fornitore("alixprsas", "piva", "via", "333", "@1", "note");
            daof.add(b6);
            Fornitore b7 = new Fornitore("alixresas", "piva", "via", "333", "@1", "note");
            daof.add(b7);
            Fornitore b8 = new Fornitore("alxpresas", "piva", "via", "333", "@1", "note");
            daof.add(b8);
            Fornitore b9 = new Fornitore("lixpresas", "piva", "via", "333", "@1", "note");
            daof.add(b9);
            Fornitore b10 = new Fornitore("presas", "piva", "via", "333", "@1", "note");
            daof.add(b10);

            /**
             * prodotto add
             */
            
            
            ProdottoDAO daop = new ProdottoDAO();
//public Prodotto(String nome int qty,String Categoria,int instock, float costo,int qty_min,String note,String foto,int negozio)
            Prodotto p = new Prodotto("1n5088", 10, "diodi", true, 10.5f, 2, "note", "./DATA/IMG/prodotti.png", false);
            daop.add(p);
            Prodotto p2 = new Prodotto("1n5098", 10, "diodi", false, 10.5f, 2, "note", "./DATA/IMG/prodotti.png", false);
            daop.add(p2);
            Prodotto p3 = new Prodotto("1n508", 10, "transistor", false, 10.5f, 2, "note", "./DATA/IMG/prodotti.png", true);
            daop.add(p3);
            Prodotto p4 = new Prodotto("1n88", 10, "reisitenzasii", true, 10.5f, 2, "note", "./DATA/IMG/prodotti.png", true);
            daop.add(p4);
            Prodotto p5 = new Prodotto("15088", 10, "optocpo", true, 10.5f, 2, "note", "./DATA/IMG/prodotti.png", false);
            daop.add(p5);
            Prodotto p6 = new Prodotto("n5088", 10, "condens pol", false, 10.5f, 2, "note", "./DATA/IMG/prodotti.png", true);
            daop.add(p6);
            Prodotto p7 = new Prodotto("1", 10, "diodi", true, 10.5f, 2, "note", "./DATA/IMG/prodotti.png", true);
            daop.add(p7);
            Prodotto p8 = new Prodotto("188", 10, "diodi", true, 10.5f, 2, "note", "./DATA/IMG/prodotti.png", false);
            daop.add(p8);
            Prodotto p9 = new Prodotto("1088", 10, "diodi", false, 10.5f, 2, "note", "./DATA/IMG/prodotti.png", false);
            daop.add(p9);
            Prodotto p10 = new Prodotto("1n58", 10, "diodi", false, 10.5f, 2, "note", "./DATA/IMG/prodotti.png", true);
            daop.add(p10);

/**
 * add ordine
 */
            Ordine bean = new Ordine();
            OrdineDAO daoo = new OrdineDAO();
            String sku = daop.getLastProdotto().getSku().toString();
            for (int j = 0; j < 3; j++) {
                bean.startOrdine();

//            Ordine o = new Ordine(qty in arrivo, gg alla cons, fk_utente, prodotto_sku, cliente, fk_fornitore)
                Ordine o = new Ordine(5, 5, "admin", sku, 0, "FR-10",0);
                Ordine o2 = new Ordine(5, 6, "admin", sku, 0, "FR-2",0);
                Ordine o3 = new Ordine(5, 7, "admin", sku, 0, "FR-1",0);
                Ordine o4 = new Ordine(5, 8, "admin", sku, 0, "FR-14",0);
                Ordine o5 = new Ordine(5, 10, "admin", sku, 0, "FR-19",0);

                daoo.add(o);
                daoo.add(o2);
                daoo.add(o3);
                daoo.add(o4);
                daoo.add(o5);
            }
            
            
            
/**
 * add cliente
 */           

       ClienteDAO daoc = new ClienteDAO();
       Cliente c = new Cliente("nome", "cf", "indirizzo", "tel", "email", "note");
       daoc.add(c);
       Cliente c2 = new Cliente("nome", "cf", "indirizzo", "tel", "email", "note");
       daoc.add(c2);
       Cliente c3 = new Cliente("nome", "cf", "indirizzo", "tel", "email", "note");
       daoc.add(c3);
       Cliente c4 = new Cliente("nome", "cf", "indirizzo", "tel", "email", "note");
       daoc.add(c4);
       Cliente c5 = new Cliente("nome", "cf", "indirizzo", "tel", "email", "note");
       daoc.add(c5);
       Cliente c6 = new Cliente("nome", "cf", "indirizzo", "tel", "email", "note");
       daoc.add(c6);
       Cliente c7 = new Cliente("nome", "cf", "indirizzo", "tel", "email", "note");
       daoc.add(c7);
       Cliente c8 = new Cliente("nome", "cf", "indirizzo", "tel", "email", "note");
       daoc.add(c8);
       Cliente c9 = new Cliente("nome", "cf", "indirizzo", "tel", "email", "note");
       daoc.add(c9);
       Cliente c10 = new Cliente("nome", "cf", "indirizzo", "tel", "email", "note");
       daoc.add(c10);
        }

    }
}
