package com.project.goinghome.common.util;

import com.project.goinghome.common.util.exception.InvalidTypeException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EnumUtilTest {

    @Test
    void valueOf() {
        // given
        String value = "TEST_A";

        // when
        TestEnum actual = EnumUtil.valueOf(value, TestEnum.class);

        // then
        assertThat(actual).isEqualTo(TestEnum.TEST_A);
    }

    @Test
    void valueOfException() {
        // given
        String value = "TEST_D";

        // when

        // then
        assertThatThrownBy(() -> EnumUtil.valueOf(value, TestEnum.class))
                .isInstanceOf(InvalidTypeException.class);
    }

    @Test
    void valueOfNull() {
        // given
        String value = null;

        // when

        // then
        assertThatThrownBy(() -> EnumUtil.valueOf(value, TestEnum.class))
                .isInstanceOf(InvalidTypeException.class);
    }

    private enum TestEnum {
        TEST_A,
        TEST_B,
        TEST_C,
        ;
    }
}