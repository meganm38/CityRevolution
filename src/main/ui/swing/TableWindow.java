package ui.swing;

import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Represents an abstract window that is used for displaying information in a table
public abstract class TableWindow extends SettingWindow {
    protected String[] columnNames;
    protected String[][] data;
    protected int columnSize;

    /*
     * EFFECTS: constructs a table window
     */
    protected TableWindow(
            String windowName, String panelName, ImageIcon imgIcon, SwingCityRevolution cityRevolution,
            int columnSize) {
        super(windowName, panelName, imgIcon, cityRevolution);
        this.columnSize = columnSize;
    }

    /*
     *
     * EFFECTS: constructs a setting window
     */
    @Override
    protected void setup() {
        JLabel menuTitle = new JLabel(panelName);
        menuTitle.setForeground(FONT_COLOR_DARK);
        menuTitle.setFont(TITLE_FONT);
        menuTitle.setBorder(new EmptyBorder(GAP, 0, GAP, 0));
        mainPanel.add(menuTitle);

        JPanel fake = new JPanel();
        fake.setPreferredSize(new Dimension(MAIN_PANEL_WIDTH, GAP));
        fake.setOpaque(false);

        backgroundPanel = new JPanel();
        backgroundPanel.setBackground(LIGHT_BLUE);
        backgroundPanel.setPreferredSize(new Dimension(MAIN_PANEL_WIDTH - 2 * GAP, 200));
        backgroundPanel.setLayout(new BorderLayout());

        columnNames = new String[columnSize];
        setupBackgroundPanel();
        mainPanel.add(backgroundPanel);
        mainPanel.add(fake);
        initConfirmPanel();
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: dispose window when button is clicked
     */
    @Override
    protected void addBtnActionListener() {
        checkBtn.addActionListener(e -> dispose());
    }

    /*
     *  MODIFIES: this
     *  EFFECTS: sets up color and font of table data
     */
    @Override
    protected void setupBackgroundPanel() {
        initTableContent();
        JTable table = new JTable(data, columnNames);
        table.setRowHeight(20);
        table.setFont(REGULAR_FONT);
        table.setForeground(FONT_COLOR_DARK);
        table.setBackground(LIGHT_BLUE);
        JScrollPane sp = new JScrollPane(table);
        backgroundPanel.add(sp);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates
     */
    protected abstract void initTableContent();
}
