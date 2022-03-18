package ui.swing;

import org.json.JSONException;
import ui.swing.simulators.SwingCityRevolution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

public class MainWindow extends JFrame {
    private JPanel buttonsPanel;
    private final SwingCityRevolution cityRevolution;

    public MainWindow(SwingCityRevolution cityRevolution) {
        super("City Revolution");
        this.cityRevolution = cityRevolution;
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
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setPreferredSize(new Dimension(this.getPreferredSize().width, 400));
        initButtons();
        initSaveBtn();
        initLoadBtn();
        add(buttonsPanel);
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
        JPanel titlePanel = new JPanel();
        ImageIcon imgIcon = new ImageIcon("data/pictures/city revolution.png");
        Image img = imgIcon.getImage();
        JLabel pic = new JLabel(new ImageIcon(img));
        pic.setBorder(new EmptyBorder(50, 50, 50, 50));
        titlePanel.add(pic);
        titlePanel.setOpaque(false);

        add(titlePanel);
    }

    private void initButtons() {
        JButton newCityBtn = new JButton(new ImageIcon("data/pictures/newcitybutton.png"));
        newCityBtn.setPreferredSize(new Dimension(110, 50));
        newCityBtn.setBorder(new EmptyBorder(30, 300, 20, 20));
        newCityBtn.addActionListener(e -> {
            CreateCityWindow createCityWindow = new CreateCityWindow(new SwingCityRevolution());
            dispose();
        });

        JButton resumeBtn = new JButton(new ImageIcon("data/pictures/resume.png"));
        resumeBtn.setPreferredSize(new Dimension(110, 50));
        resumeBtn.setBorder(new EmptyBorder(5, 300, 20, 20));
        resumeBtn.addActionListener(e -> {
            if (cityRevolution.getCity() == null) {
                showMessageDialog(this, "You have not created any city.");
            } else {
                CityWindow cityWindow = new CityWindow(cityRevolution);
                dispose();
            }
        });

        buttonsPanel.add(newCityBtn);
        buttonsPanel.add(resumeBtn);
        buttonsPanel.setOpaque(false);
    }

    private void initSaveBtn() {
        JButton saveBtn = new JButton(new ImageIcon("data/pictures/save.png"));
        saveBtn.setPreferredSize(new Dimension(110, 50));
        saveBtn.setBorder(new EmptyBorder(5, 300, 20, 20));
        saveBtn.addActionListener(e -> {
            if (cityRevolution.getCity() == null) {
                showMessageDialog(this, "No city to save.");
                return;
            }
            try {
                cityRevolution.saveCityToJson();
                showMessageDialog(this, "City info has been saved.");
            } catch (FileNotFoundException fileNotFoundException) {
                showMessageDialog(this, "Cannot open a Json file to write.");
            }
        });

        buttonsPanel.add(saveBtn);
    }

    private void initLoadBtn() {
        JButton loadBtn = new JButton(new ImageIcon("data/pictures/load.png"));
        loadBtn.setPreferredSize(new Dimension(110, 50));
        loadBtn.setBorder(new EmptyBorder(5, 300, 20, 20));
        loadBtn.addActionListener(e -> {
            try {
                cityRevolution.loadCity();
                showMessageDialog(this, "City info has been successfully loaded.\n"
                        + "Click Resume to continue.");
            } catch (IOException ioException) {
                showMessageDialog(this, "Json file not found. City not loaded.");
            } catch (JSONException ex) {
                showMessageDialog(this, "Empty Json file. City not loaded.");

            }
        });
        buttonsPanel.add(loadBtn);
    }

    // MODIFIES: this
    // EFFECTS:  frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }
}
