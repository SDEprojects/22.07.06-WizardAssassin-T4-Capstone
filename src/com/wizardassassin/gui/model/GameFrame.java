package com.wizardassassin.gui.model;

import com.wizardassassin.controller.Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;

import java.net.URISyntaxException;

public class GameFrame {

    private JTextArea textArea;
    private PrintStream standardOut;

    public GameFrame(JFrame frame) throws IOException, URISyntaxException {
        initialize(frame);
    }

    private void initialize(JFrame frame) throws IOException, URISyntaxException {
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


        // Dialogue
        textArea = new JTextArea(10,50);
        textArea.setEditable(false);
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));

        standardOut = System.out;
        System.setOut(printStream);
        System.setErr(printStream);



        panel.add(title);
        panel.add(textArea);
        printLog();
        frame.add(panel);


    }

    public void printLog() throws IOException, URISyntaxException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Game game = null;
                    try {
                        game = new Game();
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                    try {
                        assert game != null;
                        game.playGame();
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        thread.start();

    }
}
