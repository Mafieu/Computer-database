package com.excilys.malbert.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class DateValidator {
    private DateValidator() {
    }

    public static boolean isThisDateValid(String dateToValidate,
	    String dateFromat) {
	if (dateToValidate == null) {
	    return false;
	}

	SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
	sdf.setLenient(false);

	try {
	    // if not valid, it will throw ParseException
	    sdf.parse(dateToValidate);
	} catch (ParseException e) {
	    return false;
	}

	return true;
    }
}