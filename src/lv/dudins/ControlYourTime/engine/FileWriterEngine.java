package lv.dudins.ControlYourTime.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static lv.dudins.ControlYourTime.literals.MessageTemplate.*;

public class FileWriterEngine {
    // File variables
    private final String fileName = "time-record.txt";
    // Objects
    LoggerEngine log;

    public FileWriterEngine(LoggerEngine loggerEngine) {
        this.log = loggerEngine;
        log.announceAndSetStatusLabel(UPDATE, "File Writer started.");
    }

    // entry to file creation from Clock Engine
    public void updateFile(String elapsedTime) {
        File file = new File(fileName);
        // Add date and info to output
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = formatter.format(date);
        String lineToFile = "\n" + "Date: " + currentDate + " Spent: " + elapsedTime;

        createFile(file);

        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, true));
            PrintWriter writeToFile = new PrintWriter(out);
            writeToFile.append(lineToFile);
            writeToFile.flush();

            // close
            out.close();
            writeToFile.close();

            // log
            log.announceAndSetStatusLabel(SUCCESS, "Wrote to file.");
        } catch(Exception e) {
            log.announce(ERROR, "Error in file operation.");
            e.printStackTrace();
        }
    }

    private void createFile(File file) {
        try {
            if (file.createNewFile()) {
                writeTemplateFile(file);
            } else {
            log.announce(SUCCESS, "Writing to existing file.");
            }
        } catch(Exception e) {
            log.announce(ERROR, "Error in file operation.");
            e.printStackTrace();
        }
    }

    private void writeTemplateFile(File file) {
        String templateString = "TIME SCHEDULE\n" +
                "\n" +
                "Date: YYYY-MM-DD at HH:MM:SS EEST Spent: HH:MM:SS | Comment\n" +
                "--------------------------------------------------------------";

        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, true));
            PrintWriter writeToFile = new PrintWriter(out);
            writeToFile.append(templateString);
            writeToFile.flush();

            // close
            out.close();
            writeToFile.close();

            // log
            log.announce(SUCCESS, "Template file created.");
        } catch(Exception e) {
            log.announce(ERROR, "Error in file operation.");
            e.printStackTrace();
        }
    }

}
