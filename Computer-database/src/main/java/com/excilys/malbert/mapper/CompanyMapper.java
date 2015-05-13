package com.excilys.malbert.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.malbert.controller.dto.CompanyDTO;
import com.excilys.malbert.exceptions.DAOException;
import com.excilys.malbert.persistence.model.Company;

public final class CompanyMapper implements RowMapper<Company> {
    public CompanyMapper() {
    }

    public static Company companydtoToCompany(CompanyDTO companyDTO) {
	if (companyDTO == null) {
	    return null;
	} else {
	    return new Company(companyDTO.getId(), companyDTO.getName());
	}
    }

    public static CompanyDTO companyToCompanydto(Company company) {
	if (company == null) {
	    return null;
	} else {
	    return new CompanyDTO(company.getId(), company.getName());
	}
    }

    @Override
    public Company mapRow(ResultSet set, int row) {
	try {
	    Company company;
	    if (set.getRow() == 0) {
		company = null;
	    } else {
		company = new Company(set.getLong("id"), set.getString("name"));
	    }
	    return company;
	} catch (SQLException e) {
	    throw new DAOException("Couldn't parse bdd->company");
	}
    }
}
