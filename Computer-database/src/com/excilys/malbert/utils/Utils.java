package com.excilys.malbert.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class Utils {

    public static LocalDateTime timestampToLocaldatetime(Timestamp time) {
	if (time == null) {
	    return null;
	} else {
	    return time.toLocalDateTime();
	}
    }

    public static Timestamp localdatetimeToTimestamp(LocalDateTime time) {
	return time == null ? null : Timestamp.valueOf(time);
    }

    public static LocalDateTime getDate(String date) {
	if (date != null
		&& date.matches("[1-2][0-9]{3}-[0-9]?[0-9]-[0-9]?[0-9]")) {
	    String[] dates = date.split("-");
	    return LocalDateTime.of(Integer.parseInt(dates[0]),
		    Integer.parseInt(dates[1]), Integer.parseInt(dates[2]), 0,
		    0);
	} else {
	    return null;
	}
    }

    public static long stringToLong(String str) {
	if (str != null && str.matches("\\d+")) {
	    return Long.parseLong(str);
	} else {
	    return 0;
	}
    }

    public static int stringToInt(String str) {
	if (str != null && str.matches("\\d+")) {
	    return Integer.parseInt(str);
	} else {
	    return 0;
	}
    }
}
