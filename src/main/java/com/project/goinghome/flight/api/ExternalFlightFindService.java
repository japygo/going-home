package com.project.goinghome.flight.api;

import com.project.goinghome.flight.domain.Airport;
import com.project.goinghome.flight.domain.Flight;

import java.time.LocalDate;
import java.util.List;

public interface ExternalFlightFindService {

    List<Flight> findFlightByExternal(Airport departure, Airport arrival, LocalDate departureDate);

}
