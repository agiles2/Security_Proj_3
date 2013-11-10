/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationSecurity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 *
 * @author alvin
 */
public class DBCallbackHandler implements CallbackHandler {

    @Override
    public void handle(Callback[] callbackArray) throws IOException, UnsupportedCallbackException {
        NameCallback nameCallback = null;
        PasswordCallback passwordCallback = null;
        int counter = 0;
        while (counter < callbackArray.length) {
            if (callbackArray[counter] instanceof NameCallback) {
                nameCallback = (NameCallback)callbackArray[counter];
                counter++;
                System.out.print(nameCallback.getPrompt());
                nameCallback.setName(new Scanner(System.in).next());
            } else if (callbackArray[counter] instanceof PasswordCallback) {
                passwordCallback = (PasswordCallback)callbackArray[counter];
                counter++;
                System.out.print(passwordCallback.getPrompt());
                passwordCallback.setPassword(new Scanner(System.in).next().toCharArray());
            }
        }
    }
}
