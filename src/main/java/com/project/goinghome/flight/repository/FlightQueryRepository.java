package com.project.goinghome.flight.repository;

import com.project.goinghome.flight.domain.Airport;
import com.project.goinghome.flight.domain.Flight;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static com.project.goinghome.flight.domain.QFlight.flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FlightQueryRepository {

    private final JPAQueryFactory queryFactory;

    public FlightQueryRepository(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<Flight> findByItinerary(Airport departure, Airport arrival, LocalDate departureDate) {
        return queryFactory.selectFrom(flight)
                .where(
                        departureEqual(departure),
                        arrivalEqual(arrival),
                        departureDateBetween(departureDate)
                )
                .orderBy(flight.fare.asc())
                .fetch();
    }

    private BooleanExpression departureEqual(Airport departure) {
        if (departure != null) {
            return flight.departure.eq(departure);
        }
        return null;
    }

    private BooleanExpression arrivalEqual(Airport arrival) {
        if (arrival != null) {
            return flight.arrival.eq(arrival);
        }
        return null;
    }

    private BooleanExpression departureDateBetween(LocalDate departureDate) {
        if (departureDate != null) {
            LocalDateTime startDate = departureDate.atStartOfDay();
            LocalDateTime endDate = departureDate.atTime(23, 59, 59, 999999999);

            return flight.departureAt.between(startDate, endDate);
        }
        return null;
    }
}
