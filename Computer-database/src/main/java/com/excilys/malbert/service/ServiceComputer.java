package com.excilys.malbert.service;

import java.util.List;

import com.excilys.malbert.exceptions.ServiceException;
import com.excilys.malbert.persistence.DAOComputer;
import com.excilys.malbert.persistence.IDAOComputer;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.util.Validator;

/**
 * Describes the services
 * 
 * @author excilys
 *
 */
public enum ServiceComputer {
    INSTANCE;

    private IDAOComputer daoComputer = DAOComputer.INSTANCE;

    public void setComputerDAO(IDAOComputer daoComputer) {
	this.daoComputer = daoComputer;
    }

    public List<Computer> getAllComputers() {
	return daoComputer.getAll();
    }

    public List<Computer> getSome(int limit, int offset) {
	if (!Validator.isLimitOffsetCorrect(limit, offset)) {
	    throw new ServiceException(Validator.INVALID_LIMIT_OFFSET);
	}

	return daoComputer.getSome(limit, offset);
    }

    public Computer getComputer(long id) {
	if (!Validator.isIdValid(id)) {
	    throw new ServiceException(Validator.INVALID_ID);
	}

	return daoComputer.getComputer(id);
    }

    public long createComputer(Computer computer) {
	if (!Validator.isComputerValid(computer)) {
	    throw new ServiceException(Validator.INVALID_COMPUTER);
	}

	return daoComputer.create(computer);
    }

    public void deleteComputer(long id) {
	if (!Validator.isIdValid(id)) {
	    throw new ServiceException(Validator.INVALID_ID);
	}
	daoComputer.delete(id);
    }

    public void updateComputer(Computer computer) {
	if (!Validator.isComputerValid(computer)) {
	    throw new ServiceException(Validator.INVALID_COMPUTER);
	}
	daoComputer.update(computer);
    }

    public int getNumberComputer() {
	return daoComputer.getNumberComputer();
    }

    public List<Computer> getSomeOrderedByAscending(int limit, int offset,
	    String column) {
	if (!Validator.isLimitOffsetCorrect(limit, offset)) {
	    throw new ServiceException(Validator.INVALID_LIMIT_OFFSET);
	}
	if (!Validator.isColumnValid(column)) {
	    throw new ServiceException(Validator.INVALID_COLUMN);
	}

	return daoComputer.getSomeOrderedByAscending(limit, offset, column);
    }

    public List<Computer> getSomeOrderedByDescending(int limit, int offset,
	    String column) {
	if (!Validator.isLimitOffsetCorrect(limit, offset)) {
	    throw new ServiceException(Validator.INVALID_LIMIT_OFFSET);
	}
	if (!Validator.isColumnValid(column)) {
	    throw new ServiceException(Validator.INVALID_COLUMN);
	}

	return daoComputer.getSomeOrderedByDescending(limit, offset, column);
    }
}
