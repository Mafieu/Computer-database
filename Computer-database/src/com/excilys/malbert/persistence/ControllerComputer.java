package com.excilys.malbert.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;

public final class ControllerComputer extends AbstractController {

    public ControllerComputer() {
    }

    public List<Computer> getAll() {
	List<Computer> computers = new ArrayList<Computer>();
	try {
	    Statement statement = connection.createStatement();
	    ResultSet set = statement.executeQuery("SELECT * FROM computer");
	    while (set.next()) {
		computers
			.add(new Computer(set.getLong(1), set.getString(2), set
				.getTimestamp(3), set.getTimestamp(4), set
				.getInt(5)));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    System.err.println("Failed to get list of computers");
	}
	return computers;
    }

    public Computer getComputer(long id) {
	Computer computer = null;
	try {
	    Statement statement = connection.createStatement();
	    ResultSet set = statement
		    .executeQuery("SELECT * FROM computer WHERE id = " + id);
	    if (!set.next()) {
		throw new SQLException("No computer found with id = " + id);
	    } else {
		computer = new Computer(set.getLong(1), set.getString(2),
			set.getTimestamp(3), set.getTimestamp(4), set.getInt(5));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    System.err.println("Failed to get list of computers");
	}
	return computer;
    }

    public void create(String name, Timestamp introduced,
	    Timestamp discontinued, Company company) {
	try {
	    PreparedStatement statement = connection
		    .prepareStatement("INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?, ? , ?, ?)");
	    statement.setString(1, name);
	    statement.setTimestamp(2, introduced);
	    statement.setTimestamp(3, discontinued);
	    statement.setLong(4, company.getId());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	    System.err.println("Failed to create computer");
	}
    }

    public void delete(Computer computer) {

    }

    public boolean update(Computer computer, String name, Timestamp introduced,
	    Timestamp discontinued, Company company) {
	return false;
    }

}
