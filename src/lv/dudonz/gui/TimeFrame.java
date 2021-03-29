package lv.dudonz.gui;

import javax.swing.*;
import java.awt.*;

public class TimeFrame extends JFrame {
    private int menuWidth = 0, menuHeight = 0, statusWidth = 0, statusHeight = 0;

    public TimeFrame() {
        // Get dims
        this.menuWidth = this.getWidth() / 3;
        this.menuHeight = this.getHeight();
        this.statusWidth = this.getWidth() - menuWidth;
        this.statusHeight = this.getHeight();

        // Panels
        JPanel menu = new JPanel();
        menu.setBackground(new Color(0x123456));
        menu.setBounds(0,0,135,600);

        JPanel status = new JPanel();
        status.setBackground(new Color(0x103C68));
        status.setBounds(135,0,310,600);


        // ImageIcon
        ImageIcon favicon = new ImageIcon("time_logo.png"); // create an ImageIcon
        this.setIconImage(favicon.getImage()); // change icon of this
        this.getContentPane().setBackground(new Color(0x123456)); // change colour of background

        // Layout
        this.setLayout(null);

        // Add components to Frame
        this.add(menu);
        this.add(status);

        // Frame
        this.setTitle("Control Your Time");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(420, 420);

        this.setVisible(true);
    }

}
