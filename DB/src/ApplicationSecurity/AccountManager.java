/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationSecurity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvin
 */
public class AccountManager {
    
    private static final File users = new File(System.getProperty("user.dir") + "\\src\\db\\" + "users.txt");
    private static Scanner scan;
    
    public static void init() {
        if (users.exists()) {
            try {
                scan = new Scanner(users);
            } catch (FileNotFoundException ex) {
                System.out.println("Users Not Fount, Exitting System\n");
                System.exit(0);
            }
        }
    }
    
    public static void createUser(String username, String password, String name, int id) {
        if (!isUser(username, name, "(" + id + ")")) {
            try {
                FileOutputStream fos = new FileOutputStream(users, true);
                PrintStream ps = new PrintStream(fos);
                String info = username + "," + password + "," + name + ",(" + id + ")";
                ps.println();
                ps.println(info);
            } catch (FileNotFoundException ex) {
                System.out.println("Error Occurred in account creation");
            }
        } else {
            System.out.println("User Alredy Exists.");
        }
    }
    
    public static void changePassword(String username, String currentPW, String desiredPW) {
        System.out.println("Initializing Stream");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(users, false);
            System.out.println("Stream Initialized");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintStream ps = new PrintStream(fos);
        String allInfo = "";
        System.out.println("Validating");
        if (username.equals(DBLoginModule.loginUN) && currentPW.equals(DBLoginModule.loginPW)) {
            System.out.println("Validated");
            while (scan.hasNext()) {
                System.out.println("looping...");
                String line = scan.nextLine();
                allInfo += line + "\n";
                System.out.println(allInfo);
                Scanner lScan = new Scanner(line).useDelimiter(",");
                String uname = lScan.next();
                if (uname.equals(username)) {
                    ps.print(allInfo);
                    System.out.println(allInfo);
                    
                }
            }
        }else{
            System.out.println("Username/Password Invalid");
        }
    }
    
    public boolean validateUser(String username, String password) {
        boolean user = false;
        
        try {
            scan = new Scanner(users);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (scan.hasNext()) {
            String info = scan.nextLine();
            Scanner iScan = new Scanner(info).useDelimiter(",");
            if (username.equals(iScan.next())) {
                if (password.equals(iScan.next())) {
                    user = true;
                }
            }
        }
        
        return user;
    }
    
    public static boolean isUser(String username, String name, String ID) {
        boolean found = false;
        while (scan.hasNext()) {
            Scanner uScan = new Scanner(scan.nextLine()).useDelimiter(",");
            String uname = uScan.next();
            uScan.next();
            String n = uScan.next();
            String id = uScan.next();
            if (uname.equals(username)) {
                found = true;
                
            } else if (n.equals(name)) {
                found = true;
            }
            
        }
        //to reset the scanner
        try {
            scan = new Scanner(users);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return found;
    }
    
    public static String getName(String username) {
        String name = null;
        try {
            scan = new Scanner(users);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (scan.hasNext()) {
            String line = scan.nextLine();
            Scanner s = new Scanner(line).useDelimiter(",");
            if (s.next().equals(username)) {
                s.next();
                name = s.next();
            }
        }
        //to reset the scanner

        
        return name;
    }
    
    public static String getID(String username) {
        String ID = null;
        try {
            scan = new Scanner(users);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (scan.hasNext()) {
            String line = scan.nextLine();
            Scanner s = new Scanner(line).useDelimiter(",");
            if (s.next().equals(username)) {
                s.next();
                s.next();
                ID = s.next();
            }
        }
        //to reset the scanner
        try {
            scan = new Scanner(users);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ID;
    }
}
