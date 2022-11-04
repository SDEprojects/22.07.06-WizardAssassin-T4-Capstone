package com.wizardassassin.gui.screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private int count = 0;
    private JLabel label, titleLabel;
    private JFrame frame;
    Container con;
    JButton startButton, QuitButton;
    TitleScreenHandler tsHandler = new TitleScreenHandler();

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        frame = new JFrame();

        titleLabel = new JLabel();
        titleLabel.setText("WIZARD ASSASSIN");
        titleLabel.setVerticalAlignment(JLabel.TOP);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("papyrus", Font.BOLD, 50));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Wizard Assassin");
        frame.setResizable(false);
        frame.setSize(850, 600);
        frame.setVisible(true);
        frame.add(titleLabel);
        con = frame.getContentPane();
        frame.getContentPane().setBackground(Color.BLACK);

        startButton = new JButton("Start");
        QuitButton = new JButton("Quit");
        //Buttons
        startButton.setBounds(350, 400, 80, 35);
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.RED);
        con.add(startButton);
        startButton.addActionListener(tsHandler);

        QuitButton.setBounds(450, 400, 80, 35);
        QuitButton.setBackground(Color.BLACK);
        QuitButton.setForeground(Color.RED);
        con.add(QuitButton);
    }

    public void nextLevel() {
        titleLabel.setVisible(false);
        startButton.setVisible(false);
        QuitButton.setVisible(false);
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.startGameThread();
    }

    public class TitleScreenHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            nextLevel();
        }
    }

}

