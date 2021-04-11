package lv.dudins.gui;

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
    // Dims
    private int frameWidth = 600;
    private int frameHeight = 400;
    private int menuWidth = 0, statusWidth = 0;
    // JButtons
    private JButton startTimerButton = new JButton(); // So that it can be accessed from the ActionPerformed method
    private JButton stopTimerButton = new JButton();
    private JButton pauseTimerButton = new JButton();
    // JLabels
    private JLabel runningTime = new JLabel();
    private JLabel statusInfo = new JLabel();
    // File variables
    private String fileName = "time-record.txt";
    private String iconLocation = "Resources Root/time_icon.png";
    private String logoLocation = "Resources Root/time_logo.png";
    // Time control variables
    private long elapsedTime = -1; // So that timer starts with 0
    private int oneSecond = 1000;
    private boolean running = false;

    private void initialize() {
        running = true;
        new Thread() {
            public void run() {
                while (running) {
                    elapsedTime++;
                    runningTime.setText(elapsedTimeString(elapsedTime));
                    try {
                        Thread.sleep(oneSecond);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private String elapsedTimeString(long elapsedTime) {
        long seconds = elapsedTime % 60;
        long minutes = (elapsedTime / 60) % 60;
        long hours = (elapsedTime / 3600) % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void writeTemplate(File file) {
        String templateString = "TIME SCHEDULE\n" +
                "\n" +
                "Date: YYYY-MM-DD at HH:MM:SS EEST Spent: HH:MM:SS | Comment\n" +
                "--------------------------------------------------------------";

        try {
            // append for adding to existing data
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, true));
            PrintWriter writeToFile = new PrintWriter(out);
            writeToFile.append(templateString);
            writeToFile.flush();

            // close
            out.close();
            writeToFile.close();
            statusInfo.setText("TEMPLATE HAS BEEN SET UP");
        } catch(Exception e) {
            statusInfo.setText("ERROR");
            e.printStackTrace();
        }
    }

    private void createFile(File file) {
        try {
            if (file.createNewFile()) {
                statusInfo.setText("FILE CREATED");
                writeTemplate(file);
            } else {
                statusInfo.setText("WRITING TO EXISTING FILE");
            }
        } catch(Exception e) {
            statusInfo.setText("ERROR");
            e.printStackTrace();
        }
    }

    private void updateFile(String elapsedTime) {
        File file = new File(fileName);
        // Add date and info to output
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = formatter.format(date);
        String lineToFile = "\n" + "Date: " + currentDate + " Spent: " + elapsedTime;

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
        if (e.getSource() == startTimerButton) {
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
        } else if (e.getSource() == stopTimerButton) {
            running = false;
            updateFile(elapsedTimeString(elapsedTime));
            runningTime.setText("00:00:00");
            elapsedTime = 0;
        }
    }

}
