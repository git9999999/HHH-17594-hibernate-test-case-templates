package org.hibernate.bugs.entities;

public enum IdPrefix {
    ADDRESS("AD"),
    INBOX_MESSAGE("IBM"),
    USER("U");

    private  String prefix;

    IdPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

}
