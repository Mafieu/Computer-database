package com.excilys.malbert.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.malbert.persistence.DAOCompany;
import com.excilys.malbert.persistence.DAOComputer;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;

/**
 * Describes the services
 * 
 * @author excilys
 *
 */
public class Service {

    /**
     * Default constructor
     */
    public Service() {
    }

    /**
     * Gets all the computers from DAOComputer
     * 
     * @return A list of all the computers in database
     * @throws SQLException
     */
    public List<Computer> getAllComputers() throws SQLException {
	return DAOComputer.getAll();
    }

    /**
     * Gets all the companies from the DAOCompany
     * 
     * @return A list of all the companies in database
     * @throws SQLException
     */
    public List<Company> getAllCompanies() throws SQLException {
	return DAOCompany.getAll();
    }

    /**
     * Gets a computer from the DAOComputer
     * 
     * @param id
     *            Id of the computer to get
     * @return The computer
     * @throws SQLException
     */
    public Computer getComputer(long id) throws SQLException {
	return DAOComputer.getComputer(id);
    }

    /**
     * Creates a computer in the database with DAOComputer
     * 
     * @param computer
     *            The computer to insert in database
     * @throws SQLException
     */
    public void createComputer(Computer computer) throws SQLException {
	DAOComputer.create(computer);
    }

    /**
     * Deletes a computer from the database with DAOComputer
     * 
     * @param computer
     *            The computer to delete in database
     * @throws SQLException
     */
    public void deleteComputer(Computer computer) throws SQLException {
	DAOComputer.delete(computer);
    }

    /**
     * Updates a computer in the database with DAOComputer
     * 
     * @param oldComputer
     *            The computer to update
     * @param newComputer
     *            The computer updated
     * @throws SQLException
     */
    public void updateComputer(Computer oldComputer, Computer newComputer)
	    throws SQLException {
	DAOComputer.update(oldComputer, newComputer);
    }
}
