package com.wizardassassin.gui.screen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {

   private int count = 0;
   private JLabel label;
   private JFrame frame;
   private JButton button;
//   private JPanel panel;

    public GUI() {

        frame = new JFrame();

        button = new JButton("Click Me If You Are A Badass Wizard Assassin");
        button.addActionListener(this);
        label = new JLabel("Number of Wizards Killed: 0");

//        panel = new JPanel();
//        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
//        panel.setLayout(new GridLayout(0, 1));
//        panel.add(button);
//        panel.add(label);

//        frame.add(panel, BorderLayout.CENTER);
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