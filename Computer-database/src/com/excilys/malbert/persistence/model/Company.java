package com.excilys.malbert.persistence.model;

public class Company extends Entity {
    private String name = "";

    public Company() {
    }

    public Company(long id, String name) {
	this.id = id;
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return new StringBuilder("Id : ").append(id).append("\tName : ")
		.append(name).toString();
    }
}
