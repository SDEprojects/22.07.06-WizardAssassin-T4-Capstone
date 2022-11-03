package com.wizardassassin.domain;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Characters {
    List<Characters> characters;

    public Characters()  {

    }

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

    // Retrieves character data from json
    public Characters getCharacterData() throws IOException {
        Gson gson = new Gson();
        Reader read = Files.newBufferedReader(Paths.get("./resources/characters.json"));
        Characters object = gson.fromJson(read, Characters.class);
        return object;
    }

    // extracts character quotes from json data
    public Map<String, String> getQuotes(Characters object) {
        Map<String, String> characterQuotes = new HashMap<>();
        for (Characters extraCharacters : object.getCharacters()) {
            characterQuotes.put(extraCharacters.getName().toLowerCase(), extraCharacters.getQuote());
        }
        return characterQuotes;
    }
}