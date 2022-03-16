package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;


class HotelTest {
    public Hotel hotel1;
    public Resident resident1;
    public Resident resident2;

    @BeforeEach
    public void setup() {
        hotel1 = new Hotel("Holiday Inn", 3, Hotel.Theme.SKI);
        resident1 = new Resident("Megan", true, 25);
        resident2 = new Resident("Lucia", true, 26);
    }

    @Test
    public void testConstructor() {
        assertEquals("Holiday Inn", hotel1.getBusinessName());
        assertFalse(hotel1.isBusinessOpen());
        assertTrue(hotel1.getRoomNumbers().isEmpty());
        assertTrue(hotel1.getStaff().isEmpty());
        assertTrue(hotel1.getGuests().isEmpty());
        assertEquals(0, hotel1.getAvailableRooms());
        assertTrue(hotel1.getBookedRoomNumbers().isEmpty());
        assertEquals(3, hotel1.getStar());
        assertEquals(Hotel.Theme.SKI, hotel1.getTheme());
    }

    @Test
    public void testAddRooms() {
        hotel1.addRooms(1);
        assertEquals(1, hotel1.getRoomNumbers().size());
        assertEquals(1, hotel1.getAvailableRooms());

        HashMap<Integer, String> bookingInfoMap = hotel1.getBookingInfo();
        assertEquals(1, bookingInfoMap.size());
        assertEquals("Empty", bookingInfoMap.get(101));

        ArrayList<Integer> roomNumbers = new ArrayList<>(Collections.singletonList(101));
        assertEquals(roomNumbers, hotel1.getRoomNumbers());

        hotel1.addRooms(10);
        assertEquals(11, hotel1.getRoomNumbers().size());
        assertEquals(11, hotel1.getAvailableRooms());

        roomNumbers = new ArrayList<>(asList(101, 102, 103, 104, 105, 201, 202, 203, 204, 205, 301));
        assertEquals(roomNumbers, hotel1.getRoomNumbers());

        bookingInfoMap = hotel1.getBookingInfo();
        assertEquals(11, bookingInfoMap.size());
        for (Integer roomNum : roomNumbers) {
            assertEquals("Empty", bookingInfoMap.get(roomNum));
        }
    }

    @Test
    public void testAddStaff() {
        ArrayList<Resident> residents = new ArrayList<>();

        hotel1.addStaff(resident1);
        residents.add(resident1);
        assertEquals(residents, hotel1.getStaff());

        hotel1.addStaff(resident2);
        residents.add(resident2);
        assertEquals(residents, hotel1.getStaff());
    }

    @Test
    public void testRemoveStaff() {
        hotel1.addStaff(resident1);
        hotel1.removeStaff(resident1);
        assertFalse(hotel1.getStaff().contains(resident1));
    }

    @Test
    public void testOpenHotel() {
        assertFalse(hotel1.openBusiness());
        hotel1.addRooms(1);
        assertFalse(hotel1.openBusiness());
        hotel1.addStaff(resident1);
        assertTrue(hotel1.openBusiness());

    }

    @Test
    public void testMakeBooking() {
        ArrayList<Integer> roomsBooked;
        hotel1.addRooms(5);
        roomsBooked = hotel1.makeBooking(3, resident1);
        assertEquals(2, hotel1.getAvailableRooms());
        assertEquals(new ArrayList<>(asList(101, 102, 103)), roomsBooked);
        assertEquals(new ArrayList<>(asList(101, 102, 103)), hotel1.getBookedRoomNumbers());

        HashMap<Integer, String> bookingInfoMap = hotel1.getBookingInfo();
        assertTrue(bookingInfoMap.size() == 5);

        for (Integer bookedRoomNum : hotel1.getBookedRoomNumbers()) {
            assertEquals(resident1.getName(), bookingInfoMap.get(bookedRoomNum));
        }

        hotel1.addRooms(10);
        roomsBooked = hotel1.makeBooking(7, resident1);
        assertEquals(5, hotel1.getAvailableRooms());
        assertEquals(new ArrayList<>(asList(104, 105, 201, 202, 203, 204, 205)), roomsBooked);
        assertEquals(new ArrayList<>(asList(101, 102, 103, 104, 105, 201, 202, 203, 204, 205)),
                hotel1.getBookedRoomNumbers());

        bookingInfoMap = hotel1.getBookingInfo();
        assertTrue(bookingInfoMap.size() == 15);

        for (Integer bookedRoomNum : hotel1.getBookedRoomNumbers()) {
            assertEquals(resident1.getName(), bookingInfoMap.get(bookedRoomNum));
        }
    }

    @Test
    public void testGetBookingInfo() {
        assertTrue(hotel1.getBookingInfo().isEmpty());
        hotel1.addRooms(2);
        hotel1.makeBooking(1, resident1);
        HashMap<Integer, String> bookings = new HashMap<>();
        bookings.put(101, "Megan");
        bookings.put(102, "Empty");
        assertEquals(bookings, hotel1.getBookingInfo());
    }

    @Test
    public void testGetSalary() {
        assertEquals(BusinessInfo.HOTEL.salary(), hotel1.getSalary());
    }

    @Test
    public void testGetOccupationCode() {
        assertEquals(BusinessInfo.HOTEL.occupationCode(), hotel1.getOccupationCode());
    }
}