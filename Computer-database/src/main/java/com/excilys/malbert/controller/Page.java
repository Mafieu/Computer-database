package com.excilys.malbert.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.malbert.controller.dto.ComputerDTO;
import com.excilys.malbert.mapper.ComputerMapper;
import com.excilys.malbert.persistence.IComputerDAO.Column;
import com.excilys.malbert.persistence.IComputerDAO.Order;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.service.ICompanyService;
import com.excilys.malbert.service.IComputerService;
import com.excilys.malbert.validator.Date.Pattern;

/**
 * Contains all the information for the jsp page. Use toDefault to set default
 * values and then populate to populate the data list
 * 
 * @author excilys
 */
@Component
public class Page {
    private List<ComputerDTO> data;
    private int totalCount;
    private int limit;
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
	this.limit = countPerPage;
	this.page = page;
	this.order = order;
	this.column = column;
	this.search = search;
    }

    public void toDefault() {
	limit = 50;
	column = "computer.id";
	order = "asc";
	page = 1;
    }

    public void populate(IComputerService sComputer, ICompanyService sCompany,
	    Pattern language) {
	List<Computer> list = null;
	Column col = Column.map(column);
	Order ord = Order.map(order);

	if (!isSearchValid()) {
	    totalCount = sComputer.getNumberComputer();
	} else {
	    totalCount = sComputer.getNumberComputerSearch(search);
	}

	if (page - 1 > totalCount / limit) {
	    this.page = 1;
	}

	if (order.equals("asc")) {
	    if (!isSearchValid()) {
		list = sComputer.getSomeOrderedByAscending(limit, (page - 1)
			* limit, col);
	    } else {
		list = sComputer.getSomeSearch(limit, (page - 1) * limit, col,
			ord, search);
	    }
	} else {
	    if (!isSearchValid()) {
		list = sComputer.getSomeOrderedByDescending(limit, (page - 1)
			* limit, col);
	    } else {
		list = sComputer.getSomeSearch(limit, (page - 1) * limit, col,
			ord, search);
	    }
	}
	for (Computer computer : list) {
	    data.add(ComputerMapper.computerToComputerdto(computer, language));
	}
    }

    public boolean isOrderValid() {
	if (order == null) {
	    return false;
	} else if (!order.equals("asc") && order.equals("desc")) {
	    return false;
	}
	return true;
    }

    public boolean isSearchValid() {
	if (search == null) {
	    return false;
	} else if (search.trim().equals("")) {
	    return false;
	} else if (search.contains(" ")) {
	    return false;
	}
	return true;
    }

    public boolean isColumnValid() {
	if (column == null) {
	    return false;
	} else if (!(column.equals("computer.id")
		|| column.equals("computer.name")
		|| column.equals("introduced") || column.equals("discontinued")
		|| column.equals("company.id") || column.equals("company.name"))) {
	    return false;
	} else {
	    return true;
	}
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

    public int getLimit() {
	return limit;
    }

    public void setLimit(int countPerPage) {
	this.limit = countPerPage;
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
		+ ", countPerPage=" + limit + ", page=" + page + ", order="
		+ order + ", column=" + column + ", search=" + search + "]";
    }
}
