package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BankTest {
    static final int ONE_SECOND = 1000;
    Bank bank;
    Resident resident1;
    Resident resident2;

    @BeforeEach
    public void setup() {
        bank = new Bank("Test Bank");
        resident1 = new Resident("Lisa", true, 20);
        resident2 = new Resident("Bob", false, 20);

    }

    @Test
    public void testConstructor() {
        assertEquals("Test Bank", bank.getBankName());
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    public void testCreateAccount() {
        bank.createAccountForResident(resident1);
        bank.createAccountForResident(resident2);

        assertEquals(2000, bank.getAccounts().get(resident1.getName()));
        assertEquals(2000, bank.getAccounts().get(resident2.getName()));
    }

    @Test
    public void testEarnings() throws InterruptedException {
        bank.createAccountForResident(resident1);
        bank.createAccountForResident(resident2);

        bank.initializeSES();
        bank.createEarnings(resident1, 10);
        Thread.sleep(ONE_SECOND);
        bank.stopGenerateEarnings();
        assertTrue(bank.getAccounts().get(resident1.getName()) > 2000);
    }
}
