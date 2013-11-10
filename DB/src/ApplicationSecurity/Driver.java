/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationSecurity;

import java.util.ArrayList;
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
    static LoginContext loginContext = null;

    public static void main(String[] args) {

        System.setProperty("java.security.auth.login.config", "jaas.config");



        try {
            loginContext = new LoginContext("DB", new DBCallbackHandler());
        } catch (LoginException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }

        System.out.println("Type \"LOGIN\" to login\nType \"create\" to create an account");
        String response = scan.next();
        if (response.toLowerCase().equals("create")) {
            AccountManager.init();
            System.out.print("Enter Username: ");
            String username = scan.next();
            System.out.print("Enter Desired Password: ");
            String pass = scan.next();
            System.out.print("Enter First Name: ");
            String name = scan.next();
            System.out.print("Enter 4 digit Employee ID: ");
            int empID = scan.nextInt();
            AccountManager.createUser(username, pass, name, empID);
            System.out.println("System Exiting");

        } else if (response.toLowerCase().equals("login")) {

            for (int i = 0; i < 3; i++) {
                try {
                    loginContext.login();
                    //emp.setRecord(db.getRecord(AccountManager.getName(Employee.getUsername())));
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
                System.out.println("Enter Query. type \"HELP\" for addition information");
                String query = scan.nextLine();
                if (query.toUpperCase().equals("EXIT")) {
                    System.out.println("Exiting System");

                    db.closeDatabase();
                    System.exit(0);
                } else {
                    handleQuery(query);
                }
            }
        }
    }

    public static void handleQuery(String query) {
        Scanner qScan = new Scanner(query.toUpperCase());
        if (qScan.hasNext()) {
            String q = qScan.next();

            switch (q) {
                case "GET":
                    if (qScan.hasNext()) {
                        String nextQ = qScan.next();
                        if (nextQ.toLowerCase().equals("subordinates")) {
                            System.out.println("Getting Subordinates for " + DBLoginModule.loginID);
                            ArrayList<Record> supRecs = db.getSupervisorRecords(DBLoginModule.loginID);
                            for (int i = 0; i < supRecs.size(); i++) {
                                System.out.println(supRecs.get(i).toString());
                            }
                        } else {
                            try {
                                Record record = db.getRecord(nextQ);
                                System.out.println(DBLoginModule.loginName + DBLoginModule.loginID);
                                System.out.println(record.getSupervisor());
                                System.out.println(db.getRecord(DBLoginModule.loginName).getPosition().toLowerCase());
                                if (record.getSupervisor().trim().equals(DBLoginModule.loginName + DBLoginModule.loginID)
                                        || db.getRecord(DBLoginModule.loginName).getPosition().toLowerCase().trim().equals("vp")) {
                                    System.out.println(record.toString());
                                }
                            } catch (NullPointerException e) {
                                System.out.println("Record Not Found");
                            }
                        }
                    } else {
                        System.out.println("Invalid Statement");
                    }
                    break;
                case "UPDATE":
                    if (qScan.hasNext()) {
                        String element = qScan.next();
                        Record record = db.getRecord(qScan.next());
                        String val = qScan.next();
                        handleUpdate(element, record, val);
                    } else {
                        System.out.println("Invalid Statement");
                    }
                    break;
                //CHANGE PASSWORD
                case "PASSWD":
                    System.out.print("Enter username: ");
                    String username = scan.next();
                    System.out.print("Enter current password: ");
                    String password = scan.next();
                    System.out.print("Enter desired password: ");
                    String dPassword = scan.next();
                    AccountManager.changePassword(username, password, dPassword);
                    break;
                default:
                    System.out.println("INVALID STATEMENT");
            }
        }
    }

    public static void handleUpdate(String element, Record target, String val) {
        switch (element.toLowerCase()) {
            case "salary":
                target.setSalary("$" + val);
                break;
            case "title":
                target.setPosition(val);
                break;
            default:
                System.out.println("INVALID FIELD");
        }
    }
}
