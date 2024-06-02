package com.project.goinghome.flight.repository;

import com.project.goinghome.common.config.jpa.JpaConfig;
import com.project.goinghome.flight.domain.Airline;
import com.project.goinghome.flight.domain.Airport;
import com.project.goinghome.flight.domain.Flight;
import com.project.goinghome.flight.domain.SeatClass;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Import(JpaConfig.class)
@DataJpaTest
class FlightQueryRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightQueryRepository flightQueryRepository;

    @TestConfiguration
    static class TestConfig {

        @PersistenceContext
        private EntityManager entityManager;

        @Bean
        public FlightQueryRepository flightQueryRepository() {
            return new FlightQueryRepository(entityManager);
        }
    }

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
                        .departure(Airport.SEL)
                        .departureAt(LocalDateTime.of(2024, 4, 1, 11, 30))
                        .arrival(Airport.CJU)
                        .arrivalAt(LocalDateTime.of(2024, 4, 1, 12, 40))
                        .seatClass(SeatClass.Y)
                        .seatCount(10)
                        .fare(20000)
                        .build(),
                Flight.builder()
                        .airline(Airline.AAR)
                        .flightNumber("4321")
                        .departure(Airport.SEL)
                        .departureAt(LocalDateTime.of(2024, 4, 21, 12, 30))
                        .arrival(Airport.CJU)
                        .arrivalAt(LocalDateTime.of(2024, 4, 21, 13, 40))
                        .seatClass(SeatClass.Y)
                        .seatCount(10)
                        .fare(10000)
                        .build()
        );
        flightRepository.saveAll(flights);
    }

    @Test
    void findByItinerary() {
        // given
        Airport departure = Airport.SEL;
        Airport arrival = Airport.CJU;
        LocalDate departureDate = LocalDate.of(2024, 4, 1);

        // when
        List<Flight> actual = flightQueryRepository.findByItinerary(departure, arrival, departureDate);

        // then
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getFare()).isEqualTo(10000);
    }
}