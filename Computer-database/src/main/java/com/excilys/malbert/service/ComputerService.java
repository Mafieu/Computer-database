package com.excilys.malbert.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.malbert.exceptions.ServiceException;
import com.excilys.malbert.model.Computer;
import com.excilys.malbert.persistence.ComputerDAO;
import com.excilys.malbert.persistence.IComputerDAO.Column;
import com.excilys.malbert.persistence.IComputerDAO.Order;
import com.excilys.malbert.validator.DbValidator;

@Service
public class ComputerService implements IComputerService {

	@Autowired
	private ComputerDAO computerDAO;

	@Override
	public List<Computer> getAllComputers() {
		return computerDAO.getAll();
	}

	@Override
	public List<Computer> getSome(int limit, int offset) {
		if (!DbValidator.isLimitOffsetCorrect(limit, offset)) {
			throw new ServiceException(DbValidator.INVALID_LIMIT_OFFSET);
		}

		return computerDAO.getSome(limit, offset);
	}

	@Override
	public Computer getComputer(long id) {
		if (!DbValidator.isIdValid(id)) {
			throw new ServiceException(DbValidator.INVALID_ID);
		}

		return computerDAO.getOne(id);
	}

	@Override
	public void createComputer(Computer computer) {
		if (!DbValidator.isComputerValid(computer)) {
			throw new ServiceException(DbValidator.INVALID_COMPUTER);
		}

		computerDAO.create(computer);
	}

	@Override
	public void deleteComputer(long id) {
		if (!DbValidator.isIdValid(id)) {
			throw new ServiceException(DbValidator.INVALID_ID);
		}
		computerDAO.delete(id);
	}

	@Override
	public void updateComputer(Computer computer) {
		if (!DbValidator.isComputerValid(computer)) {
			throw new ServiceException(DbValidator.INVALID_COMPUTER);
		}
		computerDAO.update(computer);
	}

	@Override
	public int getNumberComputer() {
		return computerDAO.getNumberComputer();
	}

	@Override
	public List<Computer> getSomeOrderedByAscending(int limit, int offset,
			Column column) {
		if (!DbValidator.isLimitOffsetCorrect(limit, offset)) {
			throw new ServiceException(DbValidator.INVALID_LIMIT_OFFSET);
		}

		return computerDAO.getSomeOrderedByAscending(limit, offset, column);
	}

	@Override
	public List<Computer> getSomeOrderedByDescending(int limit, int offset,
			Column column) {
		if (!DbValidator.isLimitOffsetCorrect(limit, offset)) {
			throw new ServiceException(DbValidator.INVALID_LIMIT_OFFSET);
		}

		return computerDAO.getSomeOrderedByDescending(limit, offset, column);
	}

	@Override
	public List<Computer> getSomeSearch(int limit, int offset, Column column,
			Order order, String search) {
		if (!DbValidator.isLimitOffsetCorrect(limit, offset)) {
			throw new ServiceException(DbValidator.INVALID_LIMIT_OFFSET);
		}

		return computerDAO.getSomeSearch(limit, offset, column, order, search);
	}

	@Override
	public int getNumberComputerSearch(String search) {
		return computerDAO.getNumberComputerSearch(search);
	}

	@Override
	public void setComputerDAO(ComputerDAO daoComputer) {
		this.computerDAO = daoComputer;
	}
}
