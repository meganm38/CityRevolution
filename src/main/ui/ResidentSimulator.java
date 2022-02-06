package ui;

import model.Bank;
import model.BusinessInfo;
import model.Resident;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ResidentSimulator {
    Scanner scanner = new Scanner(System.in);

    protected boolean checkAndDisplayResidents(ArrayList<Resident> residents) {
        int numResidents = residents.size();
        if (numResidents == 0) {
            System.out.println("\nYou do not have any residents in this city! Start by creating new residents.");
            System.out.println("Returning you to the previous menu...");
            return false;
        } else {
            System.out.println("Choose from the following residents:");
            for (int i = 0; i < numResidents; i++) {
                System.out.print("\t" + i + ". ");
                System.out.println(residents.get(i).getName());
            }
            return true;
        }
    }

    protected void displayResidentInfo(ArrayList<Resident> residents, Bank bank) {
        int residentIndex = scanner.nextInt();
        Resident resident = residents.get(residentIndex);

        String gender = resident.isFemale() ? "Female" : "Male";

        HashMap<String, Integer> bankAccounts = bank.getAccounts();
        int bankBalance = bankAccounts.get(resident.getName());

        String job = "Unemployed";
        for (BusinessInfo businessInfo : BusinessInfo.values()) {
            job = resident.getOccupationCode() == businessInfo.occupationCode()
                    ? businessInfo.businessType() : "Unemployed";
        }

        System.out.println("\n" + resident.getName());
        System.out.println("\tAge: " + resident.getAge());
        System.out.println("\tGender: " + gender);
        System.out.println("\tJob: " + job);
        System.out.println("\tBank account balance: " + bankBalance);
    }
}
