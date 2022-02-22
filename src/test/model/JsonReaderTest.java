package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ArrayList<City> cities =  reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }
    @Test
    void test() throws IOException {
        JsonReader reader = new JsonReader("./data/testWriterGeneralCity.json");
        reader.read();

        ArrayList<City> cities =  reader.read();
        assertEquals("Vancouver", cities.get(0).getCityName());
        assertEquals("Toronto", cities.get(1).getCityName());
    }
}
