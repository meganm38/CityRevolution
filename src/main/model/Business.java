package model;

public interface Business {

    void addStaff(Resident resident);

    String getBusinessName();

    boolean openBusiness();

    int getOccupationCode();

    int getSalary();
}
