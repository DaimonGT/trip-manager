package by.bogdanov.trip_manager.controller;

import by.bogdanov.trip_manager.entity.Weather;
import by.bogdanov.trip_manager.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    // подключаем сервис погоды
    private final WeatherService weatherService;

    // конструктор
    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // Положить полученную погоду в методе getWeatherData в базу

    @GetMapping(value = "/getWeatherForTrip")
    public ResponseEntity<Weather> getWeather(
            @RequestParam String location,
            @RequestParam LocalDate day
    ) {
        Weather weather = weatherService.getWeatherData(location, day);
        return ResponseEntity.status(HttpStatus.CREATED).body(weather);
    }
}
