package ui.swing;

import model.Resident;
import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import java.util.ArrayList;


//Represents a window that lets user choose resident to add as hotel staff
public abstract class AddHotelStaffWindow extends SettingWindow {
    protected JComboBox<String> list;

    // EFFECTS: constructs an AddHotelStaffWindow window
    protected AddHotelStaffWindow(
            String windowName, String panelName, ImageIcon imgIcon, SwingCityRevolution cityRevolution) {
        super(windowName, panelName, imgIcon, cityRevolution);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up background panel and adds list of available residents
     */
    @Override
    protected void setupBackgroundPanel() {
        JLabel instructionLabel = new JLabel("Choose a resident:");
        instructionLabel.setBounds(60, 60, 150, 50);
        instructionLabel.setFont(REGULAR_FONT);
        instructionLabel.setForeground(FONT_COLOR_DARK);

        ArrayList<Resident> residents = cityRevolution.getCity().getResidents();
        String[] residentNames = new String[residents.size()];
        for (int i = 0; i < residents.size(); i++) {
            residentNames[i] = residents.get(i).getName();
        }
        list = new JComboBox<>(residentNames);
        list.setBounds(200, 60, 150, 50);

        backgroundPanel.add(instructionLabel);
        backgroundPanel.add(list);
    }
}
