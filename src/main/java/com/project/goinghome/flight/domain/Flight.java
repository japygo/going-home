package com.project.goinghome.flight.domain;

import com.project.goinghome.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Entity
@Table(name = "flight")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Flight extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "airline", nullable = false)
    private Airline airline;

    @Column(name = "flight_number", nullable = false)
    private String flightNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "departure", nullable = false)
    private Airport departure;

    @Column(name = "departure_at", nullable = false)
    private LocalDateTime departureAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "arrival", nullable = false)
    private Airport arrival;

    @Column(name = "arrival_at", nullable = false)
    private LocalDateTime arrivalAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_class", nullable = false)
    private SeatClass seatClass;

    @Column(name = "seat_count", nullable = false)
    private Integer seatCount;

    @Column(name = "fare", nullable = false)
    private Integer fare;

    private Flight(Airline airline, String flightNumber, Airport departure, LocalDateTime departureAt,
                   Airport arrival, LocalDateTime arrivalAt, SeatClass seatClass, Integer seatCount, Integer fare) {
        validateAirline(airline);
        validateFlightNumber(flightNumber);
        validateDeparture(departure);
        validateDepartureAt(departureAt);
        validateArrival(arrival);
        validateArrivalAt(arrivalAt);
        validateSeatClass(seatClass);
        validateSeatCount(seatCount);
        validateFare(fare);
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.departureAt = departureAt;
        this.arrival = arrival;
        this.arrivalAt = arrivalAt;
        this.seatClass = seatClass;
        this.seatCount = seatCount;
        this.fare = fare;
    }

    private void validateAirline(Airline airline) {
        if (airline == null) {
            throw new IllegalArgumentException("Airline cannot be null");
        }
    }

    private void validateFlightNumber(String flightNumber) {
        if (!StringUtils.hasText(flightNumber)) {
            throw new IllegalArgumentException("Invalid flight number");
        }
    }

    private void validateDeparture(Airport departure) {
        if (departure == null) {
            throw new IllegalArgumentException("Departure cannot be null");
        }
    }

    private void validateDepartureAt(LocalDateTime departureAt) {
        if (departureAt == null) {
            throw new IllegalArgumentException("DepartureAt cannot be null");
        }
    }

    private void validateArrival(Airport arrival) {
        if (arrival == null) {
            throw new IllegalArgumentException("Arrival cannot be null");
        }
    }

    private void validateArrivalAt(LocalDateTime arrivalAt) {
        if (arrivalAt == null) {
            throw new IllegalArgumentException("ArrivalAt cannot be null");
        }
    }

    private void validateSeatClass(SeatClass seatClass) {
        if (seatClass == null) {
            throw new IllegalArgumentException("SeatClass cannot be null");
        }
    }

    private void validateSeatCount(Integer seatCount) {
        if (seatCount == null) {
            throw new IllegalArgumentException("SeatCount cannot be null");
        }
        if (seatCount < 0) {
            throw new IllegalArgumentException("Seat count cannot be negative");
        }
    }

    private void validateFare(Integer fare) {
        if (fare == null) {
            throw new IllegalArgumentException("Fare cannot be null");
        }
        if (fare < 0) {
            throw new IllegalArgumentException("Fare cannot be negative");
        }
    }

    public static FlightBuilder builder() {
        return new FlightBuilder();
    }

    @NoArgsConstructor
    public static class FlightBuilder {
        private Airline airline;
        private String flightNumber;
        private Airport departure;
        private LocalDateTime departureAt;
        private Airport arrival;
        private LocalDateTime arrivalAt;
        private SeatClass seatClass;
        private Integer seatCount;
        private Integer fare;

        public FlightBuilder airline(Airline airline) {
            this.airline = airline;
            return this;
        }

        public FlightBuilder airline(String airline) {
            this.airline = Airline.valueOf(airline);
            return this;
        }

        public FlightBuilder airlineCode(String code) {
            this.airline = Airline.fromCode(code);
            return this;
        }

        public FlightBuilder flightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
            return this;
        }

        public FlightBuilder departure(Airport departure) {
            this.departure = departure;
            return this;
        }

        public FlightBuilder departure(String departure) {
            this.departure = Airport.valueOf(departure);
            return this;
        }

        public FlightBuilder departureCity(String city) {
            this.departure = Airport.fromCity(city);
            return this;
        }

        public FlightBuilder departureAt(LocalDateTime departureAt) {
            this.departureAt = departureAt;
            return this;
        }

        public FlightBuilder arrival(Airport arrival) {
            this.arrival = arrival;
            return this;
        }

        public FlightBuilder arrival(String arrival) {
            this.arrival = Airport.valueOf(arrival);
            return this;
        }

        public FlightBuilder arrivalCity(String city) {
            this.arrival = Airport.fromCity(city);
            return this;
        }

        public FlightBuilder arrivalAt(LocalDateTime arrivalAt) {
            this.arrivalAt = arrivalAt;
            return this;
        }

        public FlightBuilder seatClass(SeatClass seatClass) {
            this.seatClass = seatClass;
            return this;
        }

        public FlightBuilder seatClass(String seatClass) {
            this.seatClass = SeatClass.valueOf(seatClass);
            return this;
        }

        public FlightBuilder seatCount(Integer seatCount) {
            this.seatCount = seatCount;
            return this;
        }

        public FlightBuilder fare(Integer fare) {
            this.fare = fare;
            return this;
        }

        public Flight build() {
            return new Flight(airline, flightNumber, departure, departureAt,
                    arrival, arrivalAt, seatClass, seatCount, fare);
        }
    }
}
