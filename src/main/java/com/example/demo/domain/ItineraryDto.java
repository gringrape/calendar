package com.example.demo.domain;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryDto {

    private Long id;

    private String startDate;

    private String endDate;

    private String title;

    private String description;

    private Integer repetitionPeriod;

    private Integer sort;

}
