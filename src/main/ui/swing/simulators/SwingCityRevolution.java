package ui.swing.simulators;

import model.City;
import model.Hotel;
import model.Resident;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.ArrayList;

public class SwingCityRevolution {

    private final ArrayList<City> cities;
    private int currentCity;
    private int currentHotel;
    private int currentResident;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private final String destination = "./data/WriterCitiesSwing.json";

    // EFFECTS: initiates all simulators and runs the city revolution application
    public SwingCityRevolution() {
        cities = new ArrayList<>();
        currentCity = -1;
    }

    public void addNewCity(City city) {
        cities.add(city);
        currentCity = cities.size() - 1;
        currentHotel = -1;
        currentResident = -1;
    }

    public void addNewResident(Resident resident) {
        City city = cities.get(currentCity);
        city.addResident(resident);
        city.getBank().createAccountForResident(resident, 2000);
    }

    public void selectResident(int residentIndex) {
        currentResident = residentIndex;
    }

    public void addNewHotel(Hotel hotel) {
        City city = cities.get(currentCity);
        city.addHotel(hotel);
    }

    public Resident getSelectedResident() {
        City city = cities.get(currentCity);
        return city.getResidents().get(currentResident);
    }

    public City getCurrentCity() {
        return cities.get(currentCity);
    }

    public void changeCityName(String name) {
        City city = cities.get(currentCity);
        city.setCityName(name);
    }

    public void changeCityTheme(City.Theme theme) {
        City city = cities.get(currentCity);
        city.setTheme(theme);
    }

    public void selectHotel(int hotelIndex) {
        currentHotel = hotelIndex;
    }

    public Hotel getSelectedHotel() {
        City city = cities.get(currentCity);
        return city.getHotels().get(currentHotel);
    }

    public boolean openCurrentHotel() {
        City city = cities.get(currentCity);
        Hotel hotel = city.getHotels().get(currentHotel);
        return hotel.openBusiness();
    }

    public void closeCurrentHotel() {
        City city = cities.get(currentCity);
        Hotel hotel = city.getHotels().get(currentHotel);
        hotel.closeHotel();
    }
}
