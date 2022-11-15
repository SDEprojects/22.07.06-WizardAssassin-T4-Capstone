package com.wizardassassin.controller;

import java.awt.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

import com.google.gson.Gson;
import com.wizardassassin.model.*;

import javax.swing.*;
import javax.swing.Timer;

public class Game {

    Timer timer;
    private final Items items = new Items();
    private final Characters characters = new Characters();
    private final Location locationObj = makeObj();
    private final Location inventory = locationObj.getLocations().get(13);
    private final List<String> inventoryItems = new ArrayList<>(Arrays.asList(inventory.getItem()));
    private int count = 0;
    private Location currentLocation = locationObj.getLocations().get(14);
    private String oldLocation = "";
    public List<String> npcNames = new ArrayList<>();
    private Characters characterData;
    private Map<String, String> characterQuotes;
    private boolean isGameOver = false;

    private final JPanel panel;
    private final JPanel homePanel;
    private final JPanel listPanel;
    private final JPanel inventoryPanel;
    private final JMenuBar menuBar;
    private final JTextArea textArea;
    private final JList listNPC;
    private final DefaultListModel namesListNPC;
    private final JList listItem;
    private final JList listDirection;
    private final JList listInventory;
    private final DefaultListModel itemsList;
    private final DefaultListModel directionsList;
    private final DefaultListModel inventoryList;
    private final JLabel labelNPC;
    private final JLabel labelItem;
    private final JLabel labelDirection;
    private final JLabel labelInventory;
    private final JButton talkButton;
    private final JButton getButton;
    private final JButton fightButton;
    private final JButton goButton;
    private final JButton useButton;
    private final JButton dropButton;

    public Game(JPanel panel, JPanel homePanel, JPanel listPanel, JPanel inventoryPanel,
                JMenuBar menuBar, JTextArea textArea,
                JList listNPC,
                DefaultListModel namesListNPC, JLabel labelNPC, JList listItem,
                DefaultListModel itemsList, JLabel labelItem, JList listDirection,
                DefaultListModel directionsList, JLabel labelDirection, JList listInventory,
                DefaultListModel inventoryList, JLabel labelInventory, JButton talkButton,
                JButton getButton, JButton fightButton, JButton goButton,
                JButton useButton, JButton dropButton) throws IOException, URISyntaxException {

        this.panel = panel;
        this.homePanel = homePanel;
        this.listPanel = listPanel;
        this.inventoryPanel = inventoryPanel;
        this.menuBar = menuBar;
        this.textArea = textArea;
        this.listNPC = listNPC;
        this.listItem = listItem;
        this.listDirection = listDirection;
        this.listInventory = listInventory;
        this.namesListNPC = namesListNPC;
        this.itemsList = itemsList;
        this.directionsList = directionsList;
        this.inventoryList = inventoryList;
        this.labelNPC = labelNPC;
        this.labelItem = labelItem;
        this.labelDirection = labelDirection;
        this.labelInventory = labelInventory;
        this.talkButton = talkButton;
        this.getButton = getButton;
        this.fightButton = fightButton;
        this.goButton = goButton;
        this.useButton = useButton;
        this.dropButton = dropButton;
    }

    public Location makeObj() throws IOException, URISyntaxException {
        Gson gson = new Gson();
        ClassLoader loader = getClass().getClassLoader();

        //noinspection ConstantConditions
        try (Reader reader = new InputStreamReader(loader.getResourceAsStream("location.json"))) {
            return gson.fromJson(reader, Location.class);
        }
    }

    public void playGame() throws IOException, URISyntaxException {
        talkButton.addActionListener(e -> {
            try {
                handleEvents(talkButton);
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
        getButton.addActionListener(e -> {
            try {
                handleEvents(getButton);
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        fightButton.addActionListener(e -> {
            try {
                handleEvents(fightButton);
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        goButton.addActionListener(e -> {
            try {
                handleEvents(goButton);
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        useButton.addActionListener(e -> {
            try {
                handleEvents(useButton);
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        dropButton.addActionListener(e -> {
            try {
                handleEvents(dropButton);
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        characterData = characters.getCharacterData();
        characterQuotes = characters.getQuotes(characterData);

        gameLoop();
    }

    private void gameState() {
        if (!oldLocation.equals(currentLocation.getName())) {
            handleAutoGameEndings();
            oldLocation = currentLocation.getName();
        }

        listPanel.setBackground(Color.BLACK);

        // NPC state
        namesListNPC.clear();
        for (Characters character : characterData.getCharacters()) {
            if ((currentLocation.getName().equals(character.getRoom()))) {
                namesListNPC.addElement(character.getName().toUpperCase());
                characterQuotes.put(character.getName(), character.getQuote());
            }
        }
        labelNPC.setForeground(Color.red);
        listNPC.setForeground(Color.YELLOW);
        listNPC.setBackground(Color.BLACK);
        listNPC.setSelectionBackground(Color.BLUE);
        listNPC.setSelectionForeground(Color.YELLOW);
        listPanel.add(labelNPC);
        listPanel.add(listNPC);

        // Location Item State
        itemsList.clear();
        for (String item : currentLocation.getItem()) {
            String data = item;
            itemsList.addElement(data);
        }
        labelItem.setForeground(Color.red);
        listItem.setForeground(Color.YELLOW);
        listItem.setBackground(Color.BLACK);
        listItem.setSelectionBackground(Color.BLUE);
        listItem.setSelectionForeground(Color.YELLOW);
        listPanel.add(labelItem);
        listPanel.add(listItem);

        // Inventory Item State
        inventoryList.clear();
        for (String item : inventoryItems) {
            String data = item;
            inventoryList.addElement(data);
        }
        labelInventory.setForeground(Color.red);
        labelInventory.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        labelInventory.setAlignmentY(JLabel.TOP_ALIGNMENT);

        listInventory.setForeground(Color.YELLOW);
        listInventory.setBackground(Color.BLACK);
        listInventory.setSelectionBackground(Color.BLUE);
        listInventory.setSelectionForeground(Color.YELLOW);

        inventoryPanel.add(labelInventory);
        inventoryPanel.add(listInventory);

        // Direction State
        directionsList.clear();
        for (Map.Entry<String, String> direction : currentLocation.getDirections().entrySet()) {
            String key = direction.getKey();
            String value = direction.getValue();
            String listItem = key + " : " + value;
            directionsList.addElement(listItem);
        }
        labelDirection.setForeground(Color.red);
        listDirection.setForeground(Color.YELLOW);
        listDirection.setBackground(Color.BLACK);
        listDirection.setSelectionBackground(Color.BLUE);
        listDirection.setSelectionForeground(Color.YELLOW);
        listPanel.add(labelDirection);
        listPanel.add(listDirection);

        panel.add(listPanel);
        if (!isGameOver) {
            getStatus();
        }
        ;
    }

    // Parses User input for appropriate action paths
    private void playerActions(String userInput) {
        System.out.println(userInput);
        String[] parseInput = userInput.toLowerCase().split(" ");
        System.out.println(parseInput.length);
        if (parseInput.length >= 2) {
            String inputVerb = parseInput[0];
            String inputNoun = parseInput[1];
            if (parseInput.length == 3) {
                inputNoun = String.format("%s %s", inputNoun, parseInput[2]);
            }
            // Path for movement
            if (MoveVerbs.set().contains(inputVerb)) {
                textArea.setText("");
                handleMovement(inputNoun.split(":")[0]);
            }
            // Path for item actions
            else if (ItemVerbs.set().contains(inputVerb)) {
                textArea.setText("");
                handleItems(inputVerb, inputNoun);
            }
            // Path for character actions
            else if (CharacterVerbs.set().contains(inputVerb)) {
                textArea.setText("");
                handleCharacters(inputVerb, inputNoun);
            }
        } else {
            System.out.println("I do not understand " + userInput.toUpperCase() + ". Format command as 'VERB<space>NOUN' or 'quit' or 'view' or 'help' or 'inventory'");
        }
    }

    private void handleMovement(String inputNoun) {
        if (getCurrentLocation().getDirections().get(inputNoun) != null) {
            String locationInput = getCurrentLocation().getDirections().get(inputNoun);
            if (locationInput.equals("Courtyard") && getCurrentLocation().getName().equals("Church")) {
                if (npcNames.isEmpty()) {
                    setCurrentLocation(getLocationObject().getPickedLocation(locationInput));
                } else {
                    System.out.printf("\nThe %s blocks your path. You must fight it.\nUse the stick.",
                            getNpcNames().get(0).toUpperCase());
                }
            } else if (locationInput.equals("Great Hall") && getCurrentLocation().getName().equals("Courtyard") && getCount() == 0) {
                if (getInventoryItems().contains("password")) {
                    System.out.println("\nGuard: That's the right PASSWORD. Go ahead and pass.");
                    System.out.println();
                    setCount(getCount() + 1);
                    setCurrentLocation(getLocationObject().getPickedLocation(locationInput));
                } else {
                    System.out.println("\nGuard: Wrong PASSWORD! Get outta here, ya scum!");
                }
            } else if (locationInput.equals("Royal Lounge") && getCurrentLocation().getName().equals("Great Hall") && getCount() == 1) {
                if (getInventoryItems().contains("tunic") && getInventoryItems().contains("sword")) {
                    System.out.println("\nGuard: I don't know you... but you have the Kingdom's TUNIC...\n and that " +
                            "SWORD... You must be new... go ahead and pass.");
                    System.out.println();
                    setCount(getCount() + 1);
                    setCurrentLocation(getLocationObject().getPickedLocation(locationInput));
                } else {
                    System.out.println("\nGuard: Where do you think you're going?\nOnly knights can pass through here" +
                            ".\nAnd not just any bloke with a Kingdom's TUNIC.\nYou need a SWORD too.");
                }
            } else if (locationInput.equals("Wizard's Foyer") && getCurrentLocation().getName().equals("Great Hall") && getCount() <= 2) {
                if (getInventoryItems().contains("diamond key")) {
                    System.out.println("\nMaybe I can USE that DIAMOND KEY on this door.");
                } else {
                    System.out.println("\nHmm, it's locked. There's an emblem in the shape of a DIAMOND on the door");
                }
            } else {
                setCurrentLocation(getLocationObject().getPickedLocation(locationInput));
            }
        } else {
            System.out.println("\n" + "This is not a valid direction. Choose again...");
        }
    }

    private void handleItems(String inputVerb, String inputNoun) {
        if (inputVerb.equals("use") && inputNoun.equals("diamond key") && getCurrentLocation().getName().equals("Great Hall")) {
            System.out.println("\nThat DIAMOND KEY did the trick. You're in...");
            System.out.println();
            count++;
            currentLocation = locationObj.getPickedLocation("Wizard's Foyer");
        } else if (inputVerb.equals("use") && inputNoun.equals("poison") && getCurrentLocation().getName().equals("Laboratory")) {
            textArea.setText("");
            setGameOver(true);
            System.out.println("\nYou have poisoned the wizard.\nYou return home as a hero who saved your kingdom. " +
                    "You win!");
            System.out.println("\n-------------------------------------------------------------------");
            System.out.println("***********************  You Won the Game     *********************");
            System.out.println("-------------------------------------------------------------------");
            resetGame();
        } else if (Arrays.asList(currentLocation.getItem()).contains(inputNoun) || inventoryItems.contains(inputNoun)) {
            items.getItem(inputNoun, currentLocation, inputVerb, inventoryItems, inventory);
        } else {
            System.out.println("\nCan not " + inputVerb.toUpperCase() + " this. Select a valid item and choose" +
                    " again...");
        }
    }

    private void handleCharacters(String inputVerb, String inputNoun) {
        if (npcNames.contains(inputNoun)) {
            if (inputVerb.equals("talk")) {
                handleTalk(inputNoun);
            } else if (inputVerb.equals("fight")) {
                handleFight(inputNoun);
            }
        } else {
            System.out.print("\nSelect a valid NPC...\n");
        }
    }

    private void handleFight(String inputNoun) {
        if (inputNoun.equals("evil wizard")) {
            if (!inventoryItems.contains("knife")) {
                textArea.setText("");
                setGameOver(true);
                System.out.println("\nThe Wizard suddenly blasts your head off with a thunder bolt... and you die!");
                System.out.println("\n-------------------------------------------------------------------");
                System.out.println("***********************  You Lost the Game     *********************");
                System.out.println("-------------------------------------------------------------------");
                resetGame();
            } else if (inventoryItems.contains("knife")) {
                textArea.setText("");
                setGameOver(true);
                System.out.println("\nThe Wizard suddenly attacks you with a thunder bolt but you matrix dodge it.\n " +
                        "You shank him with the KNIFE and he dies!");
                System.out.println("\nYou have shanked the wizard to death.\nYou return home as a hero who saved " +
                        "your kingdom!");
                System.out.println("\n-------------------------------------------------------------------");
                System.out.println("***********************  You Won the Game     *********************");
                System.out.println("-------------------------------------------------------------------");
                resetGame();
            }
        } else if (inventoryItems.contains("sword")) {
            int characterIndex = npcNames.indexOf(inputNoun);
            characterData.getCharacters().remove(characterIndex);
            npcNames.remove(inputNoun);
            if (!npcNames.isEmpty() || !inputNoun.equals("rat")) {
                textArea.setText("");
                setGameOver(true);
                System.out.println("\nYou've been found out!");
                System.out.println("\nShould've listened to the Queen and not gone on that killing spree... You lose!");
                System.out.println("\n-------------------------------------------------------------------");
                System.out.println("***********************  You Lost the Game     *********************");
                System.out.println("-------------------------------------------------------------------");
                resetGame();
            }
        } else if (inventoryItems.contains("stick") && inputNoun.equals("rat")) {
            System.out.printf("\nYou beat the %s to death with the STICK.\n", inputNoun.toUpperCase());
            int characterIndex = npcNames.indexOf(inputNoun);
            characterData.getCharacters().remove(characterIndex);
            npcNames.remove(inputNoun);
        } else {
            System.out.println("\nI'm going to advise against that.");
        }
    }

    private void handleTalk(String inputNoun) {
        if (!inputNoun.equals("queen")) {
            textArea.setText("");
            System.out.printf("\n%s: '%s'%n", inputNoun.toUpperCase(), characterQuotes.get(inputNoun));
        } else {
            textArea.setText("");
            System.out.printf("\n%s: '%s'%n", inputNoun.toUpperCase(), characterQuotes.get(inputNoun));
            currentLocation = locationObj.getPickedLocation("Church");
        }
    }

    private void getStatus() {
        System.out.println("\n*********  You are in the " + currentLocation.getName() + ". *********\n");
        System.out.println(currentLocation.getDescription());
        npcNames.clear();
        for (Characters extraCharacters : characterData.getCharacters()) {
            if ((currentLocation.getName().equals(extraCharacters.getRoom()))) {
                npcNames.add(extraCharacters.getName().toLowerCase());
                characterQuotes.put(extraCharacters.getName(), extraCharacters.getQuote());
            }
        }
        System.out.println("\nWhat would you like to do now?");
    }

    private void handleAutoGameEndings() {
        if (getCurrentLocation().getName().equals("Wizard's Foyer") && !inventoryItems.contains("wizard robes")) {
            textArea.setText("");
            setGameOver(true);
            System.out.println("\nThe monster bites your head off and you die!");
            resetGame();
        }
    }

    private void resetGame() {
        int delay = 5000;
        timer = new Timer(delay, e -> {
            panel.setVisible(false);
            homePanel.setVisible(true);
            menuBar.setVisible(false);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void gameLoop() {
        gameState();
        while (true) {
        }
    }

    private void handleEvents(JButton button) throws IOException, URISyntaxException {
        String userInput = "";
        while (true) {
            if (button.equals(talkButton)) {
                userInput = "talk " + listNPC.getSelectedValue();
            } else if (button.equals(getButton)) {
                userInput = "get " + listItem.getSelectedValue();
            } else if (button.equals(fightButton)) {
                userInput = "fight " + listNPC.getSelectedValue();
            } else if (button.equals(goButton)) {
                userInput = "go " + listDirection.getSelectedValue();
            } else if (button.equals(useButton)) {
                userInput = "use " + listInventory.getSelectedValue();
            } else if (button.equals(dropButton)) {
                userInput = "drop " + listInventory.getSelectedValue();
            }
            playerActions(userInput);
            gameState();
            break;
        }
    }

    private Location getLocationObject() {
        return locationObj;
    }

    private List<String> getInventoryItems() {
        return inventoryItems;
    }

    private int getCount() {
        return count;
    }

    private Location getCurrentLocation() {
        return currentLocation;
    }

    private List<String> getNpcNames() {
        return npcNames;
    }

    private void setCount(int count) {
        this.count = count;
    }

    private void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
