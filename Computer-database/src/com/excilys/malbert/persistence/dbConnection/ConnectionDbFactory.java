package com.excilys.malbert.persistence.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ConnectionDbFactory {

    private final static String URL = "jdbc:mysql://localhost:3306/";
    private final static String DB = "computer-database-db";
    private final static String DB_TEST = "computer-database-db-test";
    private final static String OPTION = "?zeroDateTimeBehavior=convertToNull";
    private final static String USER = "admincdb";
    private final static String PASSWD = "qwerty1234";
    public static boolean TESTING = false;

    static {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
    };

    public static Connection getConnection() {
	Connection connection = null;
	try {
	    connection = DriverManager.getConnection(URL
		    + (TESTING ? DB_TEST : DB) + OPTION, USER, PASSWD);
	} catch (SQLException e) {
	    // Throw ConnectionException
	}
	return connection;
    }

    public static void closeConnection(Connection connection,
	    PreparedStatement statement, ResultSet resultSet) {
	if (resultSet != null) {
	    try {
		resultSet.close();
	    } catch (SQLException e) {
		// Throw ConnectionException
	    }
	}
	if (statement != null) {
	    try {
		statement.close();
	    } catch (SQLException e) {
		// Throw ConnectionException
	    }
	}
	if (connection != null) {
	    try {
		connection.close();
	    } catch (SQLException e) {
		// Throw ConnectionException
	    }
	}
    }
}
