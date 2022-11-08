package com.wizardassassin.controller;


import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MoveVerbs {
    MOVE("move"),
    GO("go");

    private final String moveVerb;

    public static Set<String> set() {
        return verbSet;
    }

    ;

    private static final Set<String> verbSet = Stream.of(values())
            .map(MoveVerbs::toString)
            .collect(Collectors.toSet());

    MoveVerbs(String moveVerb) {
        this.moveVerb = moveVerb;
    }

    public String moveVerb() {
        return moveVerb;
    }

    public String toString() {
        return moveVerb();
    }

}