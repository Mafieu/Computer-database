package com.excilys.malbert.client.service;

import java.util.List;

import com.excilys.malbert.binding.validator.Date.Pattern;
import com.excilys.malbert.core.model.Computer;

public interface IClientService {
	public List<Computer> getAllComputer(Pattern pattern);
	public Computer findComputer(Long id);
}
