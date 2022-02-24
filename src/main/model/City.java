package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a city. Users can create multiple cities and add contents to each one.
public class City implements Writable {
    private String cityName;
    private ArrayList<Hotel> hotels;
    private ArrayList<Resident> residents;
    private Bank bank;
    private ArrayList<Business> businesses;

    /*
     * REQUIRES: cityName is not null
     * EFFECTS: Constructs a city with no contents
     */
    public City(String cityName) {
        this.cityName = cityName;
        hotels = new ArrayList<>();
        residents = new ArrayList<>();
        businesses = new ArrayList<>();
        bank = new Bank("Bank of " + cityName);
    }

    /*
     * MODIFIES: this
     * EFFECTS: add a hotel to the city
     */
    public void addHotel(Hotel hotel) {
        hotels.add(hotel);
        businesses.add(hotel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: add a resident to the city
     */
    public void addResident(Resident resident) {
        residents.add(resident);
    }

    // EFFECTS: returns a city as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Hotels", hotelsToJson());
        json.put("City Name", cityName);
        json.put("Residents", residentsToJson());
        json.put("Bank Accounts", bank.toJson());
        return json;
    }

    // EFFECTS: returns residents in a city as Json Array
    public JSONArray residentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Resident resident : residents) {
            jsonArray.put(resident.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns hotels in a city as Json Array
    public JSONArray hotelsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Hotel hotel : hotels) {
            jsonArray.put(hotel.toJson());
        }
        return jsonArray;
    }


    /*
     * getters
     */
    public String getCityName() {
        return cityName;
    }

    public ArrayList<Hotel> getHotels() {
        return hotels;
    }

    public ArrayList<Resident> getResidents() {
        return residents;
    }

    public Bank getBank() {
        return bank;
    }

    public ArrayList<Business> getBusinesses() {
        return businesses;
    }
}
