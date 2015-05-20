package com.excilys.malbert.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.malbert.core.exception.DAOException;
import com.excilys.malbert.core.model.Company;
import com.excilys.malbert.core.model.QCompany;
import com.excilys.malbert.persistence.validator.DbValidator;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class CompanyDAO implements ICompanyDAO {

	@Autowired
	EntityManagerFactory emFactory;
	private Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	@Override
	public List<Company> getAll() {
		EntityManager em = emFactory.createEntityManager();
		QCompany company = QCompany.company;
		JPAQuery query = new JPAQuery(em);
		return query.from(company).list(company);
	}

	@Override
	public Company getOne(long id) {
		if (!DbValidator.isIdValid(id)) {
			logger.error("get company : {}", id);
			throw new DAOException(DbValidator.INVALID_ID);
		}

		EntityManager em = emFactory.createEntityManager();
		QCompany company = QCompany.company;
		JPAQuery query = new JPAQuery(em);
		return query.from(company).where(company.id.eq(id))
				.uniqueResult(company);
	}

	@Override
	public void delete(long id) {
		if (!DbValidator.isIdValid(id)) {
			logger.error("delete commpany : {}", id);
			throw new DAOException(DbValidator.INVALID_ID);
		}

		EntityManager em = emFactory.createEntityManager();
		QCompany company = QCompany.company;
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			JPADeleteClause query = new JPADeleteClause(em, company);
			query.where(company.id.eq(id)).execute();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}
	}
}
