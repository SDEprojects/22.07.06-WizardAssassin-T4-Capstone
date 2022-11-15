package com.wizardassassin.view.model;

import com.wizardassassin.model.Home;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

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

        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 60));
        panel.setBackground(Color.BLACK);

        title = new JLabel();
        title.setText("WIZARD ASSASSIN");
        title.setForeground(Color.RED);
        title.setFont(new Font("papyrus", Font.BOLD, 50));
        ImageIcon image =
                (new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("homelogo.jpg"))));
        title.setIcon(image);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.TOP);

        Home home = new Home();
        description = new JTextArea();
        description.setText(home.gameObjective());
        description.setForeground(Color.RED);
        description.setBackground(Color.BLACK);
        description.setFont(new Font("papyrus", Font.ITALIC, 17));
        description.setEditable(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(400, 200, 300, 400);
        buttonPanel.setBackground(Color.BLACK);

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
        quitButton.addActionListener(e -> {
            frame.dispose();
            System.exit(0);
        });


        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);

        panel.add(title);
        panel.add(description);
        panel.add(buttonPanel);

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
