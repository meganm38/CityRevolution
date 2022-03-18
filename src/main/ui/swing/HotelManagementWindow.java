package ui.swing;

import model.Hotel;
import model.Resident;
import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.JOptionPane.showMessageDialog;

// Represents a window that displays hotel attributes and internal management settings
public class HotelManagementWindow extends Window {
    private final SwingCityRevolution cityRevolution;
    private final Hotel hotel;

    private JPanel attributesPanel;
    private JPanel settingsPanel;
    private JPanel confirmPanel;

    private JPanel staffInfoPanel;
    private JPanel roomInfoPanel;
    private JPanel bookingInfoPanel;

    private ImageIcon imgIcon;
    private JButton openSettingBtn;
    private JLabel businessStatus;
    private JLabel staff;
    private JButton staffSettingBtn;
    private JLabel rooms;
    private JButton roomSettingBtn;
    private JLabel bookings;
    private JButton bookingSettingBtn;

    private JButton viewStaffBtn;
    private JButton viewRoomBtn;
    private JButton viewBookingBtn;

    /*
     * EFFECTS: constructs a visible hotel management window
     */
    public HotelManagementWindow(SwingCityRevolution cityRevolution) {
        super();
        this.hotel = cityRevolution.getSelectedHotel();
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

    /*
     * MODIFIES: this
     * EFFECTS: initiates and adds a panel that displays hotel's attributes to frame
     */
    private void initDisplayPanelAttributes() {
        JPanel hotelAttributePanel = new JPanel();
        hotelAttributePanel.setBackground(BLUE);
        JLabel attributesText = new JLabel("Hotel Attributes");
        attributesText.setForeground(Color.white);
        hotelAttributePanel.setPreferredSize(new Dimension(450, 30));
        hotelAttributePanel.add(attributesText);

        JPanel attributesBackgroundPanel = new JPanel();
        attributesBackgroundPanel.setPreferredSize(new Dimension(450, 120));
        attributesBackgroundPanel.setBackground(LIGHT_BLUE);
        attributesBackgroundPanel.setLayout(null);

        addAttributes();
        addAdditionalAttributes();
        attributesBackgroundPanel.add(attributesPanel);
        mainPanel.add(hotelAttributePanel);
        mainPanel.add(attributesBackgroundPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds spacing to the main panel and initiates all necessary panels
     */
    private void initDisplayPanelSettings() {
        JPanel hotelSettingsPanel = new JPanel();
        hotelSettingsPanel.setBackground(BLUE);
        JLabel hotelSettingText = new JLabel("Hotel Settings/Requirements");
        hotelSettingText.setForeground(Color.white);
        hotelSettingsPanel.setPreferredSize(new Dimension(450, 30));
        hotelSettingsPanel.add(hotelSettingText);

        JPanel settingsBackgroundPanel = new JPanel();
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

    /*
     * MODIFIES: this
     * EFFECTS: initiates attributesPanel and adds title
     */
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

    /*
     * MODIFIES: this
     * EFFECTS: adds hotel theme and star attributes to attributesPanel
     */
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

    /*
     * MODIFIES: this
     * EFFECTS: adds business status and setting button to settingsPanel
     */
    private void addBusinessStatus() {
        JPanel openForBusinessPanel = new JPanel();
        openForBusinessPanel.setBackground(PINK);
        openForBusinessPanel.setPreferredSize(new Dimension(430, 36));
        openForBusinessPanel.setLayout(null);

        JLabel openForBusiness = new JLabel("Hotel Status:");
        openForBusiness.setFont(REGULAR_FONT);
        openForBusiness.setForeground(FONT_COLOR_DARK);
        openForBusiness.setBounds(100, 5, 150, 25);
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

    /*
     * MODIFIES: this
     * EFFECTS: initiates a setting window for displaying instructions on setting a hotel status
     */
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void configOpenSettingBtn() {
        openSettingBtn.addActionListener(e -> {
            final SettingWindow settingWindow = new SettingWindow(
                    "Hotel Status Setting", "Hotel Status", imgIcon, cityRevolution) {
                JComboBox<String> list;

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

                /*
                 * MODIFIES: this
                 * EFFECTS: adds listener to checkBtn
                 */
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

    /*
     * MODIFIES: this
     * EFFECTS: updates hotel status based on openHotel being true or false
     */
    private void changeHotelStatus(boolean openHotel) {
        businessStatus.setText(openHotel ? "Open" : "Closed");
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds staff info to settingsPanel that displays the number of staff in hotel
     */
    private void addStaffInfo() {
        staffInfoPanel = new JPanel();
        staffInfoPanel.setBackground(PINK);
        staffInfoPanel.setPreferredSize(new Dimension(430, 36));
        staffInfoPanel.setLayout(null);

        JLabel hotelStaffText = new JLabel("Hotel Staff:");
        hotelStaffText.setFont(REGULAR_FONT);
        hotelStaffText.setForeground(FONT_COLOR_DARK);
        hotelStaffText.setBounds(100, 5, 150, 25);
        staffInfoPanel.add(hotelStaffText);

        staff = new JLabel(hotel.getStaff().size() + "/1");
        staff.setFont(REGULAR_FONT);
        staff.setForeground(Color.blue.darker());
        staff.setBounds(250, 5, 100, 25);
        staffInfoPanel.add(staff);

        addViewAndSettingButtons();

        JPanel blank = new JPanel();
        blank.setPreferredSize(new Dimension(430, 5));
        settingsPanel.add(staffInfoPanel);
        settingsPanel.add(blank);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds view and setting buttons for staff Info
     */
    private void addViewAndSettingButtons() {
        staffSettingBtn = new JButton(new ImageIcon("data/pictures/settingSmall.png"));
        staffSettingBtn.setBounds(380, 0, 35, 35);
        staffSettingBtn.setBorderPainted(false);

        viewStaffBtn = new JButton(new ImageIcon("data/pictures/view.png"));
        viewStaffBtn.setBounds(20, 0, 35, 35);
        viewStaffBtn.setBorderPainted(false);

        configStaffSettingBtn();
        configViewStaffBtn();
        staffInfoPanel.add(staffSettingBtn);
        staffInfoPanel.add(viewStaffBtn);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates a new table window that displays all staff
     */
    private void configViewStaffBtn() {
        viewStaffBtn.addActionListener(e -> {
            final TableWindow settingWindow = new TableWindow(
                    "View Hotel Staff", "Staff", imgIcon, cityRevolution, 3) {

                /*
                 * MODIFIES: this
                 * EFFECTS: sets up table content for displaying staff
                 */
                @Override
                protected void initTableContent() {
                    columnNames = new String[]{"Name", "Age", "Gender"};
                    ArrayList<Resident> allStaff = hotel.getStaff();
                    data = new String[allStaff.size()][columnNames.length];
                    for (int staffIndex = 0; staffIndex < allStaff.size(); staffIndex++) {
                        data[staffIndex][0] = allStaff.get(staffIndex).getName();
                        data[staffIndex][1] = allStaff.get(staffIndex).getAge() + "";
                        data[staffIndex][2] = allStaff.get(staffIndex).isFemale() ? "Female" : "Male";
                    }
                }
            };
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates a new setting window for uses to choose resident to add as hotel staff
     */
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void configStaffSettingBtn() {
        staffSettingBtn.addActionListener(e -> {
            final SettingWindow settingWindow = new SettingWindow(
                    "Hotel Staff Setting", "Add Staff", imgIcon, cityRevolution) {
                JComboBox<String> list;

                /*
                 * MODIFIES: this
                 * EFFECTS: sets up instructions for choosing resident
                 */
                @Override
                protected void setupBackgroundPanel() {
                    JLabel instructionLabel = new JLabel("Choose a resident:");
                    instructionLabel.setBounds(60, 60, 150, 50);
                    instructionLabel.setFont(REGULAR_FONT);
                    instructionLabel.setForeground(FONT_COLOR_DARK);

                    ArrayList<Resident> residents = cityRevolution.getCity().getResidents();
                    String[] residentNames = new String[residents.size()];
                    for (int i = 0; i < residents.size(); i++) {
                        residentNames[i] = residents.get(i).getName();
                    }
                    list = new JComboBox<>(residentNames);
                    list.setBounds(200, 60, 150, 50);

                    backgroundPanel.add(instructionLabel);
                    backgroundPanel.add(list);
                }

                /*
                 * MODIFIES: this
                 * EFFECTS: adds listener to checkmark button if a resident is selected
                 */
                //TODO: user working somewhere else
                @Override
                protected void addBtnActionListener() {
                    checkBtn.addActionListener(e -> {
                        if (list.getSelectedIndex() == -1) {
                            showMessageDialog(this, "No resident selected.");
                        }
                        ArrayList<Resident> residents = cityRevolution.getCity().getResidents();
                        if (hotel.getStaff().contains(residents.get(list.getSelectedIndex()))) {
                            showMessageDialog(this, "This resident is already working here.");
                        } else {
                            cityRevolution.assignJob(hotel, residents.get(list.getSelectedIndex()));
                            showMessageDialog(this, "Added as staff!");
                            changeHotelStaff();
                        }
                        dispose();
                    });
                }
            };
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates the number of staff
     */
    private void changeHotelStaff() {
        staff.setText(hotel.getStaff().size() + "/1");
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds room info to settingsPanel that displays the number of rooms in hotel
     */
    private void addRoomInfo() {
        roomInfoPanel = new JPanel();
        roomInfoPanel.setBackground(PINK);
        roomInfoPanel.setPreferredSize(new Dimension(430, 36));
        roomInfoPanel.setLayout(null);

        JLabel roomInfoText = new JLabel("Hotel Rooms:");
        roomInfoText.setFont(REGULAR_FONT);
        roomInfoText.setForeground(FONT_COLOR_DARK);
        roomInfoText.setBounds(100, 5, 150, 25);
        roomInfoPanel.add(roomInfoText);

        rooms = new JLabel(hotel.getRoomNumbers().size() + "/1");
        rooms.setFont(REGULAR_FONT);
        rooms.setForeground(Color.blue.darker());
        rooms.setBounds(250, 5, 100, 25);
        roomInfoPanel.add(rooms);

        addButtonsToRoomInfo();

        JPanel blank = new JPanel();
        blank.setPreferredSize(new Dimension(430, 5));
        settingsPanel.add(roomInfoPanel);
        settingsPanel.add(blank);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds view and setting buttons for room Info
     */
    private void addButtonsToRoomInfo() {
        roomSettingBtn = new JButton(new ImageIcon("data/pictures/settingSmall.png"));
        roomSettingBtn.setBounds(380, 0, 35, 35);
        roomSettingBtn.setBorderPainted(false);
        configRoomSettingBtn();
        roomInfoPanel.add(roomSettingBtn);

        viewRoomBtn = new JButton(new ImageIcon("data/pictures/view.png"));
        viewRoomBtn.setBounds(20, 0, 35, 35);
        viewRoomBtn.setBorderPainted(false);
        configRoomViewBtn();
        roomInfoPanel.add(viewRoomBtn);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates a new table window that displays all room numbers
     */
    private void configRoomViewBtn() {
        viewRoomBtn.addActionListener(e -> {
            final TableWindow settingWindow = new TableWindow(
                    "View Hotel Rooms", "Rooms", imgIcon, cityRevolution, 2) {

                /*
                 * MODIFIES: this
                 * EFFECTS: sets up table content for displaying room numbers
                 */
                @Override
                protected void initTableContent() {
                    columnNames = new String[]{"Count", "Room Number"};
                    ArrayList<Integer> roomNumbers = hotel.getRoomNumbers();
                    data = new String[roomNumbers.size()][columnNames.length];
                    for (int roomCount = 0; roomCount < roomNumbers.size(); roomCount++) {
                        data[roomCount][0] = (roomCount + 1) + "";
                        data[roomCount][1] = roomNumbers.get(roomCount) + "";
                    }
                }
            };
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates a new setting window for uses to choose the number of rooms to add to hotel
     */
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void configRoomSettingBtn() {
        roomSettingBtn.addActionListener(e -> {
            final SettingWindow settingWindow = new SettingWindow(
                    "Hotel Rooms Setting", "Add rooms", imgIcon, cityRevolution) {
                JTextField textField;

                /*
                 * MODIFIES: this
                 * EFFECTS: sets up instructions for choosing the number of rooms
                 */
                @Override
                protected void setupBackgroundPanel() {
                    JLabel instructionLabel = new JLabel("Number of Rooms to add:");
                    instructionLabel.setBounds(20, 60, 200, 50);
                    instructionLabel.setFont(REGULAR_FONT);
                    instructionLabel.setForeground(FONT_COLOR_DARK);

                    textField = new JTextField();
                    textField.setBounds(220, 60, 100, 50);

                    backgroundPanel.add(instructionLabel);
                    backgroundPanel.add(textField);
                }

                /*
                 * MODIFIES: this
                 * EFFECTS: adds listener to checkmark button
                 */
                @Override
                protected void addBtnActionListener() {
                    checkBtn.addActionListener(e -> {
                        try {
                            int num = Integer.parseInt(textField.getText());
                            hotel.addRooms(num);
                            changeRoomNumbersDisplay();
                            showMessageDialog(this, "Rooms successfully added!");
                            dispose();
                        } catch (NumberFormatException ex) {
                            showMessageDialog(this, "Enter a positive integer.");
                        }
                    });
                }
            };
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates rooms numbers display
     */
    private void changeRoomNumbersDisplay() {
        rooms.setText(hotel.getRoomNumbers().size() + "/1");
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds booking info to settingsPanel that displays the number of rooms in hotel
     */
    private void addBookingInfo() {
        bookingInfoPanel = new JPanel();
        bookingInfoPanel.setBackground(PINK);
        bookingInfoPanel.setPreferredSize(new Dimension(430, 36));
        bookingInfoPanel.setLayout(null);

        JLabel bookingInfoText = new JLabel("Hotel Bookings:");
        bookingInfoText.setFont(REGULAR_FONT);
        bookingInfoText.setForeground(FONT_COLOR_DARK);
        bookingInfoText.setBounds(100, 5, 150, 25);
        bookingInfoPanel.add(bookingInfoText);

        bookings = new JLabel(hotel.getBookedRoomNumbers().size() + "");
        bookings.setFont(REGULAR_FONT);
        bookings.setForeground(Color.blue.darker());
        bookings.setBounds(250, 5, 100, 25);
        bookingInfoPanel.add(bookings);

        addButtonsToBooking();

        JPanel blank = new JPanel();
        blank.setPreferredSize(new Dimension(430, 5));
        settingsPanel.add(bookingInfoPanel);
        settingsPanel.add(blank);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds view and setting buttons to bookingInfoPanel
     */
    private void addButtonsToBooking() {
        bookingSettingBtn = new JButton(new ImageIcon("data/pictures/settingSmall.png"));
        bookingSettingBtn.setBounds(380, 0, 35, 35);
        bookingSettingBtn.setBorderPainted(false);
        configBookingSettingBtn();
        bookingInfoPanel.add(bookingSettingBtn);

        viewBookingBtn = new JButton(new ImageIcon("data/pictures/view.png"));
        viewBookingBtn.setBounds(20, 0, 35, 35);
        viewBookingBtn.setBorderPainted(false);
        configBookingViewBtn();
        bookingInfoPanel.add(viewBookingBtn);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates a new table window that displays all hotel bookings
     */
    private void configBookingViewBtn() {
        viewBookingBtn.addActionListener(e -> {
            final TableWindow settingWindow = new TableWindow(
                    "View Hotel Rooms", "Rooms", imgIcon, cityRevolution, 2) {
                @Override
                protected void initTableContent() {
                    columnNames = new String[]{"Room Number", "Guest Name"};
                    HashMap<Integer, String> bookings = hotel.getBookingInfo();
                    String[] bookedRoomNumbers = new String[bookings.size()];
                    String[] guestNames = new String[bookings.size()];
                    int count = 0;
                    for (Map.Entry<Integer, String> entry : bookings.entrySet()) {
                        bookedRoomNumbers[count] = entry.getKey() + "";
                        guestNames[count] = entry.getValue();
                        count++;
                    }
                    data = new String[bookings.size()][columnNames.length];
                    for (int roomCount = 0; roomCount < bookings.size(); roomCount++) {
                        data[roomCount][0] = bookedRoomNumbers[roomCount];
                        data[roomCount][1] = guestNames[roomCount];
                    }
                }
            };
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates a new setting window for uses to add bookings
     */
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void configBookingSettingBtn() {
        bookingSettingBtn.addActionListener(e -> {
            final SettingWindow settingWindow = new SettingWindow(
                    "Hotel Bookings Setting", "Add Bookings", imgIcon, cityRevolution) {
                JTextField num;
                JComboBox<String> list;

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
                        try {
                            int num = Integer.parseInt(this.num.getText());
                            if (num > hotel.getAvailableRooms()) {
                                showMessageDialog(this, "This hotel only has "
                                        + hotel.getAvailableRooms() + " rooms available.");
                                return;
                            }
                            ArrayList<Resident> residents = cityRevolution.getCity().getResidents();
                            Resident resident = residents.get(list.getSelectedIndex());
                            hotel.makeBooking(num, resident);
                            changeBookingsInfo();
                            dispose();
                            showMessageDialog(this, "Rooms successfully added!");
                        } catch (NumberFormatException ex) {
                            showMessageDialog(this, "Number must be a positive integer.");
                        }
                    });
                }
            };
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates the number of bookings
     */
    private void changeBookingsInfo() {
        bookings.setText(hotel.getBookedRoomNumbers().size() + "");
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the background image of the window depending on the theme of city
     */
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
            HotelCheckerWindow hotelCheckerWindow = new HotelCheckerWindow(cityRevolution);
            dispose();
        });
        confirmPanel.add(checkBtn);
    }
}
