package com.excilys.malbert.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.malbert.persistence.DAOComputer;
import com.excilys.malbert.persistence.model.Computer;

/**
 * Describes the services
 * 
 * @author excilys
 *
 */
public abstract class ServiceComputer {

    /**
     * Gets all the computers from DAOComputer
     * 
     * @return A list of all the computers in database
     * @throws SQLException
     */
    public static List<Computer> getAllComputers() {
	return DAOComputer.INSTANCE.getAll();
    }

    /**
     * @param limit
     * @param offset
     * @return
     */
    public static List<Computer> getSome(int limit, int offset) {
	return DAOComputer.INSTANCE.getSome(limit, offset);
    }

    /**
     * Gets a computer from the DAOComputer
     * 
     * @param id
     *            Id of the computer to get
     * @return The computer
     * @throws SQLException
     */
    public static Computer getComputer(long id) {
	return DAOComputer.INSTANCE.getComputer(id);
    }

    /**
     * Creates a computer in the database with DAOComputer
     * 
     * @param computer
     *            The computer to insert in database
     * @throws SQLException
     */
    public static void createComputer(Computer computer) {
	DAOComputer.INSTANCE.create(computer);
    }

    /**
     * Deletes a computer from the database with DAOComputer
     * 
     * @param computer
     *            The computer to delete in database
     * @throws SQLException
     */
    public static void deleteComputer(Computer computer) {
	DAOComputer.INSTANCE.delete(computer);
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
    public static void updateComputer(Computer oldComputer, Computer newComputer) {
	DAOComputer.INSTANCE.update(oldComputer, newComputer);
    }
}
