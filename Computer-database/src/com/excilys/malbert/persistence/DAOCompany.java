package com.excilys.malbert.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.dbConnection.ComputerDbConnection;
import com.excilys.malbert.persistence.model.Company;

/**
 * DAO for the company table
 * 
 * @author excilys
 */
public abstract class DAOCompany {

    /**
     * Gets a list of all the companies
     * 
     * @return The list of all the companies in database
     * @throws SQLException
     */
    public static List<Company> getAll() throws SQLException {
	Connection connection = ComputerDbConnection.getConnection();
	List<Company> companies = new ArrayList<Company>();
	Statement statement = connection.createStatement();
	ResultSet set = statement.executeQuery("SELECT * FROM company");
	while (set.next()) {
	    companies.add(new Company(set.getLong(1), set.getString(2)));
	}

	set.close();
	statement.close();
	connection.close();
	return companies;
    }

    /**
     * Gets a company
     * 
     * @param id
     *            Id of the company we will get
     * @return The company in database
     * @throws SQLException
     */
    public static Company getCompany(long id) throws SQLException {
	Connection connection = ComputerDbConnection.getConnection();
	Company company;
	Statement statement = connection.createStatement();
	ResultSet set = statement
		.executeQuery("SELECT * FROM company WHERE id = " + id);
	if (!set.next()) {
	    throw new SQLException("No company found with id = " + id);
	} else {
	    company = new Company(set.getLong(1), set.getString(2));
	}

	set.close();
	statement.close();
	connection.close();
	return company;
    }
}
