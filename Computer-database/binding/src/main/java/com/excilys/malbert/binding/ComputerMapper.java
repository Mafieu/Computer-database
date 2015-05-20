package com.excilys.malbert.binding;

import com.excilys.malbert.binding.model.ComputerDTO;
import com.excilys.malbert.binding.util.Utils;
import com.excilys.malbert.binding.validator.Date.Pattern;
import com.excilys.malbert.core.model.Company;
import com.excilys.malbert.core.model.Computer;

public final class ComputerMapper {
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
}
