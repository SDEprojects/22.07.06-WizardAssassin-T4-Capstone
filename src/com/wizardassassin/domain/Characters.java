package com.wizardassassin.domain;

import java.util.List;

public class Characters {
    List<Characters> characters;

    public Characters(List<Characters> characters) {
        this.characters = characters;
    }

    public List<Characters> getCharacters() {
        return characters;
    }

    String type;
    String name;
    String room;
    String quote;

    public Characters(String type, String name, String room, String quote) {
        this.type = type;
        this.name = name;
        this.room = room;
        this.quote = quote;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

    public String getQuote() {
        return quote;
    }
}