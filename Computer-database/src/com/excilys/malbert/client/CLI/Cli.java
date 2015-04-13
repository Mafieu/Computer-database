package com.excilys.malbert.client.CLI;

import java.util.List;

import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.service.Service;

public class Cli {

    public static void main(String[] args) {
	Service service = new Service();
	List<Company> listCompanies;
	List<Computer> listComputers;

	listCompanies = service.getAllCompanies();
	for (Company company : listCompanies) {
	    System.out.println(company.toString());
	}
	listComputers = service.getAllComputers();
	for (Computer computer : listComputers) {
	    System.out.println(computer.toString());
	}
	System.out
		.println("******************************************************");
	Computer pc = service.getComputer(listComputers.get(
		listComputers.size() - 1).getId());
	System.out.println(pc.toString());
	pc = service.getComputer(listComputers.get(listComputers.size() - 2)
		.getId());
	System.out.println(pc.toString());
	service.updateComputer(listComputers.get(listComputers.size() - 1),
		listComputers.get(listComputers.size() - 2));
	pc = service.getComputer(listComputers.get(listComputers.size() - 1)
		.getId());
	System.out.println(pc.toString());
    }
}
