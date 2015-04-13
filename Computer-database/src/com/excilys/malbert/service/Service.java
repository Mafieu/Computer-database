package com.excilys.malbert.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.malbert.persistence.DAOCompanies;
import com.excilys.malbert.persistence.DAOComputer;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;

public class Service {
    private DAOCompanies companies;
    private DAOComputer computers;

    public Service() {
	companies = new DAOCompanies();
	computers = new DAOComputer();
    }

    public List<Computer> getAllComputers() throws SQLException {
	return computers.getAll();
    }

    public List<Company> getAllCompanies() {
	return companies.getAll();
    }

    public Computer getComputer(long id) throws SQLException {
	return computers.getComputer(id);
    }

    public void createComputer(Computer computer) throws SQLException {
	computers.create(computer);
    }

    public void deleteComputer(Computer computer) throws SQLException {
	computers.delete(computer);
    }

    public void updateComputer(Computer oldComputer, Computer newComputer)
	    throws SQLException {
	computers.update(oldComputer, newComputer);
    }
}
