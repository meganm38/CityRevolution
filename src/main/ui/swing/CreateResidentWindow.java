package ui.swing;

import model.City;
import model.Resident;
import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

// Represents a window that lets user create a new resident
public class CreateResidentWindow extends Window {
    private static final int SETTINGS_PANEL_WIDTH = 450;
    private static final int SETTINGS_PANEL_HEIGHT = 170;

    private final City city;
    private final SwingCityRevolution cityRevolution;
    private boolean isFemale;
    private String name;
    private int age;
    private boolean genderSelected;

    private JPanel settingPanel;
    private JPanel confirmPanel;
    private JTextField nameField;
    private JTextField ageField;
    private JPanel genderPanel;
    private JButton femaleBtn;
    private JButton maleBtn;

    /*
     * EFFECTS: creates a create resident window
     */
    public CreateResidentWindow(City city, SwingCityRevolution cityRevolution) {
        super();
        this.city = city;
        genderSelected = false;
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
        initGenderPanel();
        mainPanel.add(fake);
        initConfirmPanel();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates settingsPanel and sets its position
     */
    private void initSettingsPanel() {
        JPanel addResidentText = new JPanel();
        addResidentText.setBackground(BLUE);
        JLabel label = new JLabel("ADD RESIDENT");
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

    /*
     * MODIFIES: this
     * EFFECTS: adds content to settingsPanel
     */
    private void configSettingPanel() {
        JLabel cityNameText = new JLabel("Name:");
        nameField = new JTextField();
        cityNameText.setBounds(90, 25, 100, 50);
        cityNameText.setForeground(FONT_COLOR_DARK);
        nameField.setBounds(180, 25, 150, 50);
        nameField.setHorizontalAlignment(JTextField.CENTER);

        JLabel ageText = new JLabel("Age:");
        ageField = new JTextField();
        ageText.setBounds(90, 100, 100, 50);
        ageText.setForeground(FONT_COLOR_DARK);
        ageField.setBounds(180, 100, 150, 50);
        ageField.setHorizontalAlignment(JTextField.CENTER);

        settingPanel.add(cityNameText);
        settingPanel.add(nameField);
        settingPanel.add(ageText);
        settingPanel.add(ageField);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates gender panel and its content
     */
    private void initGenderPanel() {
        JPanel textPanel = new JPanel();
        textPanel.setBackground(BLUE);
        JLabel label = new JLabel("GENDER");
        label.setForeground(Color.white);
        textPanel.setPreferredSize(new Dimension(450, 30));
        textPanel.add(label);

        genderPanel = new JPanel();
        genderPanel.setPreferredSize(new Dimension(450, 130));
        genderPanel.setBackground(LIGHT_BLUE);
        genderPanel.setLayout(null);

        femaleBtn = new JButton(new ImageIcon("data/pictures/femaleImg.png"));
        maleBtn = new JButton(new ImageIcon("data/pictures/maleImg.png"));
        femaleBtn.setBounds(120, 20, 100, 100);
        maleBtn.setBounds(240, 20, 100, 100);
        femaleBtn.setBorder(BorderFactory.createEtchedBorder(0));
        maleBtn.setBorder(BorderFactory.createEtchedBorder(1));
        addGenderButtons();

        mainPanel.add(textPanel);
        mainPanel.add(genderPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds female and male buttons that allow user to select the gender of resident
     */
    private void addGenderButtons() {
        femaleBtn.addActionListener(e -> {
            if (maleBtn.isEnabled()) {
                maleBtn.setIcon(new ImageIcon("data/pictures/maleImg.png"));
            }
            femaleBtn.setIcon(new ImageIcon("data/pictures/femaleDarken.png"));
            isFemale = true;
            genderSelected = true;
        });
        maleBtn.addActionListener(e -> {
            if (femaleBtn.isEnabled()) {
                femaleBtn.setIcon(new ImageIcon("data/pictures/femaleImg.png"));
            }
            maleBtn.setIcon(new ImageIcon("data/pictures/maleDarken.png"));
            isFemale = false;
            genderSelected = true;
        });
        genderPanel.add(femaleBtn);
        genderPanel.add(maleBtn);
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
        addBackButton();
        mainPanel.add(confirmPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a checkmark button to confirmPanel that provides input instructions when is clicked on
     */
    private void addConfirmBtn() {
        JButton confirmButton = new JButton(new ImageIcon("data/pictures/checkmark.png"));
        confirmButton.setBounds(450, 0, 50, 48);
        confirmButton.setBorderPainted(false);

        confirmButton.addActionListener(e -> {
            if (nameField.getText().length() != 0 && ageField.getText().length() != 0 && genderSelected) {
                name = nameField.getText();
                try {
                    age = Integer.parseInt(ageField.getText());
                    if (age <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    showMessageDialog(this, "Age must be a positive Integer");
                }
                cityRevolution.addNewResident(new Resident(name, isFemale, age));
                showMessageDialog(this, "Resident has been successfully added!");
                CityWindow cityWindow = new CityWindow(cityRevolution);
                dispose();
            } else {
                showMessageDialog(this, "Empty Name/Age/Gender");
            }
        });
        confirmPanel.add(confirmButton);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a back button to confirmPanel that leads user to previous page if clicked on
     */
    private void addBackButton() {
        JButton backBtn = new JButton(new ImageIcon("data/pictures/backBtn.png"));
        backBtn.setBounds(0, 0, 50, 48);
        backBtn.setBorderPainted(false);
        backBtn.addActionListener(e -> {
            CityWindow cityWindow = new CityWindow(cityRevolution);
            dispose();
        });
        confirmPanel.add(backBtn);
    }
}
