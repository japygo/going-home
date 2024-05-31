package com.project.goinghome.flight.dto;

import com.project.goinghome.flight.domain.Flight;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightSearchResponse {
    private final List<FlightInfo> departures;
    private final List<FlightInfo> arrivals;

    public static FlightSearchResponse of(List<Flight> departures, List<Flight> arrivals) {
        return new FlightSearchResponse(
                departures.stream().map(FlightInfo::new).toList(),
                arrivals.stream().map(FlightInfo::new).toList()
        );
    }

    private static class FlightInfo {
        private final String airlineCode;
        private final String airlineName;
        private final String flightNumber;
        private final String departureCity;
        private final String arrivalCity;
        private final String departureCityName;
        private final String arrivalCityName;
        private final String departureDate;
        private final String arrivalDate;
        private final String departureTime;
        private final String arrivalTime;
        private final String journeyTime;
        private final String seatClass;
        private final int seatCnt;
        private final int fare;

        public FlightInfo(Flight flight) {
            this.airlineCode = flight.getAirline().code();
            this.airlineName = flight.getAirline().name();
            this.flightNumber = flight.getFlightNumber();
            this.departureCity = flight.getDeparture().code();
            this.arrivalCity = flight.getArrival().code();
            this.departureCityName = flight.getDeparture().name();
            this.arrivalCityName = flight.getArrival().name();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.departureDate = flight.getDepartureAt().format(dateFormatter);
            this.arrivalDate = flight.getArrivalAt().format(dateFormatter);

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            this.departureTime = flight.getDepartureAt().format(timeFormatter);
            this.arrivalTime = flight.getArrivalAt().format(timeFormatter);

            Duration duration = Duration.between(flight.getDepartureAt(), flight.getArrivalAt());
            this.journeyTime = duration.toHours() % 24 + ":" + duration.toMinutes() % 60;

            this.seatClass = flight.getSeatClass().label();
            this.seatCnt = flight.getSeatCount();
            this.fare = flight.getFare();
        }
    }
}
