package ui;

// Start the game by running Main.

import ui.swing.MainWindow;
import ui.swing.simulators.SwingCityRevolution;

public class Main {
    public static void main(String[] args) {
        MainWindow main = new MainWindow(new SwingCityRevolution());
    }
}
