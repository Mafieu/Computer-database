package com.excilys.malbert.binding;

import com.excilys.malbert.binding.model.CompanyDTO;
import com.excilys.malbert.core.model.Company;

/**
 * Mapper for Company and CompanyDTO
 * 
 * @author excilys
 */
public final class CompanyMapper {
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
}
