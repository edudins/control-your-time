package lv.dudins.ControlYourTime.gui;

import lv.dudins.ControlYourTime.engine.FileWriterEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeFrame extends JFrame implements ActionListener {
    // Objects
    FileWriterEngine fileWriterEngine = new FileWriterEngine();

    // Dims
    private final int frameWidth = 600;
    private final int frameHeight = 400;
    private int menuWidth, statusWidth;
    // JButtons
    private JButton startTimerButton = new JButton();
    private JButton stopTimerButton = new JButton();
    private JButton pauseTimerButton = new JButton();
    // JLabels
    private JLabel runningTime = new JLabel();
    private JLabel statusInfo = new JLabel();

    private final String iconLocation = "time_icon.png";
    private final String logoLocation = "time_logo.png";
    // Time control variables
    private long elapsedTime = -1; // So that timer starts with 0
    private final int oneSecond = 1000;
    private boolean running = false;

    private void initialize() {
        running = true;
        new Thread(() -> {
            while (running) {
                elapsedTime++;
                runningTime.setText(elapsedTimeString(elapsedTime));
                try {
                    Thread.sleep(oneSecond);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String elapsedTimeString(long elapsedTime) {
        long seconds = elapsedTime % 60;
        long minutes = (elapsedTime / 60) % 60;
        long hours = (elapsedTime / 3600) % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

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
        startTimerButton.addActionListener(this);

        stopTimerButton.setText("STOP");
        stopTimerButton.setFocusable(false);
        stopTimerButton.addActionListener(this);

        pauseTimerButton.setText("PAUSE TOGGLE");
        pauseTimerButton.setFocusable(false);
        pauseTimerButton.addActionListener(this);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startTimerButton && !running) {
            initialize();
            statusInfo.setText("TIMER RUNNING");
        } else if (e.getSource() == pauseTimerButton) {
            if (running) {
                running = false;
                statusInfo.setText("PAUSED");
            } else {
                elapsedTime--; // So elapsed time doesn't immediately go up by one
                initialize();
                statusInfo.setText("TIMER RUNNING");
            }
    } else if (e.getSource() == stopTimerButton && running) {
            running = false;
            fileWriterEngine.updateFile(elapsedTimeString(elapsedTime), statusInfo);
            runningTime.setText("00:00:00");
            elapsedTime = 0;
        }
    }

}
