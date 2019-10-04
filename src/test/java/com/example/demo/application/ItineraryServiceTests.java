package com.example.demo.application;

import com.example.demo.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class ItineraryServiceTests {

    ItineraryService itineraryService;

    @Mock
    ItineraryRepository itineraryRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        itineraryService = new ItineraryService(itineraryRepository);

    }

    @Test
    public void getItineraries() {

        List<Itinerary> mockItineraries = new ArrayList<>();

        mockItineraries.add(Itinerary.builder()
                .id(1004L)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .title("회사 워크샵")
                .description("취직해서 처음가는 회사 워크샵")
                .sort(Sort.ORDINARY)
                .repetitionPeriod(Period.DAILY)
                .build());

        given(itineraryRepository.findAll())
                .willReturn(mockItineraries);

        List<Itinerary> itineraries = itineraryService.getItineraries();

        verify(itineraryRepository).findAll();

        Itinerary itinerary = itineraries.get(0);

        assertThat(itinerary.getTitle(), is("회사 워크샵"));
    }

    @Test
    public void getItinerariesFiltered() {

        List<Itinerary> mockFromType1Search = new ArrayList<>();
        List<Itinerary> mockFromType2Search = new ArrayList<>();
        List<Itinerary> mockFromType3Search = new ArrayList<>();

        mockFromType1Search.add(
                Itinerary.builder().id(1004L).build());
        mockFromType2Search.add(
                Itinerary.builder().id(1005L).build());
        mockFromType3Search.add(
                Itinerary.builder().id(1006L).build());

        given(itineraryRepository
                        .findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(
                                any(),
                                any()
                        ))
                .willReturn(mockFromType1Search);
        given(itineraryRepository
                .findAllByStartDateLessThanAndEndDateBetween(
                        any(),
                        any(),
                        any()
                ))
                .willReturn(mockFromType2Search);
        given(itineraryRepository
                .findAllByEndDateGreaterThanAndStartDateBetween(
                   any(),
                   any(),
                   any()
                ))
                .willReturn(mockFromType3Search);

        String startDate = "20190929";
        String endDate = "20191102";

        List<ItineraryDto> itineraries =
                itineraryService.getItineraries(startDate, endDate);

        verify(itineraryRepository)
                .findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(
                        any(),
                        any()
                );
        verify(itineraryRepository)
                .findAllByStartDateLessThanAndEndDateBetween(
                        any(),
                        any(),
                        any()
                );
        verify(itineraryRepository)
                .findAllByEndDateGreaterThanAndStartDateBetween(
                        any(),
                        any(),
                        any()
                );
        verify(itineraryRepository)
                .findAllByStartDateLessThanAndEndDateGreaterThan(
                        any(),
                        any()
                );

        assertThat(itineraries.get(0).getId(), is(1004L));
        assertThat(itineraries.get(1).getId(), is(1005L));
        assertThat(itineraries.get(2).getId(), is(1006L));
    }

    @Test
    public void addItinerary() throws ParseException {
        // TODO: parse exception 처리 방법 고민
        // TODO: parsing 이 제대로 되었는지 확인 - 날짜
        // TODO: method가 너무 길어졌으므로 의미에 따라 분리

        ItineraryDto resource = ItineraryDto.builder()
                .id(1004L)
                .startDate("201909030700")
                .endDate("201909052000")
                .title("회사 워크샵")
                .description("취직해서 처음가는 회사 워크샵")
                .sort(1)
                .repetitionPeriod(1)
                .build();

        Itinerary mockItinerary = Itinerary.builder().id(1004L).build();

        given(itineraryRepository.save(any()))
                .willReturn(mockItinerary);

        Itinerary itinerary = itineraryService.addItinerary(resource);

        ArgumentCaptor<Itinerary> captor =
                ArgumentCaptor.forClass(Itinerary.class);
        verify(itineraryRepository).save(captor.capture());

        Itinerary parsed = captor.getValue();

        assertThat(parsed.getRepetitionPeriod(), is(Period.DAILY));
        assertThat(parsed.getSort(), is(Sort.CONTINUOUS));

        assertThat(itinerary.getId(),is(1004L));

    }

    @Test
    public void getItinerary() {

        Itinerary mockItinerary = Itinerary.builder()
                                .id(1004L)
                                .startDate(LocalDateTime.now())
                                .endDate(LocalDateTime.now())
                                .title("회사 워크샵")
                                .description("취직해서 처음가는 회사 워크샵")
                                .sort(Sort.ORDINARY)
                                .repetitionPeriod(Period.DAILY)
                                .build();

        given(itineraryRepository.findById(eq(1004L)))
                .willReturn(java.util.Optional.ofNullable(mockItinerary));

        Itinerary itinerary = itineraryService.getItinerary(1004L);

        verify(itineraryRepository).findById(eq(1004L));

        assertThat(itinerary.getTitle(), is("회사 워크샵"));

    }
}