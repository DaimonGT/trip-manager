package by.bogdanov.trip_manager.controller;

import by.bogdanov.trip_manager.entity.Weather;
import by.bogdanov.trip_manager.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    // подключаем сервис погоды
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
        log.info("WeatherController подключен");
    }

    // Положить полученную погоду в методе getWeatherData в базу
    @GetMapping(value = "/getWeatherForTrip")
    public ResponseEntity<Weather> getWeather(
            @RequestParam String location,
            @RequestParam LocalDate day
    ) {
        log.info("Запрос на получение погоды для города: {}, на день {}", location, day);
        Weather weather = weatherService.getWeatherData(location, day);
        return ResponseEntity.status(HttpStatus.CREATED).body(weather);
    }

    // Получение погоды по ID
    @GetMapping("/{id}")
    @Operation(summary = "Получение погоды по ID", description = "Получение погоды по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weather find successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Weather> getWeatherById(@Parameter(description = "Weather ID", required = true)
                                                  @PathVariable Long id) {
        Optional<Weather> tripById = weatherService.getWeatherById(id);
        return tripById.map(trip -> {
                    log.info("Погода найдена с ID: {}", tripById.get().getLocationName());
                    return ResponseEntity.ok(tripById.get());
                }).
                orElseGet(() -> {
                    log.info("Погода с ID {} не найдена", id);
                    return ResponseEntity.notFound().build();
                });
        //return tripById.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Получение погоды по locationName
    @GetMapping("location/{locationName}")
    @Operation(summary = "Получение погоды по locationName", description = "Получение погоды по locationName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weather find successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Weather>> getWeatherByLocationName(@Parameter(description = "Weather location", required = true)
                                                                  @PathVariable String locationName
    ) {
        log.info("Запрос погоды по городу: {}", locationName);
        try {
            List<Weather> weatherByLocation = weatherService.findWeatherByLocation(locationName);
            log.info("Найдено {} записей с погодой по городу {}", weatherByLocation.size(), locationName);
            return ResponseEntity.ok(weatherByLocation);
        } catch (Exception e) {
            log.info("Погода по городу {} не найдена {}", locationName, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Получение погоды по country
    @GetMapping("country/{country}")
    @Operation(summary = "Получение погоды по country", description = "Получение погоды по country")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weather find successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Weather>> getWeatherByCountry(@Parameter(description = "Weather location", required = true)
                                                             @PathVariable String country) {
        log.info("Запрос погоды по стране: {}", country);
        try {
            List<Weather> weatherByCountry = weatherService.findWeatherByCountry(country);
            log.info("Найдено {} записей погоды для страны {}", weatherByCountry.size(), country);
            return ResponseEntity.ok(weatherByCountry);
        } catch (Exception e) {
            log.info("Погода для страны {} не найдена: {}", country, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
