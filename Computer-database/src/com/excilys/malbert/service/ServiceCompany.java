package com.excilys.malbert.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.malbert.persistence.DAOCompany;
import com.excilys.malbert.persistence.model.Company;

public abstract class ServiceCompany {

    /**
     * Gets all the companies from the DAOCompany
     * 
     * @return A list of all the companies in database
     * @throws SQLException
     */
    public static List<Company> getAllCompanies() {
	return DAOCompany.INSTANCE.getAll();
    }
}
