package com.project.goinghome.flight.service;

import com.project.goinghome.common.util.EnumUtil;
import com.project.goinghome.flight.domain.Airport;
import com.project.goinghome.flight.domain.Flight;
import com.project.goinghome.flight.domain.SearchType;
import com.project.goinghome.flight.dto.FlightInfoPair;
import com.project.goinghome.flight.dto.FlightSearchRequest;
import com.project.goinghome.flight.dto.FlightSearchResponse;
import com.project.goinghome.flight.repository.FlightQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FlightFindService {

    private static final int FLIGHT_PAIR_LIMIT = 5;

    private final FlightQueryRepository flightQueryRepository;

    public FlightSearchResponse findFlightBySearch(FlightSearchRequest request) {
        SearchType searchType = EnumUtil.valueOf(request.getSearchType(), SearchType.class);

        return switch (searchType) {
            case D -> defaultSearch(request);
            case P -> periodSearch(request);
        };
    }

    private FlightSearchResponse defaultSearch(FlightSearchRequest request) {
        Airport departure = EnumUtil.valueOf(request.getDeparture(), Airport.class);
        Airport arrival = EnumUtil.valueOf(request.getArrival(), Airport.class);

        return FlightSearchResponse.of(
                flightQueryRepository.findByItinerary(departure, arrival, request.getStartDate()),
                flightQueryRepository.findByItinerary(arrival, departure, request.getEndDate())
        );
    }

    private FlightSearchResponse periodSearch(FlightSearchRequest request) {
        Airport departure = EnumUtil.valueOf(request.getDeparture(), Airport.class);
        Airport arrival = EnumUtil.valueOf(request.getArrival(), Airport.class);

        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        Integer days = request.getDays();
        if (days == null) {
            return FlightSearchResponse.ofPairs(List.of());
        }

        List<FlightInfoPair> pairs = new ArrayList<>();
        LocalDate departureDate = startDate;
        while (departureDate.isBefore(endDate)) {
            LocalDate arrivalDate = departureDate.plusDays(days);

            List<Flight> departureFlights =
                    flightQueryRepository.findByItinerary(departure, arrival, departureDate, FLIGHT_PAIR_LIMIT);
            List<Flight> arrivalFlights =
                    flightQueryRepository.findByItinerary(arrival, departure, arrivalDate, FLIGHT_PAIR_LIMIT);

            for (Flight departureFlight : departureFlights) {
                for (Flight arrivalFlight : arrivalFlights) {
                    pairs.add(FlightInfoPair.from(departureFlight, arrivalFlight));
                }
            }

            departureDate = departureDate.plusDays(1);
        }

        pairs.sort(Comparator.comparingInt(FlightInfoPair::getFare).reversed());

        return FlightSearchResponse.ofPairs(pairs);
    }
}
