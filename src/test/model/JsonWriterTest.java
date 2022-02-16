package model;

import org.junit.jupiter.api.Test;
import persistence.JsonWriter;

import java.io.IOException;
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

            Hotel hotel = new Hotel("Holiday Inn");
            city.addHotel(hotel);
            hotel.addRooms(10);
            hotel.makeBooking(2, resident1);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCity.json");
            writer.open();
            writer.write(city);
            writer.close();

//            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
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
