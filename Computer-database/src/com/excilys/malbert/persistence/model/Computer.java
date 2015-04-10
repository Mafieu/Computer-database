package com.excilys.malbert.persistence.model;

import java.sql.Timestamp;
import java.util.Date;

public class Computer extends Entity {
    private String name = "";
    private Timestamp introduced = null;
    private Timestamp discontinued = null;
    private Company company = null;

    public Computer() {
    }

    public Computer(long id, String name, Company company) {
	this.id = id;
	this.name = name;
	this.company = company;
	this.introduced = new Timestamp(new Date().getTime());
    }

    public Computer(long id, String name, Timestamp introduced, Company company) {
	this(id, name, company);
	this.introduced = introduced;
    }

    public Computer(long id, String name, Timestamp introduced,
	    Timestamp discontinued, Company company) {
	this(id, name, introduced, company);
	this.discontinued = discontinued;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Timestamp getIntroduced() {
	return introduced;
    }

    public void setIntroduced(Timestamp introduced) {
	this.introduced = introduced;
    }

    public Timestamp getDiscontinued() {
	return discontinued;
    }

    public void setDiscontinued(Timestamp discontinued) {
	this.discontinued = discontinued;
    }

    public Company getCompany() {
	return company;
    }

    public void setCompany(Company company) {
	this.company = company;
    }

    @Override
    public String toString() {
	return new StringBuilder().append("Id : ").append(this.id)
		.append("\tName : ").append(this.name)
		.append("\tIntroduced : ").append(this.introduced)
		.append("\tDescontinued : ").append(this.discontinued)
		.toString();
    }
}
