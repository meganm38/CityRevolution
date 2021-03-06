package ui;

import model.Hotel;
import model.Resident;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// A hotel simulator used in CityRevolution to perform actions about a hotel.

public class HotelSimulator {
    protected final Scanner scanner = new Scanner(System.in);
    protected final ResidentSimulator residentSimulator = new ResidentSimulator();

    /*
     * EFFECTS: displays a city's all hotels in a list if there are any; returns true if there are, false otherwise.
     */
    protected boolean displayAllHotels(ArrayList<Hotel> hotels) {
        int numHotels = hotels.size();
        if (numHotels == 0) {
            System.out.println("\nYou do not have any hotels in this city! Start by creating new hotels.");
            System.out.println("Returning you to the previous menu...");
            return false;
        } else {
            System.out.println("Choose from the following hotels:");
            for (int i = 0; i < numHotels; i++) {
                System.out.print("\t" + i + ". ");
                System.out.println(hotels.get(i).getBusinessName());
            }
            return true;
        }
    }

    /*
     * EFFECTS: tries to open hotel if not already open; displays information about whether a hotel was successfully
     *          opened.
     */
    protected void openHotelForBusiness(Hotel hotel) {
        boolean openSuccessful;

        if (hotel.isBusinessOpen()) {
            System.out.println("\nYour hotel " + hotel.getBusinessName() + " is already open!");
        } else {
            openSuccessful = hotel.openBusiness();
            if (openSuccessful) {
                System.out.println("\nYour hotel " + hotel.getBusinessName() + " is now open for business!");
            } else {
                System.out.println("\n" + hotel.getBusinessName() + " does not meet the requirements for opening.");
                System.out.println("You need to have staff and hotel rooms!");
                System.out.println("You currently have " + hotel.getRoomNumbers().size() + " hotel rooms and "
                        + hotel.getStaff().size() + " staff.");
                System.out.println("Returning you to the previous menu...");
            }
        }
    }

    /*
     * EFFECTS: ask for a positive integer number for the number of rooms to be added to a hotel;
     *          displays information about whether rooms were successfully
     *          added.
     */
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
        System.out.println("\nYou have successfully added " + numRooms + " hotel rooms to your "
                + hotel.getBusinessName() + " hotel!");
    }

    /*
     * EFFECTS: checks if a hotel has any available rooms and if it's open; asks for number of bookings
     *          to be made and if smaller than # of available rooms but larger than 0, calls createBookings()
     */
    protected void checkBookingConditions(Hotel hotel, ArrayList<Resident> residents) {
        if (hotel.getAvailableRooms() == 0) {
            System.out.println("\nThis hotel is fully booked. You can check out existing guests or create new rooms.");
            System.out.println("Returning you to the previous menu...");
        } else if (!hotel.isBusinessOpen()) {
            System.out.println("\nThis hotel is not open for business yet! Please open it before creating bookings.");
            System.out.println("Returning you the previous menu...");
        } else if (residentSimulator.checkAndDisplayResidents(residents)) {
            Resident resident = residents.get(scanner.nextInt());
            System.out.println("Enter the number of bookings under " + resident.getName() + "'s name.");
            int num = scanner.nextInt();
            while (num <= 0) {
                System.out.println("You need to enter a positive integer. Please enter again.");
                num = scanner.nextInt();
            }
            if (hotel.getAvailableRooms() < num) {
                System.out.println("\nOnly " + hotel.getAvailableRooms() + " rooms are available.");
                System.out.println("Returning you the previous menu...");
                return;
            }
            createBookings(hotel, num, resident);
        }
    }

    /*
     * EFFECTS: creates bookings under resident's name
     */
    protected void createBookings(Hotel hotel, int numOfBookings, Resident resident) {
        ArrayList<Integer> bookedRoomNumbers = hotel.makeBooking(numOfBookings, resident);
        System.out.println("\nSuccessful! The following rooms have been booked: ");
        for (Integer bookedRoomNumber : bookedRoomNumbers) {
            System.out.print(bookedRoomNumber + " ");
        }
        System.out.print("\n");
    }

    /*
     * EFFECTS: displays all bookings in the format of room number=> guest name
     */
    protected void displayAllBookings(Hotel hotel) {
        HashMap<Integer, String> bookingInfo = hotel.getBookingInfo();
        if (!hotel.getRoomNumbers().isEmpty()) {
            for (Map.Entry<Integer, String> entry : bookingInfo.entrySet()) {
                System.out.println(entry.getKey() + "=> " + entry.getValue());
            }
        } else {
            System.out.println("\nThis hotel does not currently have rooms. Please start by creating hotel rooms.");
            System.out.println("Returning you to the previous menu...");
        }
    }
}
