package model;

/*
 * An interface for all business-type contents where residents can be hired as staff.
 */

import java.util.ArrayList;

public interface Business {

    /*
     * REQUIRES: resident cannot be null
     * MODIFIES: this
     * EFFECTS: add resident as a staff for business
     */
    void addStaff(Resident resident);

    /*
     * REQUIRES: resident is currently working at business
     * MODIFIES: this
     * EFFECTS: removes resident as a staff for business
     */
    void removeStaff(Resident resident);

    /*
     * REQUIRES: business is not already open
     * MODIFIES: this
     * EFFECTS: opens a business
     */
    boolean openBusiness();

    // getters
    int getOccupationCode();

    int getSalary();

    String getBusinessName();

    ArrayList<Resident> getStaff();
}
