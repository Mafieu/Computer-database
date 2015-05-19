package com.excilys.malbert.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public abstract class EntityDTO {

	@NotNull
	@Min(0)
	protected long id;

	public EntityDTO() {
		this(0);
	}

	public EntityDTO(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
