package com.excilys.malbert.persistence;

import java.sql.Connection;

import com.excilys.malbert.dbConnection.ComputerDbConnection;

/* THIS IS A DAO */
public abstract class AbstractDAO {
    protected Connection connection;

    protected AbstractDAO() {
	connection = ComputerDbConnection.getConnection();
    }
}
