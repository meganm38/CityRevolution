package ui;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Simulator {
    private Scanner scanner;
    private ArrayList<City> cities;
    private boolean isNewestCity;

    // EFFECTS: runs the simulator application
    public Simulator() {
        cities = new ArrayList<>();
        scanner = new Scanner(System.in);
        isNewestCity = true;
        runSimulator();
    }

    private void runSimulator() {
        System.out.println("Welcome to City Revolution!");
        useMainMenu();
    }

    // MODIFIES: this
    // EFFECTS: add a new city to the cities list if the input city name has never been used before
    private void doAddNewCity() {
        System.out.println("Enter the name of your city: ");
        String name = scanner.nextLine();
        name = scanner.nextLine();
        boolean nameAlreadyExisted = false;

        do {
            if (nameAlreadyExisted) {
                System.out.println("You already have a city with the same name.");
                System.out.println("Please enter a new city name: ");
                name = scanner.next();
            }
            for (City city: cities) {
                if (city.getCityName().equalsIgnoreCase(name)) {
                    nameAlreadyExisted = true;
                    break;
                }
            }
        } while (nameAlreadyExisted);

        City city = new City(name);
        cities.add(city);
    }

    private void doAddNewHotelToCity(City city) {
        System.out.println("Enter the name of your hotel: ");
        String hotelName = scanner.nextLine();
        hotelName = scanner.nextLine();

        Hotel hotel = new Hotel(hotelName);
        city.addHotel(hotel);
        System.out.println(hotelName + " has been successfully added to the city of "
                + city.getCityName() + ".");
    }

    private void doOpenAnExistingCity() {
        String command = scanner.next();
    }

    // REQUIRES: there are cities created
    // EFFECTS: display any existing cities that have been created by user
    private void displayCities() {
        System.out.println("\nSelect from:");
        for (int i = 0; i < cities.size(); i++) {
            System.out.print("\t" + i + ". ");
            System.out.println(cities.get(i).getCityName());
        }
    }

    private void useMainMenu() {
        displayMainMenu();
        String newCommand = scanner.next();
        processMainMenuCommand(newCommand);
    }

    // EFFECTS: display menu of options to user
    private void displayMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> build a new city");
        System.out.println("\te -> open an existing city");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS:
    private void processMainMenuCommand(String command) {
        String newCommand;

        switch (command) {
            case "c":
                doAddNewCity();
                useSecondaryMenu();
                break;
            case "e":
                displayCities();
                doOpenAnExistingCity();
                newCommand = scanner.next();
            case "q":
                terminate();
            default:
                throw new IllegalStateException("Unexpected value: " + command);
        }
    }

    private void useSecondaryMenu() {
        displaySecondaryMenu();
        String newCommand = scanner.next();
        processSecondaryCommand(newCommand);
    }

    private void processSecondaryCommand(String command) {
        String newCommand;

        switch (command) {
            case "a":
                displayContentsMenu();
                newCommand = scanner.next();
                processContentsCommand(newCommand);
                break;
            case "c":
                displayContentsOfCity();
                break;
            case "m":
                useMainMenu();
                break;
            case "q":
                terminate();
        }
    }

    // EFFECTS: display secondary menu once a city has been created
    private void displaySecondaryMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add contents to your city");
        System.out.println("\tc -> display contents of my city");
        System.out.println("\tm -> return to the main menu");
        System.out.println("\tq -> quit");
    }

    private void displayContentsMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\th -> Hotel");
        System.out.println("\tr -> resident");
        System.out.println("\tp -> return to the previous menu");
        System.out.println("\tm -> return to the main menu");
        System.out.println("\tq -> quit");
    }

    private void processContentsCommand(String command) {
        switch (command) {
            case "h":
                City city = isNewestCity ? cities.get(cities.size() - 1) :
                doAddNewHotelToCity(city);
                useSecondaryMenu();
                break;
            case "m":
                break;
            case "r":
                break;
            case "q":
                terminate();
        }
    }

    // EFFECTS: displays contents of a city
    private void displayContentsOfCity(City city) {
        System.out.println("The city of " + city.getCityName() + " has " + city.getHotels().size()
                            + " hotels and " + city.getResidents().size() + " residents");
    }

    // EFFECTS: terminate the game
    private void terminate() {
        System.out.println("Thanks for playing. Bye!");
        System.exit(0);
    }
}
