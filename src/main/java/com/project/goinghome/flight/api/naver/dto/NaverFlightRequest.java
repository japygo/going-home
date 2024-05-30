package com.project.goinghome.flight.api.naver.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NaverFlightRequest {
    private final String operationName;
    private final Variables variables;
    private String query;

    @Getter
    public static class Variables {
        private final List<Itinerary> itinerary;
        private final Person person;
        private final String fareType;
        private final String trip;
        private final String device;

        @Builder
        public Variables(List<Itinerary> itinerary, Person person, String fareType, String trip, String device) {
            if (itinerary.size() > 2) {
                throw new IllegalArgumentException("Itinerary size must be less than 2");
            }
            this.itinerary = itinerary;
            this.person = person;
            this.fareType = fareType;
            this.trip = trip;
            this.device = device;
        }

        public String format() {
            return itinerary.get(0).format() + "/" + itinerary.get(1).format();
        }

        @Getter
        @Builder
        public static class Itinerary {
            private final String departureAirport;
            private final String arrivalAirport;
            private final String departureDate;

            private String format() {
                return departureAirport + "-" + arrivalAirport + "-" + departureDate;
            }
        }

        @Getter
        @Builder
        public static class Person {
            private final int adult;
            private final int child;
            private final int infant;
        }
    }
}
