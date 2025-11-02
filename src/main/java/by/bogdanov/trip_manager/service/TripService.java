package by.bogdanov.trip_manager.service;

import by.bogdanov.trip_manager.entity.Trip;
import by.bogdanov.trip_manager.entity.Weather;
import by.bogdanov.trip_manager.repository.TripRepository;
import by.bogdanov.trip_manager.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TripService {
    // подключаем Repository
    private final TripRepository tripRepository;
    private final WeatherRepository weatherRepository;

    // конструкторы
    @Autowired
    public TripService(TripRepository tripRepository, WeatherRepository weatherRepository) {
        this.tripRepository = tripRepository;
        this.weatherRepository = weatherRepository;
    }

    // создаем поездку и добавляем информацию о погоде
    @Transactional
    public Trip createTrip(Trip trip, Weather weather){
        return null;
    }

}
