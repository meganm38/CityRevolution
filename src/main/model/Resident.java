package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents residents living in a city.
public class Resident implements Writable {

    private String name;
    private boolean isFemale;
    private int age;
    private int occupationCode;//-1 == no occupation, 0 == working at hotel, 1 == working at grocery store
    private String workingLocation;

    /*
     * REQUIRES: name is not null AND age > 0
     * EFFECTS: Construct a resident with the given name, sex and age and no occupation
     */
    public Resident(String name, boolean isFemale, int age) {
        this.name = name;
        this.isFemale = isFemale;
        this.age = age;
        this.occupationCode = -1;
        workingLocation = "Unemployed";
    }

    /*
     * REQUIRES: code has a corresponding occupation
     * MODIFIES: this
     * EFFECTS: changes a resident's occupation code to code
     */
    public void setOccupation(int code, String workingLocation) {
        this.occupationCode = code;
        this.workingLocation = workingLocation;
    }

    // EFFECTS: returns a resident as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("Working Location", workingLocation);
        jsonObject.put("Occupation Code", occupationCode);
        jsonObject.put("Sex", isFemale ? "Female" : "Male");
        jsonObject.put("Age", age);
        jsonObject.put("Name", name);

        return jsonObject;
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

    public String getWorkingLocation() {
        return workingLocation;
    }
}
