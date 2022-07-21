package lv.dudins.ControlYourTime.gui;

import lv.dudins.ControlYourTime.engine.ClockEngine;
import lv.dudins.ControlYourTime.engine.LoggerEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static lv.dudins.ControlYourTime.literals.MessageTemplate.UPDATE;

public class ResponsiveJButton extends JButton implements ActionListener {
    ClockEngine clock;
    LoggerEngine log;

    public ResponsiveJButton(ClockEngine clock, LoggerEngine log) {
        this.addActionListener(this);
        this.clock = clock;
        this.log = log;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        log.announceAndSetStatusLabel(UPDATE, command);
        switch (command) {
            case "START":
                clock.start();
                break;
            case "STOP":
                clock.stop();
                break;
            case "PAUSE":
                clock.pause();
                break;
        }
    }

}
