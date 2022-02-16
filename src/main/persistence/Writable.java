package persistence;

import org.json.JSONObject;

//Represents a common Writable function of objects
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
