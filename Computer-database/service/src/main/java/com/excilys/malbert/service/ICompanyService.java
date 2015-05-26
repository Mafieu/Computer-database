package com.excilys.malbert.service;

import java.util.List;

import com.excilys.malbert.core.model.Company;

public interface ICompanyService {

	public List<Company> getAllCompanies();

	public Company getCompany(Long id);

	/**
	 * Deletes the company and all the computers linked to this company
	 * 
	 * @param id
	 */
	public void deleteCompany(Long id);
}
