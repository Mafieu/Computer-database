package com.excilys.malbert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.malbert.service.IComputerService;

@Controller
@RequestMapping(value = "/deleteComputer")
public class DeleteComputerController {

	@Autowired
	private IComputerService serviceComputer;

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(@ModelAttribute("selection") String selection) {
		// We should get id,id,id : see dashboard.js for more explanation
		String[] ids = selection.split(",");
		for (String id : ids) {
			serviceComputer.deleteComputer(Long.parseLong(id));
		}

		return "redirect:dashboard";
	}
}
