# Trip Manager

## 📋 Общее описание проекта

**Trip Manager** — это REST API микросервис для управления поездками с автоматическим получением прогноза погоды. Система позволяет планировать поездки, отслеживать их параметры и получать актуальную информацию о погоде в месте назначения на запланированную дату.

### Основная бизнес-логика:
- При создании поездки система автоматически запрашивает прогноз погоды через внешний API (WeatherAPI)
- Информация о погоде сохраняется вместе с данными о поездке
- При обновлении поездки погода пересчитывается автоматически
- Доступен полный CRUD функционал для управления поездками и погодой

---

## 🛠 Технологический стек

### Backend Framework:
- **Spring Boot 4.0.0** (snapshot) — основной фреймворк
- **Spring Web MVC** — для создания REST API
- **Spring Data JPA** — для работы с базой данных
- **Spring Validation** — для валидации входных данных

### База данных:
- **H2 Database** (in-memory) — встроенная база данных для разработки и тестирования
- **Hibernate** — ORM фреймворк для маппинга объектов

### Документация API:
- **SpringDoc OpenAPI** (Swagger) — автоматическая генерация документации API
- Swagger UI доступен по адресу `/swagger-ui.html`

### Дополнительные инструменты:
- **Lombok** — для сокращения boilerplate кода
- **JUnit 5** — для тестирования
- **Java 23** — язык программирования

### Внешние интеграции:
- **WeatherAPI.com** — внешний API для получения прогнозов погоды
- **RestTemplate** — HTTP-клиент для интеграции с внешним API

---

## 🏗 Архитектура и паттерны проектирования

### 1. **Многослойная архитектура (Layered Architecture)**
Проект разделен на четко определенные слои:

```
┌─────────────────────────────────┐
│   Controller Layer              │  ← REST API endpoints
├─────────────────────────────────┤
│   Service Layer                 │  ← Бизнес-логика
├─────────────────────────────────┤
│   Repository Layer              │  ← Доступ к данным
├─────────────────────────────────┤
│   Entity/DTO Layer              │  ← Модели данных
└─────────────────────────────────┘
```

**Слои:**
- **Controller** (`TripController`, `WeatherController`) — обработка HTTP запросов, валидация входных данных
- **Service** (`TripService`, `WeatherService`) — бизнес-логика приложения
- **Repository** (`TripRepository`, `WeatherRepository`) — работа с базой данных через Spring Data JPA
- **Client** (`WeatherApiClient`) — интеграция с внешними API
- **Entity** (`Trip`, `Weather`) — сущности базы данных
- **DTO** (`WeatherDTO`, `Location`, `Forecast`, и др.) — объекты передачи данных

### 2. **Repository Pattern**
Используется Spring Data JPA для абстракции работы с базой данных:
- `TripRepository extends JpaRepository<Trip, Long>`
- `WeatherRepository extends JpaRepository<Weather, Long>`

Преимущества:
- Автоматическая генерация CRUD операций
- Поддержка кастомных запросов через method naming и `@Query`

### 3. **DTO Pattern (Data Transfer Object)**
Разделение внутренних сущностей и внешних данных:
- **Entity** (`Weather`, `Trip`) — для хранения в БД
- **DTO** (`WeatherDTO`, `Location`, `Forecast`) — для обмена с внешним API

Это обеспечивает:
- Независимость внутренней модели от внешних источников
- Гибкость при изменении структуры данных

### 4. **Dependency Injection (DI)**
Использование Spring DI через конструкторы:
```java
@Autowired
public TripController(TripService tripService) {
    this.tripService = tripService;
}
```

### 5. **REST API Design**
Соблюдение RESTful принципов:
- **POST** `/api/trips` — создание поездки
- **GET** `/api/trips/{id}` — получение поездки
- **PUT** `/api/trips/{id}` — обновление поездки
- **DELETE** `/api/trips/{id}` — удаление поездки
- Использование правильных HTTP статусов (200, 201, 404, 500)

### 6. **Transaction Management**
Использование `@Transactional` для обеспечения целостности данных:
```java
@Transactional
public Trip createTrip(Trip trip) {
    Weather weather = weatherService.getWeatherData(...);
    trip.setWeather(weather);
    return tripRepository.save(trip);
}
```

### 7. **Cascade Operations**
Каскадное удаление связанных сущностей:
```java
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "weather_id")
private Weather weather;
```

### 8. **Builder Pattern (через Lombok)**
Использование `@Slf4j` для логирования

---

## 📊 Модель данных

### Entity: Trip
```
┌─────────────────┐
│     Trip        │
├─────────────────┤
│ id (PK)         │
│ nameTrip        │
│ destination     │
│ startDate       │
│ weather_id (FK) │
└─────────────────┘
```

### Entity: Weather
```
┌─────────────────┐
│    Weather      │
├─────────────────┤
│ id (PK)         │
│ startDate       │
│ locationName    │
│ region          │
│ country         │
│ maxtemp_c       │
│ mintemp_c       │
│ avgtemp_c       │
│ condition       │
└─────────────────┘
```

**Связь:** Trip ⟶ Weather (One-to-One с каскадным удалением)

---

## 🔄 Бизнес-процессы

### 1. Создание поездки
```
Пользователь → POST /api/trips
    ↓
TripController.createTrip()
    ↓
TripService.createTrip()
    ↓
WeatherService.getWeatherData()
    ↓
WeatherApiClient.getWeatherData() → Внешний API
    ↓
Создание Weather entity
    ↓
Сохранение Trip + Weather в БД
    ↓
Возврат созданного Trip
```

### 2. Обновление поездки
```
Пользователь → PUT /api/trips/{id}
    ↓
Поиск существующей поездки
    ↓
Обновление полей (nameTrip, destination, startDate)
    ↓
Удаление старой погоды
    ↓
Запрос новой погоды через WeatherAPI
    ↓
Сохранение обновленной поездки с новой погодой
```

---

## ✅ Валидация данных

Используется Bean Validation (Jakarta Validation):

```java
@Size(min = 2, max = 30, message = "Название поездки должно быть от 2 до 30 символов")
@Column(nullable = false, length = 30)
private String nameTrip;
```

Валидируемые поля:
- **nameTrip**: 2-30 символов
- **destination**: 2-50 символов
- **locationName**: 2-50 символов
- **region**: 2-100 символов
- **country**: 2-50 символов

---

## 📝 Логирование

Используется SLF4J (через Lombok `@Slf4j`):
- Логирование входящих запросов
- Логирование результатов операций
- Логирование ошибок с контекстом

Пример:
```java
log.info("Запрос на создание новой поездки: {}", trip.getNameTrip());
log.info("Поездка успешно создана с ID: {}", createdTrip.getId());
```

---

## 🔌 API Endpoints

### Trip Management:
| Метод  | Endpoint                        | Описание                     |
|--------|---------------------------------|------------------------------|
| POST   | `/api/trips`                    | Создать поездку              |
| GET    | `/api/trips/{id}`               | Получить поездку по ID       |
| PUT    | `/api/trips/{id}`               | Обновить поездку             |
| DELETE | `/api/trips/{id}`               | Удалить поездку              |
| GET    | `/api/trips/destination/{dest}` | Поездки по месту назначения  |
| GET    | `/api/trips/startDate/{date}`   | Поездки по дате              |
| GET    | `/api/trips/nameTrip/{name}`    | Поездки по названию          |

### Weather Management:
| Метод | Endpoint                           | Описание                  |
|-------|------------------------------------|---------------------------|
| GET   | `/api/weather/getWeatherForTrip`   | Получить погоду           |
| GET   | `/api/weather/{id}`                | Погода по ID              |
| GET   | `/api/weather/location/{location}` | Погода по городу          |
| GET   | `/api/weather/country/{country}`   | Погода по стране          |

---

## 🎯 Преимущества реализации

1. **Автоматизация** — погода получается автоматически при создании/обновлении поездки
2. **Масштабируемость** — слоистая архитектура позволяет легко расширять функционал
3. **Тестируемость** — разделение на слои упрощает unit-тестирование
4. **Документация** — автоматическая генерация Swagger документации
5. **Валидация** — входные данные проверяются на корректность
6. **Транзакционность** — целостность данных гарантируется через `@Transactional`
7. **Независимость от БД** — использование in-memory H2 для разработки, легко заменяется на PostgreSQL/MySQL в продакшене

---

## 🚀 Возможные улучшения

1. **Обработка исключений** — создать глобальный `@ControllerAdvice` для централизованной обработки ошибок
2. **Кэширование** — добавить `@Cacheable` для погодных данных (они не меняются для прошедших дат)
3. **Асинхронность** — сделать запросы к внешнему API асинхронными через `@Async`
4. **Безопасность** — добавить Spring Security для аутентификации/авторизации
5. **Pagination** — добавить постраничный вывод для списков поездок
6. **DTO маппинг** — использовать MapStruct для автоматического маппинга Entity ↔ DTO
7. **Конфигурация API** — вынести API ключ в application.properties или переменные окружения
8. **Обработка rate limits** — добавить механизм повторных попыток для внешнего API
9. **Мониторинг** — интеграция с Actuator для health checks и метрик
10. **Тестирование** — написать integration и unit тесты

---

## 📌 Заключение

Проект демонстрирует применение современных практик разработки на Spring Boot:
- Чистая архитектура с разделением ответственности
- Использование проверенных паттернов проектирования
- Интеграция с внешними API
- Автоматическая документация
- Валидация и логирование

Система готова к развертыванию в development окружении и может быть легко адаптирована для production использования с минимальными изменениями (замена H2 на PostgreSQL, добавление безопасности, внешняя конфигурация).
