package com.excilys.malbert.service;

import java.util.List;

import com.excilys.malbert.persistence.DAOComputer;
import com.excilys.malbert.persistence.model.Computer;

public interface IServiceComputer {

    public List<Computer> getAllComputers();

    public List<Computer> getSome(int limit, int offset);

    public Computer getComputer(long id);

    public long createComputer(Computer computer);

    public void deleteComputer(long id);

    public void updateComputer(Computer computer);

    public int getNumberComputer();

    public List<Computer> getSomeOrderedByAscending(int limit, int offset,
	    String column);

    public List<Computer> getSomeOrderedByDescending(int limit, int offset,
	    String column);

    public List<Computer> getSomeSearch(int limit, int offset, String column,
	    String order, String search);

    public int getNumberComputerSearch(String search);

    /**
     * Used for testing purpose
     * 
     * @param daoComputer
     */
    public void setComputerDAO(DAOComputer daoComputer);
}
