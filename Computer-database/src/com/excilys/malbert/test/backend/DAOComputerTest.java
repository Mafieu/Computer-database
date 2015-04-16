package com.excilys.malbert.test.backend;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.malbert.persistence.DAOComputer;
import com.excilys.malbert.persistence.DAOException;
import com.excilys.malbert.persistence.dbConnection.ConnectionDbFactory;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.test.utils.TestUtils;

public class DAOComputerTest {

    private List<Computer> computers;

    @Before
    public void before() {
	ConnectionDbFactory.TESTING = true;

	computers = new ArrayList<Computer>();
	computers.add(new Computer(1, "MacBook Pro 15.4 inch", null, null,
		new Company(1, "Apple Inc.")));
	computers.add(new Computer(2, "CM-2a", null, null, new Company(2,
		"Thinking Machines")));
	computers.add(new Computer(3, "CM-200", null, null, new Company(2,
		"Thinking Machines")));
	computers.add(new Computer(4, "CM-5e", null, null, new Company(2,
		"Thinking Machines")));
	computers.add(new Computer(5, "CM-5", LocalDateTime
		.of(1991, 1, 1, 0, 0), null,
		new Company(2, "Thinking Machines")));
	computers.add(new Computer(6, "MacBook Pro", LocalDateTime.of(2006, 1,
		10, 0, 0), null, new Company(1, "Apple Inc.")));
	computers.add(new Computer(7, "Apple IIe", null, null, null));
	computers.add(new Computer(8, "Apple IIc", null, null, null));
	computers.add(new Computer(9, "Apple IIGS", null, null, null));
	computers.add(new Computer(10, "Apple IIc Plus", null, null, null));
	computers.add(new Computer(11, "Apple II Plus", null, null, null));
	computers.add(new Computer(12, "Apple III", LocalDateTime.of(1980, 05,
		01, 0, 0), LocalDateTime.of(1984, 04, 01, 0, 0), new Company(1,
		"Apple Inc.")));
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
	assertArrayEquals(computers.subList(0, 10).toArray(),
		DAOComputer.INSTANCE.getSome(10, 0).toArray());
    }

    @Test
    public void testGetComputer() {
	assertEquals(computers.get(0), DAOComputer.INSTANCE.getComputer(1));
	try {
	    assertNull(DAOComputer.INSTANCE.getComputer(0));
	} catch (DAOException e) {

	}
	try {
	    assertNull(DAOComputer.INSTANCE.getComputer(-5));
	} catch (DAOException e) {

	}
	try {
	    assertNull(DAOComputer.INSTANCE.getComputer(1597));
	} catch (DAOException e) {

	}
    }

    @Test
    public void testCreate() {
	// We put the id because we know what's its going to be
	// The id is not put in the database
	Computer computer = new Computer(13, "Test", LocalDateTime.of(1990, 04,
		29, 0, 0), null, new Company(1, "Apple Inc."));
	DAOComputer.INSTANCE.create(computer);
	assertEquals(computer, DAOComputer.INSTANCE.getComputer(13));
    }

    @Test
    public void testDelete() {
	DAOComputer.INSTANCE.delete(computers.get(0));
	try {
	    assertNull(DAOComputer.INSTANCE.getComputer(1));
	} catch (DAOException e) {

	}
    }

    @Test
    public void testUpdate() {
	Computer computer = new Computer(3, "Test", LocalDateTime.of(1990, 04,
		29, 0, 0), null, new Company(1, "Apple Inc."));
	DAOComputer.INSTANCE.update(computer);
	assertEquals(computer, DAOComputer.INSTANCE.getComputer(3));
    }
}
