package com.wizardassassin.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Characters {
    List<Characters> characters;

    public Characters() {

    }

    public List<Characters> getCharacters() {
        return characters;
    }

    String name;
    String room;
    String quote;

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
    public Characters getCharacterData() throws IOException, URISyntaxException {
        Gson gson = new Gson();
        ClassLoader loader = getClass().getClassLoader();
        URI uri = loader.getResource("characters.json").toURI();
        String read = Files.readString(Path.of(uri));
        return gson.fromJson(read, Characters.class);
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