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
public enum ServiceComputer implements IServiceComputer {
    INSTANCE;

    private IDAOComputer daoComputer = DAOComputer.INSTANCE;

    @Override
    public List<Computer> getAllComputers() {
	return daoComputer.getAll();
    }

    @Override
    public List<Computer> getSome(int limit, int offset) {
	if (!Validator.isLimitOffsetCorrect(limit, offset)) {
	    throw new ServiceException(Validator.INVALID_LIMIT_OFFSET);
	}

	return daoComputer.getSome(limit, offset);
    }

    @Override
    public Computer getComputer(long id) {
	if (!Validator.isIdValid(id)) {
	    throw new ServiceException(Validator.INVALID_ID);
	}

	return daoComputer.getComputer(id);
    }

    @Override
    public long createComputer(Computer computer) {
	if (!Validator.isComputerValid(computer)) {
	    throw new ServiceException(Validator.INVALID_COMPUTER);
	}

	return daoComputer.create(computer);
    }

    @Override
    public void deleteComputer(long id) {
	if (!Validator.isIdValid(id)) {
	    throw new ServiceException(Validator.INVALID_ID);
	}
	daoComputer.delete(id);
    }

    @Override
    public void updateComputer(Computer computer) {
	if (!Validator.isComputerValid(computer)) {
	    throw new ServiceException(Validator.INVALID_COMPUTER);
	}
	daoComputer.update(computer);
    }

    @Override
    public int getNumberComputer() {
	return daoComputer.getNumberComputer();
    }

    @Override
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

    @Override
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

    @Override
    public List<Computer> getSomeSearch(int limit, int offset, String column,
	    String order, String search) {
	if (!Validator.isLimitOffsetCorrect(limit, offset)) {
	    throw new ServiceException(Validator.INVALID_LIMIT_OFFSET);
	}
	if (!Validator.isColumnValid(column)) {
	    throw new ServiceException(Validator.INVALID_COLUMN);
	}

	return daoComputer.getSomeSearch(limit, offset, column, order, search);
    }

    @Override
    public int getNumberComputerSearch(String search) {
	return daoComputer.getNumberComputerSearch(search);
    }

    @Override
    public void setComputerDAO(IDAOComputer daoComputer) {
	this.daoComputer = daoComputer;
    }
}
