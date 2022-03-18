package ui.swing.simulators;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SwingCityRevolution {

    private int currentHotel;
    private int currentResident;
    private City city;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private final String destination = "./data/WriterCitiesSwing.json";

    // EFFECTS: initiates all simulators and runs the city revolution application
    public SwingCityRevolution() {
        city = null;
    }

    public void addNewCity(City city) {
        this.city = city;
        currentHotel = -1;
        currentResident = -1;
    }

    public void addNewResident(Resident resident) {
        city.addResident(resident);
        city.getBank().createAccountForResident(resident, 2000);
    }

    public void selectResident(int residentIndex) {
        currentResident = residentIndex;
    }

    public void addNewHotel(Hotel hotel) {
        city.addHotel(hotel);
    }

    public Resident getSelectedResident() {
        return city.getResidents().get(currentResident);
    }

    public City getCity() {
        return city;
    }

    public void changeCityName(String name) {
        city.setCityName(name);
    }

    public void changeCityTheme(City.Theme theme) {
        city.setTheme(theme);
    }

    public void selectHotel(int hotelIndex) {
        currentHotel = hotelIndex;
    }

    public Hotel getSelectedHotel() {
        return city.getHotels().get(currentHotel);
    }

    public boolean openCurrentHotel() {
        Hotel hotel = city.getHotels().get(currentHotel);
        return hotel.openBusiness();
    }

    public void closeCurrentHotel() {
        Hotel hotel = city.getHotels().get(currentHotel);
        hotel.closeHotel();
    }

    public void assignJob(Business business, Resident resident) {
        business.addStaff(resident);
        Bank bank = city.getBank();
        bank.initializeSES();
        bank.createEarnings(resident, business.getSalary());
    }

    public void saveCityToJson() throws FileNotFoundException {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(city);

        JsonWriter jsonWriter = new JsonWriter(destination);
        jsonWriter.open();
        jsonWriter.write(cities);
        jsonWriter.close();
    }

    public void loadCity() throws IOException {
        jsonReader = new JsonReader(destination);
        ArrayList<City> citiesLoaded = jsonReader.read();
        city = citiesLoaded.get(0);

    }
}
