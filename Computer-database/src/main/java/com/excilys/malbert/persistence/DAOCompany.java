package com.excilys.malbert.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.exceptions.DAOException;
import com.excilys.malbert.mapper.MapperCompany;
import com.excilys.malbert.persistence.dbConnection.ConnectionDbFactory;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.util.Validator;

public enum DAOCompany implements IDAOCompany {
    INSTANCE;

    @Override
    public List<Company> getAll() {
	Connection connection = ConnectionDbFactory.INSTANCE.getConnection();
	PreparedStatement statement = null;
	ResultSet set = null;
	List<Company> companies = new ArrayList<Company>();

	try {
	    statement = connection.prepareStatement("SELECT * FROM company");
	    set = statement.executeQuery();
	    while (set.next()) {
		companies.add(MapperCompany.resultsetToCompany(set));
	    }
	} catch (SQLException e) {
	    throw new DAOException("Couldn't get the list of companies");
	} finally {
	    ConnectionDbFactory.INSTANCE.close(statement, set);
	    ConnectionDbFactory.INSTANCE.closeConnection();
	}

	return companies;
    }

    @Override
    public Company getCompany(long id) {
	Connection connection = ConnectionDbFactory.INSTANCE.getConnection();
	PreparedStatement statement = null;
	ResultSet set = null;
	Company company = null;

	if (!Validator.isIdValid(id)) {
	    throw new DAOException(Validator.INVALID_ID);
	}

	try {
	    statement = connection
		    .prepareStatement("SELECT * FROM company WHERE id = ?");
	    statement.setLong(1, id);
	    set = statement.executeQuery();
	    if (!set.next()) {
		throw new DAOException("No computer found with id " + id);
	    } else {
		company = MapperCompany.resultsetToCompany(set);
	    }
	} catch (SQLException e) {
	    throw new DAOException("Couldn't get the company " + id);
	} finally {
	    ConnectionDbFactory.INSTANCE.close(statement, set);
	    ConnectionDbFactory.INSTANCE.closeConnection();
	}

	return company;
    }

    @Override
    public void delete(long id) {
	Connection connection = ConnectionDbFactory.INSTANCE.getConnection();
	PreparedStatement statement = null;

	if (!Validator.isIdValid(id)) {
	    throw new DAOException(Validator.INVALID_ID);
	}

	try {
	    statement = connection
		    .prepareStatement("DELETE FROM company WHERE id = ?");
	    statement.setLong(1, id);
	    statement.executeUpdate();
	} catch (SQLException e) {
	    throw new DAOException("Couldn't delete the company");
	} finally {
	    ConnectionDbFactory.INSTANCE.close(statement, null);
	}
    }
}
