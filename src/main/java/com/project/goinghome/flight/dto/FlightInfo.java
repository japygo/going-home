package com.project.goinghome.flight.dto;

import com.project.goinghome.flight.domain.Flight;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightInfo implements FlightInfoBase {
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

    public static FlightInfo from(Flight flight) {
        String airlineCode = flight.getAirline().code();
        String airlineName = flight.getAirline().name();
        String flightNumber = flight.getFlightNumber();
        String departureCity = flight.getDeparture().code();
        String arrivalCity = flight.getArrival().code();
        String departureCityName = flight.getDeparture().name();
        String arrivalCityName = flight.getArrival().name();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String departureDate = flight.getDepartureAt().format(dateFormatter);
        String arrivalDate = flight.getArrivalAt().format(dateFormatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String departureTime = flight.getDepartureAt().format(timeFormatter);
        String arrivalTime = flight.getArrivalAt().format(timeFormatter);

        Duration duration = Duration.between(flight.getDepartureAt(), flight.getArrivalAt());
        String journeyTime = duration.toHours() % 24 + ":" + duration.toMinutes() % 60;

        String seatClass = flight.getSeatClass().label();
        int seatCnt = flight.getSeatCount();
        int fare = flight.getFare();

        return new FlightInfo(
                airlineCode,
                airlineName,
                flightNumber,
                departureCity,
                arrivalCity,
                departureCityName,
                arrivalCityName,
                departureDate,
                arrivalDate,
                departureTime,
                arrivalTime,
                journeyTime,
                seatClass,
                seatCnt,
                fare
        );
    }
}
