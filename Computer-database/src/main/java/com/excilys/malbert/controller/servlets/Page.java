package com.excilys.malbert.controller.servlets;

import java.util.ArrayList;
import java.util.List;

import com.excilys.malbert.util.Validator;

public class Page<T> {
    private List<T> data;
    private int totalCount;
    private int countPerPage;
    private int page;
    private String order;
    private String column;
    private String search;

    public Page() {
	this(new ArrayList<T>(), 0, 0, 0, null, null, null);
    }

    public Page(List<T> data, int totalCount, int countPerPage, int page,
	    String order, String column, String search) {
	this.data = data;
	this.totalCount = totalCount;
	this.countPerPage = countPerPage;
	this.page = page;
	this.order = order;
	this.column = column;
	this.search = search;
    }

    public boolean isValid() {
	boolean ret = true;
	if (countPerPage <= 0) {
	    countPerPage = 50;
	    ret = false;
	}
	if (page <= 0 || page - 1 > totalCount / countPerPage) {
	    page = 1;
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
	return ret;
    }

    public List<T> getData() {
	return data;
    }

    public void setData(List<T> data) {
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

}
