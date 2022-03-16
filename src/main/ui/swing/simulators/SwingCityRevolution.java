package ui.swing.simulators;

import model.City;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.HotelSimulator;
import ui.ResidentSimulator;

import java.util.ArrayList;

public class SwingCityRevolution {

    private final ArrayList<City> cities;
    private int currentCity;
    private int currentHotel;
    private final HotelSimulator hotelSimulator;
    private final ResidentSimulator residentSimulator;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private final String destination = "./data/WriterCitiesSwing.json";

    // EFFECTS: initiates all simulators and runs the city revolution application
    public SwingCityRevolution() {
        cities = new ArrayList<>();
        hotelSimulator = new HotelSimulator();
        residentSimulator = new ResidentSimulator();
        currentCity = -1;
        currentHotel = -1;
    }

    public void addNewCity(City city) {
        cities.add(city);
        currentCity = cities.size() - 1;
    }
}
