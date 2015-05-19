package com.excilys.malbert.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.malbert.mapper.CompanyMapper;
import com.excilys.malbert.mapper.ComputerMapper;
import com.excilys.malbert.model.Company;
import com.excilys.malbert.model.CompanyDTO;
import com.excilys.malbert.model.Computer;
import com.excilys.malbert.model.ComputerDTO;
import com.excilys.malbert.service.ICompanyService;
import com.excilys.malbert.service.IComputerService;
import com.excilys.malbert.validator.Date.Pattern;

/**
 * Servlet implementation class ServletEdit
 */
@Controller
@RequestMapping(value = "/editComputer")
public class EditComputerController {
	@Autowired
	private IComputerService serviceComputer;
	@Autowired
	private ICompanyService serviceCompany;

	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(@RequestParam(value = "id") long id, Model model) {
		Locale locale = LocaleContextHolder.getLocale();
		Pattern language = Pattern.map(locale.getLanguage());
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
							.add(CompanyMapper.companyToCompanydto(company));
				}

				model.addAttribute("companies", companiesDTO);
				model.addAttribute("computerDTO", ComputerMapper
						.computerToComputerdto(computer, language));
				return "editComputer";
			}
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(@Valid @ModelAttribute ComputerDTO computerDTO,
			BindingResult result, Model model) {
		Locale locale = LocaleContextHolder.getLocale();
		Pattern language = Pattern.map(locale.getLanguage());
		if (result.hasErrors()) {
			List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();

			model.addAttribute("companies", companiesDTO);
			return "editComputer";
		} else {
			serviceComputer.updateComputer(ComputerMapper
					.computerdtoToComputer(computerDTO, language));
			return "redirect:dashboard";
		}
	}

}
