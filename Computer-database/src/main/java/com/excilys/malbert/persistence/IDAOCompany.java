package com.excilys.malbert.persistence;

import java.sql.Connection;
import java.util.List;

import com.excilys.malbert.persistence.model.Company;

public interface IDAOCompany {

    public List<Company> getAll();

    public Company getCompany(long id);

    public void delete(long id, Connection connection);
}
