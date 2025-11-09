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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
            @ApiResponse(responseCode = "200", description = "Trip created successfully"),
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

    // получение поездки по ID
    @GetMapping("/{id}")
    @Operation(summary = "Получение поездки по ID", description = "Получение поездки по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trip find successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Trip> getTripById(@Parameter(description = "Trip ID", required = true)
                                            @PathVariable Long id) {
        Optional<Trip> tripById = tripService.getTripById(id);
        return tripById.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // обновление поездки по ID
    @PutMapping("/{id}")
    @Operation(summary = "Обновление поездки по ID", description = "Обновление поездки по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trip update successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Trip> updateTripById(
            @Parameter(description = "Trip ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Trip details", required = true)
            @RequestBody Trip trip
    ) {
        Trip updateTrip = tripService.updateTrip(id, trip);
        return ResponseEntity.ok(updateTrip);
    }

    // удаление поездки по ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление поездки по ID", description = "Удаление поездки по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trip update successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteTripById(
            @Parameter(description = "Trip ID", required = true)
            @PathVariable Long id
    ){
        tripService.deleteTripById(id);
        return ResponseEntity.noContent().build();
    }

}
