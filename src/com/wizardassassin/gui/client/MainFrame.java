package com.wizardassassin.gui.client;

import com.wizardassassin.gui.model.HomeFrame;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class MainFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        SwingUtilities.invokeLater(() -> {
            try {
                new HomeFrame(frame);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }
}
