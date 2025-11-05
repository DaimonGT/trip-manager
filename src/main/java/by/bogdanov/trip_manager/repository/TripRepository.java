package by.bogdanov.trip_manager.repository;

import by.bogdanov.trip_manager.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    // получить поездки по названию
    List<Trip> findByNameTrip(String nameTrip);

    // получить все поездки по датам
    List<Trip> findByStartDate(LocalDate startDate);

    // получить все поездки по месту назначения
    List<Trip> findByDestination(String destination);

    // получить поездку по id
    @Query("SELECT t FROM Trip t WHERE t.id = :id")
    Optional<Trip> findTripById(@Param("id") Long id);
}
