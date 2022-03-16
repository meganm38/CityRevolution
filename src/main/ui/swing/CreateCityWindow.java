package ui.swing;

import model.City;
import ui.swing.Simulators.SwingCityRevolution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class CreateCityWindow extends JFrame {
    private static final Color LIGHT_BLUE = new Color(240, 248, 255);
    private static final Color BLUE = new Color(104, 151, 187);
    private static final Color FONT_COLOR_DARK = new Color(0, 0, 102);
    private static final int THEME_IMAGE_DIMENSIONS = 150;

    private SwingCityRevolution cityRevolution;
    private JPanel mainPanel;
    private JPanel settingPanel;
    private JPanel themePanel;
    private JPanel confirmPanel;
    private JTextField inputField;
    private City.Theme theme;
    private String cityName;

    public CreateCityWindow() {
        super("City Revolution");
        cityRevolution = new SwingCityRevolution();
        setResizable(false);
        setMinimumSize(new Dimension(1024, 576));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        centreOnScreen();
        setVisible(true);
    }

    private void init() {
        initBackground();
        initMain();
    }

    private void initBackground() {
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


    private void initMain() {
        JPanel fake = new JPanel();
        fake.setPreferredSize(new Dimension(1024, 30));
        fake.setOpaque(false);
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setOpaque(true);
        mainPanel.setPreferredSize(new Dimension(500, 476));
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setupMenu();
        add(fake);
        add(mainPanel);
    }

    private void setupMenu() {
        JLabel menuTitle = new JLabel("NEW CITY");
        menuTitle.setForeground(FONT_COLOR_DARK);
        menuTitle.setFont(new Font("Ayuthaya", Font.BOLD, 28));
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

    private void configSettingPanel() {
        JLabel cityNameText = new JLabel("City Name:");
        inputField = new JTextField();
        cityNameText.setBounds(90, 25, 100, 50);
        cityNameText.setForeground(FONT_COLOR_DARK);
        inputField.setBounds(180, 25, 150, 50);
        inputField.setHorizontalAlignment(JTextField.CENTER);
        settingPanel.add(cityNameText);
        settingPanel.add(inputField);
    }

    private void initThemePanel() {
        JPanel textPanel = new JPanel();
        textPanel.setBackground(BLUE);
        JLabel label = new JLabel("THEME SETTINGS");
        label.setForeground(Color.white);
        textPanel.setPreferredSize(new Dimension(450, 30));
        textPanel.add(label);

        themePanel = new JPanel();
        themePanel.setPreferredSize(new Dimension(450, 200));
        themePanel.setBackground(LIGHT_BLUE);
        themePanel.setLayout(null);

        configThemePanel();
        mainPanel.add(textPanel);
        mainPanel.add(themePanel);
    }

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

    private void addThemeButtons() {
        JButton lightThemeBtn = new JButton(new ImageIcon("data/pictures/lighttheme.png"));
        JButton darkThemeBtn = new JButton(new ImageIcon("data/pictures/darktheme.png"));
        lightThemeBtn.setBounds(60, 20, THEME_IMAGE_DIMENSIONS, THEME_IMAGE_DIMENSIONS);
        darkThemeBtn.setBounds(80 + THEME_IMAGE_DIMENSIONS, 20, THEME_IMAGE_DIMENSIONS, THEME_IMAGE_DIMENSIONS);
        lightThemeBtn.setBorder(BorderFactory.createEtchedBorder(0));
        darkThemeBtn.setBorder(BorderFactory.createEtchedBorder(1));
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
        themePanel.add(lightThemeBtn);
        themePanel.add(darkThemeBtn);
    }

    private void initConfirmPanel() {
        confirmPanel = new JPanel();
        confirmPanel.setLayout(null);
        confirmPanel.setBackground(new Color(192, 192, 192));
        confirmPanel.setPreferredSize(new Dimension(500, 50));
        JButton checkBtn = new JButton(new ImageIcon("data/pictures/checkmark.png"));
        checkBtn.setBounds(450, 0, 50, 48);
        checkBtn.setBorderPainted(false);
        checkBtn.addActionListener(e -> {
            cityName = inputField.getText();
            if (cityName != null && theme != null) {
                City city = new City(cityName, theme);
                CityWindow cityWindow = new CityWindow(city);
                setVisible(false);
            } else {
                showMessageDialog(this, "Empty Name/Theme");
            }
        });

        confirmPanel.add(checkBtn);
        mainPanel.add(confirmPanel);
    }

    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }
}
