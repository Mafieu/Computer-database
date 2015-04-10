package com.excilys.malbert.persistence.model;

import java.sql.Timestamp;
import java.util.Date;

public class Computer extends Entity {
    private String name = "";
    /* Change to localeDateTime (Java8 powa) */
    private Timestamp introduced = null;
    private Timestamp discontinued = null;
    private long id_company = -1;

    public Computer() {
    }

    public Computer(long id, String name, long company) {
	this.id = id;
	this.name = name;
	this.id_company = company;
	this.introduced = new Timestamp(new Date().getTime());
    }

    public Computer(long id, String name, Timestamp introduced, long company) {
	this(id, name, company);
	this.introduced = introduced;
    }

    public Computer(long id, String name, Timestamp introduced,
	    Timestamp discontinued, long company) {
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

    public long getIdCompany() {
	return id_company;
    }

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
