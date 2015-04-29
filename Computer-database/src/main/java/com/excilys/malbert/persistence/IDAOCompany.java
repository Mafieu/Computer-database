package com.excilys.malbert.persistence;

import java.util.List;

import com.excilys.malbert.persistence.model.Company;

public interface IDAOCompany extends IDAOCrud<Company> {

    public List<Company> getAll();
}
