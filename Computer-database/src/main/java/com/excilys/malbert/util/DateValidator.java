package com.excilys.malbert.util;

public final class DateValidator {

    private static final org.apache.commons.validator.routines.DateValidator validator = org.apache.commons.validator.routines.DateValidator
	    .getInstance();

    private DateValidator() {
    }

    public static boolean isThisDateValid(String dateToValidate,
	    String dateFormat) {
	if (dateToValidate == null || dateToValidate.trim().isEmpty()) {
	    return false;
	}

	return validator.isValid(dateToValidate, dateFormat);
    }
}