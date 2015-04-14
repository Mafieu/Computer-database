package com.excilys.malbert.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton to get a connection to the database
 * 
 * @author excilys
 */
public abstract class ComputerDbConnection {

    private static Connection connection = null;
    private final static String DB = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
    private final static String USER = "admincdb";
    private final static String PASSWD = "qwerty1234";

    /**
     * @return A connection to the database
     */
    public static Connection getConnection() {
	try {
	    if (connection == null || connection.isClosed()) {
		connection = DriverManager.getConnection(DB, USER, PASSWD);
	    }
	} catch (SQLException e) {
	    System.err.println("Connection failed to database");
	}
	return connection;
    }
}
