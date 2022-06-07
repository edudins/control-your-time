package lv.dudins.ControlYourTime.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResponsiveJButton extends JButton implements ActionListener {

    public ResponsiveJButton() {
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("ResponsiveJButton Action Performed ... \n" + e);
    }
}
