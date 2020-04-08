/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LittleJoke
 */

import static database.DriverManagerConnectionPool.array;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import others.AES;
public class test {
    public static ArrayList<String> array;
    public static void main(String[] args) throws NullPointerException{
       array = getConf();
        System.out.println(array);
//        System.out.println(AES.decrypt(array.get(0), "miao"));
//        System.out.println(AES.decrypt(array.get(1), "miao"));
//        System.out.println(AES.decrypt(array.get(2), "miao"));
//        System.out.println(AES.decrypt(array.get(3), "miao"));
//        System.out.println(AES.decrypt(array.get(4), "miao"));
        
    }
    
      public static ArrayList<String> getConf(){
    
     try {
                File file = new File("./ai.aksn");
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                array = (ArrayList<String>) ois.readObject();

            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(CategoriePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                File file = new File("./ai.aksn");
//                Logger.getLogger(CategoriePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                //Logger.getLogger(CategoriePanel.class.getName()).log(Level.SEVERE, null, ex);
                array = new ArrayList<>();
            }return array;
}
}
