package com.excilys.malbert.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.malbert.persistence.ICompanyDAO;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ServiceCompanyTest {

    private ICompanyDAO mockedDAOCompany = mock(ICompanyDAO.class);
    private List<Company> companies;
    @Autowired
    private ICompanyService serviceCompany;
    @Autowired
    private ICompanyDAO companyDAO;

    @Before
    public void before() {
	companies = new ArrayList<Company>();
	companies.add(new Company(1, "Apple Inc."));
	companies.add(new Company(2, "Thinking Machines"));
	companies.add(new Company(3, "RCA"));
	companies.add(new Company(4, "Netronics"));

	when(mockedDAOCompany.getAll()).thenReturn(companies);
	when(mockedDAOCompany.getOne(1)).thenReturn(companies.get(0));

	// We set the dao of the service so that it uses the mock one
	serviceCompany.setCompanyDAO(mockedDAOCompany);

	// We init the db
	TestUtils.initDB();
    }

    @After
    public void after() {
	serviceCompany.setCompanyDAO(companyDAO);
    }

    @Test
    public void testGetAll() {
	assertArrayEquals(companies.toArray(), serviceCompany.getAllCompanies()
		.toArray());
    }

    @Test
    public void testGetCompany() {
	assertEquals(companies.get(0), serviceCompany.getCompany(1));
    }
}
