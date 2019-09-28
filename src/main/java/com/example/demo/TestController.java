package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
public class TestController {

    @Autowired
    TestRepository testRepository;

    @PostMapping("/tests")
    public ResponseEntity create(
            @RequestBody Test resource
    ) throws URISyntaxException {

        Test test = testRepository.save(resource);

        String url = "/tests/" + test.getId();

        return ResponseEntity.created(new URI(url)).body("{}");

    }

}
