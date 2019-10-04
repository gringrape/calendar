package com.example.demo.domain;

public class ItineraryNotFoundException extends RuntimeException {

    public ItineraryNotFoundException(Long id) {

        super("일정번호 " + id + " 를 찾을수 없습니다.");

    }

}
