package com.excilys.malbert.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.dbConnection.ComputerDbConnection;
import com.excilys.malbert.persistence.model.Company;

public abstract class DAOCompany {

    public static List<Company> getAll() throws SQLException {
	Connection connection = ComputerDbConnection.getConnection();
	List<Company> companies = new ArrayList<Company>();
	Statement statement = connection.createStatement();
	ResultSet set = statement.executeQuery("SELECT * FROM company");
	while (set.next()) {
	    companies.add(new Company(set.getLong(1), set.getString(2)));
	}
	connection.close();
	return companies;
    }

    public static Company getCompany(long id) throws SQLException {
	Connection connection = ComputerDbConnection.getConnection();
	Company company;
	Statement statement = connection.createStatement();
	ResultSet set = statement
		.executeQuery("SELECT * FROM company WHERE id = " + id);
	if (!set.next()) {
	    throw new SQLException("No computer found with id = " + id);
	} else {
	    company = new Company(set.getLong(1), set.getString(2));
	}
	connection.close();
	return company;
    }
}
