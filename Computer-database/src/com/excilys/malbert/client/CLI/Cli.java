package com.excilys.malbert.client.CLI;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.service.Service;

/**
 * Command Line Interface class for the client
 * 
 * @author excilys
 */
public class Cli {

    private static Service service;
    private static Scanner scanner;

    /**
     * Prints the menu
     */
    private static void printMenu() {
	System.out.println("1. List of computers");
	System.out.println("2. List of companies");
	System.out.println("3. Show details of a computer");
	System.out.println("4. Create a computer");
	System.out.println("5. Update a computer");
	System.out.println("6. Delete a computer");
	System.out.println("7. Quit program");
    }

    /**
     * Change the format's date (YYYY-MM-DD) to a Timestamp
     * 
     * @return The date in a Timestamp object
     */
    private static Timestamp getDate() {
	GregorianCalendar gc;
	try {
	    String[] introduction = scanner.nextLine().split("-");
	    gc = new GregorianCalendar(Integer.parseInt(introduction[0]),
		    Integer.parseInt(introduction[1]) - 1,
		    Integer.parseInt(introduction[2]));
	    return new Timestamp(gc.getTimeInMillis());
	} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
	    return null;
	}
    }

    /**
     * @return A computer
     */
    private static Computer createComputer() {
	String name;
	Timestamp introduced, discontinued;
	long id = -1;
	System.out.println("Name of the computer :");
	scanner.nextLine(); // Just for debug, otherwise name is ""
	name = scanner.nextLine();
	System.out.println("Date of introduction (YYYY-MM-DD):");
	introduced = getDate();
	System.out.println("Date of discontinuation (YYYY-MM-DD):");
	discontinued = getDate();
	System.out.println("Id of the manufacturer :");
	if (scanner.hasNextLong()) {
	    id = scanner.nextLong();
	} else {
	    scanner.next();
	}
	return new Computer(name, introduced, discontinued, id);
    }

    /**
     * Menu of the CLI
     * 
     * @param choice
     *            Menu choice
     */
    private static void menu(int choice) {
	List<Company> listCompanies;
	List<Computer> listComputers;
	Computer pc;
	long id = -1;

	switch (choice) {
	case 1:
	    try {
		listComputers = service.getAllComputers();
		Paginator<Computer> paginatorPc = new Paginator<Computer>(
			listComputers);
		paginatorPc.show();
	    } catch (SQLException e) {
		System.err.println("Failed to get list of computers");
	    }
	    break;
	case 2:
	    try {
		listCompanies = service.getAllCompanies();
		Paginator<Company> paginatorComp = new Paginator<Company>(
			listCompanies);
		paginatorComp.show();
	    } catch (SQLException e1) {
		System.err.println("Failed to get list of companies");
	    }
	    break;
	case 3:
	    try {
		System.out.println("Id of the computer :");
		id = scanner.nextLong();
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
	    if (scanner.hasNextLong()) {
		id = scanner.nextLong();
	    } else {
		scanner.next();
	    }
	    try {
		pc = service.getComputer(id);
		try {
		    service.updateComputer(pc, createComputer());
		} catch (SQLException e) {
		    System.err.println("Failed to update the computer");
		}
	    } catch (SQLException e) {
		System.err.println("Failed to get the computer");
	    }
	    break;
	case 6:
	    System.out.println("Id of the computer :");
	    id = scanner.nextLong();
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

    /**
     * @param args
     *            Unused
     */
    public static void main(String[] args) {
	service = new Service();
	scanner = new Scanner(System.in);
	int entry;

	do {
	    printMenu();
	    if (scanner.hasNextInt()) {
		entry = scanner.nextInt();
		menu(entry);
	    } else {
		scanner.next();
		entry = 0;
		System.err.println("Please enter a number between 1 and 7");
	    }
	} while (entry != 7);
    }
}
