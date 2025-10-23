package by.bogdanov.trip_manager.entity;

import by.bogdanov.trip_manager.dto.Day;
import by.bogdanov.trip_manager.dto.WeatherDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.mapping.Collection;

import java.time.LocalDate;

@Entity
@Table(name = "weathers")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Size(min = 2, max = 50, message = "Название города должно быть от 2 до 50 символов")
    @Column(nullable = false)
    private String locationName;

    @Size(min = 2, max = 100, message = "Название региона должно быть от 2 до 100 символов")
    @Column(nullable = false)
    private String region;

    @Size(min = 2, max = 50, message = "Название страны должно быть от 2 до 50 символов")
    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private double maxtemp_c;

    @Column(nullable = false)
    private double mintemp_c;

    @Column(nullable = false)
    private double avgtemp_c;

    @Size(min = 2, max = 50, message = "Название страны должно быть от 2 до 50 символов")
    @Column(nullable = false)
    private String condition;

    // конструктор
    public Weather(LocalDate startDate, String name, String region, String country, double maxtemp_c, double mintemp_c, double avgtemp_c, String condition) {
        this.startDate = startDate;
        this.locationName = name;
        this.region = region;
        this.country = country;
        this.maxtemp_c = maxtemp_c;
        this.mintemp_c = mintemp_c;
        this.avgtemp_c = avgtemp_c;
        this.condition = condition;
    }

    // конструктор для WeatherService, а именно для метода getWeatherData
    public Weather(WeatherDTO weatherDTO) {
        this.locationName = weatherDTO.getLocation().getName();
        this.region = weatherDTO.getLocation().getRegion();
        this.country = weatherDTO.getLocation().getCountry();
        this.maxtemp_c = weatherDTO.getForecast().getForecastday().getFirst().getDay().getMaxtemp_c();
        this.mintemp_c = weatherDTO.getForecast().getForecastday().getFirst().getDay().getMintemp_c();
        this.avgtemp_c = weatherDTO.getForecast().getForecastday().getFirst().getDay().getAvgtemp_c();
        this.condition = weatherDTO.getForecast().getForecastday().getFirst().getDay().getCondition().getText();
        // this.maxtemp_c = weatherDTO.getForecast().getForecastday().stream().map(d-> new Day(d)).collect(Collection.toList()); это если промежуток дат. Так же нужно создать Day
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getMaxtemp_c() {
        return maxtemp_c;
    }

    public void setMaxtemp_c(double maxtemp_c) {
        this.maxtemp_c = maxtemp_c;
    }

    public double getMintemp_c() {
        return mintemp_c;
    }

    public void setMintemp_c(double mintemp_c) {
        this.mintemp_c = mintemp_c;
    }

    public double getAvgtemp_c() {
        return avgtemp_c;
    }

    public void setAvgtemp_c(double avgtemp_c) {
        this.avgtemp_c = avgtemp_c;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
