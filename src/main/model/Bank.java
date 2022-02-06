package model;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Bank {
    private static final int TIME_UNIT_IN_SECONDS = 1;
    private static final int INITIAL_BALANCE = 2000;

    private String bankName;
    private HashMap<String, Integer> accounts;
    private ScheduledExecutorService timerService;

    /*
     * REQUIRES: bankName has a non-zero length
     * EFFECTS: construct a bank which has the name given by bankName;
     *          bank has no accounts.
     */
    public Bank(String bankName) {
        this.bankName = bankName;
        accounts = new HashMap<>();
    }

    /*
     * REQUIRES: resident cannot be NULL
     * MODIFIES: this
     * EFFECTS: creates an account for resident and give it an initial balance
     */
    public void createAccountForResident(Resident resident) {
        accounts.put(resident.getName(), INITIAL_BALANCE);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes Scheduled Executor Service
     */
    public void initializeSES() {
        timerService = Executors.newSingleThreadScheduledExecutor();
    }

    /*
     * REQUIRES: resident cannot be NULL
     * MODIFIES: this
     * EFFECTS: adds salaryPerSecond to resident's account balance
     */
    public void addBalance(Resident resident, int salaryPerSecond) {
        int currBalance = accounts.get(resident.getName());
        accounts.put(resident.getName(), currBalance + salaryPerSecond);
    }

    /*
     * REQUIRES: resident cannot be NULL
     * MODIFIES: this
     * EFFECTS: adds salaryPerSecond to resident's balance every second in the background
     */
    public void createEarnings(Resident resident, int salaryPerSecond) {
        timerService.scheduleAtFixedRate(() -> addBalance(resident, salaryPerSecond),
                0, TIME_UNIT_IN_SECONDS, TimeUnit.SECONDS);
    }

    /*
     * MODIFIES: this
     * EFFECTS: shuts down Scheduled Executor Service
     */
    public void stopGenerateEarnings() {
        timerService.shutdown();
    }

    // getters
    public HashMap<String, Integer> getAccounts() {
        return accounts;
    }

    public String getBankName() {
        return bankName;
    }
}
