package com.excilys.malbert.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ComputerDbConnection {

    private static Connection connection = null;

    public static Connection getConnection() {
	if (connection == null)
	    try {
		connection = DriverManager
			.getConnection(
				"jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull",
				"admincdb", "qwerty1234");
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	return connection;
    }
}
