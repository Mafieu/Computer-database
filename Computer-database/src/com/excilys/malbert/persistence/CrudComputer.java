package com.excilys.malbert.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.persistence.model.Entity;

public final class CrudComputer extends ICrud {

    @Override
    public List<Entity> getAll() {
	List<Entity> computers = new ArrayList<Entity>();
	try {
	    Statement statement = connection.createStatement();
	    ResultSet set = statement.executeQuery("SELECT * FROM computer");
	    while (set.next()) {
		computers.add(new Computer(set.getLong(1), set.getString(2),
			set.getTimestamp(3), set.getTimestamp(4), null));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    System.err.println("Failed to get list of computers");
	}
	return computers;
    }

    public Computer getComputer(long id) {
	return null;
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
