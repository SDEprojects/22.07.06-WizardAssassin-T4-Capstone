package com.wizardassassin.domain;

import java.util.*;

public class Location {

    List<Location> locations;

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Location getPickedLocation(String userInput){
        for (Location location : getLocations())
            if (location.getName().equals(userInput)) {
                return location;
            }
        return null; // ThisLocationNotFoundException()
    }

    public String name;
    public String description;
    Map<String, String> directions;
    public String [] items;

    public Location() {

    }

    public Location(String name, String description, Map<String, String> directions, String[] items) {
        this.name = name;
        this.description = description;
        this.directions = directions;
        this.items = items;
    }
    public void printDirections(Location currentLocation) {
        if(!currentLocation.getDirections().isEmpty()) {
            System.out.println("\n\nFrom the " + currentLocation.getName() + " you can go to the:");
            for (Map.Entry<String, String> direction : currentLocation.getDirections().entrySet()) {
                System.out.printf("       \u001B[31m %s: %s \u001B[0m%n", direction.getKey(), direction.getValue());
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getDirections() {
        return directions;
    }

    public void setDirections(Map<String, String> directions) {
        this.directions = directions;
    }

    public String[] getItem() {
        return items;
    }

    public void setItem(String[] item) {
        this.items = item;
    }
}
