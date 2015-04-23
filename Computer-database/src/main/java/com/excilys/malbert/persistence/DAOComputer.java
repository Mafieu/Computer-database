package com.excilys.malbert.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.exceptions.DAOException;
import com.excilys.malbert.mapper.MapperComputer;
import com.excilys.malbert.persistence.dbConnection.ConnectionDbFactory;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.util.Utils;
import com.excilys.malbert.util.Validator;

public enum DAOComputer implements IDAOComputer {
    INSTANCE;

    @Override
    public List<Computer> getAll() {
	Connection connection = ConnectionDbFactory.INSTANCE.getConnection();
	PreparedStatement statement = null;
	ResultSet set = null;
	List<Computer> computers = new ArrayList<Computer>();

	try {
	    statement = connection
		    .prepareStatement("SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id");
	    set = statement.executeQuery();
	    while (set.next()) {
		computers.add(MapperComputer.resultsetToComputer(set));
	    }
	} catch (SQLException e) {
	    throw new DAOException("Couldn't get the list of Computers");
	} finally {
	    ConnectionDbFactory.INSTANCE.closeConnection(connection, statement,
		    set);
	}
	return computers;
    }

    @Override
    public List<Computer> getSome(int limit, int offset) {
	return getSomeOrderedByAscending(limit, offset, "computer.id");
    }

    @Override
    public Computer getComputer(long id) {
	Connection connection = ConnectionDbFactory.INSTANCE.getConnection();
	PreparedStatement statement = null;
	ResultSet set = null;
	Computer computer = null;

	if (!Validator.isIdValid(id)) {
	    throw new DAOException(Validator.INVALID_ID);
	}

	try {
	    statement = connection
		    .prepareStatement("SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id WHERE computer.id = ?");
	    statement.setLong(1, id);
	    set = statement.executeQuery();
	    if (!set.next()) {
		throw new DAOException("No computer found with id " + id);
	    } else {
		computer = MapperComputer.resultsetToComputer(set);
	    }
	} catch (SQLException e) {
	    throw new DAOException("Couldn't get the computer " + id);
	} finally {
	    ConnectionDbFactory.INSTANCE.closeConnection(connection, statement,
		    set);
	}

	return computer;
    }

    @SuppressWarnings("static-access")
    @Override
    public long create(Computer computer) {
	Connection connection = ConnectionDbFactory.INSTANCE.getConnection();
	PreparedStatement statement = null;
	ResultSet set = null;
	long i = 0;

	if (!Validator.isComputerValid(computer)) {
	    throw new DAOException(Validator.INVALID_COMPUTER);
	}

	try {
	    statement = connection
		    .prepareStatement(
			    "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?, ? , ?, ?)",
			    statement.RETURN_GENERATED_KEYS);
	    statement.setString(1, computer.getName());
	    statement.setTimestamp(2,
		    Utils.localdatetimeToTimestamp(computer.getIntroduced()));
	    statement.setTimestamp(3,
		    Utils.localdatetimeToTimestamp(computer.getDiscontinued()));
	    statement.setLong(4, computer.getCompany().getId());
	    statement.executeUpdate();
	    set = statement.getGeneratedKeys();
	    set.next();
	    i = set.getLong(1);
	} catch (SQLException e) {
	    throw new DAOException("Couldn't create the computer");
	} finally {
	    ConnectionDbFactory.INSTANCE.closeConnection(connection, statement,
		    null);
	}
	return i;
    }

    @Override
    public void delete(long id, Connection connection) {
	PreparedStatement statement = null;

	if (!Validator.isIdValid(id)) {
	    throw new DAOException(Validator.INVALID_ID);
	}

	try {
	    statement = connection
		    .prepareStatement("DELETE FROM computer WHERE id = ?");
	    statement.setLong(1, id);
	    statement.executeUpdate();
	} catch (SQLException e) {
	    throw new DAOException("Couldn't delete the computer");
	} finally {
	    ConnectionDbFactory.INSTANCE.closeConnection(null, statement, null);
	}
    }

    @Override
    public void delete(long id) {
	Connection connection = ConnectionDbFactory.INSTANCE.getConnection();
	delete(id, connection);
	ConnectionDbFactory.INSTANCE.closeConnection(connection, null, null);
    }

    @Override
    public void update(Computer computer) {
	Connection connection = ConnectionDbFactory.INSTANCE.getConnection();
	PreparedStatement statement = null;

	if (!Validator.isComputerValid(computer)) {
	    throw new DAOException(Validator.INVALID_COMPUTER);
	}

	try {
	    statement = connection
		    .prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?");
	    statement.setString(1, computer.getName());
	    Utils.localdatetimeToTimestamp(computer.getIntroduced());
	    statement.setTimestamp(2,
		    Utils.localdatetimeToTimestamp(computer.getIntroduced()));
	    statement.setTimestamp(3,
		    Utils.localdatetimeToTimestamp(computer.getDiscontinued()));
	    statement.setLong(4, computer.getCompany().getId());
	    statement.setLong(5, computer.getId());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    throw new DAOException("Couldn't update the computer");
	} finally {
	    ConnectionDbFactory.INSTANCE.closeConnection(connection, statement,
		    null);
	}
    }

    @Override
    public int getNumberComputer() {
	Connection connection = ConnectionDbFactory.INSTANCE.getConnection();
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
	    ConnectionDbFactory.INSTANCE.closeConnection(connection, statement,
		    null);
	}
	return nb;
    }

    @Override
    public List<Computer> getSomeOrderedByAscending(int limit, int offset,
	    String column) {
	return getSomeOrderBy(limit, offset, column, "ASC");
    }

    @Override
    public List<Computer> getSomeOrderedByDescending(int limit, int offset,
	    String column) {
	return getSomeOrderBy(limit, offset, column, "DESC");
    }

    private List<Computer> getSomeOrderBy(int limit, int offset, String column,
	    String order) {
	Connection connection = ConnectionDbFactory.INSTANCE.getConnection();
	PreparedStatement statement = null;
	ResultSet set = null;
	List<Computer> computers = new ArrayList<Computer>();

	if (!Validator.isLimitOffsetCorrect(limit, offset)) {
	    throw new DAOException(Validator.INVALID_LIMIT_OFFSET);
	}
	if (!Validator.isColumnValid(column)) {
	    throw new DAOException(Validator.INVALID_COLUMN);
	}

	try {
	    statement = connection
		    .prepareStatement("SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id ORDER BY "
			    + column + " " + order + " LIMIT ? OFFSET ?");
	    statement.setInt(1, limit);
	    statement.setInt(2, offset);
	    set = statement.executeQuery();
	    while (set.next()) {
		computers.add(MapperComputer.resultsetToComputer(set));
	    }
	} catch (SQLException e) {
	    throw new DAOException("Couldn't get the list of Computers from "
		    + offset + " to " + (offset + limit) + " ordered by "
		    + column);
	} finally {
	    ConnectionDbFactory.INSTANCE.closeConnection(connection, statement,
		    set);
	}
	return computers;
    }

    @Override
    public List<Computer> getOfCompany(long id) {
	Connection connection = ConnectionDbFactory.INSTANCE.getConnection();
	PreparedStatement statement = null;
	ResultSet set = null;
	List<Computer> computers = new ArrayList<Computer>();

	if (!Validator.isIdValid(id)) {
	    throw new DAOException(Validator.INVALID_ID);
	}

	try {
	    statement = connection
		    .prepareStatement("SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id WHERE company.id = ?");
	    statement.setLong(1, id);
	    set = statement.executeQuery();
	    while (set.next()) {
		computers.add(MapperComputer.resultsetToComputer(set));
	    }
	} catch (SQLException e) {
	    throw new DAOException(
		    "Couldn't get the list of Computers of the company " + id);
	} finally {
	    ConnectionDbFactory.INSTANCE.closeConnection(connection, statement,
		    set);
	}
	return computers;
    }
}
