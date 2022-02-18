package model;

/*
 * An interface for all business-type contents where residents can be hired as staff.
 */

import java.util.ArrayList;

public abstract class Business {
    protected ArrayList<Resident> staff;
    protected String name;
    protected boolean businessIsOpen;

    //
    public Business(String name) {
        this.name = name;
        businessIsOpen = false;
        staff = new ArrayList<>();
    }

    /*
     * REQUIRES: resident cannot be null
     * MODIFIES: this
     * EFFECTS: add resident as a staff for business
     */
    public void addStaff(Resident person) {
        staff.add(person);
    }

    /*
     * REQUIRES: resident is currently working at business
     * MODIFIES: this
     * EFFECTS: removes resident as a staff for business
     */
    public void removeStaff(Resident resident) {
        staff.remove(resident);
    }

    /*
     * REQUIRES: business is not already open
     * MODIFIES: this
     * EFFECTS: opens a business
     */
    public abstract boolean openBusiness();

    // getters
    public abstract int getSalary();

    public String getBusinessName() {
        return name;
    }

    public boolean isBusinessOpen() {
        return businessIsOpen;
    }

    public ArrayList<Resident> getStaff() {
        return staff;
    }

    public abstract int getOccupationCode();
}
