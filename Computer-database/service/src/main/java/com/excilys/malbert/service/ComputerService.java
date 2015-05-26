package com.excilys.malbert.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.malbert.core.exception.ServiceException;
import com.excilys.malbert.core.model.Computer;
import com.excilys.malbert.persistence.IComputerDAO;
import com.excilys.malbert.persistence.IComputerDAO.Column;
import com.excilys.malbert.persistence.IComputerDAO.Order;
import com.excilys.malbert.persistence.validator.DbValidator;

@Service
public class ComputerService implements IComputerService {

	@Autowired
	private IComputerDAO computerDAO;

	@Override
	public List<Computer> getAllComputers() {
		return computerDAO.getAll();
	}

	@Override
	public List<Computer> getSome(Integer limit, Integer offset) {
		if (!DbValidator.isLimitOffsetCorrect(limit, offset)) {
			throw new ServiceException(DbValidator.INVALID_LIMIT_OFFSET);
		}

		return computerDAO.getSome(limit, offset);
	}

	@Override
	public Computer getComputer(Long id) {
		if (!DbValidator.isIdValid(id)) {
			throw new ServiceException(DbValidator.INVALID_ID);
		}

		return computerDAO.getOne(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void createComputer(Computer computer) {
		if (!DbValidator.isComputerValid(computer)) {
			throw new ServiceException(DbValidator.INVALID_COMPUTER);
		}

		computerDAO.create(computer);
		
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteComputer(Long id) {
		if (!DbValidator.isIdValid(id)) {
			throw new ServiceException(DbValidator.INVALID_ID);
		}
		computerDAO.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
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
	public List<Computer> getSomeOrderedByAscending(Integer limit, Integer offset,
			Column column) {
		if (!DbValidator.isLimitOffsetCorrect(limit, offset)) {
			throw new ServiceException(DbValidator.INVALID_LIMIT_OFFSET);
		}

		return computerDAO.getSomeOrderedByAscending(limit, offset, column);
	}

	@Override
	public List<Computer> getSomeOrderedByDescending(Integer limit, Integer offset,
			Column column) {
		if (!DbValidator.isLimitOffsetCorrect(limit, offset)) {
			throw new ServiceException(DbValidator.INVALID_LIMIT_OFFSET);
		}

		return computerDAO.getSomeOrderedByDescending(limit, offset, column);
	}

	@Override
	public List<Computer> getSomeSearch(Integer limit, Integer offset, Column column,
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
}
