package ui;

import model.Bank;
import model.Resident;

// A bank simulator that hasn't been implemented.
public class BankSimulator {
    protected Bank bank;

    protected BankSimulator(Bank bank) {
        this.bank = bank;
    }

    protected int getResidentAccountBalance(Resident resident) {
        return 0;
    }
}
