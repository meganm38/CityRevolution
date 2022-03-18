package ui;

// Start the game by running Main.

import model.City;
import model.Hotel;
import model.Resident;
import ui.swing.HotelManagementWindow;
import ui.swing.simulators.SwingCityRevolution;

public class Main {
    public static void main(String[] args) {
        //CityRevolution simulator = new CityRevolution();
        //MainWindow main = new MainWindow();
        SwingCityRevolution cityRevolution = new SwingCityRevolution();
        cityRevolution.addNewCity(new City("Vancouver", City.Theme.LIGHT));
        cityRevolution.addNewHotel(new Hotel("ShangriLa", 5, Hotel.Theme.SKI));
        cityRevolution.addNewResident(new Resident("Monica", true, 25));
        cityRevolution.selectHotel(0);
        HotelManagementWindow hotelManagementWindow = new HotelManagementWindow(cityRevolution);
        //CityWindow main = new CityWindow(new City("Vancouver", City.Theme.DARK), null);
        //ResidentManagementWindow residentManagementWindow = new ResidentManagementWindow(null);
    }
}
