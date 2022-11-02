<<<<<<<< HEAD:src/com/wizardassassin/domain/ItemVerbs.java
package com.wizardassassin.domain;
========
package com.company.enums;
>>>>>>>> dev:src/com/company/enums/ItemVerbs.java

public enum ItemVerbs {
    GET("get"),
    USE("use"),
    DROP("drop");

    private String itemVerb;

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