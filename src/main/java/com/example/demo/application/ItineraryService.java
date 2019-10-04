package com.example.demo.application;

import com.example.demo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItineraryService {

    private ItineraryRepository itineraryRepository;

    @Autowired
    public ItineraryService(ItineraryRepository itineraryRepository) {
        this.itineraryRepository = itineraryRepository;
    }

    public List<Itinerary> getItineraries() {

        List<Itinerary> itineraries = itineraryRepository.findAll();

        return itineraries;

    }

    public List<ItineraryDto> getItineraries(String startDate, String endDate) {

        LocalDateTime parsedStartDate =
                parseForFiltering(startDate);

        LocalDateTime parsedEndDate =
                parseForFiltering(endDate);

        List<List<Itinerary>> container = new ArrayList<>();

        container.add(
                itineraryRepository
                        .findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(
                                parsedStartDate,
                                parsedEndDate
                        ));
        container.add(
                itineraryRepository
                        .findAllByStartDateLessThanAndEndDateBetween(
                                parsedStartDate,
                                parsedStartDate,
                                parsedEndDate
                        ));
        container.add(
                itineraryRepository
                        .findAllByEndDateGreaterThanAndStartDateBetween(
                                parsedEndDate,
                                parsedStartDate,
                                parsedEndDate
                        ));
        container.add(
                itineraryRepository
                        .findAllByStartDateLessThanAndEndDateGreaterThan(
                                parsedStartDate,
                                parsedEndDate
                        ));

        List<Itinerary> itineraries = new ArrayList<>();

        for(List<Itinerary> itinerariesOfType : container) {
            itineraries.addAll(itinerariesOfType);
        }

        List<ItineraryDto> itineraryDtos = new ArrayList<>();

        for(Itinerary itinerary : itineraries) {
            itineraryDtos.add(
                    unparse(itinerary)
            );
        }

        return itineraryDtos;

    }

    public Itinerary getItinerary(Long itineraryId) {

        // TODO: 지정된 아이디의 일정이 없는 경우에 예외를 반환하도록 처리
        Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElse(null);

        return itinerary;

    }

    public Itinerary addItinerary(ItineraryDto resource) throws ParseException {

        Itinerary parsed = parseForSaving(resource);

        Itinerary itinerary = itineraryRepository.save(parsed);

        return itinerary;

    }

    private Itinerary parseForSaving(ItineraryDto resource) throws ParseException {
        //TODO: parse Exception 에 대한 처리를 추가
        //TODO: parsing 이 올바르게 진행되었는지 확인하는 테스트를 추가
        String pattern = "yyyyMMddHHmm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        LocalDateTime parsedStartDate =
                LocalDateTime.parse(resource.getStartDate(), formatter);
        LocalDateTime parsedEndDate =
                LocalDateTime.parse(resource.getEndDate(), formatter);

//        // Time Zone adjustment
//        parsedStartDate = parsedStartDate.plusHours(9);
//        parsedEndDate = parsedEndDate.plusHours(9);

        // TODO: 일정의 종류 계산. 반복 설정 읽어오기

        Sort thisSort = setSort(parsedStartDate, parsedEndDate);

        Period repetitionPeriod =
                Period.valueOf(resource.getRepetitionPeriod());

        return Itinerary.builder()
                .startDate(parsedStartDate)
                .endDate(parsedEndDate)
                .sort(thisSort)
                .repetitionPeriod(repetitionPeriod)
                .title(resource.getTitle())
                .description(resource.getDescription())
                .build();
    }

    private LocalDateTime parseForFiltering(String date) {
        // TODO: parsing이 올바르게 진행되었는지 확인하는 테스트를 추가.

        String pattern = "yyyyMMddHHmm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        LocalDateTime parsed =
                LocalDateTime.parse(date, formatter);

//        // timezone adjust
//        parsedDate.plusHours(9);

        return parsed;
    }

    private Sort setSort(LocalDateTime parsedStartDate, LocalDateTime parsedEndDate) {

        String checkPattern = "yyyyMMdd";
        DateTimeFormatter checkFormatter =
                DateTimeFormatter.ofPattern(checkPattern);

        Sort thisSort = null;

        if(parsedStartDate.format(checkFormatter)
                .equals(parsedEndDate.format(checkFormatter)
        )) {
            thisSort = Sort.ORDINARY;
        } else {
            thisSort = Sort.CONTINUOUS;
        }

        return thisSort;
    }

    private ItineraryDto unparse(Itinerary itinerary) {

        ItineraryDto dto;

        String pattern = "yyyyMMddHHmm";
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(pattern);

        String unparsedStartDate =
                itinerary.getStartDate().format(formatter);
        String unparsedEndDate =
                itinerary.getEndDate().format(formatter);

        dto = ItineraryDto.builder()
                .id(itinerary.getId())
                .startDate(unparsedStartDate)
                .endDate(unparsedEndDate)
                .sort(itinerary.getSort().intValue())
                .repetitionPeriod(
                        itinerary.getRepetitionPeriod().intValue())
                .title(itinerary.getTitle())
                .description(itinerary.getDescription())
                .build();

        return dto;

    }
}
