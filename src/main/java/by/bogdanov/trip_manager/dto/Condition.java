package by.bogdanov.trip_manager.dto;

public class Condition {
    private String text; // солнечно, облачно и т.д.
    // конструктор

    public Condition(String text) {
        this.text = text;
    }
    // геттер и сеттер

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
