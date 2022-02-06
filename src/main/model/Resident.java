package model;

// Represents residents living in a city.
public class Resident {

    private String name;
    private boolean isFemale;
    private int age;
    private int occupationCode;//-1 == no occupation, 0 == working at hotel, 1 == working at grocery store

    /*
     * REQUIRES: name is not null AND age > 0
     * EFFECTS: Construct a resident with the given name, sex and age and no occupation
     */
    public Resident(String name, boolean isFemale, int age) {
        this.name = name;
        this.isFemale = isFemale;
        this.age = age;
        this.occupationCode = -1;
    }

    /*
     * REQUIRES: code has a corresponding occupation
     * MODIFIES: this
     * EFFECTS: changes a resident's occupation code to code
     */
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
