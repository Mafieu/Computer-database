package com.excilys.malbert.persistence.dbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    private ConnectionProperties properties;
    private boolean testing = false;

    private BoneCP connectionPool = null;

    static {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
    };

    private ConnectionDbFactory() {
	try {
	    properties = new ConnectionProperties();
	} catch (IOException e) {
	    e.printStackTrace();
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
	    e.printStackTrace();
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
	Connection connection = null;
	try {
	    connection = connectionPool.getConnection();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return connection;
    }

    /**
     * Used to close all the connections
     * 
     * @param connection
     * @param statement
     * @param resultSet
     */
    public void closeConnection(Connection connection,
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
