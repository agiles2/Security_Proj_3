/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationSecurity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author alvin
 */
public class Hasher {
    /**
     * Creates MD5 hash of input string and returns the hash value in string form
     * 
     * code from:
     * http://viralpatel.net/blogs/java-md5-hashing-salting-password/
     * @param input
     * @return String MD5 hash
     */
    public static String md5Hash(String input) {
         
        String md5 = null;
         
        if(null == input) return null;
         
        try {
             
        //Create MessageDigest object for MD5
        MessageDigest digest = MessageDigest.getInstance("MD5");
         
        //Update input string in message digest
        digest.update(input.getBytes(), 0, input.length());
 
        //Converts message digest value in base 16 (hex) 
        md5 = new BigInteger(1, digest.digest()).toString(16);
 
        } catch (NoSuchAlgorithmException e) {
 
            e.printStackTrace();
        }
        return md5;
    }
}
