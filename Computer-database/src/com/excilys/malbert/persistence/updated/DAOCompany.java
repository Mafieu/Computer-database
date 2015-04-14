package com.excilys.malbert.persistence.updated;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.dbConnection.updated.ConnectionDbFactory;
import com.excilys.malbert.persistence.model.Company;

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
		companies.add(new Company(set.getLong(1), set.getString(2)));
	    }
	} catch (SQLException e) {

	} finally {
	    ConnectionDbFactory.closeConnection(connection, statement, set);
	}

	return companies;
    }
}
