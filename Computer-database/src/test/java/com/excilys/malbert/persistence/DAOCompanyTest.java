package com.excilys.malbert.persistence;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.malbert.exceptions.DAOException;
import com.excilys.malbert.persistence.dbConnection.ConnectionDbFactory;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.util.TestUtils;

public class DAOCompanyTest {

    private List<Company> companies;

    @Before
    public void before() {
	ConnectionDbFactory.INSTANCE.setTESTING(true);

	companies = new ArrayList<Company>();
	companies.add(new Company(1, "Apple Inc."));
	companies.add(new Company(2, "Thinking Machines"));
	companies.add(new Company(3, "RCA"));
	companies.add(new Company(4, "Netronics"));

	TestUtils.initDB();
    }

    @After
    public void after() {
    }

    @Test
    public void testGetAll() {
	assertArrayEquals(companies.toArray(), DAOCompany.INSTANCE.getAll()
		.toArray());
    }

    @Test
    public void testGetCompany() {
	assertEquals(companies.get(0), DAOCompany.INSTANCE.getCompany(1));
    }

    @Test(expected = DAOException.class)
    public void testGetCompanyNull() {
	DAOCompany.INSTANCE.getCompany(0);
    }

    @Test(expected = DAOException.class)
    public void testGetCompanyMinus() {
	DAOCompany.INSTANCE.getCompany(-5);
    }

    @Test(expected = DAOException.class)
    public void testGetCompanyOverLimit() {
	DAOCompany.INSTANCE.getCompany(99);
    }

    @Test
    public void testDelete() {
	Connection connection = ConnectionDbFactory.INSTANCE.getConnection();
	List<Computer> computers = DAOComputer.INSTANCE.getOfCompany(1);
	for (Computer computer : computers) {
	    DAOComputer.INSTANCE.delete(computer.getId(), connection);
	}
	DAOCompany.INSTANCE.delete(1, connection);
	ConnectionDbFactory.INSTANCE.closeConnection(connection, null, null);
    }
}
