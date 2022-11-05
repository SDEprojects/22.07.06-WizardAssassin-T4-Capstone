package com.wizardassassin.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Items {

        public void getItem(String itemInput, Location currentLocation, String verb, List<String> inventoryItems, Location inventory) throws IOException {

            List<String> roomItems = new ArrayList<String>(Arrays.asList(currentLocation.getItem()));

            if(verb.equals("get") && !inventoryItems.contains(itemInput)) {
                roomItems.remove(itemInput);
                inventoryItems.add(itemInput);
                System.out.println("\n");
                System.out.printf("You picked up a \033[32m%s\033[0m and added it to your inventory.\n", itemInput);
            }
            else if(verb.equals("get") && inventoryItems.contains(itemInput)) {
                System.out.println("\nCan not \033[92m" + verb.toUpperCase() + "\033[0m \u001B[31m" + itemInput.toUpperCase() + "\u001B[0m. It's already in your inventory. Choose again...");
            }
            else if(verb.equals("drop") && inventoryItems.contains(itemInput)) {
                inventoryItems.remove(itemInput);
                roomItems.add(itemInput);
                System.out.println("\n");
                System.out.printf("You dropped the \033[32m%s\033[0m and removed it from your inventory.\n", itemInput);
            }
            else if(verb.equals("drop") && !inventoryItems.contains(itemInput)) {
                System.out.println("\nCan not \033[92m" + verb.toUpperCase() + "\033[0m \u001B[31m" + itemInput.toUpperCase() + "\u001B[0m. It is not in your inventory. Choose again...");
            }

            // Pick up item step 2, Put item in inventory
            String[] toInventory = new String[inventoryItems.size()];
            toInventory = inventoryItems.toArray(toInventory);
            inventory.setItem(toInventory);

            // INVENTORY PRINT OUT
            checkInventory(inventoryItems);
//            System.out.printf("You now see these items in the room: \033[32m%s\033[0m", roomItems);
            // END of INVENTORY

            // NOTE convert roomItems List to array. Update masterObj with changes
            String[] updatedRoomItems = roomItems.toArray(new String[0]);
            currentLocation.setItem(updatedRoomItems);
        }

    public void checkInventory(List<String> inventoryItems) {
        System.out.println();
        System.out.println("*** Inventory ***");
        System.out.printf("Your inventory: \033[92m%s\033[0m", inventoryItems);
        System.out.println();
    }
}
