package com.wizardassassin.gui.screen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {

   private int count = 0;
   private JLabel label;
   private JFrame frame;
   private JButton button;

    public GUI() {

        frame = new JFrame();

        button = new JButton("Click Me If You Are A Badass Wizard Assassin");
        button.addActionListener(this);
        label = new JLabel("Number of Wizards Killed: 0");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Wizard Assassin");

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.startGameThread();
    }

    public static void main(String[] args) {
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        label.setText("Number of Wizards You Have Killed: " + count);
    }
}