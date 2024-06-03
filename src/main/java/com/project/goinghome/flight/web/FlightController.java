package com.project.goinghome.flight.web;

import com.project.goinghome.flight.dto.FlightSearchRequest;
import com.project.goinghome.flight.dto.FlightSearchResponse;
import com.project.goinghome.flight.service.FlightFindService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FlightController {

    private final FlightFindService flightFindService;

    @GetMapping("/flights")
    public FlightSearchResponse searchFlights(@Valid FlightSearchRequest request) {

        return flightFindService.findFlightBySearch(request);
    }
}
