package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static model.BusinessInfo.HOTEL;

// Represents a hotel to be added to a city. A hotel needs to have rooms and staff to be open for business.
public class Hotel extends Business implements Writable {
    private static final int ROOMS_EACH_FLOOR = 5;
    private static final int OCCUPATION_CODE = HOTEL.occupationCode();
    private static final int SALARY_PER_SECOND = HOTEL.salary();

    private int availableRooms;
    private ArrayList<Integer> roomNumbers;
    private ArrayList<Resident> guests;
    private ArrayList<Integer> bookedRoomNumbers;
    private HashMap<Integer, String> bookingInfo;

    /*
     * REQUIRES: hotelName has a non-zero length
     * EFFECTS: construct a hotel which has the name given by hotelName;
     *          hotel is not open; hotel has no rooms; hotel has no staff;
     *          hotel has no guests; available rooms = 0; no booked rooms.
     */
    public Hotel(String name) {
        super(name);
        availableRooms = 0;
        roomNumbers = new ArrayList<>();
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
    }

    /*
     * REQUIRES: person is not null AND person's age >= 19
     * MODIFIES: this
     * EFFECTS: add person to staff; change person's occupation code to hotel's occupation code
     */
    @Override
    public void addStaff(Resident person) {
        super.addStaff(person);
        person.setOccupation(OCCUPATION_CODE, name);
    }

    /*
     * REQUIRES: resident is currently working at business
     * MODIFIES: this
     * EFFECTS: removes resident as a staff for business
     */
    @Override
    public void removeStaff(Resident resident) {
        super.removeStaff(resident);
        resident.setOccupation(-1, null);

    }

    /*
     * REQUIRES: hotelIsOpen = false
     * MODIFIES: this
     * EFFECTS: set hotelIsOpen to true if number of rooms > 0 AND number of Staff > 0
     */
    public boolean openBusiness() {
        businessIsOpen = !roomNumbers.isEmpty() && !staff.isEmpty();
        return businessIsOpen;
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

        for (int i = 0; i < newBookedRoomNumbers.size(); i++) {
            bookingInfo.put(newBookedRoomNumbers.get(i), person.getName());
        }
        return newBookedRoomNumbers;
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

    public int getOccupationCode() {
        return OCCUPATION_CODE;
    }

    public int getSalary() {
        return SALARY_PER_SECOND;
    }

    public HashMap<Integer, String> getBookingInfo() {
        return bookingInfo;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Hotel Name", name);
        jsonObject.put("Available Rooms", availableRooms);
        jsonObject.put("Room Numbers", roomNumbers);
        jsonObject.put("Guests", guests);
        jsonObject.put("Booked Room Numbers", bookedRoomNumbers);

        JSONArray jsonArray = new JSONArray();
        Iterator it = bookingInfo.entrySet().iterator();
        while (it.hasNext()) {
            JSONObject bookingInfoJson = new JSONObject();
            HashMap.Entry pairs = (HashMap.Entry)it.next();
            bookingInfoJson.put(String.valueOf(pairs.getKey()), pairs.getValue());
            jsonArray.put(bookingInfoJson);
        }

        jsonObject.put("Booking Info", jsonArray);
        jsonObject.put("Staff", staff);
        return jsonObject;
    }
}
