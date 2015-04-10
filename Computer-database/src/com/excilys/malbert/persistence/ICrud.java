package com.excilys.malbert.persistence;

import java.sql.Connection;
import java.util.List;

import com.excilys.malbert.dbConnection.ComputerDbConnection;
import com.excilys.malbert.persistence.model.Entity;

public abstract class ICrud {
    protected Connection connection;

    protected ICrud() {
	connection = ComputerDbConnection.getConnection();
    }

    public List<Entity> getAll() {
	return null;
    }

    public void create() {
    }

    public void delete() {
    }

    public void update() {
    }
}
