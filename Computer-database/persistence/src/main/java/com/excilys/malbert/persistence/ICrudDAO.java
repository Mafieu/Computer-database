package com.excilys.malbert.persistence;

import com.excilys.malbert.core.model.Entity;

public interface ICrudDAO<T extends Entity> {

	default public void create(T object) {
		throw new IllegalAccessError("Create was not implemented");
	}

	default public T getOne(Long id) {
		throw new IllegalAccessError("Get one was not implemented");
	}

	default public void update(T object) {
		throw new IllegalAccessError("Update was not implemented");
	}

	default public void delete(Long id) {
		throw new IllegalAccessError("Delete was not implemented");
	}
}
