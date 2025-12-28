package by.bogdanov.trip_manager.client;

import by.bogdanov.trip_manager.dto.WeatherDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Component
public class WeatherApiClient {

    private String weatherOpenApiUrl = "http://api.weatherapi.com/v1/forecast.json";
    private final RestTemplate restTemplate;

    public WeatherApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // для получение погоды из API погоды
    public WeatherDTO getWeatherData(String location, LocalDate startDate) { // 3-й параметр LocalDate endDate
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

// Формируем URL с параметрами
            String url = weatherOpenApiUrl + "?q={q}&dt={dt}&key={key}";
            HttpEntity<?> requestEntity = new HttpEntity<>(headers); // тело пустое
            ResponseEntity<WeatherDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    WeatherDTO.class,
                    location,
                    startDate.toString(),
                    "e08d93f3114a4a52b59160513252310" // ключ
            );
            WeatherDTO weatherResponse = response.getBody();
            System.out.println("Успешное получение данных о погоде");
            return weatherResponse;

        } catch (Exception e) {
            System.err.println("Ошибка получения данных о погоде");
            throw new RuntimeException("Ошибка получения данных о погоде");
        }
    }
}
