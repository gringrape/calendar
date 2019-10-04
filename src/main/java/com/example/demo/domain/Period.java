package com.example.demo.domain;

public enum Period {

    DAILY(1), WEEKLY(2), MONTHLY(3), NOT(0);

    private final Integer value;

    Period(Integer value) {
        this.value = value;
    }

    public Integer intValue() {
        return value;
    }

    public static Period valueOf(Integer value) throws UnknownValueForPeriodException {
        switch(value) {
            case 0: return NOT;
            case 1: return DAILY;
            case 2: return WEEKLY;
            case 3: return MONTHLY;
            default: throw new UnknownValueForPeriodException("Unknown value for Repetition Period: " + value);
        }
    }

}
