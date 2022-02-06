package model;

// salary per minute working at different businesses
public enum BusinessInfo {
    HOTEL("hotel", 1, 0);

    private final String businessType;
    private final int salary;
    private final int occupationCode;

    BusinessInfo(final String businessType, final int salary, final int occupationCode) {
        this.businessType = businessType;
        this.salary = salary;
        this.occupationCode = occupationCode;
    }

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
