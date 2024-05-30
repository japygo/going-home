package com.project.goinghome.flight.api.naver.dto;

import com.project.goinghome.flight.api.naver.dto.NaverFlightResponse.Data.DomesticFlights.FlightInfo;
import com.project.goinghome.flight.domain.Flight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class NaverFlightResponse {
    private Data data;

    public NaverFlightResponse(List<FlightInfo> departures, List<FlightInfo> arrival) {
        this.data = new Data(departures, arrival);
    }

    public List<Flight> toFlights() {
        List<Flight> flights = new ArrayList<>();

        if (data != null && data.domesticFlights != null) {
            for (FlightInfo arrival : data.domesticFlights.arrivals) {
                flights.add(arrival.toFlight());
            }

            for (FlightInfo departure : data.domesticFlights.departures) {
                flights.add(departure.toFlight());
            }
        }

        return flights;
    }

    @Getter
    @NoArgsConstructor
    public static class Data {
        private DomesticFlights domesticFlights;

        public Data(List<FlightInfo> departures, List<FlightInfo> arrival) {
            this.domesticFlights = new DomesticFlights(departures, arrival);
        }

        @Getter
        @AllArgsConstructor
        public static class DomesticFlights {
            private List<FlightInfo> departures;
            private List<FlightInfo> arrivals;

            @Getter
            @Builder
            public static class FlightInfo {
                private String airlineCode;
                private String airlineName;
                private String fitName;
                private String depCity;
                private String arrCity;
                private String departureCityName;
                private String arrivalCityName;
                private String departureDate;
                private String arrivalDate;
                private String departureTime;
                private String arrivalTime;
                private String seatClass;
                private int seatCnt;
                private String journeyTime;
                private int minFare;

                public Flight toFlight() {
                    LocalDateTime departureAt = parseLocalDateTime(departureDate);
                    LocalDateTime arrivalAt = parseLocalDateTime(arrivalDate);

                    return Flight.builder()
                            .airlineCode(airlineCode)
                            .flightNumber(fitName)
                            .departure(depCity)
                            .departureAt(departureAt)
                            .arrival(arrCity)
                            .arrivalAt(arrivalAt)
                            .seatClass(seatClass)
                            .seatCount(seatCnt)
                            .fare(minFare)
                            .build();
                }

                private LocalDateTime parseLocalDateTime(String dateTime) {
                    try {
                        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
                    } catch (DateTimeException e) {
                        throw new IllegalArgumentException("Invalid dateTime(yyyyMMddHHmm): " + dateTime, e);
                    }
                }
            }
        }
    }
}
