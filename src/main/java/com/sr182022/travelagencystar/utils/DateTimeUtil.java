package com.sr182022.travelagencystar.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {

    public static Timestamp convertLocalDateToTimestamp(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Timestamp.valueOf(localDate.atStartOfDay());
    }

    public static LocalDate convertTimestampToLocalDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime().toLocalDate();
    }

    public static int convertLocalDateToInt(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(date, currentDate);
        return period.getYears();
    }

    public static Timestamp convertLocalDateTimeToTimestamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Timestamp.valueOf(localDateTime);
    }

    public static LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }

    public static long calculateDaysBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            // will add custom exception
            return -1;
        }
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static boolean isDateInPast(LocalDate date) {
        if (date == null) {
            return false;
        }

        LocalDate currentDate = LocalDate.now();
        return date.isBefore(currentDate);
    }
}