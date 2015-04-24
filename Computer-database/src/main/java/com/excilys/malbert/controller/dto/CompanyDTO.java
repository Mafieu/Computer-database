package com.excilys.malbert.controller.dto;

public class CompanyDTO extends EntityDTO {

    private String name;

    public CompanyDTO() {
	this(0, null);
    }

    public CompanyDTO(long id, String name) {
	super(id);
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public long getId() {
	return id;
    }
}
