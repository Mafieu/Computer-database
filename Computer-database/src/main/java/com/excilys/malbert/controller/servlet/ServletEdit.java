package com.excilys.malbert.controller.servlet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.malbert.controller.dto.CompanyDTO;
import com.excilys.malbert.controller.dto.ComputerDTO;
import com.excilys.malbert.mapper.MapperCompany;
import com.excilys.malbert.mapper.MapperComputer;
import com.excilys.malbert.persistence.model.Company;
import com.excilys.malbert.persistence.model.Computer;
import com.excilys.malbert.service.IServiceCompany;
import com.excilys.malbert.service.IServiceComputer;
import com.excilys.malbert.util.Utils;

/**
 * Servlet implementation class ServletEdit
 */
@Controller
@RequestMapping(value = "/editComputer")
public class ServletEdit {
    @Autowired
    private IServiceComputer serviceComputer;
    @Autowired
    private IServiceCompany serviceCompany;

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(@RequestParam(value = "id") long id, Model model) {
	if (id <= 0) {
	    return "redirect:dashboard";
	} else {
	    Computer computer = null;
	    List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
	    computer = serviceComputer.getComputer(id);
	    if (computer == null) {
		return "redirect:dashboard";
	    } else {
		for (Company company : serviceCompany.getAllCompanies()) {
		    companiesDTO
			    .add(MapperCompany.companyToCompanydto(company));
		}

		model.addAttribute("companies", companiesDTO);
		model.addAttribute("computer",
			MapperComputer.computerToComputerdto(computer));
		return "editComputer";
	    }
	}
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(@ModelAttribute ComputerDTO computerDTO, Model model) {
	if (computerDTO.getName() == ""
		|| computerDTO.getIntroduced() != ""
		&& Utils.stringToLocaldatetime(computerDTO.getIntroduced()) == null
		|| computerDTO.getDiscontinued() != ""
		&& Utils.stringToLocaldatetime(computerDTO.getDiscontinued()) == null) {
	    model.addAttribute("error", true);
	    return "addComputer";
	} else {
	    serviceComputer.updateComputer(MapperComputer
		    .computerdtoToComputer(computerDTO));
	    return "redirect:dashboard";
	}
    }

}
