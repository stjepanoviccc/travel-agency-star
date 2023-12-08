package com.sr182022.travelagencystar.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
}