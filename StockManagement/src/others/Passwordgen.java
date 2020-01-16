/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author LittleJoke
 */
public class Passwordgen {

private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
private static final String NUMBER = "0123456789";

private static final String PSWD_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + NUMBER; 
private static final String PSWD_SHUFFLE = shuffleString (PSWD_BASE);
private static final String PSWD = PSWD_SHUFFLE;

        
 private static SecureRandom random = new SecureRandom();
    
    public static String generateRandomPassword(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {

            int rndCharAt = random.nextInt(PSWD.length());
            char rndChar = PSWD.charAt(rndCharAt);        
            sb.append(rndChar);

        }
        

         System.out.println("pswd clear "+ sb.toString());
                  return sb.toString();
    }
    

        
        
 // shuffle
    public static String shuffleString (String string) {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        return letters.stream().collect(Collectors.joining());
    }    
}
