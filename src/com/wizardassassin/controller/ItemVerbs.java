package com.wizardassassin.controller;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ItemVerbs {
    GET("get"),
    USE("use"),
    DROP("drop");

    private String itemVerb;

    public static Set<String> set() {
        return verbSet;
    }

    ;

    private static final Set<String> verbSet = Stream.of(values())
            .map(ItemVerbs::toString)
            .collect(Collectors.toSet());

    ItemVerbs(String itemVerb) {
        this.itemVerb = itemVerb;
    }

    public String itemVerb() {
        return itemVerb;
    }

    public String toString() {
        return itemVerb();
    }


}