package com.excilys.malbert.persistence;

import java.sql.Connection;
import java.util.List;

import com.excilys.malbert.persistence.model.Computer;

public interface IDAOComputer {
    public List<Computer> getAll();

    /**
     * Get the list of computers from offset to offset + limit
     * 
     * @param limit
     * @param offset
     * @return
     */
    public List<Computer> getSome(int limit, int offset);

    /**
     * Get the list of computers of a company
     * 
     * @param id
     *            Id of the company
     * @return
     */
    public List<Computer> getOfCompany(long id);

    public Computer getComputer(long id);

    public int getNumberComputer();

    public long create(Computer computer);

    public void delete(long id);

    /**
     * @param id
     * @param connection
     *            used for rollback or commit
     */
    public void delete(long id, Connection connection);

    /**
     * @param computer
     *            Computer with values changed (must keep the same id). Must not
     *            have a null Company. To do this, set the id at 0
     */
    public void update(Computer computer);

    /**
     * Get the list of computers from offset to offset + limit ordered by column
     * in ascending order
     * 
     * @param limit
     * @param offset
     * @param column
     * @return
     */
    public List<Computer> getSomeOrderedByAscending(int limit, int offset,
	    String column);

    /**
     * Get the list of computers from offset to offset + limit ordered by column
     * in descending order
     * 
     * @param limit
     * @param offset
     * @param column
     * @return
     */
    public List<Computer> getSomeOrderedByDescending(int limit, int offset,
	    String column);

    /**
     * Get the list of computers from offset to offset + limit with the search
     * in their name or in their company name ordered by column in order
     * determined by order
     * 
     * @param limit
     * @param offset
     * @param column
     * @param order
     * @param search
     * @return
     */
    public List<Computer> getSomeSearch(int limit, int offset, String column,
	    String order, String search);

    /**
     * Get the number of computers that have search in their name or company
     * name
     * 
     * @param search
     * @return
     */
    public int getNumberComputerSearch(String search);
}
