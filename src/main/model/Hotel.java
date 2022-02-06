package model;

import java.util.ArrayList;
import java.util.HashMap;

import static model.BusinessInfo.HOTEL;

// Represents a hotel to be added to a city. A hotel needs to have rooms and staff to be open for business.
public class Hotel implements Business {
    private static final int ROOMS_EACH_FLOOR = 5;
    private static final int OCCUPATION_CODE = HOTEL.occupationCode();
    private static final int SALARY_PER_SECOND = HOTEL.salary();

    private String hotelName;
    private boolean hotelIsOpen;
    private int availableRooms;
    private ArrayList<Integer> roomNumbers;
    private ArrayList<Resident> staff;
    private ArrayList<Resident> guests;
    private ArrayList<Integer> bookedRoomNumbers;
    private HashMap<Integer, String> bookingInfo;

    /*
     * REQUIRES: hotelName has a non-zero length
     * EFFECTS: construct a hotel which has the name given by hotelName;
     *          hotel is not open; hotel has no rooms; hotel has no staff;
     *          hotel has no guests; available rooms = 0; no booked rooms.
     */
    public Hotel(String hotelName) {
        this.hotelName = hotelName;
        availableRooms = 0;
        hotelIsOpen = false;
        roomNumbers = new ArrayList<>();
        staff = new ArrayList<>();
        guests = new ArrayList<>();
        bookedRoomNumbers = new ArrayList<>();
        bookingInfo = new HashMap<>();
    }

    /*
     * REQUIRES: num > 0
     * MODIFIES: this
     * EFFECTS: add num of rooms to the hotel;
     *          assign room numbers to rooms in the format of 101, 102, 103, 104, 105, 201, 202...;
     *          add num to available rooms.
     */
    public void addRooms(int num) {
        for (int i = 0; i < num; i++) {
            if (roomNumbers.isEmpty()) {
                roomNumbers.add(101);
            } else {
                int nextRoomNumber;
                nextRoomNumber = roomNumbers.size() % ROOMS_EACH_FLOOR == 0
                        ? roomNumbers.get(roomNumbers.size() - 1) + 101 - ROOMS_EACH_FLOOR
                        : roomNumbers.get(roomNumbers.size() - 1) + 1;
                roomNumbers.add(nextRoomNumber);
            }
        }
        availableRooms += num;
    }

    /*
     * REQUIRES: person is not null AND person's age >= 19
     * MODIFIES: this
     * EFFECTS: add person to staff; change person's occupation code to hotel's occupation code
     */
    public void addStaff(Resident person) {
        staff.add(person);
        person.setOccupation(OCCUPATION_CODE, hotelName);
    }

    /*
     * REQUIRES: resident is currently working at business
     * MODIFIES: this
     * EFFECTS: removes resident as a staff for business
     */
    @Override
    public void removeStaff(Resident resident) {
        staff.remove(resident);
        resident.setOccupation(-1, null);

    }

    /*
     * REQUIRES: hotelIsOpen = false
     * MODIFIES: this
     * EFFECTS: set hotelIsOpen to true if number of rooms > 0 AND number of Staff > 0
     */
    public boolean openBusiness() {
        hotelIsOpen = !roomNumbers.isEmpty() && !staff.isEmpty();
        return hotelIsOpen;
    }

    /*
     * REQUIRES: numOfBookings > 0 AND person != null
     *           AND number of available rooms >= numOfBookings.
     * MODIFIES: this
     * EFFECTS: create bookings under person's name;
     * add newly booked room numbers to booked room numbers
     */
    public ArrayList<Integer> makeBooking(int numOfBookings, Resident person) {

        ArrayList<Integer> newBookedRoomNumbers = new ArrayList<>();

        for (int i = 0; i < numOfBookings; i++) {
            newBookedRoomNumbers.add(roomNumbers.get(guests.size()));
            guests.add(person);
        }
        availableRooms -= numOfBookings;
        bookedRoomNumbers.addAll(newBookedRoomNumbers);
        return newBookedRoomNumbers;
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates and returns a map with room numbers and guests;
     *          if a room hasn't been booked, map it with "Empty"
     */
    public HashMap<Integer, String> getBookingInfo() {
        for (int i = 0; i < roomNumbers.size(); i++) {
            if (i < guests.size()) {
                bookingInfo.put(roomNumbers.get(i), guests.get(i).getName());
            } else {
                bookingInfo.put(roomNumbers.get(i), "Empty");
            }
        }
        return bookingInfo;
    }

    /*
     * getters
     */
    public String getBusinessName() {
        return hotelName;
    }

    public boolean isHotelOpen() {
        return hotelIsOpen;
    }

    public ArrayList<Integer> getRoomNumbers() {
        return roomNumbers;
    }

    public ArrayList<Resident> getStaff() {
        return staff;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public ArrayList<Resident> getGuests() {
        return guests;
    }

    public ArrayList<Integer> getBookedRoomNumbers() {
        return bookedRoomNumbers;
    }

    public int getOccupationCode() {
        return OCCUPATION_CODE;
    }

    public int getSalary() {
        return SALARY_PER_SECOND;
    }
}
