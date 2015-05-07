package com.excilys.malbert.controller.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.malbert.controller.dto.CompanyDTO;
import com.excilys.malbert.controller.dto.ComputerDTO;
import com.excilys.malbert.mapper.MapperCompany;
import com.excilys.malbert.mapper.MapperComputer;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.service.IServiceCompany;
import com.excilys.malbert.service.IServiceComputer;
import com.excilys.malbert.util.Utils;

/**
 * Servlet implementation class ServletCreate
 */
@Controller
@RequestMapping(value = "/addComputer")
public class ServletCreate {
    @Autowired
    private IServiceCompany serviceCompany;
    @Autowired
    private IServiceComputer serviceComputer;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(Model model) {
	List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
	for (Company company : serviceCompany.getAllCompanies()) {
	    companiesDTO.add(MapperCompany.companyToCompanydto(company));
	}

	model.addAttribute("companies", companiesDTO);
	model.addAttribute("error", false);

	return "addComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(@ModelAttribute ComputerDTO computerDTO, Model model) {
	if (computerDTO.getName() == ""
		|| computerDTO.getIntroduced() != ""
		&& Utils.stringToLocaldatetime(computerDTO.getIntroduced()) == null
		|| computerDTO.getDiscontinued() != ""
		&& Utils.stringToLocaldatetime(computerDTO.getDiscontinued()) == null) {
	    List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
	    for (Company company1 : serviceCompany.getAllCompanies()) {
		companiesDTO.add(MapperCompany.companyToCompanydto(company1));
	    }

	    model.addAttribute("companies", companiesDTO);
	    model.addAttribute("error", true);
	    return "addComputer";
	} else {
	    serviceComputer.createComputer(MapperComputer
		    .computerdtoToComputer(computerDTO));
	    return "redirect:dashboard";
	}
    }

}
