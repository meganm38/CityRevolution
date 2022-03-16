package ui.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainWindow extends JFrame {
    private JPanel titlePanel;
    private JPanel buttonsPanel;

    public MainWindow() {
        super("City Revolution");
        setResizable(false);
        setMinimumSize(new Dimension(1024, 576));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        centreOnScreen();
        setVisible(true);
    }

    private void init() {
        setBackGround();
        setTitle();
        initButtons();
    }

    private void setBackGround() {
        ImageIcon imgIcon = new ImageIcon("data/pictures/background.png");
        Image img = imgIcon.getImage();
        setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, null);
            }
        });
        pack();
    }

    private void setTitle() {
        titlePanel = new JPanel();
        ImageIcon imgIcon = new ImageIcon("data/pictures/city revolution.png");
        Image img = imgIcon.getImage();
        JLabel pic = new JLabel(new ImageIcon(img));
        pic.setBorder(new EmptyBorder(50, 50, 50, 50));
        titlePanel.add(pic);
        titlePanel.setOpaque(false);

        add(titlePanel);
    }

    private void initButtons() {
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setPreferredSize(new Dimension(this.getPreferredSize().width, 400));
        JButton newCityBtn = new JButton(new ImageIcon("data/pictures/newcitybutton.png"));
        newCityBtn.setPreferredSize(new Dimension(110, 50));
        newCityBtn.setBorder(new EmptyBorder(50, 300, 20, 20));
        newCityBtn.addActionListener(e -> {
            CreateCityWindow createCityWindow = new CreateCityWindow(null, null);
            dispose();
        });

        JButton exitBtn = new JButton(new ImageIcon("data/pictures/exitbutton.png"));
        exitBtn.setPreferredSize(new Dimension(110, 50));
        exitBtn.setBorder(new EmptyBorder(50, 300, 20, 20));
        exitBtn.addActionListener(e -> System.exit(0));

        buttonsPanel.add(newCityBtn);
        buttonsPanel.add(exitBtn);
        buttonsPanel.setOpaque(false);
        add(buttonsPanel);
    }

    // MODIFIES: this
    // EFFECTS:  frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }
}
