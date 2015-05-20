package com.excilys.malbert.service;

import java.util.List;

import com.excilys.malbert.core.model.Computer;
import com.excilys.malbert.persistence.IComputerDAO.Column;
import com.excilys.malbert.persistence.IComputerDAO.Order;

public interface IComputerService {

	public List<Computer> getAllComputers();

	public List<Computer> getSome(int limit, int offset);

	public Computer getComputer(long id);

	public void createComputer(Computer computer);

	public void deleteComputer(long id);

	public void updateComputer(Computer computer);

	public int getNumberComputer();

	public List<Computer> getSomeOrderedByAscending(int limit, int offset,
			Column column);

	public List<Computer> getSomeOrderedByDescending(int limit, int offset,
			Column column);

	public List<Computer> getSomeSearch(int limit, int offset, Column column,
			Order order, String search);

	public int getNumberComputerSearch(String search);
}
