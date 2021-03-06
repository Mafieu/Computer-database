package com.excilys.malbert.client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.malbert.binding.util.Utils;
import com.excilys.malbert.binding.validator.Date.Pattern;
import com.excilys.malbert.client.service.IClientService;
import com.excilys.malbert.core.model.Company;
import com.excilys.malbert.core.model.Computer;

/**
 * Command Line Interface class for the client
 * 
 * @author excilys
 */
@Component
public class Cli {

	@Autowired
	public IClientService service;

	private Scanner scanner;

	/**
	 * Prints the menu
	 */
	private void printMenu() {
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
	private Computer createComputer(long id) {
		String name;
		LocalDateTime introduced, discontinued;
		long idCompany = 0;
		System.out.println("Name of the computer :");
		scanner.nextLine(); // Just for debug, otherwise name is ""
		name = scanner.nextLine();
		System.out.println("Date of introduction (DD-MM-YYYY):");
		introduced = Utils
				.stringToLocaldatetime(scanner.nextLine(), Pattern.FR);
		System.out.println("Date of discontinuation (DD-MM-YYYY):");
		discontinued = Utils.stringToLocaldatetime(scanner.nextLine(),
				Pattern.FR);
		do {
			System.out
					.println("Id of the manufacturer (for no manufacturer, enter a 0):");
			if (scanner.hasNextLong()) {
				idCompany = scanner.nextLong();
			} else {
				scanner.next();
			}
		} while (idCompany < 0);
		return new Computer(id, name, introduced, discontinued,
				(idCompany == 0 ? null : service.getCompany(idCompany)));
	}

	private Computer createComputer() {
		return createComputer(-1);
	}

	/**
	 * Menu of the CLI
	 * 
	 * @param choice
	 *            Menu choice
	 */
	private void menu(int choice) {
		List<Company> listCompanies;
		List<Computer> listComputers;
		long id = -1;

		switch (choice) {
		case 1:
			listComputers = service.getAllComputer();
			Paginator<Computer> paginatorPc = new Paginator<Computer>(
					listComputers);
			paginatorPc.show();
			break;
		case 2:
			listCompanies = service.getAllCompany();
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
			System.out.println(service.findComputer(id).toString());
			break;
		case 4:
			service.createComputer(createComputer());
			break;
		case 5:
			System.out.println("Id of the computer to update :");
			if (scanner.hasNextLong()) {
				id = scanner.nextLong();
			} else {
				scanner.next();
			}
			service.updateComputer(createComputer(id));
			break;
		case 6:
			System.out.println("Id of the computer :");
			if (scanner.hasNextLong()) {
				id = scanner.nextLong();
			} else {
				scanner.next();
			}
			service.deleteComputer(id);
			break;
		case 7:
			System.out.println("Id of the company :");
			if (scanner.hasNextLong()) {
				id = scanner.nextLong();
			} else {
				scanner.next();
			}
			service.deleteCompany(id);
			break;
		case 8:
			System.out.println("Good bye");
			break;
		default:
			System.err.println("Please enter a number between 1 and 7");
			break;
		}
	}

	private void displayMenu() {
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
		} while (entry != 8);
	}

	/**
	 * Gets the Bean of this class in the application context and start the menu
	 * from it. This permits to autowire the service
	 * 
	 * @param args
	 *            Unused
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		Cli cli = appContext.getBean(Cli.class);
		cli.displayMenu();
		appContext.close();
	}
}
