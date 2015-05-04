package com.excilys.malbert.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.malbert.exceptions.ServiceException;
import com.excilys.malbert.persistence.DAOComputer;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.util.Validator;

@Service
public class ServiceComputer implements IServiceComputer {

    @Autowired
    private DAOComputer computerDAO;

    @Override
    public List<Computer> getAllComputers() {
	return computerDAO.getAll();
    }

    @Override
    public List<Computer> getSome(int limit, int offset) {
	if (!Validator.isLimitOffsetCorrect(limit, offset)) {
	    throw new ServiceException(Validator.INVALID_LIMIT_OFFSET);
	}

	return computerDAO.getSome(limit, offset);
    }

    @Override
    public Computer getComputer(long id) {
	if (!Validator.isIdValid(id)) {
	    throw new ServiceException(Validator.INVALID_ID);
	}

	return computerDAO.getOne(id);
    }

    @Override
    public long createComputer(Computer computer) {
	if (!Validator.isComputerValid(computer)) {
	    throw new ServiceException(Validator.INVALID_COMPUTER);
	}

	return computerDAO.create(computer);
    }

    @Override
    public void deleteComputer(long id) {
	if (!Validator.isIdValid(id)) {
	    throw new ServiceException(Validator.INVALID_ID);
	}
	computerDAO.delete(id);
    }

    @Override
    public void updateComputer(Computer computer) {
	if (!Validator.isComputerValid(computer)) {
	    throw new ServiceException(Validator.INVALID_COMPUTER);
	}
	computerDAO.update(computer);
    }

    @Override
    public int getNumberComputer() {
	return computerDAO.getNumberComputer();
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

	return computerDAO.getSomeOrderedByAscending(limit, offset, column);
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

	return computerDAO.getSomeOrderedByDescending(limit, offset, column);
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

	return computerDAO.getSomeSearch(limit, offset, column, order, search);
    }

    @Override
    public int getNumberComputerSearch(String search) {
	return computerDAO.getNumberComputerSearch(search);
    }

    @Override
    public void setComputerDAO(DAOComputer daoComputer) {
	this.computerDAO = daoComputer;
    }
}
