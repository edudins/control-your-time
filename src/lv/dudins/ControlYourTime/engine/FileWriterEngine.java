package lv.dudins.ControlYourTime.engine;

import lv.dudins.ControlYourTime.literals.MessageTemplate;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Remove associated Jlabel, only logger engine to have it.

public class FileWriterEngine {
    // Log variables
    String updateTemplate = "[UPDATE] : ";
    String errorTemplate = "[ERROR] : ";
    String successTemplate = "[SUCCESS] : ";
    // File variables
    private final String fileName = "time-record.txt";
    // Objects
    LoggerEngine log;

    public FileWriterEngine(LoggerEngine loggerEngine) {
        this.log = loggerEngine;
        log.announceAndSetLabel(MessageTemplate.UPDATE.get(), "File Writer started.");
    }

    private void createFile(File file, JLabel statusLabel) {
        try {
            if (file.createNewFile()) {
                writeTemplateFile(file, statusLabel);
            } else {
                announce(updateTemplate, "Writing to existing file.", statusLabel);
            }
        } catch(Exception e) {
            announce(errorTemplate, "Error in file operation.", statusLabel);
            e.printStackTrace();
        }
    }

    private void writeTemplateFile(File file, JLabel statusLabel) {
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
            announce(successTemplate, "Template file created.", statusLabel);
        } catch(Exception e) {
            announce(errorTemplate, "Error in file operation.", statusLabel);
            e.printStackTrace();
        }
    }

    // entry to file creation from TimeFrame
    public void updateFile(String elapsedTime, JLabel statusLabel) {
        File file = new File(fileName);
        // Add date and info to output
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = formatter.format(date);
        String lineToFile = "\n" + "Date: " + currentDate + " Spent: " + elapsedTime;

        createFile(file, statusLabel);

        try {
            // append for adding to existing data
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, true));
            PrintWriter writeToFile = new PrintWriter(out);
            writeToFile.append(lineToFile);
            writeToFile.flush();

            // close
            out.close();
            writeToFile.close();
            announce(successTemplate, "Wrote to file.", statusLabel);
        } catch(Exception e) {
            announce(errorTemplate, "Error in file operation.", statusLabel);
            e.printStackTrace();
        }
    }

    private String elapsedTimeString(long elapsedTime) {
        long seconds = elapsedTime % 60;
        long minutes = (elapsedTime / 60) % 60;
        long hours = (elapsedTime / 3600) % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void announce(String template, String message, JLabel label) {
        label.setText(template + message);
        System.out.println(template + message);
    }

}
