package ui.swing;

import model.Hotel;
import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import java.awt.*;

public class HotelManagementWindow extends Window {
    private final SwingCityRevolution cityRevolution;
    private final Hotel hotel;

    public HotelManagementWindow(Hotel hotel, SwingCityRevolution cityRevolution) {
        super();
        this.hotel = hotel;
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
        //setupMenu();
        add(mainPanel);
    }

    @Override
    protected void initBackground() {
        ImageIcon imgIcon;
        if (hotel.getTheme().equals(Hotel.Theme.SKI)) {
            imgIcon = new ImageIcon("data/pictures/skiBackground.png");
        } else {
            imgIcon = new ImageIcon("data/pictures/beachBackground.png");
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
}
