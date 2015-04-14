package com.excilys.malbert.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.malbert.persistence.DAOException;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;

public abstract class Mapper {
    public static Computer mapComputer(ResultSet set) {
	try {
	    return new Computer(set.getLong(1), set.getString(2),
		    Utils.timestampToLocaldatetime(set.getTimestamp(3)),
		    Utils.timestampToLocaldatetime(set.getTimestamp(4)),
		    new Company(set.getLong(5), set.getString(7)));
	} catch (SQLException e) {
	    throw new DAOException("Couldn't parse bdd->computer");
	}
    }

    public static Company mapCompany(ResultSet set) {
	try {
	    return new Company(set.getLong(1), set.getString(2));
	} catch (SQLException e) {
	    throw new DAOException("Couldn't parse bdd->computer");
	}
    }
}
