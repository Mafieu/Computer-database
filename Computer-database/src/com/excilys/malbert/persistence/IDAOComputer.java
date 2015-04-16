package com.excilys.malbert.persistence;

import java.util.List;

import com.excilys.malbert.persistence.model.Computer;

public interface IDAOComputer {
    public List<Computer> getAll();

    public List<Computer> getSome(int limit, int offset);

    public Computer getComputer(long id);

    public int getNumberComputer();

    public void create(Computer computer);

    public void delete(Computer computer);

    /**
     * @param computer
     *            Computer with values changed (must keep the same id)
     */
    public void update(Computer computer);
}
