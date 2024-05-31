package com.project.goinghome.flight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.goinghome.flight.api.naver.NaverFlightFindService;
import com.project.goinghome.flight.api.naver.dto.NaverFlightResponse;
import com.project.goinghome.flight.domain.Airport;
import com.project.goinghome.flight.domain.Flight;
import com.project.goinghome.flight.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.List;

@Transactional
@SpringBootTest
class NaverFlightSaveServiceTest {

    @Autowired
    NaverFlightSaveService naverFlightSaveService;

    @Autowired
    FlightRepository flightRepository;

    @MockBean
    NaverFlightFindService naverFlightFindService;

    @Test
    void save() throws JsonProcessingException {
        // given
        Airport departure = Airport.SEL;
        Airport arrival = Airport.CJU;
        LocalDate startDate = LocalDate.of(2024, 5, 1);
        LocalDate endDate = LocalDate.of(2024, 5, 31);

        NaverFlightResponse response = new NaverFlightResponse(
                List.of(NaverFlightResponse.Data.DomesticFlights.FlightInfo.builder()
                        .airlineCode("KE")
                        .fitName("1187")
                        .depCity("GMP")
                        .arrCity("CJU")
                        .departureDate("202405221750")
                        .arrivalDate("202405221900")
                        .seatClass("Y")
                        .seatCnt(10)
                        .minFare(19000)
                        .build()),
                List.of(NaverFlightResponse.Data.DomesticFlights.FlightInfo.builder()
                        .airlineCode("KE")
                        .fitName("1187")
                        .depCity("CJU")
                        .arrCity("GMP")
                        .departureDate("202405221750")
                        .arrivalDate("202405221900")
                        .seatClass("Y")
                        .seatCnt(10)
                        .minFare(19000)
                        .build())
        );

        given(naverFlightFindService.findFlightByExternal(any(), any(), any()))
                .willReturn(response.toFlights());

        // when
        naverFlightSaveService.saveAllBetweenDate(departure, arrival, startDate, endDate);

        List<Flight> actual = flightRepository.findAll();

        // then
        assertThat(actual).hasSize(2);
    }
}