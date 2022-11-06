package com.wizardassassin.controller;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import com.apps.util.Console;
import com.google.gson.Gson;
import com.wizardassassin.model.*;

import javax.swing.*;

public class Game {
    private final Items items = new Items();
    private final Characters characters = new Characters();
    private final Location location = new Location();
    private final Location obj = makeObj();
    private final Location inventory = obj.getLocations().get(13);
    private final List<String> inventoryItems = new ArrayList<String>(Arrays.asList(inventory.getItem()));
    private final Scanner inputScanner = new Scanner(System.in);
    private int count = 0;
    private Location currentLocation = obj.getLocations().get(14);
    private String oldLocation = "";
    public List<String> npcNames = new ArrayList<>();

    private JPanel panel = null;
    private JTextArea textArea = null;
    private JList<Object> listNPC = null;
    private DefaultListModel namesListNPC = null;
    private JList listItem;
    private JList listDirection;
    private JList listInventory;
    private DefaultListModel itemsList;
    private DefaultListModel directionsList;
    private DefaultListModel inventoryList;
    private JLabel labelNPC;
    private JLabel labelItem;
    private JLabel labelDirection;
    private JLabel labelInventory;

    public Game() throws IOException, URISyntaxException {
    }

    public Location makeObj() throws IOException, URISyntaxException {
        Gson gson = new Gson();
        ClassLoader loader = getClass().getClassLoader();
        URI uri = loader.getResource("Location.json").toURI();
        String reader = Files.readString(Path.of(uri));
        return gson.fromJson(reader, Location.class);
    }

    public void playGame(JPanel panel, JTextArea textArea,
                         JList listNPC, DefaultListModel namesListNPC,
                         JLabel labelNPC, JList listItem, DefaultListModel itemsList,
                         JLabel labelItem, JList listDirection, DefaultListModel directionsList,
                         JLabel labelDirection, JList listInventory, DefaultListModel inventoryList,
                         JLabel labelInventory) throws IOException, URISyntaxException {
        this.panel = panel;
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



        Characters object = characters.getCharacterData();
        Map<String, String> characterQuotes = characters.getQuotes(object);
        while (true) {
            gameState(object, characterQuotes);
            playerActions(object, characterQuotes);
        }
    }

    private void gameState(Characters object, Map<String, String> characterQuotes) throws IOException, URISyntaxException {
        if (currentLocation.getName().equals("Laboratory") && (inventoryItems.contains("poison"))) {
            System.out.println("\033[36mYou have poisoned the wizard. You return home as a hero who saved your kingdom\033[0m.");
            resetGame();
        }
        if(!oldLocation.equals(currentLocation.getName())) {
            System.out.println();
            if(currentLocation.getName().equals("Wizard's Foyer") && !inventoryItems.contains("wizard robes")){
                System.out.println("\033[91mThe monster bites your head off and you die!\033[0m");
                System.out.println("\033[91mG\033[0m\033[30mA\033[0m\033[91mM\033[0m\033[30mE\033[0m \033[91mO\033[0m\033[30mV\033[0m\033[91mE\033[0m\033[30mR\033[0m!");
                resetGame();
            }
            oldLocation = currentLocation.getName();
        }

        // NPC state
        namesListNPC.clear();
        for (Characters extraCharacters : object.getCharacters()) {
            if ((currentLocation.getName().equals(extraCharacters.getRoom()))) {
                namesListNPC.addElement(extraCharacters.getName().toUpperCase());
                characterQuotes.put(extraCharacters.getName(), extraCharacters.getQuote());
            }
        }
        panel.add(labelNPC);
        panel.add(listNPC);

        // Location Item State
        itemsList.clear();
        for (String item: currentLocation.getItem()) {
            String data = item;
            itemsList.addElement(data);
        }
        panel.add(labelItem);
        panel.add(listItem);

        // Inventory Item State
        inventoryList.clear();
        for (String item: inventoryItems) {
            String data = item;
            inventoryList.addElement(data);
        }
        panel.add(labelInventory);
        panel.add(listInventory);

        // Direction State
        directionsList.clear();
        for (Map.Entry<String, String> direction: currentLocation.getDirections().entrySet()) {
            String key = direction.getKey();
            String value = direction.getValue();
            String listItem = key + " : " + value;
            directionsList.addElement(listItem);
        }
        panel.add(labelDirection);
        panel.add(listDirection);

        getStatus(object, characterQuotes);
    }

    // Parses User input for appropriate action paths
    private void playerActions(Characters object,Map<String, String> characterQuotes) throws IOException, URISyntaxException {
        String userInput = inputScanner.nextLine().trim().toLowerCase();
        String[] parseInput = userInput.split(" ");
        handleActions(userInput);
        if(parseInput.length == 2 || parseInput.length == 3) {
            String inputVerb = parseInput[0];
            String inputNoun = parseInput[1];
            if(parseInput.length == 3) {
                inputNoun = String.format("%s %s", inputNoun, parseInput[2]);
            }
            // Path for movement
            if (MoveVerbs.set().contains(inputVerb)) {
                textArea.setText("");
                Console.clear();
                handleMovement(inputNoun);
            }
            // Path for item actions
            else if (ItemVerbs.set().contains(inputVerb)) {
                textArea.setText("");
                Console.clear();
                handleItems(inputVerb, inputNoun);
            }
            // Path for character actions
            else if (CharacterVerbs.set().contains(inputVerb)) {
                textArea.setText("");
                Console.clear();
                handleCharacters(inputVerb, inputNoun, object, characterQuotes);
            }
        } else {
            System.out.println("I do not understand " + userInput.toUpperCase() + ". Format command as 'VERB<space>NOUN' or 'quit' or 'view' or 'help' or 'inventory'");
        }
    }



    private void handleMovement(String inputNoun) {
        if (getCurrentLocation().getDirections().get(inputNoun) != null) {
            String locationInput = getCurrentLocation().getDirections().get(inputNoun);
            if(locationInput.equals("Courtyard") && getCurrentLocation().getName().equals("Church")) {
                if(npcNames.isEmpty()) {
                    setCurrentLocation(getObj().getPickedLocation(locationInput));
                } else {
                    System.out.printf("The \033[31m%s\033[0m blocks your path. You must fight it.\nUse the stick%n", getNpcNames().get(0).toUpperCase());
                }
            }
            else if(locationInput.equals("Great Hall") && getCurrentLocation().getName().equals("Courtyard") && getCount() == 0) {
                if(getInventoryItems().contains("password")) {
                    System.out.println("\033[31mGuard:\033[0m That's the right \033[92mPASSWORD\033[0m. Go ahead and pass.");
                    System.out.println();
                    while(true) {
                        System.out.println("Hit 'enter' to continue");
                        String progress = getInputScanner().nextLine();
                        if(progress.equals("")) {
                            break;
                        }
                    }
                    setCount(getCount()+1);
                    setCurrentLocation(getObj().getPickedLocation(locationInput));
                } else {
                    System.out.println("\033[31mGuard:\033[0m Wrong \033[92mPASSWORD\033[0m! Get outta here, ya scum!");
                    System.out.println("\n\u001B[91m                         *********  You remain in the " + getCurrentLocation().getName() + ". *********\u001B[0m\n\n");
                }
            }
            else if(locationInput.equals("Royal Lounge") && getCurrentLocation().getName().equals("Great Hall") && getCount() == 1) {
                if(getInventoryItems().contains("tunic") && getInventoryItems().contains("sword")) {
                    System.out.println("\033[31mGuard:\033[0m I don't know you... but you have the Kingdom's \033[92mTUNIC\033[0m... and that \033[92mSWORD\033[0m... You must be new... go ahead and pass.");
                    System.out.println();
                    while(true) {
                        System.out.println("Hit 'enter' to continue");
                        String progress = getInputScanner().nextLine();
                        if(progress.equals("")) {
                            break;
                        }
                    }
                    setCount(getCount()+1);
                    setCurrentLocation(getObj().getPickedLocation(locationInput));
                } else {
                    System.out.println("\033[31mGuard:\033[0m Where do you think you're going? Only knights can pass through here.\nAnd not just any bloak with a Kingdom's \033[92mTUNIC\033[0m.\nYou need a \033[92mSWORD\033[0m too.");
                    System.out.println("\n\u001B[91m                         *********  You remain in the " + getCurrentLocation().getName() + ". *********\u001B[0m\n\n");
                }
            }
            else if(locationInput.equals("Wizard's Foyer") && getCurrentLocation().getName().equals("Great Hall") && getCount() <= 2) {
                if(getInventoryItems().contains("diamond key")) {
                    System.out.println("Maybe I can \033[31mUSE\033[0m that \033[92mDIAMOND KEY\033[0m on this door.");
                } else {
                    System.out.println("Hmm, it's locked. There's an emblem in the shape of a \033[92mDIAMOND\033[0m on the door");
                    System.out.println("\n\u001B[91m                         *********  You remain in the " + getCurrentLocation().getName() + ". *********\u001B[0m\n\n");
                }
            } else {
                setCurrentLocation(getObj().getPickedLocation(locationInput));
            }
        } else {
            System.out.println("\n\u001B[31m" + inputNoun.toUpperCase() + "\u001B[0m is not a valid direction. Choose again...");
        }
    }

    private void handleItems(String inputVerb, String inputNoun) throws IOException {
        if(inputVerb.equals("use") && inputNoun.equals("diamond key") && getCurrentLocation().getName().equals("Great Hall")) {
            System.out.println("That \033[92mDIAMOND KEY\033[0m did the trick. You're in...");
            System.out.println();
            while(true) {
                System.out.println("Hit 'enter' to continue");
                String progress = inputScanner.nextLine();
                if(progress.equals("")) {
                    break;
                }
            }
            count++;
            currentLocation = obj.getPickedLocation("Wizard's Foyer");
        }
        else if (Arrays.asList(currentLocation.getItem()).contains(inputNoun) || inventoryItems.contains(inputNoun)){
            items.getItem(inputNoun, currentLocation, inputVerb, inventoryItems, inventory);
        } else {
            System.out.println("\nCan not \033[92m" + inputVerb.toUpperCase() + "\033[0m \u001B[31m" + inputNoun.toUpperCase() + "\u001B[0m. Choose again...");
        }
    }

    private void handleCharacters(String inputVerb, String inputNoun, Characters object, Map<String, String> characterQuotes) throws IOException, URISyntaxException {
        if(npcNames.contains(inputNoun)) {
            if(inputVerb.equals("talk")) {
                handleTalk(inputNoun, characterQuotes);
            }
            else if(inputVerb.equals("fight")) {
                handleFight(object, inputNoun);
            }
        } else {
            System.out.printf("There is no \u001B[93m%s\u001B[0m here... You must be seeing ghosts.%n", inputNoun.toUpperCase());
        }
    }

    private void handleFight(Characters object, String inputNoun) throws IOException, URISyntaxException {
        if(inputNoun.equals("evil wizard")) {
            if(!inventoryItems.contains("knife")) {
                System.out.println("\033[91mThe Wizard suddenly blasts your head off with a thunder bolt... and you die!\033[0m");
                System.out.println("\033[91mG\033[0m\033[30mA\033[0m\033[91mM\033[0m\033[30mE\033[0m \033[91mO\033[0m\033[30mV\033[0m\033[91mE\033[0m\033[30mR\033[0m!");
                resetGame();
            }
            else if(inventoryItems.contains("knife")){
                System.out.println("\033[36mThe Wizard suddenly attacks you with a thunder bolt but you matrix dodge it.\n You shank him with the\033[0m \033[92mKNIFE\033[0m \033[36mand he dies!\033[0m");
                System.out.println("You have shanked the wizard to death. You return home as a hero who saved your kingdom!");
                resetGame();
            }
        }
        else if(inventoryItems.contains("sword")){
            int characterIndex= npcNames.indexOf(inputNoun);
            object.getCharacters().remove(characterIndex);
            npcNames.remove(inputNoun);
            if(!npcNames.isEmpty() || !inputNoun.equals("rat")) {
                System.out.println("You've been found out!");
                System.out.println("Should've listened to the Queen and not gone on that killing spree... You lose");
                System.out.println("\033[91mG\033[0m\033[30mA\033[0m\033[91mM\033[0m\033[30mE\033[0m \033[91mO\033[0m\033[30mV\033[0m\033[91mE\033[0m\033[30mR\033[0m!");
                resetGame();
            }
        }
        else if(inventoryItems.contains("stick") && inputNoun.equals("rat")) {
            System.out.printf("You beat the \033[91m%s\033[0m to death with the \033[92mSTICK\033[0m", inputNoun.toUpperCase());
            int characterIndex= npcNames.indexOf(inputNoun);
            object.getCharacters().remove(characterIndex);
            npcNames.remove(inputNoun);
        } else {
            System.out.println("I'm going to advise against that.");
        }
    }

    private void handleTalk(String inputNoun, Map<String, String> characterQuotes) throws IOException, URISyntaxException {
        if(!inputNoun.equals("queen")) {
            Console.clear();
            textArea.setText("");
            System.out.printf("\u001B[93m%s\u001B[0m: '%s'%n", inputNoun.toUpperCase(), characterQuotes.get(inputNoun));
        } else {
            Console.clear();
            textArea.setText("");
            System.out.printf("\u001B[93m%s\u001B[0m: '\033[95m%s\033[0m'%n", inputNoun.toUpperCase(), characterQuotes.get(inputNoun));
            while(true) {
                System.out.println("Hit 'enter' to continue");
                String progress = inputScanner.nextLine();
                if(progress.equals("")) {
                    Console.clear();
                    textArea.setText("");
                    currentLocation = obj.getPickedLocation("Church");
                }
                    break;
                }
//            while(true) {
//                System.out.println();
//                System.out.println("Input 'yes' to continue, 'no' to quit");
//                String userInput = inputScanner.nextLine().trim().toLowerCase();
//                if(userInput.equals("yes")) {
//                    Console.clear();
//                    textArea.setText("");
//                    currentLocation = obj.getPickedLocation("Church");
//                    break;
//                }
//                else if(userInput.equals("no")) {
//                    quitGame();
//                }
//            }
        }
    }


    private void getStatus(Characters object, Map<String, String> characterQuotes) {
        System.out.println("\n\u001B[35m*********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
        System.out.println(currentLocation.getDescription() + "\n");
//        System.out.println("You see the following characters: ");
        npcNames.clear();
        for (Characters extraCharacters : object.getCharacters()) {
            if ((currentLocation.getName().equals(extraCharacters.getRoom()))) {
                npcNames.add(extraCharacters.getName().toLowerCase());
                characterQuotes.put(extraCharacters.getName(), extraCharacters.getQuote());
            }
        }


//        if(currentLocation.getItem().length > 0) {
////            System.out.printf("You see these items: \u001B[32m %s \u001B[0m%n",
////                    Arrays.deepToString(currentLocation.getItem()));
//        }
//        if(!currentLocation.getDirections().isEmpty()) {
////            location.printDirections(currentLocation);
//        }
        System.out.println("\033[36m What would you like to do now?" +
                "\nEnter 'map' to see the map." +
                "\nEnter 'help' for list of valid commands."
        );

    }

    private void handleActions (String userInput) throws IOException, URISyntaxException {
        switch (userInput) {
//            case "quit":
//                quitGame();
//                break;
//            case "inventory":
//                items.checkInventory(inventoryItems);
//                break;
            case "help":
                Console.clear();
                textArea.setText("");
                System.out.println("All commands must be in this format 'VERB<space>NOUN'\nOr 'quit' to exit game");
                HelpMenu.printMenuHeader();
                HelpMenu.buildMenu().forEach(HelpMenu::printMenu);
                break;
            case "map":
                Console.clear();
                textArea.setText("");
                KingdomMap.printMapHeader();
                KingdomMap.showKingdomMap().forEach(KingdomMap::printMap);
                System.out.println("\n\u001B[91m*********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
                break;
        }
    }

    private void resetGame() throws IOException, URISyntaxException {
        Console.pause(2000);
        Console.clear();
        Home app = new Home();
        app.execute();
    }

//    private void quitGame() throws IOException, URISyntaxException {
//        System.out.println("Are you sure you want to 'quit'? yes| no");
//        String quit = inputScanner.nextLine().trim().toLowerCase();
//        if (quit.equals("yes")) {
//            System.out.println("Thank you for playing");
//            System.exit(0);
//        }
//        else {
//            System.out.println("\n\u001B[91m*********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
//            playGame(panel, textArea, listNPC, namesListNPC, );
//        }
//    }

    public Location getObj() {
        return obj;
    }

    public List<String> getInventoryItems() {
        return inventoryItems;
    }

    public Scanner getInputScanner() {
        return inputScanner;
    }

    public int getCount() {
        return count;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public List<String> getNpcNames() {
        return npcNames;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
}