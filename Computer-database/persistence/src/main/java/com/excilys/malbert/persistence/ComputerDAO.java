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
import com.excilys.malbert.core.model.Computer;
import com.excilys.malbert.core.model.QCompany;
import com.excilys.malbert.core.model.QComputer;
import com.excilys.malbert.persistence.validator.DbValidator;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;

@Repository
public class ComputerDAO implements IComputerDAO {

	@Autowired
	EntityManagerFactory emFactory;
	private Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	@Override
	public List<Computer> getAll() {
		EntityManager em = emFactory.createEntityManager();
		QComputer computer = QComputer.computer;
		JPAQuery query = new JPAQuery(em);
		List<Computer> computers = query.from(computer).list(computer);
		em.close();
		return computers;
	}

	@Override
	public List<Computer> getSome(int limit, int offset) {
		return getSomeOrderedByAscending(limit, offset, Column.ID);
	}

	@Override
	public Computer getOne(long id) {
		if (!DbValidator.isIdValid(id)) {
			logger.error("get computer : {}", id);
			throw new DAOException(DbValidator.INVALID_ID);
		}
		EntityManager em = emFactory.createEntityManager();
		QComputer computer = QComputer.computer;
		JPAQuery query = new JPAQuery(em);
		Computer comp = query.from(computer).where(computer.id.eq(id))
				.uniqueResult(computer);
		em.close();
		return comp;
	}

	@Override
	public void create(Computer computer) {
		if (!DbValidator.isComputerValid(computer)) {
			logger.error("create computer : invalid computer");
			throw new DAOException(DbValidator.INVALID_COMPUTER);
		}
		EntityManager em = emFactory.createEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(computer);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}
		em.close();
	}

	@Override
	public void delete(long id) {
		if (!DbValidator.isIdValid(id)) {
			logger.error("delete computer : invalid id");
			throw new DAOException(DbValidator.INVALID_ID);
		}
		EntityManager em = emFactory.createEntityManager();
		QComputer computer = QComputer.computer;
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			JPADeleteClause query = new JPADeleteClause(em, computer);
			query.where(computer.id.eq(id)).execute();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}
		em.close();
	}

	@Override
	public void update(Computer computer) {
		if (!DbValidator.isComputerValid(computer)) {
			logger.error("update computer : invalid computer");
			throw new DAOException(DbValidator.INVALID_COMPUTER);
		}
		EntityManager em = emFactory.createEntityManager();
		QComputer comp = QComputer.computer;
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			JPAUpdateClause query = new JPAUpdateClause(em, comp);
			query.where(comp.id.eq(computer.getId()))
					.set(comp.name, computer.getName())
					.set(comp.introduced, computer.getIntroduced())
					.set(comp.discontinued, computer.getDiscontinued())
					.set(comp.company, computer.getCompany()).execute();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}
		em.close();
	}

	@Override
	public int getNumberComputer() {
		EntityManager em = emFactory.createEntityManager();
		QComputer computer = QComputer.computer;
		JPAQuery query = new JPAQuery(em);
		int nb = (int) query.from(computer).count();
		em.close();
		return nb;
	}

	@Override
	public List<Computer> getSomeOrderedByAscending(int limit, int offset,
			Column column) {
		return getSomeOrderBy(limit, offset, column, Order.ASC);
	}

	@Override
	public List<Computer> getSomeOrderedByDescending(int limit, int offset,
			Column column) {
		return getSomeOrderBy(limit, offset, column, Order.DESC);
	}

	private JPAQuery orderBy(JPAQuery query, Order order, Column column) {
		QComputer computer = QComputer.computer;
		switch (column) {
		case COMPANY:
			if (order == Order.ASC) {
				query = query.orderBy(computer.company.name.asc());
			} else {
				query = query.orderBy(computer.company.name.desc());
			}
			break;
		case DISCONTINUED:
			if (order == Order.ASC) {
				query = query.orderBy(computer.discontinued.asc());
			} else {
				query = query.orderBy(computer.discontinued.desc());
			}
			break;
		case INTRODUCED:
			if (order == Order.ASC) {
				query = query.orderBy(computer.introduced.asc());
			} else {
				query = query.orderBy(computer.introduced.desc());
			}
			break;
		case NAME:
			if (order == Order.ASC) {
				query = query.orderBy(computer.name.asc());
			} else {
				query = query.orderBy(computer.name.desc());
			}
			break;
		default:
			if (order == Order.DESC) {
				query = query.orderBy(computer.id.desc());
			}
		}
		return query;
	}

	private List<Computer> getSomeOrderBy(int limit, int offset, Column column,
			Order order) {
		if (!DbValidator.isLimitOffsetCorrect(limit, offset)) {
			logger.error(
					"get some computer with order by : invalid limit and offset {}/{}",
					limit, offset);
			throw new DAOException(DbValidator.INVALID_LIMIT_OFFSET);
		}
		EntityManager em = emFactory.createEntityManager();
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		JPAQuery query = new JPAQuery(em);
		List<Computer> computers;
		query = query.from(computer).leftJoin(computer.company, company);
		query = orderBy(query, order, column);
		computers = query.limit(limit).offset(offset).list(computer);
		em.close();
		return computers;
	}

	@Override
	public List<Computer> getSomeSearch(int limit, int offset, Column column,
			Order order, String search) {
		if (!DbValidator.isLimitOffsetCorrect(limit, offset)) {
			logger.error(
					"get some computer with order by : invalid limit and offset {}/{}",
					limit, offset);
			throw new DAOException(DbValidator.INVALID_LIMIT_OFFSET);
		}
		EntityManager em = emFactory.createEntityManager();
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		JPAQuery query = new JPAQuery(em);
		List<Computer> computers;
		search = "%" + search + "%";
		query = query
				.from(computer)
				.leftJoin(computer.company, company)
				.where(computer.name.like(search).or(
						computer.company.name.like(search)));
		query = orderBy(query, order, column);
		computers = query.limit(limit).offset(offset).list(computer);
		em.close();
		return computers;
	}

	@Override
	public int getNumberComputerSearch(String search) {
		EntityManager em = emFactory.createEntityManager();
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		JPAQuery query = new JPAQuery(em);
		search = "%" + search + "%";
		int ret = (int) query
				.from(computer)
				.leftJoin(computer.company, company)
				.where(computer.name.like(search).or(
						computer.company.name.like(search))).count();
		em.close();
		return ret;
	}

	@Override
	public void deleteOfCompany(long id) {
		if (!DbValidator.isIdValid(id)) {
			logger.error("delete some computer of a company : {}", id);
			throw new DAOException(
					"Couldn't delete some computer of a company : " + id);
		}
		EntityManager em = emFactory.createEntityManager();
		QComputer computer = QComputer.computer;
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			JPADeleteClause query = new JPADeleteClause(em, computer);
			query.where(computer.company.id.eq(id)).execute();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}
		em.close();
	}
}
