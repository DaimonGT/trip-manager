package by.bogdanov.trip_manager.repository;

import by.bogdanov.trip_manager.entity.Trip;
import by.bogdanov.trip_manager.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    // есть встроенный метод save
}
