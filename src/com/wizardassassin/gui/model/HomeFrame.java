package com.wizardassassin.gui.model;

import com.wizardassassin.controller.Home;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class HomeFrame {

    public HomeFrame(JFrame frame) throws IOException, URISyntaxException {
        initialize(frame);
    }

    private void initialize(JFrame frame) throws IOException, URISyntaxException {
        frame.setTitle("Wizard Assassin");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(850, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setBackground(Color.BLACK);

        JLabel title = new JLabel();
        title.setText("WIZARD ASSASSIN");
        title.setVerticalAlignment(JLabel.TOP);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(Color.RED);
        title.setFont(new Font("papyrus", Font.BOLD, 50));

        Home home = new Home();
        JLabel description = new JLabel();
        description.setText(home.gameObjective());
        description.setForeground(Color.RED);

        JButton startButton = new JButton("Start"),
                quitButton = new JButton("Quit");

        startButton.setBounds(350, 400, 80, 35);
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.RED);
        startButton.addActionListener(e -> startGame(frame, panel));

        quitButton.setBounds(450, 400, 80, 35);
        quitButton.setBackground(Color.BLACK);
        quitButton.setForeground(Color.RED);
        quitButton.addActionListener(e -> quitGame(frame));

        panel.add(title);
        panel.add(description);
        panel.add(startButton);
        panel.add(quitButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void startGame(JFrame frame, JPanel panel) {
        panel.setVisible(false);
        new GameFrame(frame);
    }

    private void quitGame(JFrame frame) {
        frame.dispose();
    }
}
