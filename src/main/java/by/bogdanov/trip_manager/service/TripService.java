package by.bogdanov.trip_manager.service;

import by.bogdanov.trip_manager.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TripService {
    // подключаем Repository
    private final TripRepository tripRepository;

    // конструктор
    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }
}
