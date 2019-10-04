package com.example.demo.interfaces;

import com.example.demo.domain.ItineraryNotFoundException;
import com.example.demo.domain.UnknownValueForPeriodException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;

@Slf4j
@ControllerAdvice
public class ItineraryErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SearchWithEmptyDateExcecption.class)
    public String emptySearchHandler(
            SearchWithEmptyDateExcecption ex
    ) {
        log.error(ex.getMessage());
        return "{}";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItineraryNotFoundException.class)
    public String itineraryNotFoundHandler(
            ItineraryNotFoundException ex
    ) {
        log.error(ex.getMessage());
        return "{}";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    public String emptySearchHandler(
            DateTimeParseException ex
    ) {
        log.error(ex.getMessage() + " 보내온 데이터를 분석하는 과정에서 에러발생");
        return "{}";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnknownValueForPeriodException.class)
    public String unknownForPeriodHandler(
            UnknownValueForPeriodException ex
    ) {
        log.error(ex.getMessage() + " 올바른 반복값이 아님");
        return "{}";
    }
}
