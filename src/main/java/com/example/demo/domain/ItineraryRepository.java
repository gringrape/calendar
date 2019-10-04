package com.example.demo.domain;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ItineraryRepository extends CrudRepository<Itinerary, Long> {

    List<Itinerary> findAll();

    Itinerary save(Itinerary resource);

    Optional<Itinerary> findById(Long itineraryId);

    List<Itinerary> findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(
            LocalDateTime startOfCalendar
            , LocalDateTime endOfCalendar
    );

    List<Itinerary> findAllByStartDateLessThanAndEndDateBetween(
            LocalDateTime startOfCalendar1
            , LocalDateTime startOfCalendar2
            , LocalDateTime endOfCalendar
    );

    List<Itinerary> findAllByEndDateGreaterThanAndStartDateBetween(
            LocalDateTime endOfCalendar1,
            LocalDateTime startOfCalendar,
            LocalDateTime endOfCalendar2
    );

    List<Itinerary> findAllByStartDateLessThanAndEndDateGreaterThan(
            LocalDateTime startOfCalendar,
            LocalDateTime endOfCalendar
    );
}
