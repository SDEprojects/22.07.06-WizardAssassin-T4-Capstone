package com.wizardassassin.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Items {

    public void getItem(String itemInput, Location currentLocation, String verb, List<String> inventoryItems, Location inventory) {

        List<String> roomItems = new ArrayList<>(Arrays.asList(currentLocation.getItem()));

        if (verb.equals("get") && !inventoryItems.contains(itemInput)) {
            roomItems.remove(itemInput);
            inventoryItems.add(itemInput);
            System.out.println("\n");
            System.out.printf("You picked up a %s and added it to your inventory.\n", itemInput);
        } else if (verb.equals("get") && inventoryItems.contains(itemInput)) {
            System.out.println("\nCan not " + verb.toUpperCase() + " " + itemInput.toUpperCase() + ". It's already in your inventory. Choose again...");
        } else if (verb.equals("drop") && inventoryItems.contains(itemInput)) {
            inventoryItems.remove(itemInput);
            roomItems.add(itemInput);
            System.out.println("\n");
            System.out.printf("You dropped the %s and removed it from your inventory.\n", itemInput);
        } else if (verb.equals("drop") && !inventoryItems.contains(itemInput)) {
            System.out.println("\nCan not " + verb.toUpperCase() + " m" + itemInput.toUpperCase() + ". It is not in your inventory. Choose again...");
        }

        // Pick up item step 2, Put item in inventory
        String[] toInventory = new String[inventoryItems.size()];
        toInventory = inventoryItems.toArray(toInventory);
        inventory.setItem(toInventory);

        // END of INVENTORY

        // NOTE convert roomItems List to array. Update masterObj with changes
        String[] updatedRoomItems = roomItems.toArray(new String[0]);
        currentLocation.setItem(updatedRoomItems);
    }
}