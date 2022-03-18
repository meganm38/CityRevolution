package ui.swing;

import model.Hotel;
import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Represents an abstract window of type JDialog that is used for components' settings
public abstract class SettingWindow extends JDialog {
    protected static final int MAIN_PANEL_WIDTH = 400;
    protected static final int MAIN_PANEL_HEIGHT = 300;
    protected static final Color LIGHT_BLUE = new Color(240, 248, 255);
    protected static final Color BLUE = new Color(104, 151, 187);
    protected static final Color FONT_COLOR_DARK = new Color(0, 0, 102);
    protected static final Color PINK = new Color(223, 220, 229);

    protected static final Font TITLE_FONT = new Font("Ayuthaya", Font.BOLD, 14);
    protected static final Font REGULAR_FONT = new Font("Ayuthaya", Font.PLAIN, 12);
    protected static final int GAP = 10;

    protected JPanel mainPanel;
    protected ImageIcon imgIcon;
    protected String panelName;
    protected JPanel backgroundPanel;
    protected JPanel confirmPanel;
    protected JButton checkBtn;
    protected Hotel hotel;
    protected SwingCityRevolution cityRevolution;

    /*
     * EFFECTS: constructs a setting window
     */
    protected SettingWindow(
            String windowName, String panelName, ImageIcon imgIcon, SwingCityRevolution cityRevolution) {
        super();
        setTitle(windowName);
        this.imgIcon = imgIcon;
        this.panelName = panelName;
        this.cityRevolution = cityRevolution;
        hotel = cityRevolution.getSelectedHotel();
        setResizable(false);
        setMinimumSize(new Dimension(500, 400));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
        centreOnScreen();
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);
    }

    /*
     * EFFECTS: initiates the background and main panel
     */
    protected void init() {
        initBackground();
        initMain();
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up the background image
     */
    protected void initBackground() {
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
     * MODIFIES： this
     * EFFECTS: centers the window on screen
     */
    protected void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates the main panel
     */
    protected void initMain() {
        JPanel fake = new JPanel();
        fake.setPreferredSize(new Dimension(500, 30));
        fake.setOpaque(false);
        add(fake);
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setOpaque(true);
        mainPanel.setPreferredSize(new Dimension(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT));
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        setup();
        add(mainPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up title and background panel
     */
    protected void setup() {
        JLabel menuTitle = new JLabel(panelName);
        menuTitle.setForeground(FONT_COLOR_DARK);
        menuTitle.setFont(TITLE_FONT);
        menuTitle.setBorder(new EmptyBorder(GAP, 0, GAP, 0));
        mainPanel.add(menuTitle);

        JPanel fake = new JPanel();
        fake.setPreferredSize(new Dimension(MAIN_PANEL_WIDTH, GAP));
        fake.setOpaque(false);

        backgroundPanel = new JPanel();
        backgroundPanel.setBackground(LIGHT_BLUE);
        backgroundPanel.setPreferredSize(new Dimension(MAIN_PANEL_WIDTH - 2 * GAP, 200));
        backgroundPanel.setLayout(null);


        setupBackgroundPanel();
        mainPanel.add(backgroundPanel);
        mainPanel.add(fake);
        initConfirmPanel();
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds content to background panel
     */
    protected abstract void setupBackgroundPanel();

    /*
     * MODIFIES: this
     * EFFECTS: initiates confirm panel and sets its position
     */
    protected void initConfirmPanel() {
        confirmPanel = new JPanel();
        confirmPanel.setLayout(null);
        confirmPanel.setBackground(new Color(192, 192, 192));
        confirmPanel.setPreferredSize(new Dimension(MAIN_PANEL_WIDTH, 50));
        addButtonsToConfirmPanel();
        mainPanel.add(confirmPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds checkmark button to confirm panel
     */
    protected void addButtonsToConfirmPanel() {
        checkBtn = new JButton(new ImageIcon("data/pictures/checkmark.png"));
        checkBtn.setBounds(MAIN_PANEL_WIDTH - 50, 0, 50, 48);
        checkBtn.setBorderPainted(false);
        addBtnActionListener();
        confirmPanel.add(checkBtn);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds action listener to the checkmark button
     */
    protected abstract void addBtnActionListener();

}
