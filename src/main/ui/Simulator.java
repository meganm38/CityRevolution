package ui;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Simulator {
    private Scanner scanner;
    private ArrayList<City> cities;
    private int currentCity;
    private int currentHotel;

    // EFFECTS: runs the simulator application
    public Simulator() {
        cities = new ArrayList<>();
        scanner = new Scanner(System.in);
        currentCity = -1;
        currentHotel = -1;
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
                name = scanner.nextLine();
            }
            nameAlreadyExisted = false;
            for (City city: cities) {
                if (city.getCityName().equalsIgnoreCase(name)) {
                    nameAlreadyExisted = true;
                    break;
                }
            }
        } while (nameAlreadyExisted);

        City city = new City(name);
        cities.add(city);
        currentCity = cities.size() - 1;
        System.out.println("The city of " + name + " has been successfully created!");
    }

    private void doAddNewHotelToCity() {
        City myCity = cities.get(currentCity);
        String hotelName;
        boolean nameAlreadyExisted;
        do {
            System.out.println("Enter the name of your hotel: ");
            hotelName = scanner.nextLine();
            hotelName = scanner.nextLine();

            nameAlreadyExisted = false;
            for (Hotel hotel : myCity.getHotels()) {
                if (hotelName.equalsIgnoreCase(hotel.getHotelName())) {
                    nameAlreadyExisted = true;
                    break;
                }
            }
        } while (nameAlreadyExisted);

        Hotel hotel = new Hotel(hotelName);
        myCity.addHotel(hotel);
        currentHotel = myCity.getHotels().size() - 1;
        System.out.println(hotelName + " has been successfully added to the city of "
                + myCity.getCityName() + ".");
    }

    private void doAddResidentToCity() {
        City myCity = cities.get(currentCity);
        String residentName;
        boolean nameAlreadyExisted;

        do {
            System.out.println("Enter the name of your new resident: ");
            residentName = scanner.nextLine();
            residentName = scanner.nextLine();

            nameAlreadyExisted = false;
            for (Resident resident : myCity.getResidents()) {
                if (residentName.equalsIgnoreCase(resident.getName())) {
                    nameAlreadyExisted = true;
                    break;
                }
            }
        } while (nameAlreadyExisted);

        System.out.println("How old is " + residentName + "?");
        int age = scanner.nextInt();
        System.out.println("Is " + residentName + " male or female? Enter m or f.");
        boolean isFemale = scanner.next().equalsIgnoreCase("f");

        myCity.addResident(new Resident(residentName, isFemale, age));
        System.out.println(residentName + " has been successfully added to the city of "
                + myCity.getCityName() + ".");
    }


    private void doOpenAnExistingCity() {
        System.out.println("You are now in the city of " + cities.get(currentCity).getCityName() + ".");
    }

    // REQUIRES: there are cities created
    // EFFECTS: display any existing cities that have been created by user
    private void displayCities() {
        if (cities.size() == 0) {
            System.out.println("You haven't created any cities. Please start by creating a city.");
            System.out.println("Returning you to the main menu...");
            useMainMenu();
        } else {
            System.out.println("\nSelect from:");
            for (int i = 0; i < cities.size(); i++) {
                System.out.print("\t" + i + ". ");
                System.out.println(cities.get(i).getCityName());
            }
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
        System.out.println("\ta -> build a new city");
        System.out.println("\tb -> open an existing city");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS:
    private void processMainMenuCommand(String command) {
        switch (command) {
            case "a":
                doAddNewCity();
                useSecondaryMenu();
                break;
            case "b":
                displayCities();
                currentCity = scanner.nextInt();
                doOpenAnExistingCity();
                useSecondaryMenu();
                break;
            case "q":
                terminate();
            default:
                processMainMenuCommand(processInvalidCommand());
                break;
        }
    }

    private void useSecondaryMenu() {
        displaySecondaryMenu();
        String newCommand = scanner.next();
        processSecondaryCommand(newCommand);
    }

    private void processSecondaryCommand(String command) {
        switch (command) {
            case "a":
                useContentsMenu();
                break;
            case "b":
                displayContentsOfCity();
                useSecondaryMenu();
                break;
            case "c":
                useMainMenu();
                break;
            case "q":
                terminate();
            default:
                processSecondaryCommand(processInvalidCommand());
                break;
        }
    }

    // EFFECTS: display secondary menu once a city has been created
    private void displaySecondaryMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add contents to your city");
        System.out.println("\tb -> display contents of my city");
        System.out.println("\tc -> return to the main menu");
        System.out.println("\tq -> quit");
    }

    private void useContentsMenu() {
        displayContentsMenu();
        processContentsCommand(scanner.next());
    }

    private void displayContentsMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Hotel");
        System.out.println("\tb -> Resident");
        System.out.println("\tc -> return to the previous menu");
        System.out.println("\td -> return to the main menu");
        System.out.println("\tq -> quit");
    }

    private void processContentsCommand(String command) {
        switch (command) {
            case "a":
                doAddNewHotelToCity();
                useHotelMenu();
                break;
            case "b":
                doAddResidentToCity();
                break;
            case "c":
                useSecondaryMenu();
                break;
            case "d":
                useMainMenu();
                break;
            case "q":
                terminate();
            default:
                processContentsCommand(processInvalidCommand());
        }
    }

    private void useHotelMenu() {
        displayHotelMenu();
        processHotelCommand(scanner.next());
    }

    private void displayHotelMenu() {
        City myCity = cities.get(currentCity);
        System.out.println("\n You are currently in " + myCity.getHotels().get(currentHotel).getHotelName() + ".");
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Open hotel for business!");
        System.out.println("\tb -> Create hotel rooms");
        System.out.println("\tc -> Add staff to hotel");
        System.out.println("\td -> Manage a different hotel");
        System.out.println("\te -> Return to previous menu");
        System.out.println("\tf -> Return to main menu");
        System.out.println("\tq -> quit");
    }

    private void processHotelCommand(String command) {
        Hotel hotel = cities.get(currentCity).getHotels().get(currentHotel);
        switch (command) {
            case "a":
                openHotelForBusiness(hotel);
                break;
            case "b":
                createHotelRooms(hotel);
                break;
            case "c":
                addStaffToHotel(hotel);
                break;
            case "d":

                break;
            case "e":
                useSecondaryMenu();
                break;
            case "f":
                useMainMenu();
                break;
            case "q":
                terminate();
            default:
                processHotelCommand(processInvalidCommand());
        }
        useHotelMenu();
    }

    private void openHotelForBusiness(Hotel hotel) {
        boolean openSuccessful;

        if (hotel.isHotelOpen()) {
            System.out.println("Your hotel " + hotel.getHotelName() + " is already open!");
        } else {
            openSuccessful = hotel.openHotel();
            if (openSuccessful) {
                System.out.println("Your hotel " + hotel.getHotelName() + " is now open for business!");
            } else {
                System.out.println(hotel.getHotelName() + " does not meet the requirements for opening.");
                System.out.println("You need to have staff and hotel rooms!");
                System.out.println("You currently have " + hotel.getRoomNumbers().size() + " hotel rooms and "
                        + hotel.getStaff().size() + " staff.");
                System.out.println("Returning you to the previous menu...");
            }
        }
    }

    private void createHotelRooms(Hotel hotel) {
        int numRooms;

        System.out.println("How many hotel rooms do you want to create?");
        do {
            numRooms = scanner.nextInt();
            if (numRooms <= 0) {
                System.out.println("You need to enter a positive integer number. Please enter again: ");
                numRooms = scanner.nextInt();
            }
        } while (numRooms <= 0);
        hotel.addRooms(numRooms);
        System.out.println("You have successfully created " + numRooms + " hotel rooms to your "
                + hotel.getHotelName() + " hotel!");
    }

    //TODO
    private void addStaffToHotel(Hotel hotel) {
        City myCity = cities.get(currentCity);
        int numResidents = myCity.getResidents().size();
        if (numResidents == 0) {
            System.out.println("The city of " + cities.get(currentCity).getCityName() + " doesn't have any residents!");
            System.out.println("Returning you to the previous menu...");
            useSecondaryMenu();
        } else {
            System.out.println("Choose from the following residents:");
            for (int i = 0; i < numResidents; i++) {
                System.out.print("\t" + i + ". ");
                System.out.println(myCity.getResidents().get(i));
            }
            int residentIndex = scanner.nextInt();
            if (myCity.getResidents().get(residentIndex).getAge() < 19) {
                System.out.println("This resident is not 19 years old yet...Returning you to the previous menu...");
                useHotelMenu();
            } else {
                hotel.addStaff(myCity.getResidents().get(residentIndex));
                System.out.println("This resident is now working at " + hotel.getHotelName() + "!");
            }
        }
    }

    //TODO
    private void manageAnotherHotel() {
        System.out.println("Select from the following list of hotels in the city of "
                + cities.get(currentCity).getHotels());
    }

    //TODO
    private void assignJob() {
        //stub
    }

    // EFFECTS: displays contents of a city
    private void displayContentsOfCity() {
        City city = cities.get(currentCity);
        System.out.println("The city of " + city.getCityName() + " has " + city.getHotels().size()
                            + " hotels and " + city.getResidents().size() + " residents" + ".");
    }

    // EFFECTS: terminate the game
    private void terminate() {
        System.out.println("Thanks for playing. Bye!");
        System.exit(0);
    }

    // EFFECTS: prompts the user to enter a new input if they previously entered an invalid command
    private String processInvalidCommand() {
        System.out.println("You entered an invalid command. Please enter again: ");
        return scanner.next();
    }
}
