package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {

    public static void main(String[] args) throws ParseException {

        String pattern = "yyyyMMddHHmm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date parsedStartDate =
                simpleDateFormat.parse("201910030400");

        System.out.println(parsedStartDate);

    }

}
