package ui.swing;

import model.City;
import model.Resident;
import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class ResidentCheckerWindow extends Window {
    private static final int DISPLAY_PANEL_WIDTH = 450;
    private static final int DISPLAY_PANEL_HEIGHT = 330;
    private final City city;
    private final SwingCityRevolution cityRevolution;

    private JPanel displayPanel;
    private JPanel hotelPanel;
    private JList<String> residentList;
    private JScrollPane residentScrollPane;
    private JPanel confirmPanel;

    public ResidentCheckerWindow(SwingCityRevolution cityRevolution) {
        super();
        this.city = cityRevolution.getCity();
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
        JLabel menuTitle = new JLabel("CITY CONTENTS");
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

    private void initDisplayPanel() {
        JPanel cityInfoText = new JPanel();
        cityInfoText.setBackground(BLUE);
        JLabel label = new JLabel("SELECT RESIDENT");
        label.setForeground(Color.white);
        cityInfoText.setPreferredSize(new Dimension(450, 30));
        cityInfoText.add(label);

        displayPanel = new JPanel();
        displayPanel.setPreferredSize(new Dimension(450, 330));
        displayPanel.setBackground(LIGHT_BLUE);
        displayPanel.setLayout(null);

        initResidentPanel();
        mainPanel.add(cityInfoText);
        mainPanel.add(displayPanel);
    }

    private void initResidentPanel() {
        hotelPanel = new JPanel();
        hotelPanel.setBackground(new Color(171, 209, 201));
        hotelPanel.setBounds(hotelPanel.getWidth() + GAP * 3, GAP,
                DISPLAY_PANEL_WIDTH - GAP * 6, DISPLAY_PANEL_HEIGHT - 2 * GAP);

        ArrayList<Resident> residentArrayList = city.getResidents();
        String[] residentNamesArray = new String[residentArrayList.size()];
        for (int i = 0; i < residentArrayList.size(); i++) {
            residentNamesArray[i] = residentArrayList.get(i).getName();
        }
        residentList = new JList<>(residentNamesArray);
        residentList.setLayoutOrientation(JList.VERTICAL);
        residentList.setForeground(FONT_COLOR_DARK);
        residentList.setFont(new Font("Serif", Font.PLAIN, 15));
        residentScrollPane = new JScrollPane();
        residentScrollPane.setViewportView(residentList);
        hotelPanel.setLayout(null);
        residentScrollPane.setBounds(GAP, GAP, hotelPanel.getWidth() - 2 * GAP, hotelPanel.getHeight() - 2 * GAP);

        hotelPanel.add(residentScrollPane);
        displayPanel.add(hotelPanel);
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
            if (residentList.getSelectedIndex() != -1) {
                cityRevolution.selectResident(residentList.getSelectedIndex());
                ResidentManagementWindow residentManagementWindow = new ResidentManagementWindow(cityRevolution);
                dispose();
            } else {
                showMessageDialog(this, "Select a resident to proceed");
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
