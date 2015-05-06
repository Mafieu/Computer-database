package com.excilys.malbert.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.malbert.exceptions.DAOException;
import com.excilys.malbert.mapper.MapperComputer;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.util.Utils;
import com.excilys.malbert.util.Validator;

@Repository
public class DAOComputer implements IDAOComputer {

    private JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(DAOComputer.class);

    @Autowired
    public void setDataSource(DataSource datasource) {
	this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public List<Computer> getAll() {
	try {
	    return this.jdbcTemplate
		    .query("SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id",
			    new MapperComputer());
	} catch (DataAccessException e) {
	    logger.error("list company");
	    throw new DAOException("Couldn't get the list of companies");
	}
    }

    @Override
    public List<Computer> getSome(int limit, int offset) {
	return getSomeOrderedByAscending(limit, offset, "computer.id");
    }

    @Override
    public Computer getOne(long id) {
	if (!Validator.isIdValid(id)) {
	    logger.error("get computer : {}", id);
	    throw new DAOException(Validator.INVALID_ID);
	}
	try {
	    return this.jdbcTemplate
		    .queryForObject(
			    "SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id WHERE computer.id = ?",
			    new Object[] { id }, new MapperComputer());
	} catch (DataAccessException e) {
	    return null;
	}
    }

    @Override
    public long create(Computer computer) {
	if (!Validator.isComputerValid(computer)) {
	    logger.error("create computer : invalid computer");
	    throw new DAOException(Validator.INVALID_COMPUTER);
	}
	KeyHolder keyHolder = new GeneratedKeyHolder();
	try {
	    this.jdbcTemplate.update(new PreparedStatementCreator() {
		public PreparedStatement createPreparedStatement(
			Connection connection) throws SQLException {
		    PreparedStatement ps = connection
			    .prepareStatement(
				    "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)",
				    new String[] { "id" });
		    ps.setString(1, computer.getName());
		    ps.setTimestamp(2, Utils.localdatetimeToTimestamp(computer
			    .getIntroduced()));
		    ps.setTimestamp(3, Utils.localdatetimeToTimestamp(computer
			    .getDiscontinued()));
		    ps.setLong(4, computer.getCompany().getId());
		    return ps;
		}
	    }, keyHolder);
	    return (long) keyHolder.getKey();
	} catch (DataAccessException e) {
	    logger.error("create computer : {}", computer.toString());
	    throw new DAOException("Couldn't create the computer");
	}
    }

    @Override
    public void delete(long id) {
	if (!Validator.isIdValid(id)) {
	    logger.error("delete computer : invalid id");
	    throw new DAOException(Validator.INVALID_ID);
	}
	try {
	    this.jdbcTemplate.update("DELETE FROM computer WHERE id = ?", id);
	} catch (DataAccessException e) {
	    logger.error("delete computer : {}", id);
	    throw new DAOException("Coudln't delete computer : " + id);
	}
    }

    @Override
    public void update(Computer computer) {
	if (!Validator.isComputerValid(computer)) {
	    logger.error("update computer : invalid computer");
	    throw new DAOException(Validator.INVALID_COMPUTER);
	}

	try {
	    this.jdbcTemplate
		    .update("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?",
			    computer.getName(), Utils
				    .localdatetimeToTimestamp(computer
					    .getIntroduced()), Utils
				    .localdatetimeToTimestamp(computer
					    .getDiscontinued()), computer
				    .getCompany().getId(), computer.getId());
	} catch (DataAccessException e) {
	    logger.error("update computer : {}", computer.toString());
	    throw new DAOException("Couldn't update the computer");
	}
    }

    @Override
    public int getNumberComputer() {
	try {
	    return this.jdbcTemplate.queryForObject(
		    "select count(*) from computer", Integer.class);
	} catch (DataAccessException e) {
	    logger.error("count of computers");
	    throw new DAOException("Couldn't get the number of computers");
	}
    }

    @Override
    public List<Computer> getSomeOrderedByAscending(int limit, int offset,
	    String column) {
	return getSomeOrderBy(limit, offset, column, "ASC");
    }

    @Override
    public List<Computer> getSomeOrderedByDescending(int limit, int offset,
	    String column) {
	return getSomeOrderBy(limit, offset, column, "DESC");
    }

    private List<Computer> getSomeOrderBy(int limit, int offset, String column,
	    String order) {
	if (!Validator.isLimitOffsetCorrect(limit, offset)) {
	    logger.error(
		    "get some computer with order by : invalid limit and offset {}/{}",
		    limit, offset);
	    throw new DAOException(Validator.INVALID_LIMIT_OFFSET);
	}
	if (!Validator.isColumnValid(column)) {
	    logger.error("get some computer with order by : invalid column {}",
		    column);
	    throw new DAOException(Validator.INVALID_COLUMN);
	}

	try {
	    return this.jdbcTemplate
		    .query("SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id ORDER BY "
			    + column + " " + order + " LIMIT ? OFFSET ?",
			    new Object[] { limit, offset },
			    new MapperComputer());
	} catch (DataAccessException e) {
	    logger.error("get some computer with order by : {}, {}, {}, {}",
		    limit, offset, column, order);
	    throw new DAOException("Couldn't get the list of Computers from "
		    + offset + " to " + (offset + limit) + " ordered by "
		    + column);
	}
    }

    @Override
    public List<Computer> getOfCompany(long id) {
	if (!Validator.isIdValid(id)) {
	    logger.error("get computers of company : invalid id");
	    throw new DAOException(Validator.INVALID_ID);
	}

	try {
	    return this.jdbcTemplate
		    .query("SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id WHERE company.id = ?",
			    new Object[] { id }, new MapperComputer());
	} catch (DataAccessException e) {
	    logger.error("get computers of company : {}", id);
	    throw new DAOException(
		    "Couldn't get the list of Computers of the company " + id);
	}
    }

    @Override
    public List<Computer> getSomeSearch(int limit, int offset, String column,
	    String order, String search) {
	if (!Validator.isLimitOffsetCorrect(limit, offset)) {
	    logger.error(
		    "get some computer with order by : invalid limit and offset {}/{}",
		    limit, offset);
	    throw new DAOException(Validator.INVALID_LIMIT_OFFSET);
	}
	if (!Validator.isColumnValid(column)) {
	    logger.error("get some computer with order by : invalid column {}",
		    column);
	    throw new DAOException(Validator.INVALID_COLUMN);
	}

	try {
	    return this.jdbcTemplate
		    .query("SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id WHERE computer.name LIKE '%"
			    + search
			    + "%' OR company.name LIKE '%"
			    + search
			    + "%' ORDER BY "
			    + column
			    + " "
			    + order
			    + " LIMIT ? OFFSET ?",
			    new Object[] { limit, offset },
			    new MapperComputer());
	} catch (DataAccessException e) {
	    logger.error("get some computer with order by : {}, {}, {}, {}",
		    limit, offset, column, order);
	    throw new DAOException("Couldn't get the list of Computers from "
		    + offset + " to " + (offset + limit) + " ordered by "
		    + column + " with the search of " + search);
	}
    }

    @Override
    public int getNumberComputerSearch(String search) {
	try {
	    return this.jdbcTemplate
		    .queryForObject(
			    "SELECT count(*) FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id WHERE computer.name LIKE '%"
				    + search
				    + "%' OR company.name LIKE '%"
				    + search + "%'", Integer.class);
	} catch (DataAccessException e) {
	    logger.error("get count of computer according {}", search);
	    throw new DAOException(
		    "Couldn't get the number of Computers with the search of "
			    + search);
	}
    }

}
