package com.excilys.malbert.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.persistence.model.Company;

public class ControllerCompanies extends AbstractController {

    public ControllerCompanies() {

    }

    public List<Company> getAll() {
	List<Company> companies = new ArrayList<Company>();
	try {
	    Statement statement = connection.createStatement();
	    ResultSet set = statement.executeQuery("SELECT * FROM company");
	    while (set.next()) {
		companies.add(new Company(set.getLong(1), set.getString(2)));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    System.err.println("Failed to get list of companies");
	}
	return companies;
    }
}
