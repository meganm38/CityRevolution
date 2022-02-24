package model;

import java.util.ArrayList;

/*
 * An interface for all business-type contents where residents can be hired as staff.
 */
public abstract class Business {
    protected ArrayList<Resident> staff;
    protected String name;
    protected boolean businessIsOpen;
    protected int occupationCode;

    /*
     * REQUIRES: name has a non-zero length
     * EFFECTS: construct a business that is not yet open for business and does not have any staff
     */
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

    public int getOccupationCode() {
        return occupationCode;
    }
}
