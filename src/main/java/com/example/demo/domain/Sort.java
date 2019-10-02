package com.example.demo.domain;

public enum Sort {

    ORDINARY(1), CONTINUOUS(2), REPETITIVE(3);

    private final Integer value;

    Sort(Integer value) {
        this.value = value;
    }

    public Integer intValue() {
        return value;
    }

    public static Sort valueOf(Integer value) {
        switch(value) {
            case 1: return ORDINARY;
            case 2: return CONTINUOUS;
            case 3: return REPETITIVE;
            default: throw new AssertionError("Unknown value for itinerary sort: " + value);
        }
    }
}
