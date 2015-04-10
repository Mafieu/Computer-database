package com.excilys.malbert.persistence.model;

public abstract class Entity {
    protected long id = -1;

    public long getId() {
	return id;
    }
}
