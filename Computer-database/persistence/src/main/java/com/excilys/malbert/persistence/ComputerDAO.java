package com.excilys.malbert.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.malbert.core.exception.DAOException;
import com.excilys.malbert.core.model.Computer;
import com.excilys.malbert.core.model.QCompany;
import com.excilys.malbert.core.model.QComputer;
import com.excilys.malbert.persistence.validator.DbValidator;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
@Transactional(readOnly = true)
public class ComputerDAO implements IComputerDAO {

	@PersistenceContext
	EntityManager em;
	private Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	@Override
	public List<Computer> getAll() {
		QComputer computer = QComputer.computer;
		JPAQuery query = new JPAQuery(em);
		List<Computer> computers = query.from(computer).list(computer);
		return computers;
	}

	@Override
	public List<Computer> getSome(Integer limit, Integer offset) {
		return getSomeOrderedByAscending(limit, offset, Column.ID);
	}

	@Override
	public Computer getOne(Long id) {
		if (!DbValidator.isIdValid(id)) {
			logger.error("get computer : {}", id);
			throw new DAOException(DbValidator.INVALID_ID);
		}
		QComputer computer = QComputer.computer;
		JPAQuery query = new JPAQuery(em);
		Computer comp = query.from(computer).where(computer.id.eq(id))
				.uniqueResult(computer);
		return comp;
	}

	@Override
	@Transactional(readOnly = false)
	public void create(Computer computer) {
		if (!DbValidator.isComputerValid(computer)) {
			logger.error("create computer : invalid computer");
			throw new DAOException(DbValidator.INVALID_COMPUTER);
		}
		Computer c = em.merge(computer);
		em.persist(c);
		computer.setId(c != null ? c.getId() : null);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		if (!DbValidator.isIdValid(id)) {
			logger.error("delete computer : invalid id");
			throw new DAOException(DbValidator.INVALID_ID);
		}
		QComputer computer = QComputer.computer;
		JPADeleteClause query = new JPADeleteClause(em, computer);
		query.where(computer.id.eq(id)).execute();
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Computer computer) {
		if (!DbValidator.isComputerValid(computer)) {
			logger.error("update computer : invalid computer");
			throw new DAOException(DbValidator.INVALID_COMPUTER);
		}
		em.merge(computer);
	}

	@Override
	public int getNumberComputer() {
		QComputer computer = QComputer.computer;
		JPAQuery query = new JPAQuery(em);
		int nb = (int) query.from(computer).count();
		return nb;
	}

	@Override
	public List<Computer> getSomeOrderedByAscending(Integer limit,
			Integer offset, Column column) {
		return getSomeOrderBy(limit, offset, column, Order.ASC);
	}

	@Override
	public List<Computer> getSomeOrderedByDescending(Integer limit,
			Integer offset, Column column) {
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

	private List<Computer> getSomeOrderBy(Integer limit, Integer offset,
			Column column, Order order) {
		if (!DbValidator.isLimitOffsetCorrect(limit, offset)) {
			logger.error(
					"get some computer with order by : invalid limit and offset {}/{}",
					limit, offset);
			throw new DAOException(DbValidator.INVALID_LIMIT_OFFSET);
		}
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		JPAQuery query = new JPAQuery(em);
		List<Computer> computers;
		query = query.from(computer).leftJoin(computer.company, company);
		query = orderBy(query, order, column);
		computers = query.limit(limit).offset(offset).list(computer);
		return computers;
	}

	@Override
	public List<Computer> getSomeSearch(Integer limit, Integer offset,
			Column column, Order order, String search) {
		if (!DbValidator.isLimitOffsetCorrect(limit, offset)) {
			logger.error(
					"get some computer with order by : invalid limit and offset {}/{}",
					limit, offset);
			throw new DAOException(DbValidator.INVALID_LIMIT_OFFSET);
		}
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		JPAQuery query = new JPAQuery(em);
		List<Computer> computers;
		search = "%" + search + "%";
		if (column == null) {
			column = Column.ID;
		}
		if (order == null) {
			order = Order.ASC;
		}
		query = query
				.from(computer)
				.leftJoin(computer.company, company)
				.where(computer.name.like(search).or(
						computer.company.name.like(search)));
		query = orderBy(query, order, column);
		computers = query.limit(limit).offset(offset).list(computer);
		return computers;
	}

	@Override
	public int getNumberComputerSearch(String search) {
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		JPAQuery query = new JPAQuery(em);
		search = "%" + search + "%";
		int ret = (int) query
				.from(computer)
				.leftJoin(computer.company, company)
				.where(computer.name.like(search).or(
						computer.company.name.like(search))).count();
		return ret;
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteOfCompany(Long id) {
		if (!DbValidator.isIdValid(id)) {
			logger.error("delete some computer of a company : {}", id);
			throw new DAOException(
					"Couldn't delete some computer of a company : " + id);
		}
		QComputer computer = QComputer.computer;
		JPADeleteClause query = new JPADeleteClause(em, computer);
		query.where(computer.company.id.eq(id)).execute();
	}
}
