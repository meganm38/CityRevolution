package ui.swing;

import model.City;
import model.Hotel;
import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class HotelWindow extends Window {
    private static final int SETTINGS_PANEL_WIDTH = 450;
    private static final int SETTINGS_PANEL_HEIGHT = 150;

    private final City city;
    private final SwingCityRevolution cityRevolution;

    private Hotel.Theme theme;
    private String name;
    private int star;
    private boolean themeSelected;

    private JPanel confirmPanel;
    private JPanel settingPanel;
    private JTextField nameField;
    private JComboBox<String> starList;
    private JButton skiResortBtn;
    private JButton beachResortBtn;


    public HotelWindow(City city, SwingCityRevolution cityRevolution) {
        super();
        this.city = city;
        this.cityRevolution = cityRevolution;
        init();
        centreOnScreen();
        setVisible(true);
    }

    @Override
    protected void init() {
        initBackground();
        initMain();
    }

    @Override
    protected void initMain() {
        super.initMain();
        setupMenu();
        add(mainPanel);
    }

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
        initThemePanel();
        mainPanel.add(fake);
        initConfirmPanel();
    }

    private void initSettingsPanel() {
        JPanel addResidentText = new JPanel();
        addResidentText.setBackground(BLUE);
        JLabel label = new JLabel("Add Hotel");
        label.setForeground(Color.white);
        addResidentText.setPreferredSize(new Dimension(450, 30));
        addResidentText.add(label);

        settingPanel = new JPanel();
        settingPanel.setPreferredSize(new Dimension(SETTINGS_PANEL_WIDTH, SETTINGS_PANEL_HEIGHT));
        settingPanel.setBackground(LIGHT_BLUE);
        settingPanel.setLayout(null);
        configSettingPanel();

        mainPanel.add(addResidentText);
        mainPanel.add(settingPanel);
    }

    private void configSettingPanel() {
        JLabel cityNameText = new JLabel("Name:");
        nameField = new JTextField();
        cityNameText.setBounds(90, 10, 100, 50);
        cityNameText.setForeground(FONT_COLOR_DARK);
        nameField.setBounds(180, 10, 150, 50);
        nameField.setHorizontalAlignment(JTextField.CENTER);

        JLabel ageText = new JLabel("Hotel Star:");
        String[] stars = {"1-star", "2-star", "3-star", "4-star", "5-star"};
        starList = new JComboBox<>(stars);
        starList.setBounds(180, 80, 150, 50);

        ageText.setBounds(90, 80, 100, 50);
        ageText.setForeground(FONT_COLOR_DARK);


        settingPanel.add(cityNameText);
        settingPanel.add(nameField);
        settingPanel.add(ageText);
        settingPanel.add(starList);
    }

    private void initThemePanel() {
        JPanel textPanel = new JPanel();
        textPanel.setBackground(BLUE);
        JLabel label = new JLabel("Theme");
        label.setForeground(Color.white);
        textPanel.setPreferredSize(new Dimension(450, 30));
        textPanel.add(label);

        JPanel themePanel = new JPanel();
        themePanel.setPreferredSize(new Dimension(450, 150));
        themePanel.setBackground(LIGHT_BLUE);
        themePanel.setLayout(null);

        JLabel ski = new JLabel("Ski Resort");
        ski.setBounds(90, 120, 100, 20);
        JLabel beach = new JLabel("Beach Resort");
        beach.setBounds(285, 120, 100, 20);
        ski.setForeground(FONT_COLOR_DARK);
        beach.setForeground(FONT_COLOR_DARK);

        initHotelButtons();
        themePanel.add(skiResortBtn);
        themePanel.add(beachResortBtn);
        themePanel.add(ski);
        themePanel.add(beach);
        mainPanel.add(textPanel);
        mainPanel.add(themePanel);
    }

    private void initHotelButtons() {
        skiResortBtn = new JButton(new ImageIcon("data/pictures/skiResort.png"));
        beachResortBtn = new JButton(new ImageIcon("data/pictures/beachResort.png"));
        skiResortBtn.setBounds(50, 20, 150, 100);
        beachResortBtn.setBounds(250, 20, 150, 100);
        skiResortBtn.setBorder(BorderFactory.createEtchedBorder(0));
        beachResortBtn.setBorder(BorderFactory.createEtchedBorder(1));

        skiResortBtn.addActionListener(e -> {
            if (beachResortBtn.isEnabled()) {
                beachResortBtn.setIcon(new ImageIcon("data/pictures/beachResort.png"));
            }
            skiResortBtn.setIcon(new ImageIcon("data/pictures/skiDarken.png"));
            theme = Hotel.Theme.SKI;
            themeSelected = true;
        });
        beachResortBtn.addActionListener(e -> {
            if (skiResortBtn.isEnabled()) {
                skiResortBtn.setIcon(new ImageIcon("data/pictures/skiResort.png"));
            }
            beachResortBtn.setIcon(new ImageIcon("data/pictures/beachDarken.png"));
            theme = Hotel.Theme.BEACH;
            themeSelected = true;
        });
    }


    private void initConfirmPanel() {
        confirmPanel = new JPanel();
        confirmPanel.setLayout(null);
        confirmPanel.setBackground(new Color(192, 192, 192));
        confirmPanel.setPreferredSize(new Dimension(500, 50));
        addButtonsToConfirmPanel();
        addBackButton();
        mainPanel.add(confirmPanel);
    }

    private void addButtonsToConfirmPanel() {
        JButton confirmButton = new JButton(new ImageIcon("data/pictures/checkmark.png"));
        confirmButton.setBounds(450, 0, 50, 48);
        confirmButton.setBorderPainted(false);

        confirmButton.addActionListener(e -> {
            if (nameField.getText().length() != 0 && themeSelected) {
                name = nameField.getText();
                star = starList.getSelectedIndex() + 1;
                Hotel hotel = new Hotel(name, star, theme);
                city.addHotel(hotel);
                showMessageDialog(this, "Hotel has been successfully added!");
                CityWindow cityWindow = new CityWindow(city, cityRevolution);
                dispose();
            } else {
                showMessageDialog(this, "Empty Name/Theme");
            }
        });
        confirmPanel.add(confirmButton);
    }

    private void addBackButton() {
        JButton backBtn = new JButton(new ImageIcon("data/pictures/backBtn.png"));
        backBtn.setBounds(0, 0, 50, 48);
        backBtn.setBorderPainted(false);
        backBtn.addActionListener(e -> {
            CityWindow cityWindow = new CityWindow(city, cityRevolution);
            dispose();
        });
        confirmPanel.add(backBtn);
    }
}
