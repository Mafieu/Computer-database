package com.excilys.malbert.persistence;

import java.util.List;

import com.excilys.malbert.persistence.model.Entity;

public interface ICrud {
    public List<Entity> getAll();

    public void create();

    public void delete();

    public void update();
}
