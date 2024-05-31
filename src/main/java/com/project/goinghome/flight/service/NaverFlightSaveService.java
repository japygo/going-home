package com.project.goinghome.flight.service;

import com.project.goinghome.flight.api.naver.NaverFlightFindService;
import com.project.goinghome.flight.domain.Airport;
import com.project.goinghome.flight.domain.Flight;
import com.project.goinghome.flight.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NaverFlightSaveService {

    private static final int MAX_SAVE_SIZE = 1000;

    private final FlightRepository flightRepository;

    private final NaverFlightFindService naverFlightFindService;

    public void saveAllBetweenDate(Airport departure, Airport arrival, LocalDate startDate, LocalDate endDate) {
        List<Flight> flights = new ArrayList<>();
        LocalDate departureDate = startDate;

        while (!departureDate.isAfter(endDate)) {
            flights.addAll(naverFlightFindService.findFlightByExternal(departure, arrival, departureDate));

            departureDate = departureDate.plusDays(1);

            if (flights.size() >= MAX_SAVE_SIZE) {
                flightRepository.saveAll(flights);
                flightRepository.flush();
                flights.clear();
            }
        }

        flightRepository.saveAll(flights);
    }
}
