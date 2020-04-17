/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinedao;

import beans.Ordine;
import dao.OrdineDAO;
import java.sql.SQLException;
import java.text.ParseException;
import sun.applet.Main;

/**
 *
 * @author LittleJoke
 */
public class prinarrivo {
    public static void main(String[] args) throws SQLException, ParseException {
        
        
        OrdineDAO ordao = new OrdineDAO();
        
          for(Ordine o : ordao.getAll()){
                if(ordao.ggConsegnaPR2(o.getN_ordine(), o.getProdotto_sku()) <=5 && o.getGiorni_alla_consegna()>=0)
                  System.out.println( o.getProdotto_sku()+" "+o.getQty_in_arrivo()+" "+o.getN_ordine()+" "+ordao.dataArrivo(o.getN_ordine(), o.getProdotto_sku()));    
    }
    
}
}
