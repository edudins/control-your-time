package lv.dudins.engine;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class FileWriterEngine {

    public FileWriterEngine() {
        System.out.println("[UPDATE] : File writer engine started.");
    }

    public void writeTemplateFile(File file, JLabel statusInfo) {
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

    private void announceUpdate(String message, JFrame label) {

    }
}
