package com.notespace.Minimalist.Logs;

public enum REASON {
    CREATED("You created"), DELETED("Deleted"),BACKUP("Backup");

    String string;

    REASON(String s) {
        this.string = s;
    }

    public String getReason() {
        return string;
    }
}
