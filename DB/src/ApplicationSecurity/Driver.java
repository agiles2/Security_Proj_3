/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationSecurity;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

/**
 *
 * @author alvin
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    static Scanner scan = new Scanner(System.in);
    static Database db = new Database();

    public static void main(String[] args) {

        System.setProperty("java.security.auth.login.config", "jaas.config");
        LoginContext loginContext = null;

        try {
            loginContext = new LoginContext("DB", new DBCallbackHandler());
        } catch (LoginException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }



        for (int i = 0; i < 3; i++) {
            try {
                loginContext.login();
                System.out.println("Login Successful");
                break;
            } catch (LoginException ex) {
                System.out.println("Login Unsuccessful" + ex.getMessage());
                if (i == 2) {
                    System.exit(0);
                }
            }
        }
        while (true) {
            String query = scan.nextLine();
            if (query.toUpperCase().equals("EXIT")) {
                System.out.println("Exiting System");
                System.exit(0);
            } else {
                handleQuery(query);
            }
        }
    }

    public static void handleQuery(String query) {
        Scanner qScan = new Scanner(query.toUpperCase());
        switch (qScan.next()) {
            case "GET":
                if (qScan.hasNext()) {
                    try {
                        String record = db.getRecord(qScan.next()).toString();
                        System.out.println(record.toString());
                    } catch (NullPointerException e) {
                        System.out.println("Record Not Found");
                    }
                }else{
                    System.out.println("Invalid Statement");
                }
        }
    }
}
