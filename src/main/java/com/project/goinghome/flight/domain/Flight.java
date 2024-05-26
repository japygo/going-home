package com.project.goinghome.flight.domain;

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

import java.time.LocalDateTime;

@Entity
@Table(name = "flight")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Flight {

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
