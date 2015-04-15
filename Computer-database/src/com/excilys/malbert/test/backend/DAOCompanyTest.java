package com.excilys.malbert.test.backend;

import static org.junit.Assert.assertArrayEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.malbert.dbConnection.ConnectionDbFactory;
import com.excilys.malbert.persistence.DAOCompany;
import com.excilys.malbert.persistence.model.Company;

public class DAOCompanyTest {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet set;

    @Before
    public void before() {
	ConnectionDbFactory.TESTING = true;
	connection = ConnectionDbFactory.getConnection();
	statement = null;
	set = null;
    }

    @After
    public void after() {
	ConnectionDbFactory.closeConnection(connection, statement, set);
    }

    @Test
    public void testGetAll() {
	List<Company> companies = new ArrayList<Company>();
	companies.add(new Company(1, "Apple Inc."));
	companies.add(new Company(2, "Thinking Machines"));
	companies.add(new Company(3, "RCA"));
	companies.add(new Company(4, "Netronics"));
	assertArrayEquals(companies.toArray(), DAOCompany.INSTANCE.getAll()
		.toArray());
    }
}
