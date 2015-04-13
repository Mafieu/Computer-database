package com.excilys.malbert.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.malbert.persistence.DAOCompany;
import com.excilys.malbert.persistence.DAOComputer;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;

public class Service {

    public Service() {
    }

    public List<Computer> getAllComputers() throws SQLException {
	return DAOComputer.getAll();
    }

    public List<Company> getAllCompanies() throws SQLException {
	return DAOCompany.getAll();
    }

    public Computer getComputer(long id) throws SQLException {
	return DAOComputer.getComputer(id);
    }

    public void createComputer(Computer computer) throws SQLException {
	DAOComputer.create(computer);
    }

    public void deleteComputer(Computer computer) throws SQLException {
	DAOComputer.delete(computer);
    }

    public void updateComputer(Computer oldComputer, Computer newComputer)
	    throws SQLException {
	DAOComputer.update(oldComputer, newComputer);
    }
}
