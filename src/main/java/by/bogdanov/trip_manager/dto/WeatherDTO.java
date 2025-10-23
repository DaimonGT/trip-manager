package by.bogdanov.trip_manager.dto;

public class WeatherDTO {
    public Location location;
    public Forecast forecast;

    public WeatherDTO(Location location, Forecast forecast) {
        this.location = location;
        this.forecast = forecast;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }
}
