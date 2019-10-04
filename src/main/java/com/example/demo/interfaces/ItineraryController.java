package com.example.demo.interfaces;

import com.example.demo.application.ItineraryService;
import com.example.demo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ItineraryController {

    @Autowired
    ItineraryService itineraryService;

    @GetMapping("/itineraries")
    public List<ItineraryDto> list(
            @RequestParam Optional<String> startDate,
            @RequestParam Optional<String> endDate
    ) {
        List<ItineraryDto> itineraryDtos;

        itineraryDtos = itineraryService
                .getItineraries(
                        startDate.orElseThrow(() ->
                                new SearchWithEmptyDateExcecption("시작일")),
                        endDate.orElseThrow(() ->
                                new SearchWithEmptyDateExcecption("종료일")));

        return itineraryDtos;
    }

    @GetMapping("/itineraries/{itineraryId}")
    public Itinerary detail(
            @PathVariable("itineraryId") Long itineraryId
    ) {

        Itinerary itinerary = itineraryService
                                .getItinerary(itineraryId);

        return itinerary;

    }

    @PostMapping("/itineraries")
    public ResponseEntity<?> create(
            @RequestBody @Valid ItineraryDto resource
    ) throws URISyntaxException, UnknownValueForPeriodException {

        Itinerary itinerary = itineraryService.addItinerary(resource);

        String url = "/itineraries/" + itinerary.getId();

        return ResponseEntity.created(new URI(url)).body("{}");

    }

}
