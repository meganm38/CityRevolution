package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals("Unemployed", resident1.getWorkingLocation());
        assertEquals(0, resident1.getSalary());
    }

    @Test
    public void testSetOccupation() {
        resident1.setOccupation(0, "Hotel", 1);
        assertEquals(0, resident1.getOccupationCode());
        assertEquals("Hotel", resident1.getWorkingLocation());
        assertEquals(1, resident1.getSalary());
    }
}
