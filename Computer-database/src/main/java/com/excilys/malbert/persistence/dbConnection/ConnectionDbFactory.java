package com.excilys.malbert.persistence.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.malbert.exceptions.ConnectionException;

/**
 * Singleton with a pool of connection
 * 
 * @author excilys
 */
@Component
public class ConnectionDbFactory {

    private ThreadLocal<Connection> connection = new ThreadLocal<Connection>();

    private Logger logger = LoggerFactory.getLogger(ConnectionDbFactory.class);

    @Autowired
    DataSource dataSource;

    /**
     * @return a connection from the connection pool
     */
    public Connection getConnection() {
	try {
	    if (connection.get() == null || connection.get().isClosed()) {
		Connection connection = null;

		connection = dataSource.getConnection();
		this.connection.set(connection);
	    }
	} catch (SQLException e) {
	    logger.error("Couldn't get a connection from the connection pool");
	    throw new ConnectionException(
		    "Couldn't get a connection from the connection pool");
	}
	return this.connection.get();
    }

    public void startTransaction() {
	try {
	    connection.get().setAutoCommit(false);
	} catch (SQLException e) {
	    logger.error("Couldn't start a transaction");
	    throw new ConnectionException("Couldn't start a transaction");
	}
    }

    public void commit() {
	try {
	    connection.get().commit();
	} catch (SQLException e) {
	    logger.error("Couldn't commit");
	    throw new ConnectionException("Couldn't commit");
	}
    }

    public void rollback() {
	try {
	    connection.get().rollback();
	} catch (SQLException e) {
	    logger.error("Couldn't rollback");
	    throw new ConnectionException("Couldn't rollback");
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

    public void closeConnection() {
	if (connection != null && connection.get() != null) {
	    try {
		connection.get().close();
	    } catch (SQLException e) {
		logger.error("Couldn't close the connection");
		throw new ConnectionException("Couldn't close the connection");
	    }
	}
    }
}
