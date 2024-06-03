package com.project.goinghome.flight.service;

import com.project.goinghome.flight.domain.Airline;
import com.project.goinghome.flight.domain.Airport;
import com.project.goinghome.flight.domain.Flight;
import com.project.goinghome.flight.domain.SearchType;
import com.project.goinghome.flight.domain.SeatClass;
import com.project.goinghome.flight.dto.FlightInfoPair;
import com.project.goinghome.flight.dto.FlightSearchRequest;
import com.project.goinghome.flight.dto.FlightSearchResponse;
import com.project.goinghome.flight.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
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
                        .departure(Airport.GMP)
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
                        .arrival(Airport.GMP)
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
                        .arrival(Airport.GMP)
                        .arrivalAt(LocalDateTime.of(2024, 4, 2, 12, 40))
                        .seatClass(SeatClass.Y)
                        .seatCount(10)
                        .fare(15000)
                        .build(),
                Flight.builder()
                        .airline(Airline.AAR)
                        .flightNumber("4321")
                        .departure(Airport.CJU)
                        .departureAt(LocalDateTime.of(2024, 4, 3, 12, 30))
                        .arrival(Airport.GMP)
                        .arrivalAt(LocalDateTime.of(2024, 4, 3, 13, 40))
                        .seatClass(SeatClass.Y)
                        .seatCount(10)
                        .fare(10000)
                        .build()
        );

        flightRepository.saveAll(flights);
    }

    @Test
    void findFlightByDefaultSearch() {
        // given
        FlightSearchRequest request = FlightSearchRequest.builder()
                .searchType(SearchType.D.name())
                .departure(Airport.SEL.code())
                .arrival(Airport.CJU.code())
                .startDate(LocalDate.of(2024, 4, 1))
                .endDate(LocalDate.of(2024, 4, 3))
                .build();

        // when
        FlightSearchResponse actual = flightFindService.findFlightBySearch(request);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getDepartures()).hasSize(1);
        assertThat(actual.getArrivals()).hasSize(2);
    }

    @Test
    void findFlightByPeriodSearch() {
        // given
        FlightSearchRequest request = FlightSearchRequest.builder()
                .searchType(SearchType.P.name())
                .departure(Airport.SEL.code())
                .arrival(Airport.CJU.code())
                .startDate(LocalDate.of(2024, 4, 1))
                .endDate(LocalDate.of(2024, 4, 3))
                .days(1)
                .build();

        // when
        FlightSearchResponse actual = flightFindService.findFlightBySearch(request);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getDepartures()).hasSize(1);

        FlightInfoPair pair = (FlightInfoPair) actual.getDepartures().get(0);
        assertThat(pair.getFare()).isEqualTo(25000);
    }
}