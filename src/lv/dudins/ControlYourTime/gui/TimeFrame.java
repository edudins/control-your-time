package lv.dudins.ControlYourTime.gui;

import lv.dudins.ControlYourTime.engine.ClockEngine;
import lv.dudins.ControlYourTime.engine.LoggerEngine;

import javax.swing.*;
import java.awt.*;

public class TimeFrame extends JFrame {
    // Dims
    private final int frameWidth = 600;
    private final int frameHeight = 400;
    private int menuWidth, statusWidth;

    // JLabels
    private JLabel runningTime = new JLabel();
    private JLabel statusInfo = new JLabel();

    // Files
    private final String iconLocation = "res/time_icon.png";
    private final String logoLocation = "res/time_logo.png";

    // Objects and injections
    LoggerEngine loggerEngine = new LoggerEngine(statusInfo, runningTime);
    ClockEngine clock = new ClockEngine(loggerEngine);

    // JButtons
    private ResponsiveJButton startTimerButton = new ResponsiveJButton(clock, loggerEngine);
    private ResponsiveJButton stopTimerButton = new ResponsiveJButton(clock, loggerEngine);
    private ResponsiveJButton pauseTimerButton = new ResponsiveJButton(clock, loggerEngine);

    public TimeFrame() {
        // Get dims
        this.setSize(frameWidth, frameHeight);
        this.menuWidth = this.getWidth() / 3;
        this.statusWidth = this.getWidth() - menuWidth;

        // Panels
        JPanel menu = new JPanel();
        menu.setBackground(new Color(0x123456));
        menu.setBounds(0,0,menuWidth,this.getHeight());

        JPanel status = new JPanel();
        status.setBackground(new Color(0x103C68));
        status.setBounds(menuWidth,0,statusWidth,this.getHeight());
        status.setLayout(new BoxLayout(status, BoxLayout.Y_AXIS));

        // Buttons
        startTimerButton.setText("START");
        startTimerButton.setFocusable(false);

        stopTimerButton.setText("STOP");
        stopTimerButton.setFocusable(false);

        pauseTimerButton.setText("PAUSE");
        pauseTimerButton.setFocusable(false);

        // ImageIcon
        ImageIcon favicon = new ImageIcon(iconLocation); // create an ImageIcon
        ImageIcon logo = new ImageIcon(logoLocation);
        this.setIconImage(favicon.getImage()); // change icon of this
        this.getContentPane().setBackground(new Color(0x123456)); // change colour of background

        // Labels
        JLabel companyLogo = new JLabel();
        companyLogo.setIcon(logo);
        menu.add(companyLogo);

        runningTime.setText("HH:MM:SS");
        status.add(runningTime);
        runningTime.setFont(new Font("Ubuntu", Font.BOLD, 40));
        runningTime.setForeground(Color.WHITE);
        runningTime.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusInfo.setText("STATUS UPDATES");
        status.add(statusInfo);
        statusInfo.setFont(new Font("Ubuntu", Font.PLAIN, 16));
        statusInfo.setForeground(new Color(0xB6C9EC));
        statusInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Layout
        this.setLayout(null);

        // Add components to Frame
        this.add(menu);
        this.add(status);
        menu.add(startTimerButton);
        menu.add(stopTimerButton);
        menu.add(pauseTimerButton);

        // Frame
        this.setTitle("Control Your Time");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.setVisible(true);
    }

}
