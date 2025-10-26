package by.bogdanov.trip_manager.client;

import by.bogdanov.trip_manager.dto.WeatherDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;

@Component
public class WeatherApiClient {

    private String weatherOpenApiUrl = "http://api.weatherapi.com/v1/forecast.json";
    private final RestTemplate restTemplate;

    public WeatherApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //
    public WeatherDTO getWeatherData(String location, LocalDate startDate) { // 3-й параметр LocalDate endDate
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // если будет API запрос с погодой на несколько дней, то в мапу добавить endDate
            Map<String, String> param = Map.of("q", location, "dt", startDate.toString(), "key", "e08d93f3114a4a52b59160513252310");
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(param);
            ResponseEntity<WeatherDTO> response = restTemplate.exchange(
                    weatherOpenApiUrl,
                    HttpMethod.GET,
                    requestEntity,
                    WeatherDTO.class
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
