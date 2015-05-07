package com.excilys.malbert.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.malbert.controller.dto.ComputerDTO;
import com.excilys.malbert.mapper.MapperComputer;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.service.IServiceCompany;
import com.excilys.malbert.service.IServiceComputer;
import com.excilys.malbert.util.Validator;

/**
 * Contains all the information for the jsp page. After constructing it, you
 * must call isValid() method to validate the data and replace it by default
 * values if incorrect
 * 
 * @author excilys
 */
@Component
public class Page {
    private List<ComputerDTO> data;
    private int totalCount;
    private int countPerPage;
    private int page;
    private String order;
    private String column;
    private String search;

    public Page() {
	this(new ArrayList<ComputerDTO>(), 0, 50, 1, "asc", "computer.id", null);
    }

    public Page(List<ComputerDTO> data, int totalCount, int countPerPage,
	    int page, String order, String column, String search) {
	this.data = data;
	this.totalCount = totalCount;
	this.countPerPage = countPerPage;
	this.page = page;
	this.order = order;
	this.column = column;
	this.search = search;
    }

    public boolean isValid(IServiceComputer sComputer, IServiceCompany sCompany) {
	boolean ret = true;
	if (countPerPage <= 0) {
	    countPerPage = 50;
	    ret = false;
	}
	if (!Validator.isColumnValid(column)) {
	    column = "computer.id";
	    ret = false;
	}
	if (order == null) {
	    order = "asc";
	    ret = false;
	} else if (!order.equals("asc") && !order.equals("desc")) {
	    order = "asc";
	    ret = false;
	}
	if (!isSearchValid()) {
	    totalCount = sComputer.getNumberComputer();
	} else {
	    totalCount = sComputer.getNumberComputerSearch(search);
	}
	if (page <= 0 || page - 1 > totalCount / countPerPage) {
	    page = 1;
	    ret = false;
	}
	List<Computer> list = null;
	if (order.equals("asc")) {
	    if (!isSearchValid()) {
		list = sComputer.getSomeOrderedByAscending(countPerPage,
			(page - 1) * countPerPage, column);
	    } else {
		list = sComputer.getSomeSearch(countPerPage, (page - 1)
			* countPerPage, column, order, search);
	    }
	} else {
	    if (!isSearchValid()) {
		list = sComputer.getSomeOrderedByDescending(countPerPage,
			(page - 1) * countPerPage, column);
	    } else {
		list = sComputer.getSomeSearch(countPerPage, (page - 1)
			* countPerPage, column, order, search);
	    }
	}
	for (Computer computer : list) {
	    data.add(MapperComputer.computerToComputerdto(computer));
	}
	return ret;
    }

    public boolean isSearchValid() {
	if (search == null) {
	    return false;
	} else if (search.equals("")) {
	    return false;
	}
	return true;
    }

    public List<ComputerDTO> getData() {
	return data;
    }

    public void setData(List<ComputerDTO> data) {
	this.data = data;
    }

    public int getTotalCount() {
	return totalCount;
    }

    public void setTotalCount(int totalCount) {
	this.totalCount = totalCount;
    }

    public int getCountPerPage() {
	return countPerPage;
    }

    public void setCountPerPage(int countPerPage) {
	this.countPerPage = countPerPage;
    }

    public int getPage() {
	return page;
    }

    public void setPage(int page) {
	this.page = page;
    }

    public String getOrder() {
	return order;
    }

    public void setOrder(String order) {
	this.order = order;
    }

    public String getColumn() {
	return column;
    }

    public void setColumn(String column) {
	this.column = column;
    }

    public String getSearch() {
	return search;
    }

    public void setSearch(String search) {
	this.search = search;
    }

    @Override
    public String toString() {
	return "Page [data=" + data + ", totalCount=" + totalCount
		+ ", countPerPage=" + countPerPage + ", page=" + page
		+ ", order=" + order + ", column=" + column + ", search="
		+ search + "]";
    }
}
