package com.excilys.malbert.persistence;

import java.util.List;

import com.excilys.malbert.core.model.Company;

public interface ICompanyDAO extends ICrudDAO<Company> {

    public List<Company> getAll();
}
