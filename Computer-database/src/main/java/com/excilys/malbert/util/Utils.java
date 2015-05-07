package com.excilys.malbert.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class Utils {
    private Utils() {
    }

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

    public static LocalDateTime stringToLocaldatetime(String date) {
	if (DateValidator.isThisDateValid(date, "dd-MM-YYYY")) {
	    String[] dates = date.split("-");
	    return LocalDateTime.of(Integer.parseInt(dates[2]),
		    Integer.parseInt(dates[1]), Integer.parseInt(dates[0]), 0,
		    0);
	} else if (DateValidator.isThisDateValid(date, "dd/MM/YYYY")) {
	    String[] dates = date.split("/");
	    return LocalDateTime.of(Integer.parseInt(dates[2]),
		    Integer.parseInt(dates[1]), Integer.parseInt(dates[0]), 0,
		    0);
	} else {
	    return null;
	}
    }

    public static String localdatetimeToString(LocalDateTime date) {
	if (date == null) {
	    return null;
	} else {
	    StringBuilder builder = new StringBuilder();
	    builder.append(date.getDayOfMonth());
	    builder.append("-");
	    if (date.getMonthValue() < 10) {
		builder.append("0");
	    }
	    builder.append(date.getMonthValue());
	    builder.append("-");
	    if (date.getDayOfMonth() < 10) {
		builder.append("0");
	    }
	    builder.append(date.getYear());
	    return builder.toString();
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
