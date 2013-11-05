/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationSecurity;

import java.util.Scanner;

/**
 *
 * @author alvin
 */
public class Record {
    private String name;
    private String ID;
    private String position;
    private String supervisor;
    private String salary;
    public Record(String recordLine){
        Scanner scan = new Scanner(recordLine);
        scan.useDelimiter(",");
        name = scan.next();
        ID = scan.next();
        position = scan.next();
        supervisor = scan.next();
        salary = scan.next();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the supervisor
     */
    public String getSupervisor() {
        return supervisor;
    }

    /**
     * @param supervisor the supervisor to set
     */
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    /**
     * @return the salary
     */
    public String getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }
    @Override
    public String toString(){
        String record="";
        record += name+","+ID+","+position+","+supervisor+","+salary;
        return record;
    }
}
