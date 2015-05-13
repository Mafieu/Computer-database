package com.excilys.malbert.validator;

import com.excilys.malbert.persistence.model.Computer;

public final class DbValidator {
    public static final String INVALID_LIMIT_OFFSET = "Invalid limit and offset";
    public static final String INVALID_COLUMN = "Invalid column name";
    public static final String INVALID_COMPUTER = "Invalid computer";
    public static final String INVALID_ID = "Invalid id";

    private DbValidator() {
    }

    public static boolean isLimitOffsetCorrect(Integer limit, Integer offset) {
	if (limit == null || offset == null) {
	    return false;
	} else if (limit <= 0 || offset < 0) {
	    return false;
	} else {
	    return true;
	}
    }

    public static boolean isComputerValid(Computer computer) {
	if (computer == null) {
	    return false;
	} else if (computer.getName() == null) {
	    return false;
	} else if (computer.getName().trim().equals("")) {
	    return false;
	} else {
	    return true;
	}
    }

    public static boolean isIdValid(Long id) {
	if (id == null) {
	    return false;
	} else if (id <= 0L) {
	    return false;
	} else {
	    return true;
	}
    }
}
