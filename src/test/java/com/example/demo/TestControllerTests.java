package com.example.demo;

import com.example.demo.TestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TestController.class)
public class TestControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    private TestRepository testRepository;

    @Test
    public void create() throws Exception {

        com.example.demo.Test testR = com.example.demo.Test.builder()
                .name("Kim")
                .nick("Jin")
                .id(1004L)
                .build();

        given(testRepository.save(any()))
                .willReturn(testR);

        mvc.perform(post("/tests")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Kim\",\"nick\":\"Jin\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/tests/1004"));

        verify(testRepository).save(any());
    }

}