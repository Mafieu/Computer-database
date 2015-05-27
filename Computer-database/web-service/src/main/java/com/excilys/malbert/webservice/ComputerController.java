package com.excilys.malbert.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.malbert.binding.ComputerMapper;
import com.excilys.malbert.binding.model.ComputerDTO;
import com.excilys.malbert.binding.validator.Date.Pattern;
import com.excilys.malbert.persistence.IComputerDAO.Column;
import com.excilys.malbert.persistence.IComputerDAO.Order;
import com.excilys.malbert.service.IComputerService;

@RestController
@RequestMapping("/computer")
public class ComputerController {

	@Autowired
	IComputerService computerService;

	// TODO Set all request methods to POST
	@RequestMapping(value = "/getAll")
	public Object[] getAll(
			@RequestParam(value = "lang", defaultValue = "en") String lang) {
		Pattern patternTmp = Pattern.map(lang);
		if (patternTmp == null) {
			patternTmp = Pattern.EN;
		}
		final Pattern pattern = patternTmp;
		return computerService.getAllComputers().stream()
				.map(c -> ComputerMapper.computerToComputerdto(c, pattern))
				.toArray();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/find")
	public ComputerDTO find(@RequestParam(value = "id") Long id,
			@RequestParam(value = "lang", defaultValue = "en") String lang) {
		Pattern pattern = Pattern.map(lang);
		if (pattern == null) {
			pattern = Pattern.EN;
		}
		return ComputerMapper.computerToComputerdto(
				computerService.getComputer(id), pattern);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getList")
	public Object[] getList(
			@RequestParam(value = "limit") Integer limit,
			@RequestParam(value = "offset") Integer offset,
			@RequestParam(value = "column", defaultValue = "computer.id") String sColumn,
			@RequestParam(value = "order", defaultValue = "asc") String sOrder,
			@RequestParam(value = "search", defaultValue = "") String search,
			@RequestParam(value = "lang", defaultValue = "en") String lang) {
		Order order = Order.map(sOrder);
		Column column = Column.map(sColumn);
		Pattern patternTmp = Pattern.map(lang);
		if (order == null)
			order = Order.ASC;
		if (column == null)
			column = Column.ID;
		if (patternTmp == null)
			patternTmp = Pattern.EN;
		final Pattern pattern = patternTmp;
		return computerService.getList(limit, offset, column, order, search)
				.stream()
				.map(c -> ComputerMapper.computerToComputerdto(c, pattern))
				.toArray();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getCount")
	public Integer getNumberComputer(@RequestParam(value = "search", defaultValue = "") String search){
		if(search == null || search.equals("")){
			return computerService.getNumberComputer();
		}
		return computerService.getNumberComputerSearch(search);
	}
	
	
}
