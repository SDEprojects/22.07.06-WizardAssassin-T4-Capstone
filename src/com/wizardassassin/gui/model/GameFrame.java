package com.wizardassassin.gui.model;

import com.wizardassassin.controller.Game;
import com.wizardassassin.model.KingdomMap;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;

import java.net.URISyntaxException;

public class GameFrame {
    private JPanel panel;
    private final JPanel homePanel;
    private JPanel listPanel = new JPanel();
    private JPanel inventoryPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JTextArea textArea;
    private JList listNPC = new JList();
    private JList listItem = new JList();
    private JList listDirection = new JList();
    private JList listInventory = new JList();
    private final JButton talkButton = new JButton("TALK");
    private final JButton getButton = new JButton("GET");
    private JButton fightButton = new JButton("FIGHT");
    private JButton goButton = new JButton("GO");
    private JButton useButton = new JButton("USE");
    private JButton dropButton = new JButton("DROP");
    private final JLabel labelNPC = new JLabel("NPC:");
    private final JLabel labelItem = new JLabel("Location Items:");
    private final JLabel labelDirection = new JLabel("Directions:");
    private final JLabel labelInventory = new JLabel("Inventory:");
    private final JLabel labelActions = new JLabel("Actions");
    private final DefaultListModel namesListNPC = new DefaultListModel();
    private final DefaultListModel itemsList = new DefaultListModel();
    private final DefaultListModel directionsList = new DefaultListModel();
    private final DefaultListModel inventoryList = new DefaultListModel();

    public GameFrame(JFrame frame, JPanel homePanel) {
        this.homePanel = homePanel;
        initialize(frame);
    }

    private void initialize(JFrame frame) {

        panel = new JPanel();
//        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
//        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.BLACK);


        JLabel title = new JLabel();
        title.setText("Wizard Assassin");
        title.setVerticalAlignment(JLabel.TOP);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(Color.RED);
        title.setFont(new Font("papyrus", Font.BOLD, 50));

        //Menu
        Font f = new Font("sans-serif", Font.PLAIN, 18);
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f);
        UIManager.put("CheckBoxMenuItem.font", f);
        UIManager.put("RadioButtonMenuItem.font", f);

        JMenuBar menuBar = new JMenuBar();
        JMenu Menu = new JMenu("Menu");

        JMenuItem quitMenuItem = new JMenuItem("quit");
        quitMenuItem.addActionListener(e -> {
            panel.setVisible(false);
            homePanel.setVisible(true);
                });

        JMenuItem helpMenuItem = new JMenuItem("help");
        helpMenuItem.addActionListener(e -> helpWindow());

        JMenuItem MapItem = new JMenuItem("map");
        MapItem.addActionListener(e -> MapWindow());

        Menu.add(quitMenuItem);
        Menu.add(helpMenuItem);
        Menu.add(MapItem);

        menuBar.add(Menu);
        frame.setJMenuBar(menuBar);

        // NPC Box
        listNPC.setModel(namesListNPC);

        // Location Item Box
        listItem.setModel(itemsList);

        // Direction Box
        listDirection.setModel(directionsList);

        // Inventory Box
        listInventory.setModel(inventoryList);


        // Dialogue Box
        JPanel textBoxPanel = new JPanel();
        textArea = new JTextArea(10, 50);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.RED);
        textArea.setFont(new Font("papyrus", Font.ITALIC, 17));
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(100, 450));
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);
        textBoxPanel.setBackground(Color.yellow);
        textBoxPanel.add(textArea);

        // Button panel label
        labelActions.setForeground(Color.RED);
        labelActions.setFont(new Font("papyrus", Font.BOLD, 20));
        labelActions.setHorizontalAlignment(JLabel.CENTER);
        labelActions.setVerticalAlignment(JLabel.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        goButton.setBackground(Color.GREEN);
        goButton.setOpaque(true);
        goButton.setBorderPainted(false);
        buttonPanel.add(goButton);
        talkButton.setBackground(Color.BLUE);
        talkButton.setOpaque(true);
        talkButton.setBorderPainted(false);
        talkButton.setForeground(Color.YELLOW);
        buttonPanel.add(talkButton);
        fightButton.setBackground(Color.RED);
        fightButton.setOpaque(true);
        fightButton.setBorderPainted(false);
        buttonPanel.add(fightButton);
        getButton.setBackground(Color.ORANGE);
        getButton.setOpaque(true);
        getButton.setBorderPainted(false);
        buttonPanel.add(getButton);
        useButton.setBackground(Color.magenta);
        useButton.setOpaque(true);
        useButton.setBorderPainted(false);
        useButton.setForeground(Color.WHITE);
        buttonPanel.add(useButton);
        dropButton.setBackground(Color.YELLOW);
        dropButton.setOpaque(true);
        dropButton.setBorderPainted(false);
        buttonPanel.add(dropButton);

        // Inventory Panel
        inventoryPanel.setBackground(Color.BLACK);

        // List Panel
        listPanel.setLayout(new FlowLayout(15,15, FlowLayout.LEADING));
//        listPanel.setBounds(5, 5, 300, 30);

        // Right Panel
       rightPanel.setBackground(Color.BLACK);
       rightPanel.setLayout(new GridLayout(3, 1));

       // Left Panel
        leftPanel.setBackground(Color.BLACK);
        leftPanel.add(textBoxPanel);
        leftPanel.add(listPanel);
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        rightPanel.add(labelActions);
        rightPanel.add(buttonPanel);
        rightPanel.add(inventoryPanel);

        panel.add(title);
//        panel.add(textBoxPanel);
        panel.add(leftPanel);
        panel.add(rightPanel);
        printLog();
        frame.add(panel);
    }

    public void printLog() {
        Thread thread = new Thread(() -> {
            while (true) {
                Game game = null;
                try {
                    game = new Game(panel, homePanel, listPanel, inventoryPanel, textArea, listNPC,
                            namesListNPC, labelNPC, listItem,
                            itemsList, labelItem, listDirection,
                            directionsList, labelDirection, listInventory,
                            inventoryList, labelInventory, talkButton,
                            getButton, fightButton, goButton,
                            useButton, dropButton);
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
        });
        thread.start();
    }

    public void helpWindow() {
        JFrame frame = new JFrame();
        JTextArea text = new JTextArea();
        frame.setTitle("Wizard Assassin: HELP");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(480, 200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        text.setText(" To play the game, select a yellow colored item from any list, followed by clicking a\n button from the Actions panel.\n" +
                "\n Possible Combinations:\n" +
                "   select NPC name     -> click TAlk or FIGHT\n" +
                "   select LocationItem -> click GET\n" +
                "   select Directions   -> click GO\n" +
                "   select Inventory    -> click USE or DROP");
        text.setBackground(Color.BLACK);
        text.setForeground(Color.RED);
        text.setEditable(false);

        frame.add(text);
        frame.setVisible(true);
    }
    public void MapWindow() {
        JFrame frame1 = new JFrame();
        JTextArea text1 = new JTextArea();
        frame1.setTitle("Wizard Assassin: MAP");
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setSize(500, 500);
        frame1.setLocationRelativeTo(null);
        frame1.setResizable(false);

        //text1.setText("h);
        text1.setForeground(Color.RED);

        frame1.add(text1);
        frame1.setVisible(true);
    }

}
