package com.example.demo.domain;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryDto {

    private Long id;

    @NotEmpty
    private String startDate;

    @NotEmpty
    private String endDate;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotNull
    private Integer repetitionPeriod;

    private Integer sort;

}
