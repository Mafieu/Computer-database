package com.excilys.malbert.service;

import java.sql.Connection;
import java.sql.SQLException;
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

public enum ServiceCompany {
    INSTANCE;

    private IDAOCompany daoCompany = DAOCompany.INSTANCE;
    private IDAOComputer daoComputer = DAOComputer.INSTANCE;
    private ConnectionDbFactory connectionFactory = ConnectionDbFactory.INSTANCE;

    /**
     * Gets all the companies from the DAOCompany
     * 
     * @return A list of all the companies in database
     * @throws SQLException
     */
    public List<Company> getAllCompanies() {
	return daoCompany.getAll();
    }

    public Company getCompany(long id) {
	if (!Validator.isIdValid(id)) {
	    throw new ServiceException(Validator.INVALID_ID);
	}
	return daoCompany.getCompany(id);
    }

    public void deleteCompany(long id) {
	Connection connection = connectionFactory.getConnection();
	List<Computer> computers = daoComputer.getOfCompany(id);
	try {
	    connection.setAutoCommit(false);
	    for (Computer computer : computers) {
		// Call to DAO or Service ?
		daoComputer.delete(computer.getId(), connection);
	    }
	    daoCompany.delete(id, connection);
	    connection.commit();
	} catch (DAOException | SQLException e) {
	    try {
		connection.rollback();
	    } catch (SQLException e1) {
		throw new ServiceException(
			"Couldn't rollback the modifications");
	    }
	} finally {
	    connectionFactory.closeConnection(connection, null, null);
	}
    }

    public void setCompanyDAO(IDAOCompany daoCompany) {
	this.daoCompany = daoCompany;
    }
}
