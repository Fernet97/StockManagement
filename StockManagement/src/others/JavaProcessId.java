package others;

import ui.StockManagement;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

public class JavaProcessId {

    public static void kILL() throws InterruptedException, IOException {

        String PID = jPID();
        System.out.println("PID " + PID);
        String os = "";
        os = System.getProperty("os.name");
        System.out.println("os " + os);
        if (os.startsWith("Windows")) {
            closeLogger("genlog");
            Process pr = Runtime.getRuntime().exec("taskkill /pid " + jPID() + " /t /f");
        } else {
            System.out.println("sto chiudendo " + os);
            Process pr = Runtime.getRuntime().exec("kill " + jPID());
            System.out.println("Chiuso");
        }

    }
    
        public static void closeLogger(String name) {
        Logger logger = getLogger(name);
            System.out.println("nomelog"+name);
        java.util.logging.Handler[] handlers = logger.getHandlers();
        for (java.util.logging.Handler h : handlers) {
            try {
                h.close();
            } catch (SecurityException e) {}
        }
    }

    public static String jPID() throws InterruptedException {
        String vmName = ManagementFactory.getRuntimeMXBean().getName();
        int p = vmName.indexOf("@");
        String pid = vmName.substring(0, p);
        return pid;
    }

    public static String path() {
        URL jarLocationUrl = JavaProcessId.class.getProtectionDomain().getCodeSource().getLocation();
       String patht = jarLocationUrl.toString();
//        System.out.println("jarloc "+jarLocationUrl);
//        String path = new File(jarLocationUrl).getParent();
        System.out.println("patht " + patht);
        String replace = patht.replaceAll("StockManagement.jar", "java -jar StockManagement.jar");
         String os = "";
        os = System.getProperty("os.name");
        System.out.println("os " + os);
        if (os.startsWith("Windows")) {
           String pathw= replace.substring(0).replaceFirst("/", "");
            System.out.println("winzoz "+pathw);
           return pathw;
        } else {
            System.out.println("sto chiudendo " + os);
           String pathlx = replace.substring(0).replaceAll("/", "");
            System.out.println("linux "+pathlx );
            return pathlx;
        }
//                System.out.println("path "+path);
//        return jarLocationUrl;
//        return path;
    }
    
    public static void runn(){
        ProcessBuilder builder = new ProcessBuilder(path());
    
        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
