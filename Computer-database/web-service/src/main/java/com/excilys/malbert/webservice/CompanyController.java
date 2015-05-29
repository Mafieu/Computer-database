package com.excilys.malbert.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.malbert.binding.CompanyMapper;
import com.excilys.malbert.binding.model.CompanyDTO;
import com.excilys.malbert.service.ICompanyService;

// We use Spring RestController to publish the web services
@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	ICompanyService companyService;

	@RequestMapping(method = RequestMethod.GET, value = "/getAll")
	public Object[] getAll() {
		// First usage of Java8 streams ! Yay \o/
		return companyService.getAllCompanies().stream()
				.map(c -> CompanyMapper.companyToCompanydto(c)).toArray();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/find")
	public CompanyDTO find(@RequestParam(value = "id") Long id) {
		return CompanyMapper.companyToCompanydto(companyService.getCompany(id));
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/delete")
	public void delete(@RequestParam(value = "id") Long id) {
		companyService.deleteCompany(id);
	}
}
