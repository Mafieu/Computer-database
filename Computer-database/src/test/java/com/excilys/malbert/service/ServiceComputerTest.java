package com.excilys.malbert.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.malbert.persistence.DAOComputer;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.util.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ServiceComputerTest {
    // We mock a DAOComputer with DAOException as a default answer
    private DAOComputer mockedDAOComputer = mock(DAOComputer.class);
    private List<Computer> computers;
    private List<Computer> reversedComputers;
    @Autowired
    private ServiceComputer serviceComputer;
    @Autowired
    private DAOComputer computerDAO;

    @SuppressWarnings("rawtypes")
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

	// We define the methods we can call from the mock object
	when(mockedDAOComputer.getAll()).thenReturn(computers);
	when(mockedDAOComputer.getOne(2)).thenReturn(computers.get(1));
	when(mockedDAOComputer.getSome(2, 0)).thenReturn(
		computers.subList(0, 2));
	when(mockedDAOComputer.getNumberComputer()).thenReturn(4);
	when(
		mockedDAOComputer.create(new Computer(5, "Test", LocalDateTime
			.of(1990, 04, 29, 0, 0), null, new Company(1,
			"Apple Inc.")))).thenAnswer(new Answer() {
	    @Override
	    public Object answer(InvocationOnMock invocation) throws Throwable {
		computers.add((Computer) invocation.getArguments()[0]);
		return 5L;
	    }
	});
	when(mockedDAOComputer.getSomeOrderedByAscending(2, 0, "computer.id"))
		.thenReturn(computers.subList(0, 2));
	reversedComputers = new ArrayList<Computer>();
	reversedComputers.add(computers.get(1));
	reversedComputers.add(computers.get(0));
	when(mockedDAOComputer.getSomeOrderedByDescending(2, 0, "computer.id"))
		.thenReturn(reversedComputers);
	doAnswer(new Answer() {
	    @Override
	    public Object answer(InvocationOnMock invocation) throws Throwable {
		computers.remove(((long) invocation.getArguments()[0]) - 1);
		return null;
	    }
	}).when(mockedDAOComputer).delete(1L);
	doAnswer(new Answer() {
	    @Override
	    public Object answer(InvocationOnMock invocation) throws Throwable {
		return null;
	    }
	}).when(mockedDAOComputer).update(
		new Computer(3, "Test", LocalDateTime.of(1990, 04, 29, 0, 0),
			null, new Company(1, "Apple Inc.")));

	// We set the dao of the service so that it uses the mock one
	serviceComputer.setComputerDAO(mockedDAOComputer);

	// We init the db
	TestUtils.initDB();
    }

    @After
    public void after() {
	serviceComputer.setComputerDAO(computerDAO);
    }

    @Test
    public void testGetAll() {
	assertArrayEquals(computers.toArray(), serviceComputer
		.getAllComputers().toArray());
    }

    @Test
    public void testGetSome() {
	assertArrayEquals(computers.subList(0, 2).toArray(), serviceComputer
		.getSome(2, 0).toArray());
    }

    @Test
    public void testGetComputer() {
	assertEquals(computers.get(1), serviceComputer.getComputer(2));
    }

    @Test
    public void testCreateComputer() {
	Computer computer = new Computer(5, "Test", LocalDateTime.of(1990, 04,
		29, 0, 0), null, new Company(1, "Apple Inc."));
	assertEquals(5, serviceComputer.createComputer(computer));
    }

    @Test
    public void testDeleteComputer() {
	serviceComputer.deleteComputer(1L);
    }

    @Test
    public void testUpdateComputer() {
	Computer computer = new Computer(3, "Test", LocalDateTime.of(1990, 04,
		29, 0, 0), null, new Company(1, "Apple Inc."));
	serviceComputer.updateComputer(computer);
    }

    @Test
    public void testGetNumberComputer() {
	assertEquals(4, serviceComputer.getNumberComputer());
    }

    @Test
    public void testGetSomeOrderedByAscending() {
	assertArrayEquals(computers.subList(0, 2).toArray(), serviceComputer
		.getSomeOrderedByAscending(2, 0, "computer.id").toArray());
    }

    @Test
    public void testGetSomeOrderedByDescending() {
	assertArrayEquals(reversedComputers.toArray(), serviceComputer
		.getSomeOrderedByDescending(2, 0, "computer.id").toArray());
    }
}
