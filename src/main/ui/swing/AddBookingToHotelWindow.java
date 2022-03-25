package ui.swing;

import model.Resident;
import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

//Represents a window that lets user add bookings to hotel
public abstract class AddBookingToHotelWindow extends SettingWindow {
    protected JTextField num;
    protected JComboBox<String> list;

    //EFFECTS: constructs an AddBookingToHotelWindow
    protected AddBookingToHotelWindow(
            String windowName, String panelName, ImageIcon imgIcon, SwingCityRevolution cityRevolution) {
        super(windowName, panelName, imgIcon, cityRevolution);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up background panel and add residents list and number input filed
     */
    @Override
    protected void setupBackgroundPanel() {
        JLabel nameInstruction = new JLabel("Choose resident:");
        nameInstruction.setBounds(20, 40, 200, 50);
        nameInstruction.setFont(REGULAR_FONT);
        nameInstruction.setForeground(FONT_COLOR_DARK);

        ArrayList<Resident> residents = cityRevolution.getCity().getResidents();
        String[] residentNames = new String[residents.size()];
        for (int i = 0; i < residents.size(); i++) {
            residentNames[i] = residents.get(i).getName();
        }
        list = new JComboBox<>(residentNames);
        list.setBounds(220, 40, 150, 50);

        JLabel instructionLabel = new JLabel("Number of bookings to add:");
        instructionLabel.setBounds(20, 90, 200, 50);
        instructionLabel.setFont(REGULAR_FONT);
        instructionLabel.setForeground(FONT_COLOR_DARK);

        num = new JTextField();
        num.setBounds(220, 90, 100, 50);

        backgroundPanel.add(nameInstruction);
        backgroundPanel.add(list);
        backgroundPanel.add(instructionLabel);
        backgroundPanel.add(num);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds an action listener to confirm button
     */
    @Override
    protected void addBtnActionListener() {
        checkBtn.addActionListener(e -> {
            if (list.getSelectedIndex() == -1) {
                showMessageDialog(this, "No resident selected.");
                return;
            }
            if (!hotel.isBusinessOpen()) {
                showMessageDialog(this, "Hotel is not yet open for business");
                return;
            }
            setUpBtn();
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up adds bookings to hotel action
     */
    protected abstract void setUpBtn();
}
