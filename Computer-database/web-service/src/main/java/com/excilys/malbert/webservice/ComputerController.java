package com.excilys.malbert.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.malbert.binding.ComputerMapper;
import com.excilys.malbert.binding.model.ComputerDTO;
import com.excilys.malbert.binding.validator.Date.Pattern;
import com.excilys.malbert.core.model.Computer;
import com.excilys.malbert.persistence.IComputerDAO.Column;
import com.excilys.malbert.persistence.IComputerDAO.Order;
import com.excilys.malbert.service.IComputerService;

@RestController
@RequestMapping("/computer")
public class ComputerController {

	@Autowired
	IComputerService computerService;

	@RequestMapping(method = RequestMethod.GET, value = "/getAll")
	public Object[] getAll() {
		final Pattern pattern = Pattern.EN;
		return computerService.getAllComputers().stream()
				.map(c -> ComputerMapper.computerToComputerdto(c, pattern))
				.toArray();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/find")
	public ComputerDTO find(@RequestParam(value = "id") Long id) {
		Pattern pattern = Pattern.EN;
		return ComputerMapper.computerToComputerdto(
				computerService.getComputer(id), pattern);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getList")
	public Object[] getList(
			@RequestParam(value = "limit") Integer limit,
			@RequestParam(value = "offset") Integer offset,
			@RequestParam(value = "column", defaultValue = "computer.id") String sColumn,
			@RequestParam(value = "order", defaultValue = "asc") String sOrder,
			@RequestParam(value = "search", defaultValue = "") String search) {
		Order order = Order.map(sOrder);
		Column column = Column.map(sColumn);
		if (order == null)
			order = Order.ASC;
		if (column == null)
			column = Column.ID;
		final Pattern pattern = Pattern.EN;
		return computerService.getList(limit, offset, column, order, search)
				.stream()
				.map(c -> ComputerMapper.computerToComputerdto(c, pattern))
				.toArray();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getCount")
	public Integer getCount(
			@RequestParam(value = "search", defaultValue = "") String search) {
		if (search == null || search.equals("")) {
			return computerService.getNumberComputer();
		}
		return computerService.getNumberComputerSearch(search);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete")
	public void delete(@RequestParam(value = "id") Long id) {
		computerService.deleteComputer(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public ComputerDTO create(@RequestBody ComputerDTO computerDTO) {
		Pattern pattern = Pattern.EN;
		Computer computer = ComputerMapper.computerdtoToComputer(computerDTO,
				pattern);
		computerService.createComputer(computer);
		return ComputerMapper.computerToComputerdto(computer, pattern);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public void update(@RequestBody ComputerDTO computerDTO) {
		Pattern pattern = Pattern.EN;
		Computer computer = ComputerMapper.computerdtoToComputer(computerDTO,
				pattern);
		computerService.updateComputer(computer);
	}
}
