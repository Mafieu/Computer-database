package com.excilys.malbert.persistence;

import java.sql.Connection;
import java.util.List;

import com.excilys.malbert.persistence.model.Computer;

public interface IDAOComputer {
    public List<Computer> getAll();

    public List<Computer> getSome(int limit, int offset);

    /**
     * @param id
     *            Id of the company
     * @return
     */
    public List<Computer> getOfCompany(long id);

    public Computer getComputer(long id);

    public int getNumberComputer();

    public long create(Computer computer);

    public void delete(long id);

    public void delete(long id, Connection connection);

    /**
     * @param computer
     *            Computer with values changed (must keep the same id). Must not
     *            have a null Company. To do this, set the id at 0
     */
    public void update(Computer computer);

    public List<Computer> getSomeOrderedByAscending(int limit, int offset,
	    String column);

    public List<Computer> getSomeOrderedByDescending(int limit, int offset,
	    String column);

    public List<Computer> getSomeSearch(int limit, int offset, String column,
	    String order, String search);

    public int getNumberComputerSearch(String search);
}
