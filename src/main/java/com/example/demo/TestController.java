package com.example.demo;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

import static com.sun.activation.registries.LogSupport.log;

@CrossOrigin
@RestController
public class TestController {

    @Autowired
    TestRepository testRepository;

    @PostMapping("/tests")
    public ResponseEntity create(
            @RequestBody Test resource,
            HttpServletRequest request
    ) throws URISyntaxException {

        String some = request.getParameter("name");

        System.out.println(some);

        Test test = testRepository.save(resource);

        String url = "/tests/" + test.getId();

        return ResponseEntity.created(new URI(url)).body("{}");

    }

}
