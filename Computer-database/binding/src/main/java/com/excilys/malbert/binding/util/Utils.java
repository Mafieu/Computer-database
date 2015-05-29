package com.excilys.malbert.binding.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.excilys.malbert.binding.validator.Date.Pattern;
import com.excilys.malbert.binding.validator.DateValidator;

/**
 * Contains some useful functions
 * 
 * @author excilys
 */
public final class Utils {
	private Utils() {
	}

	public static LocalDateTime stringToLocaldatetime(String date,
			Pattern pattern) {
		if (date == null) {
			return null;
		}
		if (date.trim().isEmpty()) {
			return null;
		}
		if (!(new DateValidator().isValid(date, pattern))) {
			return null;
		}
		if (pattern == null) {
			pattern = Pattern.EN;
		}
		return LocalDateTime.of(
				LocalDate.parse(date,
						DateTimeFormatter.ofPattern(pattern.toString())),
				LocalTime.of(0, 0));
	}

	public static String localdatetimeToString(LocalDateTime date,
			Pattern pattern) {
		if (date == null) {
			return null;
		} else {
			if (pattern == null) {
				pattern = Pattern.EN;
			}
			return date.format(DateTimeFormatter.ofPattern(pattern.toString()));
		}
	}

	public static Long stringToLong(String str) {
		if (str != null && str.matches("\\d+")) {
			return Long.parseLong(str);
		} else {
			return null;
		}
	}

	public static Integer stringToInt(String str) {
		if (str != null && str.matches("\\d+")) {
			return Integer.parseInt(str);
		} else {
			return null;
		}
	}
}
