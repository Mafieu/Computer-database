package com.excilys.malbert.validator;

import java.time.LocalDateTime;
import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.context.i18n.LocaleContextHolder;

import com.excilys.malbert.util.Utils;
import com.excilys.malbert.validator.Date.Pattern;

public class DateValidator implements ConstraintValidator<Date, String> {
    private final org.apache.commons.validator.routines.DateValidator validator = org.apache.commons.validator.routines.DateValidator
	    .getInstance();
    private Pattern dateFormat;

    @Override
    public void initialize(Date annotation) {
	Locale locale = LocaleContextHolder.getLocale();
	Pattern language = Pattern.map(locale.getLanguage());
	dateFormat = language;
    }

    @Override
    public boolean isValid(String dateToValidate,
	    ConstraintValidatorContext arg1) {
	if (dateToValidate == null || dateToValidate.isEmpty()) {
	    return true;
	}
	if (dateToValidate.trim().isEmpty()) {
	    return false;
	}
	if (!dateToValidate.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}"))
	    return false;

	if (validator.isValid(dateToValidate, dateFormat.toString())) {
	    LocalDateTime ldt = Utils.stringToLocaldatetime(dateToValidate,
		    dateFormat);
	    if (ldt.getYear() < 1970 || ldt.getYear() >= 2038)
		return false;
	    return true;
	} else {
	    return false;
	}
    }

    public boolean isValid(String date, Pattern pattern) {
	if (date == null || date.isEmpty()) {
	    return true;
	}
	if (date.trim().isEmpty()) {
	    return false;
	}

	return validator.isValid(date, pattern.toString());
    }
}