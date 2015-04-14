package com.excilys.malbert.persistence.updated;

import java.util.List;

import com.excilys.malbert.persistence.model.Computer;

public interface IDAOComputer {
    public List<Computer> getAll();

    public Computer getComputer(long id);

    public void create(Computer computer);

    public void delete(Computer computer);

    public void update(Computer oldComputer, Computer newComputer);
}
