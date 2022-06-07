package lv.dudins.ControlYourTime.gui;

import lv.dudins.ControlYourTime.engine.ClockEngine;
import lv.dudins.ControlYourTime.engine.LoggerEngine;
import lv.dudins.ControlYourTime.literals.MessageTemplate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        log.announceAndSetLabel(MessageTemplate.UPDATE.getTemplate(), command);
        switch (command) {
            case "START":
                clock.start();
                break;
            case "STOP":
                clock.stop();
                break;
            case "PAUSE":
                clock.toggle();
                break;
        }
    }

}
