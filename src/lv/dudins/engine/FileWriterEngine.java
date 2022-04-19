package lv.dudins.engine;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class FileWriterEngine {
    String updateTemplate = "[UPDATE] : ";
    String errorTemplate = "[ERROR] : ";
    String successTemplate = "[SUCCESS] : ";

    public FileWriterEngine() {
        System.out.println(updateTemplate + "File writer engine started.");
    }

    public void writeTemplateFile(File file, JLabel statusLabel) {
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
            announce(updateTemplate, "Template file created.", statusLabel);
        } catch(Exception e) {
            announce(errorTemplate, "There was an error.", statusLabel);
            e.printStackTrace();
        }
    }

    private void announce(String template, String message, JLabel label) {
        label.setText(template + message);
        System.out.println(template + message);
    }

}
