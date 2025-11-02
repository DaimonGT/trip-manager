package by.bogdanov.trip_manager.service;

import by.bogdanov.trip_manager.entity.Trip;
import by.bogdanov.trip_manager.entity.Weather;
import by.bogdanov.trip_manager.repository.TripRepository;
import by.bogdanov.trip_manager.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TripService {
    // подключаем Repository и Services
    private final TripRepository tripRepository;
    private final WeatherRepository weatherRepository;
    private final WeatherService weatherService;

    // конструкторы
    @Autowired
    public TripService(TripRepository tripRepository, WeatherRepository weatherRepository, WeatherService weatherService) {
        this.tripRepository = tripRepository;
        this.weatherRepository = weatherRepository;
        this.weatherService = weatherService;
    }

    // создаем поездку и добавляем информацию о погоде
    @Transactional
    public Trip createTrip(Trip trip) {
        Weather weather = weatherService.getWeatherData(trip.getDestination(), trip.getStartDate());
        trip.setWeather(weather);
        return tripRepository.save(trip);
    }

    // обновляем поездку
    @Transactional
    public Trip updateTrip(Long id, Trip updateTrip) {
        // находим поездку по ID
        Trip oldTrip = tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Поездка с ID: " + id + " не найдеа"));
        // обновляем поля в поездке
        oldTrip.setNameTrip(updateTrip.getNameTrip());
        oldTrip.setDestination(updateTrip.getDestination());
        oldTrip.setStartDate(updateTrip.getStartDate());
        // удаляем погоду, если она есть
        if (oldTrip.getWeather() != null) {
            weatherRepository.delete(oldTrip.getWeather());
        }
        // получем новую погоду для обновленной поездки
        Weather newWeather = weatherService.getWeatherData(updateTrip.getDestination(), updateTrip.getStartDate());
        // сохраняем новую погоду в поездку
        oldTrip.setWeather(newWeather);
        return tripRepository.save(oldTrip);
    }

    // удаляем поездку по ID
    @Transactional
    public void deleteTripById(Long id) {
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Поездки с ID: " + id + " не существует"));
        tripRepository.delete(trip);
    }

    // находим поездку по ID
    @Transactional
    public Trip findTripById(Long id) {
        return tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Поездки с таким ID: " + id + " не существует"));
    }

    // находим все поездки по destination
    public List<Trip> findByDestination(String destination) {
        List<Trip> tripByDestination = tripRepository.findByDestination(destination);
        if (!tripByDestination.isEmpty()) {
            return tripByDestination;
        } else {
            throw new RuntimeException("Поездки с таким городом: " + destination + " не найдены");
        }
    }

    // находим все поездки по дате
    @Transactional
    public List<Trip> findTripByStartDate(LocalDate startDate) {
        List<Trip> tripByStartDate = tripRepository.findByStartDate(startDate);
        if (!tripByStartDate.isEmpty()) {
            return tripByStartDate;
        } else {
            throw new RuntimeException("Поездки на дату: " + startDate + " не найдены");
        }
    }

    // находим все поездки по названию
    @Transactional
    public List<Trip> findByTripByNameTrip(String nameTrip) {
        List<Trip> byNameTrip = tripRepository.findByNameTrip(nameTrip);
        if (!byNameTrip.isEmpty()) {
            return byNameTrip;
        } else {
            throw new RuntimeException("Поездок с именем: " + nameTrip + " не существует");
        }
    }
}
