package com.excilys.malbert.binding.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CompanyDTO extends EntityDTO {

	@NotNull
	@Size(min = 1)
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
		this.name = name.trim();
	}

	public long getId() {
		return id;
	}
}
