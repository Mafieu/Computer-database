package com.excilys.malbert.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.dbConnection.ConnectionDbFactory;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.utils.Mapper;

public enum DAOCompany implements IDAOCompany {
    INSTANCE;

    @Override
    public List<Company> getAll() {
	Connection connection = ConnectionDbFactory.getConnection();
	PreparedStatement statement = null;
	ResultSet set = null;
	List<Company> companies = new ArrayList<Company>();

	try {
	    statement = connection.prepareStatement("SELECT * FROM company");
	    set = statement.executeQuery();
	    while (set.next()) {
		companies.add(Mapper.mapCompany(set));
	    }
	} catch (SQLException e) {
	    throw new DAOException("Couldn't get the list of companies");
	} finally {
	    ConnectionDbFactory.closeConnection(connection, statement, set);
	}

	return companies;
    }
}
