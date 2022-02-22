package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
    void testWriterGeneralCity() {
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
            assertEquals("Vancouver", city1Read.getCityName());
            ArrayList<Hotel> city1Hotels = city1Read.getHotels();
            assertEquals(2, city1Hotels.size());
            assertEquals("Holiday Inn", city1Hotels.get(0).getBusinessName());
            assertEquals("Another Hotel", city1Hotels.get(1).getBusinessName());



//            city = reader.read();
//            assertEquals("My work room", city.getName());
//            List<Thingy> thingies = city.getThingies();
//            assertEquals(2, thingies.size());
//            checkThingy("saw", Category.METALWORK, thingies.get(0));
//            checkThingy("needle", Category.STITCHING, thingies.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
