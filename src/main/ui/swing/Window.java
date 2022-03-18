package ui.swing;

import javax.swing.*;
import java.awt.*;

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

    protected Window() {
        super("City Revolution");
        setResizable(false);
        setMinimumSize(new Dimension(1024, 576));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected abstract void init();

    protected abstract void initBackground();

    protected void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

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
