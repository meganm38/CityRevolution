package model;

public class Resident {

    private String name;
    private boolean isFemale;
    private int age;
    private int occupationCode;//-1 == no occupation, 0 == working at hotel, 1 == working at grocery store
    private int accountBalance;

    /*
     * REQUIRES:
     * EFFECTS: Construct a resident with the given name, sex and age and no occupation
     */
    public Resident(String name, boolean isFemale, int age) {
        this.name = name;
        this.isFemale = isFemale;
        this.age = age;
        this.occupationCode = -1;
    }

    public void setOccupationCode(int code) {
        this.occupationCode = code;
    }


    /*
     * getters
     */
    public String getName() {
        return name;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public int getAge() {
        return age;
    }

    public int getOccupationCode() {
        return occupationCode;
    }


}
