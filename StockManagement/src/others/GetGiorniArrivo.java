/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import beans.Ordine;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author LittleJoke
 */
public class GetGiorniArrivo {
    
    public synchronized int getArrivato() {
    
    Ordine bean = new Ordine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            //get data attuale
            String [] nows= bean.generateData().split(" ");
            LocalDate now = LocalDate.parse(nows[0], formatter);
            
            //get data ordine
            String [] dbs= bean.getData().split(" ");
            LocalDate db = LocalDate.parse(dbs[0], formatter);
            
            // get data della consegna
            LocalDate consegna = db.plusDays(bean.getGiorni_alla_consegna());
            
             System.out.println("now data "+ now.format(formatter)+ " db data "+db.format(formatter)+ " consegna "+ consegna.format(formatter));
             
             if (bean.getGiorni_alla_consegna() == -1 ){
                return  3;//verde
            }else if (now.format(formatter).equals(consegna.format(formatter))){
                return 2; //giallo
            }else if (now.isAfter(consegna)){
                return  2; //giallo
            }else if (now.isBefore(consegna)){
                return  0; //rosso
            }
            return -1;
            
    }
    
}
