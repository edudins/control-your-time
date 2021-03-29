package lv.dudonz.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeFrame extends JFrame implements ActionListener {
    private int menuWidth = 0, statusWidth = 0;
    private JButton startTimerButton = new JButton();
    private JLabel runningTime = new JLabel();
    private long startTime = System.currentTimeMillis();

    private String elapsedTime() {
        long elapsedTimeInSeconds = (System.currentTimeMillis() - this.startTime) / 1000;
        long seconds = elapsedTimeInSeconds % 60;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

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

        startTimerButton.setText("SHOW TIME");
        startTimerButton.setFocusable(false);
        startTimerButton.addActionListener(this);

        // ImageIcon
        ImageIcon favicon = new ImageIcon("time_icon.png"); // create an ImageIcon
        ImageIcon logo = new ImageIcon("time_logo.png");
        this.setIconImage(favicon.getImage()); // change icon of this
        this.getContentPane().setBackground(new Color(0x123456)); // change colour of background

        // Labels
        JLabel companyLogo = new JLabel();
        companyLogo.setIcon(logo);
        menu.add(companyLogo);

        runningTime.setText("HH:MM:SS");
        status.add(runningTime);
        runningTime.setFont(new Font("Ubuntu", Font.BOLD, 20));
        runningTime.setForeground(Color.WHITE);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startTimerButton) {
            runningTime.setText(elapsedTime());
        }
    }
}
