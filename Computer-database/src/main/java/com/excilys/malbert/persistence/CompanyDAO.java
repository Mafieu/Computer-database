package com.excilys.malbert.persistence;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.malbert.exceptions.DAOException;
import com.excilys.malbert.mapper.CompanyMapper;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.validator.DbValidator;

@Repository
public class CompanyDAO implements ICompanyDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    @Override
    public List<Company> getAll() {
	try {
	    return this.jdbcTemplate.query("SELECT * FROM company",
		    new CompanyMapper());
	} catch (DataAccessException e) {
	    logger.error("list company");
	    throw new DAOException("Couldn't get the list of companies");
	}
    }

    @Override
    public Company getOne(long id) {
	if (!DbValidator.isIdValid(id)) {
	    logger.error("get company : {}", id);
	    throw new DAOException(DbValidator.INVALID_ID);
	}
	try {
	    return this.jdbcTemplate.queryForObject(
		    "SELECT * FROM company WHERE id = ?", new Object[] { id },
		    new CompanyMapper());
	} catch (DataAccessException e) {
	    logger.error("get company : {}", id);
	    throw new DAOException("Couldn't get company : " + id);
	}
    }

    @Override
    public void delete(long id) {
	if (!DbValidator.isIdValid(id)) {
	    logger.error("delete commpany : {}", id);
	    throw new DAOException(DbValidator.INVALID_ID);
	}
	try {
	    this.jdbcTemplate.update("DELETE FROM company WHERE id = ?", id);
	} catch (DataAccessException e) {
	    logger.error("delete commpany : {}", id);
	    throw new DAOException("Couldn't delete the company");
	}
    }
}
