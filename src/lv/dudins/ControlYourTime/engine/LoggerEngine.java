package lv.dudins.ControlYourTime.engine;

import lv.dudins.ControlYourTime.literals.MessageTemplate;

import javax.swing.*;

public class LoggerEngine {

    // UI variables
    JLabel label;

    public LoggerEngine(JLabel label) {
        this.label = label;
        announce(MessageTemplate.UPDATE.get(), "Logger started.");
    }

    public void setLabel(String template, String status) {
        label.setText(template + status);
    }

    public void announce(String template, String message) {
        System.out.println(template + message);
    }

    public void announceAndSetLabel(String template, String message) {
        label.setText(template + message);
        System.out.println(template + message);
    }
}
