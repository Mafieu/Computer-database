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
    private long id_company = -1;

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
    public Computer(long id, String name, long id_company) {
	this.id = id;
	this.name = name;
	this.id_company = id_company;
	this.introduced = LocalDateTime.now();
    }

    /**
     * @param id
     * @param name
     * @param introduced
     * @param id_company
     */
    public Computer(long id, String name, LocalDateTime introduced,
	    long id_company) {
	this(id, name, id_company);
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
	    LocalDateTime discontinued, long id_company) {
	this(id, name, introduced, id_company);
	this.discontinued = discontinued;
    }

    /**
     * Basic constructor without id
     * 
     * @param name
     * @param id_company
     */
    public Computer(String name, long id_company) {
	this.name = name;
	this.id_company = id_company;
    }

    /**
     * @param name
     * @param introduced
     * @param id_company
     */
    public Computer(String name, LocalDateTime introduced, long id_company) {
	this(name, id_company);
	this.introduced = introduced;
    }

    /**
     * @param name
     * @param introduced
     * @param discontinued
     * @param id_company
     */
    public Computer(String name, LocalDateTime introduced,
	    LocalDateTime discontinued, long id_company) {
	this(name, introduced, id_company);
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
    public long getIdCompany() {
	return id_company;
    }

    /**
     * @param company
     */
    public void setIdCompany(Company company) {
	this.id_company = company.getId();
    }

    @Override
    public String toString() {
	return new StringBuilder().append("Id : ").append(this.id)
		.append("\tName : ").append(this.name)
		.append("\tIntroduced : ").append(this.introduced)
		.append("\tDiscontinued : ").append(this.discontinued)
		.append("\tOwner : ").append(id_company).toString();
    }
}
