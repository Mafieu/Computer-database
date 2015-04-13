package com.excilys.malbert.client.CLI;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.service.Service;

public class Cli {

    private static Service service;
    private static Scanner console;

    private static void printMenu() {
	System.out.println("1. List of computers");
	System.out.println("2. List of companies");
	System.out.println("3. Show details of a computer");
	System.out.println("4. Create a computer");
	System.out.println("5. Update a computer");
	System.out.println("6. Delete a computer");
	System.out.println("7. Quit program");
    }

    private static Computer createComputer() {
	List<Company> companies = service.getAllCompanies();
	String name;
	Timestamp introduced, discontinued;
	GregorianCalendar gc;
	long id;
	System.out.println("Name of the computer :");
	console.nextLine(); // Just for debug, otherwise name is ""
	name = console.nextLine();
	System.out.println("Date of introduction (YYYY-MM-DD):");
	try {
	    String[] introduction = console.nextLine().split("-");
	    gc = new GregorianCalendar(Integer.parseInt(introduction[0]),
		    Integer.parseInt(introduction[1]),
		    Integer.parseInt(introduction[2]));
	    introduced = new Timestamp(gc.getTimeInMillis());
	} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
	    introduced = null;
	}
	System.out.println("Date of discontinuation (YYYY-MM-DD):");
	try {
	    String[] discontinuation = console.nextLine().split("-");
	    gc = new GregorianCalendar(Integer.parseInt(discontinuation[0]),
		    Integer.parseInt(discontinuation[1]),
		    Integer.parseInt(discontinuation[2]));
	    discontinued = new Timestamp(gc.getTimeInMillis());
	} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
	    discontinued = null;
	}
	System.out.println("Id of the manufacturer :");
	id = console.nextLong();
	try {
	    companies.get((int) id - 1);
	} catch (IndexOutOfBoundsException e) {
	    System.err.println("Wrong company id");
	}
	return new Computer(-1, name, introduced, discontinued, id);
    }

    private static void menu(int choice) {
	List<Company> listCompanies;
	List<Computer> listComputers;
	Computer pc;
	long id;

	switch (choice) {
	case 1:
	    try {
		listComputers = service.getAllComputers();
		for (Computer computer : listComputers) {
		    System.out.println(computer.toString());
		}
	    } catch (SQLException e) {
		System.err.println("Failed to get list of computers");
	    }
	    break;
	case 2:
	    listCompanies = service.getAllCompanies();
	    for (Company company : listCompanies) {
		System.out.println(company.toString());
	    }
	    break;
	case 3:
	    try {
		System.out.println("Id of the computer :");
		id = console.nextLong();
		System.out.println(service.getComputer(id).toString());
	    } catch (SQLException e) {
		System.err.println("Failed to get the computer");
	    }
	    break;
	case 4:
	    try {
		service.createComputer(createComputer());
	    } catch (SQLException e) {
		System.err.println("Failed to create the computer");
	    }
	    break;
	case 5:
	    System.out.println("Id of the computer to update :");
	    id = console.nextLong();
	    try {
		pc = service.getComputer(id);
		try {
		    service.updateComputer(pc, createComputer());
		} catch (SQLException e) {
		    System.err.println("Failed to create the computer");
		}
	    } catch (SQLException e) {
		System.err.println("Failed to get the computer");
	    }
	    break;
	case 6:
	    System.out.println("Id of the computer :");
	    id = console.nextLong();
	    try {
		pc = service.getComputer(id);
		try {
		    service.deleteComputer(pc);
		} catch (SQLException e) {
		    System.err.println("Failed to delete the computer");
		}
	    } catch (SQLException e) {
		System.err.println("Failed to get the computer");
	    }
	    break;
	case 7:
	    System.out.println("Good bye");
	    break;
	default:
	    System.err.println("Please enter a number between 1 and 7");
	    break;
	}
    }

    public static void main(String[] args) {
	service = new Service();
	console = new Scanner(System.in);
	int entry;

	do {
	    printMenu();
	    if (console.hasNextInt()) {
		entry = console.nextInt();
		menu(entry);
	    } else {
		console.next();
		entry = 0;
		System.err.println("Please enter a number between 1 and 7");
	    }
	} while (entry != 7);
    }
}
