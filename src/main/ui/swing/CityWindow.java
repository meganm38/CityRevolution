package ui.swing;

import model.City;

import javax.swing.*;
import java.awt.*;

public class CityWindow extends JFrame {
    private City city;

    public CityWindow(City city) {
        super("City Revolution");
        this.city = city;
        setResizable(false);
        setMinimumSize(new Dimension(1024, 576));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        centreOnScreen();
        setVisible(true);
    }

    private void init() {

    }

    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }
}
