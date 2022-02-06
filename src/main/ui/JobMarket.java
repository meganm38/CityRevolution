package ui;

import model.Bank;
import model.Business;
import model.City;
import model.Resident;

import java.util.ArrayList;

public class JobMarket {
    //EFFECTS: display all the staff currently working at business
    private boolean displayStaff(Business business) {
        ArrayList<Resident> staff = business.getStaff();
        if (staff.isEmpty()) {
            System.out.println("\nThis business currently does not have any staff.");
            return false;
        } else {
            System.out.println("\nThis business currently has " + staff.size() + " staff:");
            for (Resident person : staff) {
                System.out.println(person.getName());
            }
            return true;
        }
    }

    //EFFECTS: asks for a staff to be removed and confirm they're currently working at business; calls removeStaff to
    //          remove them from business.
    private void selectStaffToRemove(Business business) {
        ArrayList<Resident> allStaff = business.getStaff();
        System.out.println("\nEnter the name of staff you want to remove: ");
        String staffName = scanner.nextLine();
        staffName = scanner.nextLine();
        int staffIndex = -1;
        for (int i = 0; i < allStaff.size(); i++) {
            if (allStaff.get(i).getName().equalsIgnoreCase(staffName)) {
                staffIndex = i;
            }
        }
        if (staffIndex == -1) {
            System.out.println("\nThe name you entered doesn't match with any staff on record.");
            System.out.println("Returning you to previous menu...");
        } else {
            removeStaff(business, allStaff.get(staffIndex));
        }
    }

    //MODIFIES: business, resident
    //EFFECTS: removes resident from business; change resident's income at bank to 0.
    private void removeStaff(Business business, Resident resident) {
        business.removeStaff(resident);
        Bank bank = cities.get(currentCity).getBank();
        bank.stopGenerateEarnings();
        System.out.println("\n" + resident.getName() + " is no longer working at "
                + business.getBusinessName() + ".");
    }

    //MODIFIES: business
    //EFFECTS: checks if there are any residents in the city and asks users to select one to be added as staff to
    //          business if the resident is at least 19 years old
    private void addStaffToBusiness(Business business) {
        City myCity = cities.get(currentCity);
        boolean enoughResidents = residentSimulator.checkAndDisplayResidents(myCity.getResidents());
        if (!enoughResidents) {
            useSecondaryMenu();
        } else {
            int residentIndex = scanner.nextInt();
            if (myCity.getResidents().get(residentIndex).getAge() < 19) {
                System.out.println("\nThis resident is not 19 years old yet...Returning you to the previous menu...");
            } else {
                checkJob(business, myCity.getResidents().get(residentIndex));
            }
        }
    }

    //EFFECTS: helper function for addStaffToBusiness to check if selected resident is already working in selected
    //         business and displays a message; to check if resident is already working somewhere else and asks if
    //          user wants to change their jobs; and give this resident a new job.
    private void checkJob(Business business, Resident resident) {
        if (business.getStaff().contains(resident)) {
            System.out.println("\nThis resident is already working here! Returning you to the previous menu...");
            useSecondaryMenu();
        } else if (resident.getOccupationCode() != -1) {
            String workingLocation = resident.getWorkingLocation();
            Business currentEmployer = null;
            for (Business eachBusiness : cities.get(currentCity).getBusinesses()) {
                if (eachBusiness.getBusinessName().equals(workingLocation)) {
                    currentEmployer = eachBusiness;
                }
            }
            System.out.println("This resident is currently working at " + workingLocation + ".");
            System.out.println("Do you want to change their job? y/n");
            if (scanner.next().equals("n")) {
                System.out.println("Returning you the previous menu...");
                useSecondaryMenu();
            } else {
                removeStaff(currentEmployer, resident);
                assignJob(business, resident);
            }
        } else {
            assignJob(business, resident);
        }
    }

    //MODIFIES: business, bank, resident
    //EFFECTS： adds selected resident as staff in business; modifies their bank accounts so that they receive income
    private void assignJob(Business business, Resident resident) {
        business.addStaff(resident);
        Bank bank = cities.get(currentCity).getBank();
        bank.initializeSES();
        bank.createEarnings(resident, business.getSalary());
        System.out.println("This resident is now working at " + business.getBusinessName() + "!");
    }
}
