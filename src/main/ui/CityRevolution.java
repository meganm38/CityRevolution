package ui;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

// City revolution Game.
public class CityRevolution {
    private Scanner scanner;
    private ArrayList<City> cities;
    private int currentCity;
    private int currentHotel;
    private HotelSimulator hotelSimulator;
    private ResidentSimulator residentSimulator;

    // EFFECTS: runs the simulator application
    public CityRevolution() {
        cities = new ArrayList<>();
        scanner = new Scanner(System.in);
        hotelSimulator = new HotelSimulator();
        residentSimulator = new ResidentSimulator();
        currentCity = -1;
        currentHotel = -1;
        runSimulator();
    }

    // EFFECTS: displays opening message and starts the main menu control
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
            for (City city : cities) {
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

    /********************************************
     * Add contents to the current city *
     ********************************************/

    // MODIFIES: this
    // EFFECTS: prompts the users to enter a hotel name that hasn't been used before;
    //          adds a new hotel with this name to the current city that the user has open
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
                if (hotelName.equalsIgnoreCase(hotel.getBusinessName())) {
                    nameAlreadyExisted = true;
                    break;
                }
            }
        } while (nameAlreadyExisted);

        Hotel hotel = new Hotel(hotelName);
        myCity.addHotel(hotel);
        currentHotel = myCity.getHotels().size() - 1;
        System.out.println("\n" + hotelName + " has been successfully added to the city of "
                + myCity.getCityName() + ".");
    }

    // MODIFIES: this
    // EFFECTS: prompts the users to enter resident's info;
    //          adds a resident to the current city that the user has open
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
        Resident newResident = new Resident(residentName, isFemale, age);
        myCity.addResident(newResident);
        myCity.getBank().createAccountForResident(newResident);
        System.out.println(residentName + " has been successfully added to the city of " + myCity.getCityName() + ".");
    }

    /********************************************
     * Main Menu Control *
     * Users can - build a new city
     *           - open an existing city
     ********************************************/

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
                setCurrentCity();
                useSecondaryMenu();
                break;
            case "q":
                terminate();
            default:
                processMainMenuCommand(processInvalidCommand());
                break;
        }
    }

    /********************************************
     * Secondary Menu Control *
     * Users can - Add contents to city
     *           - Browse contents
     ********************************************/

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
            case "d":
                terminate();
            default:
                processSecondaryCommand(processInvalidCommand());
                break;
        }
    }

    // EFFECTS: display secondary menu once a city has been created
    private void displaySecondaryMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add contents to your city");
        System.out.println("\tb -> Browse contents of my city");
        System.out.println("\tc -> Return to the main menu");
        System.out.println("\td -> Quit");
    }

    /********************************************
     * Contents Menu Control *
     * Users can - Add hotel
     *           - Add resident
     ********************************************/

    private void useContentsMenu() {
        displayContentsMenu();
        processContentsCommand(scanner.next());
    }

    private void displayContentsMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Hotel");
        System.out.println("\tb -> Resident");
        System.out.println("\tc -> Return to the previous menu");
        System.out.println("\td -> Return to the main menu");
        System.out.println("\te -> Quit");
    }

    private void processContentsCommand(String command) {
        switch (command) {
            case "a":
                doAddNewHotelToCity();
                useHotelMenu();
                break;
            case "b":
                doAddResidentToCity();
                useSecondaryMenu();
                break;
            case "c":
                useSecondaryMenu();
                break;
            case "d":
                useMainMenu();
                break;
            case "e":
                terminate();
            default:
                processContentsCommand(processInvalidCommand());
        }
    }

    /********************************************
     * Hotel Menu Control *
     * Users can - Open hotel for business
     *           - Create hotel rooms
     *           - Add staff
     *           - Manage hotel bookings
     ********************************************/

    private void useHotelMenu() {
        displayHotelMenu();
        processHotelCommand(scanner.next());
    }

    private void displayHotelMenu() {
        City myCity = cities.get(currentCity);
        System.out.println("\nYou are currently in " + myCity.getHotels().get(currentHotel).getBusinessName() + ".");
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Open hotel for business!");
        System.out.println("\tb -> Create hotel rooms");
        System.out.println("\tc -> Add staff to hotel");
        System.out.println("\td -> Manage hotel bookings");
        System.out.println("\te -> Return to previous menu");
//        System.out.println("\tf -> Return to main menu");
        System.out.println("\tq -> quit");
    }

    private void processHotelCommand(String command) {
        Hotel hotel = cities.get(currentCity).getHotels().get(currentHotel);
        switch (command) {
            case "a":
                hotelSimulator.openHotelForBusiness(hotel);
                break;
            case "b":
                hotelSimulator.createHotelRooms(hotel);
                break;
            case "c":
                addStaffToBusiness(hotel);
                break;
            case "d":
                useManageHotelsMenu(hotel);
                break;
            case "e":
                useSecondaryMenu();
                break;
//            case "f":
//                useMainMenu();
//                break;
            case "q":
                terminate();
//            default:
//                processHotelCommand(processInvalidCommand());
        }
        useHotelMenu();
    }

    /********************************************
     * Hotel Management Control *
     * Users can - Add bookings
     *           - Display bookings
     ********************************************/

    private void useManageHotelsMenu(Hotel hotel) {
        displayManageHotelMenu();
        processManageHotelCommands(hotel);
    }

    private void displayManageHotelMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add bookings");
        System.out.println("\tb -> Display all bookings");
        System.out.println("\tc -> Return to previous menu");
        System.out.println("\td -> Return to main menu");
        System.out.println("\te -> quit");
    }

    private void processManageHotelCommands(Hotel hotel) {
        String command = scanner.next();
        switch (command) {
            case "a":
                hotelSimulator.checkBookingConditions(hotel,cities.get(currentCity).getResidents());
                break;
            case "b":
                hotelSimulator.displayAllBookings(hotel);
                break;
            case "c":
                break;
            case "d":
                useMainMenu();
                break;
            case "e":
                terminate();
        }
        useHotelMenu();
    }

    /********************************************
     * Open Contents Control *
     * Users can - open bank
     *           - open hotel
     *           - open resident
     ********************************************/

    // EFFECTS: displays contents of a city
    private void displayContentsOfCity() {
        City city = cities.get(currentCity);
        System.out.println("The city of " + city.getCityName() + " has " + "1 bank, " + city.getHotels().size()
                + " hotels and " + city.getResidents().size() + " residents" + ".");
        System.out.println("\tSelect the content that you want to open:");
        System.out.println("\ta -> Bank");
        System.out.println("\tb -> Hotel");
        System.out.println("\tc -> Resident");
        System.out.println("\td -> Return to the previous menu");
        System.out.println("\te -> Quit");
        String command = scanner.next();
        openContentsOfCity(command);
    }

    private void openContentsOfCity(String command) {
        City city = cities.get(currentCity);
        switch (command) {
            case "a":
                System.out.println("This city has one centralized bank: " + city.getBank().getBankName() + ".");
                break;
            case "b":
                setCurrentHotel();
                break;
            case "c":
                boolean residentsPresent = residentSimulator.checkAndDisplayResidents(city.getResidents());
                if (residentsPresent) {
                    residentSimulator.displayResidentInfo(city.getResidents(), city.getBank());
                    useSecondaryMenu();
                }
                useSecondaryMenu();
                break;
            case "d":
                useSecondaryMenu();
                break;
            case "e":
                terminate();
//            default:
//                openContentsOfCity(processInvalidCommand());
//                break;
        }
    }

    /********************************************
     * Helper functions for job assignments *
     ********************************************/

    private void addStaffToBusiness(Business business) {
        City myCity = cities.get(currentCity);
        boolean enoughResidents = residentSimulator.checkAndDisplayResidents(myCity.getResidents());
        if (!enoughResidents) {
            useSecondaryMenu();
        } else {
            int residentIndex = scanner.nextInt();
            if (myCity.getResidents().get(residentIndex).getAge() < 19) {
                System.out.println("\nThis resident is not 19 years old yet...Returning you to the previous menu...");
            } else {
                checkJob(business, myCity.getResidents().get(residentIndex));
            }
        }
    }

    private void checkJob(Business business, Resident resident) {
        if (resident.getOccupationCode() == business.getOccupationCode()) {
            System.out.println("\nThis resident is already working here! Returning you to the previous menu...");
            useSecondaryMenu();
        } else if (resident.getOccupationCode() != -1) {
            String workingLocation = null;
            for (BusinessInfo businessInfo : BusinessInfo.values()) {
                workingLocation = resident.getOccupationCode() == businessInfo.occupationCode()
                        ? businessInfo.businessType() : null;
            }
            System.out.println("This resident is already working at a " + workingLocation + ".");
            System.out.println("Do you want to switch their occupation? y/n");
            if (scanner.next().equals("n")) {
                System.out.println("Returning you the previous menu...");
                useSecondaryMenu();
            } else {
                assignJob(business, resident);
            }
        } else {
            assignJob(business, resident);
        }
    }

    private void assignJob(Business business, Resident resident) {
        business.addStaff(resident);
        Bank bank = cities.get(currentCity).getBank();
        bank.initializeSES();
        bank.createEarnings(resident, business.getSalary());
        System.out.println("This resident is now working at " + business.getBusinessName() + "!");
    }

    /***********************************************************************
     * Helper functions for picking a current object of City, hotel, etc.*
     ***********************************************************************/
    //MODIFIES: this
    //EFFECTS: displays any existing cities that have been created by user if there are any; changes the current city
    //            to the city that user has picked
    private void setCurrentCity() {
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
        currentCity = scanner.nextInt();
        System.out.println("\n You are now in the city of " + cities.get(currentCity).getCityName() + ".");
    }

    //MODIFIES: this
    //EFFECTS: displays any existing hotel that have been created by user if there are any; changes the current hotel
    //            to the hotel that user has picked
    private void setCurrentHotel() {
        City myCity = cities.get(currentCity);
        boolean hotelsPresent = hotelSimulator.displayAllHotels(myCity.getHotels());
        if (!hotelsPresent) {
            useSecondaryMenu();
        } else {
            currentHotel = scanner.nextInt();
            useHotelMenu();
        }
    }

    /********************************************
     * Game management methods *
     ********************************************/

    // EFFECTS: terminates the game
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
