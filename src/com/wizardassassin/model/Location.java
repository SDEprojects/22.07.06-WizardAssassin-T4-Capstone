package com.wizardassassin.model;

import java.util.*;

public class Location {

    List<Location> locations;

    public List<Location> getLocations() {
        return locations;
    }

    public Location getPickedLocation(String userInput) {
        for (Location location : getLocations())
            if (location.getName().equals(userInput)) {
                return location;
            }
        return null; // ThisLocationNotFoundException()
    }

    public String name;
    public String description;
    Map<String, String> directions;
    public String[] items;

    public Location() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getDirections() {
        return directions;
    }

    public String[] getItem() {
        return items;
    }

    public void setItem(String[] item) {
        this.items = item;
    }
}
