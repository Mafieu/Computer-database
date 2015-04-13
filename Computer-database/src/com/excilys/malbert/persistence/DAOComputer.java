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

/**
 * DAO for the computer table
 * 
 * @author excilys
 */
public abstract class DAOComputer {

    /**
     * Gets a list of all the computers
     * 
     * @return A list of all the computers in database
     * @throws SQLException
     */
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

    /**
     * Gets a computer
     * 
     * @param id
     *            Id of the computer we want to get
     * @return The computer in database
     * @throws SQLException
     */
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

    /**
     * Creates a computer
     * 
     * @param newComputer
     *            The computer to create
     * @throws SQLException
     */
    public static void create(Computer newComputer) throws SQLException {
	Connection connection = ComputerDbConnection.getConnection();
	PreparedStatement statement = connection
		.prepareStatement("INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?, ? , ?, ?)");
	statement.setString(1, newComputer.getName());
	statement.setTimestamp(2, newComputer.getIntroduced());
	statement.setTimestamp(3, newComputer.getDiscontinued());
	statement.setLong(4, newComputer.getIdCompany());
	statement.executeUpdate();
	connection.close();
    }

    /**
     * Deletes a computer
     * 
     * @param computer
     *            The computer to delete
     * @throws SQLException
     */
    public static void delete(Computer computer) throws SQLException {
	Connection connection = ComputerDbConnection.getConnection();
	Statement statement = connection.createStatement();
	statement.executeUpdate("DELETE FROM computer WHERE id = "
		+ computer.getId());
	connection.close();
    }

    /**
     * Updates a computer
     * 
     * @param oldComputer
     *            The computer to update
     * @param newComputer
     *            The computer updated
     * @throws SQLException
     */
    public static void update(Computer oldComputer, Computer newComputer)
	    throws SQLException {
	Connection connection = ComputerDbConnection.getConnection();
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
