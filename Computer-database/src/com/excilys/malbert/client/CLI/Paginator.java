package com.excilys.malbert.client.CLI;

import java.util.List;
import java.util.Scanner;

public class Paginator<T> {

    private List<T> entities;
    private static final int NB_ENTITY_PER_PAGE = 10;
    private int page;
    private static Scanner scanner;

    public Paginator(List<T> list) {
	this.entities = list;
	scanner = new Scanner(System.in);
	page = 0;
    }

    public void show() {
	String choice;
	do {
	    for (int i = 0; i < NB_ENTITY_PER_PAGE
		    && i + page * NB_ENTITY_PER_PAGE < entities.size(); ++i) {
		System.out.println(entities.get(i + page * NB_ENTITY_PER_PAGE)
			.toString());
	    }
	    System.out.println("previous p | next n | quit q");
	    choice = scanner.nextLine();
	    switch (choice) {
	    case "n":
		nextPage();
		break;
	    case "p":
		previousPage();
		break;
	    case "q":
		break;
	    default:
		System.err
			.println("Use n for next, p for previous or q for quit");
		break;
	    }
	} while (!choice.equals("q"));
    }

    private void nextPage() {
	if ((page + 1) * NB_ENTITY_PER_PAGE < entities.size()) {
	    ++page;
	}
    }

    private void previousPage() {
	if (page > 0) {
	    --page;
	}
    }
}
