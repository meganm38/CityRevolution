package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * A bank is automatically created every time a city is created. Every resident automatically is set up with an account
 * in the bank.
 */
public class Bank implements Writable {
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
     * EFFECTS: add an account for resident and give it an initial balance
     */
    public void createAccountForResident(Resident resident) {
        accounts.put(resident.getName(), INITIAL_BALANCE);
    }

    /*
     * REQUIRES: resident cannot be NULL, balance >= 0
     * MODIFIES: this
     * EFFECTS: add an account for resident and set its balance = balance
     */
    public void createAccountForResident(Resident resident, int balance) {
        accounts.put(resident.getName(), balance);
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
     * EFFECTS: runs addBalance() every second in the background
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

    // EFFECTS: returns bank accounts as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        Iterator it = accounts.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pairs = (HashMap.Entry)it.next();
            jsonObject.put((String) pairs.getKey(), pairs.getValue());
        }
        return jsonObject;
    }
}
