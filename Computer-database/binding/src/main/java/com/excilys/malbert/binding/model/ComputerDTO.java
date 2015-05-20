package com.excilys.malbert.binding.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.excilys.malbert.binding.validator.Date;

public class ComputerDTO extends EntityDTO {

	@NotNull
	@Size(min = 1, max = 255)
	private String name;
	@Date
	private String introduced;
	@Date
	private String discontinued;
	@Min(0)
	private long companyId;
	@Size(min = 1, max = 255)
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
		this.name = name.trim();
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
