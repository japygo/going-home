package com.project.goinghome.flight.repository;

import com.project.goinghome.flight.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
