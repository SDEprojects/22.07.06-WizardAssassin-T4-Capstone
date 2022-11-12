package com.wizardassassin.view.client;

import com.wizardassassin.view.model.HomeFrame;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class MainFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Wizard Assassin");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(950, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        SwingUtilities.invokeLater(() -> {
            try {
                new HomeFrame(frame);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }
}
