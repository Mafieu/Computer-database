package com.excilys.malbert.persistence;

import java.util.List;

import com.excilys.malbert.persistence.model.Computer;

public interface IDAOComputer extends IDAOCrud<Computer> {
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

    public int getNumberComputer();

    /**
     * @param id
     * @param connection
     *            used for rollback or commit
     */
    public void transactionDelete(long id);

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
