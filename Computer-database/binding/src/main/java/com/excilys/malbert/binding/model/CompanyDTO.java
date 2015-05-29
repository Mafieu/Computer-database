package com.excilys.malbert.binding.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * POJO of Company
 * 
 * @author excilys
 * @see Company
 */
public class CompanyDTO {

	@Min(0)
	protected Long id;
	@NotNull
	@Size(min = 1)
	private String name;

	public CompanyDTO() {
		this(null, null);
	}

	public CompanyDTO(Long id, String name) {
		this.id = id;
		this.name = name;
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
}
