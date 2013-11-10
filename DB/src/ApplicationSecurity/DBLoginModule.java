/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationSecurity;

import static ApplicationSecurity.Driver.db;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 *
 * @author alvin
 */
public class DBLoginModule implements LoginModule{
    
    private ArrayList<String> usernames= new ArrayList<>();
    private ArrayList<String> passwords = new ArrayList<>();
    private CallbackHandler callbackHandler = null;
    private boolean authenticationSuccess=false;
    Subject subject;
    public static String loginName,loginID,loginPW,loginUN;
    public void initUsers(){
        File f = new File(System.getProperty("user.dir")+"\\src\\db\\"+"users.txt");
        Scanner scan=null;
        try {
            scan= new Scanner(f);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBLoginModule.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(scan != null){
            while(scan.hasNext()){
                
                String s=scan.nextLine();
                Scanner uScan = new Scanner(s).useDelimiter(",");
                usernames.add(uScan.next());
                passwords.add(uScan.next());
               
            }
            
        }
        
    }
    @Override
    public void initialize(Subject sbjct, CallbackHandler ch, Map<String, ?> map, Map<String, ?> map1) {
        subject = sbjct;
        callbackHandler = ch;
        initUsers();
    }

    @Override
    public boolean login() throws LoginException {
        authenticationSuccess =false;
        Callback[] callbackArray = new Callback[2];
        callbackArray[0] = new NameCallback("User Name:");
        callbackArray[1] = new PasswordCallback("Password:", true);
        
        try {
            callbackHandler.handle(callbackArray);
        } catch (IOException ex) {
            Logger.getLogger(DBLoginModule.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedCallbackException ex) {
            Logger.getLogger(DBLoginModule.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String name = ((NameCallback)callbackArray[0]).getName();
        
        String password = new String(((PasswordCallback)callbackArray[1]).getPassword());
        for(int i=0;i<usernames.size();i++){
            
            if(name.equals(usernames.get(i))&&password.equals(passwords.get(i))){
                loginName = AccountManager.getName(name);
                loginID = AccountManager.getID(name);
                loginUN = name;
                loginPW = password;
                authenticationSuccess =true;
                
            }
        }
        if(!authenticationSuccess){
            System.out.println("Username/Password Invalid");
        }
        
        return authenticationSuccess;
    }

    @Override
    public boolean commit() throws LoginException {
        return authenticationSuccess;
    }

    @Override
    public boolean abort() throws LoginException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean logout() throws LoginException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
