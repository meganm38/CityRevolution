package ui;

import model.Bank;
import model.Resident;

public class BankSimulator {
    protected Bank bank;

    protected BankSimulator(Bank bank) {
        this.bank = bank;
    }

    protected int getResidentAccountBalance(Resident resident) {
        return 0;
    }
}
