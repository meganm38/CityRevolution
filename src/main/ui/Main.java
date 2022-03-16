package ui;

// Start the game by running Main.

import model.City;
import ui.swing.CityWindow;

public class Main {
    public static void main(String[] args) {
        //CityRevolution simulator = new CityRevolution();
        //MainWindow main = new MainWindow();
        CityWindow main = new CityWindow(new City("Vancouver", City.Theme.DARK), null);
    }
}
