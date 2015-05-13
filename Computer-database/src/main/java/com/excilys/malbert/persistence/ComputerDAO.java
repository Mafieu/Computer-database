package com.excilys.malbert.persistence;

import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.malbert.exceptions.DAOException;
import com.excilys.malbert.mapper.ComputerMapper;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.util.Utils;
import com.excilys.malbert.validator.DbValidator;

@Repository
public class ComputerDAO implements IComputerDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

    @Override
    public List<Computer> getAll() {
	try {
	    return this.namedJdbcTemplate
		    .query("SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id",
			    new ComputerMapper());
	} catch (DataAccessException e) {
	    logger.error("list company");
	    throw new DAOException("Couldn't get the list of companies");
	}
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
	try {
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("id", id);
	    return this.namedJdbcTemplate
		    .queryForObject(
			    "SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id WHERE computer.id = :id",
			    parameters, new ComputerMapper());
	} catch (DataAccessException e) {
	    return null;
	}
    }

    @Override
    public void create(Computer computer) {
	if (!DbValidator.isComputerValid(computer)) {
	    logger.error("create computer : invalid computer");
	    throw new DAOException(DbValidator.INVALID_COMPUTER);
	}
	KeyHolder keyHolder = new GeneratedKeyHolder();
	try {
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("name", computer.getName());
	    parameters.addValue("introduced",
		    Utils.localdatetimeToTimestamp(computer.getIntroduced()));
	    parameters.addValue("discontinued",
		    Utils.localdatetimeToTimestamp(computer.getDiscontinued()));
	    parameters.addValue("company", computer.getCompany() == null ? null
		    : computer.getCompany().getId() == 0 ? null : computer
			    .getCompany().getId());
	    this.namedJdbcTemplate
		    .update("INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (:name, :introduced, :discontinued, :company)",
			    parameters, keyHolder);
	    computer.setId((long) keyHolder.getKey());
	} catch (DataAccessException e) {
	    logger.error("create computer : {}", computer.toString());
	    throw new DAOException("Couldn't create the computer");
	}
    }

    @Override
    public void delete(long id) {
	if (!DbValidator.isIdValid(id)) {
	    logger.error("delete computer : invalid id");
	    throw new DAOException(DbValidator.INVALID_ID);
	}
	try {
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("id", id);
	    this.namedJdbcTemplate.update(
		    "DELETE FROM computer WHERE id = :id", parameters);
	} catch (DataAccessException e) {
	    logger.error("delete computer : {}", id);
	    throw new DAOException("Coudln't delete computer : " + id);
	}
    }

    @Override
    public void update(Computer computer) {
	if (!DbValidator.isComputerValid(computer)) {
	    logger.error("update computer : invalid computer");
	    throw new DAOException(DbValidator.INVALID_COMPUTER);
	}

	try {
	    this.jdbcTemplate
		    .update("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?",
			    new Object[] {
				    computer.getName(),
				    Utils.localdatetimeToTimestamp(computer
					    .getIntroduced()),
				    Utils.localdatetimeToTimestamp(computer
					    .getDiscontinued()),
				    computer.getCompany() == null ? null
					    : computer.getCompany().getId(),
				    computer.getId() }, new int[] {
				    Types.VARCHAR,
				    Types.TIMESTAMP,
				    Types.TIMESTAMP,
				    computer.getCompany() == null ? Types.NULL
					    : Types.BIGINT, Types.BIGINT });
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
	    Column column) {
	return getSomeOrderBy(limit, offset, column, Order.ASC);
    }

    @Override
    public List<Computer> getSomeOrderedByDescending(int limit, int offset,
	    Column column) {
	return getSomeOrderBy(limit, offset, column, Order.DESC);
    }

    private List<Computer> getSomeOrderBy(int limit, int offset, Column column,
	    Order order) {
	if (!DbValidator.isLimitOffsetCorrect(limit, offset)) {
	    logger.error(
		    "get some computer with order by : invalid limit and offset {}/{}",
		    limit, offset);
	    throw new DAOException(DbValidator.INVALID_LIMIT_OFFSET);
	}

	try {
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("limit", limit);
	    parameters.addValue("offset", offset);
	    return this.namedJdbcTemplate
		    .query(new StringBuilder(
			    "SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id ORDER BY + ")
			    .append(column.toString()).append(" ")
			    .append(order.toString())
			    .append("  LIMIT :limit OFFSET :offset").toString(),
			    parameters, new ComputerMapper());
	} catch (DataAccessException e) {
	    logger.error("get some computer with order by : {}, {}, {}, {}",
		    limit, offset, column, order);
	    throw new DAOException("Couldn't get the list of Computers from "
		    + offset + " to " + (offset + limit) + " ordered by "
		    + column);
	}
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

	try {
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("search", "%" + search + "%");
	    parameters.addValue("limit", limit);
	    parameters.addValue("offset", offset);
	    return this.namedJdbcTemplate
		    .query(new StringBuilder(
			    "SELECT * FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id WHERE computer.name LIKE :search OR company.name LIKE :search ORDER BY ")
			    .append(column.toString()).append(" ")
			    .append(order.toString())
			    .append(" LIMIT :limit OFFSET :offset").toString(),
			    parameters, new ComputerMapper());
	} catch (DataAccessException e) {
	    e.printStackTrace();
	    logger.error(
		    "get some computer with order by and search : {}, {}, {}, {}, {}",
		    limit, offset, column, order, search);
	    throw new DAOException("Couldn't get the list of Computers from "
		    + offset + " to " + (offset + limit) + " ordered by "
		    + column + " with the search of " + search);
	}
    }

    @Override
    public int getNumberComputerSearch(String search) {
	try {
	    search = "%" + search + "%";
	    return this.jdbcTemplate
		    .queryForObject(
			    "SELECT count(*) FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?",
			    new Object[] { search, search }, Integer.class);
	} catch (DataAccessException e) {
	    logger.error("get count of computer according {}", search);
	    throw new DAOException(
		    "Couldn't get the number of Computers with the search of "
			    + search);
	}
    }

    @Override
    public void deleteOfCompany(long id) {
	if (!DbValidator.isIdValid(id)) {
	    logger.error("delete some computer of a company : {}", id);
	    throw new DAOException(
		    "Couldn't delete some computer of a company : " + id);
	}
	try {
	    this.jdbcTemplate.update(
		    "DELETE FROM computer WHERE company.id = ?", id);
	} catch (DataAccessException e) {
	    logger.error("delete some computer of a company : {}", id);
	    throw new DAOException(
		    "Couldn't delete some computer of a company : " + id);
	}
    }
}
