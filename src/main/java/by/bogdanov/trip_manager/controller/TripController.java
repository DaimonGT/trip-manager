package by.bogdanov.trip_manager.controller;

import by.bogdanov.trip_manager.entity.Trip;
import by.bogdanov.trip_manager.service.TripService;
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
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

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
        log.info("Запрос на создание новой поездки: {}", trip.getNameTrip());
        try {
            Trip createdTrip = tripService.createTrip(trip);
            log.info("Поездка успешно создана с ID: {}", createdTrip.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrip);
        } catch (Exception e) {
            log.info("Ошибка при создании поездки: {}", e.getMessage());
            throw new RuntimeException(e);
        }
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
        log.info("Запрос на получение поездки по ID: {}", id);
        Optional<Trip> tripById = tripService.getTripById(id);
        return tripById.map(trip -> {
                    log.info("Поездка найдена: {}", tripById.get());
                    return ResponseEntity.ok(tripById.get());
                }).
                orElseGet(() -> {
                    log.info("Поездка с ID {} не найдена", id);
                    return ResponseEntity.notFound().build();
                });
        //return tripById.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
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
        log.info("Запрос на обновление поездки с ID {}", id);
        try {
            Trip updateTrip = tripService.updateTrip(id, trip);
            log.info("Поездка с ID {} успешно обновлена", id);
            return ResponseEntity.ok(updateTrip);
        } catch (Exception e) {
            log.info("Ошибка при обновлении поездки с ID: {}", id);
            throw new RuntimeException(e);
        }
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
    ) {
        log.info("Запрос на удаление поездки с ID {}", id);
        try {
            tripService.deleteTripById(id);
            log.info("Поездка с ID {} успешна удалена", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.info("Ошибка при удалении поездки с ID: {}", id);
            throw new RuntimeException(e);
        }
    }

    // Получение поездок по месту назначения
    @GetMapping("/destination/{destination}")
    @Operation(summary = "Получение поездок по destination", description = "Получение поездок по destination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trip find successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Trip>> getAllTripsByDestination(
            @Parameter(description = "Trip destination", required = true)
            @PathVariable String destination
    ) {
        log.info("Запрос на получение поездок по городу {}", destination);
        try {
            List<Trip> TripsByDestination = tripService.findByDestination(destination);
            log.info("Найдено {} поездок в город {}",TripsByDestination.size(), destination);
            return ResponseEntity.ok(TripsByDestination);
        } catch (Exception e) {
            log.info("Поездки в город {} не найдены: {}", destination, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // получение поездок по дате назначения
    @GetMapping("/startDate/{startDate}")
    @Operation(summary = "Получение поездок по startDate", description = "Получение поездок по startDate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trip find successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Trip>> getAllTripsByStartDate(
            @Parameter(description = "Trip startDate", required = true)
            @PathVariable LocalDate startDate
    ) {
        log.info("Запрос на получение поездок по дате {}", startDate);
        try {
            List<Trip> tripsByDestination = tripService.findTripByStartDate(startDate);
            log.info("Найдено {} поездок на дату {}", tripsByDestination.size(), startDate);
            return ResponseEntity.ok(tripsByDestination);
        } catch (Exception e) {
            log.info("Поездки на дату {} не найдены: {}", startDate, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Получение поездок по названию
    @GetMapping(value = "nameTrip/{nameTrip}")
    @Operation(summary = "Получение поездок по nameTrip", description = "Получение поездок по nameTrip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trip find successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Trip>> getAllTripsByNameTrip(
            @Parameter(description = "Trips by nameTrip", required = true)
            @PathVariable String nameTrip
    ) {
        log.info("Запрос на получение поездок с названием {}", nameTrip);
        try {
            List<Trip> tripsByNameTrip = tripService.findByTripByNameTrip(nameTrip);
            log.info("Найдено {} поездок с названием {}", tripsByNameTrip.size(), nameTrip);
            return ResponseEntity.ok(tripsByNameTrip);
        } catch (Exception e) {
            log.info("Поездки с названием {} не найдены", nameTrip);
            throw new RuntimeException(e);
        }
    }
}
