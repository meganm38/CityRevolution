package ui.swing;

import model.Hotel;
import model.Resident;
import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class HotelManagementWindow extends Window {
    private final SwingCityRevolution cityRevolution;
    private final Hotel hotel;

    private JPanel attributesBackgroundPanel;
    private JPanel attributesPanel;
    private JPanel settingsBackgroundPanel;
    private JPanel settingsPanel;
    private JPanel confirmPanel;
    ImageIcon imgIcon;
    JButton openSettingBtn;
    JLabel businessStatus;
    JLabel staff;
    JButton staffSettingBtn;

    public HotelManagementWindow(SwingCityRevolution cityRevolution) {
        super();
        this.hotel = cityRevolution.getSelectedHotel();
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

    private void setupMenu() {
        JLabel menuTitle = new JLabel("HOTEL INFO");
        menuTitle.setForeground(FONT_COLOR_DARK);
        menuTitle.setFont(TITLE_FONT);
        menuTitle.setBorder(new EmptyBorder(GAP, 0, GAP, 0));
        mainPanel.add(menuTitle);
        JPanel fake = new JPanel();
        fake.setPreferredSize(new Dimension(640, 10));
        fake.setOpaque(false);

        initDisplayPanelAttributes();
        initDisplayPanelSettings();
        mainPanel.add(fake);
        initConfirmPanel();
    }

    private void initDisplayPanelAttributes() {
        JPanel hotelAttributePanel = new JPanel();
        hotelAttributePanel.setBackground(BLUE);
        JLabel attributesText = new JLabel("Hotel Attributes");
        attributesText.setForeground(Color.white);
        hotelAttributePanel.setPreferredSize(new Dimension(450, 30));
        hotelAttributePanel.add(attributesText);

        attributesBackgroundPanel = new JPanel();
        attributesBackgroundPanel.setPreferredSize(new Dimension(450, 120));
        attributesBackgroundPanel.setBackground(LIGHT_BLUE);
        attributesBackgroundPanel.setLayout(null);

        addAttributes();
        addAdditionalAttributes();
        attributesBackgroundPanel.add(attributesPanel);
        mainPanel.add(hotelAttributePanel);
        mainPanel.add(attributesBackgroundPanel);
    }

    private void initDisplayPanelSettings() {
        JPanel hotelSettingsPanel = new JPanel();
        hotelSettingsPanel.setBackground(BLUE);
        JLabel hotelSettingText = new JLabel("Hotel Settings/Requirements");
        hotelSettingText.setForeground(Color.white);
        hotelSettingsPanel.setPreferredSize(new Dimension(450, 30));
        hotelSettingsPanel.add(hotelSettingText);

        settingsBackgroundPanel = new JPanel();
        settingsBackgroundPanel.setPreferredSize(new Dimension(450, 178));
        settingsBackgroundPanel.setBackground(LIGHT_BLUE);
        settingsBackgroundPanel.setLayout(null);

        settingsPanel = new JPanel();
        settingsPanel.setBackground(PINK);
        settingsPanel.setBounds(GAP, GAP, 430, 160);
        settingsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        addBusinessStatus();
        addStaffInfo();
        addRoomInfo();
        addBookingInfo();
        settingsBackgroundPanel.add(settingsPanel);
        mainPanel.add(hotelSettingsPanel);
        mainPanel.add(settingsBackgroundPanel);
    }

    private void addAttributes() {
        attributesPanel = new JPanel();
        attributesPanel.setBackground(PINK);
        attributesPanel.setBounds(GAP, GAP, 430, 100);
        attributesPanel.setLayout(null);

        JLabel profilePic = new JLabel(new ImageIcon("data/pictures/hotelIcon.png"));
        profilePic.setBounds(70, 15, 65, 65);

        JLabel name = new JLabel("Name:");
        name.setFont(REGULAR_FONT);
        name.setForeground(FONT_COLOR_DARK);
        name.setBounds(170, 10, 60, 20);

        JLabel hotelName = new JLabel(hotel.getBusinessName());
        hotelName.setFont(REGULAR_FONT);
        hotelName.setForeground(Color.blue.darker());
        hotelName.setBounds(230, 10, 100, 20);

        attributesPanel.add(profilePic);
        attributesPanel.add(name);
        attributesPanel.add(hotelName);
    }

    private void addAdditionalAttributes() {
        String theme = hotel.getTheme().equals(Hotel.Theme.SKI) ? "Ski resort" : "Beach resort";
        JLabel hotelTheme = new JLabel("Theme:");
        hotelTheme.setFont(REGULAR_FONT);
        hotelTheme.setForeground(FONT_COLOR_DARK);
        hotelTheme.setBounds(170, 40, 60, 20);

        JLabel themeOfHotel = new JLabel(theme);
        themeOfHotel.setForeground(Color.blue.darker());
        themeOfHotel.setFont(REGULAR_FONT);
        themeOfHotel.setBounds(230, 40, 100, 20);

        JLabel star = new JLabel("Star:");
        star.setFont(REGULAR_FONT);
        star.setForeground(FONT_COLOR_DARK);
        star.setBounds(170, 70, 60, 20);

        JLabel starOfHotel = new JLabel(hotel.getStar() + "-star");
        starOfHotel.setFont(REGULAR_FONT);
        starOfHotel.setForeground(Color.blue.darker());
        starOfHotel.setBounds(230, 70, 100, 20);

        attributesPanel.add(hotelTheme);
        attributesPanel.add(themeOfHotel);
        attributesPanel.add(star);
        attributesPanel.add(starOfHotel);
    }

    private void addBusinessStatus() {
        JPanel openForBusinessPanel = new JPanel();
        openForBusinessPanel.setBackground(PINK);
        openForBusinessPanel.setPreferredSize(new Dimension(430, 36));
        openForBusinessPanel.setLayout(null);

        JLabel openForBusiness = new JLabel("Hotel Status:");
        openForBusiness.setFont(REGULAR_FONT);
        openForBusiness.setForeground(FONT_COLOR_DARK);
        openForBusiness.setBounds(90, 5, 150, 25);
        openForBusinessPanel.add(openForBusiness);

        businessStatus = new JLabel(hotel.isBusinessOpen() ? "Open" : "Closed");
        businessStatus.setFont(REGULAR_FONT);
        businessStatus.setForeground(Color.blue.darker());
        businessStatus.setBounds(250, 5, 100, 25);
        openForBusinessPanel.add(businessStatus);

        openSettingBtn = new JButton(new ImageIcon("data/pictures/settingSmall.png"));
        openSettingBtn.setBounds(380, 0, 35, 35);
        openSettingBtn.setBorderPainted(false);
        configOpenSettingBtn();
        openForBusinessPanel.add(openSettingBtn);

        JPanel blank = new JPanel();
        blank.setPreferredSize(new Dimension(430, 5));
        settingsPanel.add(openForBusinessPanel);
        settingsPanel.add(blank);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void configOpenSettingBtn() {
        openSettingBtn.addActionListener(e -> {
            final SettingWindow settingWindow = new SettingWindow(
                    "Hotel Status Setting", "Hotel Status", imgIcon, cityRevolution) {
                JComboBox<String> list;

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

                @Override
                protected void addBtnActionListener() {
                    checkBtn.addActionListener(e -> {
                        if (list.getSelectedIndex() == 0) {
                            if (cityRevolution.openCurrentHotel()) {
                                changeHotelStatus(true);
                                showMessageDialog(this, "Hotel is now open for Business!");
                                dispose();
                            } else {
                                showMessageDialog(this, "No staff/hotel rooms.\n"
                                        + "Hotel cannot be opened.");
                            }
                        } else {
                            cityRevolution.closeCurrentHotel();
                            changeHotelStatus(false);
                            showMessageDialog(this, "Hotel is now closed.");
                            dispose();
                        }
                    });
                }
            };
        });
    }

    private void changeHotelStatus(boolean openHotel) {
        businessStatus.setText(openHotel ? "Open" : "Closed");
    }

    private void addStaffInfo() {
        JPanel staffInfoPanel = new JPanel();
        staffInfoPanel.setBackground(PINK);
        staffInfoPanel.setPreferredSize(new Dimension(430, 36));
        staffInfoPanel.setLayout(null);

        JLabel hotelStaffText = new JLabel("Hotel Staff:");
        hotelStaffText.setFont(REGULAR_FONT);
        hotelStaffText.setForeground(FONT_COLOR_DARK);
        hotelStaffText.setBounds(90, 5, 150, 25);
        staffInfoPanel.add(hotelStaffText);

        staff = new JLabel(hotel.getStaff().size() + "/1");
        staff.setFont(REGULAR_FONT);
        staff.setForeground(Color.blue.darker());
        staff.setBounds(250, 5, 100, 25);
        staffInfoPanel.add(staff);

        staffSettingBtn = new JButton(new ImageIcon("data/pictures/settingSmall.png"));
        staffSettingBtn.setBounds(380, 0, 35, 35);
        staffSettingBtn.setBorderPainted(false);
        configStaffSettingBtn();
        staffInfoPanel.add(staffSettingBtn);

        JPanel blank = new JPanel();
        blank.setPreferredSize(new Dimension(430, 5));
        settingsPanel.add(staffInfoPanel);
        settingsPanel.add(blank);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void configStaffSettingBtn() {
        staffSettingBtn.addActionListener(e -> {
            final SettingWindow settingWindow = new SettingWindow(
                    "Hotel Staff Setting", "Add Staff", imgIcon, cityRevolution) {
                JComboBox<String> list;

                @Override
                protected void setupBackgroundPanel() {
                    JLabel instructionLabel = new JLabel("Choose a resident:");
                    instructionLabel.setBounds(60, 60, 150, 50);
                    instructionLabel.setFont(REGULAR_FONT);
                    instructionLabel.setForeground(FONT_COLOR_DARK);

                    ArrayList<Resident> residents = cityRevolution.getCurrentCity().getResidents();
                    String[] residentNames = new String[residents.size()];
                    for (int i = 0; i < residents.size(); i++) {
                        residentNames[i] = residents.get(i).getName();
                    }
                    list = new JComboBox<>(residentNames);
                    list.setBounds(200, 60, 100, 50);

                    backgroundPanel.add(instructionLabel);
                    backgroundPanel.add(list);
                }

                //TODO: change associations between staff and work
                @Override
                protected void addBtnActionListener() {
                    checkBtn.addActionListener(e -> {
                        ArrayList<Resident> residents = cityRevolution.getCurrentCity().getResidents();
                        if (hotel.getStaff().contains(residents.get(list.getSelectedIndex()))) {
                            showMessageDialog(this, "This resident is already working here.");
                        } else {
                            hotel.addStaff(residents.get(list.getSelectedIndex()));
                            showMessageDialog(this, "Added as staff!");
                            changeHotelStaff();
                        }
                        dispose();
                    });
                }
            };
        });
    }

    private void changeHotelStaff() {
        staff.setText(hotel.getStaff().size() + "/1");
    }

    private void addRoomInfo() {
        JPanel roomInfoPanel = new JPanel();
        roomInfoPanel.setBackground(PINK);
        roomInfoPanel.setPreferredSize(new Dimension(430, 36));
        roomInfoPanel.setLayout(null);

        JLabel roomInfoText = new JLabel("Hotel Rooms:");
        roomInfoText.setFont(REGULAR_FONT);
        roomInfoText.setForeground(FONT_COLOR_DARK);
        roomInfoText.setBounds(90, 5, 150, 25);
        roomInfoPanel.add(roomInfoText);

        JLabel businessStatus = new JLabel(hotel.getRoomNumbers().size() + "/1");
        businessStatus.setFont(REGULAR_FONT);
        businessStatus.setForeground(Color.blue.darker());
        businessStatus.setBounds(250, 5, 100, 25);
        roomInfoPanel.add(businessStatus);

        JButton settingIcon = new JButton(new ImageIcon("data/pictures/settingSmall.png"));
        settingIcon.setBounds(380, 0, 35, 35);
        settingIcon.setBorderPainted(false);
        roomInfoPanel.add(settingIcon);

        JPanel blank = new JPanel();
        blank.setPreferredSize(new Dimension(430, 5));
        settingsPanel.add(roomInfoPanel);
        settingsPanel.add(blank);
    }

    private void addBookingInfo() {
        JPanel bookingInfoPanel = new JPanel();
        bookingInfoPanel.setBackground(PINK);
        bookingInfoPanel.setPreferredSize(new Dimension(430, 36));
        bookingInfoPanel.setLayout(null);

        JLabel bookingInfoText = new JLabel("Hotel Bookings:");
        bookingInfoText.setFont(REGULAR_FONT);
        bookingInfoText.setForeground(FONT_COLOR_DARK);
        bookingInfoText.setBounds(90, 5, 150, 25);
        bookingInfoPanel.add(bookingInfoText);

        JLabel businessStatus = new JLabel(hotel.getBookedRoomNumbers().size() + "");
        businessStatus.setFont(REGULAR_FONT);
        businessStatus.setForeground(Color.blue.darker());
        businessStatus.setBounds(250, 5, 100, 25);
        bookingInfoPanel.add(businessStatus);

        JButton settingIcon = new JButton(new ImageIcon("data/pictures/settingSmall.png"));
        settingIcon.setBounds(380, 0, 35, 35);
        settingIcon.setBorderPainted(false);
        bookingInfoPanel.add(settingIcon);

        JPanel blank = new JPanel();
        blank.setPreferredSize(new Dimension(430, 5));
        settingsPanel.add(bookingInfoPanel);
        settingsPanel.add(blank);
    }

    @Override
    protected void initBackground() {
        if (hotel.getTheme().equals(Hotel.Theme.SKI)) {
            imgIcon = new ImageIcon("data/pictures/skiBack.png");
        } else {
            imgIcon = new ImageIcon("data/pictures/beachBack.png");
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

    private void initConfirmPanel() {
        confirmPanel = new JPanel();
        confirmPanel.setLayout(null);
        confirmPanel.setBackground(new Color(192, 192, 192));
        confirmPanel.setPreferredSize(new Dimension(500, 50));
        addButtonsToConfirmPanel();
        mainPanel.add(confirmPanel);
    }

    private void addButtonsToConfirmPanel() {
        JButton checkBtn = new JButton(new ImageIcon("data/pictures/checkmark.png"));
        checkBtn.setBounds(450, 0, 50, 48);
        checkBtn.setBorderPainted(false);
        checkBtn.addActionListener(e -> {
            CityWindow cityWindow = new CityWindow(cityRevolution);
            dispose();
        });
        confirmPanel.add(checkBtn);
    }
}
