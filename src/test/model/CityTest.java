package model;

import static org.junit.jupiter.api.Assertions.*;
import static java.util.Arrays.asList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CityTest {
    public City city;

    @BeforeEach
    public void setup() {
        city = new City("Vancouver");
    }

    @Test
    public void testConstructor() {
        assertEquals("Vancouver", city.getCityName());
        assertTrue(city.getHotels().isEmpty());
        assertTrue(city.getResidents().isEmpty());
    }

    @Test
    public void testAddHotel() {
        Hotel hotel1 = new Hotel("Holiday Inn");
        Hotel hotel2 = new Hotel("Holiday Inn");
        city.addHotel(hotel1);
        assertEquals(new ArrayList<>(asList(hotel1)), city.getHotels());

        city.addHotel(hotel2);
        assertEquals(new ArrayList<>(asList(hotel1, hotel2)), city.getHotels());
    }

    @Test
    public void testAddResident() {
        Resident resident1 = new Resident("Megan", true, 25);
        Resident resident2 = new Resident("Lucia", true, 26);
        city.addResident(resident1);
        assertEquals(new ArrayList<>(asList(resident1)), city.getResidents());

        city.addResident(resident2);
        assertEquals(new ArrayList<>(asList(resident1, resident2)), city.getResidents());
    }
}
