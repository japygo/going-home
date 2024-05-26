package com.project.goinghome.flight.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AirportTest {

    @Test
    void fromCity() {
        assertThat(Airport.fromCity("제주")).isEqualTo(Airport.CJU);
    }

    @Test
    void fromCityException() {
        assertThatThrownBy(() -> Airport.fromCity("서귀포"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}