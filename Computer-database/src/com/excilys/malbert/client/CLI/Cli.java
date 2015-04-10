package com.excilys.malbert.client.CLI;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.excilys.malbert.persistence.ControllerCompanies;
import com.excilys.malbert.persistence.ControllerComputer;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;

public class Cli {

    public static void main(String[] args) {
	ControllerComputer computers = new ControllerComputer();
	ControllerCompanies companies = new ControllerCompanies();
	List<Company> listCompanies;
	List<Computer> listCompunters;
	listCompanies = companies.getAll();
	for (Company company : listCompanies) {
	    System.out.println(company.toString());
	}
	listCompunters = computers.getAll();
	for (Computer computer : listCompunters) {
	    System.out.println(computer.toString());
	}
	computers.create("titi", new Timestamp(new Date().getTime()), null,
		listCompanies.get(5));
	listCompunters = computers.getAll();
	for (Computer computer : listCompunters) {
	    System.out.println(computer.toString());
	}

	Computer computer = computers.getComputer(500);
	System.out.println(computer.toString());
    }
}
