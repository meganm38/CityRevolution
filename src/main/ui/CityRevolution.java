package ui;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

// City revolution Game. Users/players act as the planner and leader of a city and build it from scratch.
// Players can choose to add a variety of 'contents' to a city such as hotels, restaurants, grocery stores
// and residents to build a successful town.

public class CityRevolution {
    private final Scanner scanner;
    private final ArrayList<City> cities;
    private int currentCity;
    private int currentHotel;
    private final HotelSimulator hotelSimulator;
    private final ResidentSimulator residentSimulator;

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
        System.out.println("Welcome to City Revolution!\n"
                + "Here you can create multiple cities, add residents to your cities, build and manage businesses and"
                + " much more.");
        System.out.println("When you are done creating a city, a bank will be automatically added to the city. "
                + "Every resident that you add to the city will automatically have an account at this bank and "
                + "they will get 2000 dollars right away!");
        System.out.println("Your residents will spend money if they make purchases, but you can also make them"
                + " work at businesses to earn money.");
        System.out.println("Have fun!");
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
        System.out.println("The city of " + name + " has been successfully created! "
                + "Your city has one bank: Bank of " + name + "!");
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

    //EFFECTS: calls functions that display main menu, ask for input, and process that input
    private void useMainMenu() {
        displayMainMenu();
        String newCommand = scanner.next();
        processMainMenuCommand(newCommand);
    }

    // EFFECTS: displays main menu
    private void displayMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> build a new city");
        System.out.println("\tb -> open an existing city");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: calls functions according to user input
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

    //EFFECTS: calls functions that display secondary menu, ask for input, and process that input
    private void useSecondaryMenu() {
        displaySecondaryMenu();
        String newCommand = scanner.next();
        processSecondaryCommand(newCommand);
    }

    // EFFECTS: calls functions according to user input
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

    // EFFECTS: displays secondary menu
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

    //EFFECTS: calls functions that display contents menu, ask for input, and process that input
    private void useContentsMenu() {
        displayContentsMenu();
        processContentsCommand(scanner.next());
    }

    //EFFECTS: displays contents menu
    private void displayContentsMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Hotel");
        System.out.println("\tb -> Resident");
        System.out.println("\tc -> Return to the previous menu");
        System.out.println("\td -> Return to the main menu");
        System.out.println("\te -> Quit");
    }

    //EFFECTS: calls functions according to user input to process that input
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

    //EFFECTS: calls functions that display hotel menu, ask for input, and process that input
    private void useHotelMenu() {
        displayHotelMenu();
        processHotelCommand(scanner.next());
    }

    //EFFECTS: displays hotel menu
    private void displayHotelMenu() {
        City myCity = cities.get(currentCity);
        System.out.println("\nYou are currently in " + myCity.getHotels().get(currentHotel).getBusinessName() + ".");
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Open hotel for business!");
        System.out.println("\tb -> Create hotel rooms");
        System.out.println("\tc -> Manage staff");
        System.out.println("\td -> Manage hotel bookings");
        System.out.println("\te -> Return to previous menu");
//        System.out.println("\tf -> Return to main menu");
        System.out.println("\tq -> quit");
    }

    //EFFECTS: calls functions according to user input to process that input
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
                manageStaffMenu(hotel);
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

    //EFFECTS: calls functions that display hotel management menu, ask for input, and process that input
    private void useManageHotelsMenu(Hotel hotel) {
        displayManageHotelMenu();
        processManageHotelCommands(hotel);
    }

    //EFFECTS: displays hotel management menu
    private void displayManageHotelMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add bookings");
        System.out.println("\tb -> Display all bookings");
        System.out.println("\tc -> Return to previous menu");
        System.out.println("\td -> Return to main menu");
        System.out.println("\te -> quit");
    }

    //EFFECTS: calls functions according to user input to process that input
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

    //EFFECTS: calls functions according to user input to process that input
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
     * Staff Management Control *
     * Users can - open bank
     *           - open hotel
     *           - open resident
     ********************************************/

    //EFFECTS: displays options for managing staff
    private void manageStaffMenu(Business business) {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add staff");
        System.out.println("\tb -> Remove staff");
        System.out.println("\tc -> Return to previous menu");
        System.out.println("\td -> Return to main menu");
        System.out.println("\te -> Quit");
        String command = scanner.next();
        processManageStaffCommand(command, business);
    }

    //EFFECTS: calls functions to process user's input
    private void processManageStaffCommand(String command, Business business) {
        switch (command) {
            case "a":
                displayStaff(business);
                addStaffToBusiness(business);
                System.out.println("The hourly wage is " + business.getSalary() * 3600 + ".");
                break;
            case "b":
                if (displayStaff(business)) {
                    selectStaffToRemove(business);
                } else {
                    System.out.println("Returning you to previous menu...");
                }
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
     * Helper functions for staff management *
     ********************************************/

    //EFFECTS: display all the staff currently working at business
    private boolean displayStaff(Business business) {
        ArrayList<Resident> staff = business.getStaff();
        if (staff.isEmpty()) {
            System.out.println("\nThis business currently does not have any staff.");
            return false;
        } else {
            System.out.println("\nThis business currently has " + staff.size() + " staff:");
            for (Resident person : staff) {
                System.out.println(person.getName());
            }
            return true;
        }
    }

    //EFFECTS: asks for a staff to be removed and confirm they're currently working at business; calls removeStaff to
    //          remove them from business.
    private void selectStaffToRemove(Business business) {
        ArrayList<Resident> allStaff = business.getStaff();
        System.out.println("\nEnter the name of staff you want to remove: ");
        String staffName = scanner.nextLine();
        staffName = scanner.nextLine();
        int staffIndex = -1;
        for (int i = 0; i < allStaff.size(); i++) {
            if (allStaff.get(i).getName().equalsIgnoreCase(staffName)) {
                staffIndex = i;
            }
        }
        if (staffIndex == -1) {
            System.out.println("\nThe name you entered doesn't match with any staff on record.");
            System.out.println("Returning you to previous menu...");
        } else {
            removeStaff(business, allStaff.get(staffIndex));
        }
    }

    //MODIFIES: business, resident
    //EFFECTS: removes resident from business; change resident's income at bank to 0.
    private void removeStaff(Business business, Resident resident) {
        business.removeStaff(resident);
        Bank bank = cities.get(currentCity).getBank();
        bank.stopGenerateEarnings();
        System.out.println("\n" + resident.getName() + " is no longer working at "
                + business.getBusinessName() + ".");
    }

    //MODIFIES: business
    //EFFECTS: checks if there are any residents in the city and asks users to select one to be added as staff to
    //          business if the resident is at least 19 years old
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

    //EFFECTS: helper function for addStaffToBusiness to check if selected resident is already working in selected
    //         business and displays a message; to check if resident is already working somewhere else and asks if
    //          user wants to change their jobs; and give this resident a new job.
    private void checkJob(Business business, Resident resident) {
        if (business.getStaff().contains(resident)) {
            System.out.println("\nThis resident is already working here! Returning you to the previous menu...");
            useSecondaryMenu();
        } else if (resident.getOccupationCode() != -1) {
            String workingLocation = resident.getWorkingLocation();
            Business currentEmployer = null;
            for (Business eachBusiness : cities.get(currentCity).getBusinesses()) {
                if (eachBusiness.getBusinessName().equals(workingLocation)) {
                    currentEmployer = eachBusiness;
                }
            }
            System.out.println("This resident is currently working at " + workingLocation + ".");
            System.out.println("Do you want to change their job? y/n");
            if (scanner.next().equals("n")) {
                System.out.println("Returning you the previous menu...");
                useSecondaryMenu();
            } else {
                removeStaff(currentEmployer, resident);
                assignJob(business, resident);
            }
        } else {
            assignJob(business, resident);
        }
    }

    //MODIFIES: business, bank, resident
    //EFFECTS： adds selected resident as staff in business; modifies their bank accounts so that they receive income
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
        System.out.println("\nYou are now in the city of " + cities.get(currentCity).getCityName() + ".");
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
