package com.excilys.malbert.util;

import com.excilys.malbert.persistence.model.Computer;

public final class Validator {
    public static final String INVALID_LIMIT_OFFSET = "Invalid limit and offset";
    public static final String INVALID_COLUMN = "Invalid column name";
    public static final String INVALID_COMPUTER = "Invalid computer";
    public static final String INVALID_ID = "Invalid id";

    private Validator() {
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

    public static boolean isColumnValid(String column) {
	if (column == null) {
	    return false;
	} else if (!(column.equals("computer.id")
		|| column.equals("computer.name")
		|| column.equals("introduced") || column.equals("discontinued")
		|| column.equals("company.id") || column.equals("company.name"))) {
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
	} else if (computer.getCompany() == null) {
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
