package com.excilys.malbert.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.persistence.model.Company;

public class DAOCompanies extends AbstractDAO {

    public DAOCompanies() {

    }

    public List<Company> getAll() throws SQLException {
	List<Company> companies = new ArrayList<Company>();
	Statement statement = connection.createStatement();
	ResultSet set = statement.executeQuery("SELECT * FROM company");
	while (set.next()) {
	    companies.add(new Company(set.getLong(1), set.getString(2)));
	}
	return companies;
    }

    public Company getCompany(long id) throws SQLException {
	Company company;
	Statement statement = connection.createStatement();
	ResultSet set = statement
		.executeQuery("SELECT * FROM company WHERE id = " + id);
	if (!set.next()) {
	    throw new SQLException("No computer found with id = " + id);
	} else {
	    company = new Company(set.getLong(1), set.getString(2));
	}
	return company;
    }
}
