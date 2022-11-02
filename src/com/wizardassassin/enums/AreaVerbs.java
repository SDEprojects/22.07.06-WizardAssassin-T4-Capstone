<<<<<<<< HEAD:src/com/wizardassassin/domain/AreaVerbs.java
package com.wizardassassin.domain;
========
package com.company.enums;
>>>>>>>> dev:src/com/company/enums/AreaVerbs.java

public enum AreaVerbs {
    EXAMINE("examine"),
    EXA("exa");

    private String areaVerb;

    AreaVerbs(String areaVerb) {
        this.areaVerb = areaVerb;
    }

    public String areaVerb() {
        return areaVerb;
    }

    public String toString() {
        return areaVerb();
    }
}