package by.bogdanov.trip_manager.dto;

import java.util.List;

public class Forecast {
    private List<Forecastday> forecastday;

    public Forecast(List<Forecastday> forecastday) {
        this.forecastday = forecastday;
    }

    public List<Forecastday> getForecastday() {
        return forecastday;
    }

    public void setForecastday(List<Forecastday> forecastday) {
        this.forecastday = forecastday;
    }
}
