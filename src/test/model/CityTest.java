package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CityTest {
    public City city;

    @BeforeEach
    public void setup() {
        city = new City("Vancouver", City.Theme.LIGHT);
    }

    @Test
    public void testConstructor() {
        assertEquals("Vancouver", city.getCityName());
        assertTrue(city.getHotels().isEmpty());
        assertTrue(city.getResidents().isEmpty());
        assertTrue(city.getBusinesses().isEmpty());
        assertEquals(City.Theme.LIGHT, city.getTheme());
    }

    @Test
    public void testAddHotel() {
        Hotel hotel1 = new Hotel("Holiday Inn", 3, Hotel.Theme.SKI);
        Hotel hotel2 = new Hotel("Holiday Inn", 3, Hotel.Theme.SKI);
        city.addHotel(hotel1);
        assertEquals(new ArrayList<>(Collections.singletonList(hotel1)), city.getHotels());

        city.addHotel(hotel2);
        assertEquals(new ArrayList<>(asList(hotel1, hotel2)), city.getHotels());
        assertTrue(city.getBusinesses().contains(hotel1));
    }

    @Test
    public void testAddResident() {
        Resident resident1 = new Resident("Megan", true, 25);
        Resident resident2 = new Resident("Lucia", true, 26);
        city.addResident(resident1);
        assertEquals(new ArrayList<>(Collections.singletonList(resident1)), city.getResidents());

        city.addResident(resident2);
        assertEquals(new ArrayList<>(asList(resident1, resident2)), city.getResidents());
    }

    @Test
    public void testGetBank() {
        assertEquals("Bank of Vancouver", city.getBank().getBankName());
    }
}
