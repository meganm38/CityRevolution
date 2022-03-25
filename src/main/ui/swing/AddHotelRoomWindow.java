package ui.swing;

import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;

//Represents a window that lets user select a number of rooms to add to hotel rooms
public abstract class AddHotelRoomWindow extends SettingWindow {

    // EFFECTS: constructs an add hotel room window
    protected AddHotelRoomWindow(
            String windowName, String panelName, ImageIcon imgIcon, SwingCityRevolution cityRevolution) {
        super(windowName, panelName, imgIcon, cityRevolution);
    }

    protected JTextField textField;

    /*
     * MODIFIES: this
     * EFFECTS: sets up instructions for choosing the number of rooms
     */
    @Override
    protected void setupBackgroundPanel() {
        JLabel instructionLabel = new JLabel("Number of Rooms to add:");
        instructionLabel.setBounds(20, 60, 200, 50);
        instructionLabel.setFont(REGULAR_FONT);
        instructionLabel.setForeground(FONT_COLOR_DARK);

        textField = new JTextField();
        textField.setBounds(220, 60, 100, 50);

        backgroundPanel.add(instructionLabel);
        backgroundPanel.add(textField);
    }
}
