package com.wizardassassin.gui.model;

import com.wizardassassin.controller.Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;

import java.net.URISyntaxException;

public class GameFrame {
    private JPanel panel;
    private JTextArea textArea;
    private JList listNPC;
    private JList listItem;
    private JList listDirection;
    private JList listInventory;
    private final DefaultListModel namesListNPC = new DefaultListModel();
    private final DefaultListModel itemsList = new DefaultListModel();
    private final DefaultListModel directionsList = new DefaultListModel();
    private final DefaultListModel inventoryList = new DefaultListModel();
    private JLabel labelNPC = new JLabel("NPC:");
    private JLabel labelItem = new JLabel("Location Items:");
    private JLabel labelDirection = new JLabel("Directions:");
    private JLabel labelInventory = new JLabel("Inventory:");

    public GameFrame(JFrame frame) throws IOException, URISyntaxException {
        initialize(frame);
    }

    private void initialize(JFrame frame) throws IOException, URISyntaxException {

        panel = new JPanel();
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


        // NPC Box
        listNPC = new JList();
        listNPC.setModel(namesListNPC);

        // Location Item Box
//        labelItem = new JLabel("Location Items:");
        listItem = new JList();
        listItem.setModel(itemsList);

        // Direction Box
//        labelDirection = new JLabel("Directions:");
        listDirection = new JList();
        listDirection.setModel(directionsList);

        // Inventory Box
//        labelInventory = new JLabel("Inventory:");
        listInventory = new JList();
        listInventory.setModel(inventoryList);

        // Talk Button
        JButton talkButton = new JButton("TALK");

        // GET Button
        JButton getButton = new JButton("GET");

        // Fight Button
        JButton fightButton = new JButton("FIGHT");

        // Go Button
        JButton goButton = new JButton("GO");

        // Use Button
        JButton useButton = new JButton("USE");


        // Dialogue Box
        textArea = new JTextArea(10,50);
        textArea.setEditable(false);
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);

        panel.add(title);
        panel.add(talkButton);
        panel.add(getButton);
        panel.add(fightButton);
        panel.add(goButton);
        panel.add(useButton);
        panel.add(textArea);

        printLog();
        frame.add(panel);
    }

    public void printLog() throws IOException, URISyntaxException {
        Thread thread = new Thread(() -> {
            while (true) {
                Game game = null;
                try {
                    game = new Game();
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
                try {
                    assert game != null;
                    game.playGame(panel, textArea,
                            listNPC, namesListNPC, labelNPC,
                            listItem, itemsList, labelItem,
                            listDirection, directionsList, labelDirection,
                            listInventory, inventoryList, labelInventory);
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();

    }
}
