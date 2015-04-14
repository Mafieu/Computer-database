package com.excilys.malbert.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

public abstract class Utils {

    public static LocalDateTime timestampToLocaldatetime(Timestamp time) {
	if (time == null) {
	    return null;
	} else {
	    return time.toLocalDateTime();
	}
    }

    public static Timestamp localdatetimeToTimestamp(LocalDateTime time) {
	if (time == null) {
	    return null;
	} else {
	    ZoneId zone = ZoneId.systemDefault();
	    return new Timestamp(time.atZone(zone).toEpochSecond() * 1000);
	}
    }
}
