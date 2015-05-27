package com.excilys.malbert.service;

import java.util.List;

import com.excilys.malbert.core.model.Computer;
import com.excilys.malbert.persistence.IComputerDAO.Column;
import com.excilys.malbert.persistence.IComputerDAO.Order;

public interface IComputerService {

	public List<Computer> getAllComputers();

	public List<Computer> getSome(Integer limit, Integer offset);

	public Computer getComputer(Long id);

	public void createComputer(Computer computer);

	public void deleteComputer(Long id);

	public void updateComputer(Computer computer);

	public int getNumberComputer();

	public List<Computer> getSomeOrderedByAscending(Integer limit,
			Integer offset, Column column);

	public List<Computer> getSomeOrderedByDescending(Integer limit,
			Integer offset, Column column);

	public List<Computer> getSomeSearch(Integer limit, Integer offset,
			Column column, Order order, String search);

	public int getNumberComputerSearch(String search);

	public List<Computer> getList(Integer limit, Integer offset, Column column,
			Order order, String search);
}
