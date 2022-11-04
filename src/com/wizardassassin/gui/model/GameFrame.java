package com.wizardassassin.gui.model;

import javax.swing.*;
import java.awt.*;

public class GameFrame {

    public GameFrame(JFrame frame) {
        initialize(frame);
    }

    private void initialize(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setBackground(Color.BLACK);

        JLabel title = new JLabel();
        title.setText("Hello Game Frame");
        title.setVerticalAlignment(JLabel.TOP);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(Color.RED);
        title.setFont(new Font("papyrus", Font.BOLD, 50));

        panel.add(title);

        frame.add(panel);
    }
}
