package com.excilys.malbert.dbConnection.updated;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ConnectionDbFactory {

    private final static String DB = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
    private final static String USER = "admincdb";
    private final static String PASSWD = "qwerty1234";

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
	    connection = DriverManager.getConnection(DB, USER, PASSWD);
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
