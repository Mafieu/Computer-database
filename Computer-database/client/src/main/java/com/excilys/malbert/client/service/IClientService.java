package com.excilys.malbert.client.service;

import java.util.List;

import com.excilys.malbert.core.model.Company;
import com.excilys.malbert.core.model.Computer;

/**
 * Interface of the Service communicating with the web services for CLI
 * 
 * @author excilys
 */
public interface IClientService {
	public List<Computer> getAllComputer();

	public Computer findComputer(Long id);

	public void deleteComputer(Long id);

	public void createComputer(Computer computer);

	public void updateComputer(Computer computer);

	public List<Company> getAllCompany();

	public void deleteCompany(Long id);

	public Company getCompany(Long id);
}
