package com.excilys.malbert.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.malbert.exceptions.DAOException;
import com.excilys.malbert.model.Company;
import com.excilys.malbert.model.Computer;
import com.excilys.malbert.model.ComputerDTO;
import com.excilys.malbert.util.Utils;
import com.excilys.malbert.validator.Date.Pattern;

public final class ComputerMapper implements RowMapper<Computer> {
	public ComputerMapper() {
	}

	public static Computer computerdtoToComputer(ComputerDTO computerDTO,
			Pattern pattern) {
		if (computerDTO == null) {
			return null;
		} else {
			return new Computer(computerDTO.getId(), computerDTO.getName(),
					Utils.stringToLocaldatetime(computerDTO.getIntroduced(),
							pattern), Utils.stringToLocaldatetime(
							computerDTO.getDiscontinued(), pattern),
					new Company(computerDTO.getCompanyId(), computerDTO
							.getCompanyName()));
		}
	}

	public static ComputerDTO computerToComputerdto(Computer computer,
			Pattern pattern) {
		if (computer == null) {
			return null;
		} else {
			if (computer.getCompany() == null) {
				return new ComputerDTO(computer.getId(), computer.getName(),
						Utils.localdatetimeToString(computer.getIntroduced(),
								pattern), Utils.localdatetimeToString(
								computer.getDiscontinued(), pattern), 0, null);
			} else {
				return new ComputerDTO(computer.getId(), computer.getName(),
						Utils.localdatetimeToString(computer.getIntroduced(),
								pattern), Utils.localdatetimeToString(
								computer.getDiscontinued(), pattern), computer
								.getCompany().getId(), computer.getCompany()
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
