package com.excilys.malbert.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.persistence.dbConnection.ConnectionDbFactory;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.utils.Mapper;
import com.excilys.malbert.utils.Utils;

public enum DAOComputer implements IDAOComputer {
    INSTANCE;

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

    public List<Computer> getSome(int limit, int offset) {
	Connection connection = ConnectionDbFactory.getConnection();
	PreparedStatement statement = null;
	ResultSet set = null;
	List<Computer> computers = new ArrayList<Computer>();

	try {
	    statement = connection
		    .prepareStatement("SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id LIMIT ? OFFSET ?");
	    statement.setInt(1, limit);
	    statement.setInt(2, offset);
	    set = statement.executeQuery();
	    while (set.next()) {
		computers.add(Mapper.mapComputer(set));
	    }
	} catch (SQLException e) {
	    throw new DAOException("Couldn't get the list of Computers from "
		    + offset + " to " + (offset + limit));
	} finally {
	    ConnectionDbFactory.closeConnection(connection, statement, set);
	}
	return computers;
    }

    public Computer getComputer(long id) {
	Connection connection = ConnectionDbFactory.getConnection();
	PreparedStatement statement = null;
	ResultSet set = null;
	Computer computer = null;

	try {
	    statement = connection
		    .prepareStatement("SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id WHERE computer.id = ?");
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

    public void update(Computer computer) {
	Connection connection = ConnectionDbFactory.getConnection();
	PreparedStatement statement = null;

	try {
	    statement = connection
		    .prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?");
	    statement.setString(1, computer.getName());
	    Utils.localdatetimeToTimestamp(computer.getIntroduced());
	    statement.setTimestamp(2,
		    Utils.localdatetimeToTimestamp(computer.getIntroduced()));
	    statement.setTimestamp(3,
		    Utils.localdatetimeToTimestamp(computer.getDiscontinued()));
	    if (computer.getCompany() != null
		    && computer.getCompany().getId() > 0) {
		statement.setLong(4, computer.getCompany().getId());
	    } else {
		statement.setNull(4, Types.BIGINT);
	    }
	    statement.setLong(5, computer.getId());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    throw new DAOException("Couldn't update the computer");
	} finally {
	    ConnectionDbFactory.closeConnection(connection, statement, null);
	}
    }

    @Override
    public int getNumberComputer() {
	Connection connection = ConnectionDbFactory.getConnection();
	PreparedStatement statement = null;
	ResultSet set = null;
	int nb = 0;

	try {
	    statement = connection
		    .prepareStatement("SELECT count(*) FROM computer");
	    set = statement.executeQuery();
	    set.next();
	    nb = set.getInt(1);
	} catch (SQLException e) {
	    throw new DAOException("Couldn't get the number of computers");
	} finally {
	    ConnectionDbFactory.closeConnection(connection, statement, null);
	}
	return nb;
    }

}
