package model;

/*
 * An interface for all business-type contents where residents can be hired as staff.
 */

public interface Business {

    /*
     * REQUIRES: resident cannot be null
     * MODIFIES: this
     * EFFECTS: add resident as a staff for business
     */
    void addStaff(Resident resident);

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
}
