package by.bogdanov.trip_manager.dto;

public class Forecastday {
    private String date;
    private Day day;

    // конструктор
    public Forecastday(String date, Day day) {
        this.date = date;
        this.day = day;
    }

    // геттеры и сеттеры

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
