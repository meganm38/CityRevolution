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
            City wr = new City("Vancouver", City.Theme.LIGHT);
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
            City city = new City("Vancouver", City.Theme.LIGHT);
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
            assertEquals(City.Theme.LIGHT, city.getTheme());

        } catch (IOException e) {
            fail("Exception shouldn't have been thrown");
        }
    }

    @Test
    void testWriter2Cities() {
        try {
            City city = new City("Vancouver", City.Theme.LIGHT);
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
            hotel1.openBusiness();

            Hotel hotel2 = new Hotel("Another Hotel");
            city.addHotel(hotel2);

            City city2 = new City("Toronto", City.Theme.LIGHT);
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
            ArrayList<Resident> city1Residents = city1Read.getResidents();

            //check city 1 name and residents info
            assertEquals("Vancouver", city1Read.getCityName());
            checkResidentsInfo(resident1, city1Residents.get(0));
            checkResidentsInfo(resident2, city1Residents.get(1));

            //check city 1 bank accounts info
            Thread.sleep(1000);
            assertTrue(city1Read.getBank().getAccounts().get("Monica") > 2000);
            assertTrue(city1Read.getBank().getAccounts().get("Chandler") == 2000);


            //check city 1 hotels info
            checkHotelInfo(hotel1, city1Hotels.get(0));
            checkHotelInfo(hotel2, city1Hotels.get(1));

            //check city 2 info
            City city2Read = citiesRead.get(1);
            ArrayList<Resident> city2Residents = city2Read.getResidents();
            assertEquals("Toronto", city2Read.getCityName());
            assertEquals(2, city2Read.getResidents().size());
            checkResidentsInfo(resident3, city2Residents.get(0));
            checkResidentsInfo(resident4, city2Residents.get(1));
            assertEquals(0, city2Read.getHotels().size());
            assertEquals(city2.getBank().getAccounts(), city2Read.getBank().getAccounts());


        } catch (IOException | InterruptedException e) {
            fail("Exception should not have been thrown");
        }
    }

    void checkResidentsInfo(Resident expectedResident, Resident residentRead) {
        assertEquals(expectedResident.getName(), residentRead.getName());
        assertEquals(expectedResident.getOccupationCode(), residentRead.getOccupationCode());
        assertEquals(expectedResident.getAge(), residentRead.getAge());
        assertEquals(expectedResident.getWorkingLocation(), residentRead.getWorkingLocation());
        assertEquals(expectedResident.isFemale(), residentRead.isFemale());
        assertEquals(expectedResident.getSalary(), residentRead.getSalary());
    }

    void checkHotelInfo(Hotel expectedHotel, Hotel hotelRead) {
        assertEquals(expectedHotel.getBusinessName(), hotelRead.getBusinessName());
        assertEquals(expectedHotel.getAvailableRooms(), hotelRead.getAvailableRooms());
        assertEquals(expectedHotel.getBookingInfo(), hotelRead.getBookingInfo());
        assertEquals(expectedHotel.getRoomNumbers(), hotelRead.getRoomNumbers());
        assertEquals(expectedHotel.isBusinessOpen(), hotelRead.isBusinessOpen());
        assertEquals(expectedHotel.getSalary(), hotelRead.getSalary());
        assertEquals(expectedHotel.getBookedRoomNumbers(), hotelRead.getBookedRoomNumbers());
        assertEquals(expectedHotel.getBookingInfo(), hotelRead.getBookingInfo());

        ArrayList<Resident> expectedStaff = expectedHotel.getStaff();
        ArrayList<Resident> readStaff = hotelRead.getStaff();
        for (int i = 0; i < expectedStaff.size(); i++) {
            checkResidentsInfo(expectedStaff.get(i), readStaff.get(i));
        }

        ArrayList<Resident> expectedGuests = expectedHotel.getStaff();
        ArrayList<Resident> readGuests = hotelRead.getStaff();
        for (int i = 0; i < expectedGuests.size(); i++) {
            checkResidentsInfo(expectedGuests.get(i), readGuests.get(i));
        }
    }
}
