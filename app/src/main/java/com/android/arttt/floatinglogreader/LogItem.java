package com.android.arttt.floatinglogreader;

public class LogItem {
    public int level;
    public String message;
    public String owner;

    public LogItem(int level, String owner, String message) {
        this.level = level;
        this.message = message;
        this.owner = owner;
    }
}
