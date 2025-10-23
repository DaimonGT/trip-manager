package by.bogdanov.trip_manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 30, message = "Название поездки должно быть от 2 до 30 символов")
    @Column(nullable = false, length = 30)
    private String nameTrip;

    @Size(min = 2, max = 50, message = "Название меесто назначения должно быть от 2 до 50 символов")
    @Column(nullable = false, length = 50)
    private String destination;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String weatherData; // буду получать данные из API и сохранять сюда

    // зависимости?

    // конструктор
    public Trip(String nameTrip, String destination, LocalDate startDate, LocalDate endDate, String weatherData) {
        this.nameTrip = nameTrip;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weatherData = weatherData;
    }

    // геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameTrip() {
        return nameTrip;
    }

    public void setNameTrip(String nameTrip) {
        this.nameTrip = nameTrip;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(String weatherData) {
        this.weatherData = weatherData;
    }
}
