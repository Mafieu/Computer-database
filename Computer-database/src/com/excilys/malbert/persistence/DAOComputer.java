package com.excilys.malbert.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.dbConnection.ConnectionDbFactory;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.utils.Mapper;
import com.excilys.malbert.utils.Utils;

public enum DAOComputer implements IDAOComputer {
    INSTANCE;

    @Override
    public List<Computer> getAll() {
	Connection connection = ConnectionDbFactory.getConnection();
	PreparedStatement statement = null;
	ResultSet set = null;
	List<Computer> computers = new ArrayList<Computer>();

	try {
	    statement = connection
		    .prepareStatement("SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id");
	    set = statement.executeQuery();
	    while (set.next()) {
		computers.add(Mapper.mapComputer(set));
	    }
	} catch (SQLException e) {
	    throw new DAOException("Couldn't get the list of Computers");
	} finally {
	    ConnectionDbFactory.closeConnection(connection, statement, set);
	}
	return computers;
    }

    @Override
    public Computer getComputer(long id) {
	Connection connection = ConnectionDbFactory.getConnection();
	PreparedStatement statement = null;
	ResultSet set = null;
	Computer computer = null;

	try {
	    statement = connection
		    .prepareStatement("SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id WHERE id = ?");
	    statement.setLong(1, id);
	    set = statement.executeQuery();
	    if (!set.next()) {
		throw new DAOException("No computer found with id " + id);
	    } else {
		computer = Mapper.mapComputer(set);
	    }
	} catch (SQLException e) {
	    throw new DAOException("Couldn't get the computer " + id);
	} finally {
	    ConnectionDbFactory.closeConnection(connection, statement, set);
	}

	return computer;
    }

    @Override
    public void create(Computer computer) {
	Connection connection = ConnectionDbFactory.getConnection();
	PreparedStatement statement = null;

	try {
	    statement = connection
		    .prepareStatement("INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?, ? , ?, ?)");
	    statement.setString(1, computer.getName());
	    statement.setTimestamp(2,
		    Utils.localdatetimeToTimestamp(computer.getIntroduced()));
	    statement.setTimestamp(3,
		    Utils.localdatetimeToTimestamp(computer.getDiscontinued()));
	    if (computer.getCompany().getId() <= 0) {
		statement.setNull(4, Types.BIGINT);
	    } else {
		statement.setLong(4, computer.getCompany().getId());
	    }
	    statement.executeUpdate();
	} catch (SQLException e) {
	    throw new DAOException("Couldn't create the computer");
	} finally {
	    ConnectionDbFactory.closeConnection(connection, statement, null);
	}
    }

    @Override
    public void delete(Computer computer) {
	Connection connection = ConnectionDbFactory.getConnection();
	PreparedStatement statement = null;

	try {
	    statement = connection
		    .prepareStatement("DELETE FROM computer WHERE id = ?");
	    statement.setLong(1, computer.getId());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    throw new DAOException("Couldn't delete the computer");
	} finally {
	    ConnectionDbFactory.closeConnection(connection, statement, null);
	}
    }

    @Override
    public void update(Computer oldComputer, Computer newComputer) {
	Connection connection = ConnectionDbFactory.getConnection();
	PreparedStatement statement = null;

	try {
	    statement = connection
		    .prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?");
	    statement.setString(1, newComputer.getName());
	    Utils.localdatetimeToTimestamp(newComputer.getIntroduced());
	    statement
		    .setTimestamp(2, Utils.localdatetimeToTimestamp(newComputer
			    .getIntroduced()));
	    statement.setTimestamp(3, Utils
		    .localdatetimeToTimestamp(newComputer.getDiscontinued()));
	    if (newComputer.getCompany().getId() <= 0) {
		statement.setNull(4, Types.BIGINT);
	    } else {
		statement.setLong(4, newComputer.getCompany().getId());
	    }
	    statement.setLong(5, oldComputer.getId());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    throw new DAOException("Couldn't update the computer");
	} finally {
	    ConnectionDbFactory.closeConnection(connection, statement, null);
	}
    }

}
