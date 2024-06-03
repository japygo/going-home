package com.project.goinghome.flight.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
public class FlightSearchRequest {

    @NotBlank(message = "departure 값이 없습니다")
    @Size(min = 3, max = 3, message = "잘못된 departure 형식입니다")
    private final String departure;

    @NotBlank(message = "arrival 값이 없습니다")
    @Size(min = 3, max = 3, message = "잘못된 arrival 형식입니다")
    private final String arrival;

    @NotNull(message = "startDate 값이 없습니다")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate startDate;

    @NotNull(message = "endDate 값이 없습니다")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate endDate;
}
