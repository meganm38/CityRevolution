package persistence;


import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads cities from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads cities information from source file and returns them as a list of cities
    public ArrayList<City> read() throws IOException {
        ArrayList<City> cities = new ArrayList<>();
        String jsonData = readFile(source);

        JSONArray jsonArray = new JSONArray(jsonData);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            cities.add(parseCity(jsonObject));
        }
        return cities;
    }

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }


    // EFFECTS: parses city from JSON object and returns it
    private City parseCity(JSONObject jsonObject) {
        String cityName = jsonObject.getString("City Name");
        String cityTheme = jsonObject.getString("Theme");

        City city = new City(cityName, cityTheme.equals("LIGHT") ? City.Theme.LIGHT : City.Theme.DARK);
        addResidentsToCity(city, jsonObject);
        addHotelToCity(city, jsonObject);
        addBankAccount(city, jsonObject);
        return city;
    }

    //MODIFIES: city
    //EFFECTS: parses hotels from JSON object and adds them to city
    private void addHotelToCity(City city, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Hotels");
        for (Object object : jsonArray) {
            JSONObject hotelJson = (JSONObject) object;
            Hotel hotel = new Hotel(hotelJson.getString("Hotel Name"),
                    hotelJson.getInt("Hotel Star"),
                    hotelJson.getString("Theme").equals("SKI") ? Hotel.Theme.SKI : Hotel.Theme.BEACH);
            addStaffToBusiness(hotel, city, hotelJson);
            addRoomNumbersToHotel(hotel, hotelJson);
            addGuestsToHotel(hotel, city, hotelJson);
            setHotelOpenStatus(hotel, hotelJson);
            city.addHotel(hotel);
        }
    }

    //MODIFIES: hotel
    //EFFECTS: parses hotel staff from JSON object and adds them to hotel
    private void addStaffToBusiness(Business business, City city, JSONObject hotelJson) {
        JSONArray jsonArray = hotelJson.getJSONArray("Staff");
        ArrayList<Resident> residents = city.getResidents();
        for (Object object : jsonArray) {
            JSONObject staffJson = (JSONObject) object;
            String staffName = staffJson.getString("name");
            int age = staffJson.getInt("age");
            boolean sex = staffJson.getBoolean("female");
            Resident resident = new Resident(staffName, sex, age);
            resident.setOccupation(staffJson.getInt("occupationCode"
            ), staffJson.getString("workingLocation"), staffJson.getInt("salary"));
            business.addStaff(resident);
        }
    }

    //MODIFIES: hotel
    //EFFECTS: parses room numbers from JSON object and adds them to hotel
    private void addRoomNumbersToHotel(Hotel hotel, JSONObject hotelJson) {
        JSONArray jsonArray = hotelJson.getJSONArray("Room Numbers");
        int num = jsonArray.length();
        hotel.addRooms(num);
    }

    //MODIFIES: hotel
    //EFFECTS: parses hotel guests from JSON object and adds them to hotel
    private void addGuestsToHotel(Hotel hotel, City city, JSONObject hotelJson) {
        JSONArray jsonArray = hotelJson.getJSONArray("Guests");
        ArrayList<Resident> residents = city.getResidents();
        for (Object object : jsonArray) {
            JSONObject guestJson = (JSONObject) object;
            String guestName = guestJson.getString("name");
            for (Resident resident : residents) {
                if (guestName.equalsIgnoreCase(resident.getName())) {
                    hotel.makeBooking(1, resident);
                }
            }
        }

    }

    //MODIFIES: hotel
    //EFFECTS: parses hotel open status from JSONObject and sets hotel open status
    private void setHotelOpenStatus(Hotel hotel, JSONObject hotelJson) {
        boolean isOpen = hotelJson.getBoolean("Is Open");
        if (isOpen) {
            hotel.openBusiness();
        }
    }

    //MODIFIES: city
    //EFFECTS: parses residents from JSON object and adds them to city
    private void addResidentsToCity(City city, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Residents");

        for (Object object : jsonArray) {
            JSONObject residentJson = (JSONObject) object;
            String name = residentJson.getString("Name");
            int age = residentJson.getInt("Age");
            boolean sex = residentJson.getString("Sex").equals("Female");
            Resident resident = new Resident(name, sex, age);
            resident.setOccupation(residentJson.getInt("Occupation Code"
            ), residentJson.getString("Working Location"), residentJson.getInt("Salary"));
            city.addResident(resident);
        }
    }

    //MODIFIES: city
    //EFFECTS: parses bank accounts from JSON object and adds them to city;
    //         initializes SES for residents who have a job
    private void addBankAccount(City city, JSONObject jsonObject) {
        Bank bank = city.getBank();
        ArrayList<Resident> residents = city.getResidents();
        JSONObject bankJson = jsonObject.getJSONObject("Bank Accounts");
        for (Resident resident : residents) {
            bank.createAccountForResident(resident, bankJson.getInt(resident.getName()));
            if (resident.getOccupationCode() != -1) {
                bank.initializeSES();
                bank.createEarnings(resident, resident.getSalary());
            }
        }
    }
}
