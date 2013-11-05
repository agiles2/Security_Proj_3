/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationSecurity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvin
 */
public class Database {
    File db;
    ArrayList<Record> records;
    public Database(){
        db =  new File(System.getProperty("user.dir")+"\\src\\db\\"+"DB.txt");
        Scanner scan=null;
        try {
           scan = new Scanner(db).useDelimiter(",");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        records = new ArrayList<>();
        if(scan != null){
            while(scan.hasNext()){
                records.add(new Record(scan.nextLine()));
            }
        }
    }
    public Record getRecord(String query){
        Record rec = null;
        for(int i=0;i<records.size();i++){
            if(records.get(i).getName().toUpperCase().equals(query.toUpperCase())){
                rec = records.get(i);
            }
        }
        return rec;
    }
}
