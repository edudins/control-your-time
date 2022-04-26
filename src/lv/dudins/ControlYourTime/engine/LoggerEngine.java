package lv.dudins.ControlYourTime.engine;

import lv.dudins.ControlYourTime.literals.MessageTemplate;

import javax.swing.*;

public class LoggerEngine {

    // UI variables
    JLabel label;

    public LoggerEngine(JLabel label) {
        this.label = label;
        announce(MessageTemplate.UPDATE.getTemplate(), "Logger started.");
    }

    public void setLabel(String template, String status) {
        this.label.setText(template + status);
    }

    public void announce(String template, String message) {
        System.out.println(template + message);
    }
}
