package ui;

import model.Hotel;

import java.util.Scanner;

public class HotelSimulator {
    Scanner scanner = new Scanner(System.in);

    protected void openHotelForBusiness(Hotel hotel) {
        boolean openSuccessful;

        if (hotel.isHotelOpen()) {
            System.out.println("Your hotel " + hotel.getBusinessName() + " is already open!");
        } else {
            openSuccessful = hotel.openBusiness();
            if (openSuccessful) {
                System.out.println("Your hotel " + hotel.getBusinessName() + " is now open for business!");
            } else {
                System.out.println(hotel.getBusinessName() + " does not meet the requirements for opening.");
                System.out.println("You need to have staff and hotel rooms!");
                System.out.println("You currently have " + hotel.getRoomNumbers().size() + " hotel rooms and "
                        + hotel.getStaff().size() + " staff.");
                System.out.println("Returning you to the previous menu...");
            }
        }
    }

    protected void createHotelRooms(Hotel hotel) {
        int numRooms;

        System.out.println("How many hotel rooms do you want to create?");
        do {
            numRooms = scanner.nextInt();
            if (numRooms <= 0) {
                System.out.println("You need to enter a positive integer number. Please enter again: ");
                numRooms = scanner.nextInt();
            }
        } while (numRooms <= 0);
        hotel.addRooms(numRooms);
        System.out.println("You have successfully created " + numRooms + " hotel rooms to your "
                + hotel.getBusinessName() + " hotel!");
    }


}
