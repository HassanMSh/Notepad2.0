package com.example.notepad20;

public class Notes {
    int id;
    private String title;
    private String text;

    public Notes(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        String x = text.substring(0, Math.min(text.length(), 42));
        return title + "\n" + x;
    }
}
