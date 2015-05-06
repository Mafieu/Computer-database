package com.excilys.malbert.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.malbert.controller.dto.ComputerDTO;
import com.excilys.malbert.exceptions.DAOException;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.util.Utils;

public final class MapperComputer implements RowMapper<Computer> {
    public MapperComputer() {
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

    @Override
    public Computer mapRow(ResultSet set, int row) {
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
}
