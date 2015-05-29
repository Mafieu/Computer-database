package com.excilys.malbert.binding.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.excilys.malbert.binding.validator.Date;

/**
 * POJO of Computer
 * 
 * @author excilys
 * @see Computer
 */
public class ComputerDTO {

	@Min(0)
	protected Long id;
	@NotNull
	@Size(min = 1, max = 255)
	private String name;
	@Date
	private String introduced;
	@Date
	private String discontinued;
	@Min(0)
	private Long companyId;
	@Size(min = 1, max = 255)
	private String companyName;

	public ComputerDTO() {
		this(null, null, null, null, null, null);
	}

	public ComputerDTO(Long id, String name, String introduced,
			String discontinued, Long companyId, String companyName) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
		this.companyName = companyName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", discontinued=" + discontinued
				+ ", companyId=" + companyId + ", companyName=" + companyName
				+ "]";
	}
}
