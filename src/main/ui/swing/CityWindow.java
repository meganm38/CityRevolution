package ui.swing;

import model.City;
import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Represents a window that lets user choose view/edit residents and hotels
public class CityWindow extends Window {
    private static final int SETTINGS_PANEL_WIDTH = 450;
    private static final int SETTINGS_PANEL_HEIGHT = 330;

    private final City city;
    private final SwingCityRevolution cityRevolution;
    private JPanel settingPanel;
    private JPanel residentPanel;
    private JPanel hotelPanel;
    private JPanel optionsPanel;

    /*
     * EFFECTS: creates a city window
     */
    public CityWindow(SwingCityRevolution cityRevolution) {
        super();
        this.city = cityRevolution.getCity();
        this.cityRevolution = cityRevolution;
        init();
        centreOnScreen();
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
     * EFFECTS: sets up main menu template and spacing
     */
    private void setupMenu() {
        JLabel menuTitle = new JLabel("The City of " + city.getCityName());
        menuTitle.setForeground(FONT_COLOR_DARK);
        menuTitle.setFont(TITLE_FONT);
        menuTitle.setBorder(new EmptyBorder(GAP, 0, GAP, 0));
        mainPanel.add(menuTitle);
        JPanel fake = new JPanel();
        fake.setPreferredSize(new Dimension(640, 10));
        fake.setOpaque(false);

        initSettingsPanel();
        mainPanel.add(fake);
        initConfirmPanel();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates settingsPanel and sets its position
     */
    private void initSettingsPanel() {
        JPanel cityInfoText = new JPanel();
        cityInfoText.setBackground(BLUE);
        JLabel label = new JLabel("CITY COMPONENTS");
        label.setForeground(Color.white);
        cityInfoText.setPreferredSize(new Dimension(450, 30));
        cityInfoText.add(label);

        settingPanel = new JPanel();
        settingPanel.setPreferredSize(new Dimension(SETTINGS_PANEL_WIDTH, SETTINGS_PANEL_HEIGHT));
        settingPanel.setBackground(LIGHT_BLUE);
        settingPanel.setLayout(null);
        initResidentPanel();
        initHotelPanel();
        mainPanel.add(cityInfoText);
        mainPanel.add(settingPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates resident panel where user can choose to view or edit residents
     */
    private void initResidentPanel() {
        residentPanel = new JPanel();
        residentPanel.setBackground(PINK);
        residentPanel.setBounds(GAP, GAP,
                SETTINGS_PANEL_WIDTH / 2 - GAP * 2, SETTINGS_PANEL_HEIGHT - 2 * GAP);
        JLabel residentText = new JLabel("RESIDENTS");
        residentText.setForeground(FONT_COLOR_DARK);
        residentText.setPreferredSize(new Dimension(residentPanel.getWidth(), 50));
        residentText.setBorder(new EmptyBorder(GAP, 7 * GAP, GAP, GAP));
        residentPanel.add(residentText);

        JLabel residentsImg = new JLabel(new ImageIcon("data/pictures/residentsImg.png"));
        residentsImg.setPreferredSize(new Dimension(residentPanel.getWidth(), 180));
        residentsImg.setBorder(new EmptyBorder(GAP, GAP, GAP, GAP));
        residentPanel.add(residentsImg);
        addButtonToResidentsPanel();
        settingPanel.add(residentPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds view and setting buttons to resident panel
     */
    private void addButtonToResidentsPanel() {
        JButton addBtn = new JButton(new ImageIcon("data/pictures/addIcon.png"));
        addBtn.setBounds(residentPanel.getWidth() - 45, residentPanel.getHeight() - 60, 50, 48);
        addBtn.setBorderPainted(false);
        addBtn.addActionListener(e -> {
            CreateResidentWindow createResidentWindow = new CreateResidentWindow(city, cityRevolution);
            dispose();
        });

        JButton settingBtn = new JButton(new ImageIcon("data/pictures/settingIcon.png"));
        settingBtn.setBounds(residentPanel.getWidth() / 2, residentPanel.getHeight() - 60, 45, 45);
        settingBtn.setBorderPainted(false);
        settingBtn.addActionListener(e -> {
            ResidentCheckerWindow residentCheckerWindow = new ResidentCheckerWindow(cityRevolution);
            dispose();
        });

        residentPanel.add(addBtn);
        residentPanel.add(settingBtn);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates hotel panel where user can choose to view or edit hotel
     */
    private void initHotelPanel() {
        hotelPanel = new JPanel();
        hotelPanel.setBackground(new Color(171, 209, 201));
        hotelPanel.setBounds(residentPanel.getWidth() + GAP * 3, GAP,
                SETTINGS_PANEL_WIDTH / 2 - GAP * 2, SETTINGS_PANEL_HEIGHT - 2 * GAP);

        JLabel hotelText = new JLabel("HOTELS");
        hotelText.setForeground(FONT_COLOR_DARK);
        hotelText.setPreferredSize(new Dimension(residentPanel.getWidth(), 50));
        hotelText.setBorder(new EmptyBorder(GAP, 8 * GAP, GAP, GAP));
        hotelPanel.add(hotelText);

        JLabel hotelImg = new JLabel(new ImageIcon("data/pictures/hotelImg.png"));
        hotelImg.setPreferredSize(new Dimension(hotelPanel.getWidth(), 180));
        hotelImg.setBorder(new EmptyBorder(GAP, GAP, GAP, GAP));
        hotelPanel.add(hotelImg);
        addButtonToHotelPanel();
        settingPanel.add(hotelPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds view and setting buttons to hotel panel
     */
    private void addButtonToHotelPanel() {
        JButton addBtn = new JButton(new ImageIcon("data/pictures/addIcon.png"));
        addBtn.setBounds(hotelPanel.getWidth() - 45, hotelPanel.getHeight() - 60, 50, 48);
        addBtn.setBorderPainted(false);
        addBtn.addActionListener(e -> {
            CreateHotelWindow createHotelWindow = new CreateHotelWindow(city, cityRevolution);
            dispose();
        });

        JButton settingBtn = new JButton(new ImageIcon("data/pictures/settingIcon.png"));
        settingBtn.setBounds(residentPanel.getWidth() / 2, residentPanel.getHeight() - 60, 45, 45);
        settingBtn.setBorderPainted(false);
        settingBtn.addActionListener(e -> {
            HotelCheckerWindow hotelCheckerWindow = new HotelCheckerWindow(cityRevolution);
            dispose();
        });

        hotelPanel.add(addBtn);
        hotelPanel.add(settingBtn);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates confirm panel and sets its position
     */
    private void initConfirmPanel() {
        optionsPanel = new JPanel();
        optionsPanel.setLayout(null);
        optionsPanel.setBackground(new Color(192, 192, 192));
        optionsPanel.setPreferredSize(new Dimension(500, 50));
        addButtonsToConfirmPanel();
        mainPanel.add(optionsPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a checkmark button and home button to confirmPanel
     */
    private void addButtonsToConfirmPanel() {
        JButton backBtn = new JButton(new ImageIcon("data/pictures/backBtn.png"));
        backBtn.setBounds(450, 0, 50, 48);
        backBtn.setBorderPainted(false);
        backBtn.addActionListener(e -> {
            CreateCityWindow cityWindow = new CreateCityWindow(cityRevolution);
            dispose();
        });
        optionsPanel.add(backBtn);

        JButton homeBtn = new JButton(new ImageIcon("data/pictures/home.png"));
        homeBtn.setBounds(0, 0, 50, 48);
        homeBtn.setBorderPainted(false);
        homeBtn.addActionListener(e -> {
            MainWindow main = new MainWindow(cityRevolution);
            dispose();
        });
        optionsPanel.add(homeBtn);
    }
}

