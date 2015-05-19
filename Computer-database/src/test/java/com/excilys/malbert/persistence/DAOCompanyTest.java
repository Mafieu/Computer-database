package com.excilys.malbert.persistence;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.malbert.exceptions.DAOException;
import com.excilys.malbert.model.Company;
import com.excilys.malbert.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContextTest.xml" })
public class DAOCompanyTest {

	private List<Company> companies;
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private CompanyDAO companyDAO;

	@Before
	public void before() {
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
		assertArrayEquals(companies.toArray(), companyDAO.getAll().toArray());
	}

	@Test
	public void testGetCompany() {
		assertEquals(companies.get(0), companyDAO.getOne(1));
	}

	@Test(expected = DAOException.class)
	public void testGetCompanyNull() {
		companyDAO.getOne(0);
	}

	@Test(expected = DAOException.class)
	public void testGetCompanyMinus() {
		companyDAO.getOne(-5);
	}

	@Test
	public void testGetCompanyOverLimit() {
		assertNull(companyDAO.getOne(99));
	}
}
