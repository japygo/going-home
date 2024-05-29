package com.project.goinghome.flight.repository;

import com.project.goinghome.common.config.jpa.JpaConfig;
import com.project.goinghome.flight.domain.Flight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@Import(JpaConfig.class)
@DataJpaTest
class FlightRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @Test
    void save() {
        // given
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

        // when
        Flight save = flightRepository.save(flight);

        // then
        assertThat(save.getId()).isNotNull();
        assertThat(save.getCreatedAt()).isNotNull();
    }
}