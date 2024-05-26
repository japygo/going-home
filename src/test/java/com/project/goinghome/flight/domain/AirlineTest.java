package com.project.goinghome.flight.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AirlineTest {

    @Test
    void fromCode() {
        assertThat(Airline.fromCode("KE")).isEqualTo(Airline.KAL);
    }

    @Test
    void fromCodeException() {
        assertThatThrownBy(() -> Airline.fromCode("KA"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}