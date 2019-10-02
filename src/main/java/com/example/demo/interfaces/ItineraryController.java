package com.example.demo.interfaces;

import com.example.demo.application.ItineraryService;
import com.example.demo.domain.Itinerary;
import com.example.demo.domain.ItineraryDto;
import com.example.demo.domain.Period;
import com.example.demo.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        itineraryDtos = itineraryService.getItineraries(startDate.get(), endDate.get());

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
            @RequestBody ItineraryDto resource
    ) throws URISyntaxException, ParseException {
        //TODO: Parse Exception 처리 데이터 형식을 잘못 보낸것이므로 badRequest 처리
        Itinerary itinerary = itineraryService.addItinerary(resource);

        String url = "/itineraries/" + itinerary.getId();

        return ResponseEntity.created(new URI(url)).body("{}");

    }

}
