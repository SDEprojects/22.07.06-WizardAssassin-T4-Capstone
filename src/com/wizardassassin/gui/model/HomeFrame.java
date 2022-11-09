package com.wizardassassin.gui.model;

import com.wizardassassin.model.Home;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class HomeFrame {

    JPanel panel;
    JLabel title;
    JTextArea description;
    JButton startButton;
    JButton quitButton;

    public HomeFrame(JFrame frame) throws IOException, URISyntaxException {
        initialize(frame);
    }

    private void initialize(JFrame frame) throws IOException, URISyntaxException {
        frame.setTitle("Wizard Assassin");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(950, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 70));
        panel.setBackground(Color.BLACK);

        title = new JLabel();
        title.setText("WIZARD ASSASSIN");
        title.setVerticalAlignment(JLabel.TOP);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(Color.RED);
        title.setFont(new Font("papyrus", Font.BOLD, 50));

        Home home = new Home();
        description = new JTextArea();
        description.setText(home.gameObjective());
        description.setForeground(Color.RED);
        description.setBackground(Color.BLACK);
        description.setFont(new Font("papyrus", Font.ITALIC, 17));

        startButton = new JButton("Start");
        quitButton = new JButton("Quit");

        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.RED);
        startButton.addActionListener(e -> {
            try {
                startGame(frame, panel);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        quitButton.setBackground(Color.BLACK);
        quitButton.setForeground(Color.RED);
        quitButton.addActionListener(e -> frame.dispose());

        panel.add(title);
        panel.add(description);
        panel.add(startButton);
        panel.add(quitButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void startGame(JFrame frame, JPanel panel) throws IOException, URISyntaxException {

        panel.setVisible(false);
        new GameFrame(frame, panel);
    }

    public void quitGame(JFrame frame) {
        frame.dispose();
    }

    public JPanel getPanel() {
        return panel;
    }

    public JLabel getTitle() {
        return title;
    }

    public JTextArea getDescription() {
        return description;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getQuitButton() {
        return quitButton;
    }
}
