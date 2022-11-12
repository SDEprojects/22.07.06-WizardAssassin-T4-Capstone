package com.wizardassassin.view.model;

import com.wizardassassin.controller.Game;
import com.wizardassassin.model.Music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;


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
    Music music = new Music();
    URL url = getClass().getResource("wizard.wav");
    public BufferedImage image;
    public boolean collision = false;



    public GameFrame(JFrame frame, JPanel homePanel) {
        this.homePanel = homePanel;
        initialize(frame);
    }

    private void initialize(JFrame frame) {

        panel = new JPanel();
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

        JMenuItem quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener(e -> {
            panel.setVisible(false);
            homePanel.setVisible(true);
                });

        JMenuItem helpMenuItem = new JMenuItem("Help");
        helpMenuItem.addActionListener(e -> helpWindow());

        JMenuItem mapMenuItem = new JMenuItem("Map");
        mapMenuItem.addActionListener(e -> mapWindow());

        JMenuItem musicItem = new JMenuItem("Music");
        musicItem.addActionListener(e -> musicWindow());

        Menu.add(quitMenuItem);
        Menu.add(helpMenuItem);
        Menu.add(musicItem);
        Menu.add(mapMenuItem);


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
        frame.setTitle("Wizard Assassin: Help");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(480, 200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        ImageIcon help = new ImageIcon("resources/help.jpg");
        frame.setIconImage(help.getImage());

        text.setText(" To play the game, select a yellow colored object from the lists\n below the dialogue box (NPC," +
                " " +
                "Location Items, Directions), or from the\n Inventory list below the Actions panel, followed by " +
                "clicking a button\n from the Actions panel. Ex. 'QUEEN' + TALK.\n" +
                "\n Possible Combinations:\n" +
                "   select NPC              -> click TALK or FIGHT\n" +
                "   select Location Items -> click GET\n" +
                "   select Directions      -> click GO\n" +
                "   select Inventory       -> click USE or DROP");
        text.setBackground(Color.WHITE);
        text.setForeground(Color.RED);
        text.setEditable(false);

        frame.add(text);
        frame.setVisible(true);
    }

    public void mapWindow() {
        JFrame mapFrame = new JFrame();
        mapFrame.setTitle("Wizard Assassin: Map");
        mapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mapFrame.setSize(800, 410);

        mapFrame.setLayout(null);
        mapFrame.setResizable(false);
        mapFrame.setLocationRelativeTo(null);
        mapFrame.setContentPane(new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("NewMap.png")))));

        mapFrame.setVisible(true);
    }

    public void musicWindow() {
        JFrame musicFrame = new JFrame();
        musicFrame.setTitle("Wizard Assassin: Music");
        musicFrame.setBackground(Color.white);
        musicFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        musicFrame.setLayout(new GridLayout(1,3));
        ImageIcon musicIcon = new ImageIcon("resources/music.png");
        musicFrame.setIconImage(musicIcon.getImage());

        JButton playMusic = new JButton("Play");
        playMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                music.play();
            }
        });
        playMusic.setBackground(Color.GREEN);
        musicFrame.add(playMusic);

        JButton stopMusic = new JButton("Stop");
        stopMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    music.stop();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        stopMusic.setBackground(Color.RED);
        stopMusic.setForeground(Color.BLACK);
        musicFrame.add(stopMusic);

        JButton volumeUp = new JButton("Volume Up");
        volumeUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                music.volumeUp();
            }
        });
        volumeUp.setBackground(Color.BLUE);
        volumeUp.setForeground(Color.BLACK);
        musicFrame.add(volumeUp);

        JButton volumeDown = new JButton("Volume Down");
        volumeUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                music.volumeDown();
            }
        });
        volumeUp.setBackground(Color.YELLOW);
        volumeUp.setForeground(Color.BLACK);
        musicFrame.add(volumeDown);

        musicFrame.pack();
        musicFrame.setVisible(true);
    }
}
