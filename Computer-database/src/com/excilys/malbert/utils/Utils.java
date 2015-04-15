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
}
