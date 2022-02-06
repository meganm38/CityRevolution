package model;

import org.junit.jupiter.api.Test;

import static model.BusinessInfo.*;
import static org.junit.jupiter.api.Assertions.*;

public class BusinessInfoTest {

    @Test
    public void testConstructor() {
        assertEquals(1, HOTEL.salary());
        assertEquals(0, HOTEL.occupationCode());
        assertEquals("hotel", HOTEL.businessType());
    }
}
