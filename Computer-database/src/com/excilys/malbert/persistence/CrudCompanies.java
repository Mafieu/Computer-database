package com.excilys.malbert.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Entity;

public class CrudCompanies extends ICrud {

    @Override
    public List<Entity> getAll() {
	List<Entity> companies = new ArrayList<Entity>();
	try {
	    Statement statement = connection.createStatement();
	    ResultSet set = statement.executeQuery("SELECT * FROM company");
	    while (set.next()) {
		companies.add(new Company(set.getLong(1), set.getString(2)));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    System.err.println("Failed to get list of companies");
	}
	return companies;
    }

    @Override
    public void create() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void update() {

    }

}
