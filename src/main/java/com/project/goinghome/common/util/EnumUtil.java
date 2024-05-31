package com.project.goinghome.common.util;

import com.project.goinghome.common.util.exception.InvalidTypeException;

public class EnumUtil {

    private EnumUtil() {}

    public static <T extends Enum<T>> T valueOf(String value, Class<T> clazz) {
        try {
            return Enum.valueOf(clazz, value);
        } catch (Exception e) {
            throw new InvalidTypeException(value, clazz);
        }
    }
}
