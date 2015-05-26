package com.excilys.malbert.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.malbert.core.exception.DAOException;
import com.excilys.malbert.core.model.Company;
import com.excilys.malbert.core.model.QCompany;
import com.excilys.malbert.persistence.validator.DbValidator;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class CompanyDAO implements ICompanyDAO {

	@PersistenceContext
	EntityManager em;
	private Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	@Override
	public List<Company> getAll() {
		QCompany company = QCompany.company;
		JPAQuery query = new JPAQuery(em);
		return query.from(company).list(company);
	}

	@Override
	public Company getOne(Long id) {
		if (!DbValidator.isIdValid(id)) {
			logger.error("get company : {}", id);
			throw new DAOException(DbValidator.INVALID_ID);
		}

		QCompany company = QCompany.company;
		JPAQuery query = new JPAQuery(em);
		return query.from(company).where(company.id.eq(id))
				.uniqueResult(company);
	}

	@Override
	public void delete(Long id) {
		if (!DbValidator.isIdValid(id)) {
			logger.error("delete commpany : {}", id);
			throw new DAOException(DbValidator.INVALID_ID);
		}

		QCompany company = QCompany.company;
		JPADeleteClause query = new JPADeleteClause(em, company);
		query.where(company.id.eq(id)).execute();
	}
}
