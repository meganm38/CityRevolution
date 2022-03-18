package ui.swing;

import javax.swing.*;
import java.awt.*;

// Represents an abstract window of the game with necessary colors and fonts
public abstract class Window extends JFrame {
    protected static final Color LIGHT_BLUE = new Color(240, 248, 255);
    protected static final Color BLUE = new Color(104, 151, 187);
    protected static final Color FONT_COLOR_DARK = new Color(0, 0, 102);
    protected static final Color PINK = new Color(223, 220, 229);
    protected static final int THEME_IMAGE_DIMENSIONS = 150;
    protected static final int MAIN_PANEL_WIDTH = 500;
    protected static final int MAIN_PANEL_HEIGHT = 476;
    protected static final Font TITLE_FONT = new Font("Ayuthaya", Font.BOLD, 28);
    protected static final Font REGULAR_FONT = new Font("Ayuthaya", Font.PLAIN, 14);
    protected static final int GAP = 10;

    protected JPanel mainPanel;

    /*
     *  EFFECTS: constructs a window with the game title
     */
    protected Window() {
        super("City Revolution");
        setResizable(false);
        setMinimumSize(new Dimension(1024, 576));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
     *  EFFECTS: initiates the background and main panel
     */
    protected abstract void init();

    /*
     *  EFFECTS: initiates the background image
     */
    protected abstract void initBackground();

    /*
     * MODIFIES: this
     * EFFECTS: centers window on screen
     */
    protected void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initiates and sets up the main panel
     */
    protected void initMain() {
        JPanel fake = new JPanel();
        fake.setPreferredSize(new Dimension(1024, 30));
        fake.setOpaque(false);
        add(fake);
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setOpaque(true);
        mainPanel.setPreferredSize(new Dimension(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT));
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    }
}
