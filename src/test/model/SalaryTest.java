package model;

import org.junit.jupiter.api.Test;

import static model.Salary.*;
import static org.junit.jupiter.api.Assertions.*;

public class SalaryTest {

    @Test
    public void testConstructor() {
        assertEquals(1, HOTEL.salary());
    }
}
