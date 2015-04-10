package com.excilys.malbert.persistence;

import java.sql.Connection;

import com.excilys.malbert.dbConnection.ComputerDbConnection;

/* THIS IS A DAO */
public abstract class AbstractController {
    protected Connection connection;

    protected AbstractController() {
	connection = ComputerDbConnection.getConnection();
    }
}
