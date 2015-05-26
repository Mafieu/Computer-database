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
			Computer comp = new Computer();
			if (computerDTO.getId() != null && computerDTO.getId() != 0) {
				comp.setId(computerDTO.getId());
			}
			comp.setName(computerDTO.getName());
			comp.setIntroduced(Utils.stringToLocaldatetime(
					computerDTO.getIntroduced(), pattern));
			comp.setDiscontinued(Utils.stringToLocaldatetime(
					computerDTO.getDiscontinued(), pattern));
			if (computerDTO.getCompanyId() != null && computerDTO.getCompanyId() != 0) {
				comp.setCompany(new Company(computerDTO.getCompanyId(),
						computerDTO.getCompanyName()));
			} else {
				comp.setCompany(null);
			}
			return comp;
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
								computer.getDiscontinued(), pattern), null, null);
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
