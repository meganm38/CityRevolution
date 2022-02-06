package model;

import java.util.ArrayList;

// Represents a city. Users can create multiple cities and add contents to each one.
public class City {
    private String cityName;
    private ArrayList<Hotel> hotels;
    private ArrayList<Resident> residents;
    private Bank bank;

    /*
     * EFFECTS: Construct a city with no contents
     */
    public City(String cityName) {
        this.cityName = cityName;
        hotels = new ArrayList<>();
        residents = new ArrayList<>();
        bank = new Bank("Bank of " + cityName);
    }

    /*
     * MODIFIES: this
     * EFFECTS: add a hotel to the city
     */
    public void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: add a resident to the city
     */
    public void addResident(Resident resident) {
        residents.add(resident);
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
}
