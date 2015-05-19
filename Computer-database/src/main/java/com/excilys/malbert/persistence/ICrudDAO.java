package com.excilys.malbert.persistence;

import com.excilys.malbert.model.Entity;

public interface ICrudDAO<T extends Entity> {

	default public void create(T object) {
		throw new IllegalAccessError("Create was not implemented");
	}

	default public T getOne(long id) {
		throw new IllegalAccessError("Get one was not implemented");
	}

	default public void update(T object) {
		throw new IllegalAccessError("Update was not implemented");
	}

	default public void delete(long id) {
		throw new IllegalAccessError("Delete was not implemented");
	}
}
