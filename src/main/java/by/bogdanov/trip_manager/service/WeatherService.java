package by.bogdanov.trip_manager.service;

import by.bogdanov.trip_manager.client.WeatherApiClient;
import by.bogdanov.trip_manager.dto.WeatherDTO;
import by.bogdanov.trip_manager.entity.Weather;
import by.bogdanov.trip_manager.repository.WeatherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherService {


    private final WeatherApiClient weatherApiClient;
    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherApiClient weatherApiClient, WeatherRepository weatherRepository) {
        this.weatherApiClient = weatherApiClient;
        this.weatherRepository = weatherRepository;
    }

    // Возвращаем погоду
    public Weather getWeatherData(String location, LocalDate startDate) { // LocalDate endDate - 3-параметр
        WeatherDTO weatherDTO = weatherApiClient.getWeatherData(location, startDate); // 3- й параметр endDate
        Weather weather = new Weather(weatherDTO, startDate);
        try {
            Weather saveWeather = weatherRepository.save(weather);
            return saveWeather;
        } catch (Exception e){
            System.err.println("Ошибка при попытке вернуть запрашиваемую погоду");
            throw new RuntimeException("шибка при попытке вернуть запрашиваемую погоду");
        }
    }
    // Получить погоду по ID
    @Transactional
    public Optional<Weather> getWeatherById(Long id){
        return weatherRepository.findWeatherById(id);
    }
}
