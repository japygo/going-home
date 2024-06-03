package com.project.goinghome.flight.dto;

import com.project.goinghome.flight.domain.Flight;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightInfoPair implements FlightInfoBase {
    private final FlightInfo departure;
    private final FlightInfo arrival;
    private final int fare;

    public static FlightInfoPair from(FlightInfo departure, FlightInfo arrival) {
        return new FlightInfoPair(
                departure,
                arrival,
                departure.getFare() + arrival.getFare()
        );
    }

    public static FlightInfoPair from(Flight departure, Flight arrival) {
        return new FlightInfoPair(
                FlightInfo.from(departure),
                FlightInfo.from(arrival),
                departure.getFare() + arrival.getFare()
        );
    }
}
