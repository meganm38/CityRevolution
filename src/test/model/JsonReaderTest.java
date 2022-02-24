package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ArrayList<City> cities = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCity() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyCity.json");
        try {
            ArrayList<City> cities = reader.read();
            assertEquals(1, cities.size());
            City city = cities.get(0);
            assertEquals("Vancouver", city.getCityName());
            assertEquals(0, city.getResidents().size());
            assertEquals(0, city.getHotels().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCities() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralCity.json");
        try {
            ArrayList<City> cities = reader.read();
            assertEquals("Vancouver", cities.get(0).getCityName());
            assertEquals("Toronto", cities.get(1).getCityName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
