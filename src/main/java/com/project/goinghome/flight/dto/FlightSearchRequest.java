package com.project.goinghome.flight.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class FlightSearchRequest {

    @NotBlank
    @Size(min = 3, max = 3)
    private final String departure;

    @NotBlank
    @Size(min = 3, max = 3)
    private final String arrival;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate endDate;
}
