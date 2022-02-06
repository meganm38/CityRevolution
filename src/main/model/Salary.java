package model;

// salary per minute working at different businesses
public enum Salary {
    HOTEL(1);

    private final int salary;

    Salary(final int salary) {
        this.salary = salary;
    }

    public int salary() {
        return salary;
    }
}
