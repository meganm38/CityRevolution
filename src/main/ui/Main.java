package ui;

// Start the game by running Main.

import model.Event;
import model.EventLog;
import ui.swing.MainWindow;
import ui.swing.simulators.SwingCityRevolution;

public class Main {
    public static void main(String[] args) {
        MainWindow main = new MainWindow(new SwingCityRevolution());

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                EventLog eventLog = EventLog.getInstance();
                for (Event event : eventLog) {
                    System.out.println(event.toString() + "\n");
                }
            }
        });
    }
}
