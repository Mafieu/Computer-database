package com.excilys.malbert.presentation.dto;

public class CompanyDTO {

    private long id;
    private String name;

    public CompanyDTO() {
	id = 0;
	name = null;
    }

    public CompanyDTO(long id, String name) {
	this.id = id;
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
