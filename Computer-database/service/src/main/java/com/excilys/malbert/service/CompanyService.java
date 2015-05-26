package com.excilys.malbert.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.malbert.core.exception.ServiceException;
import com.excilys.malbert.core.model.Company;
import com.excilys.malbert.persistence.ICompanyDAO;
import com.excilys.malbert.persistence.IComputerDAO;
import com.excilys.malbert.persistence.validator.DbValidator;

@Service
public class CompanyService implements ICompanyService {

	@Autowired
	private ICompanyDAO companyDAO;
	@Autowired
	private IComputerDAO computerDAO;

	@Override
	public List<Company> getAllCompanies() {
		return companyDAO.getAll();
	}

	@Override
	public Company getCompany(Long id) {
		if (!DbValidator.isIdValid(id)) {
			throw new ServiceException(DbValidator.INVALID_ID);
		}
		return companyDAO.getOne(id);
	}

	@Transactional
	@Override
	public void deleteCompany(Long id) {
		if (companyDAO.getOne(id) != null) {
			computerDAO.deleteOfCompany(id);
			companyDAO.delete(id);
		}
		// Maybe throw a non runtime exception to catch on controller and
		// display a message
	}
}
