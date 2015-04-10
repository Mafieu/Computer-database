package com.excilys.malbert.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	    // e.printStackTrace();
	    System.err.println("Failed to get some computers");
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
	    // e.printStackTrace();
	    System.err.println("Failed to get list of computers");
	}
	return computer;
    }

    public void create(Computer newComputer) {
	try {
	    PreparedStatement statement = connection
		    .prepareStatement("INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?, ? , ?, ?)");
	    statement.setString(1, newComputer.getName());
	    statement.setTimestamp(2, newComputer.getIntroduced());
	    statement.setTimestamp(3, newComputer.getDiscontinued());
	    statement.setLong(4, newComputer.getIdCompany());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    // e.printStackTrace();
	    System.err.println("Failed to create computer");
	}
    }

    public void delete(Computer computer) {
	try {
	    PreparedStatement statement = connection
		    .prepareStatement("DELETE FROM computer WHERE id = "
			    + computer.getId());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    // e.printStackTrace();
	    System.err.println("Failed to suppress the computer id : "
		    + computer.getId());
	}
    }

    public void update(Computer oldComputer, Computer newComputer) {
	try {
	    Statement statement = connection.createStatement(
		    ResultSet.TYPE_SCROLL_INSENSITIVE,
		    ResultSet.CONCUR_UPDATABLE);
	    ResultSet set = statement
		    .executeQuery("SELECT * FROM computer WHERE id = "
			    + oldComputer.getId());
	    if (!set.next()) {
		throw new SQLException("Computer not found for update, id : "
			+ oldComputer.getId());
	    } else {
		set.updateString(2, newComputer.getName());
		set.updateTimestamp(3, newComputer.getIntroduced());
		set.updateTimestamp(4, newComputer.getDiscontinued());
		set.updateLong(5, newComputer.getIdCompany());
		set.updateRow();
	    }
	} catch (SQLException e) {
	    // e.printStackTrace();
	    System.err.println("Failed to update commputer id : "
		    + oldComputer.getId());
	}
    }
}
