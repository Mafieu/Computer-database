package com.excilys.malbert.client.CLI;

import java.util.List;

import com.excilys.malbert.persistence.CrudCompanies;
import com.excilys.malbert.persistence.CrudComputer;
import com.excilys.malbert.persistence.model.Entity;

public class Cli {

    public static void main(String[] args) {
	CrudComputer computers = new CrudComputer();
	CrudCompanies companies = new CrudCompanies();
	List<Entity> list = computers.getAll();
	for (Entity computer : list) {
	    System.out.println(computer.toString());
	}
	list.clear();
	list = companies.getAll();
	for (Entity company : list) {
	    System.out.println(company.toString());
	}
    }

}
