package com.excilys.malbert.persistence.dbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.malbert.exceptions.ConnectionException;
import com.excilys.malbert.util.ConnectionProperties;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

/**
 * Singleton with a pool of connection
 * 
 * @author excilys
 */
public enum ConnectionDbFactory {
    INSTANCE;

    private ThreadLocal<Connection> connection = new ThreadLocal<Connection>();
    private ConnectionProperties properties;
    private boolean testing = false;

    private Logger logger = LoggerFactory.getLogger(ConnectionDbFactory.class);

    private BoneCP connectionPool = null;

    static {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
	    Logger loggerStat = LoggerFactory
		    .getLogger(ConnectionDbFactory.class);
	    loggerStat.error("Couldn't load jdbc driver");
	    throw new ConnectionException("Couldn't load jdbc driver");
	}
    };

    private ConnectionDbFactory() {
	try {
	    properties = new ConnectionProperties();
	} catch (IOException e) {
	    logger.error("Couldn't get property file");
	    throw new ConnectionException("Couldn't get property file");
	}
	changePool();
    }

    private void changePool() {
	try {
	    BoneCPConfig config = new BoneCPConfig();
	    config.setJdbcUrl(properties.getUrl()
		    + (testing ? properties.getDbTest() : properties.getDb())
		    + properties.getOption());
	    config.setUsername(properties.getUser());
	    config.setPassword(properties.getPasswd());
	    // To avoid a bug : true to say that db is started after server
	    config.setLazyInit(true);
	    config.setMinConnectionsPerPartition(5);
	    config.setMaxConnectionsPerPartition(10);
	    config.setPartitionCount(1);
	    connectionPool = new BoneCP(config);
	} catch (SQLException e) {
	    logger.error("Couldn't change the connection pool");
	    throw new ConnectionException("Couldn't change the connection pool");
	}
    }

    public boolean isTESTING() {
	return testing;
    }

    /**
     * Use this method to pass in testing mod
     * 
     * @param testing
     */
    public void setTESTING(boolean testing) {
	this.testing = testing;
	connectionPool.shutdown();
	changePool();
    }

    /**
     * @return a connection from the connection pool
     */
    public Connection getConnection() {
	try {
	    if (connection.get() == null || connection.get().isClosed()) {
		Connection connection = null;

		connection = connectionPool.getConnection();
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
