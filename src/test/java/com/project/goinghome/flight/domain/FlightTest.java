package com.project.goinghome.flight.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

class FlightTest {

    @Test
    void create() {
        Flight flight = Flight.builder()
                .airlineCode("KE")
                .flightNumber("1234")
                .departureCity("서울")
                .departureAt(LocalDateTime.of(2024, 4, 1, 10, 30))
                .arrivalCity("제주")
                .arrivalAt(LocalDateTime.of(2024, 4, 1, 11, 40))
                .seatClass("D")
                .seatCount(10)
                .fare(10000)
                .build();

        assertThat(flight).isNotNull();
        assertThat(flight.getAirline()).isEqualTo(Airline.KAL);
        assertThat(flight.getFlightNumber()).isEqualTo("1234");
        assertThat(flight.getDeparture()).isEqualTo(Airport.SEL);
        assertThat(flight.getSeatClass()).isEqualTo(SeatClass.D);
    }
}