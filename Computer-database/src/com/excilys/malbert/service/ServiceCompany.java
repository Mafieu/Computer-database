package com.excilys.malbert.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.malbert.persistence.DAOCompany;
import com.excilys.malbert.persistence.IDAOCompany;
import com.excilys.malbert.persistence.model.Company;

public abstract class ServiceCompany {

    private static IDAOCompany daoCompany = DAOCompany.INSTANCE;

    /**
     * Gets all the companies from the DAOCompany
     * 
     * @return A list of all the companies in database
     * @throws SQLException
     */
    public static List<Company> getAllCompanies() {
	return daoCompany.getAll();
    }

    public static Company getCompany(long id) {
	return daoCompany.getCompany(id);
    }
}
