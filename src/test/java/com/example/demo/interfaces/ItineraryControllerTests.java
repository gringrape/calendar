package com.example.demo.interfaces;

import com.example.demo.application.ItineraryService;
import com.example.demo.domain.Itinerary;
import com.example.demo.domain.Period;
import com.example.demo.domain.Sort;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ItineraryController.class)
public class ItineraryControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    ItineraryService itineraryService;

    @Test
    public void create() throws Exception {



        Itinerary mockItinerary = Itinerary.builder()
                                .id(1004L)
                                .startDate(LocalDateTime.now())
                                .endDate(LocalDateTime.now())
                                .title("회사 워크샵")
                                .description("취직해서 처음가는 회사 워크샵")
                                .sort(Sort.ORDINARY)
                                .repetitionPeriod(Period.DAILY)
                                .build();

        given(itineraryService.addItinerary(any()))
                .willReturn(mockItinerary);

        mvc.perform(post("/itineraries")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"20190830\"" +
                        ",\"endDate\":\"20190903\"" +
                        ",\"title\":\"회사 워크샵\"" +
                        ",\"description\":\"취직해서 처음가는 회사 워크샵\"" +
                        ",\"sort\":1" +
                        ",\"repetitionPeriod\":1}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/itineraries/1004"));

        verify(itineraryService).addItinerary(any());

    }

    @Test
    public void detail() throws Exception {

        Itinerary mockItinerary = Itinerary.builder()
                .id(1004L)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .title("회사 워크샵")
                .description("취직해서 처음가는 회사 워크샵")
                .sort(Sort.ORDINARY)
                .repetitionPeriod(Period.DAILY)
                .build();

        given(itineraryService.getItinerary(eq(1004L)))
                .willReturn(mockItinerary);

        mvc.perform(get("/itineraries/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"title\":\"회사 워크샵\"")));

        verify(itineraryService).getItinerary(eq(1004L));

    }

    @Test
    public void listFiltered() throws Exception {

        String startDate = "20191029";
        String endDate = "20191102";

        mvc.perform(get("/itineraries?startDate=20191029&endDate=20191102"))
                .andExpect(status().isOk());

        verify(itineraryService).getItineraries(eq(startDate), eq(endDate));

    }
}