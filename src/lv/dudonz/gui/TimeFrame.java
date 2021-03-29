package lv.dudonz.gui;

import javax.swing.*;
import java.awt.*;

public class TimeFrame extends JFrame {

    public TimeFrame() {
        

        ImageIcon favicon = new ImageIcon("time_logo.png"); // create an ImageIcon
        this.setIconImage(favicon.getImage()); // change icon of this
        this.getContentPane().setBackground(new Color(0x123456)); // change colour of background

        this.setTitle("Control Your Time");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 600);
        this.setVisible(true);
    }

}
