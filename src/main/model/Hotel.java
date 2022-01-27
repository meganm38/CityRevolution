package model;

import java.util.ArrayList;

// Represents a hotel
public class Hotel {
    private static final int ROOMS_EACH_FLOOR = 5;

    private String hotelName;
    private boolean hotelIsOpen;
    private int availableRooms;
    private ArrayList<Integer> roomNumbers;
    private ArrayList<Resident> staff;
    private ArrayList<Resident> guests;
    private ArrayList<Integer> bookedRoomNumbers;

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
     * REQUIRES: person's age > 19
     * MODIFIES: this
     * EFFECTS: add person to staff; change person's occupation to working at hotel
     */
    public void addStaff(Resident person) {
        staff.add(person);
        person.setOccupationCode(0);
    }

    /*
     * REQUIRES: number of rooms > 0 AND number of Staff > 0 AND hotelIsOpen = false
     * MODIFIES: this
     * EFFECTS: set hotelIsOpen to true
     */
    public void openHotel() {
        hotelIsOpen = true;
    }

    /*
     * REQUIRES: numOfBookings > 0 AND person != null AND person's occupation code != 0
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
     * getters
     */
    public String getHotelName() {
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
}
