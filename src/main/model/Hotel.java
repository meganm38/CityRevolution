package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static model.BusinessInfo.HOTEL;

// Represents a hotel to be added to a city. A hotel needs to have rooms and staff to be open for business.
public class Hotel extends Business implements Writable {
    private static final int ROOMS_EACH_FLOOR = 5;
    private static final int SALARY_PER_SECOND = HOTEL.salary();

    private int availableRooms;
    private int star;
    private ArrayList<Integer> roomNumbers;
    private ArrayList<Resident> guests;
    private ArrayList<Integer> bookedRoomNumbers;
    private HashMap<Integer, String> bookingInfo;
    private Theme theme;

    public enum Theme {
        BEACH, SKI
    }

    /*
     * REQUIRES: hotelName has a non-zero length
     * EFFECTS: construct a hotel which has the name given by hotelName;
     *          hotel is not open; hotel has no rooms; hotel has no staff;
     *          hotel has no guests; available rooms = 0; no booked rooms.
     */
    public Hotel(String name, int star, Theme theme) {
        super(name);
        availableRooms = 0;
        roomNumbers = new ArrayList<>();
        guests = new ArrayList<>();
        bookedRoomNumbers = new ArrayList<>();
        bookingInfo = new HashMap<>();
        occupationCode = HOTEL.occupationCode();
        this.star = star;
        this.theme = theme;
    }

    /*
     * REQUIRES: num > 0
     * MODIFIES: this
     * EFFECTS: add num of rooms to the hotel;
     *          assign room numbers to rooms in the format of 101, 102, 103, 104, 105, 201, 202...;
     *          add num to available rooms.
     */
    public void addRooms(int num) {
        ArrayList<Integer> newRoomNumbers = new ArrayList<>();
        int nextRoomNumber;

        for (int i = 0; i < num; i++) {
            if (roomNumbers.isEmpty()) {
                nextRoomNumber = 101;
            } else {
                nextRoomNumber = roomNumbers.size() % ROOMS_EACH_FLOOR == 0
                        ? roomNumbers.get(roomNumbers.size() - 1) + 101 - ROOMS_EACH_FLOOR
                        : roomNumbers.get(roomNumbers.size() - 1) + 1;
            }
            roomNumbers.add(nextRoomNumber);
            newRoomNumbers.add(nextRoomNumber);
        }
        availableRooms += num;

        // create booking info with empty rooms
        for (Integer roomNumber : newRoomNumbers) {
            bookingInfo.put(roomNumber, "Empty");
        }
        EventLog.getInstance().logEvent(new Event(num + " new hotel rooms have been added to " + name + "."));
    }

    /*
     * REQUIRES: person is not null AND person's age >= 19
     * MODIFIES: this
     * EFFECTS: add person to staff; change person's occupation code to hotel's occupation code
     */
    @Override
    public void addStaff(Resident person) {
        super.addStaff(person);
        person.setOccupation(occupationCode, name, SALARY_PER_SECOND);
        EventLog.getInstance().logEvent(new Event(person.getName() + " is now working at " + name + "."));
    }

    /*
     * REQUIRES: resident is currently working at business
     * MODIFIES: this
     * EFFECTS: removes resident as a staff for business
     */
    @Override
    public void removeStaff(Resident resident) {
        super.removeStaff(resident);
        resident.setOccupation(-1, null, 0);
    }

    /*
     * REQUIRES: hotelIsOpen = false
     * MODIFIES: this
     * EFFECTS: set hotelIsOpen to true if number of rooms > 0 AND number of Staff > 0
     */
    public boolean openBusiness() {
        businessIsOpen = !roomNumbers.isEmpty() && !staff.isEmpty();
        if (businessIsOpen) {
            EventLog.getInstance().logEvent(new Event(name + " is open for business."));
        }
        return businessIsOpen;
    }

    /*
     * MODIFIES: this
     * EFFECTS: set hotelIsOpen to false
     */
    public void closeHotel() {
        EventLog.getInstance().logEvent(new Event(name + " is now closed."));
        businessIsOpen = false;
    }


    /*
     * REQUIRES: numOfBookings > 0 AND person != null
     *           AND number of available rooms >= numOfBookings.
     * MODIFIES: this
     * EFFECTS: create bookings under person's name;
     * add newly booked room numbers to booked room numbers;
     * creates and returns a map with room numbers and guests;
     *          if a room hasn't been booked, map it with "Empty"
     */
    public ArrayList<Integer> makeBooking(int numOfBookings, Resident person) {

        ArrayList<Integer> newBookedRoomNumbers = new ArrayList<>();

        for (int i = 0; i < numOfBookings; i++) {
            newBookedRoomNumbers.add(roomNumbers.get(guests.size()));
            guests.add(person);
        }
        availableRooms -= numOfBookings;
        bookedRoomNumbers.addAll(newBookedRoomNumbers);

        for (Integer newBookedRoomNumber : newBookedRoomNumbers) {
            bookingInfo.put(newBookedRoomNumber, person.getName());
        }
        EventLog.getInstance().logEvent(new Event(
                numOfBookings + " bookings have been created for " + person.getName() + " at " + name + "."));
        return newBookedRoomNumbers;
    }

    // EFFECTS: returns a hotel as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Hotel Name", name);
        jsonObject.put("Available Rooms", availableRooms);
        jsonObject.put("Room Numbers", roomNumbers);
        jsonObject.put("Guests", guests);
        jsonObject.put("Booked Room Numbers", bookedRoomNumbers);
        jsonObject.put("Hotel Star", star);
        jsonObject.put("Theme", theme);

        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<Integer, String> integerStringEntry : bookingInfo.entrySet()) {
            JSONObject bookingInfoJson = new JSONObject();
            bookingInfoJson.put(String.valueOf(((HashMap.Entry) integerStringEntry).getKey()),
                    ((HashMap.Entry) integerStringEntry).getValue());
            jsonArray.put(bookingInfoJson);
        }
        jsonObject.put("Booking Info", jsonArray);
        jsonObject.put("Staff", staff);
        jsonObject.put("Is Open", isBusinessOpen());
        return jsonObject;
    }

    /*
     * getters
     */
    public ArrayList<Integer> getRoomNumbers() {
        return roomNumbers;
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

    public int getSalary() {
        return SALARY_PER_SECOND;
    }

    public HashMap<Integer, String> getBookingInfo() {
        return bookingInfo;
    }

    public int getStar() {
        return star;
    }

    public Theme getTheme() {
        return theme;
    }
}
