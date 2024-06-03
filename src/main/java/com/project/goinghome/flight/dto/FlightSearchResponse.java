package com.project.goinghome.flight.dto;

import com.project.goinghome.flight.domain.Flight;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightSearchResponse {
    private final List<? extends FlightInfoBase> departures;
    private final List<? extends FlightInfoBase> arrivals;

    public static FlightSearchResponse of(List<Flight> departures, List<Flight> arrivals) {
        return new FlightSearchResponse(
                departures.stream().map(FlightInfo::from).toList(),
                arrivals.stream().map(FlightInfo::from).toList()
        );
    }

    public static FlightSearchResponse ofPairs(List<FlightInfoPair> pairs) {
        return new FlightSearchResponse(
                pairs,
                List.of()
        );
    }
}
