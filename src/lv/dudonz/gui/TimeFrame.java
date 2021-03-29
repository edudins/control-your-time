package lv.dudonz.gui;

import javax.swing.*;
import java.awt.*;

public class TimeFrame extends JFrame {
    private int menuWidth = 0, statusWidth = 0;

    public TimeFrame() {
        // Get dims
        this.setSize(600, 650);
        this.menuWidth = this.getWidth() / 3;
        this.statusWidth = this.getWidth() - menuWidth;

        // Panels
        JPanel menu = new JPanel();
        menu.setBackground(new Color(0x123456));
        menu.setBounds(0,0,menuWidth,this.getHeight());

        JPanel status = new JPanel();
        status.setBackground(new Color(0x103C68));
        status.setBounds(menuWidth,0,statusWidth,this.getHeight());

        // Buttons
        JButton startTimerButton = new JButton();
        startTimerButton.setText("START");
        startTimerButton.setFocusable(false);
        startTimerButton.setSize(menuWidth, 20);


        // ImageIcon
        ImageIcon favicon = new ImageIcon("time_icon.png"); // create an ImageIcon
        ImageIcon logo = new ImageIcon("time_logo.png");
        this.setIconImage(favicon.getImage()); // change icon of this
        this.getContentPane().setBackground(new Color(0x123456)); // change colour of background

        // Labels
        JLabel companyLogo = new JLabel();
        companyLogo.setIcon(logo);
        menu.add(companyLogo);

        // Layout
        this.setLayout(null);

        // Add components to Frame
        this.add(menu);
        this.add(status);
        menu.add(startTimerButton);

        // Frame
        this.setTitle("Control Your Time");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.setVisible(true);
    }

}
