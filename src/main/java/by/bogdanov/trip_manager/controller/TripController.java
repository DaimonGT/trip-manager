package by.bogdanov.trip_manager.controller;

import by.bogdanov.trip_manager.entity.Trip;
import by.bogdanov.trip_manager.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    // подключаем tripService
    private final TripService tripService;

    // конструктор
    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    // создание поездки с полученными данными о погоде
    @PostMapping
    @Operation(summary = "Создаём новую поездку", description = "Создание поездки с данными о погоде")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trip created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Trip> createTrip(
            @Parameter(description = "Trip details", required = true)
            @RequestBody Trip trip
    ) {
        Trip createdTrip = tripService.createTrip(trip);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrip);
    }
}
