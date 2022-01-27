package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ResidentTest {
    public Resident resident1;

    @BeforeEach
    public void setup() {
        resident1 = new Resident("Megan", true, 25);
    }

    @Test
    public void testConstructor() {
        assertEquals("Megan", resident1.getName());
        assertTrue(resident1.isFemale());
        assertEquals(25, resident1.getAge());
        assertEquals(-1, resident1.getOccupationCode());
    }
}
