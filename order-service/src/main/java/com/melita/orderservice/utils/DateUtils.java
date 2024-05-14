package com.melita.orderservice.utils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format(OffsetDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }

}
