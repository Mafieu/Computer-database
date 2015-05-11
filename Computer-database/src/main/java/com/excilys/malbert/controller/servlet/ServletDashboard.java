package com.excilys.malbert.controller.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.malbert.controller.Page;
import com.excilys.malbert.service.IServiceCompany;
import com.excilys.malbert.service.IServiceComputer;

@Controller
@RequestMapping(value = "/dashboard")
public class ServletDashboard {
    @Autowired
    private IServiceComputer serviceComputer;
    @Autowired
    private IServiceCompany serviceCompany;

    @RequestMapping(method = RequestMethod.GET)
    public String dashboard(@Validated @ModelAttribute Page page,
	    BindingResult result, Model model) {
	if (result.hasErrors()) {
	    model.addAttribute("error", true);
	    model.addAttribute("errorMessage", "Invalid arguments");
	    page.toDefault();
	}
	page.populate(serviceComputer, serviceCompany);
	model.addAttribute(page);
	return "dashboard";
    }
}
