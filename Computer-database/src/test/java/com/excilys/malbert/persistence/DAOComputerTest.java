package com.excilys.malbert.persistence;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;
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
import com.excilys.malbert.model.Computer;
import com.excilys.malbert.persistence.IComputerDAO.Column;
import com.excilys.malbert.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContextTest.xml" })
public class DAOComputerTest {

	private List<Computer> computers;
	@Autowired
	private ComputerDAO computerDAO;

	@Before
	public void before() {
		computers = new ArrayList<Computer>();
		computers.add(new Computer(1, "MacBook Pro 15.4 inch", null, null,
				new Company(1, "Apple Inc.")));
		computers.add(new Computer(2, "CM-2a", null, null, new Company(2,
				"Thinking Machines")));
		computers.add(new Computer(3, "CM-200", null, null, new Company(2,
				"Thinking Machines")));
		computers.add(new Computer(4, "CM-5e", null, null, new Company(2,
				"Thinking Machines")));

		TestUtils.initDB();
	}

	@After
	public void after() {

	}

	@Test
	public void testGetAll() {
		assertArrayEquals(computers.toArray(), computerDAO.getAll().toArray());
	}

	@Test
	public void testGetSome() {
		assertArrayEquals(computers.subList(0, 2).toArray(), computerDAO
				.getSome(2, 0).toArray());
	}

	@Test(expected = DAOException.class)
	public void testGetSomeNegativeLimit() {
		computerDAO.getSome(-5, 0);
	}

	@Test(expected = DAOException.class)
	public void testGetSomeNegativeOffset() {
		computerDAO.getSome(2, -5);
	}

	@Test(expected = DAOException.class)
	public void testGetSomeOffsetAndLimitNull() {
		computerDAO.getSome(0, 0);
	}

	@Test
	public void testGetComputer() {
		assertEquals(computers.get(0), computerDAO.getOne(1));
	}

	@Test(expected = DAOException.class)
	public void testGetComputerNull() {
		computerDAO.getOne(0);
	}

	@Test(expected = DAOException.class)
	public void testGetComputerMinus() {
		computerDAO.getOne(-5);
	}

	@Test
	public void testGetComputerOverLimit() {
		assertNull(computerDAO.getOne(1597));
	}

	@Test
	public void testCreate() {
		// We set the id because we know what's its going to be
		// The id is not set in the database
		Computer computer = new Computer(0, "Test", LocalDateTime.of(1990, 04,
				29, 0, 0), null, new Company(1, "Apple Inc."));
		computerDAO.create(computer);
		assertEquals(computer.getId(), 5);
	}

	@Test
	public void testCreateNullCompany() {
		Computer computer = new Computer(5, "Test", LocalDateTime.of(1990, 04,
				29, 0, 0), null, null);
		computerDAO.create(computer);
	}

	@Test(expected = DAOException.class)
	public void testCreateNullName() {
		Computer computer = new Computer(5, null, LocalDateTime.of(1990, 04,
				29, 0, 0), null, new Company(1, "Apple Inc."));
		computerDAO.create(computer);
	}

	@Test(expected = DAOException.class)
	public void testCreateNullComputer() {
		computerDAO.create(null);
	}

	@Test
	public void testDelete() {
		computerDAO.delete(computers.get(0).getId());
		assertNull(computerDAO.getOne(computers.get(0).getId()));
	}

	@Test(expected = DAOException.class)
	public void testDeleteNegative() {
		computerDAO.delete(-5);
	}

	@Test(expected = DAOException.class)
	public void testDeleteNull() {
		computerDAO.delete(0);
	}

	@Test
	public void testUpdate() {
		Computer computer = new Computer(3, "Test", LocalDateTime.of(1990, 04,
				29, 0, 0), null, new Company(1, "Apple Inc."));
		computerDAO.update(computer);
		assertEquals(computer, computerDAO.getOne(3));
	}

	@Test
	public void testUpdateCompanyNull() {
		Computer computer = new Computer(3, "Test", LocalDateTime.of(1990, 04,
				29, 0, 0), null, null);
		computerDAO.update(computer);
	}

	@Test(expected = DAOException.class)
	public void testUpdateNameNull() {
		Computer computer = new Computer(3, null, LocalDateTime.of(1990, 04,
				29, 0, 0), null, new Company(1, "Apple Inc."));
		computerDAO.update(computer);
	}

	@Test(expected = DAOException.class)
	public void testUpdateNull() {
		computerDAO.update(null);
	}

	@Test
	public void testGetSomeAscending() {
		assertArrayEquals(computers.subList(0, 2).toArray(), computerDAO
				.getSomeOrderedByAscending(2, 0, Column.ID).toArray());
	}

	@Test
	public void testGetSomeDescending() {
		List<Computer> invertComputers = new ArrayList<Computer>();
		invertComputers.add(computers.get(3));
		invertComputers.add(computers.get(2));
		assertArrayEquals(invertComputers.toArray(), computerDAO
				.getSomeOrderedByDescending(2, 0, Column.ID).toArray());
	}

	@Test
	public void testGetNumberOfCompany() {
		assertEquals(1, computerDAO.getNumberComputerSearch("Apple"));
	}
}
