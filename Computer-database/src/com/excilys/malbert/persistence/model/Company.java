package com.excilys.malbert.persistence.model;

/**
 * Model object to represent the rows of company's table
 * 
 * @author excilys
 *
 */
public class Company extends Entity {
    private String name = "";

    /**
     * Default constructor
     */
    public Company() {
    }

    /**
     * Constructor with id
     * 
     * @param id
     * @param name
     */
    public Company(long id, String name) {
	this.id = id;
	this.name = name;
    }

    /**
     * Constructor without id
     * 
     * @param name
     */
    public Company(String name) {
	this.name = name;
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

    @Override
    public String toString() {
	return new StringBuilder("Id : ").append(id).append("\tName : ")
		.append(name).toString();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj instanceof Company) {
	    Company company = ((Company) obj);
	    return this.id == company.id
		    && (name == null ? company.name == null : name
			    .equals(company.name));
	} else {
	    return false;
	}
    }
}
