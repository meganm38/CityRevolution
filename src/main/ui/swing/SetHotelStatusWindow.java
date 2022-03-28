package ui.swing;

import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;

public abstract class SetHotelStatusWindow extends SettingWindow {
    protected JComboBox<String> list;

    protected SetHotelStatusWindow(
            String windowName, String panelName, ImageIcon imgIcon, SwingCityRevolution cityRevolution) {
        super(windowName, panelName, imgIcon, cityRevolution);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds instructions to background panel for setting a hotel status
     */
    @Override
    protected void setupBackgroundPanel() {
        JLabel instructionLabel = new JLabel("Hotel Status:");
        instructionLabel.setBounds(80, 60, 100, 50);
        instructionLabel.setFont(REGULAR_FONT);
        instructionLabel.setForeground(FONT_COLOR_DARK);

        String[] status = {"Open", "Closed"};
        list = new JComboBox<>(status);
        list.setBounds(200, 60, 100, 50);

        backgroundPanel.add(instructionLabel);
        backgroundPanel.add(list);
    }
}
