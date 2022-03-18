package ui.swing;

import model.BusinessInfo;
import model.City;
import model.Resident;
import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Represents a window that displays a resident's info
public class ResidentManagementWindow extends Window {
    private Resident resident;
    private SwingCityRevolution cityRevolution;
    private City city;

    private JPanel displayPanel;
    private JPanel confirmPanel;
    private JPanel infoPanel;

    /*
     * EFFECTS: constructs a visible resident management window
     */
    public ResidentManagementWindow(SwingCityRevolution cityRevolution) {
        super();
        this.city = cityRevolution.getCity();
        this.resident = cityRevolution.getSelectedResident();
        this.cityRevolution = cityRevolution;
        init();
        centreOnScreen();
        setVisible(true);
    }

    /*
     * EFFECTS: initiates the background and main panel
     */
    @Override
    protected void init() {
        initBackground();
        initMain();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates the main panel
     */
    @Override
    protected void initMain() {
        super.initMain();
        setupMenu();
        add(mainPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds spacing to the main panel and initiates all necessary panels
     */
    private void setupMenu() {
        JLabel menuTitle = new JLabel("RESIDENT INFO");
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
     * EFFECTS: initiates and adds a panel that displays hotel's name to frame
     */
    private void initDisplayPanel() {
        JPanel residentNameText = new JPanel();
        residentNameText.setBackground(BLUE);
        JLabel label = new JLabel(resident.getName());
        label.setForeground(Color.white);
        residentNameText.setPreferredSize(new Dimension(450, 30));
        residentNameText.add(label);

        displayPanel = new JPanel();
        displayPanel.setPreferredSize(new Dimension(450, 330));
        displayPanel.setBackground(LIGHT_BLUE);
        addProfilePic();
        addResidentInfo();
        mainPanel.add(residentNameText);
        mainPanel.add(displayPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a profile picture depending on resident's gender
     */
    private void addProfilePic() {
        JPanel fake = new JPanel();
        fake.setPreferredSize(new Dimension(450, 30));
        fake.setOpaque(false);
        displayPanel.add(fake);
        JLabel profilePic;
        if (resident.isFemale()) {
            profilePic = new JLabel(new ImageIcon("data/pictures/femaleImg.png"));
        } else {
            profilePic = new JLabel(new ImageIcon("data/pictures/maleImg.png"));
        }
        profilePic.setPreferredSize(new Dimension(100, 100));
        displayPanel.add(profilePic);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates and adds a panel that displays resident's name, age and gender
     */
    private void addResidentInfo() {
        JPanel fake = new JPanel();
        fake.setPreferredSize(new Dimension(450, 20));
        fake.setOpaque(false);
        displayPanel.add(fake);

        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(450, 150));
        infoPanel.setOpaque(false);
        infoPanel.setLayout(null);
        JLabel name = new JLabel("Name: " + resident.getName());
        JLabel age = new JLabel("Age: " + resident.getAge());
        JLabel gender = new JLabel("Gender: " + (resident.isFemale() ? "Female" : "Male"));

        name.setBounds(180, 0, 200, 20);
        name.setForeground(FONT_COLOR_DARK);
        age.setBounds(180, 30, 200, 20);
        age.setForeground(FONT_COLOR_DARK);
        gender.setBounds(180, 60, 200, 20);
        gender.setForeground(FONT_COLOR_DARK);

        infoPanel.add(name);
        infoPanel.add(age);
        infoPanel.add(gender);
        addAdditionalInfo();
        displayPanel.add(infoPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates and adds a panel that displays resident's job status and bank account balance
     */
    private void addAdditionalInfo() {
        JLabel bankAccount = new JLabel(
                "Bank Account Balance: " + city.getBank().getAccounts().get(resident.getName()));

        String job = "Unemployed";
        for (BusinessInfo businessInfo : BusinessInfo.values()) {
            job = resident.getOccupationCode() == businessInfo.occupationCode()
                    ? businessInfo.businessType() : "Unemployed";
        }
        JLabel jobLabel = new JLabel("Job: " + job + (job.equals("Unemployed")
                ? "" : (" staff at " + resident.getWorkingLocation())));

        jobLabel.setBounds(180, 90, 200, 20);
        jobLabel.setForeground(FONT_COLOR_DARK);
        bankAccount.setBounds(180, 120, 200, 20);
        bankAccount.setForeground(FONT_COLOR_DARK);
        infoPanel.add(jobLabel);
        infoPanel.add(bankAccount);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the background image of the window depending on the theme of city
     */
    @Override
    protected void initBackground() {
        ImageIcon imgIcon = new ImageIcon("data/pictures/residentBackground.png");
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
     */
    private void addButtonsToConfirmPanel() {
        JButton checkBtn = new JButton(new ImageIcon("data/pictures/checkmark.png"));
        checkBtn.setBounds(450, 0, 50, 48);
        checkBtn.setBorderPainted(false);
        checkBtn.addActionListener(e -> {
            ResidentCheckerWindow residentCheckerWindow = new ResidentCheckerWindow(cityRevolution);
            dispose();
        });
        confirmPanel.add(checkBtn);
    }
}
