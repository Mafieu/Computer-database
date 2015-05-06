package com.excilys.malbert.persistence.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import com.excilys.malbert.exceptions.ConnectionException;

/**
 * Singleton with a pool of connection
 * 
 * @author excilys
 */
@Component
public class ConnectionDbFactory {

    private Logger logger = LoggerFactory.getLogger(ConnectionDbFactory.class);

    @Autowired
    DataSource dataSource;

    /**
     * @return a connection from the connection pool
     */
    public Connection getConnection() {
	try {
	    return DataSourceUtils.doGetConnection(dataSource);
	} catch (SQLException e) {
	    throw new ConnectionException(
		    "Couldn't get a connection from the pool");
	}
    }

    /**
     * Used to close the statement and the resultSet
     * 
     * @param statement
     * @param resultSet
     */
    public void close(PreparedStatement statement, ResultSet resultSet) {
	if (resultSet != null) {
	    try {
		resultSet.close();
	    } catch (SQLException e) {
		logger.error("Couldn't close the result set");
		throw new ConnectionException("Couldn't close the result set");
	    }
	}
	if (statement != null) {
	    try {
		statement.close();
	    } catch (SQLException e) {
		logger.error("Couldn't close the statement");
		throw new ConnectionException("Couldn't close the statement");
	    }
	}
    }
}
