package com.wizardassassin.controller;


import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CharacterVerbs {
    TALK("talk"),
    FIGHT("fight");

    private String characterVerb;

    public static Set<String> set() {
        return verbSet;
    }

    private static final Set<String> verbSet = Stream.of(values())
            .map(CharacterVerbs::toString)
            .collect(Collectors.toSet());

    CharacterVerbs(String characterVerb) {
        this.characterVerb = characterVerb;
    }

    public String characterVerb() {
        return characterVerb;
    }

    public String toString() {
        return characterVerb();
    }
}