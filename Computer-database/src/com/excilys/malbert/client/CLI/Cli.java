package com.excilys.malbert.client.CLI;

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
	List<Computer> listComputers;
	listCompanies = companies.getAll();
	for (Company company : listCompanies) {
	    System.out.println(company.toString());
	}
	listComputers = computers.getAll();
	for (Computer computer : listComputers) {
	    System.out.println(computer.toString());
	}
	System.out
		.println("******************************************************");
	Computer pc = computers.getComputer(listComputers.get(
		listComputers.size() - 1).getId());
	System.out.println(pc.toString());
	pc = computers.getComputer(listComputers
		.get(listComputers.size() - 2).getId());
	System.out.println(pc.toString());
	computers.update(listComputers.get(listComputers.size() - 1),
		listComputers.get(listComputers.size() - 2));
	pc = computers.getComputer(listComputers
		.get(listComputers.size() - 1).getId());
	System.out.println(pc.toString());
    }
}
