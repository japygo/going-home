package com.project.goinghome.flight.api.naver;

import com.project.goinghome.common.api.ExternalApiService;
import com.project.goinghome.flight.api.ExternalFlightFindService;
import com.project.goinghome.flight.api.naver.dto.NaverFlightRequest;
import com.project.goinghome.flight.api.naver.dto.NaverFlightResponse;
import com.project.goinghome.flight.domain.Airport;
import com.project.goinghome.flight.domain.FareType;
import com.project.goinghome.flight.domain.Flight;
import com.project.goinghome.flight.domain.TripType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NaverFlightFindService implements ExternalFlightFindService {

    public static final String NAVER_REQUEST_OPERATION_NAME = "domesticFlights";
    public static final String NAVER_REQUEST_DEVICE = "PC";

    @Value("${naver.api.url}")
    String apiUrl;

    @Value("${naver.api.referer}")
    String referer;

    @Value("${naver.api.query}")
    String query;

    private final ExternalApiService externalApiService;

    @Override
    public List<Flight> findFlightByExternal(Airport departure, Airport arrival, LocalDate departureDate) {
        String departureDateFormat = departureDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        NaverFlightRequest request = NaverFlightRequest.builder()
                .operationName(NAVER_REQUEST_OPERATION_NAME)
                .variables(NaverFlightRequest.Variables.builder()
                        .itinerary(List.of(
                                NaverFlightRequest.Variables.Itinerary.builder()
                                        .departureAirport(departure.code())
                                        .arrivalAirport(arrival.code())
                                        .departureDate(departureDateFormat)
                                        .build(),
                                NaverFlightRequest.Variables.Itinerary.builder()
                                        .departureAirport(arrival.code())
                                        .arrivalAirport(departure.code())
                                        .departureDate(departureDateFormat)
                                        .build()
                        ))
                        .person(NaverFlightRequest.Variables.Person.builder()
                                .adult(1)
                                .build())
                        .fareType(FareType.YC.name())
                        .trip(TripType.RT.name())
                        .device(NAVER_REQUEST_DEVICE)
                        .build())
                .query(query.replaceAll("\\\\n", "\n"))
                .build();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.REFERER, String.format(referer, request.getVariables().format()));

        NaverFlightResponse response = externalApiService.post(apiUrl, NaverFlightResponse.class, headers, request);

        return response.toFlights();
    }
}
