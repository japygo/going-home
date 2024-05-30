package com.project.goinghome.flight.api.naver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.goinghome.flight.api.naver.dto.NaverFlightResponse;
import com.project.goinghome.flight.api.naver.dto.NaverFlightResponse.Data.DomesticFlights.FlightInfo;
import com.project.goinghome.flight.domain.Airport;
import com.project.goinghome.flight.domain.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class NaverFlightFindServiceTest {

    @Value("${naver.api.url}")
    String apiUrl;

    @Autowired
    NaverFlightFindService naverFlightFindService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void findFlight() throws JsonProcessingException {
        // given
        Airport departure = Airport.SEL;
        Airport arrival = Airport.CJU;
        LocalDate departureDate = LocalDate.of(2025, 1, 1);

        NaverFlightResponse response = new NaverFlightResponse(
                List.of(FlightInfo.builder()
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
                List.of(FlightInfo.builder()
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

        mockServer.expect(ExpectedCount.once(), requestTo(apiUrl))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(objectMapper.writeValueAsString(response), MediaType.APPLICATION_JSON));

        // when
        List<Flight> actual = naverFlightFindService.findFlightByExternal(departure, arrival, departureDate);

        // then
        assertThat(actual).hasSize(2);
    }
}