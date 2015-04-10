package com.excilys.malbert.persistence.model;

public class Company extends Entity {
    private String name = "";

    public Company() {
    }

    public Company(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
}
