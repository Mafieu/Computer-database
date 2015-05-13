package com.excilys.malbert.service;

import java.util.List;

import com.excilys.malbert.persistence.ICompanyDAO;
import com.excilys.malbert.persistence.model.Company;

public interface ICompanyService {

    public List<Company> getAllCompanies();

    public Company getCompany(long id);

    /**
     * Deletes the company and all the computers linked to this company
     * 
     * @param id
     */
    public void deleteCompany(long id);

    /**
     * Used for testing purpose
     * 
     * @param daoCompany
     */
    public void setCompanyDAO(ICompanyDAO daoCompany);
}
