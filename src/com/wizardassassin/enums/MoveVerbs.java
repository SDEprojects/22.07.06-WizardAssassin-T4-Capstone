<<<<<<<< HEAD:src/com/wizardassassin/domain/MoveVerbs.java
package com.wizardassassin.domain;
========
package com.company.enums;
>>>>>>>> dev:src/com/company/enums/MoveVerbs.java

public enum MoveVerbs {
    MOVE("move"),
    GO("go");

    private String moveVerb;

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