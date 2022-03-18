package ui.swing;

import model.City;
import model.Hotel;
import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

// Represents a window that displays all the hotels in the city
public class HotelCheckerWindow extends Window {
    private static final int DISPLAY_PANEL_WIDTH = 450;
    private static final int DISPLAY_PANEL_HEIGHT = 330;
    private final City city;
    private final SwingCityRevolution cityRevolution;

    private JPanel displayPanel;
    private JPanel hotelPanel;
    private JList<String> hotelList;
    private JScrollPane hotelScrollPane;
    private JPanel confirmPanel;

    /*
     * EFFECTS: creates a hotelCheckerWindow
     */
    public HotelCheckerWindow(SwingCityRevolution cityRevolution) {
        super();
        this.city = cityRevolution.getCity();
        this.cityRevolution = cityRevolution;
        init();
        centreOnScreen();
        setAlwaysOnTop(true);
        setVisible(true);
    }

    /*
     * EFFECTS: starting method that calls methods to initiate background and main menu
     */
    @Override
    protected void init() {
        initBackground();
        initMain();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates main menu
     */
    @Override
    protected void initMain() {
        super.initMain();
        setupMenu();
        add(mainPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up main menu template and spacing
     */
    private void setupMenu() {
        JLabel menuTitle = new JLabel("City Contents");
        menuTitle.setForeground(FONT_COLOR_DARK);
        menuTitle.setFont(TITLE_FONT);
        menuTitle.setBorder(new EmptyBorder(GAP, 0, GAP, 0));
        mainPanel.add(menuTitle);
        JPanel fake = new JPanel();
        fake.setPreferredSize(new Dimension(640, 10));
        fake.setOpaque(false);

        initDisplayPanel();
        mainPanel.add(fake);
        initConfirmPanel();
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up display panel and adds title
     */
    private void initDisplayPanel() {
        JPanel cityInfoText = new JPanel();
        cityInfoText.setBackground(BLUE);
        JLabel label = new JLabel("SELECT HOTEL");
        label.setForeground(Color.white);
        cityInfoText.setPreferredSize(new Dimension(450, 30));
        cityInfoText.add(label);

        displayPanel = new JPanel();
        displayPanel.setPreferredSize(new Dimension(450, 330));
        displayPanel.setBackground(LIGHT_BLUE);
        displayPanel.setLayout(null);

        initHotelPanel();
        mainPanel.add(cityInfoText);
        mainPanel.add(displayPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up hotel panel and adds hotel list
     */
    private void initHotelPanel() {
        hotelPanel = new JPanel();
        hotelPanel.setBackground(new Color(171, 209, 201));
        hotelPanel.setBounds(hotelPanel.getWidth() + GAP * 3, GAP,
                DISPLAY_PANEL_WIDTH - GAP * 6, DISPLAY_PANEL_HEIGHT - 2 * GAP);

        ArrayList<Hotel> hotelArrayList = city.getHotels();
        String[] hotelNamesArray = new String[hotelArrayList.size()];
        for (int i = 0; i < hotelArrayList.size(); i++) {
            hotelNamesArray[i] = hotelArrayList.get(i).getBusinessName();
        }
        hotelList = new JList<>(hotelNamesArray);
        hotelList.setLayoutOrientation(JList.VERTICAL);
        hotelList.setForeground(FONT_COLOR_DARK);
        hotelList.setFont(new Font("Serif", Font.PLAIN, 15));
        hotelScrollPane = new JScrollPane();
        hotelScrollPane.setViewportView(hotelList);
        hotelPanel.setLayout(null);
        hotelScrollPane.setBounds(GAP, GAP, hotelPanel.getWidth() - 2 * GAP, hotelPanel.getHeight() - 2 * GAP);

        hotelPanel.add(hotelScrollPane);
        displayPanel.add(hotelPanel);
    }


    /*
     * MODIFIES: this
     * EFFECTS: sets the background image of the window depending on the theme of city
     */
    @Override
    protected void initBackground() {
        ImageIcon imgIcon;
        if (city.getTheme().equals(City.Theme.LIGHT)) {
            imgIcon = new ImageIcon("data/pictures/lightthemedarken.png");
        } else {
            imgIcon = new ImageIcon("data/pictures/darkthemedarken.png");
        }
        Image img = imgIcon.getImage();
        JPanel background = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, null);
            }
        };
        setContentPane(background);
        pack();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates confirm panel and sets its position
     */
    private void initConfirmPanel() {
        confirmPanel = new JPanel();
        confirmPanel.setLayout(null);
        confirmPanel.setBackground(new Color(192, 192, 192));
        confirmPanel.setPreferredSize(new Dimension(500, 50));
        addButtonsToConfirmPanel();
        mainPanel.add(confirmPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a checkmark button to confirmPanel that provides input instructions when clicked on
     *          adds a back button to go back to previous window
     */
    private void addButtonsToConfirmPanel() {
        JButton checkBtn = new JButton(new ImageIcon("data/pictures/checkmark.png"));
        checkBtn.setBounds(450, 0, 50, 48);
        checkBtn.setBorderPainted(false);
        checkBtn.addActionListener(e -> {
            if (hotelList.getSelectedIndex() != -1) {
                int selectedIndex = hotelList.getSelectedIndex();
                cityRevolution.selectHotel(selectedIndex);
                HotelManagementWindow hotelManagementWindow = new HotelManagementWindow(
                        cityRevolution);
                dispose();
            } else {
                showMessageDialog(this, "Select a hotel to proceed");
            }
        });

        JButton backBtn = new JButton(new ImageIcon("data/pictures/backBtn.png"));
        backBtn.setBounds(0, 0, 50, 48);
        backBtn.setBorderPainted(false);
        backBtn.addActionListener(e -> {
            CityWindow cityWindow = new CityWindow(cityRevolution);
            dispose();
        });
        confirmPanel.add(backBtn);
        confirmPanel.add(checkBtn);
    }
}
