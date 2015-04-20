package com.excilys.malbert.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.malbert.persistence.DAOException;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.presentation.dto.CompanyDTO;
import com.excilys.malbert.presentation.dto.ComputerDTO;

public abstract class Mapper {
    public static Computer resultsetToComputer(ResultSet set) {
	try {
	    Company company;
	    if (set.getLong(5) == 0) {
		company = null;
	    } else {
		company = new Company(set.getLong(5), set.getString(7));
	    }
	    return new Computer(set.getLong(1), set.getString(2),
		    Utils.timestampToLocaldatetime(set.getTimestamp(3)),
		    Utils.timestampToLocaldatetime(set.getTimestamp(4)),
		    company);
	} catch (SQLException e) {
	    throw new DAOException("Couldn't parse bdd->computer");
	}
    }

    public static Company resultsetToCompany(ResultSet set) {
	try {
	    Company company;
	    System.out.println(set.getRow());
	    if (set.getRow() == 0) {
		company = null;
	    } else {
		company = new Company(set.getLong(1), set.getString(2));
	    }
	    return company;
	} catch (SQLException e) {
	    throw new DAOException("Couldn't parse bdd->company");
	}
    }

    public static Computer computerdtoToComputer(ComputerDTO computerDTO) {
	if (computerDTO == null) {
	    return null;
	} else {
	    return new Computer(computerDTO.getId(), computerDTO.getName(),
		    Utils.stringToLocaldatetime(computerDTO.getIntroduced()),
		    Utils.stringToLocaldatetime(computerDTO.getDiscontinued()),
		    new Company(computerDTO.getCompanyId(),
			    computerDTO.getCompanyName()));
	}
    }

    public static ComputerDTO computerToComputerdto(Computer computer) {
	if (computer == null) {
	    return null;
	} else {
	    if (computer.getCompany() == null) {
		return new ComputerDTO(
			computer.getId(),
			computer.getName(),
			Utils.localdatetimeToString(computer.getIntroduced()),
			Utils.localdatetimeToString(computer.getDiscontinued()),
			0, null);
	    } else {
		return new ComputerDTO(
			computer.getId(),
			computer.getName(),
			Utils.localdatetimeToString(computer.getIntroduced()),
			Utils.localdatetimeToString(computer.getDiscontinued()),
			computer.getCompany().getId(), computer.getCompany()
				.getName());
	    }
	}
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
}
