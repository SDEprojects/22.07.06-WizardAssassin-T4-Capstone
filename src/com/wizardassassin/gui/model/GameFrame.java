package com.wizardassassin.gui.model;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

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

        //Menu
        Font f = new Font("sans-serif", Font.PLAIN, 18);
        UIManager.put("Menu.font",f);
        UIManager.put("MenuItem.font",f);
        UIManager.put("CheckBoxMenuItem.font",f);
        UIManager.put("RadioButtonMenuItem.font",f);

        JMenuBar menuBar = new JMenuBar();
        JMenu Menu = new JMenu("Menu");
        JMenuItem quitMenuItem = new JMenuItem("quit");
        quitMenuItem.addActionListener(e -> frame.dispose());

        JMenuItem helpMenuItem = new JMenuItem("help");
        //quitMenuItem.addActionListener(e -> ("help"));

        Menu.add(quitMenuItem);
        Menu.add(helpMenuItem);

        menuBar.add(Menu);
        frame.setJMenuBar(menuBar);

        panel.add(title);

        frame.add(panel);

    }
}
