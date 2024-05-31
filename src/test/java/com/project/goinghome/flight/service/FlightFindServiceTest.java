package com.project.goinghome.flight.service;

import com.project.goinghome.flight.domain.Airline;
import com.project.goinghome.flight.domain.Airport;
import com.project.goinghome.flight.domain.Flight;
import com.project.goinghome.flight.domain.SeatClass;
import com.project.goinghome.flight.dto.FlightSearchRequest;
import com.project.goinghome.flight.dto.FlightSearchResponse;
import com.project.goinghome.flight.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class FlightFindServiceTest {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    FlightFindService flightFindService;

    @BeforeEach
    void setUp() {
        List<Flight> flights = List.of(
                Flight.builder()
                        .airline(Airline.JJA)
                        .flightNumber("1234")
                        .departure(Airport.SEL)
                        .departureAt(LocalDateTime.of(2024, 4, 1, 10, 30))
                        .arrival(Airport.CJU)
                        .arrivalAt(LocalDateTime.of(2024, 4, 1, 11, 40))
                        .seatClass(SeatClass.Y)
                        .seatCount(10)
                        .fare(10000)
                        .build(),
                Flight.builder()
                        .airline(Airline.KAL)
                        .flightNumber("4321")
                        .departure(Airport.CJU)
                        .departureAt(LocalDateTime.of(2024, 4, 3, 11, 30))
                        .arrival(Airport.SEL)
                        .arrivalAt(LocalDateTime.of(2024, 4, 3, 12, 40))
                        .seatClass(SeatClass.Y)
                        .seatCount(10)
                        .fare(10000)
                        .build(),
                Flight.builder()
                        .airline(Airline.KAL)
                        .flightNumber("4321")
                        .departure(Airport.CJU)
                        .departureAt(LocalDateTime.of(2024, 4, 2, 11, 30))
                        .arrival(Airport.SEL)
                        .arrivalAt(LocalDateTime.of(2024, 4, 2, 12, 40))
                        .seatClass(SeatClass.Y)
                        .seatCount(10)
                        .fare(10000)
                        .build(),
                Flight.builder()
                        .airline(Airline.AAR)
                        .flightNumber("4321")
                        .departure(Airport.CJU)
                        .departureAt(LocalDateTime.of(2024, 4, 3, 12, 30))
                        .arrival(Airport.SEL)
                        .arrivalAt(LocalDateTime.of(2024, 4, 3, 13, 40))
                        .seatClass(SeatClass.Y)
                        .seatCount(10)
                        .fare(10000)
                        .build()
        );
        flightRepository.saveAll(flights);
    }

    @Test
    void findFlightBySearch() {
        // given
        FlightSearchRequest request = new FlightSearchRequest(
                "SEL",
                "CJU",
                LocalDate.of(2024, 4, 1),
                LocalDate.of(2024, 4, 3)
        );

        // when
        FlightSearchResponse actual = flightFindService.findFlightBySearch(request);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getDepartures()).hasSize(1);
        assertThat(actual.getArrivals()).hasSize(2);
    }
}