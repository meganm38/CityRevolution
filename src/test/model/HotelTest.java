package model;

import static org.junit.jupiter.api.Assertions.*;
import static java.util.Arrays.asList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;


class HotelTest {
    public Hotel hotel1;
    public Resident resident1;
    public Resident resident2;

    @BeforeEach
    public void setup() {
        hotel1 = new Hotel("Holiday Inn");
        resident1 = new Resident("Megan", true, 25);
        resident2 = new Resident("Lucia", true, 26);
    }

    @Test
    public void testConstructor() {
        assertEquals("Holiday Inn", hotel1.getBusinessName());
        assertFalse(hotel1.isHotelOpen());
        assertTrue(hotel1.getRoomNumbers().isEmpty());
        assertTrue(hotel1.getStaff().isEmpty());
        assertTrue(hotel1.getGuests().isEmpty());
        assertEquals(0, hotel1.getAvailableRooms());
        assertTrue(hotel1.getBookedRoomNumbers().isEmpty());
    }

    @Test
    public void testAddRooms() {
        hotel1.addRooms(1);
        assertEquals(1, hotel1.getRoomNumbers().size());
        assertEquals(1, hotel1.getAvailableRooms());

        ArrayList<Integer> roomNumbers = new ArrayList<>(asList(101));
        assertEquals(roomNumbers, hotel1.getRoomNumbers());

        hotel1.addRooms(10);
        assertEquals(11, hotel1.getRoomNumbers().size());
        assertEquals(11, hotel1.getAvailableRooms());

        roomNumbers = new ArrayList<>(asList(101, 102, 103, 104, 105, 201, 202, 203, 204, 205, 301));
        assertEquals(roomNumbers, hotel1.getRoomNumbers());
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
    public void testOpenHotel() {
        hotel1.openBusiness();
        assertTrue(hotel1.isHotelOpen());
    }

    @Test
    public void testMakeBooking() {
        ArrayList<Integer> roomsBooked;
        hotel1.addRooms(5);
        roomsBooked = hotel1.makeBooking(3, resident1);
        assertEquals(2, hotel1.getAvailableRooms());
        assertEquals(new ArrayList<>(asList(101, 102, 103)), roomsBooked);
        assertEquals(new ArrayList<>(asList(101, 102, 103)), hotel1.getBookedRoomNumbers());

        hotel1.addRooms(10);
        roomsBooked = hotel1.makeBooking(7, resident1);
        assertEquals(5, hotel1.getAvailableRooms());
        assertEquals(new ArrayList<>(asList(104, 105, 201, 202, 203, 204, 205)), roomsBooked);
        assertEquals(new ArrayList<>(asList(101, 102, 103, 104, 105, 201, 202, 203, 204, 205)),
                hotel1.getBookedRoomNumbers());
    }
}