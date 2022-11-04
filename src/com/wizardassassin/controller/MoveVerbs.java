package com.wizardassassin.controller;


import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MoveVerbs {
    MOVE("move"),
    GO("go");

    private static final Set<String> verbSet = Stream.of(values())
            .map(MoveVerbs::toString)
            .collect(Collectors.toSet());

    private final String moveVerb;

    MoveVerbs(String moveVerb) {
        this.moveVerb = moveVerb;
    }

    public String moveVerb() {
        return moveVerb;
    }

    public String toString() {
        return moveVerb();
    }

    public static Set<String> set() {
        return verbSet;
    };
}