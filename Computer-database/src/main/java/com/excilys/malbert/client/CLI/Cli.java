package com.excilys.malbert.client.CLI;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.malbert.model.Company;
import com.excilys.malbert.model.Computer;
import com.excilys.malbert.service.ICompanyService;
import com.excilys.malbert.service.IComputerService;
import com.excilys.malbert.util.Utils;
import com.excilys.malbert.validator.Date.Pattern;

/**
 * Command Line Interface class for the client
 * 
 * @author excilys
 */
public class Cli {

	private static Scanner scanner;
	private static ICompanyService serviceCompany;
	private static IComputerService serviceComputer;

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
		System.out.println("7. Delete a company");
		System.out.println("8. Quit program");
	}

	/**
	 * @return A computer
	 */
	private static Computer createComputer(long id) {
		String name;
		LocalDateTime introduced, discontinued;
		long idCompany = -1;
		System.out.println("Name of the computer :");
		scanner.nextLine(); // Just for debug, otherwise name is ""
		name = scanner.nextLine();
		System.out.println("Date of introduction (DD-MM-YYYY):");
		introduced = Utils
				.stringToLocaldatetime(scanner.nextLine(), Pattern.FR);
		System.out.println("Date of discontinuation (DD-MM-YYYY):");
		discontinued = Utils.stringToLocaldatetime(scanner.nextLine(),
				Pattern.FR);
		System.out
				.println("Id of the manufacturer (for no manufacturer, enter a 0 or minus id):");
		if (scanner.hasNextLong()) {
			idCompany = scanner.nextLong();
		} else {
			scanner.next();
		}
		return new Computer(id, name, introduced, discontinued, new Company(
				idCompany, null));
	}

	private static Computer createComputer() {
		return createComputer(-1);
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
		long id = -1;

		switch (choice) {
		case 1:
			listComputers = serviceComputer.getAllComputers();
			Paginator<Computer> paginatorPc = new Paginator<Computer>(
					listComputers);
			paginatorPc.show();
			break;
		case 2:
			listCompanies = serviceCompany.getAllCompanies();
			Paginator<Company> paginatorComp = new Paginator<Company>(
					listCompanies);
			paginatorComp.show();
			break;
		case 3:
			System.out.println("Id of the computer :");
			if (scanner.hasNextLong()) {
				id = scanner.nextLong();
			} else {
				scanner.next();
			}
			System.out.println(serviceComputer.getComputer(id).toString());
			break;
		case 4:
			serviceComputer.createComputer(createComputer());
			break;
		case 5:
			System.out.println("Id of the computer to update :");
			if (scanner.hasNextLong()) {
				id = scanner.nextLong();
			} else {
				scanner.next();
			}
			serviceComputer.updateComputer(createComputer(id));
			break;
		case 6:
			System.out.println("Id of the computer :");
			if (scanner.hasNextLong()) {
				id = scanner.nextLong();
			} else {
				scanner.next();
			}
			serviceComputer.deleteComputer(id);
			break;
		case 7:
			System.out.println("Id of the company :");
			if (scanner.hasNextLong()) {
				id = scanner.nextLong();
			} else {
				scanner.next();
			}
			serviceCompany.deleteCompany(id);
			break;
		case 8:
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
		scanner = new Scanner(System.in);
		int entry;

		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		serviceComputer = appContext.getBean(IComputerService.class);
		serviceCompany = appContext.getBean(ICompanyService.class);

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
		} while (entry != 8);
		appContext.close();
	}
}
