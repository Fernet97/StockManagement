/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package log;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import ui.StockManagement;

/**
 *
 * @author LittleJoke
 */
public class log {
    
    public static void main(String[] args) throws IOException {
        
    Logger logger = Logger.getLogger("MyLog");  
    FileHandler fh;  
    FileHandler fh2;  

    try {  

        // This block configure the logger with handler and formatter  
        fh = new FileHandler("./miao.log");  
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  
        fh2 =new FileHandler(" ");
        // the following statement is used to log any messages  
        
        fh.close();

    } catch (SecurityException e) {  
//        e.printStackTrace(logger.log(Level.SEVERE, e.toString()));
//        System.out.println("mieow"+ e.);
    } catch (IOException e) {  
//        e.printStackTrace(); 
       
            logger.warning("mieow\n"+StockManagement.printStackTrace(e)+"miiiiieeooooooowwww");
    }catch(ArithmeticException e){
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            logger.warning("arit ex"+sw.toString());
    }  

    logger.info("Hi How r u?");  
    
    
    
    
    }
}
