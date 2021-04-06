package lv.dudonz.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFrame extends JFrame implements ActionListener {
    private int menuWidth = 0, statusWidth = 0;
    private JButton startTimerButton = new JButton(); // So that it can be accessed from the ActionPerformed method
    private JButton stopTimerButton = new JButton();

    private JLabel runningTime = new JLabel();
    private JLabel statusInfo = new JLabel();
    private boolean running = false;

    long startTime = 0;

    private void initialize() {
        running = true;
        this.startTime = System.currentTimeMillis();
        new Thread() {
            public void run() {
                while (running) {
                    runningTime.setText(elapsedTime(startTime));
                    try {
                        Thread.sleep(1000);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    private String elapsedTime(long start) {
        long elapsedTimeInSeconds = (System.currentTimeMillis() - start) / 1000;
        long seconds = elapsedTimeInSeconds % 60;
        long minutes = (elapsedTimeInSeconds / 60) % 60;
        long hours = (elapsedTimeInSeconds / 3600) % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void createFile(File file) {
        try {
            if (file.createNewFile()) {
                statusInfo.setText("FILE CREATED");
            } else {
                statusInfo.setText("WRITING TO EXISTING FILE");
            }
        } catch(Exception e) {
            statusInfo.setText("ERROR");
            e.printStackTrace();
        }
    }

    private void updateFile(String elapsedTime) {
        File file = new File("time-record.txt");
        // Add date and info to output
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = formatter.format(date);
        String lineToFile = System.lineSeparator() + "Date: " + currentDate + " Spent: " + elapsedTime;

        createFile(file);

        try {
            // append for adding to existing data
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, true));
            PrintWriter writeToFile = new PrintWriter(out);
            writeToFile.append(lineToFile);
            writeToFile.flush();

            // close
            out.close();
            writeToFile.close();
            statusInfo.setText("WROTE TO FILE");
        } catch(Exception e) {
            statusInfo.setText("ERROR");
            e.printStackTrace();
        }


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
        status.setLayout(new BoxLayout(status, BoxLayout.Y_AXIS));

        // Buttons
        startTimerButton.setText("START");
        startTimerButton.setFocusable(false);
        startTimerButton.addActionListener(this);

        stopTimerButton.setText("STOP");
        stopTimerButton.setFocusable(false);
        stopTimerButton.addActionListener(this);

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

        // Frame
        this.setTitle("Control Your Time");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startTimerButton) {
            initialize();
            statusInfo.setText("TIMER RUNNING");
        } else if (e.getSource() == stopTimerButton) {
            running = false;
            updateFile(elapsedTime(startTime));
            runningTime.setText("00:00:00");
        }
    }
}
