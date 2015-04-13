package com.excilys.malbert.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.dbConnection.ComputerDbConnection;
import com.excilys.malbert.persistence.model.Computer;

public abstract class DAOComputer {

    public static List<Computer> getAll() throws SQLException {
	Connection connection = ComputerDbConnection.getConnection();
	List<Computer> computers = new ArrayList<Computer>();
	Statement statement = connection.createStatement();
	ResultSet set = statement.executeQuery("SELECT * FROM computer");
	while (set.next()) {
	    computers.add(new Computer(set.getLong(1), set.getString(2), set
		    .getTimestamp(3), set.getTimestamp(4), set.getInt(5)));
	}
	connection.close();
	return computers;
    }

    public static Computer getComputer(long id) throws SQLException {
	Connection connection = ComputerDbConnection.getConnection();
	Computer computer;
	Statement statement = connection.createStatement();
	ResultSet set = statement
		.executeQuery("SELECT * FROM computer WHERE id = " + id);
	if (!set.next()) {
	    throw new SQLException("No computer found with id = " + id);
	} else {
	    computer = new Computer(set.getLong(1), set.getString(2),
		    set.getTimestamp(3), set.getTimestamp(4), set.getInt(5));
	}
	connection.close();
	return computer;
    }

    public static void create(Computer newComputer) throws SQLException {
	Connection connection = ComputerDbConnection.getConnection();
	// Check if company exists
	PreparedStatement statement = connection
		.prepareStatement("INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?, ? , ?, ?)");
	statement.setString(1, newComputer.getName());
	statement.setTimestamp(2, newComputer.getIntroduced());
	statement.setTimestamp(3, newComputer.getDiscontinued());
	statement.setLong(4, newComputer.getIdCompany());
	statement.executeUpdate();
	connection.close();
    }

    public static void delete(Computer computer) throws SQLException {
	Connection connection = ComputerDbConnection.getConnection();
	Statement statement = connection.createStatement();
	statement.executeUpdate("DELETE FROM computer WHERE id = "
		+ computer.getId());
	connection.close();
    }

    public static void update(Computer oldComputer, Computer newComputer)
	    throws SQLException {
	Connection connection = ComputerDbConnection.getConnection();
	// Check if company exists
	PreparedStatement statement = connection
		.prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?");
	statement.setString(1, newComputer.getName());
	statement.setTimestamp(2, newComputer.getIntroduced());
	statement.setTimestamp(3, newComputer.getDiscontinued());
	statement.setLong(4, newComputer.getIdCompany());
	statement.setLong(5, oldComputer.getId());
	statement.executeUpdate();
	connection.close();
    }
}
