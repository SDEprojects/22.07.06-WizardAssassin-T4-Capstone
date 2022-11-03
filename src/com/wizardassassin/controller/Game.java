package com.wizardassassin.controller;


import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.apps.util.Console;
import com.google.gson.Gson;
import com.wizardassassin.domain.*;

public class Game implements Verbs {
    private final Items items = new Items();
    Characters characters = new Characters();
    private final Location location = new Location();
    private final Location obj = makeObj();
    private final Location inventory = obj.getLocations().get(13);
    private final List<String> inventoryItems = new ArrayList<String>(Arrays.asList(inventory.getItem()));
    private final Scanner inputScanner = new Scanner(System.in);
    private int count = 0;
    private Location currentLocation = obj.getLocations().get(14);
    private String oldLocation = "";
    public List<String> npcNames = new ArrayList<>();

    public Game() throws IOException {
    }

    public Location makeObj() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("./resources/Location.json"));
        Location obj = gson.fromJson(reader, Location.class);
        return obj;
    }

    public void playGame() throws IOException {
        Characters object = characters.getCharacterData();
        Map<String, String> characterQuotes = characters.getQuotes(object);
        while (true) {
            gameState(object, characterQuotes);
            playerActions(object, characterQuotes);
        }
    }

    private void gameState(Characters object, Map<String, String> characterQuotes) throws IOException {
        if (currentLocation.getName().equals("Laboratory") && (inventoryItems.contains("poison"))) {
            System.out.println("\033[36mYou have poisoned the wizard. You return home as a hero who saved your kingdom\033[0m.");
            resetGame();
        }
        if(!oldLocation.equals(currentLocation.getName())) {
            getStatus(object, characterQuotes);
            System.out.println();
            if(currentLocation.getName().equals("Wizard's Foyer") && !inventoryItems.contains("wizard robes")){
                System.out.println("\033[91mThe monster bites your head off and you die!\033[0m");
                System.out.println("\033[91mG\033[0m\033[30mA\033[0m\033[91mM\033[0m\033[30mE\033[0m \033[91mO\033[0m\033[30mV\033[0m\033[91mE\033[0m\033[30mR\033[0m!");
                resetGame();
            }
            if(!currentLocation.getDirections().isEmpty()) {
                location.printDirections(currentLocation);
            }
            oldLocation = currentLocation.getName();
        }
        System.out.println("");
        System.out.println("\033[36m What would you like to do now?\033[0m\n\033[90mEnter 'quit' to exit game.\nEnter 'view' to see the map.\nEnter 'help' for list of valid commands.\nEnter 'inventory' to list all your items.\033[0m");

    }
    // Parses User input for appropriate action paths
    private void playerActions(Characters object,Map<String, String> characterQuotes) throws IOException {
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
            if (Verbs.getMoveActions().contains(inputVerb)) {
                handleMovement(inputNoun);
            }
            // Path for item actions
            else if (Verbs.getItemActions().contains(inputVerb)) {
                handleItems(inputVerb, inputNoun);
            }
            // Path for character actions
            else if (Verbs.getCharacterActions().contains(inputVerb)) {
                handleCharacters(inputVerb, inputNoun, userInput, object, characterQuotes);
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
            location.printDirections(currentLocation);
        } else {
            System.out.println("\nCan not \033[92m" + inputVerb.toUpperCase() + "\033[0m \u001B[31m" + inputNoun.toUpperCase() + "\u001B[0m. Choose again...");
        }
    }

    private void handleCharacters(String inputVerb, String inputNoun, String userInput, Characters object, Map<String, String> characterQuotes) throws IOException {
        if(npcNames.contains(inputNoun)) {
            if(inputVerb.equals("talk")) {
                handleTalk(inputNoun, userInput, characterQuotes);
            }
            else if(inputVerb.equals("fight")) {
                handleFight(object, inputNoun);
            }
        } else {
            System.out.printf("There is no \u001B[93m%s\u001B[0m here... You must be seeing ghosts.%n", inputNoun.toUpperCase());
        }
    }

    private void handleFight(Characters object, String inputNoun) throws IOException {
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

    private void handleTalk(String inputNoun, String userInput, Map<String, String> characterQuotes) throws IOException {
        if(!inputNoun.equals("queen")) {
            System.out.printf("\u001B[93m%s\u001B[0m: '%s'%n", inputNoun.toUpperCase(), characterQuotes.get(inputNoun));
        } else {
            System.out.printf("\u001B[93m%s\u001B[0m: '\033[95m%s\033[0m'%n", inputNoun.toUpperCase(), characterQuotes.get(inputNoun));
            while(true) {
                System.out.println();
                System.out.println("Input 'yes' to continue, 'no' to quit");
                userInput = inputScanner.nextLine().trim().toLowerCase();
                if(userInput.equals("yes")) {
                    currentLocation = obj.getPickedLocation("Church");
                    break;
                }
                else if(userInput.equals("no")) {
                    quitGame();
                }
            }
        }
    }

    private void getStatus(Characters object, Map<String, String> characterQuotes) {
        System.out.println("\n\u001B[35m                                              *********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
        System.out.println(currentLocation.getDescription() + "\n");
        System.out.println("You see the following characters: ");
        npcNames.clear();
        for (Characters extraCharacters : object.getCharacters()) {
            if ((currentLocation.getName().equals(extraCharacters.getRoom()))) {
                System.out.printf("             \u001B[93m%s\u001B[0m%n", extraCharacters.getName().toUpperCase());
                npcNames.add(extraCharacters.getName().toLowerCase());
                characterQuotes.put(extraCharacters.getName(), extraCharacters.getQuote());
            }
        }
        if(currentLocation.getItem().length > 0) {
            System.out.printf("You see these items: \u001B[32m %s \u001B[0m%n", Arrays.deepToString(currentLocation.getItem()));
        }
    }

    private void handleActions (String userInput) throws IOException {
        if(userInput.equals("quit")) {
            quitGame();
        }
        else if(userInput.equals("inventory")) {
            items.checkInventory(inventoryItems);
        }
        else if(userInput.equals("help")) {
            System.out.println("All commands must be in this format 'VERB<space>NOUN'\nOr 'quit' to exit game");
            HelpMenu.printMenuHeader();
            HelpMenu.buildMenu().forEach(HelpMenu::printMenu);
        }
        else if(userInput.equals("view")) {
            KingdomMap.printMapHeader();
            KingdomMap.showKingdomMap().forEach(KingdomMap::printMap);
            System.out.println("\n\u001B[91m                         *********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
        }
    }

    private void resetGame() throws IOException {
        Console.pause(2000);
        Console.clear();
        Home app = new Home();
        app.execute();
    }

    private void quitGame() throws IOException {
        System.out.println("Are you sure you want to 'quit'? yes| no");
        String quit = inputScanner.nextLine().trim().toLowerCase();
        if (quit.equals("yes")) {
            System.out.println("Thank you for playing");
            System.exit(0);
        }
        else {
            System.out.println("\n\u001B[91m                         *********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
            playGame();
        }
    }

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