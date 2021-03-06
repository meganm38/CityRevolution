package ui.swing;

import model.City;
import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

// Represents a window that lets user create a new city
public class CreateCityWindow extends Window {
    private City city;
    private SwingCityRevolution cityRevolution;
    private City.Theme theme;
    private String cityName;

    private JPanel settingPanel;
    private JPanel themePanel;
    private JPanel confirmPanel;
    private JTextField inputField;
    private JButton lightThemeBtn;
    private JButton darkThemeBtn;


    /*
     * EFFECTS: creates a create city window
     */
    public CreateCityWindow(SwingCityRevolution cityRevolution) {
        super();
        if (cityRevolution.getCity() != null) {
            city = cityRevolution.getCity();
        }
        this.cityRevolution = cityRevolution;
        init();
        centreOnScreen();
        setVisible(true);
    }

    /*
     * EFFECTS: starting method that calls methods to initiate background and main menu
     */
    protected void init() {
        initBackground();
        initMain();
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the background image of the window depending on the theme of city
     */
    protected void initBackground() {
        ImageIcon imgIcon = new ImageIcon("data/pictures/cityback.png");
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
        JLabel menuTitle = city == null ? new JLabel("NEW CITY") : new JLabel("MODIFY CITY");

        menuTitle.setForeground(FONT_COLOR_DARK);
        menuTitle.setFont(TITLE_FONT);
        menuTitle.setBorder(new EmptyBorder(10, 0, 10, 0));
        mainPanel.add(menuTitle);
        JPanel fake = new JPanel();
        fake.setPreferredSize(new Dimension(640, 10));
        fake.setOpaque(false);

        initSettingPanel();
        initThemePanel();
        mainPanel.add(fake);
        initConfirmPanel();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates settingsPanel and sets its position
     */
    private void initSettingPanel() {
        JPanel cityInfoText = new JPanel();
        cityInfoText.setBackground(BLUE);
        JLabel label = new JLabel("CITY INFORMATION");
        label.setForeground(Color.white);
        cityInfoText.setPreferredSize(new Dimension(450, 30));
        cityInfoText.add(label);

        settingPanel = new JPanel();
        settingPanel.setPreferredSize(new Dimension(450, 100));
        settingPanel.setBackground(LIGHT_BLUE);
        settingPanel.setLayout(null);
        configSettingPanel();

        mainPanel.add(cityInfoText);
        mainPanel.add(settingPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds content to settingsPanel
     */
    private void configSettingPanel() {
        JLabel cityNameText = new JLabel("City Name:");
        cityNameText.setBounds(90, 25, 100, 50);
        cityNameText.setForeground(FONT_COLOR_DARK);

        inputField = new JTextField();
        inputField.setText(city == null ? "" : city.getCityName());
        inputField.setBounds(180, 25, 150, 50);
        inputField.setHorizontalAlignment(JTextField.CENTER);
        settingPanel.add(cityNameText);
        settingPanel.add(inputField);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates themePanel
     */
    private void initThemePanel() {
        JPanel textPanel = new JPanel();
        textPanel.setBackground(BLUE);
        textPanel.setPreferredSize(new Dimension(450, 30));

        JLabel label = new JLabel("THEME SETTING");
        label.setForeground(Color.white);
        textPanel.add(label);

        themePanel = new JPanel();
        themePanel.setPreferredSize(new Dimension(450, 200));
        themePanel.setBackground(LIGHT_BLUE);
        themePanel.setLayout(null);

        configThemePanel();
        mainPanel.add(textPanel);
        mainPanel.add(themePanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds content to themePanel
     */
    private void configThemePanel() {
        addThemeButtons();
        JLabel lightThemeText = new JLabel("Light");
        JLabel darkThemeText = new JLabel("Dark");
        lightThemeText.setForeground(FONT_COLOR_DARK);
        darkThemeText.setForeground(FONT_COLOR_DARK);
        lightThemeText.setBounds(120, 170, 50, 20);
        darkThemeText.setBounds(290, 170, 50, 20);

        themePanel.add(lightThemeText);
        themePanel.add(darkThemeText);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds light and dark theme buttons to themePanel
     */
    private void addThemeButtons() {
        if (city == null) {
            lightThemeBtn = new JButton(new ImageIcon("data/pictures/lighttheme.png"));
            darkThemeBtn = new JButton(new ImageIcon("data/pictures/darktheme.png"));
        } else {
            theme = city.getTheme();
            if (theme.equals(City.Theme.LIGHT)) {
                lightThemeBtn = new JButton(new ImageIcon("data/pictures/lightthemedarken.png"));
                darkThemeBtn = new JButton(new ImageIcon("data/pictures/darktheme.png"));
            } else {
                lightThemeBtn = new JButton(new ImageIcon("data/pictures/lighttheme.png"));
                darkThemeBtn = new JButton(new ImageIcon("data/pictures/darkthemedarken.png"));
            }
        }
        lightThemeBtn.setBounds(60, 20, THEME_IMAGE_DIMENSIONS, THEME_IMAGE_DIMENSIONS);
        darkThemeBtn.setBounds(80 + THEME_IMAGE_DIMENSIONS, 20, THEME_IMAGE_DIMENSIONS, THEME_IMAGE_DIMENSIONS);
        lightThemeBtn.setBorder(BorderFactory.createEtchedBorder(0));
        darkThemeBtn.setBorder(BorderFactory.createEtchedBorder(1));
        addListenersToThemeButtons();
        themePanel.add(lightThemeBtn);
        themePanel.add(darkThemeBtn);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds listeners to themeButtons
     */
    private void addListenersToThemeButtons() {
        lightThemeBtn.addActionListener(e -> {
            if (darkThemeBtn.isEnabled()) {
                darkThemeBtn.setIcon(new ImageIcon("data/pictures/darktheme.png"));
            }
            lightThemeBtn.setIcon(new ImageIcon("data/pictures/lightthemedarken.png"));
            theme = City.Theme.LIGHT;
        });
        darkThemeBtn.addActionListener(e -> {
            if (lightThemeBtn.isEnabled()) {
                lightThemeBtn.setIcon(new ImageIcon("data/pictures/lighttheme.png"));
            }
            darkThemeBtn.setIcon(new ImageIcon("data/pictures/darkthemedarken.png"));
            theme = City.Theme.DARK;
        });
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
        addConfirmBtn();
        addBackBtn();
        mainPanel.add(confirmPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a checkmark button to confirmPanel that provides input instructions when is clicked on
     */
    private void addConfirmBtn() {
        JButton checkBtn = new JButton(new ImageIcon("data/pictures/checkmark.png"));
        checkBtn.setBounds(450, 0, 50, 48);
        checkBtn.setBorderPainted(false);
        checkBtn.addActionListener(e -> {
            cityName = inputField.getText();
            if (cityName != null && theme != null) {
                if (cityRevolution.getCity() == null) {
                    cityRevolution = new SwingCityRevolution();
                    cityRevolution.addNewCity(new City(cityName, theme));
                } else {
                    cityRevolution.changeCityName(cityName);
                    cityRevolution.changeCityTheme(theme);
                }
                CityWindow cityWindow = new CityWindow(cityRevolution);
                setVisible(false);
            } else {
                showMessageDialog(this, "Empty Name/Theme");
            }
        });
        confirmPanel.add(checkBtn);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a back button to confirmPanel that leads user to previous page if clicked on
     */
    private void addBackBtn() {
        JButton backBtn = new JButton(new ImageIcon("data/pictures/backBtn.png"));
        backBtn.setBounds(0, 0, 50, 48);
        backBtn.setBorderPainted(false);
        backBtn.addActionListener(e -> {
            MainWindow mainWindow = new MainWindow(cityRevolution);
            dispose();
        });
        confirmPanel.add(backBtn);
    }
}
