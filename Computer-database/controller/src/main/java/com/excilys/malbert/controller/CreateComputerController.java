package com.excilys.malbert.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.malbert.binding.CompanyMapper;
import com.excilys.malbert.binding.ComputerMapper;
import com.excilys.malbert.binding.model.CompanyDTO;
import com.excilys.malbert.binding.model.ComputerDTO;
import com.excilys.malbert.binding.validator.Date.Pattern;
import com.excilys.malbert.core.model.Company;
import com.excilys.malbert.service.ICompanyService;
import com.excilys.malbert.service.IComputerService;

/**
 * Servlet implementation class ServletCreate
 */
@Controller
@RequestMapping(value = "/addComputer")
public class CreateComputerController {
	@Autowired
	private ICompanyService serviceCompany;
	@Autowired
	private IComputerService serviceComputer;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(Model model) {
		ComputerDTO computerDTO = new ComputerDTO();
		List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
		for (Company company : serviceCompany.getAllCompanies()) {
			companiesDTO.add(CompanyMapper.companyToCompanydto(company));
		}

		model.addAttribute("companies", companiesDTO);
		model.addAttribute("computerDTO", computerDTO);

		return "addComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(@Valid @ModelAttribute ComputerDTO computerDTO,
			BindingResult result, Model model) {
		Locale locale = LocaleContextHolder.getLocale();
		Pattern language = Pattern.map(locale.getLanguage());
		if (result.hasErrors()) {
			List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
			for (Company company1 : serviceCompany.getAllCompanies()) {
				companiesDTO.add(CompanyMapper.companyToCompanydto(company1));
			}
			model.addAttribute("companies", companiesDTO);
			return "addComputer";
		} else {
			serviceComputer.createComputer(ComputerMapper
					.computerdtoToComputer(computerDTO, language));
			return "redirect:dashboard";
		}
	}

}
