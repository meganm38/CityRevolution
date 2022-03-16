package ui;

// Start the game by running Main.

import model.Resident;
import ui.swing.ResidentManagementWindow;

public class Main {
    public static void main(String[] args) {
        //CityRevolution simulator = new CityRevolution();
        //MainWindow main = new MainWindow();
        //CityWindow main = new CityWindow(new City("Vancouver", City.Theme.DARK), null);
        ResidentManagementWindow residentManagementWindow = new ResidentManagementWindow(null, new Resident("Megan", true, 20), null);
    }
}
