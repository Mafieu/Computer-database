package com.excilys.malbert.controller.dto;

public class ComputerDTO extends EntityDTO {

    private String name;
    private String introduced;
    private String discontinued;
    private long companyId;
    private String companyName;

    public ComputerDTO() {
	this(0, null, null, null, 0, null);
    }

    public ComputerDTO(long id) {
	this(id, null, null, null, 0, null);
    }

    public ComputerDTO(long id, String name, String introduced,
	    String discontinued, long companyId, String companyName) {
	super(id);
	this.name = name;
	this.introduced = introduced;
	this.discontinued = discontinued;
	this.companyId = companyId;
	this.companyName = companyName;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getIntroduced() {
	return introduced;
    }

    public void setIntroduced(String introduced) {
	this.introduced = introduced;
    }

    public String getDiscontinued() {
	return discontinued;
    }

    public void setDiscontinued(String discontinued) {
	this.discontinued = discontinued;
    }

    public long getCompanyId() {
	return companyId;
    }

    public void setCompanyId(long companyId) {
	this.companyId = companyId;
    }

    public String getCompanyName() {
	return companyName;
    }

    public void setCompanyName(String companyName) {
	this.companyName = companyName;
    }

    public long getId() {
	return id;
    }

    @Override
    public String toString() {
	return "ComputerDTO [name=" + name + ", introduced=" + introduced
		+ ", discontinued=" + discontinued + ", companyId=" + companyId
		+ ", companyName=" + companyName + "]";
    }
}
