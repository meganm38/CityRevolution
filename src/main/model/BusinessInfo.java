package model;

// Storing final information about business-type contents such as occupation code
public enum BusinessInfo {
    HOTEL("hotel", 1, 0);

    private final String businessType;
    private final int salary;
    private final int occupationCode;

    /*
     * EFFECTS: constructs an info with business type, salary per second, and occupation code
     */
    BusinessInfo(final String businessType, final int salary, final int occupationCode) {
        this.businessType = businessType;
        this.salary = salary;
        this.occupationCode = occupationCode;
    }

    //getters
    public int salary() {
        return salary;
    }

    public int occupationCode() {
        return occupationCode;
    }

    public String businessType() {
        return businessType;
    }
}
