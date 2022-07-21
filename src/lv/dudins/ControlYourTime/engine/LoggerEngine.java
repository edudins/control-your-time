package lv.dudins.ControlYourTime.engine;

import lv.dudins.ControlYourTime.literals.MessageTemplate;

import javax.swing.*;

import static lv.dudins.ControlYourTime.literals.MessageTemplate.UPDATE;

public class LoggerEngine {

    // UI variables
    JLabel statusLabel;
    JLabel infoLabel;

    public LoggerEngine(JLabel statusLabel, JLabel infoLabel) {
        this.statusLabel = statusLabel;
        this.infoLabel = infoLabel;
        announce(UPDATE, "Logger started.");
    }

    public void setStatusLabel(MessageTemplate template, String status) {
        statusLabel.setText(template.build() + status);
    }

    public void setInfoLabel(String status) {
        infoLabel.setText(status);
    }

    public void announce(MessageTemplate template, String message) {
        System.out.println(template.build() + message);
    }

    public void announceAndSetStatusLabel(MessageTemplate template, String message) {
        statusLabel.setText(template.build() + message);
        System.out.println(template.build() + message);
    }
}
