package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            City wr = new City("Vancouver");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testEmptyCity() {
        try {
            City city = new City("Vancouver");
            ArrayList<City> cities = new ArrayList<>();
            cities.add(city);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCity.json");
            writer.open();
            writer.write(cities);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCity.json");
            cities = reader.read();
            assertEquals("Vancouver", cities.get(0).getCityName());
            assertEquals(0, city.getResidents().size());
            assertEquals(0, city.getHotels().size());

        } catch (IOException e) {
            fail("Exception shouldn't have been thrown");
        }
    }

    @Test
    void testWriter2Cities() {
        try {
            City city = new City("Vancouver");
            Resident resident1 = new Resident("Monica", true, 25);
            Resident resident2 = new Resident("Chandler", false, 25);
            city.addResident(resident1);
            city.addResident(resident2);
            city.getBank().createAccountForResident(resident1);
            city.getBank().createAccountForResident(resident2);

            Hotel hotel1 = new Hotel("Holiday Inn");
            city.addHotel(hotel1);
            hotel1.addRooms(10);
            hotel1.makeBooking(2, resident1);
            hotel1.addStaff(resident1);
            hotel1.addStaff(resident2);
            hotel1.openBusiness();

            Hotel hotel2 = new Hotel("Another Hotel");
            city.addHotel(hotel2);

            City city2 = new City("Toronto");
            Resident resident3 = new Resident("Joey", false, 25);
            Resident resident4 = new Resident("Ross", false, 25);
            city2.addResident(resident3);
            city2.addResident(resident4);
            city2.getBank().createAccountForResident(resident3);
            city2.getBank().createAccountForResident(resident4);


            ArrayList<City> cities = new ArrayList<>();
            cities.add(city);
            cities.add(city2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCity.json");
            writer.open();
            writer.write(cities);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCity.json");
            ArrayList<City> citiesRead = reader.read();
            City city1Read = citiesRead.get(0);
            ArrayList<Hotel> city1Hotels = city1Read.getHotels();
            Hotel holidayInn = city1Hotels.get(0);
            ArrayList<Resident> holidayInnGuests = holidayInn.getGuests();
            ArrayList<Resident> city1Residents = city1Read.getResidents();

            //check city 1 name and residents info
            assertEquals("Vancouver", city1Read.getCityName());
            assertEquals(2, city1Residents.size());
            assertEquals("Monica", city1Residents.get(0).getName());
            assertEquals("Holiday Inn", city1Residents.get(0).getWorkingLocation());
            assertEquals(25, city1Residents.get(0).getAge());
            assertEquals(0, city1Residents.get(0).getOccupationCode());
            assertTrue(city1Residents.get(0).isFemale());
            assertEquals("Chandler", city1Residents.get(1).getName());
            assertEquals("Holiday Inn", city1Residents.get(1).getWorkingLocation());
            assertEquals(25, city1Residents.get(1).getAge());
            assertEquals(0, city1Residents.get(1).getOccupationCode());
            assertFalse(city1Residents.get(1).isFemale());

            //check city 1 hotel info
            assertEquals(2, city1Hotels.size());
            assertEquals("Holiday Inn", city1Hotels.get(0).getBusinessName());
            assertEquals("Another Hotel", city1Hotels.get(1).getBusinessName());
            assertEquals(2, holidayInnGuests.size());
            assertEquals(10, holidayInn.getRoomNumbers().size());
            assertEquals(2, holidayInn.getBookedRoomNumbers().size());
            assertTrue(holidayInn.isBusinessOpen());
            assertEquals(8, holidayInn.getAvailableRooms());

            //check city 2 name and residents info
            City city2Read = citiesRead.get(1);
            assertEquals("Toronto", city2Read.getCityName());
            assertEquals(0, city2Read.getHotels().size());
            assertEquals(2, city2Read.getResidents().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
