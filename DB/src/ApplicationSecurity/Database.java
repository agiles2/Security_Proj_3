/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationSecurity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
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

    public Database() {
        db = new File(System.getProperty("user.dir") + "\\src\\db\\" + "DB.txt");
        Scanner scan = null;
        try {
            scan = new Scanner(db).useDelimiter(",");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        records = new ArrayList<>();
        if (scan != null) {
            while (scan.hasNext()) {
                records.add(new Record(scan.nextLine()));
            }
        }
        scan.close();
    }

    public Record getRecord(String query) {
        Record rec = null;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getName().toUpperCase().equals(query.toUpperCase())) {
                rec = records.get(i);
            }
        }
        return rec;
    }

    public ArrayList getSupervisorRecords(String ID) {
        ArrayList<Record> recs = new ArrayList<>();
        if (this.getRecord(DBLoginModule.loginName).getPosition().trim().toLowerCase().equals("vp")) {
            recs.addAll(records);
        } else {
            for (int i = 0; i < records.size(); i++) {
                if (records.get(i).getSupervisor().equals(DBLoginModule.loginName + DBLoginModule.loginID)) {
                    recs.add(records.get(i));
                }
            }
        }
        return recs;
    }

    public void addRecord(Record r) {
        records.add(r);
    }

    public void closeDatabase() {
        PrintStream ps = null;
        try {
            ps = new PrintStream(db);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ps != null) {
            for (int i = 0; i < records.size(); i++) {
                ps.println(records.get(i).toString());
            }
        }
        ps.close();

    }

    public static boolean inDatabase(String name, String ID) {
        boolean in = false;

        return in;
    }
}
