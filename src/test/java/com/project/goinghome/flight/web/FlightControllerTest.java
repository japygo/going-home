package com.project.goinghome.flight.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.goinghome.flight.domain.Airport;
import com.project.goinghome.flight.dto.FlightSearchRequest;
import com.project.goinghome.flight.dto.FlightSearchResponse;
import com.project.goinghome.flight.service.FlightFindService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@WebMvcTest(FlightController.class)
class FlightControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FlightFindService flightFindService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void findFlightBySearch() throws Exception {
        // given
        FlightSearchRequest request = FlightSearchRequest.builder()
                .departure(Airport.SEL.code())
                .arrival(Airport.CJU.code())
                .startDate(LocalDate.of(2024, 4, 1))
                .endDate(LocalDate.of(2024, 4, 2))
                .build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Map<String, String> map = objectMapper.convertValue(request, new TypeReference<>() {});
        params.setAll(map);

        FlightSearchResponse response = FlightSearchResponse.of(List.of(), List.of());

        given(flightFindService.findFlightBySearch(request)).willReturn(response);

        // when

        // then
        mockMvc.perform(get("/flights")
                        .params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departures").exists())
                .andExpect(jsonPath("$.arrivals").exists());

        verify(flightFindService).findFlightBySearch(request);
    }
}