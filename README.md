# Trip Manager - Документация проекта

## Описание проекта

Trip Manager - это REST API приложение для управления поездками с автоматическим получением данных о погоде. Система позволяет создавать, изменять и удалять информацию о поездках, автоматически обогащая их актуальными данными о погоде из внешнего API.

## Основной функционал

- Создание поездок с автоматическим получением прогноза погоды
- Получение информации о поездках по различным критериям (ID, название, дата, место назначения)
- Обновление данных поездки с автоматическим обновлением информации о погоде
- Удаление поездок
- Просмотр сохраненных данных о погоде по различным критериям

## Технологический стек

- **Java 23**
- **Spring Boot 4.0.0-SNAPSHOT**
- **Spring Data JPA** - для работы с базой данных
- **Spring Web** - для создания REST API
- **H2 Database** - встроенная база данных (in-memory)
- **Hibernate** - ORM
- **Bean Validation** - для валидации данных
- **SpringDoc OpenAPI** - для документации API (Swagger)
- **RestTemplate** - для интеграции с внешним API погоды

## Архитектура приложения

Проект следует многослойной архитектуре (Layered Architecture):
```
├── Controller Layer (REST endpoints)
├── Service Layer (бизнес-логика)
├── Repository Layer (доступ к данным)
├── Entity Layer (модели данных)
├── DTO Layer (объекты передачи данных)
└── Client Layer (внешние интеграции)
```

## Применённые паттерны проектирования

### 1. **MVC (Model-View-Controller)**
Приложение следует паттерну MVC:
- **Model**: `Trip`, `Weather` entities
- **View**: JSON responses
- **Controller**: `TripController`, `WeatherController`

### 2. **DTO (Data Transfer Object)**
Используется для передачи данных между слоями и внешними системами:
- `WeatherDTO`
- `Location`
- `Forecast`
- `Forecastday`
- `Day`
- `Condition`

### 3. **Repository Pattern**
Абстрагирует логику доступа к данным:
- `TripRepository`
- `WeatherRepository`

### 4. **Service Layer Pattern**
Инкапсулирует бизнес-логику:
- `TripService` - управление поездками
- `WeatherService` - управление данными о погоде

### 5. **Dependency Injection (DI)**
Используется Spring DI через конструкторы с аннотацией `@Autowired`

### 6. **Facade Pattern**
`WeatherApiClient` предоставляет упрощенный интерфейс для работы с внешним API погоды

### 7. **Cascade Operations**
В `Trip` entity используется каскадное удаление для связанных данных о погоде (`CascadeType.ALL`)

## Структура базы данных

### Таблица `trips`
- `id` (Long, PK, Auto-increment)
- `name_trip` (String, 2-30 символов)
- `destination` (String, 2-50 символов)
- `start_date` (LocalDate)
- `weather_id` (Long, FK)

### Таблица `weathers`
- `id` (Long, PK, Auto-increment)
- `start_date` (LocalDate)
- `location_name` (String, 2-50 символов)
- `region` (String, 2-100 символов)
- `country` (String, 2-50 символов)
- `maxtemp_c` (double)
- `mintemp_c` (double)
- `avgtemp_c` (double)
- `condition` (String, 2-50 символов)

**Связь**: Trip ↔ Weather (One-to-One)

## API Endpoints

### Trip Management

#### `POST /api/trips`
Создание новой поездки с автоматическим получением данных о погоде

**Request Body:**
```json
{
  "nameTrip": "Отпуск в Париже",
  "destination": "Paris",
  "startDate": "2025-12-25"
}
```

#### `GET /api/trips/{id}`
Получение поездки по ID

#### `PUT /api/trips/{id}`
Обновление поездки с обновлением данных о погоде

#### `DELETE /api/trips/{id}`
Удаление поездки

#### `GET /api/trips/destination/{destination}`
Получение всех поездок по месту назначения

#### `GET /api/trips/startDate/{startDate}`
Получение всех поездок по дате начала

#### `GET /api/trips/nameTrip/{nameTrip}`
Получение всех поездок по названию

### Weather Management

#### `GET /api/weather/getWeatherForTrip`
Получение и сохранение данных о погоде

**Query Parameters:**
- `location` - название локации
- `day` - дата (LocalDate)

#### `GET /api/weather/{id}`
Получение данных о погоде по ID

#### `GET /api/weather/location/{locationName}`
Получение всех данных о погоде по названию локации

#### `GET /api/weather/country/{country}`
Получение всех данных о погоде по стране

## Ключевые особенности реализации

### 1. Транзакционность
Все методы сервисов помечены `@Transactional` для обеспечения целостности данных

### 2. Валидация данных
Используется Bean Validation (`@Size`, `@Column(nullable = false)`)

### 3. Обработка ошибок
Базовая обработка через `RuntimeException` с информативными сообщениями

### 4. Автоматическое обновление погоды
При обновлении поездки старые данные о погоде удаляются и запрашиваются новые

### 5. Интеграция с внешним API
Использование WeatherAPI.com для получения прогноза погоды

### 6. REST best practices
- Использование правильных HTTP методов (GET, POST, PUT, DELETE)
- Правильные статус-коды (200, 201, 404, 500)
- RESTful URL структура

## Swagger/OpenAPI Documentation

API автоматически документируется с помощью SpringDoc OpenAPI:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI Docs**: `http://localhost:8080/api-docs`

## Конфигурация

### База данных
- **Тип**: H2 in-memory database
- **URL**: `jdbc:h2:mem:testdb`
- **H2 Console**: `http://localhost:8080/h2-console`
- **Username**: sa
- **Password**: password

### JPA/Hibernate
- **DDL Auto**: create-drop (пересоздание схемы при запуске)
- **Show SQL**: включено для отладки
- **Format SQL**: включено для читаемости логов

## Запуск приложения
```bash
./gradlew bootRun
```

Приложение будет доступно по адресу: `http://localhost:8080`

## Возможные улучшения

1. **Обработка ошибок**: Реализовать глобальный обработчик исключений
2  **Тестирование**: Написать unit и integration тесты

## Автор

Dmitriy Bogdanov