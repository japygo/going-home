package com.project.goinghome.flight.service;

import com.project.goinghome.common.util.EnumUtil;
import com.project.goinghome.flight.domain.Airport;
import com.project.goinghome.flight.dto.FlightSearchRequest;
import com.project.goinghome.flight.dto.FlightSearchResponse;
import com.project.goinghome.flight.repository.FlightQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FlightFindService {

    private final FlightQueryRepository flightQueryRepository;

    public FlightSearchResponse findFlightBySearch(FlightSearchRequest request) {
        Airport departure = EnumUtil.valueOf(request.getDeparture(), Airport.class);
        Airport arrival = EnumUtil.valueOf(request.getArrival(), Airport.class);

        return FlightSearchResponse.of(
                flightQueryRepository.findByItinerary(departure, arrival, request.getStartDate()),
                flightQueryRepository.findByItinerary(arrival, departure, request.getEndDate())
        );
    }
}
