package by.bogdanov.trip_manager.dto;

public class Location {
    private String name;
    private String region;
    private String country;

    // конструктор
    public Location(String name, String region, String country) {
        this.name = name;
        this.region = region;
        this.country = country;
    }

    // геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
