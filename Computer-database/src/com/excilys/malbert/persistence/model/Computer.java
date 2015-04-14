package com.excilys.malbert.persistence.model;

import java.time.LocalDateTime;

/**
 * Model object to represent the rows of computer's table
 * 
 * @author excilys
 *
 */
public class Computer extends Entity {
    private String name = "";
    /* Change to localeDateTime (Java8 powa) */
    private LocalDateTime introduced = null;
    private LocalDateTime discontinued = null;
    private Company company = null;

    /**
     * Default constructor
     */
    public Computer() {
    }

    /**
     * Basic constructor with id
     * 
     * @param id
     * @param name
     * @param id_company
     */
    public Computer(long id, String name, Company company) {
	this.id = id;
	this.name = name;
	this.company = company;
	this.introduced = LocalDateTime.now();
    }

    /**
     * @param id
     * @param name
     * @param introduced
     * @param id_company
     */
    public Computer(long id, String name, LocalDateTime introduced,
	    Company company) {
	this(id, name, company);
	this.introduced = introduced;
    }

    /**
     * @param id
     * @param name
     * @param introduced
     * @param discontinued
     * @param id_company
     */
    public Computer(long id, String name, LocalDateTime introduced,
	    LocalDateTime discontinued, Company company) {
	this(id, name, introduced, company);
	this.discontinued = discontinued;
    }

    /**
     * Basic constructor without id
     * 
     * @param name
     * @param id_company
     */
    public Computer(String name, Company company) {
	this.name = name;
	this.company = company;
    }

    /**
     * @param name
     * @param introduced
     * @param id_company
     */
    public Computer(String name, LocalDateTime introduced, Company company) {
	this(name, company);
	this.introduced = introduced;
    }

    /**
     * @param name
     * @param introduced
     * @param discontinued
     * @param id_company
     */
    public Computer(String name, LocalDateTime introduced,
	    LocalDateTime discontinued, Company company) {
	this(name, introduced, company);
	this.discontinued = discontinued;
    }

    /**
     * @return
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return
     */
    public LocalDateTime getIntroduced() {
	return introduced;
    }

    public void setIntroduced(LocalDateTime introduced) {
	this.introduced = introduced;
    }

    /**
     * @return
     */
    public LocalDateTime getDiscontinued() {
	return discontinued;
    }

    /**
     * @param discontinued
     */
    public void setDiscontinued(LocalDateTime discontinued) {
	this.discontinued = discontinued;
    }

    /**
     * @return
     */
    public Company getCompany() {
	return company;
    }

    /**
     * @param company
     */
    public void setCompany(Company company) {
	this.company = company;
    }

    @Override
    public String toString() {
	return new StringBuilder().append("Id : ").append(this.id)
		.append("\tName : ").append(this.name)
		.append("\tIntroduced : ").append(this.introduced)
		.append("\tDiscontinued : ").append(this.discontinued)
		.append("\tOwner : ").append(company).toString();
    }
}
