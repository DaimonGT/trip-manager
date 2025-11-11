package by.bogdanov.trip_manager.repository;

import by.bogdanov.trip_manager.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    // Получить погоду по id (новое)
    @Query("SELECT w FROM Weather w WHERE w.id = :id")
    Optional<Weather> findWeatherById(@Param("id") Long id);
}
