package com.excilys.malbert.controller.dto;

public abstract class EntityDTO {

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
}
