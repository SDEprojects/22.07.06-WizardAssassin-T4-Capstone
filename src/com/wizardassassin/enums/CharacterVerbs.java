<<<<<<<< HEAD:src/com/wizardassassin/domain/CharacterVerbs.java
package com.wizardassassin.domain;
========
package com.company.enums;
>>>>>>>> dev:src/com/company/enums/CharacterVerbs.java

public enum CharacterVerbs {
    TALK("talk"),
    FIGHT("fight");

    private String characterVerb;

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