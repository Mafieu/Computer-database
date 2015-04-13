package com.excilys.malbert.service;

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

    public List<Computer> getAllComputers() {
	return computers.getAll();
    }

    public List<Company> getAllCompanies() {
	return companies.getAll();
    }

    public Computer getComputer(long id) {
	return computers.getComputer(id);
    }

    public void createComputer(Computer computer) {
	computers.create(computer);
    }

    public void deleteComputer(Computer computer) {
	computers.delete(computer);
    }

    public void updateComputer(Computer oldComputer, Computer newComputer) {
	computers.update(oldComputer, newComputer);
    }
}
