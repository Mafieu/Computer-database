package com.excilys.malbert.service;

import java.util.List;

import com.excilys.malbert.exceptions.DAOException;
import com.excilys.malbert.exceptions.ServiceException;
import com.excilys.malbert.persistence.DAOCompany;
import com.excilys.malbert.persistence.DAOComputer;
import com.excilys.malbert.persistence.IDAOCompany;
import com.excilys.malbert.persistence.IDAOComputer;
import com.excilys.malbert.persistence.dbConnection.ConnectionDbFactory;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.util.Validator;

public enum ServiceCompany implements IServiceCompany {
    INSTANCE;

    private IDAOCompany daoCompany = DAOCompany.INSTANCE;
    private IDAOComputer daoComputer = DAOComputer.INSTANCE;
    private ConnectionDbFactory connectionFactory = ConnectionDbFactory.INSTANCE;

    @Override
    public List<Company> getAllCompanies() {
	return daoCompany.getAll();
    }

    @Override
    public Company getCompany(long id) {
	if (!Validator.isIdValid(id)) {
	    throw new ServiceException(Validator.INVALID_ID);
	}
	return daoCompany.getCompany(id);
    }

    @Override
    public void deleteCompany(long id) {
	List<Computer> computers = daoComputer.getOfCompany(id);
	connectionFactory.getConnection();

	connectionFactory.startTransaction();
	try {
	    for (Computer computer : computers) {
		// Call to DAO or Service ?
		daoComputer.transactionDelete(computer.getId());
	    }
	    daoCompany.delete(id);
	    connectionFactory.commit();
	} catch (DAOException e) {
	    connectionFactory.rollback();
	} finally {
	    connectionFactory.closeConnection();
	}
    }

    @Override
    public void setCompanyDAO(IDAOCompany daoCompany) {
	this.daoCompany = daoCompany;
    }
}
