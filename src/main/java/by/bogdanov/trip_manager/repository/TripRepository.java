package by.bogdanov.trip_manager.repository;

import by.bogdanov.trip_manager.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    // получить поездку по названию
    Optional<Trip> findByName(String name);

    // получить все поездки по датам
    List<Trip> findByBetweenDate(LocalDate start, LocalDate end);

    // получить все поездки по месту назначения
    List<Trip> findByDestination (String destination);
}
