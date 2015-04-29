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

import com.excilys.malbert.exceptions.DAOException;
import com.excilys.malbert.persistence.dbConnection.ConnectionDbFactory;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.util.TestUtils;

public class DAOComputerTest {

    private List<Computer> computers;

    @Before
    public void before() {
	ConnectionDbFactory.INSTANCE.setTESTING(true);

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
	assertArrayEquals(computers.toArray(), DAOComputer.INSTANCE.getAll()
		.toArray());
    }

    @Test
    public void testGetSome() {
	assertArrayEquals(computers.subList(0, 2).toArray(),
		DAOComputer.INSTANCE.getSome(2, 0).toArray());
    }

    @Test(expected = DAOException.class)
    public void testGetSomeNegativeLimit() {
	DAOComputer.INSTANCE.getSome(-5, 0);
    }

    @Test(expected = DAOException.class)
    public void testGetSomeNegativeOffset() {
	DAOComputer.INSTANCE.getSome(2, -5);
    }

    @Test(expected = DAOException.class)
    public void testGetSomeOffsetAndLimitNull() {
	DAOComputer.INSTANCE.getSome(0, 0);
    }

    @Test
    public void testGetComputer() {
	assertEquals(computers.get(0), DAOComputer.INSTANCE.getOne(1));
    }

    @Test(expected = DAOException.class)
    public void testGetComputerNull() {
	DAOComputer.INSTANCE.getOne(0);
    }

    @Test(expected = DAOException.class)
    public void testGetComputerMinus() {
	DAOComputer.INSTANCE.getOne(-5);
    }

    @Test
    public void testGetComputerOverLimit() {
	assertNull(DAOComputer.INSTANCE.getOne(1597));
    }

    @Test
    public void testCreate() {
	// We set the id because we know what's its going to be
	// The id is not set in the database
	Computer computer = new Computer(5, "Test", LocalDateTime.of(1990, 04,
		29, 0, 0), null, new Company(1, "Apple Inc."));
	long id = DAOComputer.INSTANCE.create(computer);
	assertEquals(computer.getId(), id);
    }

    @Test(expected = DAOException.class)
    public void testCreateNullCompany() {
	// We set the id because we know what's its going to be
	// The id is not set in the database
	Computer computer = new Computer(5, "Test", LocalDateTime.of(1990, 04,
		29, 0, 0), null, null);
	DAOComputer.INSTANCE.create(computer);
    }

    @Test(expected = DAOException.class)
    public void testCreateNullName() {
	Computer computer = new Computer(5, null, LocalDateTime.of(1990, 04,
		29, 0, 0), null, new Company(1, "Apple Inc."));
	DAOComputer.INSTANCE.create(computer);
    }

    @Test(expected = DAOException.class)
    public void testCreateNullComputer() {
	DAOComputer.INSTANCE.create(null);
    }

    @Test
    public void testDelete() {
	DAOComputer.INSTANCE.delete(computers.get(0).getId());
	assertNull(DAOComputer.INSTANCE.getOne(computers.get(0).getId()));
    }

    @Test(expected = DAOException.class)
    public void testDeleteNegative() {
	DAOComputer.INSTANCE.delete(-5);
    }

    @Test(expected = DAOException.class)
    public void testDeleteNull() {
	DAOComputer.INSTANCE.delete(0);
    }

    @Test
    public void testUpdate() {
	Computer computer = new Computer(3, "Test", LocalDateTime.of(1990, 04,
		29, 0, 0), null, new Company(1, "Apple Inc."));
	DAOComputer.INSTANCE.update(computer);
	assertEquals(computer, DAOComputer.INSTANCE.getOne(3));
    }

    @Test(expected = DAOException.class)
    public void testUpdateCompanyNull() {
	Computer computer = new Computer(3, "Test", LocalDateTime.of(1990, 04,
		29, 0, 0), null, null);
	DAOComputer.INSTANCE.update(computer);
    }

    @Test(expected = DAOException.class)
    public void testUpdateNameNull() {
	Computer computer = new Computer(3, null, LocalDateTime.of(1990, 04,
		29, 0, 0), null, new Company(1, "Apple Inc."));
	DAOComputer.INSTANCE.update(computer);
    }

    @Test(expected = DAOException.class)
    public void testUpdateNull() {
	DAOComputer.INSTANCE.update(null);
    }

    @Test
    public void testGetSomeAscending() {
	assertArrayEquals(
		computers.subList(0, 2).toArray(),
		DAOComputer.INSTANCE.getSomeOrderedByAscending(2, 0,
			"computer.id").toArray());
    }

    @Test
    public void testGetSomeDescending() {
	List<Computer> invertComputers = new ArrayList<Computer>();
	invertComputers.add(computers.get(3));
	invertComputers.add(computers.get(2));
	assertArrayEquals(invertComputers.toArray(), DAOComputer.INSTANCE
		.getSomeOrderedByDescending(2, 0, "computer.id").toArray());
    }

    @Test
    public void testGetOfCompany() {
	List<Computer> computersOfApple = new ArrayList<Computer>();
	computersOfApple.add(computers.get(0));
	assertArrayEquals(computersOfApple.toArray(), DAOComputer.INSTANCE
		.getOfCompany(1).toArray());
    }

    @Test
    public void testGetNumberOfCompany() {
	assertEquals(1, DAOComputer.INSTANCE.getNumberComputerSearch("Apple"));
    }
}
